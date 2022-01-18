// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.Random;

public final class ItemStack
{
    public int count;
    public int b;
    public int id;
    public NBTTagCompound tag;
    private int damage;
    private EntityItemFrame f;
    
    public ItemStack(final Block block) {
        this(block, 1);
    }
    
    public ItemStack(final Block block, final int i) {
        this(block.id, i, 0);
    }
    
    public ItemStack(final Block block, final int i, final int j) {
        this(block.id, i, j);
    }
    
    public ItemStack(final Item item) {
        this(item.id, 1, 0);
    }
    
    public ItemStack(final Item item, final int i) {
        this(item.id, i, 0);
    }
    
    public ItemStack(final Item item, final int i, final int j) {
        this(item.id, i, j);
    }
    
    public ItemStack(final int i, final int j, final int k) {
        this.count = 0;
        this.f = null;
        this.id = i;
        this.count = j;
        this.setData(k);
    }
    
    public static ItemStack createStack(final NBTTagCompound nbttagcompound) {
        final ItemStack itemstack = new ItemStack();
        itemstack.c(nbttagcompound);
        return (itemstack.getItem() != null) ? itemstack : null;
    }
    
    private ItemStack() {
        this.count = 0;
        this.f = null;
    }
    
    public ItemStack a(final int i) {
        final ItemStack itemstack = new ItemStack(this.id, i, this.damage);
        if (this.tag != null) {
            itemstack.tag = (NBTTagCompound)this.tag.clone();
        }
        this.count -= i;
        return itemstack;
    }
    
    public Item getItem() {
        return Item.byId[this.id];
    }
    
