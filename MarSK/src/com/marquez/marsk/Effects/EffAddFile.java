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
/*    */ public class EffAddFile extends Effect
/*    */ {
/*    */   private Expression<String> text;
/*    */   private Expression<String> location;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 20 */     this.text = arg0[0];
/* 21 */     this.location = arg0[1];
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 26 */     return "addf";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 30 */     String text = (String)this.text.getSingle(arg0);
/* 31 */     File file = new File((String)this.location.getSingle(arg0));
/* 32 */     if ((text == null) || (file == null) || (!file.exists())) {
/* 33 */       return;
/*    */     }
/* 35 */     List list = ReadFile.FileRead(file);
/* 36 */     list.add(text);
/* 37 */     WriteFile.FileWrite(file, list);
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffAddFile
 * JD-Core Version:    0.6.0
 */