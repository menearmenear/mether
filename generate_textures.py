#!/usr/bin/env python3
"""Mether Mod texture generator - generates 16x16 PNG textures using Pillow."""
import os
import math
import random
import struct
from PIL import Image, ImageDraw

BLOCK_DIR = "src/main/resources/assets/mether/textures/block"
ITEM_DIR = "src/main/resources/assets/mether/textures/item"
ENTITY_DIR = "src/main/resources/assets/mether/textures/entity"
PARTICLE_DIR = "src/main/resources/assets/mether/textures/particle"
MODEL_DIR = "src/main/resources/assets/mether/textures/models"

for d in [BLOCK_DIR, ITEM_DIR, ENTITY_DIR, PARTICLE_DIR, MODEL_DIR]:
    os.makedirs(d, exist_ok=True)

random.seed(42)

def make_texture(name, path, width=16, height=16):
    img = Image.new("RGBA", (width, height), (0, 0, 0, 0))
    return img

def shade(color, factor):
    r = max(0, min(255, int(color[0] * factor)))
    g = max(0, min(255, int(color[1] * factor)))
    b = max(0, min(255, int(color[2] * factor)))
    return (r, g, b, color[3] if len(color) > 3 else 255)

def add_noise(img, base_color, intensity=0.12, seed_val=None):
    if seed_val is not None:
        rnd = random.Random(seed_val)
    else:
        rnd = random.Random()
    for x in range(img.width):
        for y in range(img.height):
            if img.getpixel((x, y))[3] == 0:
                continue
            f = 1.0 + rnd.uniform(-intensity, intensity)
            r = max(0, min(255, int(base_color[0] * f)))
            g = max(0, min(255, int(base_color[1] * f)))
            b = max(0, min(255, int(base_color[2] * f)))
            img.putpixel((x, y), (r, g, b, base_color[3] if len(base_color) > 3 else 255))

def draw_crystal_block(base_color, name, ore_residue=None):
    img = make_texture(name, BLOCK_DIR)
    d = ImageDraw.Draw(img)
    dark = shade(base_color, 0.55)
    for x in range(16):
        for y in range(16):
            img.putpixel((x, y), dark)
    if ore_residue:
        for x in range(16):
            for y in range(16):
                img.putpixel((x, y), ore_residue)
        rnd = random.Random(hash(name) % 1000)
        for _ in range(8):
            cx, cy = rnd.randint(0, 15), rnd.randint(0, 15)
            for x in range(max(0,cx-1), min(16,cx+2)):
                for y in range(max(0,cy-1), min(16,cy+2)):
                    img.putpixel((x, y), shade(base_color, rnd.uniform(0.8, 1.2)))
    # crystal facets - diagonal highlights
    for _ in range(12):
        x1 = random.randint(0, 14)
        y1 = random.randint(0, 14)
        length = random.randint(2, 5)
        for i in range(length):
            if x1 + i < 16 and y1 + i < 16:
                img.putpixel((x1 + i, y1 + i), shade(base_color, random.uniform(1.1, 1.5)))
    # glow corners
    for x in range(16):
        for y in range(16):
            dist = ((x - 7.5) ** 2 + (y - 7.5) ** 2) ** 0.5
            if dist > 10:
                img.putpixel((x, y), shade(base_color, 0.8))
    img.save(f"{BLOCK_DIR}/{name}.png")
    return img

def draw_ore_block(base_color, stone_color, name):
    img = make_texture(name, BLOCK_DIR)
    for x in range(16):
        for y in range(16):
            img.putpixel((x, y), stone_color)
    rnd = random.Random(hash(name) % 1000)
    for _ in range(6):
        cx, cy = rnd.randint(1, 14), rnd.randint(1, 14)
        for x in range(max(0, cx - 2), min(16, cx + 3)):
            for y in range(max(0, cy - 2), min(16, cy + 3)):
                if (x - cx) ** 2 + (y - cy) ** 2 <= 4:
                    f = 0.9 + rnd.random() * 0.3
                    img.putpixel((x, y), shade(base_color, f))
    img.save(f"{BLOCK_DIR}/{name}.png")
    return img

