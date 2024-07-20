package com.nexia.viacombattest;

import com.nexia.viacombattest.platform.ViaCombatTestPlatform;
import com.viaversion.viaversion.api.Via;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ViaFabricAddon implements ViaCombatTestPlatform, Runnable {
    private final Logger logger = LogManager.getLogManager().getLogger("ViaCombatTest");
    private File configDir;

    @Override
    public void run() {
        Path configDirPath = Via.getPlatform().getDataFolder().toPath().resolve("ViaCombatTest");
        configDir = configDirPath.toFile();
        this.init(new File(getDataFolder(), "config.yml"));
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public File getDataFolder() {
        return this.configDir;
    }
}


