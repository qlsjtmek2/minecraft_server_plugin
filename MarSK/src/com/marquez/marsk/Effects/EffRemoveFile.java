/*    */ package com.marquez.marsk.Effects;
/*    */ 
/*    */ import ch.njol.skript.lang.Effect;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import com.marquez.marsk.File.ReadFile;
/*    */ import com.marquez.marsk.File.WriteFile;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EffRemoveFile extends Effect
/*    */ {
/*    */   private Expression<Number> line;
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 21 */     this.line = arg0[0];
/* 22 */     this.location = arg0[1];
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 27 */     return "removef";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 31 */     Number line = (Number)this.line.getSingle(arg0);
/* 32 */     File file = new File((String)this.location.getSingle(arg0));
/* 33 */     if ((line == null) || (file == null) || (!file.exists())) {
/* 34 */       return;
/*    */     }
/* 36 */     List list = ReadFile.FileRead(file);
/* 37 */     if ((line.intValue() < 1) || (line.intValue() >= list.size())) {
/* 38 */       return;
/*    */     }
/* 40 */     list.remove(line.intValue() - 1);
/* 41 */     WriteFile.FileWrite(file, list);
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffRemoveFile
 * JD-Core Version:    0.6.0
 */