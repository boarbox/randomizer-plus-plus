package science.boarbox.randomizer_plus_plus.mixin;

import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.util.WorldSavePath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;
import science.boarbox.randomizer_plus_plus.component.ModComponents;
import science.boarbox.randomizer_plus_plus.config.RandomizerSettings;
import science.boarbox.randomizer_plus_plus.loot.LootRandomizer;
import science.boarbox.randomizer_plus_plus.resource.ResourcePackUtil;
import science.boarbox.randomizer_plus_plus.util.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(method = "createWorlds", at = @At("HEAD"))
    private void ensureDedicatedServerHasSeedSettings(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) throws IOException {
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
            Random random = new Random(seed);

            RandomizerPlusPlus.LOGGER.info("Generating seed");
            try {
                JsonUtil.saveToFile(server.getSavePath(WorldSavePath.ROOT), "spoilers.json",
                        LootRandomizer.randomizeLootTables("minecraft", "blocks", new HashSet<>(),random)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            RandomizerPlusPlus.LOGGER.info("Saving seed");
            ResourcePackUtil.saveResourcePack(server.getSavePath(WorldSavePath.DATAPACKS).resolve(
                    "randomizer-data-" + seed + ".zip"));
        }
    }

    @Unique
    private RandomizerSettings getRandomizerSettings() throws IOException {
        MinecraftServer server = (MinecraftServer) (Object) this;
        if (!server.isDedicated()) {
            return server.getSaveProperties().getMainWorldProperties().getComponent(ModComponents.RANDOMIZER_SETTINGS).getSettings();
        }
        var settingsPath = server.getFile("randomizer-plus-plus.settings.json");
        return new GsonBuilder()
                .registerTypeAdapter(RandomizerSettings.class, new RandomizerSettings.Serializer())
                        .create()
                                .fromJson(Files.readString(settingsPath.toPath()), RandomizerSettings.class);

    }
}
