// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntityRecordPlayer extends TileEntity
{
    private ItemStack record;
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("RecordItem")) {
            this.setRecord(ItemStack.createStack(nbttagcompound.getCompound("RecordItem")));
        }
        else if (nbttagcompound.getInt("Record") > 0) {
            this.setRecord(new ItemStack(nbttagcompound.getInt("Record"), 1, 0));
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.getRecord() != null) {
            nbttagcompound.setCompound("RecordItem", this.getRecord().save(new NBTTagCompound()));
            nbttagcompound.setInt("Record", this.getRecord().id);
        }
    }
    
    public ItemStack getRecord() {
        return this.record;
    }
    
    public void setRecord(final ItemStack itemstack) {
        if (itemstack != null) {
            itemstack.count = 1;
        }
        this.record = itemstack;
        this.update();
    }
}
