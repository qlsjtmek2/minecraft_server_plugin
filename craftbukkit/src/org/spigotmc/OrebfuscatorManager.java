// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc;

import gnu.trove.set.TByteSet;
import java.util.Iterator;
import gnu.trove.set.hash.TByteHashSet;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.World;

public class OrebfuscatorManager
{
    private static final CustomTimingsHandler update;
    private static final CustomTimingsHandler obfuscate;
    private static final boolean[] obfuscateBlocks;
    private static byte[] replacementOres;
    
    public static void updateNearbyBlocks(final World world, final int x, final int y, final int z) {
        if (world.getWorld().obfuscated) {
            OrebfuscatorManager.update.startTiming();
            updateNearbyBlocks(world, x, y, z, 2);
            OrebfuscatorManager.update.stopTiming();
        }
    }
    
    public static void obfuscateSync(final int chunkX, final int chunkY, final int bitmask, final byte[] buffer, final World world) {
        if (world.getWorld().obfuscated) {
            OrebfuscatorManager.obfuscate.startTiming();
            obfuscate(chunkX, chunkY, bitmask, buffer, world);
            OrebfuscatorManager.obfuscate.stopTiming();
        }
    }
    
    public static void obfuscate(final int chunkX, final int chunkY, final int bitmask, final byte[] buffer, final World world) {
        if (world.getWorld().obfuscated) {
            final int initialRadius = 1;
            int index = 0;
            int randomOre = 0;
            final int startX = chunkX << 4;
            final int startZ = chunkY << 4;
            for (int i = 0; i < 16; ++i) {
                if ((bitmask & 1 << i) != 0x0) {
                    for (int y = 0; y < 16; ++y) {
                        for (int z = 0; z < 16; ++z) {
                            for (int x = 0; x < 16; ++x) {
                                final int blockId = buffer[index] & 0xFF;
                                if (OrebfuscatorManager.obfuscateBlocks[blockId]) {
                                    if (initialRadius != 0 && !isLoaded(world, startX + x, (i << 4) + y, startZ + z, initialRadius)) {
                                        continue;
                                    }
                                    if (initialRadius == 0 || !hasTransparentBlockAdjacent(world, startX + x, (i << 4) + y, startZ + z, initialRadius)) {
                                        switch (world.getServer().orebfuscatorEngineMode) {
                                            case 1: {
                                                buffer[index] = (byte)Block.STONE.id;
                                                break;
                                            }
                                            case 2: {
                                                if (randomOre >= OrebfuscatorManager.replacementOres.length) {
                                                    randomOre = 0;
                                                }
                                                buffer[index] = OrebfuscatorManager.replacementOres[randomOre++];
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (++index >= buffer.length) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void updateNearbyBlocks(final World world, final int x, final int y, final int z, final int radius) {
        if (world.isLoaded(x, y, z)) {
            final int id = world.getTypeId(x, y, z);
            if (OrebfuscatorManager.obfuscateBlocks[id]) {
                world.notify(x, y, z);
            }
            if (radius > 0) {
                updateNearbyBlocks(world, x + 1, y, z, radius - 1);
                updateNearbyBlocks(world, x - 1, y, z, radius - 1);
                updateNearbyBlocks(world, x, y + 1, z, radius - 1);
                updateNearbyBlocks(world, x, y - 1, z, radius - 1);
                updateNearbyBlocks(world, x, y, z + 1, radius - 1);
                updateNearbyBlocks(world, x, y, z - 1, radius - 1);
            }
        }
    }
    
    private static boolean isLoaded(final World world, final int x, final int y, final int z, final int radius) {
        return world.isLoaded(x, y, z) || (radius > 0 && (isLoaded(world, x + 1, y, z, radius - 1) || isLoaded(world, x - 1, y, z, radius - 1) || isLoaded(world, x, y + 1, z, radius - 1) || isLoaded(world, x, y - 1, z, radius - 1) || isLoaded(world, x, y, z + 1, radius - 1) || isLoaded(world, x, y, z - 1, radius - 1)));
    }
    
    private static boolean hasTransparentBlockAdjacent(final World world, final int x, final int y, final int z, final int radius) {
        return !Block.l(world.getTypeId(x, y, z)) || (radius > 0 && (hasTransparentBlockAdjacent(world, x + 1, y, z, radius - 1) || hasTransparentBlockAdjacent(world, x - 1, y, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y + 1, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y - 1, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y, z + 1, radius - 1) || hasTransparentBlockAdjacent(world, x, y, z - 1, radius - 1)));
    }
    
    static {
        update = new CustomTimingsHandler("xray - update");
        obfuscate = new CustomTimingsHandler("xray - obfuscate");
        obfuscateBlocks = new boolean[32767];
        for (final short id : MinecraftServer.getServer().server.orebfuscatorBlocks) {
            OrebfuscatorManager.obfuscateBlocks[id] = true;
        }
        final TByteSet blocks = new TByteHashSet();
        for (int i = 0; i < OrebfuscatorManager.obfuscateBlocks.length; ++i) {
            if (OrebfuscatorManager.obfuscateBlocks[i]) {
                final Block block = Block.byId[i];
                if (block != null && !block.t()) {
                    blocks.add((byte)i);
                }
            }
        }
        OrebfuscatorManager.replacementOres = blocks.toArray();
    }
}
