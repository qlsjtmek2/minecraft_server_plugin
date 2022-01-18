// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.Iterator;
import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import java.util.Arrays;
import org.bukkit.craftbukkit.v1_5_R3.util.UnsafeList;
import java.util.HashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.List;
import java.util.Map;

public class Chunk
{
    public static boolean a;
    private ChunkSection[] sections;
    private byte[] s;
    public int[] b;
    public boolean[] c;
    public boolean d;
    public World world;
    public int[] heightMap;
    public final int x;
    public final int z;
    private boolean t;
    public Map tileEntities;
    public List[] entitySlices;
    public boolean done;
    public boolean l;
    public boolean m;
    public long n;
    public boolean seenByPlayer;
    public int p;
    private int u;
    boolean q;
    protected TObjectIntHashMap<Class> entityCount;
    public org.bukkit.Chunk bukkitChunk;
    public boolean mustSave;
    
    public Chunk(final World world, final int i, final int j) {
        this.entityCount = new TObjectIntHashMap<Class>();
        this.sections = new ChunkSection[16];
        this.s = new byte[256];
        this.b = new int[256];
        this.c = new boolean[256];
        this.t = false;
        this.tileEntities = new HashMap();
        this.done = false;
        this.l = false;
        this.m = false;
        this.n = 0L;
        this.seenByPlayer = false;
        this.p = 0;
        this.u = 4096;
        this.q = false;
        this.entitySlices = new List[16];
        this.world = world;
        this.x = i;
        this.z = j;
        this.heightMap = new int[256];
        for (int k = 0; k < this.entitySlices.length; ++k) {
            this.entitySlices[k] = new UnsafeList();
        }
        Arrays.fill(this.b, -999);
        Arrays.fill(this.s, (byte)(-1));
        if (!(this instanceof EmptyChunk)) {
            this.bukkitChunk = new CraftChunk(this);
        }
    }
    
    public Chunk(final World world, final byte[] abyte, final int i, final int j) {
        this(world, i, j);
        final int k = abyte.length / 256;
        for (int l = 0; l < 16; ++l) {
            for (int i2 = 0; i2 < 16; ++i2) {
                for (int j2 = 0; j2 < k; ++j2) {
                    final byte b0 = abyte[l << 11 | i2 << 7 | j2];
                    if (b0 != 0) {
                        final int k2 = j2 >> 4;
                        if (this.sections[k2] == null) {
                            this.sections[k2] = new ChunkSection(k2 << 4, !world.worldProvider.f);
                        }
                        this.sections[k2].setTypeId(l, j2 & 0xF, i2, b0);
                    }
                }
            }
        }
    }
    
    public boolean a(final int i, final int j) {
        return i == this.x && j == this.z;
    }
    
    public int b(final int i, final int j) {
        return this.heightMap[j << 4 | i];
    }
    
    public int h() {
        for (int i = this.sections.length - 1; i >= 0; --i) {
            if (this.sections[i] != null) {
                return this.sections[i].getYPosition();
            }
        }
        return 0;
    }
    
    public ChunkSection[] i() {
        return this.sections;
    }
    