# Crystal colors
CRYSTALS = {
    "luminite":  (230, 230, 230),
    "rubicite":  (230, 50, 50),
    "sapphirite":(50, 100, 230),
    "emerite":   (50, 200, 90),
    "citrite":   (240, 220, 50),
    "amethine":  (170, 60, 240),
    "obsidite":  (60, 60, 80),
    "celestite": (50, 220, 230),
    "pyrope":    (240, 130, 30),
    "prismarite":(200, 150, 250),
}

STONE = (90, 90, 100)

print("=== Generating block textures ===")
for name, color in CRYSTALS.items():
    draw_crystal_block(color, name + "_block")
    draw_ore_block(color, STONE, name + "_ore")
    print(f"  {name}_block, {name}_ore")

# Terrain
print("=== Terrain blocks ===")
crystal_grass = make_texture("crystal_grass_top", BLOCK_DIR)
for x in range(16):
    for y in range(16):
        crystal_grass.putpixel((x, y), (110, 200, 90))
for i in range(30):
    x = random.randint(0, 15)
    y = random.randint(5, 15)
    for j in range(random.randint(2, 5)):
        if x + j < 16:
            crystal_grass.putpixel((x + j, y), (80, 160, 60 + int(20 * random.random())))
crystal_grass.save(f"{BLOCK_DIR}/crystal_grass_top.png")

crystal_grass_side = make_texture("crystal_grass_side", BLOCK_DIR)
for x in range(16):
    for y in range(16):
        if y < 3:
            crystal_grass_side.putpixel((x, y), (110, 200, 90))
        else:
            crystal_grass_side.putpixel((x, y), (120, 90, 70))
for i in range(10):
    x = random.randint(0, 15)
    y = random.randint(1, 2)
    crystal_grass_side.putpixel((x, y), (80, 160, 60))
crystal_grass_side.save(f"{BLOCK_DIR}/crystal_grass_side.png")

crystal_dirt = make_texture("crystal_dirt", BLOCK_DIR)
for x in range(16):
    for y in range(16):
        f = 0.85 + random.random() * 0.3
        crystal_dirt.putpixel((x, y), (int(140 * f), int(105 * f), int(80 * f)))
crystal_dirt.save(f"{BLOCK_DIR}/crystal_dirt.png")

crystal_stone = make_texture("crystal_stone", BLOCK_DIR)
for x in range(16):
    for y in range(16):
        f = 0.85 + random.random() * 0.3
        crystal_stone.putpixel((x, y), (int(95 * f), int(95 * f), int(110 * f)))
crystal_stone.save(f"{BLOCK_DIR}/crystal_stone.png")

def draw_portal_frame():
    img = make_texture("luminite_portal_frame", BLOCK_DIR)
    for x in range(16):
        for y in range(16):
            gray = (60, 60, 65)
            img.putpixel((x, y), gray)
    d = ImageDraw.Draw(img)
    # crystal veins on the frame
    for _ in range(3):
        x1 = random.randint(0, 15)
        y1 = random.randint(0, 15)
        for i in range(4):
            if x1 + i < 16:
                img.putpixel((x1 + i, y1), (230, 230, 230))
    img.save(f"{BLOCK_DIR}/luminite_portal_frame.png")

draw_portal_frame()
print("  terrain + portal frame done")

# Forge / Infuser
def draw_forge():
    img = make_texture("crystal_forge", BLOCK_DIR)
    d = ImageDraw.Draw(img)
    for x in range(16):
        for y in range(16):
            img.putpixel((x, y), (70, 70, 80))
    d.rectangle([2, 6, 13, 11], fill=(110, 110, 120))
    d.rectangle([5, 3, 10, 6], fill=(230, 230, 230))
    d.rectangle([0, 12, 15, 13], fill=(50, 50, 60))
    for i in range(3):
        d.rectangle([3 + i * 4, 14, 4 + i * 4, 15], fill=(40, 40, 50))
    img.save(f"{BLOCK_DIR}/crystal_forge.png")

