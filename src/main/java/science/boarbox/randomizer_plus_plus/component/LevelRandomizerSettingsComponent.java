package science.boarbox.randomizer_plus_plus.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.WorldProperties;
import science.boarbox.randomizer_plus_plus.generation.RandomizerSettings;

public class LevelRandomizerSettingsComponent implements RandomizerSettingsComponent, AutoSyncedComponent {
    private final WorldProperties provider;
    private RandomizerSettings settings = new RandomizerSettings();

    public LevelRandomizerSettingsComponent(WorldProperties provider) {
        this.provider = provider;
    }

    @Override
    public void setSettings(RandomizerSettings settings) {
        this.settings = settings;
    }

    @Override
    public RandomizerSettings getSettings() {
        return this.settings;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.settings = RandomizerSettings.create(tag.getCompound("RandomizerSettings"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.put("RandomizerSettings", this.settings.toNbtCompound());
    }
}
