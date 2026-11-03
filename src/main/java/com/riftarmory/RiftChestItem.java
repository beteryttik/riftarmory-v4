package com.riftarmory;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RiftChestItem extends Item {

    private static final ItemStack[] LOOT = {
            new ItemStack(Items.DIAMOND, 5),
            new ItemStack(Items.ENDER_PEARL, 4),
            new ItemStack(Items.NETHERITE_SCRAP, 3),
            new ItemStack(Items.GOLD_INGOT, 16),
            new ItemStack(Items.EXPERIENCE_BOTTLE, 10)
    };

    public RiftChestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            BlockPos pos = user.getBlockPos().offset(user.getHorizontalFacing());

            if (!world.getBlockState(pos).isAir()) {
                user.sendMessage(net.minecraft.text.Text.literal("Нет свободного места перед игроком"), true);
                return ActionResult.FAIL;
            }

            world.setBlockState(pos, Blocks.CHEST.getDefaultState());
            if (world.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                for (int i = 0; i < LOOT.length && i < chest.size(); i++) {
                    chest.setStack(i, LOOT[i].copy());
                }
            }

            server.playSound(null, pos,
                    SoundEvents.BLOCK_CHEST_OPEN,
                    SoundCategory.PLAYERS, 1.0f, 0.8f);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
