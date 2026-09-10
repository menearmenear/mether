package com.menear.mether.block;

import com.menear.mether.Mether;
import com.menear.mether.block.custom.MetherPortalBlock;
import com.menear.mether.block.entity.CrystalForgeBlockEntity;
import com.menear.mether.block.entity.CrystalInfuserBlockEntity;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MetherBlocks {
    
    // Terrain Blocks
    public static final Block CRYSTAL_GRASS_BLOCK = registerBlock("crystal_grass_block",
        new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).luminance(state -> 3)));
    
    public static final Block CRYSTAL_DIRT = registerBlock("crystal_dirt",
        new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
    
    public static final Block CRYSTAL_STONE = registerBlock("crystal_stone",
        new Block(AbstractBlock.Settings.copy(Blocks.STONE).luminance(state -> 2)));
    
    // Crystal Ore Blocks (for each crystal type)
    public static final Map<CrystalType, Block> CRYSTAL_ORES = new HashMap<>();
    
    // Pure Crystal Blocks
    public static final Map<CrystalType, Block> CRYSTAL_BLOCKS = new HashMap<>();
    
    // Crafting Stations
    public static final Block CRYSTAL_FORGE = registerBlock("crystal_forge",
        new Block(AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).luminance(state -> 8)));
    
    public static final Block CRYSTAL_INFUSER = registerBlock("crystal_infuser",
        new Block(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE).luminance(state -> 8)));
    
    // Block Entity Types
    public static BlockEntityType<CrystalForgeBlockEntity> CRYSTAL_FORGE_BLOCK_ENTITY;
    public static BlockEntityType<CrystalInfuserBlockEntity> CRYSTAL_INFUSER_BLOCK_ENTITY;
    
    // Portal Frame
    public static final Block LUMINITE_PORTAL_FRAME = registerBlock("luminite_portal_frame",
        new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN).luminance(state -> 5)));
    
    // Portal Block
    public static final Block METHER_PORTAL = registerBlockNoItem("mether_portal",
        new MetherPortalBlock(AbstractBlock.Settings.create()
            .noCollision()
            .luminance(state -> 15)
            .strength(-1.0f, 3600000.0f)
            .dropsNothing()
            .mapColor(MapColor.PURPLE)));
    
    static {
        // Register crystal ores
        for (CrystalType type : CrystalType.values()) {
            Block ore = registerBlock(type.getName() + "_ore",
                new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)
                    .luminance(state -> 7)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));
            CRYSTAL_ORES.put(type, ore);
            
            Block crystalBlock = registerBlock(type.getName() + "_block",
                new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)
                    .luminance(state -> 10)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));
            CRYSTAL_BLOCKS.put(type, crystalBlock);
        }
        
        // Register block entity types
        CRYSTAL_FORGE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Mether.MOD_ID, "crystal_forge"),
            FabricBlockEntityTypeBuilder.create(CrystalForgeBlockEntity::new, CRYSTAL_FORGE).build());
        
            CRYSTAL_INFUSER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Mether.MOD_ID, "crystal_infuser"),
            FabricBlockEntityTypeBuilder.create(CrystalInfuserBlockEntity::new, CRYSTAL_INFUSER).build());
    }
    
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Mether.MOD_ID, name), block);
    }
    
    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Mether.MOD_ID, name), block);
    }
    
    private static void registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name),
            new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> entries.add(item));
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering blocks for " + Mether.MOD_ID);
    }
}
