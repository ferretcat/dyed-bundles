package archives.tater.bundlebackportish;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.BundleItem;
import net.minecraft.util.Identifier;

public class BundleBackportishClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BundleBackportishClientNetworking.register();

		ModelPredicateProviderRegistry.register(new Identifier(BundleBackportish.MOD_ID, "filled"), (stack, world, entity, seed) -> {
			var item = stack.getItem();
			if (!(item instanceof BundleItem)) return Float.NEGATIVE_INFINITY;
			return BundleItem.getAmountFilled(stack);
		});
	}

}
