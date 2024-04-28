package science.boarbox.randomizer_plus_plus.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomizerUtil {
    public static <T > @NotNull List<T> shuffleList(List<T> list, Random randomGenerator) {
       var copy = new ArrayList<T>(list);
       Collections.shuffle(copy, randomGenerator);

       return copy;
    }
}
