package science.boarbox.randomizer_plus_plus.loot;

import com.google.gson.JsonElement;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;
import science.boarbox.randomizer_plus_plus.util.JsonUtil;
import science.boarbox.randomizer_plus_plus.util.ResourcePackUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class LootUtil {
    private static LootTable deserializeLootTable(String prefix, Identifier identifier, JsonElement json) {
        var table = LootDataType.LOOT_TABLES.parse(
                IdentifierUtil.prefixPath(identifier, "loot_tables/" + prefix),
                json
        );
        if (table.isEmpty()) {
            throw new IllegalArgumentException(identifier.getNamespace() + ":" + "loot_tables/" + prefix + "/" + identifier.getPath() + " does not point to an existing loot table.");
        }
        return table.get();
    }

    public static Map<Identifier, LootTable> getLootTableMap(String namespace, @NotNull String prefix) throws IOException {
        if (prefix.isEmpty()) throw new IllegalArgumentException("prefix must not be an empty string");

        HashMap<Identifier, LootTable> tables = new HashMap<>();

        for (Map.Entry<Identifier, InputSupplier<InputStream>> entry : ResourcePackUtil.getUnexcludedResourceSuppliersMap(
                ResourceType.SERVER_DATA,
                namespace,
                "loot_tables/" + prefix
        ).entrySet()) {
            tables.put(
                    entry.getKey(),
                    deserializeLootTable(prefix, entry.getKey(), JsonUtil.readToJson(entry.getValue().get()))
            );
        }

        return tables;
    }
}
