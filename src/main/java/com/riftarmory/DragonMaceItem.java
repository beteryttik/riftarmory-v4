package com.riftarmory;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;

public class DragonMaceItem extends Item {

    public DragonMaceItem(Settings settings) {
        super(settings);
    }

    // Поджиг цели живёт в DamageHandler.
    public static void playWhoosh() {
        SoundEvents.ENTITY_BLAZE_SHOOT.getClass();
    }
}
