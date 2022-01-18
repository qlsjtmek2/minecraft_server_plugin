// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntityMagmaCube extends EntitySlime
{
    public EntityMagmaCube(final World world) {
        super(world);
        this.texture = "/mob/lava.png";
        this.fireProof = true;
        this.aO = 0.2f;
    }
    
    public boolean canSpawn() {
        return this.world.difficulty > 0 && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }
    
    public int aZ() {
        return this.getSize() * 3;
    }
    
    public float c(final float f) {
        return 1.0f;
    }
    
    protected String h() {
        return "flame";
    }
    
    protected EntitySlime i() {
        return new EntityMagmaCube(this.world);
    }
    
    protected int getLootId() {
        return Item.MAGMA_CREAM.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        final int j = this.getLootId();
        if (j > 0 && this.getSize() > 1) {
            int k = this.random.nextInt(4) - 2;
            if (i > 0) {
                k += this.random.nextInt(i + 1);
            }
            if (k > 0) {
                loot.add(new ItemStack(j, k));
            }
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean isBurning() {
        return false;
    }
    
    protected int j() {
        return super.j() * 4;
    }
    
    protected void k() {
        this.b *= 0.9f;
    }
    
    protected void bl() {
        this.motY = 0.42f + this.getSize() * 0.1f;
        this.an = true;
    }
    
    protected void a(final float f) {
    }
    
    protected boolean l() {
        return true;
    }
    
    protected int m() {
        return super.m() + 2;
    }
    
    protected String bc() {
        return "mob.slime." + ((this.getSize() > 1) ? "big" : "small");
    }
    
    protected String bd() {
        return "mob.slime." + ((this.getSize() > 1) ? "big" : "small");
    }
    
    protected String n() {
        return (this.getSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
    }
    
    public boolean I() {
        return false;
    }
    
    protected boolean o() {
        return true;
    }
}
