// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.MetadataValue;
import java.util.Collections;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.TileEntitySkull;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.PistonMoveReaction;
import net.minecraft.server.v1_5_R3.BlockRedstoneWire;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockFace;
import net.minecraft.server.v1_5_R3.EnumSkyBlock;
import org.bukkit.Material;
import org.bukkit.Chunk;
import org.bukkit.util.BlockVector;
import org.bukkit.Location;
import org.bukkit.World;
import net.minecraft.server.v1_5_R3.BiomeBase;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import org.bukkit.block.Block;

public class CraftBlock implements Block
{
    private final CraftChunk chunk;
    private final int x;
    private final int y;
    private final int z;
    private static final Biome[] BIOME_MAPPING;
    private static final BiomeBase[] BIOMEBASE_MAPPING;
    
    public CraftBlock(final CraftChunk chunk, final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
    }
    
    public World getWorld() {
        return this.chunk.getWorld();
    }
    
    public Location getLocation() {
        return new Location(this.getWorld(), this.x, this.y, this.z);
    }
    
    public Location getLocation(final Location loc) {
        if (loc != null) {
            loc.setWorld(this.getWorld());
            loc.setX(this.x);
            loc.setY(this.y);
            loc.setZ(this.z);
            loc.setYaw(0.0f);
            loc.setPitch(0.0f);
        }
        return loc;
    }
    
    public BlockVector getVector() {
        return new BlockVector(this.x, this.y, this.z);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Chunk getChunk() {
        return this.chunk;
    }
    
    public void setData(final byte data) {
        this.chunk.getHandle().world.setData(this.x, this.y, this.z, data, 3);
    }
    
    public void setData(final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            this.chunk.getHandle().world.setData(this.x, this.y, this.z, data, 3);
        }
        else {
            this.chunk.getHandle().world.setData(this.x, this.y, this.z, data, 2);
        }
    }
    
