package com.nexia.viacombattest.platform;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;

public record VCTServerVersionProvider(VersionProvider old) implements VersionProvider {
    @Override
    public ProtocolVersion getClientProtocol(UserConnection connection) {
        final ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
        if (version.getVersionType() == VersionType.SPECIAL) {
            return ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion());
        } else {
            return old.getClientProtocol(connection);
        }
    }

    @Override
    public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
        return old.getClosestServerProtocol(connection);
    }

}