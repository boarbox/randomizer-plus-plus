package science.boarbox.randomizer_plus_plus.util;

import net.minecraft.util.Identifier;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;

public class IdentifierUtil {
    public static Identifier createForMod(String path) {
        return new Identifier(RandomizerPlusPlus.MOD_ID, path);
    }
    public static Identifier create(String s) {
        int nameSpaceLen = s.indexOf(":") + 1;
        return new Identifier(s.substring(0, nameSpaceLen - 1), s.substring(nameSpaceLen));
    }

    public static Identifier omitSuffix(Identifier identifier, String suffix) {
        if (!identifier.getPath().endsWith(suffix)) return  identifier;
        String path = identifier.getPath();
        return new Identifier(identifier.getNamespace(), path.substring(0, path.length() - suffix.length()));
    }

    public static Identifier omitPrefix(Identifier identifier, String prefix) {
        if (!identifier.getPath().startsWith(prefix)) return identifier;

        return new Identifier(identifier.getNamespace(), identifier.getPath().substring(prefix.length()));
    }

    public static Identifier omit(Identifier identifier, String prefix, String suffix) {
        return IdentifierUtil.omitSuffix(
                IdentifierUtil.omitPrefix(identifier, prefix),
                suffix
        );
    }

    public static Identifier prefixPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + "/" + identifier.getPath());
    }
}
