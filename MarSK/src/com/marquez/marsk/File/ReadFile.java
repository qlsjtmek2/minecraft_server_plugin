/*    */ package com.marquez.marsk.File;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReadFile
/*    */ {
/*    */   public static List<String> FileRead(File filename)
/*    */   {
/*    */     try
/*    */     {
/* 13 */       BufferedReader r = new BufferedReader(new FileReader(filename));
/* 14 */       String s = "";
/* 15 */       List list = new ArrayList();
/* 16 */       while ((s = r.readLine()) != null) {
/* 17 */         list.add(s);
/*    */       }
/* 19 */       r.close();
/* 20 */       return list; } catch (IOException e) {
/*    */     }
/* 22 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.File.ReadFile
 * JD-Core Version:    0.6.0
 */