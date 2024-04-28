package science.boarbox.randomizer_plus_plus.mixin;

import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.util.WorldSavePath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;
import science.boarbox.randomizer_plus_plus.generation.RandomizerSettings;
import science.boarbox.randomizer_plus_plus.component.ModComponents;
import science.boarbox.randomizer_plus_plus.generation.SeedGenerator;
import science.boarbox.randomizer_plus_plus.loot.LootRandomizer;
import science.boarbox.randomizer_plus_plus.util.JsonUtil;
import science.boarbox.randomizer_plus_plus.util.ResourcePackUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(method = "createWorlds", at = @At("HEAD"))
    private void ensureDedicatedServerHasSeedSettings(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) throws IOException {
        // Since dedicated servers don't currently have an interactive method for choosing randomizer settings,
        // in case any worldgen based randomization options are added in future, the settings need to be provided prior to world gen,
        // if not cause a crash to force the server admin to include the settings file
        MinecraftServer server = (MinecraftServer) (Object) this;

        if (!server.isDedicated()) return;

        if (!server.getFile("randomizer-plus-plus.settings.json").exists()) {
            RandomizerPlusPlus.LOGGER.error("Please ensure a valid settings file named 'randomizer-plus-plus.settings.json' is in the root directory of the server!");
            throw new IOException("Missing randomizer-plus-plus.settings.json file");
        }
    }

    @Inject(method = "createWorlds", at = @At(value = "TAIL"))
    private void generateSeed(CallbackInfo info) throws IOException {
        MinecraftServer server = (MinecraftServer) (Object) this;
        RandomizerPlusPlus.LOGGER.info("Server dedicated?: " + server.isDedicated());
        RandomizerSettings settings = this.getRandomizerSettings();;
        if (settings.isEnabled()) {
            long seed = server.getSaveProperties().getGeneratorOptions().getSeed();
//            Random random = new Random(seed);
            SeedGenerator seedGenerator = new SeedGenerator(seed, settings);
            seedGenerator.addPool("blocks", LootRandomizer::getBlockOptions, LootRandomizer::applyBlockRandomization);


            RandomizerPlusPlus.LOGGER.info("Generating seed");

            // Randomization step + store results for spoilers
//            var blockLootTableSpoilers =  LootRandomizer.randomizeLootTables("minecraft", "blocks", random);

            // Create spoilers
//            var spoilers = new JsonObject();
//            spoilers.add("blocks", blockLootTableSpoilers);

            // Save spoilers to save root directory
            JsonUtil.saveToFile(server.getSavePath(WorldSavePath.ROOT), "spoilers.json", seedGenerator.getSpoilers());

            // finally save as a datapack so on future loads, generation step can be skipped and instead load
            // from the persisted datapack
            RandomizerPlusPlus.LOGGER.info("Saving seed datapack");
            ResourcePackUtil.saveResourcePack(server.getSavePath(WorldSavePath.DATAPACKS).resolve(
                    "randomizer-data-" + seed + ".zip"));
        }
    }

    @Unique
    private RandomizerSettings getRandomizerSettings() throws IOException {
        MinecraftServer server = (MinecraftServer) (Object) this;

        // integrated client servers get settings directly injected into nbt data prior to the world gen completing
        if (!server.isDedicated()) {
            return ModComponents.accessRandomizerSettings(server.getSaveProperties()).getSettings();
        }

        // dedicated servers currently don't get settings injected like client side worlds at world gen,
        // so they are, at present, read from a json file
        var settingsPath = server.getFile("randomizer-plus-plus.settings.json");
        return RandomizerSettings.deserialize(Files.readString(settingsPath.toPath()));
    }
}
