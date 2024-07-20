package com.nexia.vct.mixin;

import com.nexia.vct.handler.DecodeHandler;
import com.nexia.vct.handler.EncodeHandler;
import com.nexia.vct.handler.PipelineReorderEvent;
import com.nexia.vct.protocol.HostnameParserProtocol;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import net.minecraft.network.BandwidthDebugMonitor;
import net.minecraft.network.Connection;
import net.minecraft.network.HandlerNames;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ConnectionMixin extends SimpleChannelInboundHandler<Packet<?>> {
    @Shadow private Channel channel;

    @Inject(method = "setupCompression", at = @At("RETURN"))
    private void reorderCompression(int compressionThreshold, boolean rejectBad, CallbackInfo ci) {
        channel.pipeline().fireUserEventTriggered(new PipelineReorderEvent());
    }

    @Inject(method = "configureSerialization", at = @At("RETURN"))
    private static void onConfigureSerialization(ChannelPipeline pipeline, PacketFlow packetFlow, boolean bl, BandwidthDebugMonitor bandwidthDebugMonitor, CallbackInfo ci) {
        Channel channel = pipeline.channel();
        if (channel instanceof SocketChannel) {
            final UserConnection user = new UserConnectionImpl(channel, packetFlow == PacketFlow.CLIENTBOUND);
            ProtocolPipelineImpl protocolPipeline = new ProtocolPipelineImpl(user);

            final boolean clientSide = user.isClientSide();
            if (clientSide) {
                protocolPipeline.add(HostnameParserProtocol.INSTANCE);
            }

            pipeline.addBefore(clientSide ? HandlerNames.ENCODER : HandlerNames.OUTBOUND_CONFIG, "via-encoder", new EncodeHandler(user));
            pipeline.addBefore(clientSide ? HandlerNames.INBOUND_CONFIG : HandlerNames.DECODER, "via-decoder", new DecodeHandler(user));
        }
    }
}
