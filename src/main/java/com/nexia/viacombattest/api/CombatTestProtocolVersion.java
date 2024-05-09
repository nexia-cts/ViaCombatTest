package com.nexia.viacombattest.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import java.util.ArrayList;
import java.util.List;

public class CombatTestProtocolVersion {
    public static final List<ProtocolVersion> PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> COMBAT_TEST_PROTOCOLS = new ArrayList<>();
    public static final ProtocolVersion CombatTest8c = registerCombatTest(803, "8c");

    public static final ProtocolVersion CombatTest7c = registerCombatTest(802, "7c");


    private static ProtocolVersion registerCombatTest(final int version, final String name) {
        final ProtocolVersion protocolVersion = ProtocolVersion.register(version, "Combat Test " + name);
        PROTOCOLS.add(protocolVersion);
        COMBAT_TEST_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }
}
