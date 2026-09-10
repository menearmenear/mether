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

public class CrystalSpiderEntity extends HostileEntity {
    
    public CrystalSpiderEntity(EntityType<? extends CrystalSpiderEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 16.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.35)
            .add(EntityAttributes.ATTACK_DAMAGE, 4.0)
            .add(EntityAttributes.FOLLOW_RANGE, 20.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ClimbOntopOfCrystalSpiderGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }
    
    @Override
    public boolean canClimb() {
        return true;
    }
    
    @Override
    public int getLuminance() {
        return 4;
    }
    
    private static class ClimbOntopOfCrystalSpiderGoal extends Goal {
        private final CrystalSpiderEntity spider;
        
        public ClimbOntopOfCrystalSpiderGoal(CrystalSpiderEntity spider) {
            this.spider = spider;
        }
        
        @Override
        public boolean canStart() {
            return false;
        }
    }
    
    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(CrystalSpiderEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
