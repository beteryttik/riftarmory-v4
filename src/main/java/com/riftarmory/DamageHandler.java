package com.riftarmory;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DamageHandler {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((target, source, amount) -> {
            if (!(source.getAttacker() instanceof ServerPlayerEntity player)) {
                return true;
            }
            if (!(player.getEntityWorld() instanceof ServerWorld world)) {
                return true;
            }

            ItemStack stack = player.getMainHandStack();

            if (stack.getItem() instanceof RiftMaceItem) {
                world.createExplosion(player, target.getX(), target.getY(), target.getZ(),
                        3.0f, World.ExplosionSourceType.TNT);
            } else if (stack.getItem() instanceof DragonMaceItem) {
                target.setOnFireFor(8.0f);
            } else if (stack.getItem() instanceof LightningMaceItem) {
                chainLightning(world, player, target);
            }

            return true;
        });
    }

    private static void chainLightning(ServerWorld world, ServerPlayerEntity player, LivingEntity target) {
        world.spawnEntity(createBolt(world, target.getX(), target.getY(), target.getZ()));

        List<LivingEntity> nearby = world.getEntitiesByClass(LivingEntity.class,
                Box.of(target.getPos(), 8.0, 8.0, 8.0),
                e -> e != target && e != player && e.isAlive());

        int chained = 0;
        for (LivingEntity victim : nearby) {
            if (chained >= 3) break;
            world.spawnEntity(createBolt(world, victim.getX(), victim.getY(), victim.getZ()));
            chained++;
        }

        if (world.isRaining() && world.isRainingAt(player.getBlockPos())) {
            player.damage(world.getDamageSources().lightningBolt(), 4.0f);
        }
    }

    private static LightningEntity createBolt(ServerWorld world, double x, double y, double z) {
        LightningEntity bolt = new LightningEntity(world.getType(), world);
        bolt.refreshPositionAfterTeleport(x, y, z);
        return bolt;
    }
}
