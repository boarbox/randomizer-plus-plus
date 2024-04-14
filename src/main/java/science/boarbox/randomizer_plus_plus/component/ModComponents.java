package science.boarbox.randomizer_plus_plus.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;

public final class ModComponents implements LevelComponentInitializer {
    public static final ComponentKey<RandomizerSettingsComponent> RANDOMIZER_SETTINGS =
            ComponentRegistryV3.INSTANCE.getOrCreate(IdentifierUtil.create("randomizer_settings"), RandomizerSettingsComponent.class);

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(RANDOMIZER_SETTINGS, LevelRandomizerSettingsComponent::new);
    }
}
