package science.boarbox.randomizer_plus_plus.util;

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
}
