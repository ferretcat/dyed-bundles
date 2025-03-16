package archives.tater.bundlebackportish;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.BundleItem;

public class BundleBackportishClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BundleBackportishClientNetworking.register();

		ModelPredicateProviderRegistry.register(BundleBackportish.id("filled"), (stack, world, entity, seed) -> {
			var item = stack.getItem();
			if (!(item instanceof BundleItem)) return Float.NEGATIVE_INFINITY;
			return BundleItem.getAmountFilled(stack);
		});

		TooltipComponentCallback.EVENT.register(tooltipData ->
				tooltipData instanceof SelectionBundleTooltipData selectionBundleTooltipData
						? new SelectionBundleTooltipComponent(selectionBundleTooltipData)
						: null
		);
	}

}
