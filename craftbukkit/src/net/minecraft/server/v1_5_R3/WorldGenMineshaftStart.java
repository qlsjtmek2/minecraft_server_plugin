// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftStart extends StructureStart
{
    public WorldGenMineshaftStart(final World world, final Random random, final int n, final int n2) {
        final WorldGenMineshaftRoom worldGenMineshaftRoom = new WorldGenMineshaftRoom(0, random, (n << 4) + 2, (n2 << 4) + 2);
        this.a.add(worldGenMineshaftRoom);
        worldGenMineshaftRoom.a(worldGenMineshaftRoom, this.a, random);
        this.c();
        this.a(world, random, 10);
    }
}
