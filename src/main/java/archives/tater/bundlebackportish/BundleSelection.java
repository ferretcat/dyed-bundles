package archives.tater.bundlebackportish;

import archives.tater.bundlebackportish.mixin.BundleContentsComponentBuilderAccessor;
import archives.tater.bundlebackportish.mixin.BundleContentsComponentInvoker;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;

import static java.lang.Math.*;

public class BundleSelection {
    private static int getContentsSize(ItemStack itemStack) {
        return itemStack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).size();
    }

    private static int clampToContents(ItemStack itemStack, int index) {
        return clamp(index, 0, max(getContentsSize(itemStack) - 1, 0));
    }

    public static int get(ItemStack itemStack) {
        return clampToContents(itemStack, itemStack.getOrDefault(BundleBackportishItems.BUNDLE_SELECTION, 0));
    }

    public static void set(ItemStack itemStack, int selected) {
        itemStack.set(BundleBackportishItems.BUNDLE_SELECTION, clampToContents(itemStack, selected));
    }

    public static void clear(ItemStack itemStack) {
        itemStack.remove(BundleBackportishItems.BUNDLE_SELECTION);
    }

    public static void add(ItemStack itemStack, int amount) {
        set(itemStack, floorMod(get(itemStack) + amount, max(getContentsSize(itemStack), 1)));
    }

    public static ItemStack remove(BundleContentsComponent.Builder contents, int index) {
        var stacks = ((BundleContentsComponentBuilderAccessor) contents).bundlebackportish$getStacks();
        if (stacks.isEmpty())
            return null;

        var itemStack = stacks.remove(clamp(index, 0, stacks.size())).copy();
        ((BundleContentsComponentBuilderAccessor) contents).bundlebackportish$setOccupancy(
                contents.getOccupancy().subtract(
                        BundleContentsComponentInvoker.invokeGetOccupancy(itemStack).multiplyBy(
                                Fraction.getFraction(itemStack.getCount(), 1))));
        return itemStack;
    }
}
