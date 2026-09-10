package com.menear.mether.util;

public enum CrystalType {
    LUMINITE("luminite", 0xFFFFFF),      // White/Silver - Basic
    RUBICITE("rubicite", 0xFF0000),      // Red - Fire
    SAPPHIRITE("sapphirite", 0x0000FF),  // Blue - Water/Ice
    EMERITE("emerite", 0x00FF00),        // Green - Nature
    CITRITE("citrite", 0xFFFF00),        // Yellow - Lightning
    AMETHINE("amethine", 0x9B30FF),      // Purple - Magic
    OBSIDITE("obsidite", 0x1A1A1A),      // Black - Shadow
    CELESTITE("celestite", 0x00FFFF),    // Cyan - Sky
    PYROPE("pyrope", 0xFF8C00),          // Orange - Energy
    PRISMARITE("prismarite", 0xFF00FF);  // Rainbow - All

    private final String name;
    private final int color;

    CrystalType(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getTier() {
        return switch (this) {
            case LUMINITE -> 1;
            case RUBICITE, SAPPHIRITE, EMERITE, CITRITE -> 2;
            case AMETHINE, OBSIDITE, CELESTITE, PYROPE -> 3;
            case PRISMARITE -> 4;
        };
    }
}
