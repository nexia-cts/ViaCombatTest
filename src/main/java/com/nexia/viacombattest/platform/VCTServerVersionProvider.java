package com.nexia.viacombattest.platform;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;

/**
 * Custom version provider for 8c
 * @param old Old provider
 */
public record VCTServerVersionProvider(VersionProvider old) implements VersionProvider {
    /**
     * Get the correct protocol for the client
     *
     * @param connection connection
     * @return protocol used for connection
     */
    @Override
    public ProtocolVersion getClientProtocol(UserConnection connection) {
        final ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
        if (version.getVersionType() == VersionType.SPECIAL) {
            return ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion());
        } else {
            return old.getClientProtocol(connection);
        }
    }

    /**
     * Returns the closest server protocol for connection
     *
     * @param connection connection
     * @return old version provider's closest protocol
     * @throws Exception old ProtocolVersion may throw exceptions
     */
    @Override
    public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
        return old.getClosestServerProtocol(connection);
    }

}