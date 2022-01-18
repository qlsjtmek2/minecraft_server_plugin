// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntitySign;
import org.bukkit.block.Sign;

public class CraftSign extends CraftBlockState implements Sign
{
    private final TileEntitySign sign;
    private final String[] lines;
    
    public CraftSign(final Block block) {
        super(block);
        final CraftWorld world = (CraftWorld)block.getWorld();
        this.sign = (TileEntitySign)world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
        if (this.sign == null) {
            this.lines = new String[] { "", "", "", "" };
            return;
        }
        this.lines = new String[this.sign.lines.length];
        System.arraycopy(this.sign.lines, 0, this.lines, 0, this.lines.length);
    }
    
    public String[] getLines() {
        return this.lines;
    }
    
    public String getLine(final int index) throws IndexOutOfBoundsException {
        return this.lines[index];
    }
    
    public void setLine(final int index, final String line) throws IndexOutOfBoundsException {
        this.lines[index] = line;
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result && this.sign != null) {
            for (int i = 0; i < 4; ++i) {
                if (this.lines[i] != null) {
                    this.sign.lines[i] = this.lines[i];
                }
                else {
                    this.sign.lines[i] = "";
                }
            }
            this.sign.update();
        }
        return result;
    }
}
