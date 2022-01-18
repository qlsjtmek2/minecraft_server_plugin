// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Connection;
import net.minecraft.server.v1_5_R3.INetworkManager;
import java.util.Arrays;
import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import java.util.Map;

public class PacketListener
{
    private static final Map<PacketListener, Plugin> listeners;
    private static PacketListener[] baked;
    
    public static synchronized void register(final PacketListener listener, final Plugin plugin) {
        Preconditions.checkNotNull(listener, (Object)"listener");
        Preconditions.checkNotNull(plugin, (Object)"plugin");
        Preconditions.checkState(!PacketListener.listeners.containsKey(listener), (Object)"listener already registered");
        final int size = PacketListener.listeners.size();
        Preconditions.checkState(PacketListener.baked.length == size);
        PacketListener.listeners.put(listener, plugin);
        (PacketListener.baked = Arrays.copyOf(PacketListener.baked, size + 1))[size] = listener;
    }
    
    static Packet callReceived(final INetworkManager networkManager, final Connection connection, Packet packet) {
        for (final PacketListener listener : PacketListener.baked) {
            try {
                packet = listener.packetReceived(networkManager, connection, packet);
            }
            catch (Throwable t) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Error whilst firing receive hook for packet", t);
            }
        }
        return packet;
    }
    
    static Packet callQueued(final INetworkManager networkManager, final Connection connection, Packet packet) {
        for (final PacketListener listener : PacketListener.baked) {
            try {
                packet = listener.packetQueued(networkManager, connection, packet);
            }
            catch (Throwable t) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Error whilst firing queued hook for packet", t);
            }
        }
        return packet;
    }
    
    public Packet packetReceived(final INetworkManager networkManager, final Connection connection, final Packet packet) {
        return packet;
    }
    
    public Packet packetQueued(final INetworkManager networkManager, final Connection connection, final Packet packet) {
        return packet;
    }
    
    static {
        listeners = new HashMap<PacketListener, Plugin>();
        PacketListener.baked = new PacketListener[0];
    }
}
