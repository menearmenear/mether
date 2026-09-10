# METHER MOD - Complete Planning Document

**Author:** menear  
**License:** All Rights Reserved  
**Minecraft Version:** 1.21.4 (Fabric)  
**Mod Version:** 1.0.0

---

## Overview
Mether is a large-scale dimension mod that adds a completely new world to explore, featuring extensive content including new blocks, items, equipment, mobs, bosses, and a complex progression system.

---

## Core Concept
- **Main Theme:** New Dimension with multiple biomes and structures
- **Dimension Name:** Mether (the "meth dimension")
- **Atmosphere:** Special sky effects, mixed danger levels (safe and dangerous areas)
- **Color Scheme:** Multi-colored blocks and items

---

## Dimension Details

### Access Method
- **Portal Type:** Altar/structure with item placement activation
- **Requirement:** Player must place a specific crafted item on an altar to activate the portal

### Environment
- **Danger Level:** Mixed (both safe and hostile areas)
- **Sky:** Special effects (unique atmospheric particles, storms, or visual phenomena)
- **Biomes:** 7+ unique biomes within the Mether dimension
- **Structures:** Mixed sizes (small camps/ruins, medium towers/temples, large dungeons/cities)

---

## Content Scale

### Blocks
- **Quantity:** 30+ custom blocks
- **Types:** 
  - Dimension terrain blocks (stone, dirt, grass variants)
  - Ore blocks (6+ ore types)
  - Decorative blocks
  - Functional blocks (crafting stations, machines, altars)
  - Structure blocks

### Items
- **Quantity:** 30+ custom items
- **Types:**
  - Crafting materials
  - Portal activation items
  - Quest/progression items
  - Food items (basic food system)
  - Special tools and components

### Equipment

#### Tiers
- **Quantity:** 4+ tiers of equipment
- **Power Levels:** Ranging from basic tier up to better than Netherite
- **Special Features:** 
  - Custom enchantments
  - Built-in special effects
  - Active abilities (right-click actions)
  - Multiple special features per tier

#### Equipment Sets
Each tier includes:
- **Tools:** Pickaxe, Axe, Shovel, Hoe
- **Weapons:** Sword, potentially bows/ranged weapons
- **Armor:** Helmet, Chestplate, Leggings, Boots

---

## Mobs & Creatures

### Regular Mobs
- **Quantity:** 9+ regular mobs
- **Types:** 
  - Passive creatures
  - Hostile enemies
  - Neutral mobs
  - Mini-bosses

### Boss Mobs
- **Quantity:** 7+ bosses
- **Purpose:** Mixed (some optional for challenge/loot, some required for progression)
- **Design:** Unique mechanics and challenging fights

---

## Ores & Resources
- **Quantity:** 6+ ore types
- **Purpose:** 
  - Equipment crafting materials
  - Progression gates
  - Special item components
  - Trading currency

---

## Progression System

### Type
- **Complexity:** Complex progression system
- **Requirements:** 
  - Research/tech tree elements
  - Boss defeats unlock content
  - Resource gathering gates
  - Crafting station advancement

### Boss Integration
- Some bosses optional for extra loot/challenge
- Some bosses required to progress through tech tree
- Mixed system allows player choice in progression paths

---

## Crafting System

### Complexity
- **Type:** Complex crafting system
- **Features:**
  - Multiple custom crafting stations
  - Advanced recipe trees
  - Multi-step crafting processes
  - Station upgrades

### Crafting Stations
- Portal altar/activator
- Basic Mether workbench
- Advanced crafting stations (multiple tiers)
- Specialized equipment forges
- Resource processing stations

---

## Food & Farming
- **Type:** Basic food system
- **Content:** 
  - Several unique food items from the Mether dimension
  - Mob drops as food sources
  - Potentially simple crop growing

---

## NPCs & Trading
- **Type:** Custom NPCs
- **Features:**
  - Dimension-specific trader NPCs
  - Quest givers
  - Unique trading systems
  - Progression-locked trades

---

## Audio & Visual

### Music & Sounds
- **Custom Music:** Ambient tracks for dimension atmosphere
- **Custom Sounds:** 
  - Mob sounds
  - Block breaking/placing sounds
  - Equipment sounds
  - Boss music/sounds

### Particles & Effects
- **Quantity:** Moderate amount
- **Usage:**
  - Sky/atmosphere particles
  - Special blocks emit particles
  - Equipment ability effects
  - Boss attack effects
  - Portal effects

---

## Technical Structure

### Package Structure
```
com.menear.mether/
├── Mether.java (main initializer)
├── block/
│   ├── MatherBlocks.java
│   ├── custom/ (custom block classes)
│   └── entity/ (block entities)
├── item/
│   ├── MetherItems.java
│   ├── tools/
│   ├── weapons/
│   ├── armor/
│   └── custom/
├── entity/
│   ├── MetherEntities.java
│   ├── mob/
│   └── boss/
├── world/
│   ├── dimension/
│   ├── biome/
│   ├── feature/
│   └── structure/
├── crafting/
│   ├── station/
│   └── recipe/
├── progression/
│   ├── research/
│   └── unlock/
├── sound/
│   └── MetherSounds.java
├── particle/
│   └── MetherParticles.java
└── client/
    ├── render/
    └── particle/
```

---

## Implementation Phases

### Phase 1: Foundation
- [ ] Update mod metadata
- [ ] Basic dimension registration
- [ ] Core block/item registration systems
- [ ] Basic worldgen (terrain, simple biomes)

### Phase 2: Content - Tier 1
- [ ] First tier blocks (stone, dirt, grass)
- [ ] First ore types (2-3 basic ores)
- [ ] Portal activation system
- [ ] Tier 1 equipment (tools, weapons, armor)
- [ ] Basic crafting station

### Phase 3: Mobs & Combat
- [ ] 3-4 basic hostile mobs
- [ ] 2-3 passive mobs
- [ ] First boss mob
- [ ] Combat mechanics and balancing

### Phase 4: Biomes & Worldgen
- [ ] 7+ unique biomes
- [ ] Small structures
- [ ] Medium structures
- [ ] Biome-specific features

### Phase 5: Content - Tier 2-3
- [ ] Additional ore types
- [ ] Tier 2 equipment with special abilities
- [ ] Tier 3 equipment with advanced abilities
- [ ] Advanced crafting stations
- [ ] More mobs (4-6 additional)
- [ ] 2-3 more bosses

### Phase 6: Progression System
- [ ] Research/tech tree mechanics
- [ ] Boss-gated progression
- [ ] Unlock systems
- [ ] Quest/progression tracking

### Phase 7: Content - Tier 4+
- [ ] Final ore types
- [ ] Top tier equipment (better than netherite)
- [ ] Final bosses (4-5 end-game bosses)
- [ ] Large structures/dungeons
- [ ] End-game content

### Phase 8: NPCs & Trading
- [ ] Custom NPC entities
- [ ] Trading systems
- [ ] NPC spawning in structures
- [ ] Progression-locked trades

### Phase 9: Polish
- [ ] Custom sounds for all mobs
- [ ] Custom ambient music
- [ ] Particle effects
- [ ] Sky effects
- [ ] Food items
- [ ] Balance testing

### Phase 10: Testing & Refinement
- [ ] Full playthrough testing
- [ ] Balance adjustments
- [ ] Bug fixes
- [ ] Performance optimization

---

## Notes
- This is a large-scale mod that will require significant development time
- Each phase should be tested before moving to the next
- Git commits should be made after each major feature/phase
- Textures and models will need to be created for all content
- Sound files will need to be sourced or created
