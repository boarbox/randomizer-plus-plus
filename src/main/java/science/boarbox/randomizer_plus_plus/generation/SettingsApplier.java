package science.boarbox.randomizer_plus_plus.generation;

import net.minecraft.util.Identifier;

import java.util.Collection;

@FunctionalInterface
public interface SettingsApplier {
    Collection<Identifier> apply(RandomizerSettings settings);
}
