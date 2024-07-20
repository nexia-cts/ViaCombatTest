package com.nexia.vct.impl;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import com.viaversion.viaversion.libs.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import net.raphimc.vialoader.commands.UserCommandSender;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ViaPlatformImpl implements ViaPlatform<UserConnection> {
    private final Path dataFolder;
    private final JLoggerToSLF4J logger = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaVersion"));
    private final ViaAPI<UserConnection> api =
            new ViaAPIBase<>() {
                @Override
                public ProtocolVersion getPlayerProtocolVersion(final UserConnection player) {
                    return player.getProtocolInfo().protocolVersion();
                }

                @Override
                public void sendRawPacket(final UserConnection player, final ByteBuf packet) {
                    player.scheduleSendRawPacket(packet);
                }
            };

    private final ViaInjector injector = new ViaInjectorImpl();
    private ViaVersionConfig config;

    public ViaInjector getInjector() {
        return this.injector;
    }

    public ViaPlatformImpl(Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void init() {
        config = new AbstractViaConfig(dataFolder.resolve("config.yml").toFile(), logger) {
                    private static final List<String> UNSUPPORTED = List.of(
                                    "checkforupdates",
                                    "bungee-ping-interval",
                                    "bungee-ping-save",
                                    "bungee-servers",
                                    "velocity-ping-interval",
                                    "velocity-ping-save",
                                    "velocity-servers",
                                    "block-protocols",
                                    "block-disconnect-msg",
                                    "reload-disconnect-msg",
                                    "max-pps",
                                    "max-pps-kick-msg",
                                    "tracking-period",
                                    "tracking-warning-pps",
                                    "tracking-max-warnings",
                                    "tracking-max-kick-msg",
                                    "blockconnection-method",
                                    "quick-move-action-fix",
                                    "item-cache",
                                    "change-1_9-hitbox",
                                    "change-1_14-hitbox",
                                    "use-new-deathmessages",
                                    "nms-player-ticking"
                    );

                    {
                        this.reload();
                    }

                    @Override
                    protected void handleConfig(Map<String, Object> config) {}

                    @Override
                    public List<String> getUnsupportedOptions() {
                        return UNSUPPORTED;
                    }

                    @Override
                    public boolean isCheckForUpdates() {
                        return false;
                    }
                };
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getPlatformName() {
        return "ViaCombatTest";
    }

    @Override
    public String getPlatformVersion() {
        return "1.0";
    }

    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    @Override
    public ViaTaskImpl runAsync(Runnable runnable) {
        return new ViaTaskImpl(Via.getManager().getScheduler().execute(runnable));
    }

    @Override
    public ViaTaskImpl runRepeatingAsync(Runnable runnable, long period) {
        return new ViaTaskImpl(
                Via.getManager()
                        .getScheduler()
                        .scheduleRepeating(runnable, 0, period * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTaskImpl runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public ViaTaskImpl runSync(Runnable runnable, long delay) {
        return new ViaTaskImpl(
                Via.getManager().getScheduler().schedule(runnable, delay * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTaskImpl runRepeatingSync(Runnable runnable, long period) {
        return this.runRepeatingAsync(runnable, period);
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return Via.getManager().getConnectionManager().getConnectedClients().values().stream()
                .map(UserCommandSender::new)
                .toArray(ViaCommandSender[]::new);
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        if (uuid == null) {
            this.getLogger().info(message);
        } else {
            this.getLogger().info("[%s] %s".formatted(uuid, message));
        }
    }

    @Override
    public boolean kickPlayer(UUID uuid, String message) {
        return false;
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ViaAPI<UserConnection> getApi() {
        return api;
    }

    @Override
    public ViaVersionConfig getConf() {
        return config;
    }

    @Override
    public File getDataFolder() {
        return dataFolder.toFile();
    }

    @Override
    public void onReload() {}

    @Override
    public JsonObject getDump() {
        return injector.getDump();
    }

    @Override
    public boolean hasPlugin(String name) {
        return false;
    }
}
