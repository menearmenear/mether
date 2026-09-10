package com.menear.mether.client;

import com.menear.mether.block.MetherBlocks;
import com.menear.mether.util.CrystalType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class MetherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register translucent render layer for crystal blocks
        for (CrystalType type : CrystalType.values()) {
            BlockRenderLayerMap.putBlock(
                MetherBlocks.CRYSTAL_BLOCKS.get(type),
                BlockRenderLayer.TRANSLUCENT
            );
            BlockRenderLayerMap.putBlock(
                MetherBlocks.CRYSTAL_ORES.get(type),
                BlockRenderLayer.TRANSLUCENT
            );
        }

        // Portal block render layer
        BlockRenderLayerMap.putBlock(
            MetherBlocks.METHER_PORTAL,
            BlockRenderLayer.TRANSLUCENT
        );
    }
}