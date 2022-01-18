// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Chunk;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.event.world.ChunkUnloadEvent;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Listener;

public class ChunkUnload implements Listener
{
    private EpicBoss eb;
    
    public ChunkUnload(final EpicBoss eb) {
        this.eb = eb;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void Chunk(final ChunkUnloadEvent e) {
        for (final Boss b : this.getChunkBosses(e.getChunk())) {
            b.getLivingEntity().remove();
        }
    }
    
    public List<Boss> getChunkBosses(final Chunk c) {
        final List<Boss> bosses = new ArrayList<Boss>();
        for (final Boss b : this.eb.BossList) {
            if (!b.getSaved() && b.getLocation().getChunk() == c) {
                bosses.add(b);
            }
        }
        return bosses;
    }
}
