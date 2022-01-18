// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntitySkull extends TileEntity
{
    private int a;
    private int b;
    private String c;
    
    public TileEntitySkull() {
        this.c = "";
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setByte("SkullType", (byte)(this.a & 0xFF));
        nbttagcompound.setByte("Rot", (byte)(this.b & 0xFF));
        nbttagcompound.setString("ExtraType", this.c);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getByte("SkullType");
        this.b = nbttagcompound.getByte("Rot");
        if (nbttagcompound.hasKey("ExtraType")) {
            this.c = nbttagcompound.getString("ExtraType");
        }
    }
    
    public Packet getUpdatePacket() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.b(nbttagcompound);
        return new Packet132TileEntityData(this.x, this.y, this.z, 4, nbttagcompound);
    }
    
    public void setSkullType(final int i, final String s) {
        this.a = i;
        this.c = s;
    }
    
    public int getSkullType() {
        return this.a;
    }
    
    public void setRotation(final int i) {
        this.b = i;
    }
    
    public int getRotation() {
        return this.b;
    }
    
    public String getExtraType() {
        return this.c;
    }
}
