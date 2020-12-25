package com.kqp.longshot;

import com.kqp.longshot.enchantment.AirLoadingEnchantment;
import com.kqp.longshot.enchantment.SlingEnchantment;
import com.kqp.longshot.item.LongshotItem;
import java.util.Optional;
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

    public static Optional<Enchantment> SLING = Optional.empty();

    public static Optional<Enchantment> AIR_LOADING = Optional.empty();

    @Override
    public void onInitialize() {
        LongshotConfig.init();

        if (LongshotConfig.get().enableSlingEnchantment) {
            SLING =
                Optional.of(
                    Registry.register(
                        Registry.ENCHANTMENT,
                        id("sling"),
                        new SlingEnchantment()
                    )
                );
        }

        if (LongshotConfig.get().enableAirLoadingEnchantment) {
            AIR_LOADING =
                Optional.of(
                    Registry.register(
                        Registry.ENCHANTMENT,
                        id("air_loading"),
                        new AirLoadingEnchantment()
                    )
                );
        }
    }

    private static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
