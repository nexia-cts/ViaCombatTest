package com.nexia.viacombattest.protocols;

import com.nexia.viacombattest.integration.CombatifyIntegration;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import net.fabricmc.loader.api.FabricLoader;

public class CombatTest8c extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2> {
    public static final ProtocolVersion instance = new ProtocolVersion(VersionType.SPECIAL, 803, -1, "Combat Test 8c", null);
    static {
        ProtocolVersion.register(instance);
    }

    public CombatTest8c() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPackets1_16_2.CLIENT_INFORMATION, new PacketHandlers() {
            @Override
            protected void register() {
                map(Types.STRING); //language
                map(Types.BYTE); //viewDistance
                map(Types.VAR_INT); //chatVisibility
                map(Types.BOOLEAN); //chatColors
                map(Types.UNSIGNED_BYTE); //playerModelBitMask
                map(Types.VAR_INT); //mainArm
                handler(wrapper -> {
                    boolean useShieldOnCrouch = wrapper.read(Types.BOOLEAN);
                    if (FabricLoader.getInstance().isModLoaded("combatify")) {
                        // Convert to ServerboundClientInformationExtensionPacket and handle it
                        CombatifyIntegration.fakeHandleServerboundClientInformationExtension(useShieldOnCrouch);
                    }
                }); // useShieldOnCrouch
            }
        });
    }
}
