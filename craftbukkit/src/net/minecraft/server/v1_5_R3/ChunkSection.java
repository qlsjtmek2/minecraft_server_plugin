// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkSection
{
    private int yPos;
    private int nonEmptyBlockCount;
    private int tickingBlockCount;
    private byte[] blockIds;
    private NibbleArray extBlockIds;
    private NibbleArray blockData;
    private NibbleArray emittedLight;
    private NibbleArray skyLight;
    
    public ChunkSection(final int i, final boolean flag) {
        this.yPos = i;
        this.blockIds = new byte[4096];
        this.blockData = new NibbleArray(this.blockIds.length, 4);
        this.emittedLight = new NibbleArray(this.blockIds.length, 4);
        if (flag) {
            this.skyLight = new NibbleArray(this.blockIds.length, 4);
        }
    }
    
    public ChunkSection(final int y, final boolean flag, final byte[] blkIds, final byte[] extBlkIds) {
        this.yPos = y;
        this.blockIds = blkIds;
        if (extBlkIds != null) {
            this.extBlockIds = new NibbleArray(extBlkIds, 4);
        }
        this.blockData = new NibbleArray(this.blockIds.length, 4);
        this.emittedLight = new NibbleArray(this.blockIds.length, 4);
        if (flag) {
            this.skyLight = new NibbleArray(this.blockIds.length, 4);
        }
        this.recalcBlockCounts();
    }
    
    public int getTypeId(final int i, final int j, final int k) {
        final int l = this.blockIds[j << 8 | k << 4 | i] & 0xFF;
        return (this.extBlockIds != null) ? (this.extBlockIds.a(i, j, k) << 8 | l) : l;
    }
    
    public void setTypeId(final int i, final int j, final int k, final int l) {
        int i2 = this.blockIds[j << 8 | k << 4 | i] & 0xFF;
        if (this.extBlockIds != null) {
            i2 |= this.extBlockIds.a(i, j, k) << 8;
        }
        if (i2 == 0 && l != 0) {
            ++this.nonEmptyBlockCount;
            if (Block.byId[l] != null && Block.byId[l].isTicking()) {
                ++this.tickingBlockCount;
            }
        }
        else if (i2 != 0 && l == 0) {
            --this.nonEmptyBlockCount;
            if (Block.byId[i2] != null && Block.byId[i2].isTicking()) {
                --this.tickingBlockCount;
            }
        }
        else if (Block.byId[i2] != null && Block.byId[i2].isTicking() && (Block.byId[l] == null || !Block.byId[l].isTicking())) {
            --this.tickingBlockCount;
        }
        else if ((Block.byId[i2] == null || !Block.byId[i2].isTicking()) && Block.byId[l] != null && Block.byId[l].isTicking()) {
            ++this.tickingBlockCount;
        }
        this.blockIds[j << 8 | k << 4 | i] = (byte)(l & 0xFF);
        if (l > 255) {
            if (this.extBlockIds == null) {
                this.extBlockIds = new NibbleArray(this.blockIds.length, 4);
            }
            this.extBlockIds.a(i, j, k, (l & 0xF00) >> 8);
        }
        else if (this.extBlockIds != null) {
            this.extBlockIds.a(i, j, k, 0);
        }
    }
    
    public int getData(final int i, final int j, final int k) {
        return this.blockData.a(i, j, k);
    }
    
    public void setData(final int i, final int j, final int k, final int l) {
        this.blockData.a(i, j, k, l);
    }
    
    public boolean isEmpty() {
        return this.nonEmptyBlockCount == 0;
    }
    
    public boolean shouldTick() {
        return this.tickingBlockCount > 0;
    }
    
    public int getYPosition() {
        return this.yPos;
    }
    
    public void setSkyLight(final int i, final int j, final int k, final int l) {
        this.skyLight.a(i, j, k, l);
    }
    
    public int getSkyLight(final int i, final int j, final int k) {
        return this.skyLight.a(i, j, k);
    }
    
    public void setEmittedLight(final int i, final int j, final int k, final int l) {
        this.emittedLight.a(i, j, k, l);
    }
    
    public int getEmittedLight(final int i, final int j, final int k) {
        return this.emittedLight.a(i, j, k);
    }
    
    public void recalcBlockCounts() {
        final byte[] blkIds = this.blockIds;
        int cntNonEmpty = 0;
        int cntTicking = 0;
        if (this.extBlockIds == null) {
            for (int off = 0; off < blkIds.length; ++off) {
                final int l = blkIds[off] & 0xFF;
                if (l > 0) {
                    if (Block.byId[l] == null) {
                        blkIds[off] = 0;
                    }
                    else {
                        ++cntNonEmpty;
                        if (Block.byId[l].isTicking()) {
                            ++cntTicking;
                        }
                    }
                }
            }
        }
        else {
            this.extBlockIds.forceToNonTrivialArray();
            final byte[] ext = this.extBlockIds.getValueArray();
            for (int off2 = 0, off3 = 0; off2 < blkIds.length; ++off2, ++off3) {
                final byte extid = ext[off3];
                int i = (blkIds[off2] & 0xFF) | (extid & 0xF) << 8;
                if (i > 0) {
                    if (Block.byId[i] == null) {
                        blkIds[off2] = 0;
                        final byte[] array = ext;
                        final int n = off3;
                        array[n] &= (byte)240;
                    }
                    else {
                        ++cntNonEmpty;
                        if (Block.byId[i].isTicking()) {
                            ++cntTicking;
                        }
                    }
                }
                ++off2;
                i = ((blkIds[off2] & 0xFF) | (extid & 0xF0) << 4);
                if (i > 0) {
                    if (Block.byId[i] == null) {
                        blkIds[off2] = 0;
                        final byte[] array2 = ext;
                        final int n2 = off3;
                        array2[n2] &= 0xF;
                    }
                    else {
                        ++cntNonEmpty;
                        if (Block.byId[i].isTicking()) {
                            ++cntTicking;
                        }
                    }
                }
            }
            this.extBlockIds.detectAndProcessTrivialArray();
            if (this.extBlockIds.isTrivialArray() && this.extBlockIds.getTrivialArrayValue() == 0) {
                this.extBlockIds = null;
            }
        }
        this.nonEmptyBlockCount = cntNonEmpty;
        this.tickingBlockCount = cntTicking;
    }
    
    public void old_recalcBlockCounts() {
        this.nonEmptyBlockCount = 0;
        this.tickingBlockCount = 0;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    final int l = this.getTypeId(i, j, k);
                    if (l > 0) {
                        if (Block.byId[l] == null) {
                            this.blockIds[j << 8 | k << 4 | i] = 0;
                            if (this.extBlockIds != null) {
                                this.extBlockIds.a(i, j, k, 0);
                            }
                        }
                        else {
                            ++this.nonEmptyBlockCount;
                            if (Block.byId[l].isTicking()) {
                                ++this.tickingBlockCount;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public byte[] getIdArray() {
        return this.blockIds;
    }
    
    public NibbleArray getExtendedIdArray() {
        return this.extBlockIds;
    }
    
    public NibbleArray getDataArray() {
        return this.blockData;
    }
    
    public NibbleArray getEmittedLightArray() {
        return this.emittedLight;
    }
    
    public NibbleArray getSkyLightArray() {
        return this.skyLight;
    }
    
    public void setIdArray(final byte[] abyte) {
        this.blockIds = this.validateByteArray(abyte);
    }
    
    public void setExtendedIdArray(final NibbleArray nibblearray) {
        boolean empty = true;
        if (!nibblearray.isTrivialArray() || nibblearray.getTrivialArrayValue() != 0) {
            empty = false;
        }
        if (empty) {
            return;
        }
        this.extBlockIds = this.validateNibbleArray(nibblearray);
    }
    
    public void setDataArray(final NibbleArray nibblearray) {
        this.blockData = this.validateNibbleArray(nibblearray);
    }
    
    public void setEmittedLightArray(final NibbleArray nibblearray) {
        this.emittedLight = this.validateNibbleArray(nibblearray);
    }
    
    public void setSkyLightArray(final NibbleArray nibblearray) {
        this.skyLight = this.validateNibbleArray(nibblearray);
    }
    
    private NibbleArray validateNibbleArray(final NibbleArray nibbleArray) {
        if (nibbleArray != null && nibbleArray.getByteLength() < 2048) {
            nibbleArray.resizeArray(2048);
        }
        return nibbleArray;
    }
    
    private byte[] validateByteArray(byte[] byteArray) {
        if (byteArray != null && byteArray.length < 4096) {
            final byte[] newArray = new byte[4096];
            System.arraycopy(byteArray, 0, newArray, 0, byteArray.length);
            byteArray = newArray;
        }
        return byteArray;
    }
}
