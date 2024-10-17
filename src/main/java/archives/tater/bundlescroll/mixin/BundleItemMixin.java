package archives.tater.bundlescroll.mixin;

import archives.tater.bundlescroll.BundleSelection;
import archives.tater.bundlescroll.SelectionBundleTooltipData;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    @Redirect(
            method = "getTooltipData",
            at = @At(value = "NEW", target = "(Lnet/minecraft/util/collection/DefaultedList;I)Lnet/minecraft/client/item/BundleTooltipData;")
    )
    private BundleTooltipData setDataSelected(DefaultedList<ItemStack> inventory, int bundleOccupancy, @Local(argsOnly = true) ItemStack itemStack) {
        return new SelectionBundleTooltipData(inventory, bundleOccupancy, BundleSelection.get(itemStack));
    }

    @ModifyConstant(
            method = "removeFirstStack",
            constant = @Constant(intValue = 0)
    )
    private static int useSelected(int constant, @Local(argsOnly = true) ItemStack itemStack) {
        return BundleSelection.get(itemStack);
    }
}
