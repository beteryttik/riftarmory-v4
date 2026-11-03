package com.riftarmory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RiftSpearItem extends Item {

    public RiftSpearItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            if (PlayerState.tryUseStability(user, 20)) {
                Vec3d eye = user.getEyePos();
                Vec3d dir = user.getRotationVector().normalize().multiply(64.0);
                Vec3d point = eye.add(dir);

                world.createExplosion(user, point.x, point.y, point.z,
                        3.0f, World.ExplosionSourceType.TNT);
                server.playSound(null, user.getBlockPos(),
                        SoundEvents.ENTITY_GENERIC_EXPLODE.value(),
                        SoundCategory.PLAYERS, 1.0f, 0.9f);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
