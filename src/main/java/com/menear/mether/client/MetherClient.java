package com.menear.mether.client;

import com.menear.mether.block.MetherBlocks;
import com.menear.mether.util.CrystalType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;

public class MetherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register translucent render layer for crystal blocks
        for (CrystalType type : CrystalType.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(
                MetherBlocks.CRYSTAL_BLOCKS.get(type),
                RenderLayer.getTranslucent()
            );
            BlockRenderLayerMap.INSTANCE.putBlock(
                MetherBlocks.CRYSTAL_ORES.get(type),
                RenderLayer.getTranslucent()
            );
        }
        
        // Portal block render layer
        BlockRenderLayerMap.INSTANCE.putBlock(
            MetherBlocks.METHER_PORTAL,
            RenderLayer.getTranslucent()
        );
    }
}
