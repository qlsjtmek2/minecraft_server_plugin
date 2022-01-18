/*     */ package com.marquez.marsk;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLDecoder;
/*     */ import java.security.CodeSource;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Enumeration;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class JarUtils
/*     */ {
/*  21 */   private static boolean RUNNING_FROM_JAR = false;
/*     */ 
/*     */   static {
/*  24 */     URL resource = JarUtils.class.getClassLoader()
/*  25 */       .getResource("plugin.yml");
/*  26 */     if (resource != null)
/*  27 */       RUNNING_FROM_JAR = true;
/*     */   }
/*     */ 
/*     */   public static JarFile getRunningJar()
/*     */     throws IOException
/*     */   {
/*  33 */     if (!RUNNING_FROM_JAR) {
/*  34 */       return null;
/*     */     }
/*  36 */     String path = new File(Main.class.getProtectionDomain()
/*  37 */       .getCodeSource().getLocation().getPath()).getAbsolutePath();
/*  38 */     path = URLDecoder.decode(path, "UTF-8");
/*  39 */     return new JarFile(path);
/*     */   }
/*     */ 
/*     */   public static boolean extractFromJar(String fileName, String dest) throws IOException
/*     */   {
/*  44 */     if (getRunningJar() == null) {
/*  45 */       return false;
/*     */     }
/*  47 */     File file = new File(dest);
/*  48 */     if (file.isDirectory()) {
/*  49 */       file.mkdir();
/*  50 */       return false;
/*     */     }
/*  52 */     if (!file.exists()) {
/*  53 */       file.getParentFile().mkdirs();
/*     */     }
/*     */ 
/*  56 */     JarFile jar = getRunningJar();
/*  57 */     Enumeration e = jar.entries();
/*  58 */     while (e.hasMoreElements()) {
/*  59 */       JarEntry je = (JarEntry)e.nextElement();
/*  60 */       if (!je.getName().contains(fileName)) {
/*     */         continue;
/*     */       }
/*  63 */       InputStream in = new BufferedInputStream(
/*  64 */         jar.getInputStream(je));
/*  65 */       OutputStream out = new BufferedOutputStream(
/*  66 */         new FileOutputStream(file));
/*  67 */       copyInputStream(in, out);
/*  68 */       jar.close();
/*  69 */       return true;
/*     */     }
/*  71 */     jar.close();
/*  72 */     return false;
/*     */   }
/*     */   public static void reg(String file) {
/*     */     try {
/*  76 */       File[] libs = { 
/*  77 */         new File(Main.instance.getDataFolder(), file) };
/*  78 */       for (File lib : libs) {
/*  79 */         if (!lib.exists()) {
/*  80 */           extractFromJar(lib.getName(), 
/*  81 */             lib.getAbsolutePath());
/*     */         }
/*     */       }
/*  84 */       for (File lib : libs) {
/*  85 */         if (!lib.exists()) {
/*  86 */           Main.instance.getLogger().warning(
/*  87 */             "There was a critical error loading My plugin! Could not find lib: " + 
/*  88 */             lib.getName());
/*  89 */           Bukkit.getServer().getPluginManager().disablePlugin(Main.instance);
/*  90 */           return;
/*     */         }
/*  92 */         addClassPath(getJarUrl(lib));
/*     */       }
/*     */     } catch (Exception e) {
/*  95 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
/*     */     try {
/* 101 */       byte[] buff = new byte[4096];
/*     */       int n;
/* 103 */       while ((n = in.read(buff)) > 0)
/*     */       {
/*     */         int n;
/* 104 */         out.write(buff, 0, n);
/*     */       }
/*     */     } finally {
/* 107 */       out.flush();
/* 108 */       out.close();
/* 109 */       in.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void addClassPath(URL url) throws IOException {
/* 114 */     URLClassLoader sysloader = (URLClassLoader)
/* 115 */       ClassLoader.getSystemClassLoader();
/* 116 */     Class sysclass = URLClassLoader.class;
/*     */     try {
/* 118 */       Method method = sysclass.getDeclaredMethod("addURL", 
/* 119 */         new Class[] { URL.class });
/* 120 */       method.setAccessible(true);
/* 121 */       method.invoke(sysloader, new Object[] { url });
/*     */     } catch (Throwable t) {
/* 123 */       t.printStackTrace();
/* 124 */       throw new IOException("Error adding " + url + 
/* 125 */         " to system classloader");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static URL getJarUrl(File file) throws IOException {
/* 130 */     return new URL("jar:" + file.toURI().toURL().toExternalForm() + "!/");
/*     */   }
/*     */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.JarUtils
 * JD-Core Version:    0.6.0
 */