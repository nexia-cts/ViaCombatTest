package com.nexia.vct.impl;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocol.version.BaseVersionProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CompressionProvider;
import net.raphimc.vialoader.impl.viaversion.VLLoader;

public class VLLoaderImpl extends VLLoader {
    @Override
    public void load() {
        // ViaVersion
        Via.getManager().getProviders().use(VersionProvider.class, new BaseVersionProvider() {
            @Override
            public ProtocolVersion getClosestServerProtocol(UserConnection connection) {
                return connection.getProtocolInfo().protocolVersion();
            }
        });

        Via.getManager().getProviders().use(CompressionProvider.class, new CompressionProvider());
    }
}
