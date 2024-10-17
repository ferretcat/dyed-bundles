package archives.tater.bundlescroll;

import archives.tater.bundlescroll.mixin.client.HandledScreenInvoker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class BundleScrollBackportClient implements ClientModInitializer {
	private double accScroll = 0;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof HandledScreen<?> handledScreen) {
				ScreenMouseEvents.afterMouseScroll(screen).register((_screen, x, y, horiz, vert) ->
						this.onMouseScrolled(handledScreen, x, y, vert));
			}
		});
	}

	private boolean onMouseScrolled(HandledScreen<?> screen, double x, double y, double scroll) {
		Slot slot = ((HandledScreenInvoker) screen).invokeGetSlotAt(x, y);
		if (slot == null) {
			return true;
		}
		ItemStack stack = slot.getStack();
		if (!(stack.getItem() instanceof BundleItem)) {
			return true;
		}
		if (accScroll * scroll < 0) {
			accScroll = 0;
		}
		accScroll += scroll;
		int amt = (int) accScroll;
		accScroll -= amt;
		if (amt == 0) {
			return true;
		}

		BundleSelection.add(stack, -amt);

		PacketByteBuf buf = PacketByteBufs.create();

		buf.writeInt(screen.getScreenHandler().syncId);
		buf.writeInt(screen.getScreenHandler().getRevision());
		buf.writeInt(slot.id);
		buf.writeInt(-amt);

		ClientPlayNetworking.send(BundleScrollBackport.SCROLL_PACKET_ID, buf);

		return false;
	}
}
