package science.boarbox.randomizer_plus_plus.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.NbtCompound;

import java.lang.reflect.Type;

/**
 * A class for storing settings for a single randomizer seed. This class also provides easy methods for converting from and to NBT data
 */
public class RandomizerSettings {
    private boolean enabled;

    public RandomizerSettings() {
        this.enabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public NbtCompound toNbtCompound() {
        var compound = new NbtCompound();
        compound.putBoolean("enabled", this.enabled);

        return compound;
    }

    public static RandomizerSettings create(NbtCompound tag) {
        var settings = new RandomizerSettings();
        settings.setEnabled(tag.getBoolean("enabled"));
        return settings;
    }

    public static class Serializer implements JsonDeserializer<RandomizerSettings> {

        @Override
        public RandomizerSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var settings = new RandomizerSettings();
            var obj = json.getAsJsonObject();
            settings.setEnabled(obj.get("enabled").getAsBoolean());


            return settings;
        }
    }
}
