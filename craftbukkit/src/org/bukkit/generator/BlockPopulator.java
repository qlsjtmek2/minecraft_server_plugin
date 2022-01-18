// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.generator;

import org.bukkit.Chunk;
import java.util.Random;
import org.bukkit.World;

public abstract class BlockPopulator
{
    public abstract void populate(final World p0, final Random p1, final Chunk p2);
}