    public boolean placeItem(final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        final boolean flag = this.getItem().interactWith(this, entityhuman, world, i, j, k, l, f, f1, f2);
        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }
        return flag;
    }
    
    public float a(final Block block) {
        return this.getItem().getDestroySpeed(this, block);
    }
    
    public ItemStack a(final World world, final EntityHuman entityhuman) {
        return this.getItem().a(this, world, entityhuman);
    }
    
    public ItemStack b(final World world, final EntityHuman entityhuman) {
        return this.getItem().b(this, world, entityhuman);
    }
    
    public NBTTagCompound save(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("id", (short)this.id);
        nbttagcompound.setByte("Count", (byte)this.count);
        nbttagcompound.setShort("Damage", (short)this.damage);
        if (this.tag != null) {
            nbttagcompound.set("tag", this.tag.clone());
        }
        return nbttagcompound;
    }
    
    public void c(final NBTTagCompound nbttagcompound) {
        this.id = nbttagcompound.getShort("id");
        this.count = nbttagcompound.getByte("Count");
        this.damage = nbttagcompound.getShort("Damage");
        if (this.damage < 0) {
            this.damage = 0;
        }
        if (nbttagcompound.hasKey("tag")) {
            this.tag = (NBTTagCompound)nbttagcompound.getCompound("tag").clone().setName("");
        }
    }
    
    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }
    
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.g() || !this.i());
    }
    
    public boolean g() {
        return Item.byId[this.id].getMaxDurability() > 0;
    }
    
    public boolean usesData() {
        return Item.byId[this.id].m();
    }
    
    public boolean i() {
        return this.g() && this.damage > 0;
    }
    
    public int j() {
        return this.damage;
    }
    
    public int getData() {
        return this.damage;
    }
    
    public void setData(int i) {
        if (i == 32767) {
            this.damage = i;
            return;
        }
        if (!this.usesData() && !Item.byId[this.id].usesDurability() && this.id <= 255) {
            i = 0;
        }
        if (this.id == Block.WOOL.id) {
            i = Math.min(15, i);
        }
        this.damage = i;
        if (this.damage < -1) {
            this.damage = 0;
        }
    }
    
    public int l() {
        return Item.byId[this.id].getMaxDurability();
    }
    
    public boolean isDamaged(final int i, final Random random) {
        return this.isDamaged(i, random, null);
    }
    
    public boolean isDamaged(int i, final Random random, final EntityLiving entityliving) {
        if (!this.g()) {
            return false;
        }
        if (i > 0) {
            final int j = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
            int k = 0;
            for (int l = 0; j > 0 && l < i; ++l) {
                if (EnchantmentDurability.a(this, j, random)) {
                    ++k;
                }
            }
            i -= k;
            if (entityliving instanceof EntityPlayer) {
                final CraftItemStack item = CraftItemStack.asCraftMirror(this);
                final PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player)entityliving.getBukkitEntity(), item, i);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return false;
                }
                i = event.getDamage();
            }
            if (i <= 0) {
                return false;
            }
        }
        this.damage += i;
        return this.damage > this.l();
    }
    
    public void damage(final int i, final EntityLiving entityliving) {
        if ((!(entityliving instanceof EntityHuman) || !((EntityHuman)entityliving).abilities.canInstantlyBuild) && this.g() && this.isDamaged(i, entityliving.aE(), entityliving)) {
            entityliving.a(this);
            if (entityliving instanceof EntityHuman) {
                ((EntityHuman)entityliving).a(StatisticList.F[this.id], 1);
            }
            --this.count;
            if (this.count < 0) {
                this.count = 0;
            }
            if (this.count == 0 && entityliving instanceof EntityHuman) {
                CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)entityliving, this);
            }
            this.damage = 0;
        }
    }
    
    public void a(final EntityLiving entityliving, final EntityHuman entityhuman) {
        final boolean flag = Item.byId[this.id].a(this, entityliving, entityhuman);
        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final int l, final EntityHuman entityhuman) {
        final boolean flag = Item.byId[this.id].a(this, world, i, j, k, l, entityhuman);
        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }
    }
    
    public int a(final Entity entity) {
        return Item.byId[this.id].a(entity);
    }
    
    public boolean b(final Block block) {
        return Item.byId[this.id].canDestroySpecialBlock(block);
    }
    
    public boolean a(final EntityLiving entityliving) {
        return Item.byId[this.id].a(this, entityliving);
    }
    
    public ItemStack cloneItemStack() {
        final ItemStack itemstack = new ItemStack(this.id, this.count, this.damage);
        if (this.tag != null) {
            itemstack.tag = (NBTTagCompound)this.tag.clone();
        }
        return itemstack;
    }
    
    public static boolean equals(final ItemStack itemstack, final ItemStack itemstack1) {
        return (itemstack == null && itemstack1 == null) || (itemstack != null && itemstack1 != null && (itemstack.tag != null || itemstack1.tag == null) && (itemstack.tag == null || itemstack.tag.equals(itemstack1.tag)));
    }
    
    public static boolean matches(final ItemStack itemstack, final ItemStack itemstack1) {
        return (itemstack == null && itemstack1 == null) || (itemstack != null && itemstack1 != null && itemstack.d(itemstack1));
    }
    
    private boolean d(final ItemStack itemstack) {
        return this.count == itemstack.count && this.id == itemstack.id && this.damage == itemstack.damage && (this.tag != null || itemstack.tag == null) && (this.tag == null || this.tag.equals(itemstack.tag));
    }
    
    public boolean doMaterialsMatch(final ItemStack itemstack) {
        return this.id == itemstack.id && this.damage == itemstack.damage;
    }
    
    public String a() {
        return Item.byId[this.id].d(this);
    }
    
    public static ItemStack b(final ItemStack itemstack) {
        return (itemstack == null) ? null : itemstack.cloneItemStack();
    }
    
    public String toString() {
        return this.count + "x" + Item.byId[this.id].getName() + "@" + this.damage;
    }
    
    public void a(final World world, final Entity entity, final int i, final boolean flag) {
        if (this.b > 0) {
            --this.b;
        }
        Item.byId[this.id].a(this, world, entity, i, flag);
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i) {
        entityhuman.a(StatisticList.D[this.id], i);
        Item.byId[this.id].d(this, world, entityhuman);
    }
    
    public int n() {
        return this.getItem().c_(this);
    }
    
    public EnumAnimation o() {
        return this.getItem().b_(this);
    }
    
    public void b(final World world, final EntityHuman entityhuman, final int i) {
        this.getItem().a(this, world, entityhuman, i);
    }
    
    public boolean hasTag() {
        return this.tag != null;
    }
    
    public NBTTagCompound getTag() {
        return this.tag;
    }
    
    public NBTTagList getEnchantments() {
        return (this.tag == null) ? null : ((NBTTagList)this.tag.get("ench"));
    }
    
    public void setTag(final NBTTagCompound nbttagcompound) {
        this.tag = nbttagcompound;
    }
    
    public String getName() {
        String s = this.getItem().l(this);
        if (this.tag != null && this.tag.hasKey("display")) {
            final NBTTagCompound nbttagcompound = this.tag.getCompound("display");
            if (nbttagcompound.hasKey("Name")) {
                s = nbttagcompound.getString("Name");
            }
        }
        return s;
    }
    
    public void c(final String s) {
        if (this.tag == null) {
            this.tag = new NBTTagCompound("tag");
        }
        if (!this.tag.hasKey("display")) {
            this.tag.setCompound("display", new NBTTagCompound());
        }
        this.tag.getCompound("display").setString("Name", s);
    }
    
    public boolean hasName() {
        return this.tag != null && this.tag.hasKey("display") && this.tag.getCompound("display").hasKey("Name");
    }
    
    public boolean w() {
        return this.getItem().d_(this) && !this.hasEnchantments();
    }
    
    public void addEnchantment(final Enchantment enchantment, final int i) {
        if (this.tag == null) {
            this.setTag(new NBTTagCompound());
        }
        if (!this.tag.hasKey("ench")) {
            this.tag.set("ench", new NBTTagList("ench"));
        }
        final NBTTagList nbttaglist = (NBTTagList)this.tag.get("ench");
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short)enchantment.id);
        nbttagcompound.setShort("lvl", (byte)i);
        nbttaglist.add(nbttagcompound);
    }
    
    public boolean hasEnchantments() {
        return this.tag != null && this.tag.hasKey("ench");
    }
    
    public void a(final String s, final NBTBase nbtbase) {
        if (this.tag == null) {
            this.setTag(new NBTTagCompound());
        }
        this.tag.set(s, nbtbase);
    }
    
    public boolean y() {
        return this.getItem().y();
    }
    
    public boolean z() {
        return this.f != null;
    }
    
    public void a(final EntityItemFrame entityitemframe) {
        this.f = entityitemframe;
    }
    
    public EntityItemFrame A() {
        return this.f;
    }
    
    public int getRepairCost() {
        return (this.hasTag() && this.tag.hasKey("RepairCost")) ? this.tag.getInt("RepairCost") : 0;
    }
    
    public void setRepairCost(final int i) {
        if (!this.hasTag()) {
            this.tag = new NBTTagCompound("tag");
        }
        this.tag.setInt("RepairCost", i);
    }
}
