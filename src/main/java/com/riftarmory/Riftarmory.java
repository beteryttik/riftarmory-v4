package com.riftarmory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class Riftarmory implements ModInitializer {
    public static final String MOD_ID = "riftarmory";

    @Override
    public void onInitialize() {
        ModItems.register();
        ModItemGroups.register();
        DamageHandler.register();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                PlayerState.tick(player);
            }
        });
    }
}
