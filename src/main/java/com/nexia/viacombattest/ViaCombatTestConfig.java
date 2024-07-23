package com.nexia.viacombattest;

import com.viaversion.viaversion.util.Config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Config implementation for ViaCombatTest
 */
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

    /**
     * Reloads the config
     */
    @Override
    public void reload() {
        super.reload();
        this.loadFields();
    }

    /**
     * Loads the non-existent fields in the config
     */
    private void loadFields() {}

    /**
     * URL
     * @return gets the default config's URL
     */
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viacombattest/viacombattest.yml");
    }

    /**
     * InputStream
     * @return gets the default config's InputStream
     */
    @Override
    public InputStream getDefaultConfigInputStream() {
        return getClass().getClassLoader().getResourceAsStream("assets/viacombattest/viacombattest.yml");
    }

    /**
     * Handles config
     * @param config config to be handled
     */
    @Override
    protected void handleConfig(Map<String, Object> config) {}

    /**
     * Yes
     * @return Unsupported options
     */
    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
