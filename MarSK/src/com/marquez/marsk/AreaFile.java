/*     */ package com.marquez.marsk;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ public class AreaFile
/*     */ {
/*  18 */   public static List<Area> areaArray = new ArrayList();
/*     */ 
/*     */   public static boolean createArea(String name, List<Location> location) {
/*  21 */     if ((((Location)location.get(0)).getWorld().equals(((Location)location.get(1)).getWorld())) && 
/*  22 */       (findArea(name) == -1)) {
/*  23 */       areaArray.add(new Area(name, location));
/*  24 */       return true;
/*     */     }
/*  26 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean deleteArea(String name) {
/*  30 */     int num = findArea(name);
/*  31 */     if (num != -1) {
/*  32 */       areaArray.remove(num);
/*  33 */       return true;
/*     */     }
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */   public static int findArea(String name) {
/*  39 */     for (int i = 0; i < areaArray.size(); i++) {
/*  40 */       if (((Area)areaArray.get(i)).getName().equals(name)) {
/*  41 */         return i;
/*     */       }
/*     */     }
/*  44 */     return -1;
/*     */   }
/*     */ 
/*     */   public static Area foundArea(String name) {
/*  48 */     for (Area a : areaArray) {
/*  49 */       if (a.getName().equals(name)) {
/*  50 */         return a;
/*     */       }
/*     */     }
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public static void saveArea() {
/*  57 */     File filename = new File("plugins/Skript/Area.sk");
/*     */     try {
/*  59 */       if (filename.exists()) {
/*  60 */         filename.createNewFile();
/*     */       }
/*  62 */       BufferedWriter w = new BufferedWriter(new FileWriter(filename));
/*  63 */       StringBuilder sb = new StringBuilder();
/*  64 */       for (Area a : areaArray) {
/*  65 */         sb.append(areaToText(a.getName(), a.getLoc()));
/*     */       }
/*  67 */       w.append(sb);
/*  68 */       w.flush();
/*  69 */       w.close(); } catch (IOException localIOException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void loadArea() {
/*  74 */     areaArray.clear();
/*  75 */     File filename = new File("plugins/Skript/Area.sk");
/*  76 */     if (filename.exists())
/*     */       try {
/*  78 */         BufferedReader r = new BufferedReader(new FileReader(filename));
/*  79 */         String s = "";
/*  80 */         List list = new ArrayList();
/*  81 */         while ((s = r.readLine()) != null) {
/*  82 */           list.add(s.replace("\t", ""));
/*     */         }
/*  84 */         if (list.size() >= 3) {
/*  85 */           for (int i = 0; i < list.size() / 3; i++) {
/*  86 */             String name = ((String)list.get(i * 3)).replace(":", "");
/*  87 */             World w = Bukkit.getWorld(((String)list.get(i * 3 + 1)).replace("world: ", ""));
/*  88 */             String[] split = ((String)list.get(i * 3 + 2)).replace("location: ", "").split(" ~ ");
/*  89 */             List location = new ArrayList();
/*  90 */             location.add(stringToLocation(w, split[0]));
/*  91 */             location.add(stringToLocation(w, split[1]));
/*  92 */             areaArray.add(new Area(name, location));
/*     */           }
/*     */         }
/*  95 */         r.close();
/*     */       } catch (IOException localIOException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public static String areaToText(String name, List<Location> location) {
/* 101 */     StringBuilder sb = new StringBuilder();
/* 102 */     sb.append(name).append(":\r\n");
/* 103 */     sb.append("\tworld: ").append(((Location)location.get(0)).getWorld().getName()).append("\r\n");
/* 104 */     sb.append("\tlocation: ").append(locationToString((Location)location.get(0))).append(" ~ ").append(locationToString((Location)location.get(1))).append("\r\n");
/* 105 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String locationToString(Location location) {
/* 109 */     StringBuilder sb = new StringBuilder();
/* 110 */     sb.append(location.getBlockX()).append(",");
/* 111 */     sb.append(location.getBlockY()).append(",");
/* 112 */     sb.append(location.getBlockZ());
/* 113 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static Location stringToLocation(World w, String s) {
/* 117 */     String[] split = s.split(",");
/* 118 */     Location location = new Location(w, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
/* 119 */     return location;
/*     */   }
/*     */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.AreaFile
 * JD-Core Version:    0.6.0
 */