    public void initLighting() {
        final int i = this.h();
        this.p = Integer.MAX_VALUE;
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                this.b[j + (k << 4)] = -999;
                int l = i + 16 - 1;
                while (l > 0) {
                    if (this.b(j, l - 1, k) == 0) {
                        --l;
                    }
                    else {
                        if ((this.heightMap[k << 4 | j] = l) < this.p) {
                            this.p = l;
                            break;
                        }
                        break;
                    }
                }
                if (!this.world.worldProvider.f) {
                    l = 15;
                    int i2 = i + 16 - 1;
                    do {
                        l -= this.b(j, i2, k);
                        if (l > 0) {
                            final ChunkSection chunksection = this.sections[i2 >> 4];
                            if (chunksection == null) {
                                continue;
                            }
                            chunksection.setSkyLight(j, i2 & 0xF, k, l);
                            this.world.p((this.x << 4) + j, i2, (this.z << 4) + k);
                        }
                    } while (--i2 > 0 && l > 0);
                }
            }
        }
        this.l = true;
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                this.e(j, k);
            }
        }
    }
    
    private void e(final int i, final int j) {
        this.c[i + j * 16] = true;
        this.t = true;
    }
    
    private void q() {
        this.world.methodProfiler.a("recheckGaps");
        if (this.world.areChunksLoaded(this.x * 16 + 8, 0, this.z * 16 + 8, 16)) {
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    if (this.c[i + j * 16]) {
                        this.c[i + j * 16] = false;
                        final int k = this.b(i, j);
                        final int l = this.x * 16 + i;
                        final int i2 = this.z * 16 + j;
                        int j2 = this.world.g(l - 1, i2);
                        final int k2 = this.world.g(l + 1, i2);
                        final int l2 = this.world.g(l, i2 - 1);
                        final int i3 = this.world.g(l, i2 + 1);
                        if (k2 < j2) {
                            j2 = k2;
                        }
                        if (l2 < j2) {
                            j2 = l2;
                        }
                        if (i3 < j2) {
                            j2 = i3;
                        }
                        this.g(l, i2, j2);
                        this.g(l - 1, i2, k);
                        this.g(l + 1, i2, k);
                        this.g(l, i2 - 1, k);
                        this.g(l, i2 + 1, k);
                    }
                }
            }
            this.t = false;
        }
        this.world.methodProfiler.b();
    }
    
    private void g(final int i, final int j, final int k) {
        final int l = this.world.getHighestBlockYAt(i, j);
        if (l > k) {
            this.d(i, j, k, l + 1);
        }
        else if (l < k) {
            this.d(i, j, l, k + 1);
        }
    }
    
    private void d(final int i, final int j, final int k, final int l) {
        if (l > k && this.world.areChunksLoaded(i, 0, j, 16)) {
            for (int i2 = k; i2 < l; ++i2) {
                this.world.c(EnumSkyBlock.SKY, i, i2, j);
            }
            this.l = true;
        }
    }
    
    private void h(final int i, final int j, final int k) {
        int i2;
        final int l = i2 = (this.heightMap[k << 4 | i] & 0xFF);
        if (j > l) {
            i2 = j;
        }
        while (i2 > 0 && this.b(i, i2 - 1, k) == 0) {
            --i2;
        }
        if (i2 != l) {
            this.world.e(i + this.x * 16, k + this.z * 16, i2, l);
            this.heightMap[k << 4 | i] = i2;
            final int j2 = this.x * 16 + i;
            final int k2 = this.z * 16 + k;
            if (!this.world.worldProvider.f) {
                if (i2 < l) {
                    for (int l2 = i2; l2 < l; ++l2) {
                        final ChunkSection chunksection = this.sections[l2 >> 4];
                        if (chunksection != null) {
                            chunksection.setSkyLight(i, l2 & 0xF, k, 15);
                            this.world.p((this.x << 4) + i, l2, (this.z << 4) + k);
                        }
                    }
                }
                else {
                    for (int l2 = l; l2 < i2; ++l2) {
                        final ChunkSection chunksection = this.sections[l2 >> 4];
                        if (chunksection != null) {
                            chunksection.setSkyLight(i, l2 & 0xF, k, 0);
                            this.world.p((this.x << 4) + i, l2, (this.z << 4) + k);
                        }
                    }
                }
                int l2 = 15;
                while (i2 > 0 && l2 > 0) {
                    --i2;
                    int i3 = this.b(i, i2, k);
                    if (i3 == 0) {
                        i3 = 1;
                    }
                    l2 -= i3;
                    if (l2 < 0) {
                        l2 = 0;
                    }
                    final ChunkSection chunksection2 = this.sections[i2 >> 4];
                    if (chunksection2 != null) {
                        chunksection2.setSkyLight(i, i2 & 0xF, k, l2);
                    }
                }
            }
            int l2 = this.heightMap[k << 4 | i];
            int i3;
            int j3;
            if ((j3 = l2) < (i3 = l)) {
                i3 = l2;
                j3 = l;
            }
            if (l2 < this.p) {
                this.p = l2;
            }
            if (!this.world.worldProvider.f) {
                this.d(j2 - 1, k2, i3, j3);
                this.d(j2 + 1, k2, i3, j3);
                this.d(j2, k2 - 1, i3, j3);
                this.d(j2, k2 + 1, i3, j3);
                this.d(j2, k2, i3, j3);
            }
            this.l = true;
        }
    }
    
    public int b(final int i, final int j, final int k) {
        return Block.lightBlock[this.getTypeId(i, j, k)];
    }
    
    public int getTypeId(final int i, final int j, final int k) {
        if (j >> 4 >= this.sections.length) {
            return 0;
        }
        final ChunkSection chunksection = this.sections[j >> 4];
        return (chunksection != null) ? chunksection.getTypeId(i, j & 0xF, k) : 0;
    }
    
    public int getData(final int i, final int j, final int k) {
        if (j >> 4 >= this.sections.length) {
            return 0;
        }
        final ChunkSection chunksection = this.sections[j >> 4];
        return (chunksection != null) ? chunksection.getData(i, j & 0xF, k) : 0;
    }
    
    public boolean a(final int i, final int j, final int k, final int l, final int i1) {
        final int j2 = k << 4 | i;
        if (j >= this.b[j2] - 1) {
            this.b[j2] = -999;
        }
        final int k2 = this.heightMap[j2];
        final int l2 = this.getTypeId(i, j, k);
        final int i2 = this.getData(i, j, k);
        if (l2 == l && i2 == i1) {
            return false;
        }
        ChunkSection chunksection = this.sections[j >> 4];
        boolean flag = false;
        if (chunksection == null) {
            if (l == 0) {
                return false;
            }
            final ChunkSection[] sections = this.sections;
            final int n = j >> 4;
            final ChunkSection chunkSection = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.f);
            sections[n] = chunkSection;
            chunksection = chunkSection;
            flag = (j >= k2);
        }
        final int j3 = this.x * 16 + i;
        final int k3 = this.z * 16 + k;
        if (l2 != 0 && !this.world.isStatic) {
            Block.byId[l2].l(this.world, j3, j, k3, i2);
        }
        chunksection.setTypeId(i, j & 0xF, k, l);
        if (l2 != 0) {
            if (!this.world.isStatic) {
                Block.byId[l2].remove(this.world, j3, j, k3, l2, i2);
            }
            else if (Block.byId[l2] instanceof IContainer && l2 != l) {
                this.world.s(j3, j, k3);
            }
        }
        if (chunksection.getTypeId(i, j & 0xF, k) != l) {
            return false;
        }
        chunksection.setData(i, j & 0xF, k, i1);
        if (flag) {
            this.initLighting();
        }
        else {
            if (Block.lightBlock[l & 0xFFF] > 0) {
                if (j >= k2) {
                    this.h(i, j + 1, k);
                }
            }
            else if (j == k2 - 1) {
                this.h(i, j, k);
            }
            this.e(i, k);
        }
        if (l != 0) {
            if (!this.world.isStatic && (!this.world.callingPlaceEvent || Block.byId[l] instanceof BlockContainer)) {
                Block.byId[l].onPlace(this.world, j3, j, k3);
            }
            if (Block.byId[l] instanceof IContainer) {
                if (this.getTypeId(i, j, k) != l) {
                    return false;
                }
                TileEntity tileentity = this.e(i, j, k);
                if (tileentity == null) {
                    tileentity = ((IContainer)Block.byId[l]).b(this.world);
                    this.world.setTileEntity(j3, j, k3, tileentity);
                }
                if (tileentity != null) {
                    tileentity.i();
                }
            }
        }
        else if (l2 > 0 && Block.byId[l2] instanceof IContainer) {
            final TileEntity tileentity = this.e(i, j, k);
            if (tileentity != null) {
                tileentity.i();
            }
        }
        return this.l = true;
    }
    
    public boolean b(final int i, final int j, final int k, final int l) {
        final ChunkSection chunksection = this.sections[j >> 4];
        if (chunksection == null) {
            return false;
        }
        final int i2 = chunksection.getData(i, j & 0xF, k);
        if (i2 == l) {
            return false;
        }
        this.l = true;
        chunksection.setData(i, j & 0xF, k, l);
        final int j2 = chunksection.getTypeId(i, j & 0xF, k);
        if (j2 > 0 && Block.byId[j2] instanceof IContainer) {
            final TileEntity tileentity = this.e(i, j, k);
            if (tileentity != null) {
                tileentity.i();
                tileentity.p = l;
            }
        }
        return true;
    }
    
    public int getBrightness(final EnumSkyBlock enumskyblock, final int i, final int j, final int k) {
        final ChunkSection chunksection = this.sections[j >> 4];
        return (chunksection == null) ? (this.d(i, j, k) ? enumskyblock.c : 0) : ((enumskyblock == EnumSkyBlock.SKY) ? (this.world.worldProvider.f ? 0 : chunksection.getSkyLight(i, j & 0xF, k)) : ((enumskyblock == EnumSkyBlock.BLOCK) ? chunksection.getEmittedLight(i, j & 0xF, k) : enumskyblock.c));
    }
    
    public void a(final EnumSkyBlock enumskyblock, final int i, final int j, final int k, final int l) {
        ChunkSection chunksection = this.sections[j >> 4];
        if (chunksection == null) {
            final ChunkSection[] sections = this.sections;
            final int n = j >> 4;
            final ChunkSection chunkSection = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.f);
            sections[n] = chunkSection;
            chunksection = chunkSection;
            this.initLighting();
        }
        this.l = true;
        if (enumskyblock == EnumSkyBlock.SKY) {
            if (!this.world.worldProvider.f) {
                chunksection.setSkyLight(i, j & 0xF, k, l);
            }
        }
        else if (enumskyblock == EnumSkyBlock.BLOCK) {
            chunksection.setEmittedLight(i, j & 0xF, k, l);
        }
    }
    
    public int c(final int i, final int j, final int k, final int l) {
        final ChunkSection chunksection = this.sections[j >> 4];
        if (chunksection == null) {
            return (!this.world.worldProvider.f && l < EnumSkyBlock.SKY.c) ? (EnumSkyBlock.SKY.c - l) : 0;
        }
        int i2 = this.world.worldProvider.f ? 0 : chunksection.getSkyLight(i, j & 0xF, k);
        if (i2 > 0) {
            Chunk.a = true;
        }
        i2 -= l;
        final int j2 = chunksection.getEmittedLight(i, j & 0xF, k);
        if (j2 > i2) {
            i2 = j2;
        }
        return i2;
    }
    
    public void a(final Entity entity) {
        this.m = true;
        final int i = MathHelper.floor(entity.locX / 16.0);
        final int j = MathHelper.floor(entity.locZ / 16.0);
        if (i != this.x || j != this.z) {
            Bukkit.getLogger().warning("Wrong location for " + entity + " in world '" + this.world.getWorld().getName() + "'!");
            Bukkit.getLogger().warning("Entity is at " + entity.locX + "," + entity.locZ + " (chunk " + i + "," + j + ") but was stored in chunk " + this.x + "," + this.z);
        }
        int k = MathHelper.floor(entity.locY / 16.0);
        if (k < 0) {
            k = 0;
        }
        if (k >= this.entitySlices.length) {
            k = this.entitySlices.length - 1;
        }
        entity.ai = true;
        entity.aj = this.x;
        entity.ak = k;
        entity.al = this.z;
        this.entitySlices[k].add(entity);
        for (final EnumCreatureType creatureType : EnumCreatureType.values()) {
            if (creatureType.a().isAssignableFrom(entity.getClass())) {
                this.entityCount.adjustOrPutValue(creatureType.a(), 1, 1);
            }
        }
    }
    
    public void b(final Entity entity) {
        this.a(entity, entity.ak);
    }
    
    public void a(final Entity entity, int i) {
        if (i < 0) {
            i = 0;
        }
        if (i >= this.entitySlices.length) {
            i = this.entitySlices.length - 1;
        }
        this.entitySlices[i].remove(entity);
        for (final EnumCreatureType creatureType : EnumCreatureType.values()) {
            if (creatureType.a().isAssignableFrom(entity.getClass())) {
                this.entityCount.adjustValue(creatureType.a(), -1);
            }
        }
    }
    
    public boolean d(final int i, final int j, final int k) {
        return j >= this.heightMap[k << 4 | i];
    }
    
    public TileEntity e(final int i, final int j, final int k) {
        final ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        TileEntity tileentity = this.tileEntities.get(chunkposition);
        if (tileentity == null) {
            final int l = this.getTypeId(i, j, k);
            if (l <= 0 || !Block.byId[l].t()) {
                return null;
            }
            if (tileentity == null) {
                tileentity = ((IContainer)Block.byId[l]).b(this.world);
                this.world.setTileEntity(this.x * 16 + i, j, this.z * 16 + k, tileentity);
            }
            tileentity = this.tileEntities.get(chunkposition);
        }
        if (tileentity != null && tileentity.r()) {
            this.tileEntities.remove(chunkposition);
            return null;
        }
        return tileentity;
    }
    
    public void a(final TileEntity tileentity) {
        final int i = tileentity.x - this.x * 16;
        final int j = tileentity.y;
        final int k = tileentity.z - this.z * 16;
        this.a(i, j, k, tileentity);
        if (this.d) {
            this.world.tileEntityList.add(tileentity);
        }
    }
    
    public void a(final int i, final int j, final int k, final TileEntity tileentity) {
        final ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        tileentity.b(this.world);
        tileentity.x = this.x * 16 + i;
        tileentity.y = j;
        tileentity.z = this.z * 16 + k;
        if (this.getTypeId(i, j, k) != 0 && Block.byId[this.getTypeId(i, j, k)] instanceof IContainer) {
            if (this.tileEntities.containsKey(chunkposition)) {
                this.tileEntities.get(chunkposition).w_();
            }
            tileentity.s();
            this.tileEntities.put(chunkposition, tileentity);
        }
        else {
            System.out.println("Attempted to place a tile entity (" + tileentity + ") at " + tileentity.x + "," + tileentity.y + "," + tileentity.z + " (" + Material.getMaterial(this.getTypeId(i, j, k)) + ") where there was no entity tile!");
            System.out.println("Chunk coordinates: " + this.x * 16 + "," + this.z * 16);
            new Exception().printStackTrace();
        }
    }
    
    public void f(final int i, final int j, final int k) {
        final ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        if (this.d) {
            final TileEntity tileentity = this.tileEntities.remove(chunkposition);
            if (tileentity != null) {
                tileentity.w_();
            }
        }
    }
    
    public void addEntities() {
        this.d = true;
        this.world.a(this.tileEntities.values());
        for (int i = 0; i < this.entitySlices.length; ++i) {
            this.world.a(this.entitySlices[i]);
        }
    }
    
    public void removeEntities() {
        this.d = false;
        for (final TileEntity tileentity : this.tileEntities.values()) {
            this.world.a(tileentity);
        }
        for (int i = 0; i < this.entitySlices.length; ++i) {
            final Iterator<Object> iter = this.entitySlices[i].iterator();
            while (iter.hasNext()) {
                final Entity entity = iter.next();
                if (entity instanceof EntityPlayer) {
                    iter.remove();
                }
            }
            this.world.b(this.entitySlices[i]);
        }
    }
    
    public void e() {
        this.l = true;
    }
    
    public void a(final Entity entity, final AxisAlignedBB axisalignedbb, final List list, final IEntitySelector ientityselector) {
        int i = MathHelper.floor((axisalignedbb.b - 2.0) / 16.0);
        int j = MathHelper.floor((axisalignedbb.e + 2.0) / 16.0);
        if (i < 0) {
            i = 0;
            j = Math.max(i, j);
        }
        if (j >= this.entitySlices.length) {
            j = this.entitySlices.length - 1;
            i = Math.min(i, j);
        }
        for (int k = i; k <= j; ++k) {
            final List list2 = this.entitySlices[k];
            for (int l = 0; l < list2.size(); ++l) {
                Entity entity2 = list2.get(l);
                if (entity2 != entity && entity2.boundingBox.a(axisalignedbb) && (ientityselector == null || ientityselector.a(entity2))) {
                    list.add(entity2);
                    final Entity[] aentity = entity2.an();
                    if (aentity != null) {
                        for (int i2 = 0; i2 < aentity.length; ++i2) {
                            entity2 = aentity[i2];
                            if (entity2 != entity && entity2.boundingBox.a(axisalignedbb) && (ientityselector == null || ientityselector.a(entity2))) {
                                list.add(entity2);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void a(final Class oclass, final AxisAlignedBB axisalignedbb, final List list, final IEntitySelector ientityselector) {
        int i = MathHelper.floor((axisalignedbb.b - 2.0) / 16.0);
        int j = MathHelper.floor((axisalignedbb.e + 2.0) / 16.0);
        if (i < 0) {
            i = 0;
        }
        else if (i >= this.entitySlices.length) {
            i = this.entitySlices.length - 1;
        }
        if (j >= this.entitySlices.length) {
            j = this.entitySlices.length - 1;
        }
        else if (j < 0) {
            j = 0;
        }
        for (int k = i; k <= j; ++k) {
            final List list2 = this.entitySlices[k];
            for (int l = 0; l < list2.size(); ++l) {
                final Entity entity = list2.get(l);
                if (oclass.isAssignableFrom(entity.getClass()) && entity.boundingBox.a(axisalignedbb) && (ientityselector == null || ientityselector.a(entity))) {
                    list.add(entity);
                }
            }
        }
    }
    
    public boolean a(final boolean flag) {
        if (flag) {
            if ((this.m && this.world.getTime() != this.n) || this.l) {
                return true;
            }
        }
        else if (this.m && this.world.getTime() >= this.n + 600L) {
            return true;
        }
        return this.l;
    }
    
    public Random a(final long i) {
        return new Random(this.world.getSeed() + this.x * this.x * 4987142 + this.x * 5947611 + this.z * this.z * 4392871L + this.z * 389711 ^ i);
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public void a(final IChunkProvider ichunkprovider, final IChunkProvider ichunkprovider1, final int i, final int j) {
        if (!this.done && ichunkprovider.isChunkLoaded(i + 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i, j);
        }
        if (ichunkprovider.isChunkLoaded(i - 1, j) && !ichunkprovider.getOrCreateChunk(i - 1, j).done && ichunkprovider.isChunkLoaded(i - 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i - 1, j + 1)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j);
        }
        if (ichunkprovider.isChunkLoaded(i, j - 1) && !ichunkprovider.getOrCreateChunk(i, j - 1).done && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i, j - 1);
        }
        if (ichunkprovider.isChunkLoaded(i - 1, j - 1) && !ichunkprovider.getOrCreateChunk(i - 1, j - 1).done && ichunkprovider.isChunkLoaded(i, j - 1) && ichunkprovider.isChunkLoaded(i - 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j - 1);
        }
    }
    
    public int d(final int i, final int j) {
        final int k = i | j << 4;
        int l = this.b[k];
        if (l == -999) {
            int i2 = this.h() + 15;
            l = -1;
            while (i2 > 0 && l == -1) {
                final int j2 = this.getTypeId(i, i2, j);
                final net.minecraft.server.v1_5_R3.Material material = (j2 == 0) ? net.minecraft.server.v1_5_R3.Material.AIR : Block.byId[j2].material;
                if (!material.isSolid() && !material.isLiquid()) {
                    --i2;
                }
                else {
                    l = i2 + 1;
                }
            }
            this.b[k] = l;
        }
        return l;
    }
    
    public void k() {
        if (this.t && !this.world.worldProvider.f) {
            this.q();
        }
    }
    
    public ChunkCoordIntPair l() {
        return new ChunkCoordIntPair(this.x, this.z);
    }
    
    public boolean c(int i, int j) {
        if (i < 0) {
            i = 0;
        }
        if (j >= 256) {
            j = 255;
        }
        for (int k = i; k <= j; k += 16) {
            final ChunkSection chunksection = this.sections[k >> 4];
            if (chunksection != null && !chunksection.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public void a(final ChunkSection[] achunksection) {
        this.sections = achunksection;
    }
    
    public BiomeBase a(final int i, final int j, final WorldChunkManager worldchunkmanager) {
        int k = this.s[j << 4 | i] & 0xFF;
        if (k == 255) {
            final BiomeBase biomebase = worldchunkmanager.getBiome((this.x << 4) + i, (this.z << 4) + j);
            k = biomebase.id;
            this.s[j << 4 | i] = (byte)(k & 0xFF);
        }
        return (BiomeBase.biomes[k] == null) ? BiomeBase.PLAINS : BiomeBase.biomes[k];
    }
    
    public byte[] m() {
        return this.s;
    }
    
    public void a(final byte[] abyte) {
        this.s = abyte;
    }
    
    public void n() {
        this.u = 0;
    }
    
    public void o() {
        for (int i = 0; i < 8; ++i) {
            if (this.u >= 4096) {
                return;
            }
            final int j = this.u % 16;
            final int k = this.u / 16 % 16;
            final int l = this.u / 256;
            ++this.u;
            final int i2 = (this.x << 4) + k;
            final int j2 = (this.z << 4) + l;
            for (int k2 = 0; k2 < 16; ++k2) {
                final int l2 = (j << 4) + k2;
                if ((this.sections[j] == null && (k2 == 0 || k2 == 15 || k == 0 || k == 15 || l == 0 || l == 15)) || (this.sections[j] != null && this.sections[j].getTypeId(k, k2, l) == 0)) {
                    if (Block.lightEmission[this.world.getTypeId(i2, l2 - 1, j2)] > 0) {
                        this.world.A(i2, l2 - 1, j2);
                    }
                    if (Block.lightEmission[this.world.getTypeId(i2, l2 + 1, j2)] > 0) {
                        this.world.A(i2, l2 + 1, j2);
                    }
                    if (Block.lightEmission[this.world.getTypeId(i2 - 1, l2, j2)] > 0) {
                        this.world.A(i2 - 1, l2, j2);
                    }
                    if (Block.lightEmission[this.world.getTypeId(i2 + 1, l2, j2)] > 0) {
                        this.world.A(i2 + 1, l2, j2);
                    }
                    if (Block.lightEmission[this.world.getTypeId(i2, l2, j2 - 1)] > 0) {
                        this.world.A(i2, l2, j2 - 1);
                    }
                    if (Block.lightEmission[this.world.getTypeId(i2, l2, j2 + 1)] > 0) {
                        this.world.A(i2, l2, j2 + 1);
                    }
                    this.world.A(i2, l2, j2);
                }
            }
        }
    }
}
