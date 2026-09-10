package com.menear.mether.mixin;

import com.menear.mether.world.dimension.MetherDimension;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class WorldMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerWorld self = (ServerWorld) (Object) this;
        if (self.getRegistryKey() == MetherDimension.WORLD_KEY) {
            // Custom tick behavior for the Mether dimension
            // Crystal growth, ambient particles, etc.
        }
    }
}
