// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemEnderEye extends Item
{
    public ItemEnderEye(final int n) {
        super(n);
        this.a(CreativeModeTab.f);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityHuman, final World world, final int n, final int j, final int n2, final int l, final float n3, final float n4, final float n5) {
        final int typeId = world.getTypeId(n, j, n2);
        final int data = world.getData(n, j, n2);
        if (!entityHuman.a(n, j, n2, l, itemstack) || typeId != Block.ENDER_PORTAL_FRAME.id || BlockEnderPortalFrame.d(data)) {
            return false;
        }
        if (world.isStatic) {
            return true;
        }
        world.setData(n, j, n2, data + 4, 2);
        --itemstack.count;
        for (int i = 0; i < 16; ++i) {
            world.addParticle("smoke", n + (5.0f + ItemEnderEye.e.nextFloat() * 6.0f) / 16.0f, j + 0.8125f, n2 + (5.0f + ItemEnderEye.e.nextFloat() * 6.0f) / 16.0f, 0.0, 0.0, 0.0);
        }
        final int n6 = data & 0x3;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 1;
        final int n11 = Direction.g[n6];
        for (int k = -2; k <= 2; ++k) {
            final int n12 = n + Direction.a[n11] * k;
            final int n13 = n2 + Direction.b[n11] * k;
            if (world.getTypeId(n12, j, n13) == Block.ENDER_PORTAL_FRAME.id) {
                if (!BlockEnderPortalFrame.d(world.getData(n12, j, n13))) {
                    n10 = 0;
                    break;
                }
                n8 = k;
                if (n9 == 0) {
                    n7 = k;
                    n9 = 1;
                }
            }
        }
        if (n10 != 0 && n8 == n7 + 2) {
            for (int n14 = n7; n14 <= n8; ++n14) {
                final int n15 = n + Direction.a[n11] * n14;
                final int n16 = n2 + Direction.b[n11] * n14;
                final int n17 = n15 + Direction.a[n6] * 4;
                final int n18 = n16 + Direction.b[n6] * 4;
                final int typeId2 = world.getTypeId(n17, j, n18);
                final int data2 = world.getData(n17, j, n18);
                if (typeId2 != Block.ENDER_PORTAL_FRAME.id || !BlockEnderPortalFrame.d(data2)) {
                    n10 = 0;
                    break;
                }
            }
            for (int n19 = n7 - 1; n19 <= n8 + 1; n19 += 4) {
                for (int n20 = 1; n20 <= 3; ++n20) {
                    final int n21 = n + Direction.a[n11] * n19;
                    final int n22 = n2 + Direction.b[n11] * n19;
                    final int n23 = n21 + Direction.a[n6] * n20;
                    final int n24 = n22 + Direction.b[n6] * n20;
                    final int typeId3 = world.getTypeId(n23, j, n24);
                    final int data3 = world.getData(n23, j, n24);
                    if (typeId3 != Block.ENDER_PORTAL_FRAME.id || !BlockEnderPortalFrame.d(data3)) {
                        n10 = 0;
                        break;
                    }
                }
            }
            if (n10 != 0) {
                for (int n25 = n7; n25 <= n8; ++n25) {
                    for (int n26 = 1; n26 <= 3; ++n26) {
                        world.setTypeIdAndData(n + Direction.a[n11] * n25 + Direction.a[n6] * n26, j, n2 + Direction.b[n11] * n25 + Direction.b[n6] * n26, Block.ENDER_PORTAL.id, 0, 2);
                    }
                }
            }
        }
        return true;
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entity) {
        final MovingObjectPosition a = this.a(world, entity, false);
        if (a != null && a.type == EnumMovingObjectType.TILE && world.getTypeId(a.b, a.c, a.d) == Block.ENDER_PORTAL_FRAME.id) {
            return itemStack;
        }
        if (!world.isStatic) {
            final ChunkPosition b = world.b("Stronghold", (int)entity.locX, (int)entity.locY, (int)entity.locZ);
            if (b != null) {
                final EntityEnderSignal entity2 = new EntityEnderSignal(world, entity.locX, entity.locY + 1.62 - entity.height, entity.locZ);
                entity2.a(b.x, b.y, (double)b.z);
                world.addEntity(entity2);
                world.makeSound(entity, "random.bow", 0.5f, 0.4f / (ItemEnderEye.e.nextFloat() * 0.4f + 0.8f));
                world.a(null, 1002, (int)entity.locX, (int)entity.locY, (int)entity.locZ, 0);
                if (!entity.abilities.canInstantlyBuild) {
                    --itemStack.count;
                }
            }
        }
        return itemStack;
    }
}
