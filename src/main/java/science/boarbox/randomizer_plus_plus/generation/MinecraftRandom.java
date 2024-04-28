package science.boarbox.randomizer_plus_plus.generation;

import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;

import java.util.List;
import java.util.Random;

/**
 * This class is a compatibility layer to allow use with method {@link java.util.Collections#shuffle(List, Random)}
 */
public class MinecraftRandom extends Random {
    private final Xoroshiro128PlusPlusRandom random;

    public MinecraftRandom(long seed) {
        this.random = new Xoroshiro128PlusPlusRandom(seed);
    }

    @Override
    public int nextInt() {
        return this.random.nextInt();
    }

    @Override
    public int nextInt(int val) {
        return this.random.nextInt(val);
    }


    @Override
    public long nextLong() {
        return this.random.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return this.random.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return this.random.nextFloat();
    }

    @Override
    public double nextDouble() {
        return this.random.nextDouble();
    }

    @Override
    public double nextGaussian() {
        return this.random.nextGaussian();
    }
}
