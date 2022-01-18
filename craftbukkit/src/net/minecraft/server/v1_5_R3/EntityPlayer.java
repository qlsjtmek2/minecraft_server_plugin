// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import java.util.EnumSet;
import org.bukkit.event.inventory.InventoryType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import java.util.LinkedList;
import org.bukkit.WeatherType;
import org.bukkit.Location;
import java.util.List;
import java.util.Set;

public class EntityPlayer extends EntityHuman implements ICrafting
{
    public static Object serverPlayerAPI;
	private LocaleLanguage locale;
    public PlayerConnection playerConnection;
    public MinecraftServer server;
    public PlayerInteractManager playerInteractManager;
    public double d;
    public double e;
    public final List chunkCoordIntPairQueue;
    public final List removeQueue;
    private int cm;
    private int cn;
    private boolean co;
    public int lastSentExp;
    public int invulnerableTicks;
    private int cr;
    private int cs;
    private boolean ct;
    private int containerCounter;
    public boolean h;
    public int ping;
    public boolean viewingCredits;
    public String displayName;
    public String listName;
    public Location compassTarget;
    public int newExp;
    public int newLevel;
    public int newTotalExp;
    public boolean keepLevel;
    public int lastPing;
    public long timeOffset;
    public boolean relativeTime;
    public WeatherType weather;
    
    public EntityPlayer(final MinecraftServer minecraftserver, final World world, final String s, final PlayerInteractManager playerinteractmanager) {
        super(world);
        this.locale = new LocaleLanguage("en_US");
        this.chunkCoordIntPairQueue = new LinkedList();
        this.removeQueue = new LinkedList();
        this.cm = -99999999;
        this.cn = -99999999;
        this.co = true;
        this.lastSentExp = -99999999;
        this.invulnerableTicks = 60;
        this.cr = 0;
        this.cs = 0;
        this.ct = true;
        this.containerCounter = 0;
        this.viewingCredits = false;
        this.newExp = 0;
        this.newLevel = 0;
        this.newTotalExp = 0;
        this.keepLevel = false;
        this.lastPing = -1;
        this.timeOffset = 0L;
        this.relativeTime = true;
        this.weather = null;
        playerinteractmanager.player = this;
        this.playerInteractManager = playerinteractmanager;
        this.cr = minecraftserver.getPlayerList().o();
        final ChunkCoordinates chunkcoordinates = world.getSpawn();
        int i = chunkcoordinates.x;
        int j = chunkcoordinates.z;
        int k = chunkcoordinates.y;
        if (!world.worldProvider.f && world.getWorldData().getGameType() != EnumGamemode.ADVENTURE) {
            final int l = Math.max(5, minecraftserver.getSpawnProtection() - 6);
            i += this.random.nextInt(l * 2) - l;
            j += this.random.nextInt(l * 2) - l;
            k = world.i(i, j);
        }
        this.server = minecraftserver;
        this.Y = 0.0f;
        this.name = s;
        this.height = 0.0f;
        this.setPositionRotation(i + 0.5, k, j + 0.5, 0.0f, 0.0f);
        while (!world.getCubes(this, this.boundingBox).isEmpty()) {
            this.setPosition(this.locX, this.locY + 1.0, this.locZ);
        }
        this.displayName = this.name;
        this.listName = this.name;
        this.canPickUpLoot = true;

        ServerPlayerAPI.afterLocalConstructing(this, minecraftserver, world, s, playerinteractmanager);
    }
    
    public final ServerPlayerBase getServerPlayerBase(String paramString)
    {
      if (EntityPlayer.serverPlayerAPI != null)
        return ((EntityPlayer) EntityPlayer.serverPlayerAPI).getServerPlayerBase(paramString);
      return null;
    }

    public final Set<String> getServerPlayerBaseIds(String paramString)
    {
      if (EntityPlayer.serverPlayerAPI != null)
        return ((ServerPlayerAPI) EntityPlayer.serverPlayerAPI).getServerPlayerBaseIds();
      return Collections.emptySet();
    }

    public Object dynamic(String paramString, Object[] paramArrayOfObject)
    {
      if (EntityPlayer.serverPlayerAPI != null)
        return ((EntityPlayer) EntityPlayer.serverPlayerAPI).dynamic(paramString, paramArrayOfObject);
      return null;
    }

