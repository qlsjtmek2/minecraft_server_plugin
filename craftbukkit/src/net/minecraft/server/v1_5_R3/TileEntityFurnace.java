// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class TileEntityFurnace extends TileEntity implements IWorldInventory
{
    private static final int[] d;
    private static final int[] e;
    private static final int[] f;
    private ItemStack[] items;
    public int burnTime;
    public int ticksForCurrentFuel;
    public int cookTime;
    private String h;
    private int lastTick;
    private int maxStack;
    public List<HumanEntity> transaction;
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.transaction.add(who);
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public TileEntityFurnace() {
        this.items = new ItemStack[3];
        this.burnTime = 0;
        this.ticksForCurrentFuel = 0;
        this.cookTime = 0;
        this.lastTick = MinecraftServer.currentTick;
        this.maxStack = 64;
        this.transaction = new ArrayList<HumanEntity>();
    }
    
    public int getSize() {
        return this.items.length;
    }
    
    public ItemStack getItem(final int i) {
        return this.items[i];
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.items[i] == null) {
            return null;
        }
        if (this.items[i].count <= j) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        final ItemStack itemstack = this.items[i].a(j);
        if (this.items[i].count == 0) {
            this.items[i] = null;
        }
        return itemstack;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.items[i] != null) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
    }
    
    public String getName() {
        return this.c() ? this.h : "container.furnace";
    }
    
    public boolean c() {
        return this.h != null && this.h.length() > 0;
    }
    
    public void a(final String s) {
        this.h = s;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Items");
        this.items = new ItemStack[this.getSize()];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final byte b0 = nbttagcompound2.getByte("Slot");
            if (b0 >= 0 && b0 < this.items.length) {
                this.items[b0] = ItemStack.createStack(nbttagcompound2);
            }
        }
        this.burnTime = nbttagcompound.getShort("BurnTime");
        this.cookTime = nbttagcompound.getShort("CookTime");
        this.ticksForCurrentFuel = fuelTime(this.items[1]);
        if (nbttagcompound.hasKey("CustomName")) {
            this.h = nbttagcompound.getString("CustomName");
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("BurnTime", (short)this.burnTime);
        nbttagcompound.setShort("CookTime", (short)this.cookTime);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)i);
                this.items[i].save(nbttagcompound2);
                nbttaglist.add(nbttagcompound2);
            }
        }
        nbttagcompound.set("Items", nbttaglist);
        if (this.c()) {
            nbttagcompound.setString("CustomName", this.h);
        }
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public boolean isBurning() {
        return this.burnTime > 0;
    }
    
    public void h() {
        final boolean flag = this.burnTime > 0;
        boolean flag2 = false;
        final int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
        this.lastTick = MinecraftServer.currentTick;
        if (this.isBurning() && this.canBurn()) {
            this.cookTime += elapsedTicks;
            if (this.cookTime >= 200) {
                this.cookTime %= 200;
                this.burn();
                flag2 = true;
            }
        }
        else {
            this.cookTime = 0;
        }
        if (this.burnTime > 0) {
            this.burnTime -= elapsedTicks;
        }
        if (!this.world.isStatic) {
            if (this.burnTime <= 0 && this.canBurn() && this.items[1] != null) {
                final CraftItemStack fuel = CraftItemStack.asCraftMirror(this.items[1]);
                final FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), fuel, fuelTime(this.items[1]));
                this.world.getServer().getPluginManager().callEvent(furnaceBurnEvent);
                if (furnaceBurnEvent.isCancelled()) {
                    return;
                }
                this.ticksForCurrentFuel = furnaceBurnEvent.getBurnTime();
                this.burnTime += this.ticksForCurrentFuel;
                if (this.burnTime > 0 && furnaceBurnEvent.isBurning()) {
                    flag2 = true;
                    if (this.items[1] != null) {
                        final ItemStack itemStack = this.items[1];
                        --itemStack.count;
                        if (this.items[1].count == 0) {
                            final Item item = this.items[1].getItem().s();
                            this.items[1] = ((item != null) ? new ItemStack(item) : null);
                        }
                    }
                }
            }
            if (flag != this.burnTime > 0) {
                flag2 = true;
                BlockFurnace.a(this.burnTime > 0, this.world, this.x, this.y, this.z);
            }
        }
        if (flag2) {
            this.update();
        }
    }
    
    private boolean canBurn() {
        if (this.items[0] == null) {
            return false;
        }
        final ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0].getItem().id);
        return itemstack != null && (this.items[2] == null || (this.items[2].doMaterialsMatch(itemstack) && ((this.items[2].count + itemstack.count <= this.getMaxStackSize() && this.items[2].count < this.items[2].getMaxStackSize()) || this.items[2].count + itemstack.count <= itemstack.getMaxStackSize())));
    }
    
    public void burn() {
        if (this.canBurn()) {
            ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0].getItem().id);
            final CraftItemStack source = CraftItemStack.asCraftMirror(this.items[0]);
            final CraftItemStack result = CraftItemStack.asCraftMirror(itemstack.cloneItemStack());
            final FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), source, result);
            this.world.getServer().getPluginManager().callEvent(furnaceSmeltEvent);
            if (furnaceSmeltEvent.isCancelled()) {
                return;
            }
            itemstack = CraftItemStack.asNMSCopy(furnaceSmeltEvent.getResult());
            if (this.items[2] == null) {
                this.items[2] = itemstack.cloneItemStack();
            }
            else if (this.items[2].id == itemstack.id && this.items[2].getData() == itemstack.getData()) {
                final ItemStack itemStack = this.items[2];
                itemStack.count += itemstack.count;
            }
            final ItemStack itemStack2 = this.items[0];
            --itemStack2.count;
            if (this.items[0].count <= 0) {
                this.items[0] = null;
            }
        }
    }
    
    public static int fuelTime(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        final int i = itemstack.getItem().id;
        final Item item = itemstack.getItem();
        if (i < 256 && Block.byId[i] != null) {
            final Block block = Block.byId[i];
            if (block == Block.WOOD_STEP) {
                return 150;
            }
            if (block.material == Material.WOOD) {
                return 300;
            }
        }
        return (item instanceof ItemTool && ((ItemTool)item).g().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).h().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).g().equals("WOOD")) ? 200 : ((i == Item.STICK.id) ? 100 : ((i == Item.COAL.id) ? 1600 : ((i == Item.LAVA_BUCKET.id) ? 20000 : ((i == Block.SAPLING.id) ? 100 : ((i == Item.BLAZE_ROD.id) ? 2400 : 0)))))));
    }
    
    public static boolean isFuel(final ItemStack itemstack) {
        return fuelTime(itemstack) > 0;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.world.getTileEntity(this.x, this.y, this.z) == this && entityhuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return i != 2 && (i != 1 || isFuel(itemstack));
    }
    
    public int[] getSlotsForFace(final int i) {
        return (i == 0) ? TileEntityFurnace.e : ((i == 1) ? TileEntityFurnace.d : TileEntityFurnace.f);
    }
    
    public boolean canPlaceItemThroughFace(final int i, final ItemStack itemstack, final int j) {
        return this.b(i, itemstack);
    }
    
    public boolean canTakeItemThroughFace(final int i, final ItemStack itemstack, final int j) {
        return j != 0 || i != 1 || itemstack.id == Item.BUCKET.id;
    }
    
    static {
        d = new int[] { 0 };
        e = new int[] { 2, 1 };
        f = new int[] { 1 };
    }
}
