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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfernalCrystalTitanEntity extends HostileEntity {

    private final ServerBossBar bossBar = new ServerBossBar(
        this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS
    );

    private int fireTimer = 0;

    public InfernalCrystalTitanEntity(EntityType<? extends InfernalCrystalTitanEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 350.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
            .add(EntityAttributes.ATTACK_DAMAGE, 18.0)
            .add(EntityAttributes.FOLLOW_RANGE, 40.0)
            .add(EntityAttributes.ARMOR, 12.0)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.85);
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
            this.fireTimer++;
            if (this.fireTimer >= 60) {
                this.fireTimer = 0;
                createFire();
            }
        }
    }

    private void createFire() {
        BlockPos origin = this.getBlockPos();
        for (BlockPos pos : BlockPos.iterate(origin.add(-2, 0, -2), origin.add(2, 0, 2))) {
            if (this.getWorld().isAir(pos) && !pos.equals(origin)) {
                this.getWorld().setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
            }
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
        return 14;
    }

    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(InfernalCrystalTitanEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
