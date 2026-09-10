package com.menear.mether.entity.mob;

import com.menear.mether.item.MetherItems;
import com.menear.mether.util.CrystalType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class CrystalButterflyEntity extends AnimalEntity {
    
    public CrystalButterflyEntity(EntityType<? extends CrystalButterflyEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 4.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.FLYING_SPEED, 0.4)
            .add(EntityAttributes.FOLLOW_RANGE, 16.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }
    
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(MetherItems.CRYSTAL_DUST);
    }
    
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    
    @Override
    protected void dropLoot(ServerWorld world, net.minecraft.entity.damage.DamageSource source, boolean causedByPlayer) {
        ItemStack stack = new ItemStack(MetherItems.CRYSTAL_DUST);
        this.dropItem(stack, false, false);
    }
}
