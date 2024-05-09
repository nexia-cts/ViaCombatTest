package com.nexia.viacombattest.protocols;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;

public class CombatTest8c extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2> {
    public CombatTest8c() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPackets1_16_2.CLIENT_SETTINGS, new PacketHandlers() {
            @Override
            public void register() {
                map(Type.STRING); //language
                map(Type.BYTE); //viewDistance
                map(Type.VAR_INT); //chatVisibility
                map(Type.BOOLEAN); //chatColors
                map(Type.UNSIGNED_BYTE); //playerModelBitMask
                map(Type.VAR_INT); //mainArm
                handler(wrapper -> {
                    wrapper.write(Type.BOOLEAN, false); //useShieldOnCrouch
                });
            }
        });
    }
}
