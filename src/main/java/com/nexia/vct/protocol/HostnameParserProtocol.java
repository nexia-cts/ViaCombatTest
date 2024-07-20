package com.nexia.vct.protocol;

import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;

public class HostnameParserProtocol extends AbstractSimpleProtocol {
    public static final HostnameParserProtocol INSTANCE = new HostnameParserProtocol();

    @Override
    protected void registerPackets() {
        registerServerbound(State.HANDSHAKE, ServerboundHandshakePackets.CLIENT_INTENTION.getId(), ServerboundHandshakePackets.CLIENT_INTENTION.getId(), new PacketHandlers() {
            @Override
            protected void register() {
                map(Types.VAR_INT); // Protocol version
                map(Types.STRING, new ValueTransformer<>(Types.STRING) {
                    @Override
                    public String transform(PacketWrapper packetWrapper, String s) {
                        return new AddressParser().parse(s).serverAddress;
                    }
                });
            }
        });
    }

    @Override
    public boolean isBaseProtocol() {
        return true;
    }
}
