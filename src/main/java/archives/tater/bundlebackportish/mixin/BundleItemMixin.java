package archives.tater.bundlebackportish.mixin;

import archives.tater.bundlebackportish.BundleSelection;
import archives.tater.bundlebackportish.SelectionBundleTooltipData;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyReturnValue(
            method = "getTooltipData",
            at = @At("RETURN")
    )
    private Optional<TooltipData> setDataSelected(Optional<TooltipData> original, @Local(argsOnly = true) ItemStack itemStack) {
        return original.map(tooltipData ->
                tooltipData instanceof net.minecraft.item.tooltip.BundleTooltipData bundleData
                        ? new SelectionBundleTooltipData(bundleData.contents(), BundleSelection.get(itemStack))
                        : tooltipData);
    }

    @WrapOperation(
            method = "onClicked",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/BundleContentsComponent$Builder;removeFirst()Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack removeSelected(BundleContentsComponent.Builder instance, Operation<ItemStack> original, @Local(argsOnly = true, ordinal = 0) ItemStack stack) {
        var selected = BundleSelection.get(stack);
        if (selected == 0) return original.call(instance);
        return BundleSelection.remove(instance, selected);
    }

    @ModifyExpressionValue(
            method = "onClicked",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/BundleContentsComponent$Builder;build()Lnet/minecraft/component/type/BundleContentsComponent;")
    )
    private BundleContentsComponent fixAfterRemove(BundleContentsComponent original, @Local(argsOnly = true, ordinal = 0) ItemStack stack) {
        if (original.isEmpty())
            BundleSelection.clear(stack);
        else if (BundleSelection.get(stack) >= original.size())
            BundleSelection.set(stack, original.size() - 1);
        return original;
    }

    @Inject(
            method = "dropAllBundledItems",
            at = @At(value = "RETURN", ordinal = 0)
    )
    private static void fixAfterDropAll(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        BundleSelection.clear(stack);
    }
}
