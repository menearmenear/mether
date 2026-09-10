package com.menear.mether.item.tool;

import com.menear.mether.Mether;
import com.menear.mether.item.MetherItems;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MetherToolItems {
    
    // Tool sets for each crystal type
    public static final Map<CrystalType, ToolSet> TOOL_SETS = new HashMap<>();
    
    public static class ToolSet {
        public final Item pickaxe;
        public final Item axe;
        public final Item shovel;
        public final Item hoe;
        public final Item sword;
        
        public ToolSet(Item pickaxe, Item axe, Item shovel, Item hoe, Item sword) {
            this.pickaxe = pickaxe;
            this.axe = axe;
            this.shovel = shovel;
            this.hoe = hoe;
            this.sword = sword;
        }
    }
    
    static {
        for (CrystalType type : CrystalType.values()) {
            ToolMaterial material = createToolMaterial(type);
            
            Item pickaxe = registerTool(type.getName() + "_pickaxe",
                new PickaxeItem(material, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(material, 1, -2.8f))));
            
            Item axe = registerTool(type.getName() + "_axe",
                new AxeItem(material, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(material, 6, -3.0f))));
            
            Item shovel = registerTool(type.getName() + "_shovel",
                new ShovelItem(material, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(material, 1.5f, -3.0f))));
            
            Item hoe = registerTool(type.getName() + "_hoe",
                new HoeItem(material, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(material, 0, -3.0f))));
            
            Item sword = registerTool(type.getName() + "_sword",
                new SwordItem(material, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(material, 3, -2.4f))));
            
            TOOL_SETS.put(type, new ToolSet(pickaxe, axe, shovel, hoe, sword));
        }
    }
    
    private static ToolMaterial createToolMaterial(CrystalType type) {
        return new ToolMaterial() {
            @Override
            public int getDurability() {
                return switch (type.getTier()) {
                    case 1 -> 500;   // Luminite - Iron level
                    case 2 -> 1561;  // Elemental - Diamond level
                    case 3 -> 2500;  // Advanced - Better than diamond
                    case 4 -> 3500;  // Prismarite - Better than netherite
                    default -> 250;
                };
            }
            
            @Override
            public float getMiningSpeedMultiplier() {
                return switch (type.getTier()) {
                    case 1 -> 6.0f;
                    case 2 -> 8.0f;
                    case 3 -> 10.0f;
                    case 4 -> 12.0f;
                    default -> 4.0f;
                };
            }
            
            @Override
            public float getAttackDamage() {
                return switch (type.getTier()) {
                    case 1 -> 2.0f;
                    case 2 -> 3.0f;
                    case 3 -> 4.0f;
                    case 4 -> 5.0f;
                    default -> 1.0f;
                };
            }
            
            @Override
            public int getMiningLevel() {
                return switch (type.getTier()) {
                    case 1 -> 2;  // Iron level
                    case 2 -> 3;  // Diamond level
                    case 3 -> 4;  // Netherite level
                    case 4 -> 5;  // Beyond netherite
                    default -> 1;
                };
            }
            
            @Override
            public int getEnchantability() {
                return 10 + (type.getTier() * 5);
            }
            
            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.ofItems(MetherItems.REFINED_CRYSTALS.get(type));
            }
        };
    }
    
    private static Item registerTool(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(registered));
        return registered;
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering tools for " + Mether.MOD_ID);
    }
}
