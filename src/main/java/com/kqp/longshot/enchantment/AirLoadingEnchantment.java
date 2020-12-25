package com.kqp.longshot.enchantment;

import com.kqp.cakelib.enchantment.TargetedEnchantment;
import com.kqp.strangery.item.LongshotItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class AirLoadingEnchantment extends TargetedEnchantment {

    public AirLoadingEnchantment() {
        super(
            Enchantment.Rarity.RARE,
            item -> item instanceof LongshotItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return level * 25;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
