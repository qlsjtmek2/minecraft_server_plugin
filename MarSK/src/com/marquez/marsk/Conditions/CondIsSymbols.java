/*    */ package com.marquez.marsk.Conditions;
/*    */ 
/*    */ import ch.njol.skript.lang.Condition;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class CondIsSymbols extends Condition
/*    */ {
/*    */   private Expression<String> text;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 17 */     this.text = arg0[0];
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 22 */     return "contains Symbols";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0) {
/* 26 */     String text = (String)this.text.getSingle(arg0);
/* 27 */     if (text == null) {
/* 28 */       return false;
/*    */     }
/*    */ 
/* 31 */     return !text.equals(StringReplace(text));
/*    */   }
/*    */ 
/*    */   public static String StringReplace(String str)
/*    */   {
/* 36 */     String match = "[^가-힣xfe0-9a-zA-Z\\sㄱ-ㅣ]";
/* 37 */     str = str.replaceAll(match, "");
/* 38 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Conditions.CondIsSymbols
 * JD-Core Version:    0.6.0
 */