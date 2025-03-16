package archives.tater.bundlebackportish;

import net.minecraft.client.gui.tooltip.BundleTooltipComponent;

public class SelectionBundleTooltipComponent extends BundleTooltipComponent {
    public final int selected;

    public SelectionBundleTooltipComponent(SelectionBundleTooltipData data) {
        super(data.contents());
        selected = data.selected();
    }
}