    public byte getData() {
        return (byte)this.chunk.getHandle().getData(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }
    
    public void setType(final Material type) {
        this.setTypeId(type.getId());
    }
    
    public boolean setTypeId(final int type) {
        return this.chunk.getHandle().world.setTypeIdAndData(this.x, this.y, this.z, type, this.getData(), 3);
    }
    
    public boolean setTypeId(final int type, final boolean applyPhysics) {
        if (applyPhysics) {
            return this.setTypeId(type);
        }
        return this.chunk.getHandle().world.setTypeIdAndData(this.x, this.y, this.z, type, this.getData(), 2);
    }
    
    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            return this.chunk.getHandle().world.setTypeIdAndData(this.x, this.y, this.z, type, data, 3);
        }
        final boolean success = this.chunk.getHandle().world.setTypeIdAndData(this.x, this.y, this.z, type, data, 2);
        if (success) {
            this.chunk.getHandle().world.notify(this.x, this.y, this.z);
        }
        return success;
    }
    
    public Material getType() {
        return Material.getMaterial(this.getTypeId());
    }
    
    public int getTypeId() {
        return this.chunk.getHandle().getTypeId(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }
    
    public byte getLightLevel() {
        return (byte)this.chunk.getHandle().world.getLightLevel(this.x, this.y, this.z);
    }
    
    public byte getLightFromSky() {
        return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.SKY, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }
    
    public byte getLightFromBlocks() {
        return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.BLOCK, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }
    
    public Block getFace(final BlockFace face) {
        return this.getRelative(face, 1);
    }
    
    public Block getFace(final BlockFace face, final int distance) {
        return this.getRelative(face, distance);
    }
    
    public Block getRelative(final int modX, final int modY, final int modZ) {
        return this.getWorld().getBlockAt(this.getX() + modX, this.getY() + modY, this.getZ() + modZ);
    }
    
    public Block getRelative(final BlockFace face) {
        return this.getRelative(face, 1);
    }
    
    public Block getRelative(final BlockFace face, final int distance) {
        return this.getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }
    
    public BlockFace getFace(final Block block) {
        final BlockFace[] arr$;
        final BlockFace[] values = arr$ = BlockFace.values();
        for (final BlockFace face : arr$) {
            if (this.getX() + face.getModX() == block.getX() && this.getY() + face.getModY() == block.getY() && this.getZ() + face.getModZ() == block.getZ()) {
                return face;
            }
        }
        return null;
    }
    
    public String toString() {
        return "CraftBlock{chunk=" + this.chunk + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",type=" + this.getType() + ",data=" + this.getData() + '}';
    }
    
    public static BlockFace notchToBlockFace(final int notch) {
        switch (notch) {
            case 0: {
                return BlockFace.DOWN;
            }
            case 1: {
                return BlockFace.UP;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            case 4: {
                return BlockFace.WEST;
            }
            case 5: {
                return BlockFace.EAST;
            }
            default: {
                return BlockFace.SELF;
            }
        }
    }
    
    public static int blockFaceToNotch(final BlockFace face) {
        switch (face) {
            case DOWN: {
                return 0;
            }
            case UP: {
                return 1;
            }
            case NORTH: {
                return 2;
            }
            case SOUTH: {
                return 3;
            }
            case WEST: {
                return 4;
            }
            case EAST: {
                return 5;
            }
            default: {
                return 7;
            }
        }
    }
    
    public BlockState getState() {
        final Material material = this.getType();
        switch (material) {
            case SIGN:
            case SIGN_POST:
            case WALL_SIGN: {
                return new CraftSign(this);
            }
            case CHEST:
            case TRAPPED_CHEST: {
                return new CraftChest(this);
            }
            case BURNING_FURNACE:
            case FURNACE: {
                return new CraftFurnace(this);
            }
            case DISPENSER: {
                return new CraftDispenser(this);
            }
            case DROPPER: {
                return new CraftDropper(this);
            }
            case HOPPER: {
                return new CraftHopper(this);
            }
            case MOB_SPAWNER: {
                return new CraftCreatureSpawner(this);
            }
            case NOTE_BLOCK: {
                return new CraftNoteBlock(this);
            }
            case JUKEBOX: {
                return new CraftJukebox(this);
            }
            case BREWING_STAND: {
                return new CraftBrewingStand(this);
            }
            case SKULL: {
                return new CraftSkull(this);
            }
            case COMMAND: {
                return new CraftCommandBlock(this);
            }
            case BEACON: {
                return new CraftBeacon(this);
            }
            default: {
                return new CraftBlockState(this);
            }
        }
    }
    
    public Biome getBiome() {
        return this.getWorld().getBiome(this.x, this.z);
    }
    
    public void setBiome(final Biome bio) {
        this.getWorld().setBiome(this.x, this.z, bio);
    }
    
    public static Biome biomeBaseToBiome(final BiomeBase base) {
        if (base == null) {
            return null;
        }
        return CraftBlock.BIOME_MAPPING[base.id];
    }
    
    public static BiomeBase biomeToBiomeBase(final Biome bio) {
        if (bio == null) {
            return null;
        }
        return CraftBlock.BIOMEBASE_MAPPING[bio.ordinal()];
    }
    
    public double getTemperature() {
        return this.getWorld().getTemperature(this.x, this.z);
    }
    
    public double getHumidity() {
        return this.getWorld().getHumidity(this.x, this.z);
    }
    
    public boolean isBlockPowered() {
        return this.chunk.getHandle().world.getBlockPower(this.x, this.y, this.z) > 0;
    }
    
    public boolean isBlockIndirectlyPowered() {
        return this.chunk.getHandle().world.isBlockIndirectlyPowered(this.x, this.y, this.z);
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CraftBlock)) {
            return false;
        }
        final CraftBlock other = (CraftBlock)o;
        return this.x == other.x && this.y == other.y && this.z == other.z && this.getWorld().equals(other.getWorld());
    }
    
    public int hashCode() {
        return this.y << 24 ^ this.x ^ this.z ^ this.getWorld().hashCode();
    }
    
    public boolean isBlockFacePowered(final BlockFace face) {
        return this.chunk.getHandle().world.isBlockFacePowered(this.x, this.y, this.z, blockFaceToNotch(face));
    }
    
    public boolean isBlockFaceIndirectlyPowered(final BlockFace face) {
        final int power = this.chunk.getHandle().world.getBlockFacePower(this.x, this.y, this.z, blockFaceToNotch(face));
        final Block relative = this.getRelative(face);
        if (relative.getType() == Material.REDSTONE_WIRE) {
            return Math.max(power, relative.getData()) > 0;
        }
        return power > 0;
    }
    
    public int getBlockPower(final BlockFace face) {
        int power = 0;
        final BlockRedstoneWire wire = net.minecraft.server.v1_5_R3.Block.REDSTONE_WIRE;
        final net.minecraft.server.v1_5_R3.World world = this.chunk.getHandle().world;
        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y - 1, this.z, 0)) {
            power = wire.getPower(world, this.x, this.y - 1, this.z, power);
        }
        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y + 1, this.z, 1)) {
            power = wire.getPower(world, this.x, this.y + 1, this.z, power);
        }
        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x + 1, this.y, this.z, 2)) {
            power = wire.getPower(world, this.x + 1, this.y, this.z, power);
        }
        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x - 1, this.y, this.z, 3)) {
            power = wire.getPower(world, this.x - 1, this.y, this.z, power);
        }
        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z - 1, 4)) {
            power = wire.getPower(world, this.x, this.y, this.z - 1, power);
        }
        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z + 1, 5)) {
            power = wire.getPower(world, this.x, this.y, this.z - 1, power);
        }
        return (power > 0) ? power : (((face == BlockFace.SELF) ? this.isBlockIndirectlyPowered() : this.isBlockFaceIndirectlyPowered(face)) ? 15 : 0);
    }
    
    public int getBlockPower() {
        return this.getBlockPower(BlockFace.SELF);
    }
    
    public boolean isEmpty() {
        return this.getType() == Material.AIR;
    }
    
    public boolean isLiquid() {
        return this.getType() == Material.WATER || this.getType() == Material.STATIONARY_WATER || this.getType() == Material.LAVA || this.getType() == Material.STATIONARY_LAVA;
    }
    
    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(net.minecraft.server.v1_5_R3.Block.byId[this.getTypeId()].material.getPushReaction());
    }
    
    private boolean itemCausesDrops(final ItemStack item) {
        final net.minecraft.server.v1_5_R3.Block block = net.minecraft.server.v1_5_R3.Block.byId[this.getTypeId()];
        final Item itemType = (item != null) ? Item.byId[item.getTypeId()] : null;
        return block != null && (block.material.isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(block)));
    }
    
    public boolean breakNaturally() {
        final net.minecraft.server.v1_5_R3.Block block = net.minecraft.server.v1_5_R3.Block.byId[this.getTypeId()];
        final byte data = this.getData();
        boolean result = false;
        if (block != null) {
            block.dropNaturally(this.chunk.getHandle().world, this.x, this.y, this.z, data, 1.0f, 0);
            result = true;
        }
        this.setTypeId(Material.AIR.getId());
        return result;
    }
    
    public boolean breakNaturally(final ItemStack item) {
        if (this.itemCausesDrops(item)) {
            return this.breakNaturally();
        }
        return this.setTypeId(Material.AIR.getId());
    }
    
    public Collection<ItemStack> getDrops() {
        final List<ItemStack> drops = new ArrayList<ItemStack>();
        final net.minecraft.server.v1_5_R3.Block block = net.minecraft.server.v1_5_R3.Block.byId[this.getTypeId()];
        if (block != null) {
            final byte data = this.getData();
            for (int count = block.getDropCount(0, this.chunk.getHandle().world.random), i = 0; i < count; ++i) {
                final int item = block.getDropType(data, this.chunk.getHandle().world.random, 0);
                if (item > 0) {
                    if (net.minecraft.server.v1_5_R3.Block.SKULL.id == this.getTypeId()) {
                        final net.minecraft.server.v1_5_R3.ItemStack nmsStack = new net.minecraft.server.v1_5_R3.ItemStack(item, 1, block.getDropData(this.chunk.getHandle().world, this.x, this.y, this.z));
                        final TileEntitySkull tileentityskull = (TileEntitySkull)this.chunk.getHandle().world.getTileEntity(this.x, this.y, this.z);
                        if (tileentityskull.getSkullType() == 3 && tileentityskull.getExtraType() != null && tileentityskull.getExtraType().length() > 0) {
                            nmsStack.setTag(new NBTTagCompound());
                            nmsStack.getTag().setString("SkullOwner", tileentityskull.getExtraType());
                        }
                        drops.add(CraftItemStack.asBukkitCopy(nmsStack));
                    }
                    else {
                        drops.add(new ItemStack(item, 1, (short)block.getDropData(data)));
                    }
                }
            }
        }
        return drops;
    }
    
    public Collection<ItemStack> getDrops(final ItemStack item) {
        if (this.itemCausesDrops(item)) {
            return this.getDrops();
        }
        return (Collection<ItemStack>)Collections.emptyList();
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        this.chunk.getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }
    
    static {
        BIOME_MAPPING = new Biome[BiomeBase.biomes.length];
        BIOMEBASE_MAPPING = new BiomeBase[Biome.values().length];
        CraftBlock.BIOME_MAPPING[BiomeBase.SWAMPLAND.id] = Biome.SWAMPLAND;
        CraftBlock.BIOME_MAPPING[BiomeBase.FOREST.id] = Biome.FOREST;
        CraftBlock.BIOME_MAPPING[BiomeBase.TAIGA.id] = Biome.TAIGA;
        CraftBlock.BIOME_MAPPING[BiomeBase.DESERT.id] = Biome.DESERT;
        CraftBlock.BIOME_MAPPING[BiomeBase.PLAINS.id] = Biome.PLAINS;
        CraftBlock.BIOME_MAPPING[BiomeBase.HELL.id] = Biome.HELL;
        CraftBlock.BIOME_MAPPING[BiomeBase.SKY.id] = Biome.SKY;
        CraftBlock.BIOME_MAPPING[BiomeBase.RIVER.id] = Biome.RIVER;
        CraftBlock.BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id] = Biome.EXTREME_HILLS;
        CraftBlock.BIOME_MAPPING[BiomeBase.OCEAN.id] = Biome.OCEAN;
        CraftBlock.BIOME_MAPPING[BiomeBase.FROZEN_OCEAN.id] = Biome.FROZEN_OCEAN;
        CraftBlock.BIOME_MAPPING[BiomeBase.FROZEN_RIVER.id] = Biome.FROZEN_RIVER;
        CraftBlock.BIOME_MAPPING[BiomeBase.ICE_PLAINS.id] = Biome.ICE_PLAINS;
        CraftBlock.BIOME_MAPPING[BiomeBase.ICE_MOUNTAINS.id] = Biome.ICE_MOUNTAINS;
        CraftBlock.BIOME_MAPPING[BiomeBase.MUSHROOM_ISLAND.id] = Biome.MUSHROOM_ISLAND;
        CraftBlock.BIOME_MAPPING[BiomeBase.MUSHROOM_SHORE.id] = Biome.MUSHROOM_SHORE;
        CraftBlock.BIOME_MAPPING[BiomeBase.BEACH.id] = Biome.BEACH;
        CraftBlock.BIOME_MAPPING[BiomeBase.DESERT_HILLS.id] = Biome.DESERT_HILLS;
        CraftBlock.BIOME_MAPPING[BiomeBase.FOREST_HILLS.id] = Biome.FOREST_HILLS;
        CraftBlock.BIOME_MAPPING[BiomeBase.TAIGA_HILLS.id] = Biome.TAIGA_HILLS;
        CraftBlock.BIOME_MAPPING[BiomeBase.SMALL_MOUNTAINS.id] = Biome.SMALL_MOUNTAINS;
        CraftBlock.BIOME_MAPPING[BiomeBase.JUNGLE.id] = Biome.JUNGLE;
        CraftBlock.BIOME_MAPPING[BiomeBase.JUNGLE_HILLS.id] = Biome.JUNGLE_HILLS;
        for (int i = 0; i < CraftBlock.BIOME_MAPPING.length; ++i) {
            if (BiomeBase.biomes[i] != null && CraftBlock.BIOME_MAPPING[i] == null) {
                throw new IllegalArgumentException("Missing Biome mapping for BiomeBase[" + i + "]");
            }
            if (CraftBlock.BIOME_MAPPING[i] != null) {
                CraftBlock.BIOMEBASE_MAPPING[CraftBlock.BIOME_MAPPING[i].ordinal()] = BiomeBase.biomes[i];
            }
        }
    }
}
