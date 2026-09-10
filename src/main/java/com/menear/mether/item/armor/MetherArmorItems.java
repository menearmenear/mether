package com.menear.mether.item.armor;

import com.menear.mether.Mether;
import com.menear.mether.item.MetherItems;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MetherArmorItems {
    
    public static final Map<CrystalType, ArmorSet> ARMOR_SETS = new HashMap<>();
    
    public static class ArmorSet {
        public final Item helmet;
        public final Item chestplate;
        public final Item leggings;
        public final Item boots;
        
        public ArmorSet(Item helmet, Item chestplate, Item leggings, Item boots) {
            this.helmet = helmet;
            this.chestplate = chestplate;
            this.leggings = leggings;
            this.boots = boots;
        }
    }
    
    static {
        for (CrystalType type : CrystalType.values()) {
            ArmorMaterial material = createArmorMaterial(type);
            
            Item helmet = registerArmor(type.getName() + "_helmet",
                new ArmorItem(material, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(material.getDurability()))));
            
            Item chestplate = registerArmor(type.getName() + "_chestplate",
                new ArmorItem(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(material.getDurability()))));
            
            Item leggings = registerArmor(type.getName() + "_leggings",
                new ArmorItem(material, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(material.getDurability()))));
            
            Item boots = registerArmor(type.getName() + "_boots",
                new ArmorItem(material, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(material.getDurability()))));
            
            ARMOR_SETS.put(type, new ArmorSet(helmet, chestplate, leggings, boots));
        }
    }
    
    private static ArmorMaterial createArmorMaterial(CrystalType type) {
        return new ArmorMaterial() {
            @Override
            public int getDurability() {
                return switch (type.getTier()) {
                    case 1 -> 15;  // Iron level
                    case 2 -> 33;  // Diamond level
                    case 3 -> 40;  // Better than diamond
                    case 4 -> 50;  // Better than netherite
                    default -> 10;
                };
            }
            
            @Override
            public Map<ArmorItem.Type, Integer> getProtection() {
                int[] protection = switch (type.getTier()) {
                    case 1 -> new int[]{2, 5, 6, 2};  // Iron level (15 total)
                    case 2 -> new int[]{3, 6, 8, 3};  // Diamond level (20 total)
                    case 3 -> new int[]{3, 7, 9, 3};  // Better than diamond (22 total)
                    case 4 -> new int[]{4, 8, 10, 4}; // Better than netherite (26 total)
                    default -> new int[]{1, 3, 4, 1};
                };
                
                return Map.of(
                    ArmorItem.Type.BOOTS, protection[0],
                    ArmorItem.Type.LEGGINGS, protection[1],
                    ArmorItem.Type.CHESTPLATE, protection[2],
                    ArmorItem.Type.HELMET, protection[3]
                );
            }
            
            @Override
            public int getEnchantability() {
                return 10 + (type.getTier() * 5);
            }
            
            @Override
            public RegistryEntry<SoundEvent> getEquipSound() {
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            }
            
            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.ofItems(MetherItems.REFINED_CRYSTALS.get(type));
            }
            
            @Override
            public List<ArmorMaterial.Layer> getLayers() {
                return List.of(new ArmorMaterial.Layer(Identifier.of(Mether.MOD_ID, type.getName())));
            }
            
            @Override
            public float getToughness() {
                return switch (type.getTier()) {
                    case 1 -> 0.0f;
                    case 2 -> 2.0f;
                    case 3 -> 3.0f;
                    case 4 -> 4.0f;
                    default -> 0.0f;
                };
            }
            
            @Override
            public float getKnockbackResistance() {
                return switch (type.getTier()) {
                    case 1 -> 0.0f;
                    case 2 -> 0.0f;
                    case 3 -> 0.1f;
                    case 4 -> 0.2f;
                    default -> 0.0f;
                };
            }
        };
    }
    
    private static Item registerArmor(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(registered));
        return registered;
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering armor for " + Mether.MOD_ID);
    }
}
