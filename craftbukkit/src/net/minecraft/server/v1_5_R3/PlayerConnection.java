// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.block.Block;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.command.CommandException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerChatEvent;
import java.util.Set;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.craftbukkit.v1_5_R3.util.LazyPlayerSet;
import java.util.concurrent.ExecutionException;
import org.bukkit.craftbukkit.v1_5_R3.util.Waitable;
import org.bukkit.event.player.PlayerItemHeldEvent;
import java.util.Iterator;
import java.util.concurrent.Callable;
import org.bukkit.craftbukkit.v1_5_R3.TextWrapper;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import java.util.HashSet;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.Random;

public class PlayerConnection extends Connection
{
    public final INetworkManager networkManager;
    private final MinecraftServer minecraftServer;
    public boolean disconnected;
    public EntityPlayer player;
    private int e;
    private int f;
    private boolean g;
    private int h;
    private long i;
    private static Random j;
    private long k;
    private volatile int chatThrottle;
    private static final AtomicIntegerFieldUpdater chatSpamField;
    private int x;
    private double y;
    private double z;
    private double p;
    public boolean checkMovement;
    private IntHashMap r;
    private final CraftServer server;
    private int lastTick;
    private int lastDropTick;
    private int dropCount;
    private static final int PLACE_DISTANCE_SQUARED = 36;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private float lastPitch;
    private float lastYaw;
    private boolean justTeleported;
    Long lastPacket;
    private int lastMaterial;
    private static final HashSet<Integer> invalidItems;
    
    public PlayerConnection(final MinecraftServer minecraftserver, final INetworkManager inetworkmanager, final EntityPlayer entityplayer) {
        this.disconnected = false;
        this.chatThrottle = 0;
        this.x = 0;
        this.checkMovement = true;
        this.r = new IntHashMap();
        this.lastTick = MinecraftServer.currentTick;
        this.lastDropTick = MinecraftServer.currentTick;
        this.dropCount = 0;
        this.lastPosX = Double.MAX_VALUE;
        this.lastPosY = Double.MAX_VALUE;
        this.lastPosZ = Double.MAX_VALUE;
        this.lastPitch = Float.MAX_VALUE;
        this.lastYaw = Float.MAX_VALUE;
        this.justTeleported = false;
        this.minecraftServer = minecraftserver;
        (this.networkManager = inetworkmanager).a(this);
        this.player = entityplayer;
        entityplayer.playerConnection = this;
        this.server = minecraftserver.server;
    }
    
    public CraftPlayer getPlayer() {
        return (this.player == null) ? null : this.player.getBukkitEntity();
    }
    
    public void d() {
        this.g = false;
        ++this.e;
        this.minecraftServer.methodProfiler.a("packetflow");
        this.networkManager.b();
        this.minecraftServer.methodProfiler.c("keepAlive");
        if (this.e - this.k > 20L) {
            this.k = this.e;
            this.i = System.nanoTime() / 1000000L;
            this.h = PlayerConnection.j.nextInt();
            this.sendPacket(new Packet0KeepAlive(this.h));
        }
        int spam;
        while ((spam = this.chatThrottle) > 0 && !PlayerConnection.chatSpamField.compareAndSet(this, spam, spam - 1)) {}
        if (this.x > 0) {
            --this.x;
        }
        this.minecraftServer.methodProfiler.c("playerTick");
        this.minecraftServer.methodProfiler.b();
    }
    
    public void disconnect(String s) {
        if (!this.disconnected) {
            String leaveMessage = EnumChatFormat.YELLOW + this.player.name + " left the game.";
            final PlayerKickEvent event = new PlayerKickEvent(this.server.getPlayer(this.player), s, leaveMessage);
            if (this.server.getServer().isRunning()) {
                this.server.getPluginManager().callEvent(event);
            }
            if (event.isCancelled()) {
                return;
            }
            s = event.getReason();
            this.player.k();
            this.sendPacket(new Packet255KickDisconnect(s));
            this.networkManager.d();
            leaveMessage = event.getLeaveMessage();
            if (leaveMessage != null && leaveMessage.length() > 0) {
                this.minecraftServer.getPlayerList().sendAll(new Packet3Chat(leaveMessage));
            }
            this.minecraftServer.getPlayerList().disconnect(this.player);
            this.disconnected = true;
        }
    }
    
