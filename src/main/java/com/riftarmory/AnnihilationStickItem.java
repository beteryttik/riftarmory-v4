package com.riftarmory;

import net.minecraft.entity.TntEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnnihilationStickItem extends Item {

    public AnnihilationStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, ServerPlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world instanceof ServerWorld server) {
            user.sendMessage(Text.literal("Сервер будет уничтожен! Тик-так..."), false);

            BlockPos anchor = BlockPos.ofFloored(user.getX(), 40, user.getZ());
            int radius = 8; // сетка 17x17 = 289 ТНТ

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    TntEntity tnt = new TntEntity(world,
                            anchor.getX() + dx + 0.5, anchor.getY() + 1, anchor.getZ() + dz + 0.5,
                            null);
                    tnt.setFuse(80 + (Math.abs(dx) + Math.abs(dz)));
                    world.spawnEntity(tnt);
                }
            }

            server.getPlayerManager().broadcast(
                    Text.literal("Сервер будет уничтожен через 4 секунды..."), false);

            stack.decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
