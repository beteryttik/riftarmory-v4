package com.riftarmory;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonSpearItem extends Item {

    public DragonSpearItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            Vec3d look = user.getRotationVector().normalize().multiply(12.0);
            Vec3d dest = user.getPos().add(look);
            BlockPos blockPos = BlockPos.ofFloored(dest.x, dest.y + 1.0, dest.z);

            if (blockPos.getY() >= world.getTopYInclusive()) {
                user.sendMessage(Text.literal("Слишком высоко — рывок невозможен"), true);
                return ActionResult.FAIL;
            }

            BlockState state = world.getBlockState(blockPos);
            if (state.isLiquid()) {
                user.sendMessage(Text.literal("Впереди жидкость — рывок невозможен"), true);
                return ActionResult.FAIL;
            }
            if (!state.isAir()) {
                user.sendMessage(Text.literal("Впереди стена — рывок невозможен"), true);
                return ActionResult.FAIL;
            }

            if (PlayerState.tryUseStability(user, 10)) {
                user.setPosition(dest.x, dest.y, dest.z);
                server.playSound(null, user.getBlockPos(),
                        SoundEvents.ENTITY_BLAZE_SHOOT,
                        SoundCategory.PLAYERS, 1.0f, 1.0f);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
