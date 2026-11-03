package com.riftarmory;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerState {
    private static final Map<UUID, Integer> STABILITY = new HashMap<>();
    private static final Map<UUID, Integer> REGEN_COUNTER = new HashMap<>();

    public static int getStability(ServerPlayerEntity player) {
        return STABILITY.getOrDefault(player.getUuid(), 100);
    }

    public static boolean tryUseStability(ServerPlayerEntity player, int cost) {
        int current = getStability(player);
        if (current < cost) {
            player.sendMessage(Text.literal("Стержень разлома не готов! (" + current + "/100)"), true);
            return false;
        }
        STABILITY.put(player.getUuid(), current - cost);
        return true;
    }

    public static void tick(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int counter = REGEN_COUNTER.getOrDefault(id, 0) + 1;
        if (counter >= 10) {
            counter = 0;
            int current = STABILITY.getOrDefault(id, 100);
            if (current < 100) {
                STABILITY.put(id, current + 1);
            }
        }
        REGEN_COUNTER.put(id, counter);
    }
}
