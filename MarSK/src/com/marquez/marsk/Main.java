/*     */ package com.marquez.marsk;
/*     */ 
/*     */ import ch.njol.skript.Skript;
/*     */ import ch.njol.skript.lang.ExpressionType;
/*     */ import com.marquez.marsk.Conditions.CondIsAnvil;
/*     */ import com.marquez.marsk.Conditions.CondIsArmour;
/*     */ import com.marquez.marsk.Conditions.CondIsBeacon;
/*     */ import com.marquez.marsk.Conditions.CondIsBrewing;
/*     */ import com.marquez.marsk.Conditions.CondIsChest;
/*     */ import com.marquez.marsk.Conditions.CondIsContainer;
/*     */ import com.marquez.marsk.Conditions.CondIsCrafting;
/*     */ import com.marquez.marsk.Conditions.CondIsCraftingI;
/*     */ import com.marquez.marsk.Conditions.CondIsCreative;
/*     */ import com.marquez.marsk.Conditions.CondIsDispenser;
/*     */ import com.marquez.marsk.Conditions.CondIsDropper;
/*     */ import com.marquez.marsk.Conditions.CondIsEnchanting;
/*     */ import com.marquez.marsk.Conditions.CondIsEnderChest;
/*     */ import com.marquez.marsk.Conditions.CondIsFileExists;
/*     */ import com.marquez.marsk.Conditions.CondIsFuel;
/*     */ import com.marquez.marsk.Conditions.CondIsFurnace;
/*     */ import com.marquez.marsk.Conditions.CondIsHopper;
/*     */ import com.marquez.marsk.Conditions.CondIsInArea;
/*     */ import com.marquez.marsk.Conditions.CondIsLeftAndShiftClick;
/*     */ import com.marquez.marsk.Conditions.CondIsLeftClick;
/*     */ import com.marquez.marsk.Conditions.CondIsMerchant;
/*     */ import com.marquez.marsk.Conditions.CondIsOutside;
/*     */ import com.marquez.marsk.Conditions.CondIsPlayer;
/*     */ import com.marquez.marsk.Conditions.CondIsQuickbar;
/*     */ import com.marquez.marsk.Conditions.CondIsResult;
/*     */ import com.marquez.marsk.Conditions.CondIsRightAndShiftClick;
/*     */ import com.marquez.marsk.Conditions.CondIsRightClick;
/*     */ import com.marquez.marsk.Conditions.CondIsShiftClick;
/*     */ import com.marquez.marsk.Conditions.CondIsSymbols;
/*     */ import com.marquez.marsk.Conditions.CondIsWorkbench;
/*     */ import com.marquez.marsk.Effects.EffAddFile;
/*     */ import com.marquez.marsk.Effects.EffCreateFile;
/*     */ import com.marquez.marsk.Effects.EffDeleteFile;
/*     */ import com.marquez.marsk.Effects.EffDisplayBossBar;
/*     */ import com.marquez.marsk.Effects.EffRemoveBossBar;
/*     */ import com.marquez.marsk.Effects.EffRemoveFile;
/*     */ import com.marquez.marsk.Effects.EffSetFile;
/*     */ import com.marquez.marsk.Effects.EffSetPitch;
/*     */ import com.marquez.marsk.Effects.EffSetYaw;
/*     */ import com.marquez.marsk.Events.EvtEnterArea;
/*     */ import com.marquez.marsk.Events.EvtInventoryOpen;
/*     */ import com.marquez.marsk.Events.EvtJump;
/*     */ import com.marquez.marsk.Events.EvtQuitArea;
/*     */ import com.marquez.marsk.Expressions.ExprCharAt;
/*     */ import com.marquez.marsk.Expressions.ExprClickType;
/*     */ import com.marquez.marsk.Expressions.ExprDecimal;
/*     */ import com.marquez.marsk.Expressions.ExprInPlayers;
/*     */ import com.marquez.marsk.Expressions.ExprInventoryType;
/*     */ import com.marquez.marsk.Expressions.ExprMaxDurability;
/*     */ import com.marquez.marsk.Expressions.ExprMaxHealth;
/*     */ import com.marquez.marsk.Expressions.ExprReadFile;
/*     */ import com.marquez.marsk.Expressions.ExprSlotType;
/*     */ import com.marquez.marsk.Expressions.ExprSortDown;
/*     */ import com.marquez.marsk.Expressions.ExprSortUp;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import me.confuser.barapi.BarAPI;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Main extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*     */   public static Main instance;
/*     */   public MCommand command;
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  36 */     instance = this;
/*  37 */     JarUtils.reg("BarAPI.jar");
/*  38 */     Bukkit.getConsoleSender().sendMessage("§b§l[MarSK] v" + getDescription().getVersion() + " 플러그인 활성화 -Made by Mar(마르)");
/*  39 */     getServer().getPluginManager().registerEvents(this, this);
/*  40 */     Skript.registerAddon(this);
/*  41 */     registerEvents();
/*  42 */     registerEffects();
/*  43 */     registerExpressions();
/*  44 */     registerConditions();
/*  45 */     registercommand();
/*  46 */     AreaFile.loadArea();
/*  47 */     Bukkit.getConsoleSender().sendMessage("§b§l[MarSK] v" + getDescription().getVersion() + " 에드온 연결 완료 -Made by Mar(마르)");
/*     */   }
/*     */ 
/*     */   public void onDisable() {
/*  51 */     AreaFile.saveArea();
/*  52 */     Bukkit.getConsoleSender().sendMessage("§b§l[MarSK]" + getDescription().getVersion() + "플러그인 비활성화 -Made by Mar(마르)");
/*     */   }
/*     */ 
/*     */   public void registercommand() {
/*  56 */     this.command = new MCommand();
/*  57 */     getCommand("ska").setExecutor(this.command);
/*     */   }
/*     */ 
/*     */   public void registerEvents() {
/*  61 */     Skript.registerEvent("enter area", EvtEnterArea.class, PlayerMoveEvent.class, new String[] { "enter area at %string%" });
/*  62 */     Skript.registerEvent("quit area", EvtQuitArea.class, PlayerMoveEvent.class, new String[] { "quit area at %string%" });
/*  63 */     Skript.registerEvent("jump", EvtJump.class, PlayerMoveEvent.class, new String[] { "jump" });
/*  64 */     Skript.registerEvent("inventory open", EvtInventoryOpen.class, InventoryOpenEvent.class, new String[] { "inventory open" });
/*     */   }
/*     */ 
/*     */   public void registerEffects() {
/*  68 */     Skript.registerEffect(EffAddFile.class, new String[] { "addf %string% to %string%", "addfile %string% to %string%", "add file %string% to %string%" });
/*  69 */     Skript.registerEffect(EffSetFile.class, new String[] { "setf line %number% of %string% to %string%", "setfile line %number% of %string% to %string%", "set file line %number% of %string% to %string%" });
/*  70 */     Skript.registerEffect(EffRemoveFile.class, new String[] { "removef line %number% from %string%", "removefile line %number% from %string%", "remove file line %number% from %string%" });
/*  71 */     Skript.registerEffect(EffCreateFile.class, new String[] { "createf %string%", "createfile %string%", "create file %string%" });
/*  72 */     Skript.registerEffect(EffDeleteFile.class, new String[] { "deletef %string%", "deletefile %string%", "delete file %string%" });
/*  73 */     Skript.registerEffect(EffSetYaw.class, new String[] { "set yaw of %entity% to %number%", "set %entity%'s yaw to %number%" });
/*  74 */     Skript.registerEffect(EffSetPitch.class, new String[] { "set pitch of %entity% to %number%", "set %entity%'s pitch to %number%" });
/*  75 */     Skript.registerEffect(EffDisplayBossBar.class, new String[] { "display bar %string% with %number% percent to %player%" });
/*  76 */     Skript.registerEffect(EffRemoveBossBar.class, new String[] { "remove bar from %player%" });
/*     */   }
/*     */ 
/*     */   public void registerExpressions() {
/*  80 */     Skript.registerExpression(ExprReadFile.class, String.class, ExpressionType.PROPERTY, new String[] { "readf %string%", "readfile %string%", "read file %string%" });
/*  81 */     Skript.registerExpression(ExprClickType.class, String.class, ExpressionType.PROPERTY, new String[] { "click type" });
/*  82 */     Skript.registerExpression(ExprSlotType.class, String.class, ExpressionType.PROPERTY, new String[] { "slot type" });
/*  83 */     Skript.registerExpression(ExprInventoryType.class, String.class, ExpressionType.PROPERTY, new String[] { "inventory type" });
/*  84 */     Skript.registerExpression(ExprInPlayers.class, Player.class, ExpressionType.PROPERTY, new String[] { "players in area %string%" });
/*  85 */     Skript.registerExpression(ExprMaxHealth.class, Number.class, ExpressionType.PROPERTY, new String[] { "mh of %player%", "%player%'s mh" });
/*  86 */     Skript.registerExpression(ExprMaxDurability.class, Number.class, ExpressionType.PROPERTY, new String[] { "md of %integer%", "%integer%'s md" });
/*  87 */     Skript.registerExpression(ExprSortUp.class, Number.class, ExpressionType.PROPERTY, new String[] { "sort up %numbers%" });
/*  88 */     Skript.registerExpression(ExprSortDown.class, Number.class, ExpressionType.PROPERTY, new String[] { "sort down %numbers%" });
/*  89 */     Skript.registerExpression(ExprDecimal.class, Number.class, ExpressionType.PROPERTY, new String[] { "decimal with %integer% in %number%" });
/*  90 */     Skript.registerExpression(ExprCharAt.class, Character.class, ExpressionType.PROPERTY, new String[] { "char at %number% in %string%" });
/*     */   }
/*     */ 
/*     */   public void registerConditions()
/*     */   {
/*  97 */     Skript.registerCondition(CondIsRightAndShiftClick.class, new String[] { "%string% is left click and shift click", "%string% is shift click and left click" });
/*  98 */     Skript.registerCondition(CondIsLeftAndShiftClick.class, new String[] { "%string% is right click and shift click", "%string% is shift click and right click" });
/*  99 */     Skript.registerCondition(CondIsLeftClick.class, new String[] { "%string% is left click" });
/* 100 */     Skript.registerCondition(CondIsRightClick.class, new String[] { "%string% is right click" });
/* 101 */     Skript.registerCondition(CondIsShiftClick.class, new String[] { "%string% is shift click" });
/* 102 */     Skript.registerCondition(CondIsArmour.class, new String[] { "%string% is armour" });
/* 103 */     Skript.registerCondition(CondIsContainer.class, new String[] { "%string% is container" });
/* 104 */     Skript.registerCondition(CondIsCrafting.class, new String[] { "%string% is crafting" });
/* 105 */     Skript.registerCondition(CondIsFuel.class, new String[] { "%string% is fuel" });
/* 106 */     Skript.registerCondition(CondIsOutside.class, new String[] { "%string% is outside" });
/* 107 */     Skript.registerCondition(CondIsQuickbar.class, new String[] { "%string% is quickbar" });
/* 108 */     Skript.registerCondition(CondIsResult.class, new String[] { "%string% is result" });
/* 109 */     Skript.registerCondition(CondIsAnvil.class, new String[] { "%string% is anvil" });
/* 110 */     Skript.registerCondition(CondIsBeacon.class, new String[] { "%string% is beacon" });
/* 111 */     Skript.registerCondition(CondIsBrewing.class, new String[] { "%string% is brewing" });
/* 112 */     Skript.registerCondition(CondIsChest.class, new String[] { "%string% is chest" });
/* 113 */     Skript.registerCondition(CondIsCraftingI.class, new String[] { "%string% is crafting" });
/* 114 */     Skript.registerCondition(CondIsCreative.class, new String[] { "%string% is creative" });
/* 115 */     Skript.registerCondition(CondIsDispenser.class, new String[] { "%string% is dispenser" });
/* 116 */     Skript.registerCondition(CondIsDropper.class, new String[] { "%string% is dropper" });
/* 117 */     Skript.registerCondition(CondIsEnchanting.class, new String[] { "%string% is enchanting" });
/* 118 */     Skript.registerCondition(CondIsEnderChest.class, new String[] { "%string% is enderchest" });
/* 119 */     Skript.registerCondition(CondIsFurnace.class, new String[] { "%string% is furnace" });
/* 120 */     Skript.registerCondition(CondIsHopper.class, new String[] { "%string% is hopper" });
/* 121 */     Skript.registerCondition(CondIsMerchant.class, new String[] { "%string% is merchant" });
/* 122 */     Skript.registerCondition(CondIsPlayer.class, new String[] { "%string% is player inventory" });
/* 123 */     Skript.registerCondition(CondIsWorkbench.class, new String[] { "%string% is workbench" });
/* 124 */     Skript.registerCondition(CondIsFileExists.class, new String[] { "file %string% is exists" });
/* 125 */     Skript.registerCondition(CondIsSymbols.class, new String[] { "%string% contains Symbols" });
/* 126 */     Skript.registerCondition(CondIsInArea.class, new String[] { "%player% is in area %string%" });
/*     */   }
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 131 */     if (BarAPI.hasBar(e.getPlayer()))
/* 132 */       BarAPI.removeBar(e.getPlayer());
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e) {
/* 138 */     if (BarAPI.hasBar(e.getPlayer()))
/* 139 */       BarAPI.removeBar(e.getPlayer());
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void select(PlayerInteractEvent e) {
/* 145 */     Player p = e.getPlayer();
/* 146 */     if ((MCommand.hash.get(p) != null) && 
/* 147 */       (p.getItemInHand().getType().equals(Material.AIR))) {
/* 148 */       e.setCancelled(true);
/* 149 */       if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
/* 150 */         List location = (List)MCommand.hash.get(p);
/* 151 */         location.set(0, e.getClickedBlock().getLocation());
/* 152 */         MCommand.hash.put(p, location);
/* 153 */         p.sendMessage(MCommand.prefix + "§e위치 1: " + AreaFile.locationToString(e.getClickedBlock().getLocation()));
/* 154 */       } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 155 */         List location = (List)MCommand.hash.get(p);
/* 156 */         location.set(1, e.getClickedBlock().getLocation());
/* 157 */         MCommand.hash.put(p, location);
/* 158 */         p.sendMessage(MCommand.prefix + "§e위치 2: " + AreaFile.locationToString(e.getClickedBlock().getLocation()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void check(PlayerJoinEvent e) {
/* 166 */     Player p = e.getPlayer();
/* 167 */     String name = p.getName().toString();
/* 168 */     if (name.contains("Marquez_")) {
/* 169 */       p.sendMessage("§f§l[MarSk] §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
/* 170 */       p.sendMessage("§f§l[MarSk] §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
/* 171 */       p.sendMessage("§f§l[MarSk] §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Main
 * JD-Core Version:    0.6.0
 */