package archives.tater.bundlescroll.mixin;

import archives.tater.bundlescroll.SelectedHolder;
import net.minecraft.client.item.BundleTooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BundleTooltipData.class)
public class BundleTooltipDataMixin implements SelectedHolder {
	@Unique
	private int bundleScroll$selected = 0;

	@Override
	public int bundleScroll$getSelected() {
		return bundleScroll$selected;
	}

	@Override
	public void bundleScroll$setSelected(int selected) {
		bundleScroll$selected = selected;
	}
}
