package com.kqp.longshot.item;

import com.kqp.cakelib.util.EnchantmentUtil;
import com.kqp.longshot.Longshot;
import com.kqp.longshot.LongshotConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LongshotItem extends Item {

    public LongshotItem() {
        super(
            new Settings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
                .maxDamage(LongshotConfig.get().longshotDurability)
        );
    }

    @Override
    public void usageTick(
        World world,
        LivingEntity user,
        ItemStack stack,
        int remainingUseTicks
    ) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;

            int i = this.getMaxUseTime(stack) - remainingUseTicks;

            if (i == LongshotConfig.get().longshotLoadTime) {
                world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ITEM_CROSSBOW_LOADING_END,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
                );
            }
        }
    }

    @Override
    public void onStoppedUsing(
        ItemStack stack,
        World world,
        LivingEntity user,
        int remainingUseTicks
    ) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            boolean hasAirLoading =
                EnchantmentUtil.getLevel(Longshot.AIR_LOADING, stack) > 0;

            if (player.isOnGround() || hasAirLoading) {
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(i);

                if ((double) pullProgress >= 0.1D) {
                    float yaw = player.yaw;
                    float pitch = player.pitch;

                    double maxVelY = calcSlingVelocity(
                        pullProgress,
                        yaw,
                        (float) Math.max(
                            player.pitch,
                            -LongshotConfig.get().longshotMaxPitchAngle
                        ),
                        EnchantmentUtil.getLevel(Longshot.SLING, stack)
                    )
                        .y;
                    Vec3d normalVel = calcSlingVelocity(
                        pullProgress,
                        yaw,
                        pitch,
                        EnchantmentUtil.getLevel(Longshot.SLING, stack)
                    );
                    double velY =
                        Math.signum(maxVelY) *
                        Math.min(Math.abs(maxVelY), Math.abs(normalVel.y));
                    Vec3d finalVel = new Vec3d(normalVel.x, velY, normalVel.z);

                    player.setVelocity(finalVel);

                    world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.PLAYERS,
                        1.0F,
                        pullProgress * 0.025F
                    );

                    stack.damage(
                        1,
                        player,
                        p -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        }
                    );

                    if (hasAirLoading) {
                        player.fallDistance = 0.0F;
                    }
                }
            }
        }
    }

    public static float getPullProgress(int useTicks) {
        return (
            (float) Math.min(useTicks, LongshotConfig.get().longshotLoadTime) /
            LongshotConfig.get().longshotLoadTime
        );
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(
        World world,
        PlayerEntity user,
        Hand hand
    ) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        return TypedActionResult.consume(itemStack);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    private static Vec3d calcSlingVelocity(
        float pullProgress,
        float yaw,
        float pitch,
        int slingLevel
    ) {
        float speed = (float) (
            (pullProgress * 3.0D) *
            (
                LongshotConfig.get().longshotBaseSpeed +
                LongshotConfig.get().slingSpeedMultiplier *
                // Avoid dividing by 0
                (
                    slingLevel /
                    (double) Math.max(
                        1,
                        EnchantmentUtil.getMaxLevel(Longshot.SLING)
                    )
                )
            )
        );

        return new Vec3d(
            -MathHelper.sin(yaw * 0.017453292F) *
            MathHelper.cos(pitch * 0.017453292F),
            -MathHelper.sin(pitch * 0.017453292F),
            MathHelper.cos(yaw * 0.017453292F) *
            MathHelper.cos(pitch * 0.017453292F)
        )
            .normalize()
            .multiply(speed);
    }
}
