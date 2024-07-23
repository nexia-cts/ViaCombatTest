package com.nexia.viacombattest.platform;

import com.nexia.viacombattest.ViaCombatTest;
import com.nexia.viacombattest.ViaCombatTestConfig;
import com.nexia.viacombattest.protocols.CombatTest8c;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import java.io.File;
import java.util.logging.Logger;

/**
 * Via Combat Test main platform
 */
public interface ViaCombatTestPlatform {

    /**
     * Initializes protocols
     * @param dataFolder config folder
     */
    default void init(final File dataFolder) {
        final ViaCombatTestConfig config = new ViaCombatTestConfig(dataFolder, getLogger());
        config.reload();
        ViaCombatTest.init(this, config);
        Via.getManager().getConfigurationProvider().register(config);
        Via.getManager().getSubPlatforms().add("ViaCombatTest-" + ViaCombatTest.VERSION);

        final ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol(new CombatTest8c(), CombatTest8c.instance, ProtocolVersion.v1_16_2);
    }

    /**
     * Logger
     * @return logger for the platform
     */
    Logger getLogger();

    /**
     * Config
     * @return platform's config folder
     */
    File getDataFolder();

}
