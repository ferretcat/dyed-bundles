package archives.tater.bundlebackportish.mixin.client;

import archives.tater.bundlebackportish.SelectionBundleTooltipComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(BundleTooltipComponent.class)
public class BundleTooltipComponentMixin {
    @ModifyVariable(
            method = "drawSlot",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V", shift = At.Shift.AFTER),
            ordinal = 2,
            argsOnly = true
    )
    private int checkSelected(int index) {
        //noinspection ConstantValue
        if (!((Object) this instanceof SelectionBundleTooltipComponent selection)) return index;
        if (index == selection.selected)
            return 0; // For ifne check
        return -1;
    }
}