    public void a(final Packet10Flying packet10flying) {
        final WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        this.g = true;
        if (!this.player.viewingCredits) {
            if (!this.checkMovement) {
                final double d0 = packet10flying.y - this.z;
                if (packet10flying.x == this.y && d0 * d0 < 0.01 && packet10flying.z == this.p) {
                    this.checkMovement = true;
                }
            }
            final Player player = this.getPlayer();
            final Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
            final Location to = player.getLocation().clone();
            if (packet10flying.hasPos && (!packet10flying.hasPos || packet10flying.y != -999.0 || packet10flying.stance != -999.0)) {
                to.setX(packet10flying.x);
                to.setY(packet10flying.y);
                to.setZ(packet10flying.z);
            }
            if (packet10flying.hasLook) {
                to.setYaw(packet10flying.yaw);
                to.setPitch(packet10flying.pitch);
            }
            final double delta = Math.pow(this.lastPosX - to.getX(), 2.0) + Math.pow(this.lastPosY - to.getY(), 2.0) + Math.pow(this.lastPosZ - to.getZ(), 2.0);
            final float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
            if ((delta > 0.00390625 || deltaAngle > 10.0f) && this.checkMovement && !this.player.dead) {
                this.lastPosX = to.getX();
                this.lastPosY = to.getY();
                this.lastPosZ = to.getZ();
                this.lastYaw = to.getYaw();
                this.lastPitch = to.getPitch();
                if (from.getX() != Double.MAX_VALUE) {
                    final PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
                    this.server.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        this.player.playerConnection.sendPacket(new Packet13PlayerLookMove(from.getX(), from.getY() + 1.6200000047683716, from.getY(), from.getZ(), from.getYaw(), from.getPitch(), false));
                        return;
                    }
                    if (!to.equals(event.getTo()) && !event.isCancelled()) {
                        this.player.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                        return;
                    }
                    if (!from.equals(this.getPlayer().getLocation()) && this.justTeleported) {
                        this.justTeleported = false;
                        return;
                    }
                }
            }
            if (Double.isNaN(packet10flying.x) || Double.isNaN(packet10flying.y) || Double.isNaN(packet10flying.z) || Double.isNaN(packet10flying.stance)) {
                player.teleport(player.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                System.err.println(player.getName() + " was caught trying to crash the server with an invalid position.");
                player.kickPlayer("Nope!");
                return;
            }
            if (this.checkMovement && !this.player.dead) {
                if (this.player.vehicle != null) {
                    float f = this.player.yaw;
                    float f2 = this.player.pitch;
                    this.player.vehicle.U();
                    final double d2 = this.player.locX;
                    final double d3 = this.player.locY;
                    final double d4 = this.player.locZ;
                    double d5 = 0.0;
                    double d6 = 0.0;
                    if (packet10flying.hasLook) {
                        f = packet10flying.yaw;
                        f2 = packet10flying.pitch;
                    }
                    if (packet10flying.hasPos && packet10flying.y == -999.0 && packet10flying.stance == -999.0) {
                        if (Math.abs(packet10flying.x) > 1.0 || Math.abs(packet10flying.z) > 1.0) {
                            System.err.println(this.player.name + " was caught trying to crash the server with an invalid position.");
                            this.disconnect("Nope!");
                            return;
                        }
                        d5 = packet10flying.x;
                        d6 = packet10flying.z;
                    }
                    this.player.onGround = packet10flying.g;
                    this.player.g();
                    this.player.move(d5, 0.0, d6);
                    this.player.setLocation(d2, d3, d4, f, f2);
                    this.player.motX = d5;
                    this.player.motZ = d6;
                    if (this.player.vehicle != null) {
                        worldserver.vehicleEnteredWorld(this.player.vehicle, true);
                    }
                    if (this.player.vehicle != null) {
                        this.player.vehicle.U();
                    }
                    this.minecraftServer.getPlayerList().d(this.player);
                    this.y = this.player.locX;
                    this.z = this.player.locY;
                    this.p = this.player.locZ;
                    worldserver.playerJoinedWorld(this.player);
                    return;
                }
                if (this.player.isSleeping()) {
                    this.player.g();
                    this.player.setLocation(this.y, this.z, this.p, this.player.yaw, this.player.pitch);
                    worldserver.playerJoinedWorld(this.player);
                    return;
                }
                final double d0 = this.player.locY;
                this.y = this.player.locX;
                this.z = this.player.locY;
                this.p = this.player.locZ;
                double d2 = this.player.locX;
                double d3 = this.player.locY;
                double d4 = this.player.locZ;
                float f3 = this.player.yaw;
                float f4 = this.player.pitch;
                if (packet10flying.hasPos && packet10flying.y == -999.0 && packet10flying.stance == -999.0) {
                    packet10flying.hasPos = false;
                }
                if (packet10flying.hasPos) {
                    d2 = packet10flying.x;
                    d3 = packet10flying.y;
                    d4 = packet10flying.z;
                    final double d6 = packet10flying.stance - packet10flying.y;
                    if (!this.player.isSleeping() && (d6 > 1.65 || d6 < 0.1)) {
                        this.disconnect("Illegal stance");
                        this.minecraftServer.getLogger().warning(this.player.name + " had an illegal stance: " + d6);
                        return;
                    }
                    if (Math.abs(packet10flying.x) > 3.2E7 || Math.abs(packet10flying.z) > 3.2E7) {
                        this.a(this.y, this.z, this.p, this.player.yaw, this.player.pitch);
                        return;
                    }
                }
                if (packet10flying.hasLook) {
                    f3 = packet10flying.yaw;
                    f4 = packet10flying.pitch;
                }
                this.player.g();
                this.player.X = 0.0f;
                this.player.setLocation(this.y, this.z, this.p, f3, f4);
                if (!this.checkMovement) {
                    return;
                }
                double d6 = d2 - this.player.locX;
                double d7 = d3 - this.player.locY;
                double d8 = d4 - this.player.locZ;
                final double d9 = Math.max(Math.abs(d6), Math.abs(this.player.motX));
                final double d10 = Math.max(Math.abs(d7), Math.abs(this.player.motY));
                final double d11 = Math.max(Math.abs(d8), Math.abs(this.player.motZ));
                double d12 = d9 * d9 + d10 * d10 + d11 * d11;
                if (d12 > 100.0 && this.checkMovement && (!this.minecraftServer.I() || !this.minecraftServer.H().equals(this.player.name))) {
                    this.minecraftServer.getLogger().warning(this.player.name + " moved too quickly! " + d6 + "," + d7 + "," + d8 + " (" + d9 + ", " + d10 + ", " + d11 + ")");
                    this.a(this.y, this.z, this.p, this.player.yaw, this.player.pitch);
                    return;
                }
                final float f5 = 0.0625f;
                final boolean flag = worldserver.getCubes(this.player, this.player.boundingBox.clone().shrink(f5, f5, f5)).isEmpty();
                if (this.player.onGround && !packet10flying.g && d7 > 0.0) {
                    this.player.j(0.2f);
                }
                this.player.move(d6, d7, d8);
                this.player.onGround = packet10flying.g;
                this.player.checkMovement(d6, d7, d8);
                final double d13 = d7;
                d6 = d2 - this.player.locX;
                d7 = d3 - this.player.locY;
                if (d7 > -0.5 || d7 < 0.5) {
                    d7 = 0.0;
                }
                d8 = d4 - this.player.locZ;
                d12 = d6 * d6 + d7 * d7 + d8 * d8;
                boolean flag2 = false;
                if (d12 > 0.0625 && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative()) {
                    flag2 = true;
                    this.minecraftServer.getLogger().warning(this.player.name + " moved wrongly!");
                }
                this.player.setLocation(d2, d3, d4, f3, f4);
                final boolean flag3 = worldserver.getCubes(this.player, this.player.boundingBox.clone().shrink(f5, f5, f5)).isEmpty();
                if (flag && (flag2 || !flag3) && !this.player.isSleeping()) {
                    this.a(this.y, this.z, this.p, f3, f4);
                    return;
                }
                final AxisAlignedBB axisalignedbb = this.player.boundingBox.clone().grow(f5, f5, f5).a(0.0, -0.55, 0.0);
                if (!this.minecraftServer.getAllowFlight() && !this.player.abilities.canFly && !worldserver.c(axisalignedbb)) {
                    if (d13 >= -0.03125) {
                        ++this.f;
                        if (this.f > 80) {
                            this.minecraftServer.getLogger().warning(this.player.name + " was kicked for floating too long!");
                            this.disconnect("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else {
                    this.f = 0;
                }
                this.player.onGround = packet10flying.g;
                this.minecraftServer.getPlayerList().d(this.player);
                if (this.player.playerInteractManager.isCreative()) {
                    return;
                }
                this.player.b(this.player.locY - d0, packet10flying.g);
            }
        }
    }
    
    public void a(final double d0, final double d1, final double d2, final float f, final float f1) {
        final Player player = this.getPlayer();
        Location from = player.getLocation();
        Location to = new Location(this.getPlayer().getWorld(), d0, d1, d2, f, f1);
        final PlayerTeleportEvent event = new PlayerTeleportEvent(player, from, to, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        this.server.getPluginManager().callEvent(event);
        from = event.getFrom();
        to = (event.isCancelled() ? from : event.getTo());
        this.teleport(to);
    }
    
    public void teleport(final Location dest) {
        final double d0 = dest.getX();
        final double d2 = dest.getY();
        final double d3 = dest.getZ();
        float f = dest.getYaw();
        float f2 = dest.getPitch();
        if (Float.isNaN(f)) {
            f = 0.0f;
        }
        if (Float.isNaN(f2)) {
            f2 = 0.0f;
        }
        this.lastPosX = d0;
        this.lastPosY = d2;
        this.lastPosZ = d3;
        this.lastYaw = f;
        this.lastPitch = f2;
        this.justTeleported = true;
        this.checkMovement = false;
        this.y = d0;
        this.z = d2;
        this.p = d3;
        this.player.setLocation(d0, d2, d3, f, f2);
        this.player.playerConnection.sendPacket(new Packet13PlayerLookMove(d0, d2 + 1.6200000047683716, d2, d3, f, f2, false));
    }
    
    public void a(final Packet14BlockDig packet14blockdig) {
        if (this.player.dead) {
            return;
        }
        final WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        if (packet14blockdig.e == 4) {
            if (this.lastDropTick != MinecraftServer.currentTick) {
                this.dropCount = 0;
                this.lastDropTick = MinecraftServer.currentTick;
            }
            else {
                ++this.dropCount;
                if (this.dropCount >= 20) {
                    this.minecraftServer.getLogger().warning(this.player.name + " dropped their items too quickly!");
                    this.disconnect("You dropped your items too quickly (Hacking?)");
                    return;
                }
            }
            this.player.a(false);
        }
        else if (packet14blockdig.e == 3) {
            this.player.a(true);
        }
        else if (packet14blockdig.e == 5) {
            this.player.bZ();
        }
        else {
            boolean flag = false;
            if (packet14blockdig.e == 0) {
                flag = true;
            }
            if (packet14blockdig.e == 1) {
                flag = true;
            }
            if (packet14blockdig.e == 2) {
                flag = true;
            }
            final int i = packet14blockdig.a;
            final int j = packet14blockdig.b;
            final int k = packet14blockdig.c;
            if (flag) {
                final double d0 = this.player.locX - (i + 0.5);
                final double d2 = this.player.locY - (j + 0.5) + 1.5;
                final double d3 = this.player.locZ - (k + 0.5);
                final double d4 = d0 * d0 + d2 * d2 + d3 * d3;
                if (d4 > 36.0) {
                    return;
                }
                if (j >= this.minecraftServer.getMaxBuildHeight()) {
                    return;
                }
            }
            if (packet14blockdig.e == 0) {
                if (!this.minecraftServer.a(worldserver, i, j, k, this.player)) {
                    this.player.playerInteractManager.dig(i, j, k, packet14blockdig.face);
                }
                else {
                    CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, packet14blockdig.face, this.player.inventory.getItemInHand());
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
                    final TileEntity tileentity = worldserver.getTileEntity(i, j, k);
                    if (tileentity != null) {
                        this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
                    }
                }
            }
            else if (packet14blockdig.e == 2) {
                this.player.playerInteractManager.a(i, j, k);
                if (worldserver.getTypeId(i, j, k) != 0) {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
                }
            }
            else if (packet14blockdig.e == 1) {
                this.player.playerInteractManager.c(i, j, k);
                if (worldserver.getTypeId(i, j, k) != 0) {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
                }
            }
        }
    }
    
    public void a(final Packet15Place packet15place) {
        final WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        if (this.player.dead) {
            return;
        }
        if (packet15place.getFace() == 255) {
            if (packet15place.getItemStack() != null && packet15place.getItemStack().id == this.lastMaterial && this.lastPacket != null && packet15place.timestamp - this.lastPacket < 100L) {
                this.lastPacket = null;
                return;
            }
        }
        else {
            this.lastMaterial = ((packet15place.getItemStack() == null) ? -1 : packet15place.getItemStack().id);
            this.lastPacket = packet15place.timestamp;
        }
        boolean always = false;
        ItemStack itemstack = this.player.inventory.getItemInHand();
        boolean flag = false;
        int i = packet15place.d();
        int j = packet15place.f();
        int k = packet15place.g();
        final int l = packet15place.getFace();
        if (packet15place.getFace() == 255) {
            if (itemstack == null) {
                return;
            }
            final int itemstackAmount = itemstack.count;
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
            if (event.useItemInHand() != Event.Result.DENY) {
                this.player.playerInteractManager.useItem(this.player, this.player.world, itemstack);
            }
            always = (itemstack.count != itemstackAmount);
        }
        else if (packet15place.f() >= this.minecraftServer.getMaxBuildHeight() - 1 && (packet15place.getFace() == 1 || packet15place.f() >= this.minecraftServer.getMaxBuildHeight())) {
            this.player.playerConnection.sendPacket(new Packet3Chat("" + EnumChatFormat.GRAY + "Height limit for building is " + this.minecraftServer.getMaxBuildHeight()));
            flag = true;
        }
        else {
            final Location eyeLoc = this.getPlayer().getEyeLocation();
            if (Math.pow(eyeLoc.getX() - i, 2.0) + Math.pow(eyeLoc.getY() - j, 2.0) + Math.pow(eyeLoc.getZ() - k, 2.0) > 36.0) {
                return;
            }
            this.player.playerInteractManager.interact(this.player, worldserver, itemstack, i, j, k, l, packet15place.j(), packet15place.k(), packet15place.l());
            flag = true;
        }
        if (flag) {
            this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
            if (l == 0) {
                --j;
            }
            if (l == 1) {
                ++j;
            }
            if (l == 2) {
                --k;
            }
            if (l == 3) {
                ++k;
            }
            if (l == 4) {
                --i;
            }
            if (l == 5) {
                ++i;
            }
            this.player.playerConnection.sendPacket(new Packet53BlockChange(i, j, k, worldserver));
        }
        itemstack = this.player.inventory.getItemInHand();
        if (itemstack != null && itemstack.count == 0) {
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
            itemstack = null;
        }
        if (itemstack == null || itemstack.n() == 0) {
            this.player.h = true;
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
            final Slot slot = this.player.activeContainer.a(this.player.inventory, this.player.inventory.itemInHandIndex);
            this.player.activeContainer.b();
            this.player.h = false;
            if (!ItemStack.matches(this.player.inventory.getItemInHand(), packet15place.getItemStack()) || always) {
                this.sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, slot.g, this.player.inventory.getItemInHand()));
            }
        }
    }
    
    public void a(final String s, final Object[] aobject) {
        if (this.disconnected) {
            return;
        }
        this.minecraftServer.getLogger().info(this.player.name + " lost connection: " + s);
        final String quitMessage = this.minecraftServer.getPlayerList().disconnect(this.player);
        if (quitMessage != null && quitMessage.length() > 0) {
            this.minecraftServer.getPlayerList().sendAll(new Packet3Chat(quitMessage));
        }
        this.disconnected = true;
        if (this.minecraftServer.I() && this.player.name.equals(this.minecraftServer.H())) {
            this.minecraftServer.getLogger().info("Stopping singleplayer server as player logged out");
            this.minecraftServer.safeShutdown();
        }
    }
    
    public void onUnhandledPacket(final Packet packet) {
        if (this.disconnected) {
            return;
        }
        this.minecraftServer.getLogger().warning(this.getClass() + " wasn't prepared to deal with a " + packet.getClass());
        this.disconnect("Protocol error, unexpected packet");
    }
    
    public void sendPacket(final Packet packet) {
        if (packet instanceof Packet3Chat) {
            final Packet3Chat packet3chat = (Packet3Chat)packet;
            final int i = this.player.getChatFlags();
            if (i == 2) {
                return;
            }
            if (i == 1 && !packet3chat.isServer()) {
                return;
            }
            final String message = packet3chat.message;
            for (final String line : TextWrapper.wrapText(message)) {
                this.networkManager.queue(new Packet3Chat(line));
            }
        }
        else {
            if (packet == null) {
                return;
            }
            if (packet instanceof Packet6SpawnPosition) {
                final Packet6SpawnPosition packet2 = (Packet6SpawnPosition)packet;
                this.player.compassTarget = new Location(this.getPlayer().getWorld(), packet2.x, packet2.y, packet2.z);
            }
            try {
                this.networkManager.queue(packet);
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.a(throwable, "Sending packet");
                final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");
                crashreportsystemdetails.a("Packet ID", new CrashReportConnectionPacketID(this, packet));
                crashreportsystemdetails.a("Packet class", new CrashReportConnectionPacketClass(this, packet));
                throw new ReportedException(crashreport);
            }
        }
    }
    
    public void a(final Packet16BlockItemSwitch packet16blockitemswitch) {
        if (this.player.dead) {
            return;
        }
        if (packet16blockitemswitch.itemInHandIndex >= 0 && packet16blockitemswitch.itemInHandIndex < PlayerInventory.getHotbarSize()) {
            final PlayerItemHeldEvent event = new PlayerItemHeldEvent(this.getPlayer(), this.player.inventory.itemInHandIndex, packet16blockitemswitch.itemInHandIndex);
            this.server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                this.sendPacket(new Packet16BlockItemSwitch(this.player.inventory.itemInHandIndex));
                return;
            }
            this.player.inventory.itemInHandIndex = packet16blockitemswitch.itemInHandIndex;
        }
        else {
            this.minecraftServer.getLogger().warning(this.player.name + " tried to set an invalid carried item");
            this.disconnect("Nope!");
        }
    }
    
