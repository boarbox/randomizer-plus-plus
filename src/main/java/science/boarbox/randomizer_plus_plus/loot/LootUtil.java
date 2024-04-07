package science.boarbox.randomizer_plus_plus.loot;

import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.resource.ResourcePackUtil;
import science.boarbox.randomizer_plus_plus.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class LootUtil {
    public static Map<Identifier, LootTable> getLootTableMapWithExclusions(String namespace, String prefix, Set<Identifier> blacklist) throws IOException {
        HashMap<Identifier, LootTable> tables = new HashMap<>();

        for (Map.Entry<Identifier, InputSupplier<InputStream>> entry : ResourcePackUtil.getUnexcludedResourceSuppliersMap(
                ResourceType.SERVER_DATA,
                namespace,
                "loot_tables/" + (prefix != null ? prefix : ""),
                blacklist
        ).entrySet()) {
            tables.put(entry.getKey(), LootDataType.LOOT_TABLES.parse(entry.getKey(), JsonUtil.readToJson(entry.getValue().get())).get());
        }

        return tables;
    }
}
