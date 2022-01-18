// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.UUID;
import java.io.File;

public class WorldNBTStorage implements IDataManager, IPlayerFileData
{
    private final File baseDir;
    private final File playerDir;
    private final File dataDir;
    private final long sessionId;
    private final String e;
    private UUID uuid;
    
    public WorldNBTStorage(final File file1, final String s, final boolean flag) {
        this.sessionId = System.currentTimeMillis();
        this.uuid = null;
        (this.baseDir = new File(file1, s)).mkdirs();
        this.playerDir = new File(this.baseDir, "players");
        (this.dataDir = new File(this.baseDir, "data")).mkdirs();
        this.e = s;
        if (flag) {
            this.playerDir.mkdirs();
        }
        this.h();
    }
    
    private void h() {
        try {
            final File file1 = new File(this.baseDir, "session.lock");
            final DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
            try {
                dataoutputstream.writeLong(this.sessionId);
            }
            finally {
                dataoutputstream.close();
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }
    
    public File getDirectory() {
        return this.baseDir;
    }
    
    public void checkSession() throws ExceptionWorldConflict {
        try {
            final File file1 = new File(this.baseDir, "session.lock");
            final DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
            try {
                if (datainputstream.readLong() != this.sessionId) {
                    throw new ExceptionWorldConflict("The save is being accessed from another location, aborting");
                }
            }
            finally {
                datainputstream.close();
            }
        }
        catch (IOException ioexception) {
            throw new ExceptionWorldConflict("Failed to check session lock, aborting");
        }
    }
    
    public IChunkLoader createChunkLoader(final WorldProvider worldprovider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }
    
    public WorldData getWorldData() {
        File file1 = new File(this.baseDir, "level.dat");
        if (file1.exists()) {
            try {
                final NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
                final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("Data");
                return new WorldData(nbttagcompound2);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        file1 = new File(this.baseDir, "level.dat_old");
        if (file1.exists()) {
            try {
                final NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
                final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("Data");
                return new WorldData(nbttagcompound2);
            }
            catch (Exception exception2) {
                exception2.printStackTrace();
            }
        }
        return null;
    }
    
    public void saveWorldData(final WorldData worlddata, final NBTTagCompound nbttagcompound) {
        final NBTTagCompound nbttagcompound2 = worlddata.a(nbttagcompound);
        final NBTTagCompound nbttagcompound3 = new NBTTagCompound();
        nbttagcompound3.set("Data", nbttagcompound2);
        try {
            final File file1 = new File(this.baseDir, "level.dat_new");
            final File file2 = new File(this.baseDir, "level.dat_old");
            final File file3 = new File(this.baseDir, "level.dat");
            NBTCompressedStreamTools.a(nbttagcompound3, new FileOutputStream(file1));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file1.renameTo(file3);
            if (file1.exists()) {
                file1.delete();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void saveWorldData(final WorldData worlddata) {
        final NBTTagCompound nbttagcompound = worlddata.a();
        final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.set("Data", nbttagcompound);
        try {
            final File file1 = new File(this.baseDir, "level.dat_new");
            final File file2 = new File(this.baseDir, "level.dat_old");
            final File file3 = new File(this.baseDir, "level.dat");
            NBTCompressedStreamTools.a(nbttagcompound2, new FileOutputStream(file1));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file1.renameTo(file3);
            if (file1.exists()) {
                file1.delete();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void save(final EntityHuman entityhuman) {
        try {
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            entityhuman.e(nbttagcompound);
            final File file1 = new File(this.playerDir, entityhuman.name + ".dat.tmp");
            final File file2 = new File(this.playerDir, entityhuman.name + ".dat");
            NBTCompressedStreamTools.a(nbttagcompound, new FileOutputStream(file1));
            if (file2.exists()) {
                file2.delete();
            }
            file1.renameTo(file2);
        }
        catch (Exception exception) {
            MinecraftServer.getServer().getLogger().warning("Failed to save player data for " + entityhuman.name);
        }
    }
    
    public NBTTagCompound load(final EntityHuman entityhuman) {
        final NBTTagCompound nbttagcompound = this.getPlayerData(entityhuman.name);
        if (nbttagcompound != null) {
            if (entityhuman instanceof EntityPlayer) {
                final CraftPlayer player = (CraftPlayer)entityhuman.bukkitEntity;
                player.setFirstPlayed(new File(this.playerDir, entityhuman.name + ".dat").lastModified());
            }
            entityhuman.f(nbttagcompound);
        }
        return nbttagcompound;
    }
    
    public NBTTagCompound getPlayerData(final String s) {
        try {
            final File file1 = new File(this.playerDir, s + ".dat");
            if (file1.exists()) {
                return NBTCompressedStreamTools.a(new FileInputStream(file1));
            }
        }
        catch (Exception exception) {
            MinecraftServer.getServer().getLogger().warning("Failed to load player data for " + s);
        }
        return null;
    }
    
    public IPlayerFileData getPlayerFileData() {
        return this;
    }
    
    public String[] getSeenPlayers() {
        final String[] astring = this.playerDir.list();
        for (int i = 0; i < astring.length; ++i) {
            if (astring[i].endsWith(".dat")) {
                astring[i] = astring[i].substring(0, astring[i].length() - 4);
            }
        }
        return astring;
    }
    
    public void a() {
    }
    
    public File getDataFile(final String s) {
        return new File(this.dataDir, s + ".dat");
    }
    
    public String g() {
        return this.e;
    }
    
    public UUID getUUID() {
        if (this.uuid != null) {
            return this.uuid;
        }
        try {
            final File file1 = new File(this.baseDir, "uid.dat");
            if (!file1.exists()) {
                final DataOutputStream dos = new DataOutputStream(new FileOutputStream(file1));
                this.uuid = UUID.randomUUID();
                dos.writeLong(this.uuid.getMostSignificantBits());
                dos.writeLong(this.uuid.getLeastSignificantBits());
                dos.close();
            }
            else {
                final DataInputStream dis = new DataInputStream(new FileInputStream(file1));
                this.uuid = new UUID(dis.readLong(), dis.readLong());
                dis.close();
            }
            return this.uuid;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public File getPlayerDir() {
        return this.playerDir;
    }
}
