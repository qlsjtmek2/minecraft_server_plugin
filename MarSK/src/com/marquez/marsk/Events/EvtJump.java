/*    */ package com.marquez.marsk.Events;
/*    */ 
/*    */ import ch.njol.skript.lang.Literal;
/*    */ import ch.njol.skript.lang.SkriptEvent;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class EvtJump extends SkriptEvent
/*    */ {
/*    */   public String toString(@Nullable Event arg0, boolean arg1)
/*    */   {
/* 17 */     return "on jump";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0)
/*    */   {
/* 22 */     PlayerMoveEvent e = (PlayerMoveEvent)arg0;
/*    */ 
/* 24 */     return e.getFrom().getY() + 0.41999998688698D == e.getTo().getY();
/*    */   }
/*    */ 
/*    */   public boolean init(Literal<?>[] arg0, int arg1, SkriptParser.ParseResult arg2)
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Events.EvtJump
 * JD-Core Version:    0.6.0
 */