package com.menear.mether.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.targeting.TargetingConditions;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CrystalGolemEntity extends HostileEntity {
    
    public CrystalGolemEntity(EntityType<? extends CrystalGolemEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 40.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.28)
            .add(EntityAttributes.ATTACK_DAMAGE, 8.0)
            .add(EntityAttributes.FOLLOW_RANGE, 24.0)
            .add(EntityAttributes.ARMOR, 6.0)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.5);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }
    
    @Override
    public int getLuminance() {
        return 6;
    }
    
    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(CrystalGolemEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
        
        @Override
        public boolean canStart() {
            return super.canStart();
        }
    }
}
