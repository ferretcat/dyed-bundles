package archives.tater.bundlebackportish;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 * Based on <a href="https://github.com/zacharybarbanell/bundle-scroll">Bundle Tweaks</a> by
 * <a href="https://github.com/zacharybarbanell">zacharybarnabell</a> (MIT License)
 */
public class BundleBackportishNetworking {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(BundleScrollPayload.ID, BundleScrollPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(BundleScrollPayload.ID, (payload, context) -> {

            context.server().execute( () -> {
                var player = context.player();
                var i = payload.i();

                player.updateLastActionTime();
                ScreenHandler screenHandler = player.currentScreenHandler;

                if (screenHandler.syncId != payload.syncId()) {
                    return;
                }
                if (player.isSpectator()) {
                    screenHandler.syncState();
                    return;
                }
                if (!screenHandler.canUse(player)) {
                    BundleBackportish.LOGGER.debug("Player {} interacted with invalid menu {}", player, screenHandler);
                    return;
                }
                if (!screenHandler.isValid(i)) {
                    BundleBackportish.LOGGER.debug("Player {} clicked invalid slot index: {}, available slots: {}", player.getName(), i, screenHandler.slots.size());
                    return;
                }
                boolean flag = payload.revision() == player.currentScreenHandler.getRevision();
                screenHandler.disableSyncing();
                Slot slot = screenHandler.getSlot(i);
                ItemStack stack = slot.getStack();
                if (stack.getItem() instanceof BundleItem) {
                    BundleSelection.add(stack, payload.amt());
                }
                screenHandler.enableSyncing();
                if (flag) {
                    screenHandler.updateToClient();
                } else {
                    screenHandler.sendContentUpdates();
                }
            });
        });
    }
}
