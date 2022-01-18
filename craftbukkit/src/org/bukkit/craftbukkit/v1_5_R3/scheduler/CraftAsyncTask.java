// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scheduler;

import org.bukkit.plugin.Plugin;
import java.util.Map;
import org.bukkit.scheduler.BukkitWorker;
import java.util.LinkedList;

class CraftAsyncTask extends CraftTask
{
    private final LinkedList<BukkitWorker> workers;
    private final Map<Integer, CraftTask> runners;
    
    CraftAsyncTask(final Map<Integer, CraftTask> runners, final Plugin plugin, final Runnable task, final int id, final long delay) {
        super(plugin, task, id, delay);
        this.workers = new LinkedList<BukkitWorker>();
        this.runners = runners;
    }
    
    public boolean isSync() {
        return false;
    }
    
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //     3: astore_1        /* thread */
        //     4: aload_0         /* this */
        //     5: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.workers:Ljava/util/LinkedList;
        //     8: dup            
        //     9: astore_2       
        //    10: monitorenter   
        //    11: aload_0         /* this */
        //    12: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getPeriod:()J
        //    15: ldc2_w          -2
        //    18: lcmp           
        //    19: ifne            25
        //    22: aload_2        
        //    23: monitorexit    
        //    24: return         
        //    25: aload_0         /* this */
        //    26: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.workers:Ljava/util/LinkedList;
        //    29: new             Lorg/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask$1;
        //    32: dup            
        //    33: aload_0         /* this */
        //    34: aload_1         /* thread */
        //    35: invokespecial   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask$1.<init>:(Lorg/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask;Ljava/lang/Thread;)V
        //    38: invokevirtual   java/util/LinkedList.add:(Ljava/lang/Object;)Z
        //    41: pop            
        //    42: aload_2        
        //    43: monitorexit    
        //    44: goto            52
        //    47: astore_3       
        //    48: aload_2        
        //    49: monitorexit    
        //    50: aload_3        
        //    51: athrow         
        //    52: aconst_null    
        //    53: astore_2        /* thrown */
        //    54: aload_0         /* this */
        //    55: invokespecial   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftTask.run:()V
        //    58: jsr             118
        //    61: goto            307
        //    64: astore_3        /* t */
        //    65: aload_3         /* t */
        //    66: astore_2        /* thrown */
        //    67: new             Lorg/apache/commons/lang/UnhandledException;
        //    70: dup            
        //    71: ldc             "Plugin %s generated an exception while executing task %s"
        //    73: iconst_2       
        //    74: anewarray       Ljava/lang/Object;
        //    77: dup            
        //    78: iconst_0       
        //    79: aload_0         /* this */
        //    80: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getOwner:()Lorg/bukkit/plugin/Plugin;
        //    83: invokeinterface org/bukkit/plugin/Plugin.getDescription:()Lorg/bukkit/plugin/PluginDescriptionFile;
        //    88: invokevirtual   org/bukkit/plugin/PluginDescriptionFile.getFullName:()Ljava/lang/String;
        //    91: aastore        
        //    92: dup            
        //    93: iconst_1       
        //    94: aload_0         /* this */
        //    95: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getTaskId:()I
        //    98: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   101: aastore        
        //   102: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   105: aload_2         /* thrown */
        //   106: invokespecial   org/apache/commons/lang/UnhandledException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   109: athrow         
        //   110: astore          4
        //   112: jsr             118
        //   115: aload           4
        //   117: athrow         
        //   118: astore          5
        //   120: aload_0         /* this */
        //   121: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.workers:Ljava/util/LinkedList;
        //   124: dup            
        //   125: astore          6
        //   127: monitorenter   
        //   128: aload_0         /* this */
        //   129: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.workers:Ljava/util/LinkedList;
        //   132: invokevirtual   java/util/LinkedList.iterator:()Ljava/util/Iterator;
        //   135: astore          workers
        //   137: iconst_0       
        //   138: istore          removed
        //   140: aload           workers
        //   142: invokeinterface java/util/Iterator.hasNext:()Z
        //   147: ifeq            182
        //   150: aload           workers
        //   152: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   157: checkcast       Lorg/bukkit/scheduler/BukkitWorker;
        //   160: invokeinterface org/bukkit/scheduler/BukkitWorker.getThread:()Ljava/lang/Thread;
        //   165: aload_1         /* thread */
        //   166: if_acmpne       140
        //   169: aload           workers
        //   171: invokeinterface java/util/Iterator.remove:()V
        //   176: iconst_1       
        //   177: istore          removed
        //   179: goto            182
        //   182: iload           removed
        //   184: ifne            237
        //   187: new             Ljava/lang/IllegalStateException;
        //   190: dup            
        //   191: ldc             "Unable to remove worker %s on task %s for %s"
        //   193: iconst_3       
        //   194: anewarray       Ljava/lang/Object;
        //   197: dup            
        //   198: iconst_0       
        //   199: aload_1         /* thread */
        //   200: invokevirtual   java/lang/Thread.getName:()Ljava/lang/String;
        //   203: aastore        
        //   204: dup            
        //   205: iconst_1       
        //   206: aload_0         /* this */
        //   207: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getTaskId:()I
        //   210: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   213: aastore        
        //   214: dup            
        //   215: iconst_2       
        //   216: aload_0         /* this */
        //   217: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getOwner:()Lorg/bukkit/plugin/Plugin;
        //   220: invokeinterface org/bukkit/plugin/Plugin.getDescription:()Lorg/bukkit/plugin/PluginDescriptionFile;
        //   225: invokevirtual   org/bukkit/plugin/PluginDescriptionFile.getFullName:()Ljava/lang/String;
        //   228: aastore        
        //   229: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   232: aload_2         /* thrown */
        //   233: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   236: athrow         
        //   237: jsr             251
        //   240: goto            291
        //   243: astore          9
        //   245: jsr             251
        //   248: aload           9
        //   250: athrow         
        //   251: astore          10
        //   253: aload_0         /* this */
        //   254: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getPeriod:()J
        //   257: lconst_0       
        //   258: lcmp           
        //   259: ifge            289
        //   262: aload_0         /* this */
        //   263: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.workers:Ljava/util/LinkedList;
        //   266: invokevirtual   java/util/LinkedList.isEmpty:()Z
        //   269: ifeq            289
        //   272: aload_0         /* this */
        //   273: getfield        org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.runners:Ljava/util/Map;
        //   276: aload_0         /* this */
        //   277: invokevirtual   org/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask.getTaskId:()I
        //   280: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   283: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   288: pop            
        //   289: ret             10
        //   291: aload           6
        //   293: monitorexit    
        //   294: goto            305
        //   297: astore          11
        //   299: aload           6
        //   301: monitorexit    
        //   302: aload           11
        //   304: athrow         
        //   305: ret             5
        //   307: return         
        //    LocalVariableTable:
        //  Start  Length  Slot  Name     Signature
        //  -----  ------  ----  -------  ---------------------------------------------------------
        //  65     45      3     t        Ljava/lang/Throwable;
        //  137    100     7     workers  Ljava/util/Iterator;
        //  140    97      8     removed  Z
        //  0      308     0     this     Lorg/bukkit/craftbukkit/v1_5_R3/scheduler/CraftAsyncTask;
        //  4      304     1     thread   Ljava/lang/Thread;
        //  54     254     2     thrown   Ljava/lang/Throwable;
        //    LocalVariableTypeTable:
        //  Start  Length  Slot  Name     Signature
        //  -----  ------  ----  -------  ---------------------------------------------------------
        //  137    100     7     workers  Ljava/util/Iterator<Lorg/bukkit/scheduler/BukkitWorker;>;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  11     24     47     52     Any
        //  25     44     47     52     Any
        //  47     50     47     52     Any
        //  54     58     64     110    Ljava/lang/Throwable;
        //  54     61     110    118    Any
        //  64     115    110    118    Any
        //  128    240    243    251    Any
        //  243    248    243    251    Any
        //  128    294    297    305    Any
        //  297    302    297    305    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0752 (coming from #0488).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2181)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    LinkedList<BukkitWorker> getWorkers() {
        return this.workers;
    }
    
    boolean cancel0() {
        synchronized (this.workers) {
            this.setPeriod(-2L);
            if (this.workers.isEmpty()) {
                this.runners.remove(this.getTaskId());
            }
        }
        return true;
    }
}
