package com.menear.mether.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.targeting.TargetingConditions;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ShadowWraithEntity extends HostileEntity {
    
    public ShadowWraithEntity(EntityType<? extends ShadowWraithEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 20.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.FLYING_SPEED, 0.35)
            .add(EntityAttributes.ATTACK_DAMAGE, 6.0)
            .add(EntityAttributes.FOLLOW_RANGE, 32.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }
    
    @Override
    public boolean isFlying() {
        return true;
    }
    
    @Override
    public int getLuminance() {
        return 2;
    }
    
    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(ShadowWraithEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
