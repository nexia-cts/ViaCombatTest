package com.nexia.viacombattest.mixin;

import com.nexia.viacombattest.mixin_interfaces.Is8CMissPacket;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinAirSwingFix implements Is8CMissPacket {
    @Unique
    public boolean isMiss = false;

    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readEnumConstant(Ljava/lang/Class;)Ljava/lang/Enum;"))
    public Enum<?> initHook(PacketByteBuf instance, Class<Enum<?>> enumClass) {
        int readVar = instance.readVarInt();

        if(readVar > 2) {
            System.out.println("FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU");
            //new Exception().printStackTrace();
            this.isMiss = true; //cancel packet
            return enumClass.getEnumConstants()[0];
        }

        return enumClass.getEnumConstants()[readVar];
    }

    @Unique
    @Override
    public boolean viaCombatTest$isMissPacket() {
        return isMiss;
    }
}
