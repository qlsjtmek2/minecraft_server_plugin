// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import java.util.concurrent.Callable;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import java.util.Map;
import org.spigotmc.CustomTimingsHandler;

public class TileEntity
{
    public CustomTimingsHandler tickTimer;
    private static Map a;
    private static Map b;
    protected World world;
    public int x;
    public int y;
    public int z;
    protected boolean o;
    public int p;
    public Block q;
    
    public TileEntity() {
        this.tickTimer = SpigotTimings.getTileEntityTimings(this);
        this.p = -1;
    }
    
    private static void a(final Class oclass, final String s) {
        if (TileEntity.a.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate id: " + s);
        }
        TileEntity.a.put(s, oclass);
        TileEntity.b.put(oclass, s);
    }
    
    public void b(final World world) {
        this.world = world;
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public boolean o() {
        return this.world != null;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.x = nbttagcompound.getInt("x");
        this.y = nbttagcompound.getInt("y");
        this.z = nbttagcompound.getInt("z");
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        final String s = TileEntity.b.get(this.getClass());
        if (s == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        nbttagcompound.setString("id", s);
        nbttagcompound.setInt("x", this.x);
        nbttagcompound.setInt("y", this.y);
        nbttagcompound.setInt("z", this.z);
    }
    
    public void h() {
    }
    
    public static TileEntity c(final NBTTagCompound nbttagcompound) {
        TileEntity tileentity = null;
        try {
            final Class oclass = TileEntity.a.get(nbttagcompound.getString("id"));
            if (oclass != null) {
                tileentity = oclass.newInstance();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (tileentity != null) {
            tileentity.a(nbttagcompound);
        }
        else {
            MinecraftServer.getServer().getLogger().warning("Skipping TileEntity with id " + nbttagcompound.getString("id"));
        }
        return tileentity;
    }
    
    public int p() {
        if (this.p == -1) {
            this.p = this.world.getData(this.x, this.y, this.z);
        }
        return this.p;
    }
    
    public void update() {
        if (this.world != null) {
            this.p = this.world.getData(this.x, this.y, this.z);
            this.world.b(this.x, this.y, this.z, this);
            if (this.q() != null) {
                this.world.m(this.x, this.y, this.z, this.q().id);
            }
        }
    }
    
    public Block q() {
        if (this.q == null) {
            this.q = Block.byId[this.world.getTypeId(this.x, this.y, this.z)];
        }
        return this.q;
    }
    
    public Packet getUpdatePacket() {
        return null;
    }
    
    public boolean r() {
        return this.o;
    }
    
    public void w_() {
        this.o = true;
    }
    
    public void s() {
        this.o = false;
    }
    
    public boolean b(final int i, final int j) {
        return false;
    }
    
    public void i() {
        this.q = null;
        this.p = -1;
    }
    
    public void a(final CrashReportSystemDetails crashreportsystemdetails) {
        crashreportsystemdetails.a("Name", new CrashReportTileEntityName(this));
        CrashReportSystemDetails.a(crashreportsystemdetails, this.x, this.y, this.z, this.q().id, this.p());
        crashreportsystemdetails.a("Actual block type", new CrashReportTileEntityType(this));
        crashreportsystemdetails.a("Actual block data value", new CrashReportTileEntityData(this));
    }
    
    static Map t() {
        return TileEntity.b;
    }
    
    public InventoryHolder getOwner() {
        final BlockState state = this.world.getWorld().getBlockAt(this.x, this.y, this.z).getState();
        if (state instanceof InventoryHolder) {
            return (InventoryHolder)state;
        }
        return null;
    }
    
    static {
        TileEntity.a = new HashMap();
        TileEntity.b = new HashMap();
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityEnderChest.class, "EnderChest");
        a(TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntityDropper.class, "Dropper");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantTable.class, "EnchantTable");
        a(TileEntityEnderPortal.class, "Airportal");
        a(TileEntityCommand.class, "Control");
        a(TileEntityBeacon.class, "Beacon");
        a(TileEntitySkull.class, "Skull");
        a(TileEntityLightDetector.class, "DLDetector");
        a(TileEntityHopper.class, "Hopper");
        a(TileEntityComparator.class, "Comparator");
    }
}
