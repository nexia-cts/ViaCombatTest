package com.nexia.viacombattest.mixin;

import com.nexia.viacombattest.ViaFabricAddon;
import com.viaversion.viaversion.ViaManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ViaManagerImpl.class)
public class MixinViaVersionLateEnable {
    //https://github.com/ViaVersion/ViaVersion/pull/4038
    @Inject(method = "onServerLoaded", at = @At(value = "INVOKE", target = "Lcom/viaversion/viaversion/protocol/ProtocolManagerImpl;refreshVersions()V", shift = At.Shift.AFTER), remap = false)
    public void afterRefreshVersions(CallbackInfo ci) {
        ViaFabricAddon.lateEnable();
    }
}
