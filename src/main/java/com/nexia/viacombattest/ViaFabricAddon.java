package com.nexia.viacombattest;

import com.nexia.viacombattest.platform.VCTServerVersionProvider;
import com.nexia.viacombattest.platform.ViaCombatTestPlatform;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * ViaFabric addon for ViaCombatTest
 */
public class ViaFabricAddon implements ViaCombatTestPlatform, Runnable {
    /**
     * Constructor for the addon
     */
    public ViaFabricAddon() {}
    
    private final Logger logger = LogManager.getLogManager().getLogger("ViaCombatTest");
    private File configDir;

    /**
     * Late enable implementation
     * @see com.nexia.viacombattest.mixin.MixinViaVersionLateEnable#afterRefreshVersions(CallbackInfo) 
     */
    public static void lateEnable() {
        final ViaManager manager = Via.getManager();
        final VersionProvider delegate = manager.getProviders().get(VersionProvider.class);
        manager.getProviders().use(VersionProvider.class, new VCTServerVersionProvider(delegate));
    }

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


