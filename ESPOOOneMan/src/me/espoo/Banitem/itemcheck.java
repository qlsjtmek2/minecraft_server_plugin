/*    */ package me.espoo.Banitem;
/*    */ 
/*    */ import java.util.ArrayList;

import me.espoo.oneman.main;
/*    */ 
/*    */ public class itemcheck
/*    */ {
/*    */   private int number;
/*    */   private int Id;
/*    */   private byte Data;
/*    */   private String Reason;
/*    */   private String World;
/*    */   private int check;
/*    */   public static main plugin;
/*    */ 
/*    */   public itemcheck(main instance)
/*    */   {
/* 15 */     plugin = instance;
/*    */   }
/*    */   public itemcheck(ArrayList<String> ls, int id, byte data, String world) {
/* 18 */     for (int x = 0; x < ls.size(); x++) {
/* 19 */       String string = (String)ls.get(x);
/* 20 */       String[] seperated = string.split(":");
/* 21 */       this.Id = Integer.parseInt(seperated[0]);
/* 22 */       this.Data = Byte.parseByte(seperated[1]);
/* 23 */       if (seperated.length == 3) {
/* 24 */         if (this.Id == id) {
/* 25 */           if (this.Data == data) {
/* 26 */             this.check = 1;
/* 27 */             this.number = 1;
/* 28 */             this.Reason = seperated[2].toString();
/* 29 */             break;
/* 30 */           }if (this.Data == -1) {
/* 31 */             this.number = 1;
/* 32 */             this.Reason = seperated[2].toString();
/* 33 */             break;
/*    */           }
/* 35 */           this.number = 0;
/*    */         }
/*    */         else {
/* 38 */           this.number = 0;
/*    */         }
/*    */       }
/* 41 */       else if (seperated.length == 4) {
/* 42 */         this.World = seperated[2].toString().toLowerCase();
/* 43 */         if (this.World.equalsIgnoreCase(world))
/* 44 */           this.check = 1;
/*    */         else {
/* 46 */           this.check = 0;
/*    */         }
/* 48 */         if (this.Id == id) {
/* 49 */           if (this.Data == data) {
/* 50 */             this.number = 1;
/* 51 */             this.Reason = seperated[3].toString();
/* 52 */             break;
/* 53 */           }if (this.Data == -1) {
/* 54 */             this.number = 1;
/* 55 */             this.Reason = seperated[3].toString();
/* 56 */             break;
/*    */           }
/* 58 */           this.number = 0;
/*    */         }
/*    */         else {
/* 61 */           this.number = 0;
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getId() {
/* 67 */     return this.Id;
/*    */   }
/*    */   public int getData() {
/* 70 */     return this.Data;
/*    */   }
/*    */   public String getReason() {
/* 73 */     return this.Reason;
/*    */   }
/*    */   public int getnumber() {
/* 76 */     return this.number;
/*    */   }
/*    */   public String getWorld() {
/* 79 */     return this.World;
/*    */   }
/*    */   public int worldcheck() {
/* 82 */     return this.check;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\[�뜝�럥�닡�뜝�럩逾졾뜝�럥占싸몄낯占쎈쳴�뜝占�]banitem.jar
 * Qualified Name:     com.abcalvin.BanItem.itemcheck
 * JD-Core Version:    0.6.0
 */