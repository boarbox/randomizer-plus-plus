package science.boarbox.randomizer_plus_plus.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;
import science.boarbox.randomizer_plus_plus.loot.LootRandomizer;
import science.boarbox.randomizer_plus_plus.resource.ResourcePackUtil;
import science.boarbox.randomizer_plus_plus.util.JsonUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

@Mixin(MinecraftServer.class)
public abstract class WorldCreateMixin {
    @Inject(method = "createWorlds", at = @At(value = "TAIL"))
    private void generateSeed(CallbackInfo info) {
        MinecraftServer server = (MinecraftServer) (Object) this;
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
