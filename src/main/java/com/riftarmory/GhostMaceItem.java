package com.riftarmory;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GhostMaceItem extends Item {

    public GhostMaceItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            if (PlayerState.tryUseStability(user, 25)) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 200, 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
                server.playSound(null, user.getBlockPos(),
                        SoundEvents.PARTICLE_SOUL_ESCAPE.value(),
                        SoundCategory.PLAYERS, 1.0f, 1.2f);
                user.sendMessage(net.minecraft.text.Text.literal("Фаза неосязаемости: 10 секунд"), true);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
