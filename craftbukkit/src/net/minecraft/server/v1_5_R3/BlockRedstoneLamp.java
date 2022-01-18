// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class BlockRedstoneLamp extends Block
{
    private final boolean a;
    
    public BlockRedstoneLamp(final int i, final boolean flag) {
        super(i, Material.BUILDABLE_GLASS);
        this.a = flag;
        if (flag) {
            this.a(1.0f);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        if (!world.isStatic) {
            if (this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
                world.a(i, j, k, this.id, 4);
            }
            else if (!this.a && world.isBlockIndirectlyPowered(i, j, k)) {
                if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
                    return;
                }
                world.setTypeIdAndData(i, j, k, Block.REDSTONE_LAMP_ON.id, 0, 2);
            }
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            if (this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
                world.a(i, j, k, this.id, 4);
            }
            else if (!this.a && world.isBlockIndirectlyPowered(i, j, k)) {
                if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
                    return;
                }
                world.setTypeIdAndData(i, j, k, Block.REDSTONE_LAMP_ON.id, 0, 2);
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic && this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
            if (CraftEventFactory.callRedstoneChange(world, i, j, k, 15, 0).getNewCurrent() != 0) {
                return;
            }
            world.setTypeIdAndData(i, j, k, Block.REDSTONE_LAMP_OFF.id, 0, 2);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Block.REDSTONE_LAMP_OFF.id;
    }
}
