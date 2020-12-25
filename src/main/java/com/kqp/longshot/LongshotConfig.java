package com.kqp.longshot;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = Longshot.MOD_ID)
public class LongshotConfig implements ConfigData {

    @Comment("Requires a restart.")
    public int longshotDurability = 1024;

    public int longshotLoadTime = 10;

    @Comment(
        "Angle at which the y-velocity calculation stops increasing.\n" +
        "This prevents players from launching themselves several blocks into the air.\n" +
        "Set to 90 for a fun time."
    )
    public double longshotMaxPitchAngle = 25;

    @Comment("Requires a restart.")
    public boolean enableSlingEnchantment = true;

    public int slingEnchantmentMaxLevel = 5;

    public boolean slingEnchantmentIsTreasure = false;

    @Comment("Requires a restart.")
    public boolean enableAirLoadingEnchantment = true;

    public boolean airLoadingEnchantmentIsTreasure = true;

    public static void init() {
        AutoConfig.register(LongshotConfig.class, JanksonConfigSerializer::new);
    }

    public static LongshotConfig get() {
        return AutoConfig.getConfigHolder(LongshotConfig.class).getConfig();
    }
}
