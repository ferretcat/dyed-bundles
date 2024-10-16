package archives.tater.bundlescroll.mixin;

import archives.tater.bundlescroll.BundleSelection;
import archives.tater.bundlescroll.SelectedHolder;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    @ModifyArg(
            method = "getTooltipData",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;of(Ljava/lang/Object;)Ljava/util/Optional;"),
            index = 0
    )
    private Object setDataSelected(Object value, @Local(argsOnly = true) ItemStack itemStack) {
        ((SelectedHolder) value).bundleScroll$setSelected(BundleSelection.get(itemStack));
        return value;
    }

    @ModifyConstant(
            method = "removeFirstStack",
            constant = @Constant(intValue = 0)
    )
    private static int useSelected(int constant, @Local(argsOnly = true) ItemStack itemStack) {
        return BundleSelection.get(itemStack);
    }
}
