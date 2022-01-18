// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntitySign extends TileEntity
{
    public String[] lines;
    public int b;
    public boolean isEditable;
    
    public TileEntitySign() {
        this.lines = new String[] { "", "", "", "" };
        this.b = -1;
        this.isEditable = true;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setString("Text1", this.lines[0]);
        nbttagcompound.setString("Text2", this.lines[1]);
        nbttagcompound.setString("Text3", this.lines[2]);
        nbttagcompound.setString("Text4", this.lines[3]);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.isEditable = false;
        super.a(nbttagcompound);
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = nbttagcompound.getString("Text" + (i + 1));
            if (this.lines[i].length() > 15) {
                this.lines[i] = this.lines[i].substring(0, 15);
            }
        }
    }
    
    public Packet getUpdatePacket() {
        final String[] astring = new String[4];
        for (int i = 0; i < 4; ++i) {
            astring[i] = this.lines[i];
            if (this.lines[i].length() > 15) {
                astring[i] = this.lines[i].substring(0, 15);
            }
        }
        return new Packet130UpdateSign(this.x, this.y, this.z, astring);
    }
    
    public boolean a() {
        return this.isEditable;
    }
}
