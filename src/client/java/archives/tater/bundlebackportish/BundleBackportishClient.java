package archives.tater.bundlebackportish;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.BundleItem;

public class BundleBackportishClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(BundleBackportish.id("filled"), (stack, world, entity, seed) -> {
            var item = stack.getItem();
            if (!(item instanceof BundleItem))
                return Float.NEGATIVE_INFINITY;
            return BundleItem.getAmountFilled(stack);
        });
    }

}
