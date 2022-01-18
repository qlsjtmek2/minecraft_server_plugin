// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityDamageEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import org.spigotmc.OrebfuscatorManager;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityFallingBlock extends Entity
{
    public int id;
    public int data;
    public int c;
    public boolean dropItem;
    private boolean f;
    private boolean hurtEntities;
    private int fallHurtMax;
    private float fallHurtAmount;
    public NBTTagCompound tileEntityData;
    
    public EntityFallingBlock(final World world) {
        super(world);
        this.c = 0;
        this.dropItem = true;
        this.f = false;
        this.hurtEntities = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.tileEntityData = null;
    }
    
    public EntityFallingBlock(final World world, final double d0, final double d1, final double d2, final int i) {
        this(world, d0, d1, d2, i, 0);
    }
    
    public EntityFallingBlock(final World world, final double d0, final double d1, final double d2, final int i, final int j) {
        super(world);
        this.c = 0;
        this.dropItem = true;
        this.f = false;
        this.hurtEntities = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.tileEntityData = null;
        this.id = i;
        this.data = j;
        this.m = true;
        this.a(0.98f, 0.98f);
        this.height = this.length / 2.0f;
        this.setPosition(d0, d1, d2);
        this.motX = 0.0;
        this.motY = 0.0;
        this.motZ = 0.0;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected void a() {
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public void l_() {
        if (this.id == 0) {
            this.die();
        }
        else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            ++this.c;
            this.motY -= 0.03999999910593033;
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.9800000190734863;
            this.motY *= 0.9800000190734863;
            this.motZ *= 0.9800000190734863;
            if (!this.world.isStatic) {
                final int i = MathHelper.floor(this.locX);
                final int j = MathHelper.floor(this.locY);
                final int k = MathHelper.floor(this.locZ);
                if (this.c == 1) {
                    if (this.c != 1 || this.world.getTypeId(i, j, k) != this.id || this.world.getData(i, j, k) != this.data || CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, 0, 0).isCancelled()) {
                        this.die();
                        return;
                    }
                    this.world.setAir(i, j, k);
                    OrebfuscatorManager.updateNearbyBlocks(this.world, i, j, k);
                }
                if (this.onGround) {
                    this.motX *= 0.699999988079071;
                    this.motZ *= 0.699999988079071;
                    this.motY *= -0.5;
                    if (this.world.getTypeId(i, j, k) != Block.PISTON_MOVING.id) {
                        this.die();
                        if (!this.f && this.world.mayPlace(this.id, i, j, k, true, 1, null, null) && !BlockSand.canFall(this.world, i, j - 1, k) && i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && j > 0 && j < 256 && (this.world.getTypeId(i, j, k) != this.id || this.world.getData(i, j, k) != this.data)) {
                            if (CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, this.id, this.data).isCancelled()) {
                                return;
                            }
                            this.world.setTypeIdAndData(i, j, k, this.id, this.data, 3);
                            if (Block.byId[this.id] instanceof BlockSand) {
                                ((BlockSand)Block.byId[this.id]).a_(this.world, i, j, k, this.data);
                            }
                            if (this.tileEntityData != null && Block.byId[this.id] instanceof IContainer) {
                                final TileEntity tileentity = this.world.getTileEntity(i, j, k);
                                if (tileentity != null) {
                                    final NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    tileentity.b(nbttagcompound);
                                    for (final NBTBase nbtbase : this.tileEntityData.c()) {
                                        if (!nbtbase.getName().equals("x") && !nbtbase.getName().equals("y") && !nbtbase.getName().equals("z")) {
                                            nbttagcompound.set(nbtbase.getName(), nbtbase.clone());
                                        }
                                    }
                                    tileentity.a(nbttagcompound);
                                    tileentity.update();
                                }
                            }
                        }
                        else if (this.dropItem && !this.f) {
                            this.a(new ItemStack(this.id, 1, Block.byId[this.id].getDropData(this.data)), 0.0f);
                        }
                    }
                }
                else if ((this.c > 100 && !this.world.isStatic && (j < 1 || j > 256)) || this.c > 600) {
                    if (this.dropItem) {
                        this.a(new ItemStack(this.id, 1, Block.byId[this.id].getDropData(this.data)), 0.0f);
                    }
                    this.die();
                }
            }
        }
    }
    
    protected void a(final float f) {
        if (this.hurtEntities) {
            final int i = MathHelper.f(f - 1.0f);
            if (i > 0) {
                final ArrayList arraylist = new ArrayList(this.world.getEntities(this, this.boundingBox));
                final DamageSource damagesource = (this.id == Block.ANVIL.id) ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                for (final Entity entity : arraylist) {
                    final int damage = Math.min(MathHelper.d(i * this.fallHurtAmount), this.fallHurtMax);
                    final EntityDamageEvent event = CraftEventFactory.callEntityDamageEvent(this, entity, EntityDamageEvent.DamageCause.FALLING_BLOCK, damage);
                    if (event.isCancelled()) {
                        continue;
                    }
                    entity.damageEntity(damagesource, event.getDamage());
                }
                if (this.id == Block.ANVIL.id && this.random.nextFloat() < 0.05000000074505806 + i * 0.05) {
                    int j = this.data >> 2;
                    final int k = this.data & 0x3;
                    if (++j > 2) {
                        this.f = true;
                    }
                    else {
                        this.data = (k | j << 2);
                    }
                }
            }
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("Tile", (byte)this.id);
        nbttagcompound.setInt("TileID", this.id);
        nbttagcompound.setByte("Data", (byte)this.data);
        nbttagcompound.setByte("Time", (byte)this.c);
        nbttagcompound.setBoolean("DropItem", this.dropItem);
        nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
        nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            nbttagcompound.setCompound("TileEntityData", this.tileEntityData);
        }
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("TileID")) {
            this.id = nbttagcompound.getInt("TileID");
        }
        else {
            this.id = (nbttagcompound.getByte("Tile") & 0xFF);
        }
        this.data = (nbttagcompound.getByte("Data") & 0xFF);
        this.c = (nbttagcompound.getByte("Time") & 0xFF);
        if (nbttagcompound.hasKey("HurtEntities")) {
            this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
            this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
            this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
        }
        else if (this.id == Block.ANVIL.id) {
            this.hurtEntities = true;
        }
        if (nbttagcompound.hasKey("DropItem")) {
            this.dropItem = nbttagcompound.getBoolean("DropItem");
        }
        if (nbttagcompound.hasKey("TileEntityData")) {
            this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
        }
        if (nbttagcompound.hasKey("Bukkit.tileData")) {
            this.tileEntityData = (NBTTagCompound)nbttagcompound.getCompound("Bukkit.tileData").clone();
        }
        if (this.id == 0) {
            this.id = Block.SAND.id;
        }
    }
    
    public void a(final boolean flag) {
        this.hurtEntities = flag;
    }
    
    public void a(final CrashReportSystemDetails crashreportsystemdetails) {
        super.a(crashreportsystemdetails);
        crashreportsystemdetails.a("Immitating block ID", this.id);
        crashreportsystemdetails.a("Immitating block data", this.data);
    }
}
