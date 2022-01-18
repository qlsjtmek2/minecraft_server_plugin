// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.Set;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import java.util.HashSet;
import org.bukkit.entity.Player;

public class LazyPlayerSet extends LazyHashSet<Player>
{
    HashSet<Player> makeReference() {
        if (this.reference != null) {
            throw new IllegalStateException("Reference already created!");
        }
        final List<EntityPlayer> players = (List<EntityPlayer>)MinecraftServer.getServer().getPlayerList().players;
        final HashSet<Player> reference = new HashSet<Player>(players.size());
        for (final EntityPlayer player : players) {
            reference.add(player.getBukkitEntity());
        }
        return reference;
    }
}
