/*    */ package com.marquez.marsk.Events;
/*    */ 
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Literal;
/*    */ import ch.njol.skript.lang.SkriptEvent;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Checker;
/*    */ import com.marquez.marsk.AreaFile;
/*    */ import com.marquez.marsk.Locations;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class EvtQuitArea extends SkriptEvent
/*    */ {
/*    */   private Literal<String> area;
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1)
/*    */   {
/* 27 */     return "on quit area at " + this.area;
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0)
/*    */   {
/* 32 */     if (this.area != null)
/*    */     {
/* 37 */       if (!this.area.check(arg0, new Checker(arg0)
/*    */       {
/*    */         public boolean check(String area) {
/* 35 */           return EvtQuitArea.this.LocContains(area, ((PlayerMoveEvent)this.val$arg0).getPlayer());
/*    */         }
/*    */       }))
/* 32 */         return false; 
/* 32 */     }return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Literal<?>[] arg0, int arg1, SkriptParser.ParseResult arg2)
/*    */   {
/* 42 */     String area = (String)arg0[0].getSingle();
/* 43 */     if (AreaFile.findArea(area) == -1) {
/* 44 */       Skript.error("'" + arg0[0] + "' 은(는) 존재하지 않는 이름입니다.");
/* 45 */       return false;
/*    */     }
/* 47 */     this.area = arg0[0];
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean LocContains(String area, Player p) {
/* 52 */     if (Locations.playerArea.get(p) == null) {
/* 53 */       Locations.playerArea.put(p, new ArrayList());
/*    */     }
/* 55 */     if ((AreaFile.findArea(area) == -1) || (!((List)Locations.playerArea.get(p)).contains(area))) {
/* 56 */       return false;
/*    */     }
/* 58 */     if (!Locations.isInPosition(new Locations(AreaFile.foundArea(area)), p.getLocation(), p.getWorld())) {
/* 59 */       if (((List)Locations.playerArea.get(p)).contains(area)) {
/* 60 */         List arealist = (List)Locations.playerArea.get(p);
/* 61 */         arealist.remove(area);
/* 62 */         Locations.playerArea.put(p, arealist);
/* 63 */         return true;
/*    */       }
/* 65 */       return false;
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Events.EvtQuitArea
 * JD-Core Version:    0.6.0
 */