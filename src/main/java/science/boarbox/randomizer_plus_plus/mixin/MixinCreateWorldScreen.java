package science.boarbox.randomizer_plus_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import science.boarbox.randomizer_plus_plus.component.LevelRandomizerSettingsComponent;
import science.boarbox.randomizer_plus_plus.component.ModComponents;
import science.boarbox.randomizer_plus_plus.config.RandomizerSettings;
import science.boarbox.randomizer_plus_plus.gui.RandomizerTab;

@Environment(EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen {
    @Unique
    private RandomizerSettings randomizerSettings = new RandomizerSettings();

    @ModifyExpressionValue(method = "startServer", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Lnet/minecraft/world/level/LevelProperties$SpecialProperty;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/world/level/LevelProperties;"))
    private LevelProperties preStartServerLevelProperties(LevelProperties saveProperties) {
        LevelRandomizerSettingsComponent component = (LevelRandomizerSettingsComponent)saveProperties.getComponent(ModComponents.RANDOMIZER_SETTINGS);
        component.setSettings(randomizerSettings);
        component.writeToNbt(randomizerSettings.toNbtCompound());
        this.randomizerSettings = new RandomizerSettings();
        return saveProperties;
    }

    @ModifyExpressionValue(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;"))
    private TabNavigationWidget.Builder addRandomizerSettingsTab(TabNavigationWidget.Builder original) {
        return original.tabs(new RandomizerTab(randomizerSettings));
    }
}
