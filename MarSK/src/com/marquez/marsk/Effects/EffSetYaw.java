/*    */ package com.marquez.marsk.Effects;
/*    */ 
/*    */ import ch.njol.skript.lang.Effect;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EffSetYaw extends Effect
/*    */ {
/*    */   private Expression<Entity> entity;
/*    */   private Expression<Number> yaw;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 17 */     this.entity = arg0[0];
/* 18 */     this.yaw = arg0[1];
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 23 */     return "set yaw";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 27 */     Entity entity = (Entity)this.entity.getSingle(arg0);
/* 28 */     Number yaw = (Number)this.yaw.getSingle(arg0);
/* 29 */     Location loc = entity.getLocation();
/* 30 */     loc.setYaw(yaw.floatValue());
/* 31 */     entity.teleport(loc);
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffSetYaw
 * JD-Core Version:    0.6.0
 */