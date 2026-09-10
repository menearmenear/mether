package com.menear.mether.entity.mob;

import com.menear.mether.util.CrystalType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CrystalMageEntity extends HostileEntity implements RangedAttackMob {
    
    public CrystalMageEntity(EntityType<? extends CrystalMageEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 24.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.ATTACK_DAMAGE, 5.0)
            .add(EntityAttributes.FOLLOW_RANGE, 24.0)
            .add(EntityAttributes.ARMOR, 2.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.0, 40, 20.0f));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, true));
    }
    
    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        SnowballEntity snowball = new SnowballEntity(this.getEntityWorld(), this, new ItemStack(Items.SNOWBALL));
        double dx = target.getX() - this.getX();
        double dy = target.getBodyY(0.5) - this.getBodyY(0.5);
        double dz = target.getZ() - this.getZ();
        snowball.setVelocity(dx, dy, dz, 1.5f, 1.0f);
        this.getEntityWorld().spawnEntity(snowball);
    }
    
    public static class TargetGoal extends ActiveTargetGoal<PlayerEntity> {
        public TargetGoal(CrystalMageEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) {
            super(mob, targetClass, checkVisibility);
        }
    }
}
