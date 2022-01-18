/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import com.marquez.marsk.File.ReadFile;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprReadFile extends SimpleExpression<String>
/*    */ {
/*    */   private Expression<String> location;
/*    */ 
/*    */   public Class<? extends String> getReturnType()
/*    */   {
/* 20 */     return String.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 28 */     this.location = arg0[0];
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 33 */     return "readf";
/*    */   }
/*    */   @Nullable
/*    */   protected String[] get(Event arg0) {
/* 38 */     File file = new File((String)this.location.getSingle(arg0));
/* 39 */     if ((file == null) || (!file.exists())) {
/* 40 */       return null;
/*    */     }
/* 42 */     List list = ReadFile.FileRead(file);
/* 43 */     String[] str = new String[list.size()];
/* 44 */     for (int i = 0; i < list.size(); i++) {
/* 45 */       str[i] = ((String)list.get(i));
/*    */     }
/* 47 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprReadFile
 * JD-Core Version:    0.6.0
 */