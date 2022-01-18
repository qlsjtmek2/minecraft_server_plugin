/*     */ package com.marquez.marsk;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class MCommand
/*     */   implements CommandExecutor
/*     */ {
/*  16 */   public static HashMap<Player, List<Location>> hash = new HashMap();
/*  17 */   public static String prefix = "§b§l[MarSK] ";
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  20 */     if ((sender instanceof Player)) {
/*  21 */       Player p = (Player)sender;
/*  22 */       if ((label.equalsIgnoreCase("ska")) && 
/*  23 */         (p.isOp())) {
/*  24 */         if (args.length == 0) {
/*  25 */           p.sendMessage(prefix + "§f§l/ska select §7§l- 구역을 선택합니다. [On/Off]");
/*  26 */           p.sendMessage(prefix + "§f§l/ska create [이름] §7§l- 구역을 생성합니다.");
/*  27 */           p.sendMessage(prefix + "§f§l/ska delete [이름] §7§l- 구역을 삭제합니다.");
/*  28 */           p.sendMessage(prefix + "§f§l/ska list §7§l- 목록을 확인합니다.");
/*  29 */           p.sendMessage(prefix + "§f§l/ska save §7§l- 데이터를 저장합니다.");
/*  30 */           p.sendMessage(prefix + "§f§l/ska load §7§l- 데이터를 불러옵니다.");
/*     */         }
/*     */         else
/*     */         {
/*     */           String str1;
/*  32 */           switch ((str1 = args[0]).hashCode()) { case -1352294148:
/*  32 */             if (str1.equals("create"));
/*  32 */             break;
/*     */           case -1335458389:
/*  32 */             if (str1.equals("delete"));
/*  32 */             break;
/*     */           case -906021636:
/*  32 */             if (str1.equals("select")) break; break;
/*     */           case 3322014:
/*  32 */             if (str1.equals("list"));
/*  32 */             break;
/*     */           case 3327206:
/*  32 */             if (str1.equals("load"));
/*  32 */             break;
/*     */           case 3522941:
/*  32 */             if (!str1.equals("save")) { break label1099;
/*     */ 
/*  34 */               if (hash.get(p) == null) {
/*  35 */                 List location = new ArrayList();
/*  36 */                 location.add(null);
/*  37 */                 location.add(null);
/*  38 */                 hash.put(p, location);
/*  39 */                 p.sendMessage(prefix + "§e맨손으로 우클릭, 좌클릭을 하여 구역을 설정해주세요.");
/*  40 */                 break label1127;
/*  41 */               }hash.remove(p);
/*  42 */               p.sendMessage(prefix + "§c구역 선택 모드가 해제되었습니다.");
/*     */ 
/*  44 */               break label1127;
/*     */ 
/*  46 */               if (args.length >= 2) {
/*  47 */                 if (hash.get(p) == null) {
/*  48 */                   p.sendMessage(prefix + "§c위치를 지정하지 않았습니다.");
/*  49 */                   break label1127;
/*     */                 }
/*  51 */                 if (((List)hash.get(p)).get(0) == null) {
/*  52 */                   p.sendMessage(prefix + "§c위치 1이 지정되지 않았습니다.");
/*  53 */                   break label1127;
/*  54 */                 }if (((List)hash.get(p)).get(1) == null) {
/*  55 */                   p.sendMessage(prefix + "§c위치 2가 지정되지 않았습니다.");
/*  56 */                   break label1127;
/*     */                 }
/*  58 */                 if (AreaFile.createArea(args[1], (List)hash.get(p))) {
/*  59 */                   p.sendMessage(prefix + "§a구역 '" + args[1] + "' 를 생성하였습니다.");
/*  60 */                   hash.remove(p);
/*  61 */                   break label1127;
/*  62 */                 }p.sendMessage(prefix + "§c이름이 이미 존재하거나, 구역 설정이 올바르지 않습니다.");
/*     */ 
/*  64 */                 break label1127;
/*  65 */               }p.sendMessage(prefix + "§c이름을 입력해주세요.");
/*     */ 
/*  67 */               break label1127;
/*     */ 
/*  69 */               if (args.length >= 2) {
/*  70 */                 if (AreaFile.deleteArea(args[1])) {
/*  71 */                   p.sendMessage(prefix + "§a구역을 삭제하였습니다.");
/*  72 */                   break label1127;
/*  73 */                 }p.sendMessage(prefix + "§c존재하지 않는 이름입니다.");
/*     */ 
/*  75 */                 break label1127;
/*  76 */               }p.sendMessage(prefix + "§c이름을 입력해주세요.");
/*     */ 
/*  78 */               break label1127;
/*     */ 
/*  80 */               p.sendMessage("§f§l:: Area list ::");
/*  81 */               for (Area a : AreaFile.areaArray) {
/*  82 */                 String name = a.getName();
/*  83 */                 List location = a.getLoc();
/*  84 */                 p.sendMessage("§e" + name + " §7(world: " + ((Location)location.get(0)).getWorld().getName() + " " + AreaFile.locationToString((Location)location.get(0)) + " ~ " + AreaFile.locationToString((Location)location.get(1)) + ")");
/*     */               }
/*  86 */               break label1127;
/*     */             } else {
/*  88 */               AreaFile.saveArea();
/*  89 */               p.sendMessage(prefix + "§a성공적으로 데이터를 저장하였습니다.");
/*  90 */               break label1127;
/*     */ 
/*  92 */               AreaFile.loadArea();
/*  93 */               p.sendMessage(prefix + "§a성공적으로 데이터를 불러왔습니다.");
/*  94 */             }break;
/*     */           }
/*  96 */           label1099: p.sendMessage(prefix + "§c올바르지 않은 명령어입니다.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 103 */     label1127: return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.MCommand
 * JD-Core Version:    0.6.0
 */