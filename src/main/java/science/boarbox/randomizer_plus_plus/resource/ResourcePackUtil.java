package science.boarbox.randomizer_plus_plus.resource;


import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

public final class ResourcePackUtil {
    public static Map<Identifier, InputSupplier<InputStream>> getWhitelistedResourceSuppliersMap(
            ResourceType resourceType,
            @NotNull Set<Identifier> whitelist
    ) {
        var resourcePack =  VanillaDataPackProvider.createDefaultPack();
        Map<Identifier, InputSupplier<InputStream>> result = new HashMap<>();

        for (Identifier identifier : whitelist) {
            result.put(
                    identifier,
                    resourcePack.open(resourceType, identifier)
            );
        }

        resourcePack.close();
        return result;
    }

    public static Map<Identifier, InputSupplier<InputStream>> getUnexcludedResourceSuppliersMap(
            ResourceType resourceType,
            @NotNull String namespace,
            @NotNull String prefix,
            @NotNull Set<Identifier> blacklist
    ) {
        var resourcePack =  VanillaDataPackProvider.createDefaultPack();
        Map<Identifier, InputSupplier<InputStream>> result = new HashMap<>();

        resourcePack.findResources(resourceType, namespace, prefix, ((identifier, inputStreamInputSupplier) -> {
            if (!blacklist.contains(identifier)) {
                result.put(identifier, inputStreamInputSupplier);
            }
        }));


        resourcePack.close();
        return result;
    }

    public static void saveResourcePack(Path saveLocation) {
        try {
            RandomizerPlusPlus.RESOURCE_PACK.dump(new ZipOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(saveLocation)))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
