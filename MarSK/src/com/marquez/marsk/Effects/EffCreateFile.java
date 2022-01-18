/*    */ package com.marquez.marsk.Effects;
/*    */ 
/*    */ import ch.njol.skript.lang.Effect;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EffCreateFile extends Effect
/*    */ {
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 17 */     this.location = arg0[0];
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 22 */     return "createf";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 26 */     File file = new File((String)this.location.getSingle(arg0));
/* 27 */     if ((file == null) || (file.exists()))
/* 28 */       return;
/*    */     try
/*    */     {
/* 31 */       file.createNewFile();
/*    */     } catch (IOException e) {
/* 33 */       return;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffCreateFile
 * JD-Core Version:    0.6.0
 */