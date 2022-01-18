/*    */ package com.marquez.marsk.Effects;
/*    */ 
/*    */ import ch.njol.skript.lang.Effect;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import java.io.File;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EffDeleteFile extends Effect
/*    */ {
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 16 */     this.location = arg0[0];
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 21 */     return "deletef";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 25 */     File file = new File((String)this.location.getSingle(arg0));
/* 26 */     if ((file == null) || (!file.exists())) {
/* 27 */       return;
/*    */     }
/* 29 */     file.delete();
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffDeleteFile
 * JD-Core Version:    0.6.0
 */