package com.nexia.viacombattest.mixin;

import com.nexia.viacombattest.integration.CombatifyIntegration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cancel AIR_SWING packet sent by Combat Test 8c
 * @see CombatifyIntegration#fakeHandleServerboundMiss()
 */
@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinAirSwingFix {
    private MixinAirSwingFix() { throw new IllegalStateException("Should not be initialized"); }

    /**
     * Cancel the packet if it is AIR_SWING
     */
    @Unique
    private boolean isCancelled = false;

    /**
     * Reads enum values
     * if the read enum value is bigger than 2 (which is 8c AIR_SWING)
     * write into the buffer so that we don't experience {@link io.netty.handler.codec.DecoderException}
     *
     * @param instance packet's byte buffer
     * @param enumClass the class @see
     * @return read enum value
     */
    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readEnumConstant(Ljava/lang/Class;)Ljava/lang/Enum;"))
    public Enum<?> initHook(PacketByteBuf instance, Class<Enum<?>> enumClass) {
        int readVar = instance.readVarInt();

        if(readVar > 2) {
            if (FabricLoader.getInstance().isModLoaded("combatify")) {
                // Convert to ServerboundMissPacket, handle it
                CombatifyIntegration.fakeHandleServerboundMiss();
            }
            isCancelled = true;
            instance.writeVarInt(0); //Add missing variable in packet (DecoderException)
            return enumClass.getEnumConstants()[0];
        }

        return enumClass.getEnumConstants()[readVar];
    }

    /**
     * Cancel the 8c AIR_SWING packet
     *
     * @param serverPlayPacketListener server's packet listener
     * @param ci used to cancel the event
     */
    @Inject(method = "apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V", at = @At("HEAD"), cancellable = true)
    public void setCancelled(ServerPlayPacketListener serverPlayPacketListener, CallbackInfo ci) {
        if (isCancelled) ci.cancel();
    }
}
