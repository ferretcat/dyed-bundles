package archives.tater.bundlescroll;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleScrollBackport implements ModInitializer {
	public static final String MOD_ID = "bundle-scroll-backport";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier SCROLL_PACKET_ID = new Identifier(MOD_ID, "scrollbundle");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerPlayNetworking.registerGlobalReceiver(SCROLL_PACKET_ID, (server, player, handler, buf, responseSender) -> {
			int syncId = buf.readInt();
			int revision = buf.readInt();
			int i = buf.readInt();
			int amt = buf.readInt();

			server.execute( () -> {
				player.updateLastActionTime();
				ScreenHandler screenHandler = player.currentScreenHandler;

				if (screenHandler.syncId != syncId) {
					return;
				}
				if (player.isSpectator()) {
					screenHandler.syncState();
					return;
				}
				if (!screenHandler.canUse(player)) {
					LOGGER.debug("Player {} interacted with invalid menu {}", player, screenHandler);
					return;
				}
				if (!screenHandler.isValid(i)) {
					LOGGER.debug("Player {} clicked invalid slot index: {}, available slots: {}", player.getName(), i, screenHandler.slots.size());
					return;
				}
				boolean flag = revision == player.currentScreenHandler.getRevision();
				screenHandler.disableSyncing();
				Slot slot = screenHandler.getSlot(i);
				ItemStack stack = slot.getStack();
				if (stack.getItem() instanceof BundleItem) {
					BundleSelection.add(stack, amt);
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
