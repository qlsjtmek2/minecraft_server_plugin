// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import java.util.Set;
import java.util.Map;

public class StandardMessenger implements Messenger
{
    private final Map<String, Set<PluginMessageListenerRegistration>> incomingByChannel;
    private final Map<Plugin, Set<PluginMessageListenerRegistration>> incomingByPlugin;
    private final Map<String, Set<Plugin>> outgoingByChannel;
    private final Map<Plugin, Set<String>> outgoingByPlugin;
    private final Object incomingLock;
    private final Object outgoingLock;
    
    public StandardMessenger() {
        this.incomingByChannel = new HashMap<String, Set<PluginMessageListenerRegistration>>();
        this.incomingByPlugin = new HashMap<Plugin, Set<PluginMessageListenerRegistration>>();
        this.outgoingByChannel = new HashMap<String, Set<Plugin>>();
        this.outgoingByPlugin = new HashMap<Plugin, Set<String>>();
        this.incomingLock = new Object();
        this.outgoingLock = new Object();
    }
    
    private void addToOutgoing(final Plugin plugin, final String channel) {
        synchronized (this.outgoingLock) {
            Set<Plugin> plugins = this.outgoingByChannel.get(channel);
            Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (plugins == null) {
                plugins = new HashSet<Plugin>();
                this.outgoingByChannel.put(channel, plugins);
            }
            if (channels == null) {
                channels = new HashSet<String>();
                this.outgoingByPlugin.put(plugin, channels);
            }
            plugins.add(plugin);
            channels.add(channel);
        }
    }
    
    private void removeFromOutgoing(final Plugin plugin, final String channel) {
        synchronized (this.outgoingLock) {
            final Set<Plugin> plugins = this.outgoingByChannel.get(channel);
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (plugins != null) {
                plugins.remove(plugin);
                if (plugins.isEmpty()) {
                    this.outgoingByChannel.remove(channel);
                }
            }
            if (channels != null) {
                channels.remove(channel);
                if (channels.isEmpty()) {
                    this.outgoingByChannel.remove(channel);
                }
            }
        }
    }
    
