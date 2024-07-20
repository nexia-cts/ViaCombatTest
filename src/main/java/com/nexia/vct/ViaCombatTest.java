package com.nexia.vct;

import com.nexia.vct.impl.VLLoaderImpl;
import com.nexia.vct.impl.ViaPlatformImpl;
import com.nexia.vct.protocol.HostnameParserProtocol;
import com.nexia.vct.util.ClassLoaderPriorityUtil;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.raphimc.vialoader.impl.platform.ViaAprilFoolsPlatformImpl;
import net.raphimc.vialoader.impl.platform.ViaBackwardsPlatformImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class ViaCombatTest implements ModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("ViaCombatTest");

    @Override
    public void onInitialize() {
        Path configDirectory = FabricLoader.getInstance().getConfigDir();

        var platform = new ViaPlatformImpl(configDirectory.resolve("ViaVersion"));

        Via.init(
                ViaManagerImpl.builder()
                        .platform(platform)
                        .injector(platform.getInjector())
                        .loader(new VLLoaderImpl())
                        .build());

        platform.init();

        Via.getManager()
                .addEnableListener(
                        () -> {
                            new ViaBackwardsPlatformImpl();
                            new ViaAprilFoolsPlatformImpl();
                        });

        var manager = (ViaManagerImpl) Via.getManager();
        manager.init();

        HostnameParserProtocol.INSTANCE.initialize();
        HostnameParserProtocol.INSTANCE.register(Via.getManager().getProviders());
        ProtocolVersion.register(-2, "AUTO");

        manager.getPlatform().getConf().setCheckForUpdates(false);

        manager.onServerLoaded();

        ClassLoaderPriorityUtil.loadOverridingJars(FabricLoader.getInstance().getConfigDir().toFile());
    }
}
