package com.nexia.viacombattest.integration;


/**
 *  So, like, how do we do this? <br>
 *  Answer: we need to make
 *  ViaVersion work with the Fabric Networking API, <br> then we can integrate Combatify here
 */
public class CombatifyIntegration {
    private CombatifyIntegration() {}

    /**
     * Handle the net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket$InteractionType#AIR_SWING
     */
    public static void fakeHandleServerboundMiss() {

    }

    /**
     * Handle client information
     * @param useShieldOnCrouch client uses shield on crouch
     */
    public static void fakeHandleServerboundClientInformationExtension(boolean useShieldOnCrouch) {

    }
}