    private void removeFromOutgoing(final Plugin plugin) {
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (channels != null) {
                final String[] toRemove = channels.toArray(new String[0]);
                this.outgoingByPlugin.remove(plugin);
                for (final String channel : toRemove) {
                    this.removeFromOutgoing(plugin, channel);
                }
            }
        }
    }
    
    private void addToIncoming(final PluginMessageListenerRegistration registration) {
        synchronized (this.incomingLock) {
            Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
            if (registrations == null) {
                registrations = new HashSet<PluginMessageListenerRegistration>();
                this.incomingByChannel.put(registration.getChannel(), registrations);
            }
            else if (registrations.contains(registration)) {
                throw new IllegalArgumentException("This registration already exists");
            }
            registrations.add(registration);
            registrations = this.incomingByPlugin.get(registration.getPlugin());
            if (registrations == null) {
                registrations = new HashSet<PluginMessageListenerRegistration>();
                this.incomingByPlugin.put(registration.getPlugin(), registrations);
            }
            else if (registrations.contains(registration)) {
                throw new IllegalArgumentException("This registration already exists");
            }
            registrations.add(registration);
        }
    }
    
    private void removeFromIncoming(final PluginMessageListenerRegistration registration) {
        synchronized (this.incomingLock) {
            Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
            if (registrations != null) {
                registrations.remove(registration);
                if (registrations.isEmpty()) {
                    this.incomingByChannel.remove(registration.getChannel());
                }
            }
            registrations = this.incomingByPlugin.get(registration.getPlugin());
            if (registrations != null) {
                registrations.remove(registration);
                if (registrations.isEmpty()) {
                    this.incomingByPlugin.remove(registration.getPlugin());
                }
            }
        }
    }
    
    private void removeFromIncoming(final Plugin plugin, final String channel) {
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final PluginMessageListenerRegistration[] arr$;
                final PluginMessageListenerRegistration[] toRemove = arr$ = registrations.toArray(new PluginMessageListenerRegistration[0]);
                for (final PluginMessageListenerRegistration registration : arr$) {
                    if (registration.getChannel().equals(channel)) {
                        this.removeFromIncoming(registration);
                    }
                }
            }
        }
    }
    
    private void removeFromIncoming(final Plugin plugin) {
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final PluginMessageListenerRegistration[] toRemove = registrations.toArray(new PluginMessageListenerRegistration[0]);
                this.incomingByPlugin.remove(plugin);
                for (final PluginMessageListenerRegistration registration : toRemove) {
                    this.removeFromIncoming(registration);
                }
            }
        }
    }
    
    public boolean isReservedChannel(final String channel) {
        validateChannel(channel);
        return channel.equals("REGISTER") || channel.equals("UNREGISTER");
    }
    
    public void registerOutgoingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        if (this.isReservedChannel(channel)) {
            throw new ReservedChannelException(channel);
        }
        this.addToOutgoing(plugin, channel);
    }
    
    public void unregisterOutgoingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        this.removeFromOutgoing(plugin, channel);
    }
    
    public void unregisterOutgoingPluginChannel(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.removeFromOutgoing(plugin);
    }
    
    public PluginMessageListenerRegistration registerIncomingPluginChannel(final Plugin plugin, final String channel, final PluginMessageListener listener) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        if (this.isReservedChannel(channel)) {
            throw new ReservedChannelException(channel);
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        final PluginMessageListenerRegistration result = new PluginMessageListenerRegistration(this, plugin, channel, listener);
        this.addToIncoming(result);
        return result;
    }
    
    public void unregisterIncomingPluginChannel(final Plugin plugin, final String channel, final PluginMessageListener listener) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        validateChannel(channel);
        this.removeFromIncoming(new PluginMessageListenerRegistration(this, plugin, channel, listener));
    }
    
    public void unregisterIncomingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        this.removeFromIncoming(plugin, channel);
    }
    
    public void unregisterIncomingPluginChannel(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.removeFromIncoming(plugin);
    }
    
    public Set<String> getOutgoingChannels() {
        synchronized (this.outgoingLock) {
            final Set<String> keys = this.outgoingByChannel.keySet();
            return (Set<String>)ImmutableSet.copyOf((Collection<?>)keys);
        }
    }
    
    public Set<String> getOutgoingChannels(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (channels != null) {
                return (Set<String>)ImmutableSet.copyOf((Collection<?>)channels);
            }
            return (Set<String>)ImmutableSet.of();
        }
    }
    
    public Set<String> getIncomingChannels() {
        synchronized (this.incomingLock) {
            final Set<String> keys = this.incomingByChannel.keySet();
            return (Set<String>)ImmutableSet.copyOf((Collection<?>)keys);
        }
    }
    
    public Set<String> getIncomingChannels(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
                for (final PluginMessageListenerRegistration registration : registrations) {
                    builder.add(registration.getChannel());
                }
                return builder.build();
            }
            return (Set<String>)ImmutableSet.of();
        }
    }
    
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                return (Set<PluginMessageListenerRegistration>)ImmutableSet.copyOf((Collection<?>)registrations);
            }
            return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
        }
    }
    
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final String channel) {
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(channel);
            if (registrations != null) {
                return (Set<PluginMessageListenerRegistration>)ImmutableSet.copyOf((Collection<?>)registrations);
            }
            return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
        }
    }
    
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final ImmutableSet.Builder<PluginMessageListenerRegistration> builder = ImmutableSet.builder();
                for (final PluginMessageListenerRegistration registration : registrations) {
                    if (registration.getChannel().equals(channel)) {
                        builder.add(registration);
                    }
                }
                return builder.build();
            }
            return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
        }
    }
    
    public boolean isRegistrationValid(final PluginMessageListenerRegistration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("Registration cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(registration.getPlugin());
            return registrations != null && registrations.contains(registration);
        }
    }
    
    public boolean isIncomingChannelRegistered(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                for (final PluginMessageListenerRegistration registration : registrations) {
                    if (registration.getChannel().equals(channel)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    public boolean isOutgoingChannelRegistered(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            return channels != null && channels.contains(channel);
        }
    }
    
    public void dispatchIncomingMessage(final Player source, final String channel, final byte[] message) {
        if (source == null) {
            throw new IllegalArgumentException("Player source cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        validateChannel(channel);
        final Set<PluginMessageListenerRegistration> registrations = this.getIncomingChannelRegistrations(channel);
        for (final PluginMessageListenerRegistration registration : registrations) {
            registration.getListener().onPluginMessageReceived(channel, source, message);
        }
    }
    
    public static void validateChannel(final String channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        if (channel.length() > 16) {
            throw new ChannelNameTooLongException(channel);
        }
    }
    
    public static void validatePluginMessage(final Messenger messenger, final Plugin source, final String channel, final byte[] message) {
        if (messenger == null) {
            throw new IllegalArgumentException("Messenger cannot be null");
        }
        if (source == null) {
            throw new IllegalArgumentException("Plugin source cannot be null");
        }
        if (!source.isEnabled()) {
            throw new IllegalArgumentException("Plugin must be enabled to send messages");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (!messenger.isOutgoingChannelRegistered(source, channel)) {
            throw new ChannelNotRegisteredException(channel);
        }
        if (message.length > 32766) {
            throw new MessageTooLargeException(message);
        }
        validateChannel(channel);
    }
}
