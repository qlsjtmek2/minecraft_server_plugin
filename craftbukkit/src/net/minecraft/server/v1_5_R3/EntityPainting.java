// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging
{
    public EnumArt art;
    
    public EntityPainting(final World world) {
        super(world);
        this.art = EnumArt.values()[this.random.nextInt(EnumArt.values().length)];
    }
    
    public EntityPainting(final World world, final int i, final int j, final int k, final int l) {
        super(world, i, j, k, l);
        final ArrayList arraylist = new ArrayList();
        for (final EnumArt enumart : EnumArt.values()) {
            this.art = enumart;
            this.setDirection(l);
            if (this.survives()) {
                arraylist.add(enumart);
            }
        }
        if (!arraylist.isEmpty()) {
            this.art = arraylist.get(this.random.nextInt(arraylist.size()));
        }
        this.setDirection(l);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("Motive", this.art.B);
        super.b(nbttagcompound);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        final String s = nbttagcompound.getString("Motive");
        for (final EnumArt enumart : EnumArt.values()) {
            if (enumart.B.equals(s)) {
                this.art = enumart;
            }
        }
        if (this.art == null) {
            this.art = EnumArt.KEBAB;
        }
        super.a(nbttagcompound);
    }
    
    public int d() {
        return this.art.C;
    }
    
    public int g() {
        return this.art.D;
    }
    
    public void h() {
        this.a(new ItemStack(Item.PAINTING), 0.0f);
    }
}
