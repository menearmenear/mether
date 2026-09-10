package com.menear.mether.item.armor;

import com.menear.mether.Mether;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MetherArmorItems {

    public static final TagKey<Item> REPAIRS_CRYSTAL_ARMOR =
        TagKey.of(RegistryKeys.ITEM, Identifier.of(Mether.MOD_ID, "repairs_crystal_armor"));

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
                new Item(new Item.Settings()
                    .armor(material, EquipmentType.HELMET)
                    .maxDamage(EquipmentType.HELMET.getMaxDamage(material.durability()))));

            Item chestplate = registerArmor(type.getName() + "_chestplate",
                new Item(new Item.Settings()
                    .armor(material, EquipmentType.CHESTPLATE)
                    .maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(material.durability()))));

            Item leggings = registerArmor(type.getName() + "_leggings",
                new Item(new Item.Settings()
                    .armor(material, EquipmentType.LEGGINGS)
                    .maxDamage(EquipmentType.LEGGINGS.getMaxDamage(material.durability()))));

            Item boots = registerArmor(type.getName() + "_boots",
                new Item(new Item.Settings()
                    .armor(material, EquipmentType.BOOTS)
                    .maxDamage(EquipmentType.BOOTS.getMaxDamage(material.durability()))));

            ARMOR_SETS.put(type, new ArmorSet(helmet, chestplate, leggings, boots));
        }
    }

    private static ArmorMaterial createArmorMaterial(CrystalType type) {
        int durability = switch (type.getTier()) {
            case 1 -> 15;  // Iron level
            case 2 -> 33;  // Diamond level
            case 3 -> 40;  // Better than diamond
            case 4 -> 50;  // Better than netherite
            default -> 10;
        };

        int[] protection = switch (type.getTier()) {
            case 1 -> new int[]{2, 5, 6, 2};  // Iron level (15 total)
            case 2 -> new int[]{3, 6, 8, 3};  // Diamond level (20 total)
            case 3 -> new int[]{3, 7, 9, 3};  // Better than diamond (22 total)
            case 4 -> new int[]{4, 8, 10, 4}; // Better than netherite (26 total)
            default -> new int[]{1, 3, 4, 1};
        };

        Map<EquipmentType, Integer> defense = Map.of(
            EquipmentType.BOOTS, protection[0],
            EquipmentType.LEGGINGS, protection[1],
            EquipmentType.CHESTPLATE, protection[2],
            EquipmentType.HELMET, protection[3]
        );

        int enchantability = 10 + (type.getTier() * 5);

        float toughness = switch (type.getTier()) {
            case 1 -> 0.0f;
            case 2 -> 2.0f;
            case 3 -> 3.0f;
            case 4 -> 4.0f;
            default -> 0.0f;
        };

        float knockbackResistance = switch (type.getTier()) {
            case 1 -> 0.0f;
            case 2 -> 0.0f;
            case 3 -> 0.1f;
            case 4 -> 0.2f;
            default -> 0.0f;
        };

        RegistryKey<EquipmentAsset> asset = RegistryKey.of(
            EquipmentAssetKeys.REGISTRY_KEY,
            Identifier.of(Mether.MOD_ID, type.getName())
        );

        return new ArmorMaterial(
            durability,
            defense,
            enchantability,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            toughness,
            knockbackResistance,
            REPAIRS_CRYSTAL_ARMOR,
            asset
        );
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