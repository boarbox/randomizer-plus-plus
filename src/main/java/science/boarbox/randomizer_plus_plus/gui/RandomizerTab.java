package science.boarbox.randomizer_plus_plus.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.text.Text;
import science.boarbox.randomizer_plus_plus.config.RandomizerSettings;

/**
 * GUI for selecting randomizer settings for a new seed
 */
@Environment(EnvType.CLIENT)
public class RandomizerTab extends GridScreenTab {
    private static final Text RANDOMIZER_ENABLED_TEXT = Text.literal("Enabled");
    private final RandomizerSettings settings;
    private final GridWidget.Adder adder;
    private final Positioner positioner;

    private CheckboxWidget enabledWidget;

    public RandomizerTab(RandomizerSettings settings) {
        super(Text.literal("Randomizer"));

        this.settings = settings;
        this.adder = this.grid.setRowSpacing(8).createAdder(2);
        this.positioner = this.adder.copyPositioner();;

       this.init();
       this.build();
    }

    private void initEnabledWidget() {
        this.enabledWidget = CheckboxWidget.builder(RANDOMIZER_ENABLED_TEXT, MinecraftClient.getInstance().textRenderer)
                .checked(this.settings.isEnabled())
                .callback(((checkbox, checked) -> {
                    this.settings.setEnabled(checked);
                }))
                .build();
    }

    private void init() {
        this.initEnabledWidget();
    }

    private void build() {
        this.positioner.alignHorizontalCenter();
        this.adder.add(this.enabledWidget, 2);
        this.positioner.alignLeft();
    }
}
