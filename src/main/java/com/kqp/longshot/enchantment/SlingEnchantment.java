package com.kqp.longshot.enchantment;

import com.kqp.cakelib.enchantment.TargetedEnchantment;
import com.kqp.longshot.LongshotConfig;
import com.kqp.longshot.item.LongshotItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;

public class SlingEnchantment extends TargetedEnchantment {

    public SlingEnchantment() {
        super(
            Enchantment.Rarity.UNCOMMON,
            item -> item instanceof LongshotItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public boolean isTreasure() {
        return LongshotConfig.get().slingIsTreasure;
    }

    @Override
    public int getMaxLevel() {
        return LongshotConfig.get().slingMaxLevel;
    }

    @Override
    public ItemGroup getItemGroup() {
        return ItemGroup.TOOLS;
    }
}
