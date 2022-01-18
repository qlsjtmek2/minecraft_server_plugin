// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.scoreboard.Team;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftItem;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.TrigMath;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;

public abstract class EntityHuman extends EntityLiving implements ICommandListener
{
    public PlayerInventory inventory;
    private InventoryEnderChest enderChest;
    public Container defaultContainer;
    public Container activeContainer;
    protected FoodMetaData foodData;
    protected int bO;
    public byte bP;
    public float bQ;
    public float bR;
    public String name;
    public int bT;
    public double bU;
    public double bV;
    public double bW;
    public double bX;
    public double bY;
    public double bZ;
    public boolean sleeping;
    public boolean fauxSleeping;
    public String spawnWorld;
    public ChunkCoordinates cb;
    public int sleepTicks;
    public float cc;
    public float cd;
    private ChunkCoordinates c;
    private boolean d;
    private ChunkCoordinates e;
    public PlayerAbilities abilities;
    public int oldLevel;
    public int expLevel;
    public int expTotal;
    public float exp;
    private ItemStack f;
    private int g;
    protected float ci;
    protected float cj;
    private int h;
    public EntityFishingHook hookedFish;
    
    public CraftHumanEntity getBukkitEntity() {
        return (CraftHumanEntity)super.getBukkitEntity();
    }
    
