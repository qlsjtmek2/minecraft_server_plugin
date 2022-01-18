/*    */ package com.marquez.marsk.Events;
/*    */ 
/*    */ import ch.njol.skript.lang.Literal;
/*    */ import ch.njol.skript.lang.SkriptEvent;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import java.io.PrintStream;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EvtInventoryOpen extends SkriptEvent
/*    */ {
/*    */   public String toString(@Nullable Event arg0, boolean arg1)
/*    */   {
/* 16 */     return "on inventory open";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0)
/*    */   {
/* 21 */     System.out.println("TEST");
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Literal<?>[] arg0, int arg1, SkriptParser.ParseResult arg2)
/*    */   {
/* 27 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Events.EvtInventoryOpen
 * JD-Core Version:    0.6.0
 */