def draw_infuser():
    img = make_texture("crystal_infuser", BLOCK_DIR)
    d = ImageDraw.Draw(img)
    for x in range(16):
        for y in range(16):
            img.putpixel((x, y), (50, 50, 60))
    d.rectangle([4, 2, 11, 12], fill=(60, 60, 80))
    d.ellipse([5, 0, 10, 5], fill=(170, 60, 240))
    d.rectangle([5, 5, 10, 10], fill=(100, 80, 140))
    img.save(f"{BLOCK_DIR}/crystal_infuser.png")

def draw_portal():
    img = make_texture("mether_portal", BLOCK_DIR)
    for x in range(16):
        for y in range(16):
            hue = (x + y) / 32.0
            r = int(255 * (0.3 + 0.7 * abs(math.sin(hue * 3.14))))
            g = int(255 * (0.3 + 0.7 * abs(math.sin(hue * 3.14 + 2.1))))
            b = int(255 * (0.3 + 0.7 * abs(math.sin(hue * 3.14 + 4.2))))
            img.putpixel((x, y), (255 - b // 2, 255 - r // 2, 255 - g // 2, 200))
    img.save(f"{BLOCK_DIR}/mether_portal.png")

draw_forge()
draw_infuser()
draw_portal()
print("  forge, infuser, portal done")

def draw_plant(base_color, name):
    img = make_texture(name, BLOCK_DIR)
    d = ImageDraw.Draw(img)
    d.rectangle([7, 10, 8, 15], fill=(60, 120, 60))
    for i in range(5):
        x = 2 + i * 3
        y = 12 - i * 2
        d.ellipse([x, y, x + 3, y + 3], fill=shade(base_color, 0.9))
    img.save(f"{BLOCK_DIR}/{name}.png")

draw_plant((200, 220, 240), "crystal_flower")
draw_plant((170, 60, 240), "glowing_mushroom")
print("  plants done")

# Entity models
def draw_butterfly_entity():
    img = make_texture("crystal_butterfly", ENTITY_DIR, 32, 32)
    d = ImageDraw.Draw(img)
    # wings
    d.polygon([(8, 12), (2, 4), (6, 8), (8, 10)], fill=(200, 230, 255, 200))
    d.polygon([(8, 12), (2, 20), (8, 18)], fill=(180, 210, 255, 180))
    d.polygon([(24, 12), (30, 4), (26, 8), (24, 10)], fill=(200, 230, 255, 200))
    d.polygon([(24, 12), (30, 20), (24, 18)], fill=(180, 210, 255, 180))
    # body
    d.ellipse([14, 10, 18, 22], fill=(120, 140, 160))
    # antennae
    d.line([(15, 10), (12, 4)], fill=(120, 140, 160))
    d.line([(17, 10), (20, 4)], fill=(120, 140, 160))
    img.save(f"{ENTITY_DIR}/crystal_butterfly.png")

draw_butterfly_entity()
print("  butterfly entity done")

def draw_golem_entity():
    img = make_texture("crystal_golem", ENTITY_DIR, 64, 64)
    d = ImageDraw.Draw(img)
    d.rectangle([12, 24, 24, 48], fill=(150, 150, 170))
    d.rectangle([8, 16, 28, 28], fill=(180, 180, 200))
    d.rectangle([20, 28, 28, 44], fill=(150, 150, 170))
    d.rectangle([4, 28, 12, 44], fill=(150, 150, 170))
    d.rectangle([16, 44, 24, 52], fill=(140, 140, 160))
    d.rectangle([10, 18, 14, 22], fill=(230, 230, 230))
    d.rectangle([18, 18, 22, 22], fill=(230, 230, 230))
    img.save(f"{ENTITY_DIR}/crystal_golem.png")

draw_golem_entity()
print("  golem entity done")

# =============== ITEM TEXTURES ===============
print("=== Generating item textures ===")

def crystal_shard_shape(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.polygon([(8, 1), (11, 4), (9, 6), (8, 13), (6, 15), (4, 5)], fill=shade(base_color, 0.8))
    d.polygon([(8, 1), (11, 4), (10, 6), (8, 10)], fill=shade(base_color, 1.4))
    img.save(f"{ITEM_DIR}/{name}.png")

def refined_crystal_shape(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.polygon([(8, 0), (12, 4), (10, 6), (8, 15), (5, 14), (3, 8)], fill=shade(base_color, 0.85))
    d.polygon([(8, 0), (12, 4), (10, 6), (8, 10)], fill=shade(base_color, 1.5))
    d.polygon([(5, 12), (8, 15), (5, 14)], fill=shade(base_color, 1.2))
    img.save(f"{ITEM_DIR}/{name}.png")

def boss_core_shape(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.ellipse([2, 2, 13, 13], fill=shade(base_color, 0.7))
    d.ellipse([4, 4, 11, 11], fill=shade(base_color, 1.1))
    d.ellipse([6, 6, 9, 9], fill=create_bright(base_color))
    d.polygon([(8, 0), (10, 3), (8, 5), (6, 3)], fill=shade(base_color, 1.3))
    d.polygon([(8, 11), (10, 13), (8, 15), (6, 13)], fill=shade(base_color, 1.3))
    d.polygon([(0, 8), (3, 6), (5, 8), (3, 10)], fill=shade(base_color, 1.3))
    d.polygon([(11, 8), (13, 6), (15, 8), (13, 10)], fill=shade(base_color, 1.3))
    img.save(f"{ITEM_DIR}/{name}.png")

def create_bright(color):
    return (min(255, color[0] + 60), min(255, color[1] + 60), min(255, color[2] + 60))

for name, color in CRYSTALS.items():
    crystal_shard_shape(color, name + "_shard")
    refined_crystal_shape(color, "refined_" + name)
    if name not in ("luminite",):
        boss_core_shape(color, name + "_core")
    print(f"  {name}_shard, refined_{name}")

# Special items
print("=== Special items ===")

def draw_special_item(base_color, name, item_type="orb"):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    if item_type == "orb":
        d.ellipse([2, 2, 13, 13], fill=shade(base_color, 0.7))
        d.ellipse([4, 4, 11, 11], fill=shade(base_color, 1.1))
        d.ellipse([6, 6, 9, 9], fill=create_bright(base_color))
    elif item_type == "ring":
        d.ellipse([2, 5, 13, 13], outline=shade(base_color, 1.0), width=2)
        d.ellipse([5, 2, 10, 5], fill=shade(base_color, 1.0))
        r = 0
        if base_color[0] > 150: r = 255
        d.ellipse([6, 3, 9, 6], fill=(r, 0, 0))
    elif item_type == "lens":
        d.ellipse([2, 2, 13, 13], fill=shade(base_color, 0.6))
        d.ellipse([4, 4, 11, 11], fill=shade(base_color, 1.3))
        d.line([(4, 4), (11, 11)], fill=(255, 255, 255, 120))
        d.line([(11, 4), (4, 11)], fill=(255, 255, 255, 80))
    elif item_type == "compass":
        d.ellipse([1, 1, 14, 14], fill=(180, 180, 160))
        d.ellipse([3, 3, 12, 12], fill=(70, 70, 80))
        d.polygon([(7, 3), (9, 3), (8, 8)], fill=(255, 100, 100))
        d.polygon([(7, 13), (9, 13), (8, 8)], fill=(220, 220, 220))
    elif item_type == "key":
        d.ellipse([8, 2, 14, 8], fill=shade(base_color, 1.2))
        d.rectangle([6, 8, 7, 14], fill=shade(base_color, 0.9))
        d.rectangle([7, 10, 12, 11], fill=shade(base_color, 0.9))
        d.rectangle([10, 11, 11, 13], fill=shade(base_color, 0.9))
    img.save(f"{ITEM_DIR}/{name}.png")

draw_special_item((255, 215, 0), "crystal_key", "key")
draw_special_item((200, 230, 255), "crystal_compass", "compass")
draw_special_item((170, 240, 200), "growth_catalyst", "ring")
draw_special_item((50, 220, 230), "teleportation_crystal", "orb")
draw_special_item((240, 240, 255), "crystal_lens", "lens")
draw_special_item((255, 140, 50), "energy_cell", "bag" if False else "orb")
print("  special items done")

def draw_food(base_color, name, food_type="berry"):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    if food_type == "berry":
        for i in range(3):
            d.ellipse([3 + i * 4, 4, 7 + i * 4, 8], fill=shade(base_color, random.uniform(0.8, 1.2)))
        d.line([(8, 4), (8, 1)], fill=(80, 140, 60))
        d.polygon([(8, 1), (6, 3), (10, 3)], fill=(100, 180, 70))
    elif food_type == "mushroom":
        d.rectangle([7, 8, 9, 15], fill=(210, 210, 200))
        d.ellipse([2, 4, 13, 10], fill=shade(base_color, 1.2))
        for _ in range(6):
            x = random.randint(4, 11)
            y = random.randint(5, 8)
            d.ellipse([x, y, x + 2, y + 2], fill=create_bright(base_color))
    elif food_type == "honey":
        d.rectangle([4, 2, 11, 14], fill=(160, 120, 60))
        d.rectangle([6, 1, 9, 2], fill=(200, 160, 80))
        d.rectangle([5, 3, 10, 13], fill=shade(base_color, 1.0))
    elif food_type == "apple":
        d.ellipse([4, 4, 12, 12], fill=shade(base_color, random.uniform(0.9, 1.1)))
        d.line([(8, 4), (8, 2)], fill=(120, 80, 40))
        d.polygon([(8, 2), (6, 4), (10, 4)], fill=(100, 180, 70))
        d.ellipse([6, 8, 8, 11], fill=create_bright(base_color))
    elif food_type == "fruit":
        d.ellipse([3, 4, 13, 12], fill=shade(base_color, 0.9))
        d.line([(7, 4), (7, 2)], fill=(150, 120, 60))
        d.polygon([(7, 2), (5, 4), (9, 4)], fill=(100, 180, 70))
        d.polygon([(5, 5), (3, 4), (3, 1), (5, 2)], fill=(180, 200, 255))
    img.save(f"{ITEM_DIR}/{name}.png")

draw_food((240, 60, 180), "crystal_berries", "berry")
draw_food((170, 60, 240), "glowing_mushroom", "mushroom")
draw_food((255, 200, 60), "crystal_honey", "honey")
draw_food((255, 80, 80), "crystal_apple", "apple")
draw_food((230, 230, 230), "luminite_fruit", "fruit")
print("  food done")

def draw_dust():
    img = make_texture("crystal_dust", ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    rnd = random.Random(7)
    for _ in range(20):
        x = rnd.randint(1, 15)
        y = rnd.randint(1, 15)
        c = rnd.randint(180, 255)
        d.point((x, y), fill=(c, c, min(255, c + 30)))
    img.save(f"{ITEM_DIR}/crystal_dust.png")

def draw_luminite_stone():
    img = make_texture("luminite_stone", ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    for x in range(16):
        for y in range(16):
            f = random.uniform(0.8, 1.1)
            img.putpixel((x, y), (int(110 * f), int(110 * f), int(125 * f)))
    d.ellipse([6, 6, 9, 9], fill=(230, 230, 230))
    img.save(f"{ITEM_DIR}/luminite_stone.png")

draw_dust()
draw_luminite_stone()
print("  crystal_dust, luminite_stone done")

# =============== TOOLS ===============
print("=== Generating tool textures ===")

def draw_pickaxe(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    # handle
    d.line([(4, 15), (8, 8)], fill=(120, 90, 50), width=2)
    d.line([(8, 8), (12, 3)], fill=(120, 90, 50), width=2)
    # head
    d.rectangle([1, 6, 8, 8], fill=shade(base_color, 0.9))
    d.rectangle([8, 2, 10, 9], fill=shade(base_color, 1.0))
    d.rectangle([10, 1, 12, 4], fill=shade(base_color, 0.8))
    d.rectangle([6, 8, 12, 10], fill=shade(base_color, 0.7))
    # shine
    d.rectangle([9, 3, 9, 5], fill=create_bright(base_color))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_axe(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    # handle
    d.line([(8, 15), (6, 6)], fill=(120, 90, 50), width=2)
    d.line([(6, 6), (6, 3)], fill=(120, 90, 50), width=2)
    # blade
    d.polygon([(3, 1), (10, 3), (10, 7), (6, 9), (3, 9)], fill=shade(base_color, 0.9))
    d.polygon([(3, 1), (10, 3), (10, 5), (8, 7)], fill=shade(base_color, 1.3))
    d.polygon([(3, 9), (6, 9), (5, 11)], fill=shade(base_color, 0.7))
    # shine
    d.point((5, 2), fill=create_bright(base_color))
    d.point((5, 3), fill=create_bright(base_color))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_shovel(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    # handle
    d.line([(8, 15), (8, 6)], fill=(120, 90, 50), width=2)
    # blade
    d.polygon([(4, 5), (12, 5), (14, 1), (10, 3), (8, 1), (6, 3), (2, 1)], fill=shade(base_color, 0.8))
    d.polygon([(4, 5), (8, 5), (8, 6), (6, 7)], fill=shade(base_color, 0.6))
    # shine
    d.point((6, 2), fill=create_bright(base_color))
    d.point((7, 2), fill=create_bright(base_color))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_hoe(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    # handle
    d.line([(8, 15), (8, 6)], fill=(120, 90, 50), width=2)
    d.line([(8, 6), (8, 2)], fill=(120, 90, 50), width=2)
    # blade
    d.rectangle([3, 2, 13, 5], fill=shade(base_color, 0.9))
    d.rectangle([3, 2, 13, 4], fill=shade(base_color, 1.2))
    d.polygon([(3, 3), (1, 3), (0, 4), (3, 4)], fill=shade(base_color, 0.7))
    d.polygon([(13, 3), (15, 3), (16, 4), (13, 4)], fill=shade(base_color, 0.7))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_sword(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    # handle
    d.line([(13, 15), (11, 12)], fill=(120, 90, 50), width=2)
    # guard
    d.line([(9, 12), (13, 12)], fill=shade(base_color, 0.8), width=2)
    # blade
    d.polygon([(12, 11), (14, 11), (13, 3), (11, 3)], fill=shade(base_color, 0.8))
    d.polygon([(12, 10), (13, 10), (13, 2), (12, 2)], fill=shade(base_color, 1.3))
    d.point((12, 4), fill=create_bright(base_color))
    d.point((13, 4), fill=create_bright(base_color))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_helmet(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.polygon([(4, 11), (5, 4), (11, 4), (12, 11)], fill=shade(base_color, 0.8))
    d.polygon([(5, 4), (8, 4), (8, 5), (4, 7)], fill=shade(base_color, 1.3))
    d.rectangle([5, 5, 11, 8], fill=(40, 40, 50))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_chestplate(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.polygon([(3, 3), (6, 6), (6, 13), (4, 13), (3, 6)], fill=shade(base_color, 0.8))
    d.polygon([(13, 3), (10, 6), (10, 13), (12, 13), (13, 6)], fill=shade(base_color, 0.8))
    d.rectangle([5, 3, 11, 14], fill=shade(base_color, 0.9))
    d.rectangle([5, 3, 11, 6], fill=shade(base_color, 1.2))
    d.polygon([(8, 4), (7, 8), (9, 8)], fill=(255, 200, 0))
    d.polygon([(8, 9), (7, 12), (9, 12)], fill=(255, 200, 0))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_leggings(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.rectangle([5, 1, 11, 6], fill=shade(base_color, 0.9))
    d.polygon([(5, 4), (5, 13), (8, 13), (8, 4)], fill=shade(base_color, 0.8))
    d.polygon([(8, 4), (11, 5), (11, 13), (8, 13)], fill=shade(base_color, 0.7))
    d.rectangle([5, 1, 11, 3], fill=shade(base_color, 1.2))
    img.save(f"{ITEM_DIR}/{name}.png")

def draw_boots(base_color, name):
    img = make_texture(name, ITEM_DIR, 16, 16)
    d = ImageDraw.Draw(img)
    d.polygon([(4, 5), (5, 13), (12, 13), (13, 5)], fill=shade(base_color, 0.8))
    d.polygon([(4, 5), (6, 8), (12, 7), (13, 5)], fill=shade(base_color, 1.2))
    d.rectangle([7, 11, 11, 14], fill=(60, 60, 70))
    img.save(f"{ITEM_DIR}/{name}.png")

# Draw tool/armor textures for all crystal types
tool_drawers = {
    "pickaxe": draw_pickaxe,
    "axe": draw_axe,
    "shovel": draw_shovel,
    "hoe": draw_hoe,
    "sword": draw_sword,
    "helmet": draw_helmet,
    "chestplate": draw_chestplate,
    "leggings": draw_leggings,
    "boots": draw_boots,
}

for name, color in CRYSTALS.items():
    for tool_name, drawer in tool_drawers.items():
        drawer(color, f"{name}_{tool_name}")
    print(f"  {name} tools+armor done")

# =============== PARTICLE TEXTURES ===============
print("=== Generating particle textures ===")

def draw_particle(name, base_color, size=8):
    img = make_texture(name, PARTICLE_DIR, size, size)
    d = ImageDraw.Draw(img)
    cx = cy = size // 2
    d.ellipse([cx - size//3, cy - size//3, cx + size//3, cy + size//3], fill=base_color)
    d.point((cx, cy), fill=create_bright(base_color))
    img.save(f"{PARTICLE_DIR}/{name}.png")

draw_particle("crystal_sparkle", (255, 255, 255))
draw_particle("crystal_ambient", (200, 230, 255))
draw_particle("portal_crystal", (170, 60, 240))
draw_particle("boss_death", (255, 100, 100))
draw_particle("crystal_grow", (100, 255, 150))
print("  particles done")

# =============== MODEL TEXTURES (armor layer) ===============
print("=== Generating armor layer textures ===")

def make_armor_layer(base_color, name, layer_num):
    img = make_texture(f"{name}_layer_{layer_num}", MODEL_DIR, 64, 32)
    d = ImageDraw.Draw(img)
    # head
    d.rectangle([0, 0, 8, 8], fill=shade(base_color, 0.8))
    d.rectangle([8, 0, 16, 8], fill=shade(base_color, 0.9))
    d.rectangle([0, 8, 8, 16], fill=shade(base_color, 0.7))
    # body
    d.rectangle([16, 0, 24, 8], fill=shade(base_color, 0.9))
    d.rectangle([24, 0, 32, 8], fill=shade(base_color, 1.0))
    d.rectangle([16, 8, 24, 16], fill=shade(base_color, 0.8))
    # arms
    d.rectangle([32, 0, 40, 8], fill=shade(base_color, 0.8))
    d.rectangle([40, 0, 48, 8], fill=shade(base_color, 0.9))
    d.rectangle([32, 8, 40, 16], fill=shade(base_color, 0.7))
    # legs
    d.rectangle([48, 0, 56, 8], fill=shade(base_color, 0.8))
    d.rectangle([56, 0, 64, 8], fill=shade(base_color, 0.9))
    d.rectangle([48, 8, 56, 16], fill=shade(base_color, 0.7))
    d.rectangle([56, 8, 64, 16], fill=shade(base_color, 0.8))
    img.save(f"{MODEL_DIR}/{name}_layer_{layer_num}.png")
    return img

for name, color in CRYSTALS.items():
    make_armor_layer(color, name, 1)
    make_armor_layer(create_bright(color), name, 2)
print("  armor layers done")

print("\n=== ALL TEXTURES GENERATED SUCCESSFULLY ===")