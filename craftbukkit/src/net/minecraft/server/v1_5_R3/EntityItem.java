// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityItem extends Entity
{
    public int age;
    public int pickupDelay;
    private int d;
    public float c;
    private int lastTick;
    
    public EntityItem(final World world, final double d0, final double d1, final double d2) {
        super(world);
        this.lastTick = MinecraftServer.currentTick;
        this.age = 0;
        this.d = 5;
        this.c = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.a(0.25f, 0.25f);
        this.height = this.length / 2.0f;
        this.setPosition(d0, d1, d2);
        this.yaw = (float)(Math.random() * 360.0);
        this.motX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motY = 0.20000000298023224;
        this.motZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntityItem(final World world, final double d0, final double d1, final double d2, final ItemStack itemstack) {
        this(world, d0, d1, d2);
        if (itemstack == null || itemstack.getItem() == null) {
            return;
        }
        this.setItemStack(itemstack);
    }
    
    protected boolean f_() {
        return false;
    }
    
    public EntityItem(final World world) {
        super(world);
        this.lastTick = MinecraftServer.currentTick;
        this.age = 0;
        this.d = 5;
        this.c = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.a(0.25f, 0.25f);
        this.height = this.length / 2.0f;
    }
    
    protected void a() {
        this.getDataWatcher().a(10, 5);
    }
    
    public void l_() {
        super.l_();
        final int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
        this.pickupDelay -= elapsedTicks;
        this.age += elapsedTicks;
        this.lastTick = MinecraftServer.currentTick;
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033;
        this.Z = this.i(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0, this.locZ);
        this.move(this.motX, this.motY, this.motZ);
        final boolean flag = (int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ;
        if (flag || this.ticksLived % 25 == 0) {
            if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
                this.motY = 0.20000000298023224;
                this.motX = (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
                this.motZ = (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
                this.makeSound("random.fizz", 0.4f, 2.0f + this.random.nextFloat() * 0.4f);
            }
            if (!this.world.isStatic) {
                this.g();
            }
        }
        float f = 0.98f;
        if (this.onGround) {
            f = 0.58800006f;
            final int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
            if (i > 0) {
                f = Block.byId[i].frictionFactor * 0.98f;
            }
        }
        this.motX *= f;
        this.motY *= 0.9800000190734863;
        this.motZ *= f;
        if (this.onGround) {
            this.motY *= -0.5;
        }
        if (!this.world.isStatic && this.age >= 6000) {
            if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
                this.age = 0;
                return;
            }
            this.die();
        }
    }
    
    private void g() {
        final double radius = this.world.getWorld().itemMergeRadius;
        for (final EntityItem entityitem : this.world.a(EntityItem.class, this.boundingBox.grow(radius, radius, radius))) {
            this.a(entityitem);
        }
    }
    
    public boolean a(final EntityItem entityitem) {
        if (entityitem == this) {
            return false;
        }
        if (!entityitem.isAlive() || !this.isAlive()) {
            return false;
        }
        final ItemStack itemstack = this.getItemStack();
        final ItemStack itemstack2 = entityitem.getItemStack();
        if (itemstack2.getItem() != itemstack.getItem()) {
            return false;
        }
        if (itemstack2.hasTag() ^ itemstack.hasTag()) {
            return false;
        }
        if (itemstack2.hasTag() && !itemstack2.getTag().equals(itemstack.getTag())) {
            return false;
        }
        if (itemstack2.getItem().m() && itemstack2.getData() != itemstack.getData()) {
            return false;
        }
        if (itemstack2.count < itemstack.count) {
            return entityitem.a(this);
        }
        if (itemstack2.count + itemstack.count > itemstack2.getMaxStackSize()) {
            return false;
        }
        final ItemStack itemStack = itemstack;
        itemStack.count += itemstack2.count;
        this.pickupDelay = Math.max(entityitem.pickupDelay, this.pickupDelay);
        this.age = Math.min(entityitem.age, this.age);
        this.setItemStack(itemstack);
        entityitem.die();
        return true;
    }
    
    public void c() {
        this.age = 4800;
    }
    
    public boolean H() {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }
    
    protected void burn(final int i) {
        this.damageEntity(DamageSource.FIRE, i);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (this.getItemStack() != null && this.getItemStack().id == Item.NETHER_STAR.id && damagesource.c()) {
            return false;
        }
        this.J();
        this.d -= i;
        if (this.d <= 0) {
            this.die();
        }
        return false;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("Health", (byte)this.d);
        nbttagcompound.setShort("Age", (short)this.age);
        if (this.getItemStack() != null) {
            nbttagcompound.setCompound("Item", this.getItemStack().save(new NBTTagCompound()));
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.d = (nbttagcompound.getShort("Health") & 0xFF);
        this.age = nbttagcompound.getShort("Age");
        final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("Item");
        if (nbttagcompound2 != null) {
            final ItemStack itemstack = ItemStack.createStack(nbttagcompound2);
            if (itemstack != null) {
                this.setItemStack(itemstack);
            }
            else {
                this.die();
            }
        }
        else {
            this.die();
        }
        if (this.getItemStack() == null) {
            this.die();
        }
    }
    
    public void b_(final EntityHuman entityhuman) {
        if (!this.world.isStatic) {
            final ItemStack itemstack = this.getItemStack();
            final int i = itemstack.count;
            final int canHold = entityhuman.inventory.canHold(itemstack);
            final int remaining = itemstack.count - canHold;
            if (this.pickupDelay <= 0 && canHold > 0) {
                itemstack.count = canHold;
                final PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), (org.bukkit.entity.Item)this.getBukkitEntity(), remaining);
                event.setCancelled(!entityhuman.canPickUpLoot);
                this.world.getServer().getPluginManager().callEvent(event);
                itemstack.count = canHold + remaining;
                if (event.isCancelled()) {
                    return;
                }
                this.pickupDelay = 0;
            }
            if (this.pickupDelay == 0 && entityhuman.inventory.pickup(itemstack)) {
                if (itemstack.id == Block.LOG.id) {
                    entityhuman.a(AchievementList.g);
                }
                if (itemstack.id == Item.LEATHER.id) {
                    entityhuman.a(AchievementList.t);
                }
                if (itemstack.id == Item.DIAMOND.id) {
                    entityhuman.a(AchievementList.w);
                }
                if (itemstack.id == Item.BLAZE_ROD.id) {
                    entityhuman.a(AchievementList.z);
                }
                this.makeSound("random.pop", 0.2f, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityhuman.receive(this, i);
                if (itemstack.count <= 0) {
                    this.die();
                }
            }
        }
    }
    
    public String getLocalizedName() {
        return LocaleI18n.get("item." + this.getItemStack().a());
    }
    
    public boolean ap() {
        return false;
    }
    
    public void c(final int i) {
        super.c(i);
        if (!this.world.isStatic) {
            this.g();
        }
    }
    
    public ItemStack getItemStack() {
        final ItemStack itemstack = this.getDataWatcher().getItemStack(10);
        if (itemstack == null) {
            if (this.world != null) {
                this.world.getLogger().severe("Item entity " + this.id + " has no item?!");
            }
            return new ItemStack(Block.STONE);
        }
        return itemstack;
    }
    
    public void setItemStack(final ItemStack itemstack) {
        this.getDataWatcher().watch(10, itemstack);
        this.getDataWatcher().h(10);
    }
}
