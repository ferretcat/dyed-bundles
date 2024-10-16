package archives.tater.bundlescroll.mixin.client;

import archives.tater.bundlescroll.SelectedHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.item.BundleTooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BundleTooltipComponent.class)
public class BundleTooltipComponentMixin {
    @Unique
    private int bundleScroll$selected = 0;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void readSelected(BundleTooltipData data, CallbackInfo ci) {
        bundleScroll$selected = ((SelectedHolder) data).bundleScroll$getSelected();
    }

    @ModifyVariable(
            method = "drawSlot",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V", shift = At.Shift.AFTER),
            ordinal = 2,
            argsOnly = true
    )
    private int checkSelected(int index) {
        if (index == bundleScroll$selected)
            return 0; // For ifne check
        return -1;
    }
}
