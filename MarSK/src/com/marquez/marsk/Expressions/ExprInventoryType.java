/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.ScriptLoader;
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.event.inventory.InventoryType;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class ExprInventoryType extends SimpleExpression<String>
/*    */ {
/*    */   public Class<? extends String> getReturnType()
/*    */   {
/* 16 */     return String.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 24 */     if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
/* 25 */       Skript.error("'Inventory Type' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
/* 26 */       return false;
/*    */     }
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 32 */     return "slot type";
/*    */   }
/*    */   @Nullable
/*    */   protected String[] get(Event arg0) {
/* 37 */     InventoryClickEvent e = (InventoryClickEvent)arg0;
/* 38 */     String inventorytype = "";
/* 39 */     if (e.getInventory().getType().equals(InventoryType.ANVIL))
/* 40 */       inventorytype = "Anvil";
/* 41 */     else if (e.getInventory().getType().equals(InventoryType.BEACON))
/* 42 */       inventorytype = "Beacon";
/* 43 */     else if (e.getInventory().getType().equals(InventoryType.BREWING))
/* 44 */       inventorytype = "Brewing";
/* 45 */     else if (e.getInventory().getType().equals(InventoryType.CHEST))
/* 46 */       inventorytype = "Chest";
/* 47 */     else if (e.getInventory().getType().equals(InventoryType.CRAFTING))
/* 48 */       inventorytype = "Crafting";
/* 49 */     else if (e.getInventory().getType().equals(InventoryType.CREATIVE))
/* 50 */       inventorytype = "Creative";
/* 51 */     else if (e.getInventory().getType().equals(InventoryType.DISPENSER))
/* 52 */       inventorytype = "Dispenser";
/* 53 */     else if (e.getInventory().getType().equals(InventoryType.DROPPER))
/* 54 */       inventorytype = "Dropper";
/* 55 */     else if (e.getInventory().getType().equals(InventoryType.ENCHANTING))
/* 56 */       inventorytype = "Enchanting";
/* 57 */     else if (e.getInventory().getType().equals(InventoryType.ENDER_CHEST))
/* 58 */       inventorytype = "EnderChest";
/* 59 */     else if (e.getInventory().getType().equals(InventoryType.FURNACE))
/* 60 */       inventorytype = "Furnace";
/* 61 */     else if (e.getInventory().getType().equals(InventoryType.HOPPER))
/* 62 */       inventorytype = "Hopper";
/* 63 */     else if (e.getInventory().getType().equals(InventoryType.MERCHANT))
/* 64 */       inventorytype = "Merchant";
/* 65 */     else if (e.getInventory().getType().equals(InventoryType.PLAYER))
/* 66 */       inventorytype = "Player";
/* 67 */     else if (e.getInventory().getType().equals(InventoryType.WORKBENCH))
/* 68 */       inventorytype = "Workbench";
/* 69 */     return new String[] { inventorytype };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprInventoryType
 * JD-Core Version:    0.6.0
 */