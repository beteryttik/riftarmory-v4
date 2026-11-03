package com.riftarmory;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RIFT_ROD = register("rift_rod",
            new Item(new Item.Settings()));
    public static final Item GHOST_GLASS = register("ghost_glass",
            new Item(new Item.Settings()));
    public static final Item DRAGON_HEART = register("dragon_heart",
            new Item(new Item.Settings()));

    public static final Item RIFT_MACE = register("rift_mace",
            new RiftMaceItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 9, -2.4f).maxCount(1)));
    public static final Item GHOST_MACE = register("ghost_mace",
            new GhostMaceItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 8, -2.4f).maxCount(1)));
    public static final Item DRAGON_MACE = register("dragon_mace",
            new DragonMaceItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 9, -2.4f).maxCount(1)));
    public static final Item LIGHTNING_MACE = register("lightning_mace",
            new LightningMaceItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 8, -2.4f).maxCount(1)));

    public static final Item RIFT_SPEAR = register("rift_spear",
            new RiftSpearItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 7, -2.8f).maxCount(1)));
    public static final Item GHOST_SPEAR = register("ghost_spear",
            new GhostSpearItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 7, -2.8f).maxCount(1)));
    public static final Item DRAGON_SPEAR = register("dragon_spear",
            new DragonSpearItem(new Item.Settings().sword(ToolMaterial.DIAMOND, 7, -2.8f).maxCount(1)));

    public static final Item ANNIHILATION_STICK = register("annihilation_stick",
            new AnnihilationStickItem(new Item.Settings().maxCount(1)));

    public static final Item RIFT_CHEST = register("rift_chest",
            new RiftChestItem(new Item.Settings().maxCount(1)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Riftarmory.MOD_ID, name), item);
    }
}
