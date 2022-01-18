// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMapCollection
{
    private IDataManager a;
    private Map b;
    private List c;
    private Map d;
    
    public WorldMapCollection(final IDataManager a) {
        this.b = new HashMap();
        this.c = new ArrayList();
        this.d = new HashMap();
        this.a = a;
        this.b();
    }
    
    public WorldMapBase get(final Class clazz, final String s) {
        WorldMapBase worldMapBase = this.b.get(s);
        if (worldMapBase != null) {
            return worldMapBase;
        }
        if (this.a != null) {
            try {
                final File dataFile = this.a.getDataFile(s);
                if (dataFile != null && dataFile.exists()) {
                    try {
                        worldMapBase = clazz.getConstructor(String.class).newInstance(s);
                    }
                    catch (Exception ex) {
                        throw new RuntimeException("Failed to instantiate " + clazz.toString(), ex);
                    }
                    final FileInputStream fileInputStream = new FileInputStream(dataFile);
                    final NBTTagCompound a = NBTCompressedStreamTools.a(fileInputStream);
                    fileInputStream.close();
                    worldMapBase.a(a.getCompound("data"));
                }
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        if (worldMapBase != null) {
            this.b.put(s, worldMapBase);
            this.c.add(worldMapBase);
        }
        return worldMapBase;
    }
    
    public void a(final String s, final WorldMapBase worldMapBase) {
        if (worldMapBase == null) {
            throw new RuntimeException("Can't set null data");
        }
        if (this.b.containsKey(s)) {
            this.c.remove(this.b.remove(s));
        }
        this.b.put(s, worldMapBase);
        this.c.add(worldMapBase);
    }
    
    public void a() {
        for (int i = 0; i < this.c.size(); ++i) {
            final WorldMapBase worldMapBase = this.c.get(i);
            if (worldMapBase.d()) {
                this.a(worldMapBase);
                worldMapBase.a(false);
            }
        }
    }
    
    private void a(final WorldMapBase worldMapBase) {
        if (this.a == null) {
            return;
        }
        try {
            final File dataFile = this.a.getDataFile(worldMapBase.id);
            if (dataFile != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                worldMapBase.b(nbtTagCompound);
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setCompound("data", nbtTagCompound);
                final FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
                NBTCompressedStreamTools.a(nbtTagCompound2, fileOutputStream);
                fileOutputStream.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void b() {
        try {
            this.d.clear();
            if (this.a == null) {
                return;
            }
            final File dataFile = this.a.getDataFile("idcounts");
            if (dataFile != null && dataFile.exists()) {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(dataFile));
                final NBTTagCompound a = NBTCompressedStreamTools.a((DataInput)dataInputStream);
                dataInputStream.close();
                for (final NBTBase nbtBase : a.c()) {
                    if (nbtBase instanceof NBTTagShort) {
                        final NBTTagShort nbtTagShort = (NBTTagShort)nbtBase;
                        this.d.put(nbtTagShort.getName(), nbtTagShort.data);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int a(final String s) {
        final Short n = this.d.get(s);
        Short n2;
        if (n == null) {
            n2 = 0;
        }
        else {
            n2 = (short)(n + 1);
        }
        this.d.put(s, n2);
        if (this.a == null) {
            return n2;
        }
        try {
            final File dataFile = this.a.getDataFile("idcounts");
            if (dataFile != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                for (final String s2 : this.d.keySet()) {
                    nbtTagCompound.setShort(s2, (short)this.d.get(s2));
                }
                final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(dataFile));
                NBTCompressedStreamTools.a(nbtTagCompound, (DataOutput)dataOutputStream);
                dataOutputStream.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return n2;
    }
}