    public void a(final Packet3Chat packet3chat) {
        if (this.player.getChatFlags() == 2) {
            this.sendPacket(new Packet3Chat("Cannot send chat message."));
        }
        else {
            String s = packet3chat.message;
            if (s.length() > 100) {
                if (packet3chat.a_()) {
                    final Waitable waitable = new Waitable() {
                        protected Object evaluate() {
                            PlayerConnection.this.disconnect("Chat message too long");
                            return null;
                        }
                    };
                    this.minecraftServer.processQueue.add(waitable);
                    try {
                        waitable.get();
                    }
                    catch (InterruptedException e3) {
                        Thread.currentThread().interrupt();
                    }
                    catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    this.disconnect("Chat message too long");
                }
            }
            else {
                s = s.trim();
                for (int i = 0; i < s.length(); ++i) {
                    if (!SharedConstants.isAllowedChatCharacter(s.charAt(i))) {
                        if (packet3chat.a_()) {
                            final Waitable waitable2 = new Waitable() {
                                protected Object evaluate() {
                                    PlayerConnection.this.disconnect("Illegal characters in chat");
                                    return null;
                                }
                            };
                            this.minecraftServer.processQueue.add(waitable2);
                            try {
                                waitable2.get();
                            }
                            catch (InterruptedException e4) {
                                Thread.currentThread().interrupt();
                            }
                            catch (ExecutionException e2) {
                                throw new RuntimeException(e2);
                            }
                        }
                        else {
                            this.disconnect("Illegal characters in chat");
                        }
                        return;
                    }
                }
                if (this.player.getChatFlags() == 1 && !s.startsWith("/")) {
                    this.sendPacket(new Packet3Chat("Cannot send chat message."));
                    return;
                }
                this.chat(s, packet3chat.a_());
                boolean isCounted = true;
                if (this.server.spamGuardExclusions != null) {
                    for (final String excluded : this.server.spamGuardExclusions) {
                        if (s.startsWith(excluded)) {
                            isCounted = false;
                            break;
                        }
                    }
                }
                if (isCounted && PlayerConnection.chatSpamField.addAndGet(this, 20) > 200 && !this.minecraftServer.getPlayerList().isOp(this.player.name)) {
                    if (packet3chat.a_()) {
                        final Waitable waitable2 = new Waitable() {
                            protected Object evaluate() {
                                PlayerConnection.this.disconnect("disconnect.spam");
                                return null;
                            }
                        };
                        this.minecraftServer.processQueue.add(waitable2);
                        try {
                            waitable2.get();
                        }
                        catch (InterruptedException e4) {
                            Thread.currentThread().interrupt();
                        }
                        catch (ExecutionException e2) {
                            throw new RuntimeException(e2);
                        }
                    }
                    else {
                        this.disconnect("disconnect.spam");
                    }
                }
            }
        }
    }
    
