package science.boarbox.randomizer_plus_plus.util;


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
import java.util.zip.ZipOutputStream;

public final class ResourcePackUtil {
    public static @NotNull Map<Identifier, InputSupplier<InputStream>> getUnexcludedResourceSuppliersMap(
            ResourceType resourceType,
            @NotNull String namespace,
            @NotNull String prefix
    ) {
        var resourcePack =  VanillaDataPackProvider.createDefaultPack();
        Map<Identifier, InputSupplier<InputStream>> result = new HashMap<>();

        resourcePack.findResources(resourceType, namespace, prefix, ((identifier, inputStreamInputSupplier) -> {
            result.put(IdentifierUtil.omit(identifier, prefix + "/", ".json"), inputStreamInputSupplier);
        }));


        resourcePack.close();
        return result;
    }

    public static void saveResourcePack(Path saveLocation) {
        try {
            var output = Files.newOutputStream(saveLocation);
            var buffer =  new BufferedOutputStream(output);
            var zip = new ZipOutputStream(buffer);

            RandomizerPlusPlus.RESOURCE_PACK.dump(zip);

            // cleanup
            zip.close();
            buffer.close();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
