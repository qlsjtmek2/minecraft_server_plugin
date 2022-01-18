// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.plugin.Plugin;
import java.util.List;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Chunk;
import net.minecraft.server.v1_5_R3.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.BlockState;

public class CraftBlockState implements BlockState
{
    private final CraftWorld world;
    private final CraftChunk chunk;
    private final int x;
    private final int y;
    private final int z;
    protected int type;
    protected MaterialData data;
    protected final byte light;
    
    public CraftBlockState(final Block block) {
        this.world = (CraftWorld)block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.type = block.getTypeId();
        this.light = block.getLightLevel();
        this.chunk = (CraftChunk)block.getChunk();
        this.createData(block.getData());
    }
    
    public static CraftBlockState getBlockState(final World world, final int x, final int y, final int z) {
        return new CraftBlockState(world.getWorld().getBlockAt(x, y, z));
    }
    
    public org.bukkit.World getWorld() {
        return this.world;
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
    
    public void setData(final MaterialData data) {
        final Material mat = this.getType();
        if (mat == null || mat.getData() == null) {
            this.data = data;
        }
        else {
            if (data.getClass() != mat.getData() && data.getClass() != MaterialData.class) {
                throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
            }
            this.data = data;
        }
    }
    
    public MaterialData getData() {
        return this.data;
    }
    
    public void setType(final Material type) {
        this.setTypeId(type.getId());
    }
    
    public boolean setTypeId(final int type) {
        if (this.type != type) {
            this.type = type;
            this.createData((byte)0);
        }
        return true;
    }
    
    public Material getType() {
        return Material.getMaterial(this.getTypeId());
    }
    
    public int getTypeId() {
        return this.type;
    }
    
    public byte getLightLevel() {
        return this.light;
    }
    
    public Block getBlock() {
        return this.world.getBlockAt(this.x, this.y, this.z);
    }
    
    public boolean update() {
        return this.update(false);
    }
    
    public boolean update(final boolean force) {
        return this.update(force, true);
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final Block block = this.getBlock();
        if (block.getType() != this.getType()) {
            if (!force) {
                return false;
            }
            block.setTypeId(this.getTypeId(), applyPhysics);
        }
        block.setData(this.getRawData(), applyPhysics);
        this.world.getHandle().notify(this.x, this.y, this.z);
        return true;
    }
    
    private void createData(final byte data) {
        final Material mat = this.getType();
        if (mat == null || mat.getData() == null) {
            this.data = new MaterialData(this.type, data);
        }
        else {
            this.data = mat.getNewData(data);
        }
    }
    
    public byte getRawData() {
        return this.data.getData();
    }
    
    public Location getLocation() {
        return new Location(this.world, this.x, this.y, this.z);
    }
    
    public Location getLocation(final Location loc) {
        if (loc != null) {
            loc.setWorld(this.world);
            loc.setX(this.x);
            loc.setY(this.y);
            loc.setZ(this.z);
            loc.setYaw(0.0f);
            loc.setPitch(0.0f);
        }
        return loc;
    }
    
    public void setRawData(final byte data) {
        this.data.setData(data);
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final CraftBlockState other = (CraftBlockState)obj;
        return (this.world == other.world || (this.world != null && this.world.equals(other.world))) && this.x == other.x && this.y == other.y && this.z == other.z && this.type == other.type && (this.data == other.data || (this.data != null && this.data.equals(other.data)));
    }
    
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + ((this.world != null) ? this.world.hashCode() : 0);
        hash = 73 * hash + this.x;
        hash = 73 * hash + this.y;
        hash = 73 * hash + this.z;
        hash = 73 * hash + this.type;
        hash = 73 * hash + ((this.data != null) ? this.data.hashCode() : 0);
        return hash;
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        this.chunk.getCraftWorld().getBlockMetadata().setMetadata(this.getBlock(), metadataKey, newMetadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(this.getBlock(), metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(this.getBlock(), metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(this.getBlock(), metadataKey, owningPlugin);
    }
}
