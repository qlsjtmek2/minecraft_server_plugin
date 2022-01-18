// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemDoor extends Item
{
    private Material a;
    
    public ItemDoor(final int i, final Material material) {
        super(i);
        this.a = material;
        this.maxStackSize = 1;
        this.a(CreativeModeTab.d);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, int j, final int k, final int l, final float f, final float f1, final float f2) {
        final int clickedY = j;
        if (l != 1) {
            return false;
        }
        ++j;
        Block block;
        if (this.a == Material.WOOD) {
            block = Block.WOODEN_DOOR;
        }
        else {
            block = Block.IRON_DOOR_BLOCK;
        }
        if (!entityhuman.a(i, j, k, l, itemstack) || !entityhuman.a(i, j + 1, k, l, itemstack)) {
            return false;
        }
        if (!block.canPlace(world, i, j, k)) {
            return false;
        }
        final int i2 = MathHelper.floor((entityhuman.yaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
        if (!place(world, i, j, k, i2, block, entityhuman, i, clickedY, k)) {
            return false;
        }
        --itemstack.count;
        return true;
    }
    
    public static void place(final World world, final int i, final int j, final int k, final int l, final Block block) {
        place(world, i, j, k, l, block, null, i, j, k);
    }
    
    public static boolean place(final World world, final int i, final int j, final int k, final int l, final Block block, final EntityHuman entityhuman, final int clickedX, final int clickedY, final int clickedZ) {
        byte b0 = 0;
        byte b2 = 0;
        if (l == 0) {
            b2 = 1;
        }
        if (l == 1) {
            b0 = -1;
        }
        if (l == 2) {
            b2 = -1;
        }
        if (l == 3) {
            b0 = 1;
        }
        final int i2 = (world.u(i - b0, j, k - b2) + world.u(i - b0, j + 1, k - b2)) ? 1 : 0;
        final int j2 = (world.u(i + b0, j, k + b2) + world.u(i + b0, j + 1, k + b2)) ? 1 : 0;
        final boolean flag = world.getTypeId(i - b0, j, k - b2) == block.id || world.getTypeId(i - b0, j + 1, k - b2) == block.id;
        final boolean flag2 = world.getTypeId(i + b0, j, k + b2) == block.id || world.getTypeId(i + b0, j + 1, k + b2) == block.id;
        boolean flag3 = false;
        if (flag && !flag2) {
            flag3 = true;
        }
        else if (j2 > i2) {
            flag3 = true;
        }
        if (entityhuman != null) {
            if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, block.id, l, clickedX, clickedY, clickedZ)) {
                ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet53BlockChange(i, j + 1, k, world));
                return false;
            }
            if (world.getTypeId(i, j, k) != block.id) {
                ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet53BlockChange(i, j + 1, k, world));
                return true;
            }
        }
        else {
            world.setTypeIdAndData(i, j, k, block.id, l, 2);
        }
        world.setTypeIdAndData(i, j + 1, k, block.id, 0x8 | (flag3 ? 1 : 0), 2);
        world.applyPhysics(i, j, k, block.id);
        world.applyPhysics(i, j + 1, k, block.id);
        return true;
    }
}
