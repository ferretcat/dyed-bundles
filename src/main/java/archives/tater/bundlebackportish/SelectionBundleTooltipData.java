package archives.tater.bundlebackportish;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.tooltip.TooltipData;

public record SelectionBundleTooltipData(
        BundleContentsComponent contents,
        int selected
) implements TooltipData {}