    public void j(float paramFloat)
    {
      if ((EntityPlayer.serverPlayerAPI != null) && (EntityPlayer.serverPlayerAPI.isAddExhaustionModded))
        ServerPlayerAPI.addExhaustion(this, paramFloat);
      else
        super.j(paramFloat);
    }

    public final void realAddExhaustion(float paramFloat)
    {
      j(paramFloat);
    }

    public final void superAddExhaustion(float paramFloat)
    {
      super.j(paramFloat);
    }

    public final void localAddExhaustion(float paramFloat)
    {
      super.j(paramFloat);
    }
    
    public void giveExp(int paramInt)
    {
      if ((this.serverPlayerAPI != null) && (this.serverPlayerAPI.isAddExperienceModded))
        ServerPlayerAPI.addExperience(this, paramInt);
      else
        super.giveExp(paramInt);
    }

    public final void realAddExperience(int paramInt)
    {
      giveExp(paramInt);
    }

    public final void superAddExperience(int paramInt)
    {
      super.giveExp(paramInt);
    }

    public final void localAddExperience(int paramInt)
    {
      super.giveExp(paramInt);
    }

    public void levelDown(int paramInt)
    {
      if ((this.serverPlayerAPI != null) && (this.serverPlayerAPI.isAddExperienceLevelModded))
        ServerPlayerAPI.addExperienceLevel(this, paramInt);
      else
        localAddExperienceLevel(paramInt);
    }

    public final void realAddExperienceLevel(int paramInt)
    {
      levelDown(paramInt);
    }

    public final void superAddExperienceLevel(int paramInt)
    {
      super.levelDown(paramInt);
    }

    public final void localAddExperienceLevel(int paramInt)
    {
      super.levelDown(paramInt);
      this.lastSentExp = -1;
    }

    public void checkMovement(double paramDouble1, double paramDouble2, double paramDouble3)
    {
      if ((this.serverPlayerAPI != null) && (this.serverPlayerAPI.isAddMovementStatModded))
        ServerPlayerAPI.addMovementStat(this, paramDouble1, paramDouble2, paramDouble3);
      else
        super.checkMovement(paramDouble1, paramDouble2, paramDouble3);
    }

    public final void realAddMovementStat(double paramDouble1, double paramDouble2, double paramDouble3)
    {
      checkMovement(paramDouble1, paramDouble2, paramDouble3);
    }

    public final void superAddMovementStat(double paramDouble1, double paramDouble2, double paramDouble3)
    {
      super.checkMovement(paramDouble1, paramDouble2, paramDouble3);
    }

    public final void localAddMovementStat(double paramDouble1, double paramDouble2, double paramDouble3)
    {
      super.checkMovement(paramDouble1, paramDouble2, paramDouble3);
    }

    public boolean damageEntity(DamageSource paramDamageSource, int paramInt)
    {
      boolean bool;
      if ((this.serverPlayerAPI != null) && (this.serverPlayerAPI.isAttackEntityFromModded))
        bool = ServerPlayerAPI.attackEntityFrom(this, paramDamageSource, paramInt);
      else
        bool = localAttackEntityFrom(paramDamageSource, paramInt);
      return bool;
    }

    public final boolean realAttackEntityFrom(DamageSource paramDamageSource, int paramInt)
    {
      return damageEntity(paramDamageSource, paramInt);
    }

    public final boolean superAttackEntityFrom(DamageSource paramDamageSource, int paramInt)
    {
      return super.damageEntity(paramDamageSource, paramInt);
    }

    public final boolean localAttackEntityFrom(DamageSource paramDamageSource, int paramInt)
    {
      if (isInvulnerable())
        return false;
      int i = (this.server.T()) && (this.world.pvpMode) && ("fall".equals(paramDamageSource.translationIndex)) ? 1 : 0;
      if ((i == 0) && (this.invulnerableTicks > 0) && (paramDamageSource != DamageSource.OUT_OF_WORLD))
        return false;
      if ((paramDamageSource instanceof EntityDamageSource))
      {
        Entity localEntity = paramDamageSource.getEntity();
        if (((localEntity instanceof EntityHuman)) && (!a((EntityHuman)localEntity)))
          return false;
        if ((localEntity instanceof EntityArrow))
        {
          EntityArrow localEntityArrow = (EntityArrow)localEntity;
          if (((localEntityArrow.shooter instanceof EntityHuman)) && (!a((EntityHuman)localEntityArrow.shooter)))
            return false;
        }
      }
      return super.damageEntity(paramDamageSource, paramInt);
    }

