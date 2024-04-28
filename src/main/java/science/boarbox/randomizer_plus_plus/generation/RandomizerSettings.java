package science.boarbox.randomizer_plus_plus.generation;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;
import science.boarbox.randomizer_plus_plus.util.NbtUtil;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * A class for storing settings for a single randomizer seed. This class also provides easy methods for converting from and to NBT data
 */
public class RandomizerSettings {
    private boolean enabled;
    private Set<Identifier> blockLootTableBlacklist;

    public RandomizerSettings() {
        this.enabled = false;
        this.blockLootTableBlacklist = new HashSet<>();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ImmutableSet<Identifier> getBlockLootTableBlacklist() {
        return ImmutableSet.copyOf(this.blockLootTableBlacklist);
    }

    private void setBlockLootTableBlacklist(Set<Identifier> blockLootTableBlacklist) {
        this.blockLootTableBlacklist = blockLootTableBlacklist;
    }

    public NbtCompound toNbtCompound() {
        var compound = new NbtCompound();
        compound.putBoolean("enabled", this.enabled);
        compound.put("blockLootTableBlacklist", NbtUtil.asNbtList(this.blockLootTableBlacklist.stream().map(NbtUtil::toNbtElement).toList()));


        return compound;
    }

    public static RandomizerSettings create(NbtCompound tag) {
        var settings = new RandomizerSettings();
        settings.setEnabled(tag.getBoolean("enabled"));
        settings.setBlockLootTableBlacklist(new HashSet<>(
                tag.getList("blockLootTableBlacklist", NbtElement.STRING_TYPE).stream()
                        .map(NbtElement::asString)
                        .map(IdentifierUtil::create)
                        .toList()
        ));
        return settings;
    }

    public static class Serializer implements JsonDeserializer<RandomizerSettings> {

        @Override
        public RandomizerSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var settings = new RandomizerSettings();
            var obj = json.getAsJsonObject();
            settings.setEnabled(obj.get("enabled").getAsBoolean());
            settings.setBlockLootTableBlacklist(new HashSet<>(obj.get("blockLootTableBlacklist").getAsJsonArray()
                    .asList().stream().map(JsonElement::getAsString).map(IdentifierUtil::create).toList()));

            return settings;
        }
    }

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(RandomizerSettings.class, new RandomizerSettings.Serializer())
            .create();

    public static RandomizerSettings deserialize(String json) {
        return gson.fromJson(json, RandomizerSettings.class);
    }
}
