package com.riftarmory;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final RegistryKey<ItemGroup> RIFTARMORY_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            Identifier.of(Riftarmory.MOD_ID, "riftarmory"));

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, RIFTARMORY_KEY,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModItems.RIFT_MACE))
                        .displayName(Text.translatable("itemgroup.riftarmory"))
                        .build());

        ItemGroupEvents.modifyEntriesEvent(RIFTARMORY_KEY).register(group -> {
            group.add(ModItems.RIFT_ROD);
            group.add(ModItems.GHOST_GLASS);
            group.add(ModItems.DRAGON_HEART);
            group.add(ModItems.RIFT_MACE);
            group.add(ModItems.GHOST_MACE);
            group.add(ModItems.DRAGON_MACE);
            group.add(ModItems.LIGHTNING_MACE);
            group.add(ModItems.RIFT_SPEAR);
            group.add(ModItems.GHOST_SPEAR);
            group.add(ModItems.DRAGON_SPEAR);
            group.add(ModItems.ANNIHILATION_STICK);
            group.add(ModItems.RIFT_CHEST);
        });
    }
}
