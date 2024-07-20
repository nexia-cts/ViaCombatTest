package com.nexia.vct.impl;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import com.viaversion.viaversion.libs.gson.JsonObject;

import java.util.SortedSet;

public class ViaInjectorImpl implements ViaInjector {
    @Override
    public void inject() {}

    @Override
    public void uninject() {}

    @Override
    public ProtocolVersion getServerProtocolVersion() {
        return getServerProtocolVersions().first();
    }

    @Override
    public SortedSet<ProtocolVersion> getServerProtocolVersions() {
        final SortedSet<ProtocolVersion> versions = new ObjectLinkedOpenHashSet<>();
        versions.addAll(ProtocolVersion.getProtocols());
        return versions;
    }

    @Override
    public String getEncoderName() {
        return getDecoderName();
    }

    @Override
    public String getDecoderName() {
        return "via-codec";
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}
