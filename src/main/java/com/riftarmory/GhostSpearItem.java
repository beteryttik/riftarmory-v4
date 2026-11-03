package com.riftarmory;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class GhostSpearItem extends Item {

    public GhostSpearItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class,
                    Box.of(user.getPos(), 24.0, 24.0, 24.0),
                    e -> e != user && e.isAlive());

            if (targets.isEmpty()) {
                user.sendMessage(Text.literal("Живых целей рядом нет"), true);
                return ActionResult.FAIL;
            }

            if (PlayerState.tryUseStability(user, 15)) {
                LivingEntity closest = targets.get(0);
                double best = closest.squaredDistanceTo(user);
                for (LivingEntity t : targets) {
                    double d = t.squaredDistanceTo(user);
                    if (d < best) {
                        best = d;
                        closest = t;
                    }
                }

                closest.damage(user.getDamageSources().playerAttack(user), 12.0f);
                server.playSound(null, user.getBlockPos(),
                        SoundEvents.PARTICLE_SOUL_ESCAPE.value(),
                        SoundCategory.PLAYERS, 1.0f, 0.8f);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
