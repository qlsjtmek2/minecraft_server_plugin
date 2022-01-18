// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.Map;
import java.util.HashMap;

public class DataWatcher
{
    private boolean a;
    private static final HashMap b;
    private final Map c;
    private boolean d;
    private ReadWriteLock e;
    
    public DataWatcher() {
        this.a = true;
        this.c = new HashMap();
        this.e = new ReentrantReadWriteLock();
    }
    
    public void a(final int n, final Object o) {
        final Integer n2 = DataWatcher.b.get(o.getClass());
        if (n2 == null) {
            throw new IllegalArgumentException("Unknown data type: " + o.getClass());
        }
        if (n > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is " + 31 + ")");
        }
        if (this.c.containsKey(n)) {
            throw new IllegalArgumentException("Duplicate id value for " + n + "!");
        }
        final WatchableObject watchableObject = new WatchableObject(n2, n, o);
        this.e.writeLock().lock();
        this.c.put(n, watchableObject);
        this.e.writeLock().unlock();
        this.a = false;
    }
    
    public void a(final int n, final int n2) {
        final WatchableObject watchableObject = new WatchableObject(n2, n, null);
        this.e.writeLock().lock();
        this.c.put(n, watchableObject);
        this.e.writeLock().unlock();
        this.a = false;
    }
    
    public byte getByte(final int n) {
        return (byte)this.i(n).b();
    }
    
    public short getShort(final int n) {
        return (short)this.i(n).b();
    }
    
    public int getInt(final int n) {
        return (int)this.i(n).b();
    }
    
    public String getString(final int n) {
        return (String)this.i(n).b();
    }
    
    public ItemStack getItemStack(final int n) {
        return (ItemStack)this.i(n).b();
    }
    
    private WatchableObject i(final int n) {
        this.e.readLock().lock();
        WatchableObject watchableObject;
        try {
            watchableObject = this.c.get(n);
        }
        catch (Throwable throwable) {
            final CrashReport a = CrashReport.a(throwable, "Getting synched entity data");
            a.a("Synched entity data").a("Data ID", n);
            throw new ReportedException(a);
        }
        this.e.readLock().unlock();
        return watchableObject;
    }
    
    public void watch(final int n, final Object o) {
        final WatchableObject i = this.i(n);
        if (!o.equals(i.b())) {
            i.a(o);
            i.a(true);
            this.d = true;
        }
    }
    
    public void h(final int n) {
        this.i(n).d = true;
        this.d = true;
    }
    
    public boolean a() {
        return this.d;
    }
    
    public static void a(final List list, final DataOutputStream dataOutputStream) {
        if (list != null) {
            final Iterator<WatchableObject> iterator = list.iterator();
            while (iterator.hasNext()) {
                a(dataOutputStream, iterator.next());
            }
        }
        dataOutputStream.writeByte(127);
    }
    
    public List b() {
        ArrayList<WatchableObject> list = null;
        if (this.d) {
            this.e.readLock().lock();
            for (final WatchableObject watchableObject : this.c.values()) {
                if (watchableObject.d()) {
                    watchableObject.a(false);
                    if (list == null) {
                        list = new ArrayList<WatchableObject>();
                    }
                    list.add(watchableObject);
                }
            }
            this.e.readLock().unlock();
        }
        this.d = false;
        return list;
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        this.e.readLock().lock();
        final Iterator<WatchableObject> iterator = this.c.values().iterator();
        while (iterator.hasNext()) {
            a(dataOutputStream, iterator.next());
        }
        this.e.readLock().unlock();
        dataOutputStream.writeByte(127);
    }
    
    public List c() {
        ArrayList<WatchableObject> list = null;
        this.e.readLock().lock();
        for (final WatchableObject watchableObject : this.c.values()) {
            if (list == null) {
                list = new ArrayList<WatchableObject>();
            }
            list.add(watchableObject);
        }
        this.e.readLock().unlock();
        return list;
    }
    
    private static void a(final DataOutputStream dataOutputStream, final WatchableObject watchableObject) {
        dataOutputStream.writeByte((watchableObject.c() << 5 | (watchableObject.a() & 0x1F)) & 0xFF);
        switch (watchableObject.c()) {
            case 0: {
                dataOutputStream.writeByte((byte)watchableObject.b());
                break;
            }
            case 1: {
                dataOutputStream.writeShort((short)watchableObject.b());
                break;
            }
            case 2: {
                dataOutputStream.writeInt((int)watchableObject.b());
                break;
            }
            case 3: {
                dataOutputStream.writeFloat((float)watchableObject.b());
                break;
            }
            case 4: {
                Packet.a((String)watchableObject.b(), dataOutputStream);
                break;
            }
            case 5: {
                Packet.a((ItemStack)watchableObject.b(), dataOutputStream);
                break;
            }
            case 6: {
                final ChunkCoordinates chunkCoordinates = (ChunkCoordinates)watchableObject.b();
                dataOutputStream.writeInt(chunkCoordinates.x);
                dataOutputStream.writeInt(chunkCoordinates.y);
                dataOutputStream.writeInt(chunkCoordinates.z);
                break;
            }
        }
    }
    
    public static List a(final DataInputStream dataInputStream) {
        List<Object> list = null;
        for (byte b = dataInputStream.readByte(); b != 127; b = dataInputStream.readByte()) {
            if (list == null) {
                list = new ArrayList<Object>();
            }
            final int n = (b & 0xE0) >> 5;
            final byte b2 = (byte)(b & 0x1F);
            Object o = null;
            switch (n) {
                case 0: {
                    o = new WatchableObject(n, b2, dataInputStream.readByte());
                    break;
                }
                case 1: {
                    o = new WatchableObject(n, b2, dataInputStream.readShort());
                    break;
                }
                case 2: {
                    o = new WatchableObject(n, b2, dataInputStream.readInt());
                    break;
                }
                case 3: {
                    o = new WatchableObject(n, b2, dataInputStream.readFloat());
                    break;
                }
                case 4: {
                    o = new WatchableObject(n, b2, Packet.a(dataInputStream, 64));
                    break;
                }
                case 5: {
                    o = new WatchableObject(n, b2, Packet.c(dataInputStream));
                    break;
                }
                case 6: {
                    o = new WatchableObject(n, b2, new ChunkCoordinates(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt()));
                    break;
                }
            }
            list.add(o);
        }
        return list;
    }
    
    public boolean d() {
        return this.a;
    }
    
    static {
        (b = new HashMap()).put(Byte.class, 0);
        DataWatcher.b.put(Short.class, 1);
        DataWatcher.b.put(Integer.class, 2);
        DataWatcher.b.put(Float.class, 3);
        DataWatcher.b.put(String.class, 4);
        DataWatcher.b.put(ItemStack.class, 5);
        DataWatcher.b.put(ChunkCoordinates.class, 6);
    }
}
