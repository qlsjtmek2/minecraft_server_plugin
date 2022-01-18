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
/*    */ public class EffSetFile extends Effect
/*    */ {
/*    */   private Expression<Number> line;
/*    */   private Expression<String> text;
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 22 */     this.line = arg0[0];
/* 23 */     this.text = arg0[1];
/* 24 */     this.location = arg0[2];
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 29 */     return "setf";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 33 */     Number line = (Number)this.line.getSingle(arg0);
/* 34 */     String text = (String)this.text.getSingle(arg0);
/* 35 */     File file = new File((String)this.location.getSingle(arg0));
/* 36 */     if ((line == null) || (text == null) || (file == null) || (!file.exists())) {
/* 37 */       return;
/*    */     }
/* 39 */     List list = ReadFile.FileRead(file);
/* 40 */     if (line.intValue() > list.size()) {
/* 41 */       return;
/*    */     }
/* 43 */     list.set(line.intValue() - 1, text);
/* 44 */     WriteFile.FileWrite(file, list);
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffSetFile
 * JD-Core Version:    0.6.0
 */