    public EntityHuman(final World world) {
        super(world);
        this.inventory = new PlayerInventory(this);
        this.enderChest = new InventoryEnderChest();
        this.foodData = new FoodMetaData();
        this.bO = 0;
        this.bP = 0;
        this.bT = 0;
        this.spawnWorld = "";
        this.abilities = new PlayerAbilities();
        this.oldLevel = -1;
        this.ci = 0.1f;
        this.cj = 0.02f;
        this.h = 0;
        this.hookedFish = null;
        this.defaultContainer = new ContainerPlayer(this.inventory, !world.isStatic, this);
        this.activeContainer = this.defaultContainer;
        this.height = 1.62f;
        final ChunkCoordinates chunkcoordinates = world.getSpawn();
        this.setPositionRotation(chunkcoordinates.x + 0.5, chunkcoordinates.y + 1, chunkcoordinates.z + 0.5, 0.0f, 0.0f);
        this.aK = "humanoid";
        this.aJ = 180.0f;
        this.maxFireTicks = 20;
        this.texture = "/mob/char.png";
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)0);
        this.datawatcher.a(17, (Object)(byte)0);
        this.datawatcher.a(18, (Object)0);
    }
    
    public boolean bX() {
        return this.f != null;
    }
    
    public void bZ() {
        if (this.f != null) {
            this.f.b(this.world, this, this.g);
        }
        this.ca();
    }
    
    public void ca() {
        this.f = null;
        this.g = 0;
        if (!this.world.isStatic) {
            this.e(false);
        }
    }
    
    public boolean isBlocking() {
        return this.bX() && Item.byId[this.f.id].b_(this.f) == EnumAnimation.BLOCK;
    }
    
    public void l_() {
        if (this.f != null) {
            final ItemStack itemstack = this.inventory.getItemInHand();
            if (itemstack == this.f) {
                if (this.g <= 25 && this.g % 4 == 0) {
                    this.c(itemstack, 5);
                }
                if (--this.g == 0 && !this.world.isStatic) {
                    this.m();
                }
            }
            else {
                this.ca();
            }
        }
        if (this.bT > 0) {
            --this.bT;
        }
        if (this.isSleeping()) {
            ++this.sleepTicks;
            if (this.sleepTicks > 100) {
                this.sleepTicks = 100;
            }
            if (!this.world.isStatic) {
                if (!this.i()) {
                    this.a(true, true, false);
                }
                else if (this.world.v()) {
                    this.a(false, true, true);
                }
            }
        }
        else if (this.sleepTicks > 0) {
            ++this.sleepTicks;
            if (this.sleepTicks >= 110) {
                this.sleepTicks = 0;
            }
        }
        super.l_();
        if (!this.world.isStatic && this.activeContainer != null && !this.activeContainer.a(this)) {
            this.closeInventory();
            this.activeContainer = this.defaultContainer;
        }
        if (this.isBurning() && this.abilities.isInvulnerable) {
            this.extinguish();
        }
        this.bU = this.bX;
        this.bV = this.bY;
        this.bW = this.bZ;
        final double d0 = this.locX - this.bX;
        final double d2 = this.locY - this.bY;
        final double d3 = this.locZ - this.bZ;
        final double d4 = 10.0;
        if (d0 > d4) {
            final double locX = this.locX;
            this.bX = locX;
            this.bU = locX;
        }
        if (d3 > d4) {
            final double locZ = this.locZ;
            this.bZ = locZ;
            this.bW = locZ;
        }
        if (d2 > d4) {
            final double locY = this.locY;
            this.bY = locY;
            this.bV = locY;
        }
        if (d0 < -d4) {
            final double locX2 = this.locX;
            this.bX = locX2;
            this.bU = locX2;
        }
        if (d3 < -d4) {
            final double locZ2 = this.locZ;
            this.bZ = locZ2;
            this.bW = locZ2;
        }
        if (d2 < -d4) {
            final double locY2 = this.locY;
            this.bY = locY2;
            this.bV = locY2;
        }
        this.bX += d0 * 0.25;
        this.bZ += d3 * 0.25;
        this.bY += d2 * 0.25;
        this.a(StatisticList.k, 1);
        if (this.vehicle == null) {
            this.e = null;
        }
        if (!this.world.isStatic) {
            this.foodData.a(this);
        }
    }
    
    public int y() {
        return this.abilities.isInvulnerable ? 0 : 80;
    }
    
    public int aa() {
        return 10;
    }
    
    public void makeSound(final String s, final float f, final float f1) {
        this.world.a(this, s, f, f1);
    }
    
    protected void c(final ItemStack itemstack, final int i) {
        if (itemstack.o() == EnumAnimation.DRINK) {
            this.makeSound("random.drink", 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
        }
        if (itemstack.o() == EnumAnimation.EAT) {
            for (int j = 0; j < i; ++j) {
                final Vec3D vec3d = this.world.getVec3DPool().create((this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                vec3d.a(-this.pitch * 3.1415927f / 180.0f);
                vec3d.b(-this.yaw * 3.1415927f / 180.0f);
                Vec3D vec3d2 = this.world.getVec3DPool().create((this.random.nextFloat() - 0.5) * 0.3, -this.random.nextFloat() * 0.6 - 0.3, 0.6);
                vec3d2.a(-this.pitch * 3.1415927f / 180.0f);
                vec3d2.b(-this.yaw * 3.1415927f / 180.0f);
                vec3d2 = vec3d2.add(this.locX, this.locY + this.getHeadHeight(), this.locZ);
                this.world.addParticle("iconcrack_" + itemstack.getItem().id, vec3d2.c, vec3d2.d, vec3d2.e, vec3d.c, vec3d.d + 0.05, vec3d.e);
            }
            this.makeSound("random.eat", 0.5f + 0.5f * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    protected void m() {
        if (this.f != null) {
            this.c(this.f, 16);
            final int i = this.f.count;
            final org.bukkit.inventory.ItemStack craftItem = CraftItemStack.asBukkitCopy(this.f);
            final PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player)this.getBukkitEntity(), craftItem);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                if (this instanceof EntityPlayer) {
                    ((EntityPlayer)this).playerConnection.sendPacket(new Packet103SetSlot(0, this.activeContainer.a(this.inventory, this.inventory.itemInHandIndex).index, this.f));
                }
                return;
            }
            if (!craftItem.equals(event.getItem())) {
                CraftItemStack.asNMSCopy(event.getItem()).b(this.world, this);
                if (this instanceof EntityPlayer) {
                    ((EntityPlayer)this).playerConnection.sendPacket(new Packet103SetSlot(0, this.activeContainer.a(this.inventory, this.inventory.itemInHandIndex).index, this.f));
                }
                return;
            }
            final ItemStack itemstack = this.f.b(this.world, this);
            if (itemstack != this.f || (itemstack != null && itemstack.count != i)) {
                this.inventory.items[this.inventory.itemInHandIndex] = itemstack;
                if (itemstack.count == 0) {
                    this.inventory.items[this.inventory.itemInHandIndex] = null;
                }
            }
            this.ca();
        }
    }
    
    protected boolean bj() {
        return this.getHealth() <= 0 || this.isSleeping();
    }
    
    public void closeInventory() {
        this.activeContainer = this.defaultContainer;
    }
    
    public void mount(final Entity entity) {
        this.setPassengerOf(entity);
    }
    
    public void setPassengerOf(final Entity entity) {
        if (this.vehicle == entity) {
            this.h(entity);
            if (this.vehicle != null) {
                this.vehicle.passenger = null;
            }
            this.vehicle = null;
        }
        else {
            super.setPassengerOf(entity);
        }
    }
    
    public void T() {
        final double d0 = this.locX;
        final double d2 = this.locY;
        final double d3 = this.locZ;
        final float f = this.yaw;
        final float f2 = this.pitch;
        super.T();
        this.bQ = this.bR;
        this.bR = 0.0f;
        this.k(this.locX - d0, this.locY - d2, this.locZ - d3);
        if (this.vehicle instanceof EntityPig) {
            this.pitch = f2;
            this.yaw = f;
            this.ay = ((EntityPig)this.vehicle).ay;
        }
    }
    
    protected void bq() {
        this.br();
    }
    
    public void c() {
        if (this.bO > 0) {
            --this.bO;
        }
        if (this.world.difficulty == 0 && this.getHealth() < this.maxHealth && this.ticksLived % 20 * 12 == 0) {
            this.heal(1, EntityRegainHealthEvent.RegainReason.REGEN);
        }
        this.inventory.k();
        this.bQ = this.bR;
        super.c();
        this.aO = this.abilities.b();
        this.aP = this.cj;
        if (this.isSprinting()) {
            this.aO += (float)(this.abilities.b() * 0.3);
            this.aP += (float)(this.cj * 0.3);
        }
        float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        float f2 = (float)TrigMath.atan(-this.motY * 0.20000000298023224) * 15.0f;
        if (f > 0.1f) {
            f = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0) {
            f = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0) {
            f2 = 0.0f;
        }
        this.bR += (f - this.bR) * 0.4f;
        this.bc += (f2 - this.bc) * 0.8f;
        if (this.getHealth() > 0) {
            final List list = this.world.getEntities(this, this.boundingBox.grow(1.0, 0.5, 1.0));
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    if (!entity.dead) {
                        this.r(entity);
                    }
                }
            }
        }
    }
    
    private void r(final Entity entity) {
        entity.b_(this);
    }
    
    public int getScore() {
        return this.datawatcher.getInt(18);
    }
    
    public void setScore(final int i) {
        this.datawatcher.watch(18, i);
    }
    
    public void addScore(final int i) {
        final int j = this.getScore();
        this.datawatcher.watch(18, j + i);
    }
    
    public void die(final DamageSource damagesource) {
        super.die(damagesource);
        this.a(0.2f, 0.2f);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.motY = 0.10000000149011612;
        if (this.name.equals("Notch")) {
            this.a(new ItemStack(Item.APPLE, 1), true);
        }
        if (!this.world.getGameRules().getBoolean("keepInventory")) {
            this.inventory.m();
        }
        if (damagesource != null) {
            this.motX = -MathHelper.cos((this.aY + this.yaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motZ = -MathHelper.sin((this.aY + this.yaw) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.motZ = n;
            this.motX = n;
        }
        this.height = 0.1f;
        this.a(StatisticList.y, 1);
    }
    
    public void c(final Entity entity, final int i) {
        this.addScore(i);
        final Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, this.getLocalizedName(), new ArrayList<ScoreboardScore>());
        if (entity instanceof EntityHuman) {
            this.a(StatisticList.A, 1);
            this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, this.getLocalizedName(), collection);
        }
        else {
            this.a(StatisticList.z, 1);
        }
        for (final ScoreboardScore scoreboardscore : collection) {
            scoreboardscore.incrementScore();
        }
    }
    
    public EntityItem a(final boolean flag) {
        return this.a(this.inventory.splitStack(this.inventory.itemInHandIndex, (flag && this.inventory.getItemInHand() != null) ? this.inventory.getItemInHand().count : 1), false);
    }
    
    public EntityItem drop(final ItemStack itemstack) {
        return this.a(itemstack, false);
    }
    
    public EntityItem a(final ItemStack itemstack, final boolean flag) {
        if (itemstack == null) {
            return null;
        }
        final EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY - 0.30000001192092896 + this.getHeadHeight(), this.locZ, itemstack);
        entityitem.pickupDelay = 40;
        float f = 0.1f;
        if (flag) {
            final float f2 = this.random.nextFloat() * 0.5f;
            final float f3 = this.random.nextFloat() * 3.1415927f * 2.0f;
            entityitem.motX = -MathHelper.sin(f3) * f2;
            entityitem.motZ = MathHelper.cos(f3) * f2;
            entityitem.motY = 0.20000000298023224;
        }
        else {
            f = 0.3f;
            entityitem.motX = -MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
            entityitem.motZ = MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
            entityitem.motY = -MathHelper.sin(this.pitch / 180.0f * 3.1415927f) * f + 0.1f;
            f = 0.02f;
            final float f2 = this.random.nextFloat() * 3.1415927f * 2.0f;
            f *= this.random.nextFloat();
            final EntityItem entityItem = entityitem;
            entityItem.motX += Math.cos(f2) * f;
            final EntityItem entityItem2 = entityitem;
            entityItem2.motY += (this.random.nextFloat() - this.random.nextFloat()) * 0.1f;
            final EntityItem entityItem3 = entityitem;
            entityItem3.motZ += Math.sin(f2) * f;
        }
        final Player player = (Player)this.getBukkitEntity();
        final CraftItem drop = new CraftItem(this.world.getServer(), entityitem);
        final PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            player.getInventory().addItem(drop.getItemStack());
            return null;
        }
        this.a(entityitem);
        this.a(StatisticList.v, 1);
        return entityitem;
    }
    
    protected void a(final EntityItem entityitem) {
        this.world.addEntity(entityitem);
    }
    
    public float a(final Block block, final boolean flag) {
        float f = this.inventory.a(block);
        if (f > 1.0f) {
            final int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
            final ItemStack itemstack = this.inventory.getItemInHand();
            if (i > 0 && itemstack != null) {
                final float f2 = i * i + 1;
                if (!itemstack.b(block) && f <= 1.0f) {
                    f += f2 * 0.08f;
                }
                else {
                    f += f2;
                }
            }
        }
        if (this.hasEffect(MobEffectList.FASTER_DIG)) {
            f *= 1.0f + (this.getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2f;
        }
        if (this.hasEffect(MobEffectList.SLOWER_DIG)) {
            f *= 1.0f - (this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier() + 1) * 0.2f;
        }
        if (this.a(Material.WATER) && !EnchantmentManager.hasWaterWorkerEnchantment(this)) {
            f /= 5.0f;
        }
        if (!this.onGround) {
            f /= 5.0f;
        }
        return f;
    }
    
    public boolean a(final Block block) {
        return this.inventory.b(block);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Inventory");
        this.inventory.b(nbttaglist);
        this.inventory.itemInHandIndex = nbttagcompound.getInt("SelectedItemSlot");
        this.sleeping = nbttagcompound.getBoolean("Sleeping");
        this.sleepTicks = nbttagcompound.getShort("SleepTimer");
        this.exp = nbttagcompound.getFloat("XpP");
        this.expLevel = nbttagcompound.getInt("XpLevel");
        this.expTotal = nbttagcompound.getInt("XpTotal");
        this.setScore(nbttagcompound.getInt("Score"));
        if (this.sleeping) {
            this.cb = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            this.a(true, true, false);
        }
        this.spawnWorld = nbttagcompound.getString("SpawnWorld");
        if ("".equals(this.spawnWorld)) {
            this.spawnWorld = this.world.getServer().getWorlds().get(0).getName();
        }
        if (nbttagcompound.hasKey("SpawnX") && nbttagcompound.hasKey("SpawnY") && nbttagcompound.hasKey("SpawnZ")) {
            this.c = new ChunkCoordinates(nbttagcompound.getInt("SpawnX"), nbttagcompound.getInt("SpawnY"), nbttagcompound.getInt("SpawnZ"));
            this.d = nbttagcompound.getBoolean("SpawnForced");
        }
        this.foodData.a(nbttagcompound);
        this.abilities.b(nbttagcompound);
        if (nbttagcompound.hasKey("EnderItems")) {
            final NBTTagList nbttaglist2 = nbttagcompound.getList("EnderItems");
            this.enderChest.a(nbttaglist2);
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.set("Inventory", this.inventory.a(new NBTTagList()));
        nbttagcompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
        nbttagcompound.setBoolean("Sleeping", this.sleeping);
        nbttagcompound.setShort("SleepTimer", (short)this.sleepTicks);
        nbttagcompound.setFloat("XpP", this.exp);
        nbttagcompound.setInt("XpLevel", this.expLevel);
        nbttagcompound.setInt("XpTotal", this.expTotal);
        nbttagcompound.setInt("Score", this.getScore());
        if (this.c != null) {
            nbttagcompound.setInt("SpawnX", this.c.x);
            nbttagcompound.setInt("SpawnY", this.c.y);
            nbttagcompound.setInt("SpawnZ", this.c.z);
            nbttagcompound.setBoolean("SpawnForced", this.d);
            nbttagcompound.setString("SpawnWorld", this.spawnWorld);
        }
        this.foodData.b(nbttagcompound);
        this.abilities.a(nbttagcompound);
        nbttagcompound.set("EnderItems", this.enderChest.h());
    }
    
    public void openContainer(final IInventory iinventory) {
    }
    
    public void openHopper(final TileEntityHopper tileentityhopper) {
    }
    
    public void openMinecartHopper(final EntityMinecartHopper entityminecarthopper) {
    }
    
    public void startEnchanting(final int i, final int j, final int k, final String s) {
    }
    
    public void openAnvil(final int i, final int j, final int k) {
    }
    
    public void startCrafting(final int i, final int j, final int k) {
    }
    
    public float getHeadHeight() {
        return 0.12f;
    }
    
    protected void e_() {
        this.height = 1.62f;
    }
    
    public boolean damageEntity(final DamageSource damagesource, int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (this.abilities.isInvulnerable && !damagesource.ignoresInvulnerability()) {
            return false;
        }
        this.bC = 0;
        if (this.getHealth() <= 0) {
            return false;
        }
        if (this.isSleeping() && !this.world.isStatic) {
            this.a(true, true, false);
        }
        if (damagesource.p()) {
            if (this.world.difficulty == 0) {
                return false;
            }
            if (this.world.difficulty == 1) {
                i = i / 2 + 1;
            }
            if (this.world.difficulty == 3) {
                i = i * 3 / 2;
            }
        }
        Entity entity = damagesource.getEntity();
        if (entity instanceof EntityArrow && ((EntityArrow)entity).shooter != null) {
            entity = ((EntityArrow)entity).shooter;
        }
        if (entity instanceof EntityLiving) {
            this.a((EntityLiving)entity, false);
        }
        this.a(StatisticList.x, i);
        return super.damageEntity(damagesource, i);
    }
    
    public boolean a(final EntityHuman entityhuman) {
        Team team;
        if (entityhuman instanceof EntityPlayer) {
            final EntityPlayer thatPlayer = (EntityPlayer)entityhuman;
            team = thatPlayer.getBukkitEntity().getScoreboard().getPlayerTeam(thatPlayer.getBukkitEntity());
            if (team == null || team.allowFriendlyFire()) {
                return true;
            }
        }
        else {
            final OfflinePlayer thisPlayer = entityhuman.world.getServer().getOfflinePlayer(entityhuman.name);
            team = entityhuman.world.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(thisPlayer);
            if (team == null || team.allowFriendlyFire()) {
                return true;
            }
        }
        if (this instanceof EntityPlayer) {
            return !team.hasPlayer(((EntityPlayer)this).getBukkitEntity());
        }
        return !team.hasPlayer(this.world.getServer().getOfflinePlayer(this.name));
    }
    
    protected void a(final EntityLiving entityliving, final boolean flag) {
        if (!(entityliving instanceof EntityCreeper) && !(entityliving instanceof EntityGhast)) {
            if (entityliving instanceof EntityWolf) {
                final EntityWolf entitywolf = (EntityWolf)entityliving;
                if (entitywolf.isTamed() && this.name.equals(entitywolf.getOwnerName())) {
                    return;
                }
            }
            if (!(entityliving instanceof EntityHuman) || this.a((EntityHuman)entityliving)) {
                final List list = this.world.a(EntityWolf.class, AxisAlignedBB.a().a(this.locX, this.locY, this.locZ, this.locX + 1.0, this.locY + 1.0, this.locZ + 1.0).grow(16.0, 4.0, 16.0));
                for (final EntityWolf entitywolf2 : list) {
                    if (entitywolf2.isTamed() && entitywolf2.l() == null && this.name.equals(entitywolf2.getOwnerName()) && (!flag || !entitywolf2.isSitting())) {
                        entitywolf2.setSitting(false);
                        entitywolf2.setTarget(entityliving);
                    }
                }
            }
        }
    }
    
    protected void k(final int i) {
        this.inventory.g(i);
    }
    
    public int aZ() {
        return this.inventory.l();
    }
    
    public float cc() {
        int i = 0;
        for (final ItemStack itemstack : this.inventory.armor) {
            if (itemstack != null) {
                ++i;
            }
        }
        return i / this.inventory.armor.length;
    }
    
    protected void d(final DamageSource damagesource, int i) {
        if (!this.isInvulnerable()) {
            if (!damagesource.ignoresArmor() && this.isBlocking()) {
                i = 1 + i >> 1;
            }
            i = this.b(damagesource, i);
            i = this.c(damagesource, i);
            this.j(damagesource.f());
            final int j = this.getHealth();
            this.setHealth(this.getHealth() - i);
            this.bt.a(damagesource, j, i);
        }
    }
    
    public void openFurnace(final TileEntityFurnace tileentityfurnace) {
    }
    
    public void openDispenser(final TileEntityDispenser tileentitydispenser) {
    }
    
    public void a(final TileEntity tileentity) {
    }
    
    public void openBrewingStand(final TileEntityBrewingStand tileentitybrewingstand) {
    }
    
    public void openBeacon(final TileEntityBeacon tileentitybeacon) {
    }
    
    public void openTrade(final IMerchant imerchant, final String s) {
    }
    
    public void d(final ItemStack itemstack) {
    }
    
    public boolean p(final Entity entity) {
        if (entity.a_(this)) {
            return true;
        }
        ItemStack itemstack = this.cd();
        if (itemstack != null && entity instanceof EntityLiving) {
            if (this.abilities.canInstantlyBuild) {
                itemstack = itemstack.cloneItemStack();
            }
            if (itemstack.a((EntityLiving)entity)) {
                if (itemstack.count == 0 && !this.abilities.canInstantlyBuild) {
                    this.ce();
                }
                return true;
            }
        }
        return false;
    }
    
    public ItemStack cd() {
        return this.inventory.getItemInHand();
    }
    
    public void ce() {
        this.inventory.setItem(this.inventory.itemInHandIndex, null);
    }
    
    public double V() {
        return this.height - 0.5f;
    }
    
    public void attack(final Entity entity) {
        if (entity.ap() && !entity.j(this)) {
            int i = this.inventory.a(entity);
            if (this.hasEffect(MobEffectList.INCREASE_DAMAGE)) {
                i += 3 << this.getEffect(MobEffectList.INCREASE_DAMAGE).getAmplifier();
            }
            if (this.hasEffect(MobEffectList.WEAKNESS)) {
                i -= 2 << this.getEffect(MobEffectList.WEAKNESS).getAmplifier();
            }
            int j = 0;
            int k = 0;
            if (entity instanceof EntityLiving) {
                k = EnchantmentManager.a(this, (EntityLiving)entity);
                j += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving)entity);
            }
            if (this.isSprinting()) {
                ++j;
            }
            if (i > 0 || k > 0) {
                final boolean flag = this.fallDistance > 0.0f && !this.onGround && !this.g_() && !this.G() && !this.hasEffect(MobEffectList.BLINDNESS) && this.vehicle == null && entity instanceof EntityLiving;
                if (flag && i > 0) {
                    i += this.random.nextInt(i / 2 + 2);
                }
                i += k;
                boolean flag2 = false;
                final int l = EnchantmentManager.getFireAspectEnchantmentLevel(this);
                if (entity instanceof EntityLiving && l > 0 && !entity.isBurning()) {
                    flag2 = true;
                    entity.setOnFire(1);
                }
                final boolean flag3 = entity.damageEntity(DamageSource.playerAttack(this), i);
                if (!flag3) {
                    if (flag2) {
                        entity.extinguish();
                    }
                    return;
                }
                if (flag3) {
                    if (j > 0) {
                        entity.g(-MathHelper.sin(this.yaw * 3.1415927f / 180.0f) * j * 0.5f, 0.1, MathHelper.cos(this.yaw * 3.1415927f / 180.0f) * j * 0.5f);
                        this.motX *= 0.6;
                        this.motZ *= 0.6;
                        this.setSprinting(false);
                    }
                    if (flag) {
                        this.b(entity);
                    }
                    if (k > 0) {
                        this.c(entity);
                    }
                    if (i >= 18) {
                        this.a(AchievementList.E);
                    }
                    this.l(entity);
                    if (entity instanceof EntityLiving) {
                        EnchantmentThorns.a(this, (EntityLiving)entity, this.random);
                    }
                }
                final ItemStack itemstack = this.cd();
                Object object = entity;
                if (entity instanceof EntityComplexPart) {
                    final IComplex icomplex = ((EntityComplexPart)entity).owner;
                    if (icomplex != null && icomplex instanceof EntityLiving) {
                        object = icomplex;
                    }
                }
                if (itemstack != null && object instanceof EntityLiving) {
                    itemstack.a((EntityLiving)object, this);
                    if (itemstack.count == 0) {
                        this.ce();
                    }
                }
                if (entity instanceof EntityLiving) {
                    if (entity.isAlive()) {
                        this.a((EntityLiving)entity, true);
                    }
                    this.a(StatisticList.w, i);
                    if (l > 0 && flag3) {
                        final EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), l * 4);
                        Bukkit.getPluginManager().callEvent(combustEvent);
                        if (!combustEvent.isCancelled()) {
                            entity.setOnFire(combustEvent.getDuration());
                        }
                    }
                    else if (flag2) {
                        entity.extinguish();
                    }
                }
                this.j(0.3f);
            }
        }
    }
    
    public void b(final Entity entity) {
    }
    
    public void c(final Entity entity) {
    }
    
    public void die() {
        super.die();
        this.defaultContainer.b(this);
        if (this.activeContainer != null) {
            this.activeContainer.b(this);
        }
    }
    
    public boolean inBlock() {
        return !this.sleeping && super.inBlock();
    }
    
    public boolean cg() {
        return false;
    }
    
    public EnumBedResult a(final int i, final int j, final int k) {
        if (!this.world.isStatic) {
            if (this.isSleeping() || !this.isAlive()) {
                return EnumBedResult.OTHER_PROBLEM;
            }
            if (!this.world.worldProvider.d()) {
                return EnumBedResult.NOT_POSSIBLE_HERE;
            }
            if (this.world.v()) {
                return EnumBedResult.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.locX - i) > 3.0 || Math.abs(this.locY - j) > 2.0 || Math.abs(this.locZ - k) > 3.0) {
                return EnumBedResult.TOO_FAR_AWAY;
            }
            final double d0 = 8.0;
            final double d2 = 5.0;
            final List list = this.world.a(EntityMonster.class, AxisAlignedBB.a().a(i - d0, j - d2, k - d0, i + d0, j + d2, k + d0));
            if (!list.isEmpty()) {
                return EnumBedResult.NOT_SAFE;
            }
        }
        if (this.getBukkitEntity() instanceof Player) {
            final Player player = (Player)this.getBukkitEntity();
            final org.bukkit.block.Block bed = this.world.getWorld().getBlockAt(i, j, k);
            final PlayerBedEnterEvent event = new PlayerBedEnterEvent(player, bed);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return EnumBedResult.OTHER_PROBLEM;
            }
        }
        this.a(0.2f, 0.2f);
        this.height = 0.2f;
        if (this.world.isLoaded(i, j, k)) {
            final int l = this.world.getData(i, j, k);
            final int i2 = BlockDirectional.j(l);
            float f = 0.5f;
            float f2 = 0.5f;
            switch (i2) {
                case 0: {
                    f2 = 0.9f;
                    break;
                }
                case 1: {
                    f = 0.1f;
                    break;
                }
                case 2: {
                    f2 = 0.1f;
                    break;
                }
                case 3: {
                    f = 0.9f;
                    break;
                }
            }
            this.x(i2);
            this.setPosition(i + f, j + 0.9375f, k + f2);
        }
        else {
            this.setPosition(i + 0.5f, j + 0.9375f, k + 0.5f);
        }
        this.sleeping = true;
        this.sleepTicks = 0;
        this.cb = new ChunkCoordinates(i, j, k);
        final double motX = 0.0;
        this.motY = motX;
        this.motZ = motX;
        this.motX = motX;
        if (!this.world.isStatic) {
            this.world.everyoneSleeping();
        }
        return EnumBedResult.OK;
    }
    
    private void x(final int i) {
        this.cc = 0.0f;
        this.cd = 0.0f;
        switch (i) {
            case 0: {
                this.cd = -1.8f;
                break;
            }
            case 1: {
                this.cc = 1.8f;
                break;
            }
            case 2: {
                this.cd = 1.8f;
                break;
            }
            case 3: {
                this.cc = -1.8f;
                break;
            }
        }
    }
    
    public void a(final boolean flag, final boolean flag1, final boolean flag2) {
        this.a(0.6f, 1.8f);
        this.e_();
        final ChunkCoordinates chunkcoordinates = this.cb;
        ChunkCoordinates chunkcoordinates2 = this.cb;
        if (chunkcoordinates != null && this.world.getTypeId(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Block.BED.id) {
            BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, false);
            chunkcoordinates2 = BlockBed.b(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
            if (chunkcoordinates2 == null) {
                chunkcoordinates2 = new ChunkCoordinates(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
            }
            this.setPosition(chunkcoordinates2.x + 0.5f, chunkcoordinates2.y + this.height + 0.1f, chunkcoordinates2.z + 0.5f);
        }
        this.sleeping = false;
        if (!this.world.isStatic && flag1) {
            this.world.everyoneSleeping();
        }
        if (this.getBukkitEntity() instanceof Player) {
            final Player player = (Player)this.getBukkitEntity();
            org.bukkit.block.Block bed;
            if (chunkcoordinates != null) {
                bed = this.world.getWorld().getBlockAt(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
            }
            else {
                bed = this.world.getWorld().getBlockAt(player.getLocation());
            }
            final PlayerBedLeaveEvent event = new PlayerBedLeaveEvent(player, bed);
            this.world.getServer().getPluginManager().callEvent(event);
        }
        if (flag) {
            this.sleepTicks = 0;
        }
        else {
            this.sleepTicks = 100;
        }
        if (flag2) {
            this.setRespawnPosition(this.cb, false);
        }
    }
    
    private boolean i() {
        return this.world.getTypeId(this.cb.x, this.cb.y, this.cb.z) == Block.BED.id;
    }
    
    public static ChunkCoordinates getBed(final World world, final ChunkCoordinates chunkcoordinates, final boolean flag) {
        final IChunkProvider ichunkprovider = world.K();
        ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z - 3 >> 4);
        ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z - 3 >> 4);
        ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z + 3 >> 4);
        ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z + 3 >> 4);
        if (world.getTypeId(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Block.BED.id) {
            final ChunkCoordinates chunkcoordinates2 = BlockBed.b(world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
            return chunkcoordinates2;
        }
        final Material material = world.getMaterial(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
        final Material material2 = world.getMaterial(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
        final boolean flag2 = !material.isBuildable() && !material.isLiquid();
        final boolean flag3 = !material2.isBuildable() && !material2.isLiquid();
        return (flag && flag2 && flag3) ? chunkcoordinates : null;
    }
    
    public boolean isSleeping() {
        return this.sleeping;
    }
    
    public boolean isDeeplySleeping() {
        return this.sleeping && this.sleepTicks >= 100;
    }
    
    protected void b(final int i, final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (byte)(b0 | 1 << i));
        }
        else {
            this.datawatcher.watch(16, (byte)(b0 & ~(1 << i)));
        }
    }
    
    public void b(final String s) {
    }
    
    public ChunkCoordinates getBed() {
        return this.c;
    }
    
    public boolean isRespawnForced() {
        return this.d;
    }
    
    public void setRespawnPosition(final ChunkCoordinates chunkcoordinates, final boolean flag) {
        if (chunkcoordinates != null) {
            this.c = new ChunkCoordinates(chunkcoordinates);
            this.d = flag;
            this.spawnWorld = this.world.worldData.getName();
        }
        else {
            this.c = null;
            this.d = false;
            this.spawnWorld = "";
        }
    }
    
    public void a(final Statistic statistic) {
        this.a(statistic, 1);
    }
    
    public void a(final Statistic statistic, final int i) {
    }
    
    protected void bl() {
        super.bl();
        this.a(StatisticList.u, 1);
        if (this.isSprinting()) {
            this.j(0.8f);
        }
        else {
            this.j(0.2f);
        }
    }
    
    public void e(final float f, final float f1) {
        final double d0 = this.locX;
        final double d2 = this.locY;
        final double d3 = this.locZ;
        if (this.abilities.isFlying && this.vehicle == null) {
            final double d4 = this.motY;
            final float f2 = this.aP;
            this.aP = this.abilities.a();
            super.e(f, f1);
            this.motY = d4 * 0.6;
            this.aP = f2;
        }
        else {
            super.e(f, f1);
        }
        this.checkMovement(this.locX - d0, this.locY - d2, this.locZ - d3);
    }
    
    public void checkMovement(final double d0, final double d1, final double d2) {
        if (this.vehicle == null) {
            if (this.a(Material.WATER)) {
                final int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0f);
                if (i > 0) {
                    this.a(StatisticList.q, i);
                    this.j(0.015f * i * 0.01f);
                }
            }
            else if (this.G()) {
                final int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0f);
                if (i > 0) {
                    this.a(StatisticList.m, i);
                    this.j(0.015f * i * 0.01f);
                }
            }
            else if (this.g_()) {
                if (d1 > 0.0) {
                    this.a(StatisticList.o, (int)Math.round(d1 * 100.0));
                }
            }
            else if (this.onGround) {
                final int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0f);
                if (i > 0) {
                    this.a(StatisticList.l, i);
                    if (this.isSprinting()) {
                        this.j(0.099999994f * i * 0.01f);
                    }
                    else {
                        this.j(0.01f * i * 0.01f);
                    }
                }
            }
            else {
                final int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0f);
                if (i > 25) {
                    this.a(StatisticList.p, i);
                }
            }
        }
    }
    
    private void k(final double d0, final double d1, final double d2) {
        if (this.vehicle != null) {
            final int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0f);
            if (i > 0) {
                if (this.vehicle instanceof EntityMinecartAbstract) {
                    this.a(StatisticList.r, i);
                    if (this.e == null) {
                        this.e = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
                    }
                    else if (this.e.e(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000000.0) {
                        this.a(AchievementList.q, 1);
                    }
                }
                else if (this.vehicle instanceof EntityBoat) {
                    this.a(StatisticList.s, i);
                }
                else if (this.vehicle instanceof EntityPig) {
                    this.a(StatisticList.t, i);
                }
            }
        }
    }
    
    protected void a(final float f) {
        if (!this.abilities.canFly) {
            if (f >= 2.0f) {
                this.a(StatisticList.n, (int)Math.round(f * 100.0));
            }
            super.a(f);
        }
    }
    
    public void a(final EntityLiving entityliving) {
        if (entityliving instanceof IMonster) {
            this.a(AchievementList.s);
        }
    }
    
    public void al() {
        if (!this.abilities.isFlying) {
            super.al();
        }
    }
    
    public ItemStack q(final int i) {
        return this.inventory.f(i);
    }
    
    protected void bH() {
    }
    
    protected void bI() {
    }
    
    public void giveExp(int i) {
        this.addScore(i);
        final int j = Integer.MAX_VALUE - this.expTotal;
        if (i > j) {
            i = j;
        }
        this.exp += i / this.getExpToLevel();
        this.expTotal += i;
        while (this.exp >= 1.0f) {
            this.exp = (this.exp - 1.0f) * this.getExpToLevel();
            this.levelDown(1);
            this.exp /= this.getExpToLevel();
        }
    }
    
    public void levelDown(final int i) {
        this.expLevel += i;
        if (this.expLevel < 0) {
            this.expLevel = 0;
            this.exp = 0.0f;
            this.expTotal = 0;
        }
        if (i > 0 && this.expLevel % 5 == 0 && this.h < this.ticksLived - 100.0f) {
            final float f = (this.expLevel > 30) ? 1.0f : (this.expLevel / 30.0f);
            this.world.makeSound(this, "random.levelup", f * 0.75f, 1.0f);
            this.h = this.ticksLived;
        }
    }
    
    public int getExpToLevel() {
        return (this.expLevel >= 30) ? (62 + (this.expLevel - 30) * 7) : ((this.expLevel >= 15) ? (17 + (this.expLevel - 15) * 3) : 17);
    }
    
    public void j(final float f) {
        if (!this.abilities.isInvulnerable && !this.world.isStatic) {
            this.foodData.a(f);
        }
    }
    
    public FoodMetaData getFoodData() {
        return this.foodData;
    }
    
    public boolean i(final boolean flag) {
        return (flag || this.foodData.c()) && !this.abilities.isInvulnerable;
    }
    
    public boolean co() {
        return this.getHealth() > 0 && this.getHealth() < this.maxHealth;
    }
    
    public void a(final ItemStack itemstack, final int i) {
        if (itemstack != this.f) {
            this.f = itemstack;
            this.g = i;
            if (!this.world.isStatic) {
                this.e(true);
            }
        }
    }
    
    public boolean e(final int i, final int j, final int k) {
        if (this.abilities.mayBuild) {
            return true;
        }
        final int l = this.world.getTypeId(i, j, k);
        if (l > 0) {
            final Block block = Block.byId[l];
            if (block.material.q()) {
                return true;
            }
            if (this.cd() != null) {
                final ItemStack itemstack = this.cd();
                if (itemstack.b(block) || itemstack.a(block) > 1.0f) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean a(final int i, final int j, final int k, final int l, final ItemStack itemstack) {
        return this.abilities.mayBuild || (itemstack != null && itemstack.y());
    }
    
    protected int getExpValue(final EntityHuman entityhuman) {
        if (this.world.getGameRules().getBoolean("keepInventory")) {
            return 0;
        }
        final int i = this.expLevel * 7;
        return (i > 100) ? 100 : i;
    }
    
    protected boolean alwaysGivesExp() {
        return true;
    }
    
    public String getLocalizedName() {
        return this.name;
    }
    
    public boolean getCustomNameVisible() {
        return super.getCustomNameVisible();
    }
    
    public void copyTo(final EntityHuman entityhuman, final boolean flag) {
        if (flag) {
            this.inventory.b(entityhuman.inventory);
            this.health = entityhuman.health;
            this.foodData = entityhuman.foodData;
            this.expLevel = entityhuman.expLevel;
            this.expTotal = entityhuman.expTotal;
            this.exp = entityhuman.exp;
            this.setScore(entityhuman.getScore());
            this.as = entityhuman.as;
        }
        else if (this.world.getGameRules().getBoolean("keepInventory")) {
            this.inventory.b(entityhuman.inventory);
            this.expLevel = entityhuman.expLevel;
            this.expTotal = entityhuman.expTotal;
            this.exp = entityhuman.exp;
            this.setScore(entityhuman.getScore());
        }
        this.enderChest = entityhuman.enderChest;
    }
    
    protected boolean f_() {
        return !this.abilities.isFlying;
    }
    
    public void updateAbilities() {
    }
    
    public void a(final EnumGamemode enumgamemode) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public LocaleLanguage getLocale() {
        return LocaleLanguage.a();
    }
    
    public String a(final String s, final Object... aobject) {
        return this.getLocale().a(s, aobject);
    }
    
    public InventoryEnderChest getEnderChest() {
        return this.enderChest;
    }
    
    public ItemStack getEquipment(final int i) {
        return (i == 0) ? this.inventory.getItemInHand() : this.inventory.armor[i - 1];
    }
    
    public ItemStack bG() {
        return this.inventory.getItemInHand();
    }
    
    public void setEquipment(final int i, final ItemStack itemstack) {
        this.inventory.armor[i] = itemstack;
    }
    
    public ItemStack[] getEquipment() {
        return this.inventory.armor;
    }
    
    public boolean aw() {
        return !this.abilities.isFlying;
    }
    
    public Scoreboard getScoreboard() {
        return this.world.getScoreboard();
    }
    
    public ScoreboardTeam getScoreboardTeam() {
        return this.getScoreboard().getPlayerTeam(this.name);
    }
    
    public String getScoreboardDisplayName() {
        return ScoreboardTeam.getPlayerDisplayName(this.getScoreboardTeam(), this.name);
    }
}
