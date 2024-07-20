package com.nexia.viacombattest.platform;

import com.nexia.viacombattest.ViaCombatTest;
import com.nexia.viacombattest.api.CombatTestProtocolVersion;
import com.nexia.viacombattest.protocols.CombatTest8c;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_16;

import com.google.common.collect.Range;

import java.io.File;
import java.util.logging.Logger;

public interface ViaCombatTestPlatform {

    default void init(final File dataFolder) {
        ViaCombatTest.init(this);
        Via.getManager().getSubPlatforms().add("ViaCombatTest-" + ViaCombatTest.VERSION);

        final ProtocolManager protocolManager = Via.getManager().getProtocolManager();

        protocolManager.registerProtocol(new CombatTest8c(), CombatTestProtocolVersion.CombatTest8c, ProtocolVersion.v1_16_2);

        //protocolManager.registerBaseProtocol(new BaseProtocol1_16(), Range.singleton(ProtocolVersion.getProtocol(CombatTestProtocolVersion.CombatTest8c.getVersion())));
    }

    void run();

    Logger getLogger();

    File getDataFolder();

}