    public void attack(Entity paramEntity)
    {
      if ((this.serverPlayerAPI != null) && (this.serverPlayerAPI.isAttackTargetEntityWithCurrentItemModded))
        ServerPlayerAPI.attackTargetEntityWithCurrentItem(this, paramEntity);
      else
        super.attack(paramEntity);
    }

    public final void realAttackTargetEntityWithCurrentItem(Entity paramEntity)
    {
      attack(paramEntity);
    }

    public final void superAttackTargetEntityWithCurrentItem(Entity paramEntity)
    {
      super.attack(paramEntity);
    }

    public final void localAttackTargetEntityWithCurrentItem(Entity paramEntity)
    {
      super.attack(paramEntity);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("playerGameType")) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.playerInteractManager.setGameMode(MinecraftServer.getServer().getGamemode());
            }
            else {
                this.playerInteractManager.setGameMode(EnumGamemode.a(nbttagcompound.getInt("playerGameType")));
            }
        }
        this.getBukkitEntity().readExtraData(nbttagcompound);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("playerGameType", this.playerInteractManager.getGameMode().a());
        this.getBukkitEntity().setExtraData(nbttagcompound);
    }
    
    public void spawnIn(World world) {
        super.spawnIn(world);
        if (world == null) {
            this.dead = false;
            ChunkCoordinates position = null;
            if (this.spawnWorld != null && !this.spawnWorld.equals("")) {
                final CraftWorld cworld = (CraftWorld)Bukkit.getServer().getWorld(this.spawnWorld);
                if (cworld != null && this.getBed() != null) {
                    world = cworld.getHandle();
                    position = EntityHuman.getBed(cworld.getHandle(), this.getBed(), false);
                }
            }
            if (world == null || position == null) {
            	
                world = Bukkit.getServer().getWorlds().get(0).getHandle();
                position = world.getSpawn();
            }
            this.world = world;
            this.setPosition(position.x + 0.5, position.y, position.z + 0.5);
        }
        this.dimension = ((WorldServer)this.world).dimension;
        this.playerInteractManager.a((WorldServer)world);
    }
    
    public void syncInventory() {
        this.activeContainer.addSlotListener(this);
    }
    
    protected void e_() {
        this.height = 0.0f;
    }
    
    public float getHeadHeight() {
        return 1.62f;
    }
    
    public void l_() {
        this.playerInteractManager.a();
        --this.invulnerableTicks;
        this.activeContainer.b();
        if (!this.activeContainer.a(this)) {
            this.closeInventory();
            this.activeContainer = this.defaultContainer;
        }
        while (!this.removeQueue.isEmpty()) {
            final int i = Math.min(this.removeQueue.size(), 127);
            final int[] aint = new int[i];
            final Iterator iterator = this.removeQueue.iterator();
            int j = 0;
            while (iterator.hasNext() && j < i) {
                aint[j++] = (int) iterator.next();
                iterator.remove();
            }
            this.playerConnection.sendPacket(new Packet29DestroyEntity(aint));
        }
        if (!this.chunkCoordIntPairQueue.isEmpty()) {
            final ArrayList<Chunk> arraylist = new ArrayList();
            final Iterator iterator2 = this.chunkCoordIntPairQueue.iterator();
            final ArrayList<TileEntity> arraylist2 = new ArrayList();
            while (iterator2.hasNext() && arraylist.size() < 5) {
                final ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) iterator2.next();
                iterator2.remove();
                if (chunkcoordintpair != null && this.world.isLoaded(chunkcoordintpair.x << 4, 0, chunkcoordintpair.z << 4)) {
                    final Chunk chunk = this.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
                    arraylist.add(chunk);
                    arraylist2.addAll(chunk.tileEntities.values());
                }
            }
            if (!arraylist.isEmpty()) {
                this.playerConnection.sendPacket(new Packet56MapChunkBulk(arraylist));
                for (final TileEntity tileentity : arraylist2) {
                    this.b(tileentity);
                }
                for (final Chunk chunk : arraylist) {
                    this.o().getTracker().a(this, chunk);
                }
            }
        }
    }
    
    public void setHealth(final int i) {
        super.setHealth(i);
        this.world.getServer().getScoreboardManager().updateAllScoresForList(IScoreboardCriteria.f, this.getLocalizedName(), ImmutableList.of(this));
    }
    
    public void g() {
        try {
            super.l_();
            for (int i = 0; i < this.inventory.getSize(); ++i) {
                final ItemStack itemstack = this.inventory.getItem(i);
                if (itemstack != null && Item.byId[itemstack.id].f() && this.playerConnection.lowPriorityCount() <= 5) {
                    final Packet packet = ((ItemWorldMapBase)Item.byId[itemstack.id]).c(itemstack, this.world, this);
                    if (packet != null) {
                        this.playerConnection.sendPacket(packet);
                    }
                }
            }
            if (this.getHealth() != this.cm || this.cn != this.foodData.a() || this.foodData.e() == 0.0f != this.co) {
                this.playerConnection.sendPacket(new Packet8UpdateHealth(this.getScaledHealth(), this.foodData.a(), this.foodData.e()));
                this.cm = this.getHealth();
                this.cn = this.foodData.a();
                this.co = (this.foodData.e() == 0.0f);
            }
            if (this.expTotal != this.lastSentExp) {
                this.lastSentExp = this.expTotal;
                this.playerConnection.sendPacket(new Packet43SetExperience(this.exp, this.expTotal, this.expLevel));
            }
            if (this.oldLevel == -1) {
                this.oldLevel = this.expLevel;
            }
            if (this.oldLevel != this.expLevel) {
                CraftEventFactory.callPlayerLevelChangeEvent(this.world.getServer().getPlayer(this), this.oldLevel, this.expLevel);
                this.oldLevel = this.expLevel;
            }
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");
            this.a(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }
    
    public void die(final DamageSource damagesource) {
        if (this.dead) {
            return;
        }
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        final boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
        if (!keepInventory) {
            for (int i = 0; i < this.inventory.items.length; ++i) {
                if (this.inventory.items[i] != null) {
                    loot.add(CraftItemStack.asCraftMirror(this.inventory.items[i]));
                }
            }
            for (int i = 0; i < this.inventory.armor.length; ++i) {
                if (this.inventory.armor[i] != null) {
                    loot.add(CraftItemStack.asCraftMirror(this.inventory.armor[i]));
                }
            }
        }
        final PlayerDeathEvent event = CraftEventFactory.callPlayerDeathEvent(this, loot, this.bt.b());
        final String deathMessage = event.getDeathMessage();
        if (deathMessage != null && deathMessage.length() > 0) {
            this.server.getPlayerList().k(event.getDeathMessage());
        }
        if (!keepInventory) {
            for (int j = 0; j < this.inventory.items.length; ++j) {
                this.inventory.items[j] = null;
            }
            for (int j = 0; j < this.inventory.armor.length; ++j) {
                this.inventory.armor[j] = null;
            }
        }
        this.closeInventory();
        final Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.c, this.getLocalizedName(), new ArrayList<ScoreboardScore>());
        for (final ScoreboardScore scoreboardscore : collection) {
            scoreboardscore.incrementScore();
        }
        final EntityLiving entityliving = this.bN();
        if (entityliving != null) {
            entityliving.c(this, this.aM);
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.world.pvpMode && super.a(entityhuman);
    }
    
    public void c(final int i) {
        if (this.dimension == 1 && i == 1) {
            this.a(AchievementList.C);
            this.world.kill(this);
            this.viewingCredits = true;
            this.playerConnection.sendPacket(new Packet70Bed(4, 0));
        }
        else {
            if (this.dimension == 1 && i == 0) {
                this.a(AchievementList.B);
            }
            else {
                this.a(AchievementList.x);
            }
            final PlayerTeleportEvent.TeleportCause cause = (this.dimension == 1 || i == 1) ? PlayerTeleportEvent.TeleportCause.END_PORTAL : PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
            this.server.getPlayerList().changeDimension(this, i, cause);
            this.lastSentExp = -1;
            this.cm = -1;
            this.cn = -1;
        }
    }
    
    private void b(final TileEntity tileentity) {
        if (tileentity != null) {
            final Packet packet = tileentity.getUpdatePacket();
            if (packet != null) {
                this.playerConnection.sendPacket(packet);
            }
        }
    }
    
    public void receive(final Entity entity, final int i) {
        super.receive(entity, i);
        this.activeContainer.b();
    }
    
    public EnumBedResult a(final int i, final int j, final int k) {
        final EnumBedResult enumbedresult = super.a(i, j, k);
        if (enumbedresult == EnumBedResult.OK) {
            final Packet17EntityLocationAction packet17entitylocationaction = new Packet17EntityLocationAction(this, 0, i, j, k);
            this.o().getTracker().a(this, packet17entitylocationaction);
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.playerConnection.sendPacket(packet17entitylocationaction);
        }
        return enumbedresult;
    }
    
    public void a(final boolean flag, final boolean flag1, final boolean flag2) {
        if (!this.sleeping) {
            return;
        }
        if (this.isSleeping()) {
            this.o().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(this, 3));
        }
        super.a(flag, flag1, flag2);
        if (this.playerConnection != null) {
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        }
    }
    
    public void mount(final Entity entity) {
        this.setPassengerOf(entity);
    }
    
    public void setPassengerOf(final Entity entity) {
        super.setPassengerOf(entity);
        this.playerConnection.sendPacket(new Packet39AttachEntity(this, this.vehicle));
        this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }
    
    protected void a(final double d0, final boolean flag) {
    }
    
    public void b(final double d0, final boolean flag) {
        super.a(d0, flag);
    }
    
    public int nextContainerCounter() {
        return this.containerCounter = this.containerCounter % 100 + 1;
    }
    
    public void startCrafting(final int i, final int j, final int k) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerWorkbench(this.inventory, this.world, i, j, k));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 1, "Crafting", 9, true));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void startEnchanting(final int i, final int j, final int k, final String s) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerEnchantTable(this.inventory, this.world, i, j, k));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 4, (s == null) ? "" : s, 9, s != null));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openAnvil(final int i, final int j, final int k) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerAnvil(this.inventory, this.world, i, j, k, this));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 8, "Repairing", 9, true));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openContainer(final IInventory iinventory) {
        if (this.activeContainer != this.defaultContainer) {
            this.closeInventory();
        }
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerChest(this.inventory, iinventory));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 0, iinventory.getName(), iinventory.getSize(), iinventory.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openHopper(final TileEntityHopper tileentityhopper) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHopper(this.inventory, tileentityhopper));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 9, tileentityhopper.getName(), tileentityhopper.getSize(), tileentityhopper.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openMinecartHopper(final EntityMinecartHopper entityminecarthopper) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHopper(this.inventory, entityminecarthopper));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 9, entityminecarthopper.getName(), entityminecarthopper.getSize(), entityminecarthopper.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openFurnace(final TileEntityFurnace tileentityfurnace) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerFurnace(this.inventory, tileentityfurnace));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 2, tileentityfurnace.getName(), tileentityfurnace.getSize(), tileentityfurnace.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openDispenser(final TileEntityDispenser tileentitydispenser) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerDispenser(this.inventory, tileentitydispenser));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, (tileentitydispenser instanceof TileEntityDropper) ? 10 : 3, tileentitydispenser.getName(), tileentitydispenser.getSize(), tileentitydispenser.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openBrewingStand(final TileEntityBrewingStand tileentitybrewingstand) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerBrewingStand(this.inventory, tileentitybrewingstand));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 5, tileentitybrewingstand.getName(), tileentitybrewingstand.getSize(), tileentitybrewingstand.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openBeacon(final TileEntityBeacon tileentitybeacon) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerBeacon(this.inventory, tileentitybeacon));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 7, tileentitybeacon.getName(), tileentitybeacon.getSize(), tileentitybeacon.c()));
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }
    
    public void openTrade(final IMerchant imerchant, final String s) {
        final Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerMerchant(this.inventory, imerchant, this.world));
        if (container == null) {
            return;
        }
        this.nextContainerCounter();
        this.activeContainer = container;
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
        final InventoryMerchant inventorymerchant = ((ContainerMerchant)this.activeContainer).getMerchantInventory();
        this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 6, (s == null) ? "" : s, inventorymerchant.getSize(), s != null));
        final MerchantRecipeList merchantrecipelist = imerchant.getOffers(this);
        if (merchantrecipelist != null) {
            try {
                final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                final DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(this.containerCounter);
                merchantrecipelist.a(dataoutputstream);
                this.playerConnection.sendPacket(new Packet250CustomPayload("MC|TrList", bytearrayoutputstream.toByteArray()));
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }
    }
    
    public void a(final Container container, final int i, final ItemStack itemstack) {
        if (!(container.getSlot(i) instanceof SlotResult) && !this.h) {
            this.playerConnection.sendPacket(new Packet103SetSlot(container.windowId, i, itemstack));
        }
    }
    
    public void updateInventory(final Container container) {
        this.a(container, container.a());
    }
    
    public void a(final Container container, final List list) {
        this.playerConnection.sendPacket(new Packet104WindowItems(container.windowId, list));
        this.playerConnection.sendPacket(new Packet103SetSlot(-1, -1, this.inventory.getCarried()));
        if (EnumSet.of(InventoryType.CRAFTING, InventoryType.WORKBENCH).contains(container.getBukkitView().getType())) {
            this.playerConnection.sendPacket(new Packet103SetSlot(container.windowId, 0, container.getSlot(0).getItem()));
        }
    }
    
    public void setContainerData(final Container container, final int i, final int j) {
        this.playerConnection.sendPacket(new Packet105CraftProgressBar(container.windowId, i, j));
    }
    
    public void closeInventory() {
        CraftEventFactory.handleInventoryCloseEvent(this);
        this.playerConnection.sendPacket(new Packet101CloseWindow(this.activeContainer.windowId));
        this.j();
    }
    
    public void broadcastCarriedItem() {
        if (!this.h) {
            this.playerConnection.sendPacket(new Packet103SetSlot(-1, -1, this.inventory.getCarried()));
        }
    }
    
    public void j() {
        this.activeContainer.b(this);
        this.activeContainer = this.defaultContainer;
    }
    
    public void a(final Statistic statistic, int i) {
        if (statistic != null && !statistic.f) {
            while (i > 100) {
                this.playerConnection.sendPacket(new Packet200Statistic(statistic.e, 100));
                i -= 100;
            }
            this.playerConnection.sendPacket(new Packet200Statistic(statistic.e, i));
        }
    }
    
    public void k() {
        if (this.passenger != null) {
            this.passenger.mount(this);
        }
        if (this.sleeping) {
            this.a(true, false, false);
        }
    }
    
    public void triggerHealthUpdate() {
        this.cm = -99999999;
        this.lastSentExp = -1;
    }
    
    public void b(final String s) {
        final LocaleLanguage localelanguage = LocaleLanguage.a();
        final String s2 = localelanguage.a(s);
        this.playerConnection.sendPacket(new Packet3Chat(s2));
    }
    
    protected void m() {
        this.playerConnection.sendPacket(new Packet38EntityStatus(this.id, (byte)9));
        super.m();
    }
    
    public void a(final ItemStack itemstack, final int i) {
        super.a(itemstack, i);
        if (itemstack != null && itemstack.getItem() != null && itemstack.getItem().b_(itemstack) == EnumAnimation.EAT) {
            this.o().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(this, 5));
        }
    }
    
    public void copyTo(final EntityHuman entityhuman, final boolean flag) {
        super.copyTo(entityhuman, flag);
        this.lastSentExp = -1;
        this.cm = -1;
        this.cn = -1;
        this.removeQueue.addAll(((EntityPlayer)entityhuman).removeQueue);
    }
    
    protected void a(final MobEffect mobeffect) {
        super.a(mobeffect);
        this.playerConnection.sendPacket(new Packet41MobEffect(this.id, mobeffect));
    }
    
    protected void b(final MobEffect mobeffect) {
        super.b(mobeffect);
        this.playerConnection.sendPacket(new Packet41MobEffect(this.id, mobeffect));
    }
    
    protected void c(final MobEffect mobeffect) {
        super.c(mobeffect);
        this.playerConnection.sendPacket(new Packet42RemoveMobEffect(this.id, mobeffect));
    }
    
    public void enderTeleportTo(final double d0, final double d1, final double d2) {
        this.playerConnection.a(d0, d1, d2, this.yaw, this.pitch);
    }
    
    public void b(final Entity entity) {
        this.o().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(entity, 6));
    }
    
    public void c(final Entity entity) {
        this.o().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(entity, 7));
    }
    
    public void updateAbilities() {
        if (this.playerConnection != null) {
            this.playerConnection.sendPacket(new Packet202Abilities(this.abilities));
        }
    }
    
    public WorldServer o() {
        return (WorldServer)this.world;
    }
    
    public void a(final EnumGamemode enumgamemode) {
        this.playerInteractManager.setGameMode(enumgamemode);
        this.playerConnection.sendPacket(new Packet70Bed(3, enumgamemode.a()));
    }
    
    public void sendMessage(final String s) {
        this.playerConnection.sendPacket(new Packet3Chat(s));
    }
    
    public boolean a(final int i, final String s) {
        return ("seed".equals(s) && !this.server.T()) || "tell".equals(s) || "help".equals(s) || "me".equals(s) || this.server.getPlayerList().isOp(this.name);
    }
    
    public String p() {
        String s = this.playerConnection.networkManager.getSocketAddress().toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }
    
    public void a(final Packet204LocaleAndViewDistance packet204localeandviewdistance) {
        if (this.locale.b().containsKey(packet204localeandviewdistance.d())) {
            this.locale.a(packet204localeandviewdistance.d(), false);
        }
        final int i = 256 >> packet204localeandviewdistance.f();
        if (i > 3 && i < 15) {
            this.cr = i;
        }
        this.cs = packet204localeandviewdistance.g();
        this.ct = packet204localeandviewdistance.h();
        if (this.server.I() && this.server.H().equals(this.name)) {
            this.server.c(packet204localeandviewdistance.i());
        }
        this.b(1, !packet204localeandviewdistance.j());
    }
    
    public LocaleLanguage getLocale() {
        return this.locale;
    }
    
    public int getChatFlags() {
        return this.cs;
    }
    
    public void a(final String s, final int i) {
        final String s2 = s + "\u0000" + i;
        this.playerConnection.sendPacket(new Packet250CustomPayload("MC|TPack", s2.getBytes()));
    }
    
    public ChunkCoordinates b() {
        return new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY + 0.5), MathHelper.floor(this.locZ));
    }
    
    public long getPlayerTime() {
        if (this.relativeTime) {
            return this.world.getDayTime() + this.timeOffset;
        }
        return this.world.getDayTime() - this.world.getDayTime() % 24000L + this.timeOffset;
    }
    
    public WeatherType getPlayerWeather() {
        return this.weather;
    }
    
    public void setPlayerWeather(final WeatherType type, final boolean plugin) {
        if (!plugin && this.weather != null) {
            return;
        }
        if (plugin) {
            this.weather = type;
        }
        this.playerConnection.sendPacket(new Packet70Bed((type == WeatherType.DOWNFALL) ? 1 : 2, 0));
    }
    
    public void resetPlayerWeather() {
        this.weather = null;
        this.setPlayerWeather(this.o().getWorldData().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
    }
    
    public String toString() {
        return super.toString() + "(" + this.name + " at " + this.locX + "," + this.locY + "," + this.locZ + ")";
    }
    
    public void reset() {
        float exp = 0.0f;
        final boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
        if (this.keepLevel || keepInventory) {
            exp = this.exp;
            this.newTotalExp = this.expTotal;
            this.newLevel = this.expLevel;
        }
        this.health = this.maxHealth;
        this.fireTicks = 0;
        this.fallDistance = 0.0f;
        this.foodData = new FoodMetaData();
        this.expLevel = this.newLevel;
        this.expTotal = this.newTotalExp;
        this.exp = 0.0f;
        this.deathTicks = 0;
        this.effects.clear();
        this.updateEffects = true;
        this.activeContainer = this.defaultContainer;
        this.killer = null;
        this.lastDamager = null;
        this.bt = new CombatTracker(this);
        this.lastSentExp = -1;
        if (this.keepLevel || keepInventory) {
            this.exp = exp;
        }
        else {
            this.giveExp(this.newExp);
        }
        this.keepLevel = false;
    }
    
    public CraftPlayer getBukkitEntity() {
        return (CraftPlayer)super.getBukkitEntity();
    }
}
