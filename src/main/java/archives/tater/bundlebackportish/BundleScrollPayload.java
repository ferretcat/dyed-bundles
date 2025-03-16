package archives.tater.bundlebackportish;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record BundleScrollPayload(
        int syncId,
        int revision,
        int i,
        int amt
) implements CustomPayload {
    public static Id<BundleScrollPayload> ID = new Id<>(BundleBackportish.id("bundle_scroll"));
    public static PacketCodec<RegistryByteBuf, BundleScrollPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, BundleScrollPayload::syncId,
            PacketCodecs.INTEGER, BundleScrollPayload::revision,
            PacketCodecs.INTEGER, BundleScrollPayload::i,
            PacketCodecs.INTEGER, BundleScrollPayload::amt,
            BundleScrollPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
