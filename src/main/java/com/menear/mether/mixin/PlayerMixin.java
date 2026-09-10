package com.menear.mether.mixin;

import com.menear.mether.world.dimension.MetherDimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    private void onGetDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if (self.getWorld().getRegistryKey() == MetherDimension.WORLD_KEY) {
            // Players are slightly smaller in the Mether for thematic effect
            cir.setReturnValue(EntityDimensions.changing(0.6f, 1.7f));
        }
    }
}
