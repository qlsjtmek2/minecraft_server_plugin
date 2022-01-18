/*    */ package com.marquez.marsk.Conditions;
/*    */ 
/*    */ import ch.njol.skript.lang.Condition;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import java.io.File;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class CondIsFileExists extends Condition
/*    */ {
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 18 */     this.location = arg0[0];
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 23 */     return "is exists";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0) {
/* 27 */     String file = (String)this.location.getSingle(arg0);
/*    */ 
/* 29 */     return new File(file).exists();
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Conditions.CondIsFileExists
 * JD-Core Version:    0.6.0
 */