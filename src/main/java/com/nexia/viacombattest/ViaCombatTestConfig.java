package com.nexia.viacombattest;

import com.viaversion.viaversion.util.Config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaCombatTestConfig extends Config implements com.nexia.viacombattest.platform.ViaCombatTestConfig {
    /**
     * Create a new Config instance, this will *not* load the config by default.
     * To load config see {@link #reload()}
     *
     * @param configFile The location of where the config is loaded/saved.
     * @param logger     The logger to use.
     */
    public ViaCombatTestConfig(File configFile, Logger logger) {
        super(configFile, logger);
    }

    @Override
    public void reload() {
        super.reload();
        this.loadFields();
    }

    private void loadFields() {}

    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viacombattest/viacombattest.yml");
    }

    @Override
    public InputStream getDefaultConfigInputStream() {
        return getClass().getClassLoader().getResourceAsStream("assets/viacombattest/viacombattest.yml");
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {}

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
