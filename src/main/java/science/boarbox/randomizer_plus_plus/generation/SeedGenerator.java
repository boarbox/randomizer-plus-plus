package science.boarbox.randomizer_plus_plus.generation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.util.RandomizerUtil;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class SeedGenerator {
    public final long seed;
    private final Random random;
    private final RandomizerSettings settings;

    private final JsonObject spoilers = new JsonObject();


    public SeedGenerator(long seed, RandomizerSettings settings) {
        this.seed = seed;
        this.settings = settings;

        this.random = new MinecraftRandom(this.seed);
    }

    public void addPool(String name, SettingsApplier applier, BiConsumer<Identifier, Identifier> function) {
        List<Identifier> originalList = List.copyOf(applier.apply(this.settings));
        List<Identifier> shuffledList = RandomizerUtil.shuffleList(originalList, this.random);

        JsonObject sectionSpoilers = new JsonObject();
        for (int i=0; i<originalList.size(); i++) {
            Identifier from = originalList.get(i);
            Identifier to = shuffledList.get(i);

            function.accept(from, to);
            sectionSpoilers.add(from.toString(), new JsonPrimitive(to.toString()));
        }

        this.spoilers.add(name, sectionSpoilers);
    }

    public JsonElement getSpoilers() {
        return this.spoilers;
    }
}
