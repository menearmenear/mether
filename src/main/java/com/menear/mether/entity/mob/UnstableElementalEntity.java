package com.menear.mether.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class UnstableElementalEntity extends HostileEntity {
    
    public UnstableElementalEntity(EntityType<? extends UnstableElementalEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 12.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
            .add(EntityAttributes.ATTACK_DAMAGE, 10.0)
            .add(EntityAttributes.FOLLOW_RANGE, 16.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }
    
    
    @Override
    public void onDeath(net.minecraft.entity.damage.DamageSource source) {
        if (!this.getEntityWorld().isClient()) {
            this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0f, World.ExplosionSourceType.MOB);
        }
        super.onDeath(source);
    }
    
    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(UnstableElementalEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
