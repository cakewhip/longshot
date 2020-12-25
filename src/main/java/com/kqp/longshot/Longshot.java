package com.kqp.longshot;

import com.kqp.longshot.enchantment.AirLoadingEnchantment;
import com.kqp.longshot.enchantment.SlingEnchantment;
import com.kqp.longshot.item.LongshotItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Longshot implements ModInitializer {

    public static final String MOD_ID = "longshot";

    public static Item LONGSHOT = Registry.register(
        Registry.ITEM,
        id("longshot"),
        new LongshotItem()
    );

    public static Enchantment SLING = Registry.register(
        Registry.ENCHANTMENT,
        id("sling"),
        new SlingEnchantment()
    );

    public static Enchantment AIR_LOADING = Registry.register(
        Registry.ENCHANTMENT,
        id("air_loading"),
        new AirLoadingEnchantment()
    );

    @Override
    public void onInitialize() {}

    private static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
