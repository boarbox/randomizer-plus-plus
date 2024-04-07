package science.boarbox.randomizer_plus_plus.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomizerUtil {
    public static <T>List<T> createShuffledList(List<T> unshuffledList, Random random) {
        List<T> shuffledList = new ArrayList<>(unshuffledList);
        Collections.shuffle(shuffledList, random);
        return shuffledList;
    }

    public static JsonObject generateSpoilerJson(List<Identifier> unshuffledList, List<Identifier> shuffledList) {
        if (unshuffledList.size() != shuffledList.size()) throw new IllegalArgumentException("unshuffledList and shuffledList cannot differ in size");
        JsonObject spoilers = new JsonObject();
        for (int i=0; i<shuffledList.size(); i++) {
            spoilers.add(
                    unshuffledList.get(i).toString(),
                    new JsonPrimitive(shuffledList.get(i).toString())
            );
        }

        return spoilers;
    }
}
