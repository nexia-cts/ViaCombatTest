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

@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinAirSwingFix {
    @Unique
    boolean isCancelled = false;
    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readEnumConstant(Ljava/lang/Class;)Ljava/lang/Enum;"))
    public Enum<?> initHook(PacketByteBuf instance, Class<Enum<?>> enumClass) {
        int readVar = instance.readVarInt();

        if(readVar > 2) {
            if (FabricLoader.getInstance().isModLoaded("combatify")) {
                // Convert to ServerboundMissPacket, handle it, and cancel this
                CombatifyIntegration.fakeHandleServerboundMiss();
                isCancelled = true;
            }
            instance.writeVarInt(0);
            return enumClass.getEnumConstants()[0];
        }

        return enumClass.getEnumConstants()[readVar];
    }

    @Inject(method = "apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V", at = @At("HEAD"), cancellable = true)
    public void setCancelled(ServerPlayPacketListener serverPlayPacketListener, CallbackInfo ci) {
        if (isCancelled) ci.cancel();
    }
}
