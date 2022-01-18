// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class WorldLoader implements Convertable
{
    protected final File a;
    
    public WorldLoader(final File a) {
        if (!a.exists()) {
            a.mkdirs();
        }
        this.a = a;
    }
    
    public void d() {
    }
    
    public WorldData c(final String s) {
        final File file = new File(this.a, s);
        if (!file.exists()) {
            return null;
        }
        final File file2 = new File(file, "level.dat");
        if (file2.exists()) {
            try {
                return new WorldData(NBTCompressedStreamTools.a(new FileInputStream(file2)).getCompound("Data"));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        final File file3 = new File(file, "level.dat_old");
        if (file3.exists()) {
            try {
                return new WorldData(NBTCompressedStreamTools.a(new FileInputStream(file3)).getCompound("Data"));
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        return null;
    }
    
    public boolean e(final String s) {
        final File file = new File(this.a, s);
        if (!file.exists()) {
            return true;
        }
        System.out.println("Deleting level " + s);
        for (int i = 1; i <= 5; ++i) {
            System.out.println("Attempt " + i + "...");
            if (a(file.listFiles())) {
                break;
            }
            System.out.println("Unsuccessful in deleting contents.");
            if (i < 5) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {}
            }
        }
        return file.delete();
    }
    
    protected static boolean a(final File[] array) {
        for (int i = 0; i < array.length; ++i) {
            final File file = array[i];
            System.out.println("Deleting " + file);
            if (file.isDirectory() && !a(file.listFiles())) {
                System.out.println("Couldn't delete directory " + file);
                return false;
            }
            if (!file.delete()) {
                System.out.println("Couldn't delete file " + file);
                return false;
            }
        }
        return true;
    }
    
    public IDataManager a(final String s, final boolean flag) {
        return new WorldNBTStorage(this.a, s, flag);
    }
    
    public boolean isConvertable(final String s) {
        return false;
    }
    
    public boolean convert(final String s, final IProgressUpdate progressUpdate) {
        return false;
    }
}
