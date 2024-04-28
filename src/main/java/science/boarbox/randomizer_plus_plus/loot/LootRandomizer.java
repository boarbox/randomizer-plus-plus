package science.boarbox.randomizer_plus_plus.loot;

import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;
import science.boarbox.randomizer_plus_plus.generation.RandomizerSettings;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class LootRandomizer {
    private static final Map<Identifier, LootTable> BLOCK_LOOT_TABLES;
    static {
        try {
            BLOCK_LOOT_TABLES = LootUtil.getLootTableMap("minecraft", "blocks");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void applyBlockRandomization(Identifier from, Identifier to) {
        RandomizerPlusPlus.RESOURCE_PACK.addLootTable(
                IdentifierUtil.prefixPath(from, "blocks"), BLOCK_LOOT_TABLES.get(to));
    }

    public static Collection<Identifier> getBlockOptions(RandomizerSettings settings) {
        return BLOCK_LOOT_TABLES.keySet();
    }
}
