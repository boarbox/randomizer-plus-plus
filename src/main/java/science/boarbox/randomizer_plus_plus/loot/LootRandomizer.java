package science.boarbox.randomizer_plus_plus.loot;

import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;
import science.boarbox.randomizer_plus_plus.util.RandomizerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LootRandomizer {
    public static void randomizeLootTables(String namespace, String prefix, Set<Identifier> blacklist, Random random) throws IOException {
        Map<Identifier, LootTable> lootTableMap = LootUtil.getLootTableMapWithExclusions(namespace, prefix, blacklist);

        List<Identifier> unshuffledList = lootTableMap.keySet().stream().toList();
        List<Identifier> shuffledList = RandomizerUtil.createShuffledList(unshuffledList, random);
        for (int i=0; i<lootTableMap.size(); i++) {
            RandomizerPlusPlus.RESOURCE_PACK.addLootTable(
                    IdentifierUtil.omit(unshuffledList.get(i), "loot_tables/", ".json"), lootTableMap.get(shuffledList.get(i)));
        }
    }
}
