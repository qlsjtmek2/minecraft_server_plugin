// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.OrebfuscatorManager;
import org.bukkit.event.Event;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class PlayerInteractManager
{
    public World world;
    public EntityPlayer player;
    private EnumGamemode gamemode;
    private boolean d;
    private int lastDigTick;
    private int f;
    private int g;
    private int h;
    private int currentTick;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    
    public PlayerInteractManager(final World world) {
        this.gamemode = EnumGamemode.NONE;
        this.o = -1;
        this.world = world;
    }
    
    public void setGameMode(final EnumGamemode enumgamemode) {
        (this.gamemode = enumgamemode).a(this.player.abilities);
        this.player.updateAbilities();
    }
    
    public EnumGamemode getGameMode() {
        return this.gamemode;
    }
    
    public boolean isCreative() {
        return this.gamemode.d();
    }
    
    public void b(final EnumGamemode enumgamemode) {
        if (this.gamemode == EnumGamemode.NONE) {
            this.gamemode = enumgamemode;
        }
        this.setGameMode(this.gamemode);
    }
    
    public void a() {
        this.currentTick = MinecraftServer.currentTick;
        if (this.j) {
            final int i = this.currentTick - this.n;
            final int k = this.world.getTypeId(this.k, this.l, this.m);
            if (k == 0) {
                this.j = false;
            }
            else {
                final Block block = Block.byId[k];
                final float f = block.getDamage(this.player, this.player.world, this.k, this.l, this.m) * (i + 1);
                final int j = (int)(f * 10.0f);
                if (j != this.o) {
                    this.world.f(this.player.id, this.k, this.l, this.m, j);
                    this.o = j;
                }
                if (f >= 1.0f) {
                    this.j = false;
                    this.breakBlock(this.k, this.l, this.m);
                }
            }
        }
        else if (this.d) {
            final int i = this.world.getTypeId(this.f, this.g, this.h);
            final Block block2 = Block.byId[i];
            if (block2 == null) {
                this.world.f(this.player.id, this.f, this.g, this.h, -1);
                this.o = -1;
                this.d = false;
            }
            else {
                final int l = this.currentTick - this.lastDigTick;
                final float f = block2.getDamage(this.player, this.player.world, this.f, this.g, this.h) * (l + 1);
                final int j = (int)(f * 10.0f);
                if (j != this.o) {
                    this.world.f(this.player.id, this.f, this.g, this.h, j);
                    this.o = j;
                }
            }
        }
    }
    
    public void dig(final int i, final int j, final int k, final int l) {
        final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, l, this.player.inventory.getItemInHand());
        if (!this.gamemode.isAdventure() || this.player.e(i, j, k)) {
            if (event.isCancelled()) {
                this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                final TileEntity tileentity = this.world.getTileEntity(i, j, k);
                if (tileentity != null) {
                    this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
                }
                return;
            }
            if (this.isCreative()) {
                if (!this.world.douseFire(null, i, j, k, l)) {
                    this.breakBlock(i, j, k);
                }
            }
            else {
                this.world.douseFire(null, i, j, k, l);
                this.lastDigTick = this.currentTick;
                float f = 1.0f;
                final int i2 = this.world.getTypeId(i, j, k);
                if (event.useInteractedBlock() == Event.Result.DENY) {
                    if (i2 == Block.WOODEN_DOOR.id) {
                        final boolean bottom = (this.world.getData(i, j, k) & 0x8) == 0x0;
                        this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                        this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j + (bottom ? 1 : -1), k, this.world));
                    }
                    else if (i2 == Block.TRAP_DOOR.id) {
                        this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    }
                }
                else if (i2 > 0) {
                    Block.byId[i2].attack(this.world, i, j, k, this.player);
                    this.world.douseFire(null, i, j, k, l);
                }
                if (i2 > 0) {
                    f = Block.byId[i2].getDamage(this.player, this.world, i, j, k);
                }
                if (event.useItemInHand() == Event.Result.DENY) {
                    if (f > 1.0f) {
                        this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    }
                    return;
                }
                final BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, i, j, k, this.player.inventory.getItemInHand(), f >= 1.0f);
                if (blockEvent.isCancelled()) {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    return;
                }
                if (blockEvent.getInstaBreak()) {
                    f = 2.0f;
                }
                if (i2 > 0 && f >= 1.0f) {
                    this.breakBlock(i, j, k);
                }
                else {
                    this.d = true;
                    this.f = i;
                    this.g = j;
                    this.h = k;
                    final int j2 = (int)(f * 10.0f);
                    this.world.f(this.player.id, i, j, k, j2);
                    this.o = j2;
                }
            }
            OrebfuscatorManager.updateNearbyBlocks(this.world, i, j, k);
        }
    }
    
    public void a(final int i, final int j, final int k) {
        if (i == this.f && j == this.g && k == this.h) {
            this.currentTick = MinecraftServer.currentTick;
            final int l = this.currentTick - this.lastDigTick;
            final int i2 = this.world.getTypeId(i, j, k);
            if (i2 != 0) {
                final Block block = Block.byId[i2];
                final float f = block.getDamage(this.player, this.player.world, i, j, k) * (l + 1);
                if (f >= 0.7f) {
                    this.d = false;
                    this.world.f(this.player.id, i, j, k, -1);
                    this.breakBlock(i, j, k);
                }
                else if (!this.j) {
                    this.d = false;
                    this.j = true;
                    this.k = i;
                    this.l = j;
                    this.m = k;
                    this.n = this.lastDigTick;
                }
            }
        }
        else {
            this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
        }
    }
    
    public void c(final int i, final int j, final int k) {
        this.d = false;
        this.world.f(this.player.id, this.f, this.g, this.h, -1);
    }
    
    private boolean d(final int i, final int j, final int k) {
        final Block block = Block.byId[this.world.getTypeId(i, j, k)];
        final int l = this.world.getData(i, j, k);
        if (block != null) {
            block.a(this.world, i, j, k, l, this.player);
        }
        final boolean flag = this.world.setAir(i, j, k);
        if (block != null && flag) {
            block.postBreak(this.world, i, j, k, l);
        }
        return flag;
    }
    
    public boolean breakBlock(final int i, final int j, final int k) {
        BlockBreakEvent event = null;
        if (this.player instanceof EntityPlayer) {
            final org.bukkit.block.Block block = this.world.getWorld().getBlockAt(i, j, k);
            if (this.world.getTileEntity(i, j, k) == null) {
                final Packet53BlockChange packet = new Packet53BlockChange(i, j, k, this.world);
                packet.material = 0;
                packet.data = 0;
                this.player.playerConnection.sendPacket(packet);
            }
            event = new BlockBreakEvent(block, this.player.getBukkitEntity());
            event.setCancelled(this.gamemode.isAdventure() && !this.player.e(i, j, k));
            final Block nmsBlock = Block.byId[block.getTypeId()];
            if (nmsBlock != null && !event.isCancelled() && !this.isCreative() && this.player.a(nmsBlock) && (!nmsBlock.r_() || !EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
                final int data = block.getData();
                final int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
                event.setExpToDrop(nmsBlock.getExpDrop(this.world, data, bonusLevel));
            }
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                final TileEntity tileentity = this.world.getTileEntity(i, j, k);
                if (tileentity != null) {
                    this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
                }
                return false;
            }
        }
        final int l = this.world.getTypeId(i, j, k);
        if (Block.byId[l] == null) {
            return false;
        }
        final int i2 = this.world.getData(i, j, k);
        if (l == Block.SKULL.id && !this.isCreative()) {
            Block.SKULL.dropNaturally(this.world, i, j, k, i2, 1.0f, 0);
            return this.d(i, j, k);
        }
        this.world.a(this.player, 2001, i, j, k, l + (this.world.getData(i, j, k) << 12));
        final boolean flag = this.d(i, j, k);
        if (this.isCreative()) {
            this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, this.world));
        }
        else {
            final ItemStack itemstack = this.player.cd();
            final boolean flag2 = this.player.a(Block.byId[l]);
            if (itemstack != null) {
                itemstack.a(this.world, l, i, j, k, this.player);
                if (itemstack.count == 0) {
                    this.player.ce();
                }
            }
            if (flag && flag2) {
                Block.byId[l].a(this.world, this.player, i, j, k, i2);
            }
        }
        if (flag && event != null) {
            Block.byId[l].j(this.world, i, j, k, event.getExpToDrop());
        }
        return flag;
    }
    
    public boolean useItem(final EntityHuman entityhuman, final World world, final ItemStack itemstack) {
        final int i = itemstack.count;
        final int j = itemstack.getData();
        final ItemStack itemstack2 = itemstack.a(world, entityhuman);
        if (itemstack2 == itemstack && (itemstack2 == null || (itemstack2.count == i && itemstack2.n() <= 0 && itemstack2.getData() == j))) {
            return false;
        }
        entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack2;
        if (this.isCreative()) {
            itemstack2.count = i;
            if (itemstack2.g()) {
                itemstack2.setData(j);
            }
        }
        if (itemstack2.count == 0) {
            entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
        }
        if (!entityhuman.bX()) {
            ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
        }
        return true;
    }
    
    public boolean interact(final EntityHuman entityhuman, final World world, final ItemStack itemstack, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        final int i2 = world.getTypeId(i, j, k);
        boolean result = false;
        if (i2 > 0) {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
            if (event.useInteractedBlock() == Event.Result.DENY) {
                if (i2 == Block.WOODEN_DOOR.id) {
                    final boolean bottom = (world.getData(i, j, k) & 0x8) == 0x0;
                    ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet53BlockChange(i, j + (bottom ? 1 : -1), k, world));
                }
                result = (event.useItemInHand() != Event.Result.ALLOW);
            }
            else if (!entityhuman.isSneaking() || itemstack == null) {
                result = Block.byId[i2].interact(world, i, j, k, entityhuman, l, f, f1, f2);
            }
            if (itemstack != null && !result) {
                final int j2 = itemstack.getData();
                final int k2 = itemstack.count;
                result = itemstack.placeItem(entityhuman, world, i, j, k, l, f, f1, f2);
                if (this.isCreative()) {
                    itemstack.setData(j2);
                    itemstack.count = k2;
                }
            }
            if (itemstack != null && ((!result && event.useItemInHand() != Event.Result.DENY) || event.useItemInHand() == Event.Result.ALLOW)) {
                this.useItem(entityhuman, world, itemstack);
            }
        }
        return result;
    }
    
    public void a(final WorldServer worldserver) {
        this.world = worldserver;
    }
}
