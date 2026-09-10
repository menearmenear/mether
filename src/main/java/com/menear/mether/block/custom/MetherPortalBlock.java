package com.menear.mether.block.custom;

import com.menear.mether.world.dimension.MetherDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MetherPortalBlock extends Block {
    
    public MetherPortalBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld serverWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            // Teleport entity to Mether dimension or back to overworld
            ServerWorld destination = serverWorld.getServer().getWorld(
                serverWorld.getRegistryKey() == MetherDimension.WORLD_KEY ? World.OVERWORLD : MetherDimension.WORLD_KEY
            );
            
            if (destination != null) {
                // Simple teleportation (will be enhanced later)
                entity.moveToWorld(destination);
            }
        }
    }
}
