package science.boarbox.randomizer_plus_plus.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import science.boarbox.randomizer_plus_plus.config.RandomizerSettings;

public interface RandomizerSettingsComponent extends Component {
    RandomizerSettings getSettings();
}
