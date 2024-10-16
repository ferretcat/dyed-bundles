package archives.tater.bundlescroll;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class BundleSelection {
    public static int get(ItemStack itemStack) {
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound == null) return 0;
        return nbtCompound.getInt("Selected");
    }

    public static void set(ItemStack itemStack, int selected) {
        itemStack.getOrCreateNbt().putInt("Selected", selected);
    }
}
