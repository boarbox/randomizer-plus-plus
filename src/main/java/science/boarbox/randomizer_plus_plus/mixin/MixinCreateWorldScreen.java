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
import science.boarbox.randomizer_plus_plus.generation.RandomizerSettings;
import science.boarbox.randomizer_plus_plus.component.ModComponents;
import science.boarbox.randomizer_plus_plus.component.RandomizerSettingsComponent;
import science.boarbox.randomizer_plus_plus.gui.RandomizerTab;

@Environment(EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen {
    @Unique
    private RandomizerSettings randomizerSettings = new RandomizerSettings();

    @ModifyExpressionValue(method = "startServer", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Lnet/minecraft/world/level/LevelProperties$SpecialProperty;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/world/level/LevelProperties;"))
    private LevelProperties preStartServerLevelProperties(LevelProperties saveProperties) {
        // at the point the server can start randomizer seed settings have been finalized
        // With this in mind we attach a component to the level to inject custom nbt data to the global save between instantiation and returning the instance
        RandomizerSettingsComponent component = saveProperties.getComponent(ModComponents.RANDOMIZER_SETTINGS);
        component.setSettings(randomizerSettings);
        component.writeToNbt(randomizerSettings.toNbtCompound());

        // clear randomizer settings for if the screen is reopened later, as to make sure settings don't get loaded from the last played world
        this.randomizerSettings = new RandomizerSettings();

        return saveProperties;
    }

    @ModifyExpressionValue(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;"))
    private TabNavigationWidget.Builder addRandomizerSettingsTab(TabNavigationWidget.Builder original) {
        // inject a new tab into the create world screen for all the randomizer settings GUI elements
        return original.tabs(new RandomizerTab(randomizerSettings));
    }
}
