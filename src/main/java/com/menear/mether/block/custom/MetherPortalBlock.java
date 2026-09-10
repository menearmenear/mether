package com.menear.mether.block.custom;

import com.menear.mether.world.dimension.MetherDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class MetherPortalBlock extends Block {

    public MetherPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean canStepOn) {
        if (world instanceof ServerWorld serverWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals(false)) {
            // Teleport entity to Mether dimension or back to overworld
            ServerWorld destination = serverWorld.getServer().getWorld(
                serverWorld.getRegistryKey() == MetherDimension.WORLD_KEY ? World.OVERWORLD : MetherDimension.WORLD_KEY
            );

            if (destination != null) {
                TeleportTarget target = new TeleportTarget(
                    destination,
                    entity.getEntityPos(),
                    Vec3d.ZERO,
                    entity.getYaw(),
                    entity.getPitch(),
                    TeleportTarget.NO_OP
                );
                entity.teleportTo(target);
            }
        }
    }
}