    public void chat(String s, final boolean async) {
        if (!this.player.dead) {
            if (s.length() == 0) {
                this.minecraftServer.getLogger().warning(this.player.name + " tried to send an empty message");
                return;
            }
            if (this.getPlayer().isConversing()) {
                this.getPlayer().acceptConversationInput(s);
                return;
            }
            if (s.startsWith("/")) {
                this.handleCommand(s);
                return;
            }
            final Player player = this.getPlayer();
            final AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, player, s, new LazyPlayerSet());
            this.server.getPluginManager().callEvent(event);
            if (PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0) {
                final PlayerChatEvent queueEvent = new PlayerChatEvent(player, event.getMessage(), event.getFormat(), event.getRecipients());
                queueEvent.setCancelled(event.isCancelled());
                final Waitable waitable = new Waitable() {
                    protected Object evaluate() {
                        Bukkit.getPluginManager().callEvent(queueEvent);
                        if (queueEvent.isCancelled()) {
                            return null;
                        }
                        final String message = String.format(queueEvent.getFormat(), queueEvent.getPlayer().getDisplayName(), queueEvent.getMessage());
                        PlayerConnection.this.minecraftServer.console.sendMessage(message);
                        if (((LazyPlayerSet)queueEvent.getRecipients()).isLazy()) {
                            for (final Object player : PlayerConnection.this.minecraftServer.getPlayerList().players) {
                                ((EntityPlayer)player).sendMessage(message);
                            }
                        }
                        else {
                            for (final Player player2 : queueEvent.getRecipients()) {
                                player2.sendMessage(message);
                            }
                        }
                        return null;
                    }
                };
                if (async) {
                    this.minecraftServer.processQueue.add(waitable);
                }
                else {
                    waitable.run();
                }
                try {
                    waitable.get();
                }
                catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException e) {
                    throw new RuntimeException("Exception processing chat event", e.getCause());
                }
            }
            else {
                if (event.isCancelled()) {
                    return;
                }
                s = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
                this.minecraftServer.console.sendMessage(s);
                if (((LazyPlayerSet)event.getRecipients()).isLazy()) {
                    for (final Object recipient : this.minecraftServer.getPlayerList().players) {
                        ((EntityPlayer)recipient).sendMessage(s);
                    }
                }
                else {
                    for (final Player recipient2 : event.getRecipients()) {
                        recipient2.sendMessage(s);
                    }
                }
            }
        }
    }
    
    private void handleCommand(final String s) {
        SpigotTimings.playerCommandTimer.startTiming();
        final CraftPlayer player = this.getPlayer();
        final PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, s, new LazyPlayerSet());
        this.server.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            SpigotTimings.playerCommandTimer.stopTiming();
            return;
        }
        try {
            if (this.server.logCommands) {
                this.minecraftServer.getLogger().info(event.getPlayer().getName() + " issued server command: " + event.getMessage());
            }
            if (this.server.dispatchCommand(event.getPlayer(), event.getMessage().substring(1))) {
                SpigotTimings.playerCommandTimer.stopTiming();
                return;
            }
        }
        catch (CommandException ex) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
            Logger.getLogger(PlayerConnection.class.getName()).log(Level.SEVERE, null, ex);
            SpigotTimings.playerCommandTimer.stopTiming();
            return;
        }
        SpigotTimings.playerCommandTimer.stopTiming();
    }
    
    public void a(final Packet18ArmAnimation packet18armanimation) {
        if (this.player.dead) {
            return;
        }
        if (packet18armanimation.b == 1) {
            final float f = 1.0f;
            final float f2 = this.player.lastPitch + (this.player.pitch - this.player.lastPitch) * f;
            final float f3 = this.player.lastYaw + (this.player.yaw - this.player.lastYaw) * f;
            final double d0 = this.player.lastX + (this.player.locX - this.player.lastX) * f;
            final double d2 = this.player.lastY + (this.player.locY - this.player.lastY) * f + 1.62 - this.player.height;
            final double d3 = this.player.lastZ + (this.player.locZ - this.player.lastZ) * f;
            final Vec3D vec3d = this.player.world.getVec3DPool().create(d0, d2, d3);
            final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
            final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
            final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
            final float f7 = MathHelper.sin(-f2 * 0.017453292f);
            final float f8 = f5 * f6;
            final float f9 = f4 * f6;
            final double d4 = 5.0;
            final Vec3D vec3d2 = vec3d.add(f8 * d4, f7 * d4, f9 * d4);
            final MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d2, true);
            if (movingobjectposition == null || movingobjectposition.type != EnumMovingObjectType.TILE) {
                CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand());
            }
            final PlayerAnimationEvent event = new PlayerAnimationEvent(this.getPlayer());
            this.server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            this.player.bK();
        }
    }
    
    public void a(final Packet19EntityAction packet19entityaction) {
        if (this.player.dead) {
            return;
        }
        if (packet19entityaction.animation == 1 || packet19entityaction.animation == 2) {
            final PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(this.getPlayer(), packet19entityaction.animation == 1);
            this.server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        if (packet19entityaction.animation == 4 || packet19entityaction.animation == 5) {
            final PlayerToggleSprintEvent event2 = new PlayerToggleSprintEvent(this.getPlayer(), packet19entityaction.animation == 4);
            this.server.getPluginManager().callEvent(event2);
            if (event2.isCancelled()) {
                return;
            }
        }
        if (packet19entityaction.animation == 1) {
            this.player.setSneaking(true);
        }
        else if (packet19entityaction.animation == 2) {
            this.player.setSneaking(false);
        }
        else if (packet19entityaction.animation == 4) {
            this.player.setSprinting(true);
        }
        else if (packet19entityaction.animation == 5) {
            this.player.setSprinting(false);
        }
        else if (packet19entityaction.animation == 3) {
            this.player.a(false, true, true);
        }
    }
    
    public void a(final Packet255KickDisconnect packet255kickdisconnect) {
        this.networkManager.a("disconnect.quitting", new Object[0]);
    }
    
    public int lowPriorityCount() {
        return this.networkManager.e();
    }
    
    public void a(final Packet7UseEntity packet7useentity) {
        if (this.player.dead) {
            return;
        }
        final WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        final Entity entity = worldserver.getEntity(packet7useentity.target);
        if (entity != null) {
            final boolean flag = this.player.n(entity);
            double d0 = 36.0;
            if (!flag) {
                d0 = 9.0;
            }
            if (this.player.e(entity) < d0) {
                final ItemStack itemInHand = this.player.inventory.getItemInHand();
                if (packet7useentity.action == 0) {
                    final PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(this.getPlayer(), entity.getBukkitEntity());
                    this.server.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    this.player.p(entity);
                    if (itemInHand != null && itemInHand.count <= -1) {
                        this.player.updateInventory(this.player.activeContainer);
                    }
                }
                else if (packet7useentity.action == 1) {
                    if (entity instanceof EntityItem || entity instanceof EntityExperienceOrb || entity instanceof EntityArrow) {
                        final String type = entity.getClass().getSimpleName();
                        this.disconnect("Attacking an " + type + " is not permitted");
                        System.out.println("Player " + this.player.name + " tried to attack an " + type + ", so I have disconnected them for exploiting.");
                        return;
                    }
                    this.player.attack(entity);
                    if (itemInHand != null && itemInHand.count <= -1) {
                        this.player.updateInventory(this.player.activeContainer);
                    }
                }
            }
        }
    }
    
    public void a(final Packet205ClientCommand packet205clientcommand) {
        if (packet205clientcommand.a == 1) {
            if (this.player.viewingCredits) {
                this.minecraftServer.getPlayerList().changeDimension(this.player, 0, PlayerTeleportEvent.TeleportCause.END_PORTAL);
            }
            else if (this.player.o().getWorldData().isHardcore()) {
                if (this.minecraftServer.I() && this.player.name.equals(this.minecraftServer.H())) {
                    this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
                    this.minecraftServer.P();
                }
                else {
                    final BanEntry banentry = new BanEntry(this.player.name);
                    banentry.setReason("Death in Hardcore");
                    this.minecraftServer.getPlayerList().getNameBans().add(banentry);
                    this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
                }
            }
            else {
                if (this.player.getHealth() > 0) {
                    return;
                }
                this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
            }
        }
    }
    
    public boolean b() {
        return true;
    }
    
    public void a(final Packet9Respawn packet9respawn) {
    }
    
    public void handleContainerClose(final Packet101CloseWindow packet101closewindow) {
        if (this.player.dead) {
            return;
        }
        CraftEventFactory.handleInventoryCloseEvent(this.player);
        this.player.j();
    }
    
    public void a(final Packet102WindowClick packet102windowclick) {
        if (this.player.dead) {
            return;
        }
        if (this.player.activeContainer.windowId == packet102windowclick.a && this.player.activeContainer.c(this.player)) {
            if (packet102windowclick.slot < -1 && packet102windowclick.slot != -999) {
                return;
            }
            final InventoryView inventory = this.player.activeContainer.getBukkitView();
            InventoryType.SlotType type = CraftInventoryView.getSlotType(inventory, packet102windowclick.slot);
            InventoryClickEvent event = null;
            ClickType click = ClickType.UNKNOWN;
            InventoryAction action = InventoryAction.UNKNOWN;
            ItemStack itemstack = null;
            if (packet102windowclick.slot == -1) {
                type = InventoryType.SlotType.OUTSIDE;
                click = ((packet102windowclick.button == 0) ? ClickType.WINDOW_BORDER_LEFT : ClickType.WINDOW_BORDER_RIGHT);
                action = InventoryAction.NOTHING;
            }
            else if (packet102windowclick.shift == 0) {
                if (packet102windowclick.button == 0) {
                    click = ClickType.LEFT;
                }
                else if (packet102windowclick.button == 1) {
                    click = ClickType.RIGHT;
                }
                if (packet102windowclick.button == 0 || packet102windowclick.button == 1) {
                    action = InventoryAction.NOTHING;
                    if (packet102windowclick.slot == -999) {
                        if (this.player.inventory.getCarried() != null) {
                            action = ((packet102windowclick.button == 0) ? InventoryAction.DROP_ALL_CURSOR : InventoryAction.DROP_ONE_CURSOR);
                        }
                    }
                    else {
                        final Slot slot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                        if (slot != null) {
                            final ItemStack clickedItem = slot.getItem();
                            final ItemStack cursor = this.player.inventory.getCarried();
                            if (clickedItem == null) {
                                if (cursor != null) {
                                    action = ((packet102windowclick.button == 0) ? InventoryAction.PLACE_ALL : InventoryAction.PLACE_ONE);
                                }
                            }
                            else if (slot.a(this.player)) {
                                if (cursor == null) {
                                    action = ((packet102windowclick.button == 0) ? InventoryAction.PICKUP_ALL : InventoryAction.PICKUP_HALF);
                                }
                                else if (slot.isAllowed(cursor)) {
                                    if (clickedItem.doMaterialsMatch(cursor) && ItemStack.equals(clickedItem, cursor)) {
                                        int toPlace = (packet102windowclick.button == 0) ? cursor.count : 1;
                                        toPlace = Math.min(toPlace, clickedItem.getMaxStackSize() - clickedItem.count);
                                        toPlace = Math.min(toPlace, slot.inventory.getMaxStackSize() - clickedItem.count);
                                        if (toPlace == 1) {
                                            action = InventoryAction.PLACE_ONE;
                                        }
                                        else if (toPlace == cursor.count) {
                                            action = InventoryAction.PLACE_ALL;
                                        }
                                        else if (toPlace < 0) {
                                            action = ((toPlace != -1) ? InventoryAction.PICKUP_SOME : InventoryAction.PICKUP_ONE);
                                        }
                                        else if (toPlace != 0) {
                                            action = InventoryAction.PLACE_SOME;
                                        }
                                    }
                                    else if (cursor.count <= slot.a()) {
                                        action = InventoryAction.SWAP_WITH_CURSOR;
                                    }
                                }
                                else if (cursor.id == clickedItem.id && (!cursor.usesData() || cursor.getData() == clickedItem.getData()) && ItemStack.equals(cursor, clickedItem) && clickedItem.count >= 0 && clickedItem.count + cursor.count <= cursor.getMaxStackSize()) {
                                    action = InventoryAction.PICKUP_ALL;
                                }
                            }
                        }
                    }
                }
            }
            else if (packet102windowclick.shift == 1) {
                if (packet102windowclick.button == 0) {
                    click = ClickType.SHIFT_LEFT;
                }
                else if (packet102windowclick.button == 1) {
                    click = ClickType.SHIFT_RIGHT;
                }
                if (packet102windowclick.button == 0 || packet102windowclick.button == 1) {
                    if (packet102windowclick.slot < 0) {
                        action = InventoryAction.NOTHING;
                    }
                    else {
                        final Slot slot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                        if (slot != null && slot.a(this.player) && slot.d()) {
                            action = InventoryAction.MOVE_TO_OTHER_INVENTORY;
                        }
                        else {
                            action = InventoryAction.NOTHING;
                        }
                    }
                }
            }
            else if (packet102windowclick.shift == 2) {
                if (packet102windowclick.button >= 0 && packet102windowclick.button < 9) {
                    click = ClickType.NUMBER_KEY;
                    final Slot clickedSlot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                    if (clickedSlot.a(this.player)) {
                        final ItemStack hotbar = this.player.inventory.getItem(packet102windowclick.button);
                        final boolean canCleanSwap = hotbar == null || (clickedSlot.inventory == this.player.inventory && clickedSlot.isAllowed(hotbar));
                        if (clickedSlot.d()) {
                            if (canCleanSwap) {
                                action = InventoryAction.HOTBAR_SWAP;
                            }
                            else {
                                final int firstEmptySlot = this.player.inventory.j();
                                if (firstEmptySlot > -1) {
                                    action = InventoryAction.HOTBAR_MOVE_AND_READD;
                                }
                                else {
                                    action = InventoryAction.NOTHING;
                                }
                            }
                        }
                        else if (!clickedSlot.d() && hotbar != null && clickedSlot.isAllowed(hotbar)) {
                            action = InventoryAction.HOTBAR_SWAP;
                        }
                        else {
                            action = InventoryAction.NOTHING;
                        }
                    }
                    else {
                        action = InventoryAction.NOTHING;
                    }
                    event = new InventoryClickEvent(inventory, type, packet102windowclick.slot, click, action, packet102windowclick.button);
                }
            }
            else if (packet102windowclick.shift == 3) {
                if (packet102windowclick.button == 2) {
                    click = ClickType.MIDDLE;
                    if (packet102windowclick.slot == -999) {
                        action = InventoryAction.NOTHING;
                    }
                    else {
                        final Slot slot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                        if (slot != null && slot.d() && this.player.abilities.canInstantlyBuild && this.player.inventory.getCarried() == null) {
                            action = InventoryAction.CLONE_STACK;
                        }
                        else {
                            action = InventoryAction.NOTHING;
                        }
                    }
                }
                else {
                    click = ClickType.UNKNOWN;
                    action = InventoryAction.UNKNOWN;
                }
            }
            else if (packet102windowclick.shift == 4) {
                if (packet102windowclick.slot >= 0) {
                    if (packet102windowclick.button == 0) {
                        click = ClickType.DROP;
                        final Slot slot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                        if (slot != null && slot.d() && slot.a(this.player) && slot.getItem() != null && slot.getItem().id != 0) {
                            action = InventoryAction.DROP_ONE_SLOT;
                        }
                        else {
                            action = InventoryAction.NOTHING;
                        }
                    }
                    else if (packet102windowclick.button == 1) {
                        click = ClickType.CONTROL_DROP;
                        final Slot slot = this.player.activeContainer.getSlot(packet102windowclick.slot);
                        if (slot != null && slot.d() && slot.a(this.player) && slot.getItem() != null && slot.getItem().id != 0) {
                            action = InventoryAction.DROP_ALL_SLOT;
                        }
                        else {
                            action = InventoryAction.NOTHING;
                        }
                    }
                }
                else {
                    click = ClickType.LEFT;
                    if (packet102windowclick.button == 1) {
                        click = ClickType.RIGHT;
                    }
                    action = InventoryAction.NOTHING;
                }
            }
            else if (packet102windowclick.shift == 5) {
                itemstack = this.player.activeContainer.clickItem(packet102windowclick.slot, packet102windowclick.button, 5, this.player);
            }
            else if (packet102windowclick.shift == 6) {
                click = ClickType.DOUBLE_CLICK;
                action = InventoryAction.NOTHING;
                if (packet102windowclick.slot >= 0 && this.player.inventory.getCarried() != null) {
                    final ItemStack cursor2 = this.player.inventory.getCarried();
                    action = InventoryAction.NOTHING;
                    if (inventory.getTopInventory().contains(cursor2.id) || inventory.getBottomInventory().contains(cursor2.id)) {
                        action = InventoryAction.COLLECT_TO_CURSOR;
                    }
                }
            }
            if (packet102windowclick.shift != 5) {
                if (click == ClickType.NUMBER_KEY) {
                    event = new InventoryClickEvent(inventory, type, packet102windowclick.slot, click, action, packet102windowclick.button);
                }
                else {
                    event = new InventoryClickEvent(inventory, type, packet102windowclick.slot, click, action);
                }
                final Inventory top = inventory.getTopInventory();
                if (packet102windowclick.slot == 0 && top instanceof CraftingInventory) {
                    final Recipe recipe = ((CraftingInventory)top).getRecipe();
                    if (recipe != null) {
                        if (click == ClickType.NUMBER_KEY) {
                            event = new CraftItemEvent(recipe, inventory, type, packet102windowclick.slot, click, action, packet102windowclick.button);
                        }
                        else {
                            event = new CraftItemEvent(recipe, inventory, type, packet102windowclick.slot, click, action);
                        }
                    }
                }
                this.server.getPluginManager().callEvent(event);
                switch (event.getResult()) {
                    case ALLOW:
                    case DEFAULT: {
                        itemstack = this.player.activeContainer.clickItem(packet102windowclick.slot, packet102windowclick.button, packet102windowclick.shift, this.player);
                        break;
                    }
                    case DENY: {
                        switch (action) {
                            case PICKUP_ALL:
                            case MOVE_TO_OTHER_INVENTORY:
                            case HOTBAR_MOVE_AND_READD:
                            case HOTBAR_SWAP:
                            case COLLECT_TO_CURSOR:
                            case UNKNOWN: {
                                this.player.updateInventory(this.player.activeContainer);
                                break;
                            }
                            case PICKUP_SOME:
                            case PICKUP_HALF:
                            case PICKUP_ONE:
                            case PLACE_ALL:
                            case PLACE_SOME:
                            case PLACE_ONE:
                            case SWAP_WITH_CURSOR: {
                                this.player.playerConnection.sendPacket(new Packet103SetSlot(-1, -1, this.player.inventory.getCarried()));
                                this.player.playerConnection.sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, packet102windowclick.slot, this.player.activeContainer.getSlot(packet102windowclick.slot).getItem()));
                                break;
                            }
                            case DROP_ALL_SLOT:
                            case DROP_ONE_SLOT: {
                                this.player.playerConnection.sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, packet102windowclick.slot, this.player.activeContainer.getSlot(packet102windowclick.slot).getItem()));
                                break;
                            }
                            case DROP_ALL_CURSOR:
                            case DROP_ONE_CURSOR:
                            case CLONE_STACK: {
                                this.player.playerConnection.sendPacket(new Packet103SetSlot(-1, -1, this.player.inventory.getCarried()));
                                break;
                            }
                        }
                        return;
                    }
                }
            }
            if (ItemStack.matches(packet102windowclick.item, itemstack)) {
                this.player.playerConnection.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, true));
                this.player.h = true;
                this.player.activeContainer.b();
                this.player.broadcastCarriedItem();
                this.player.h = false;
            }
            else {
                this.r.a(this.player.activeContainer.windowId, (Object)packet102windowclick.d);
                this.player.playerConnection.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, false));
                this.player.activeContainer.a(this.player, false);
                final ArrayList arraylist = new ArrayList();
                for (int i = 0; i < this.player.activeContainer.c.size(); ++i) {
                    arraylist.add(this.player.activeContainer.c.get(i).getItem());
                }
                this.player.a(this.player.activeContainer, arraylist);
                if (type == InventoryType.SlotType.RESULT && itemstack != null) {
                    this.player.playerConnection.sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, 0, itemstack));
                }
            }
        }
    }
    
    public void a(final Packet108ButtonClick packet108buttonclick) {
        if (this.player.activeContainer.windowId == packet108buttonclick.a && this.player.activeContainer.c(this.player)) {
            this.player.activeContainer.a(this.player, packet108buttonclick.b);
            this.player.activeContainer.b();
        }
    }
    
    public void a(final Packet107SetCreativeSlot packet107setcreativeslot) {
        if (this.player.playerInteractManager.isCreative()) {
            final boolean flag = packet107setcreativeslot.slot < 0;
            ItemStack itemstack = packet107setcreativeslot.b;
            final boolean flag2 = packet107setcreativeslot.slot >= 1 && packet107setcreativeslot.slot < 36 + PlayerInventory.getHotbarSize();
            boolean flag3 = itemstack == null || (itemstack.id < Item.byId.length && itemstack.id >= 0 && Item.byId[itemstack.id] != null && !PlayerConnection.invalidItems.contains(itemstack.id));
            boolean flag4 = itemstack == null || (itemstack.getData() >= 0 && itemstack.getData() >= 0 && itemstack.count <= 64 && itemstack.count > 0);
            if (flag || (flag2 && !ItemStack.matches(this.player.defaultContainer.getSlot(packet107setcreativeslot.slot).getItem(), packet107setcreativeslot.b))) {
                final HumanEntity player = this.player.getBukkitEntity();
                final InventoryView inventory = new CraftInventoryView(player, player.getInventory(), this.player.defaultContainer);
                final org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(packet107setcreativeslot.b);
                InventoryType.SlotType type = InventoryType.SlotType.QUICKBAR;
                if (flag) {
                    type = InventoryType.SlotType.OUTSIDE;
                }
                else if (packet107setcreativeslot.slot < 36) {
                    if (packet107setcreativeslot.slot >= 5 && packet107setcreativeslot.slot < 9) {
                        type = InventoryType.SlotType.ARMOR;
                    }
                    else {
                        type = InventoryType.SlotType.CONTAINER;
                    }
                }
                final InventoryCreativeEvent event = new InventoryCreativeEvent(inventory, type, flag ? -999 : packet107setcreativeslot.slot, item);
                this.server.getPluginManager().callEvent(event);
                itemstack = CraftItemStack.asNMSCopy(event.getCursor());
                switch (event.getResult()) {
                    case ALLOW: {
                        flag4 = (flag3 = true);
                    }
                    case DENY: {
                        if (packet107setcreativeslot.slot >= 0) {
                            this.player.playerConnection.sendPacket(new Packet103SetSlot(this.player.defaultContainer.windowId, packet107setcreativeslot.slot, this.player.defaultContainer.getSlot(packet107setcreativeslot.slot).getItem()));
                            this.player.playerConnection.sendPacket(new Packet103SetSlot(-1, -1, null));
                        }
                        return;
                    }
                }
            }
            if (flag2 && flag3 && flag4) {
                if (itemstack == null) {
                    this.player.defaultContainer.setItem(packet107setcreativeslot.slot, null);
                }
                else {
                    this.player.defaultContainer.setItem(packet107setcreativeslot.slot, itemstack);
                }
                this.player.defaultContainer.a(this.player, true);
            }
            else if (flag && flag3 && flag4 && this.x < 200) {
                this.x += 20;
                final EntityItem entityitem = this.player.drop(itemstack);
                if (entityitem != null) {
                    entityitem.c();
                }
            }
        }
    }
    
    public void a(final Packet106Transaction packet106transaction) {
        if (this.player.dead) {
            return;
        }
        final Short oshort = (Short)this.r.get(this.player.activeContainer.windowId);
        if (oshort != null && packet106transaction.b == oshort && this.player.activeContainer.windowId == packet106transaction.a && !this.player.activeContainer.c(this.player)) {
            this.player.activeContainer.a(this.player, true);
        }
    }
    
    public void a(final Packet130UpdateSign packet130updatesign) {
        if (this.player.dead) {
            return;
        }
        final WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        if (worldserver.isLoaded(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z)) {
            final TileEntity tileentity = worldserver.getTileEntity(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z);
            if (tileentity instanceof TileEntitySign) {
                final TileEntitySign tileentitysign = (TileEntitySign)tileentity;
                if (!tileentitysign.a()) {
                    this.minecraftServer.warning("Player " + this.player.name + " just tried to change non-editable sign");
                    this.sendPacket(new Packet130UpdateSign(packet130updatesign.x, packet130updatesign.y, packet130updatesign.z, tileentitysign.lines));
                    return;
                }
            }
            for (int j = 0; j < 4; ++j) {
                boolean flag = true;
                if (packet130updatesign.lines[j].length() > 15) {
                    flag = false;
                }
                else {
                    for (int i = 0; i < packet130updatesign.lines[j].length(); ++i) {
                        if (!SharedConstants.isAllowedChatCharacter(packet130updatesign.lines[j].charAt(i))) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (!flag) {
                    packet130updatesign.lines[j] = "!?";
                }
            }
            if (tileentity instanceof TileEntitySign) {
                final int j = packet130updatesign.x;
                final int k = packet130updatesign.y;
                final int i = packet130updatesign.z;
                final TileEntitySign tileentitysign2 = (TileEntitySign)tileentity;
                final Player player = this.server.getPlayer(this.player);
                final SignChangeEvent event = new SignChangeEvent(player.getWorld().getBlockAt(j, k, i), this.server.getPlayer(this.player), packet130updatesign.lines);
                this.server.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    for (int l = 0; l < 4; ++l) {
                        tileentitysign2.lines[l] = event.getLine(l);
                        if (tileentitysign2.lines[l] == null) {
                            tileentitysign2.lines[l] = "";
                        }
                    }
                    tileentitysign2.isEditable = false;
                }
                tileentitysign2.update();
                worldserver.notify(j, k, i);
            }
        }
    }
    
    public void a(final Packet0KeepAlive packet0keepalive) {
        if (packet0keepalive.a == this.h) {
            final int i = (int)(System.nanoTime() / 1000000L - this.i);
            this.player.ping = (this.player.ping * 3 + i) / 4;
        }
    }
    
    public boolean a() {
        return true;
    }
    
    public void a(final Packet202Abilities packet202abilities) {
        if (this.player.abilities.canFly && this.player.abilities.isFlying != packet202abilities.f()) {
            final PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this.server.getPlayer(this.player), packet202abilities.f());
            this.server.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.player.abilities.isFlying = packet202abilities.f();
            }
            else {
                this.player.updateAbilities();
            }
        }
    }
    
    public void a(final Packet203TabComplete packet203tabcomplete) {
        final StringBuilder stringbuilder = new StringBuilder();
        for (final String s : this.minecraftServer.a(this.player, packet203tabcomplete.d())) {
            if (stringbuilder.length() > 0) {
                stringbuilder.append('\0');
            }
            stringbuilder.append(s);
        }
        this.player.playerConnection.sendPacket(new Packet203TabComplete(stringbuilder.toString()));
    }
    
    public void a(final Packet204LocaleAndViewDistance packet204localeandviewdistance) {
        this.player.a(packet204localeandviewdistance);
    }
    
    public void a(final Packet250CustomPayload packet250custompayload) {
        if (packet250custompayload.length <= 0) {
            return;
        }
        if ("MC|BEdit".equals(packet250custompayload.tag)) {
            try {
                final DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packet250custompayload.data));
                final ItemStack itemstack = Packet.c(datainputstream);
                if (!ItemBookAndQuill.a(itemstack.getTag())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack itemstack2 = this.player.inventory.getItemInHand();
                if (itemstack != null && itemstack.id == Item.BOOK_AND_QUILL.id && itemstack.id == itemstack2.id) {
                    CraftEventFactory.handleEditBookEvent(this.player, itemstack);
                }
            }
            catch (Throwable exception) {
                this.minecraftServer.getLogger().warning(this.player.name + " sent invalid MC|BEdit data", exception);
                this.disconnect("Invalid book data!");
            }
        }
        else if ("MC|BSign".equals(packet250custompayload.tag)) {
            try {
                final DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packet250custompayload.data));
                final ItemStack itemstack = Packet.c(datainputstream);
                if (!ItemWrittenBook.a(itemstack.getTag())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack itemstack2 = this.player.inventory.getItemInHand();
                if (itemstack != null && itemstack.id == Item.WRITTEN_BOOK.id && itemstack2.id == Item.BOOK_AND_QUILL.id) {
                    CraftEventFactory.handleEditBookEvent(this.player, itemstack);
                }
            }
            catch (Throwable exception2) {
                this.minecraftServer.getLogger().warning(this.player.name + " sent invalid MC|BSign data", exception2);
                this.disconnect("Invalid book data!");
            }
        }
        else if ("MC|TrSel".equals(packet250custompayload.tag)) {
            try {
                final DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packet250custompayload.data));
                final int i = datainputstream.readInt();
                final Container container = this.player.activeContainer;
                if (container instanceof ContainerMerchant) {
                    ((ContainerMerchant)container).e(i);
                }
            }
            catch (Exception exception3) {
                this.minecraftServer.getLogger().warning(this.player.name + " sent invalid MC|TrSel data", exception3);
                this.disconnect("Invalid trade data!");
            }
        }
        else if ("MC|AdvCdm".equals(packet250custompayload.tag)) {
            if (!this.minecraftServer.getEnableCommandBlock()) {
                this.player.sendMessage(this.player.a("advMode.notEnabled", new Object[0]));
            }
            else if (this.player.a(2, "") && this.player.abilities.canInstantlyBuild) {
                try {
                    final DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packet250custompayload.data));
                    final int i = datainputstream.readInt();
                    final int j = datainputstream.readInt();
                    final int k = datainputstream.readInt();
                    final String s = Packet.a(datainputstream, 256);
                    final TileEntity tileentity = this.player.world.getTileEntity(i, j, k);
                    if (tileentity != null && tileentity instanceof TileEntityCommand) {
                        ((TileEntityCommand)tileentity).b(s);
                        this.player.world.notify(i, j, k);
                        this.player.sendMessage("Command set: " + s);
                    }
                }
                catch (Exception exception4) {
                    this.minecraftServer.getLogger().warning(this.player.name + " sent invalid MC|AdvCdm data", exception4);
                    this.disconnect("Invalid CommandBlock data!");
                }
            }
            else {
                this.player.sendMessage(this.player.a("advMode.notAllowed", new Object[0]));
            }
        }
        else if ("MC|Beacon".equals(packet250custompayload.tag)) {
            if (this.player.activeContainer instanceof ContainerBeacon) {
                try {
                    final DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packet250custompayload.data));
                    final int i = datainputstream.readInt();
                    final int j = datainputstream.readInt();
                    final ContainerBeacon containerbeacon = (ContainerBeacon)this.player.activeContainer;
                    final Slot slot = containerbeacon.getSlot(0);
                    if (slot.d()) {
                        slot.a(1);
                        final TileEntityBeacon tileentitybeacon = containerbeacon.e();
                        tileentitybeacon.d(i);
                        tileentitybeacon.e(j);
                        tileentitybeacon.update();
                    }
                }
                catch (Exception exception5) {
                    this.minecraftServer.getLogger().warning(this.player.name + " sent invalid MC|Beacon data", exception5);
                    this.disconnect("Invalid beacon data!");
                }
            }
        }
        else if ("MC|ItemName".equals(packet250custompayload.tag) && this.player.activeContainer instanceof ContainerAnvil) {
            final ContainerAnvil containeranvil = (ContainerAnvil)this.player.activeContainer;
            if (packet250custompayload.data != null && packet250custompayload.data.length >= 1) {
                final String s2 = SharedConstants.a(new String(packet250custompayload.data));
                if (s2.length() <= 30) {
                    containeranvil.a(s2);
                }
            }
            else {
                containeranvil.a("");
            }
        }
        else {
            if (packet250custompayload.tag.equals("REGISTER")) {
                try {
                    final String channels = new String(packet250custompayload.data, "UTF8");
                    for (final String channel : channels.split("\u0000")) {
                        this.getPlayer().addChannel(channel);
                    }
                    return;
                }
                catch (UnsupportedEncodingException ex) {
                    throw new AssertionError((Object)ex);
                }
            }
            if (packet250custompayload.tag.equals("UNREGISTER")) {
                try {
                    final String channels = new String(packet250custompayload.data, "UTF8");
                    for (final String channel : channels.split("\u0000")) {
                        this.getPlayer().removeChannel(channel);
                    }
                    return;
                }
                catch (UnsupportedEncodingException ex) {
                    throw new AssertionError((Object)ex);
                }
            }
            this.server.getMessenger().dispatchIncomingMessage(this.player.getBukkitEntity(), packet250custompayload.tag, packet250custompayload.data);
        }
    }
    
    static {
        PlayerConnection.j = new Random();
        chatSpamField = AtomicIntegerFieldUpdater.newUpdater(PlayerConnection.class, "chatThrottle");
        invalidItems = new HashSet<Integer>(Arrays.asList(8, 9, 10, 11, 26, 34, 36, 43, 51, 52, 55, 59, 60, 62, 63, 64, 68, 71, 74, 75, 83, 90, 92, 93, 94, 95, 104, 105, 115, 117, 118, 119, 125, 127, 132, 137, 140, 141, 142, 144));
    }
}
