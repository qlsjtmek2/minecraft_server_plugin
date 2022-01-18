/*    */ package com.marquez.marsk.File;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WriteFile
/*    */ {
/*    */   public static void FileWrite(File filename, List<String> text)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       if (!filename.exists()) {
/* 13 */         return;
/*    */       }
/* 15 */       BufferedWriter w = new BufferedWriter(new FileWriter(filename));
/* 16 */       for (int i = 0; i < text.size(); i++) {
/* 17 */         if (i == text.size() - 1) {
/* 18 */           w.append((CharSequence)text.get(i));
/*    */         }
/*    */         else
/* 21 */           w.append((String)text.get(i) + "\r\n");
/*    */       }
/* 23 */       w.flush();
/* 24 */       w.close();
/*    */     }
/*    */     catch (IOException localIOException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.File.WriteFile
 * JD-Core Version:    0.6.0
 */