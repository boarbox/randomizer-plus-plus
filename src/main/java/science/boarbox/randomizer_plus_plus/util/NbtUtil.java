package science.boarbox.randomizer_plus_plus.util;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.Collection;

public final class NbtUtil {
    public static NbtList asNbtList(Collection<NbtElement> elements) {
        NbtList list = new NbtList();
        list.addAll(elements);
        return list;
    }

    public static NbtElement toNbtElement(Identifier element) {
        return NbtString.of(element.toString());
    }

}
