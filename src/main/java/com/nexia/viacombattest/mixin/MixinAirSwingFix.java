package com.nexia.viacombattest.mixin;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinAirSwingFix {
    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readEnumConstant(Ljava/lang/Class;)Ljava/lang/Enum;"))
    public Enum<?> initHook(PacketByteBuf instance, Class<Enum<?>> enumClass) {
        int readVar = instance.readVarInt();

        if(readVar > 2) {
            instance.writeVarInt(0);
            return enumClass.getEnumConstants()[0];
        }

        return enumClass.getEnumConstants()[readVar];
    }
}
