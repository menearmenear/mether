package com.menear.mether.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.targeting.TargetingConditions;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class PrismaticOverlordEntity extends HostileEntity {

    private final ServerBossBar bossBar = new ServerBossBar(
        this.getDisplayName(), BossBar.Color.WHITE, BossBar.Style.PROGRESS
    );

    public PrismaticOverlordEntity(EntityType<? extends PrismaticOverlordEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 500.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
            .add(EntityAttributes.ATTACK_DAMAGE, 22.0)
            .add(EntityAttributes.FOLLOW_RANGE, 40.0)
            .add(EntityAttributes.ARMOR, 16.0)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.95);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 12.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void onStartTrackingBy(ServerPlayerEntity player) {
        super.onStartTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public int getLuminance() {
        return 15;
    }

    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(PrismaticOverlordEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
