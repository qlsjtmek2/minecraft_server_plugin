// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.command.defaults.ScoreboardCommand;
import org.bukkit.command.defaults.EffectCommand;
import org.bukkit.command.defaults.TestForCommand;
import org.bukkit.command.defaults.EnchantCommand;
import org.bukkit.command.defaults.GameRuleCommand;
import org.bukkit.command.defaults.ClearCommand;
import org.bukkit.command.defaults.SpawnpointCommand;
import org.bukkit.command.defaults.WeatherCommand;
import org.bukkit.command.defaults.DifficultyCommand;
import org.bukkit.command.defaults.SeedCommand;
import org.bukkit.command.defaults.DefaultGameModeCommand;
import org.bukkit.command.defaults.BanListCommand;
import org.bukkit.command.defaults.ToggleDownfallCommand;
import org.bukkit.command.defaults.ExpCommand;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.command.defaults.GameModeCommand;
import org.bukkit.command.defaults.KillCommand;
import org.bukkit.command.defaults.MeCommand;
import org.bukkit.command.defaults.TellCommand;
import org.bukkit.command.defaults.WhitelistCommand;
import org.bukkit.command.defaults.SayCommand;
import org.bukkit.command.defaults.TimeCommand;
import org.bukkit.command.defaults.GiveCommand;
import org.bukkit.command.defaults.TeleportCommand;
import org.bukkit.command.defaults.KickCommand;
import org.bukkit.command.defaults.PardonCommand;
import org.bukkit.command.defaults.BanCommand;
import org.bukkit.command.defaults.PardonIpCommand;
import org.bukkit.command.defaults.BanIpCommand;
import org.bukkit.command.defaults.DeopCommand;
import org.bukkit.command.defaults.OpCommand;
import org.bukkit.command.defaults.ListCommand;
import java.util.Collection;
import java.util.Comparator;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.util.Java15Compat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.defaults.TimingsCommand;
import org.bukkit.command.defaults.PluginsCommand;
import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.command.defaults.StopCommand;
import org.bukkit.command.defaults.SaveOffCommand;
import org.bukkit.command.defaults.SaveOnCommand;
import org.bukkit.command.defaults.SaveCommand;
import java.util.HashSet;
import java.util.HashMap;
import org.bukkit.command.defaults.VanillaCommand;
import org.bukkit.Server;
import java.util.Set;
import java.util.Map;
import java.util.regex.Pattern;

public class SimpleCommandMap implements CommandMap
{
    private static final Pattern PATTERN_ON_SPACE;
    protected final Map<String, Command> knownCommands;
    protected final Set<String> aliases;
    private final Server server;
    protected static final Set<VanillaCommand> fallbackCommands;
    
    public SimpleCommandMap(final Server server) {
        this.knownCommands = new HashMap<String, Command>();
        this.aliases = new HashSet<String>();
        this.setDefaultCommands(this.server = server);
    }
    
    private void setDefaultCommands(final Server server) {
        this.register("bukkit", new SaveCommand());
        this.register("bukkit", new SaveOnCommand());
        this.register("bukkit", new SaveOffCommand());
        this.register("bukkit", new StopCommand());
        this.register("bukkit", new VersionCommand("version"));
        this.register("bukkit", new ReloadCommand("reload"));
        this.register("bukkit", new PluginsCommand("plugins"));
        this.register("bukkit", new TimingsCommand("timings"));
    }
    
    public void registerAll(final String fallbackPrefix, final List<Command> commands) {
        if (commands != null) {
            for (final Command c : commands) {
                this.register(fallbackPrefix, c);
            }
        }
    }
    
    public boolean register(final String fallbackPrefix, final Command command) {
        return this.register(command.getName(), fallbackPrefix, command);
    }
    
    public boolean register(final String label, final String fallbackPrefix, final Command command) {
        final boolean registeredPassedLabel = this.register(label, fallbackPrefix, command, false);
        final Iterator<String> iterator = command.getAliases().iterator();
        while (iterator.hasNext()) {
            if (!this.register(iterator.next(), fallbackPrefix, command, true)) {
                iterator.remove();
            }
        }
        command.register(this);
        return registeredPassedLabel;
    }
    
    private synchronized boolean register(final String label, final String fallbackPrefix, final Command command, final boolean isAlias) {
        String lowerLabel = label.trim().toLowerCase();
        if (isAlias && this.knownCommands.containsKey(lowerLabel)) {
            return false;
        }
        final String lowerPrefix = fallbackPrefix.trim().toLowerCase();
        boolean registerdPassedLabel = true;
        while (this.knownCommands.containsKey(lowerLabel) && !this.aliases.contains(lowerLabel)) {
            lowerLabel = lowerPrefix + ":" + lowerLabel;
            registerdPassedLabel = false;
        }
        if (isAlias) {
            this.aliases.add(lowerLabel);
        }
        else {
            this.aliases.remove(lowerLabel);
            command.setLabel(lowerLabel);
        }
        this.knownCommands.put(lowerLabel, command);
        return registerdPassedLabel;
    }
    
    protected Command getFallback(final String label) {
        for (final VanillaCommand cmd : SimpleCommandMap.fallbackCommands) {
            if (cmd.matches(label)) {
                return cmd;
            }
        }
        return null;
    }
    
    public Set<VanillaCommand> getFallbackCommands() {
        return Collections.unmodifiableSet((Set<? extends VanillaCommand>)SimpleCommandMap.fallbackCommands);
    }
    
    public boolean dispatch(final CommandSender sender, final String commandLine) throws CommandException {
        final String[] args = SimpleCommandMap.PATTERN_ON_SPACE.split(commandLine);
        if (args.length == 0) {
            return false;
        }
        final String sentCommandLabel = args[0].toLowerCase();
        final Command target = this.getCommand(sentCommandLabel);
        if (target == null) {
            return false;
        }
        try {
            target.execute(sender, sentCommandLabel, Java15Compat.Arrays_copyOfRange(args, 1, args.length));
        }
        catch (CommandException ex) {
            throw ex;
        }
        catch (Throwable ex2) {
            throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex2);
        }
        return true;
    }
    
    public synchronized void clearCommands() {
        for (final Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
            entry.getValue().unregister(this);
        }
        this.knownCommands.clear();
        this.aliases.clear();
        this.setDefaultCommands(this.server);
    }
    
    public Command getCommand(final String name) {
        Command target = this.knownCommands.get(name.toLowerCase());
        if (target == null) {
            target = this.getFallback(name);
        }
        return target;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String cmdLine) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(cmdLine, "Command line cannot null");
        final int spaceIndex = cmdLine.indexOf(32);
        if (spaceIndex == -1) {
            final ArrayList<String> completions = new ArrayList<String>();
            final Map<String, Command> knownCommands = this.knownCommands;
            for (final VanillaCommand command : SimpleCommandMap.fallbackCommands) {
                final String name = command.getName();
                if (!command.testPermissionSilent(sender)) {
                    continue;
                }
                if (knownCommands.containsKey(name)) {
                    continue;
                }
                if (!StringUtil.startsWithIgnoreCase(name, cmdLine)) {
                    continue;
                }
                completions.add('/' + name);
            }
            for (final Map.Entry<String, Command> commandEntry : knownCommands.entrySet()) {
                final Command command2 = commandEntry.getValue();
                if (!command2.testPermissionSilent(sender)) {
                    continue;
                }
                final String name2 = commandEntry.getKey();
                if (!StringUtil.startsWithIgnoreCase(name2, cmdLine)) {
                    continue;
                }
                completions.add('/' + name2);
            }
            Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
            return completions;
        }
        final String commandName = cmdLine.substring(0, spaceIndex);
        final Command target = this.getCommand(commandName);
        if (target == null) {
            return null;
        }
        if (!target.testPermissionSilent(sender)) {
            return null;
        }
        final String argLine = cmdLine.substring(spaceIndex + 1, cmdLine.length());
        final String[] args = SimpleCommandMap.PATTERN_ON_SPACE.split(argLine, -1);
        try {
            return target.tabComplete(sender, commandName, args);
        }
        catch (CommandException ex) {
            throw ex;
        }
        catch (Throwable ex2) {
            throw new CommandException("Unhandled exception executing tab-completer for '" + cmdLine + "' in " + target, ex2);
        }
    }
    
    public Collection<Command> getCommands() {
        return this.knownCommands.values();
    }
    
    public void registerServerAliases() {
        final Map<String, String[]> values = this.server.getCommandAliases();
        for (final String alias : values.keySet()) {
            final String[] targetNames = values.get(alias);
            final List<Command> targets = new ArrayList<Command>();
            final StringBuilder bad = new StringBuilder();
            for (final String name : targetNames) {
                final Command command = this.getCommand(name);
                if (command == null) {
                    if (bad.length() > 0) {
                        bad.append(", ");
                    }
                    bad.append(name);
                }
                else {
                    targets.add(command);
                }
            }
            if (targets.size() > 0) {
                this.knownCommands.put(alias.toLowerCase(), new MultipleCommandAlias(alias.toLowerCase(), targets.toArray(new Command[0])));
            }
            else {
                this.knownCommands.remove(alias.toLowerCase());
            }
            if (bad.length() > 0) {
                this.server.getLogger().warning("The following command(s) could not be aliased under '" + alias + "' because they do not exist: " + (Object)bad);
            }
        }
    }
    
    static {
        PATTERN_ON_SPACE = Pattern.compile(" ", 16);
        (fallbackCommands = new HashSet<VanillaCommand>()).add(new ListCommand());
        SimpleCommandMap.fallbackCommands.add(new OpCommand());
        SimpleCommandMap.fallbackCommands.add(new DeopCommand());
        SimpleCommandMap.fallbackCommands.add(new BanIpCommand());
        SimpleCommandMap.fallbackCommands.add(new PardonIpCommand());
        SimpleCommandMap.fallbackCommands.add(new BanCommand());
        SimpleCommandMap.fallbackCommands.add(new PardonCommand());
        SimpleCommandMap.fallbackCommands.add(new KickCommand());
        SimpleCommandMap.fallbackCommands.add(new TeleportCommand());
        SimpleCommandMap.fallbackCommands.add(new GiveCommand());
        SimpleCommandMap.fallbackCommands.add(new TimeCommand());
        SimpleCommandMap.fallbackCommands.add(new SayCommand());
        SimpleCommandMap.fallbackCommands.add(new WhitelistCommand());
        SimpleCommandMap.fallbackCommands.add(new TellCommand());
        SimpleCommandMap.fallbackCommands.add(new MeCommand());
        SimpleCommandMap.fallbackCommands.add(new KillCommand());
        SimpleCommandMap.fallbackCommands.add(new GameModeCommand());
        SimpleCommandMap.fallbackCommands.add(new HelpCommand());
        SimpleCommandMap.fallbackCommands.add(new ExpCommand());
        SimpleCommandMap.fallbackCommands.add(new ToggleDownfallCommand());
        SimpleCommandMap.fallbackCommands.add(new BanListCommand());
        SimpleCommandMap.fallbackCommands.add(new DefaultGameModeCommand());
        SimpleCommandMap.fallbackCommands.add(new SeedCommand());
        SimpleCommandMap.fallbackCommands.add(new DifficultyCommand());
        SimpleCommandMap.fallbackCommands.add(new WeatherCommand());
        SimpleCommandMap.fallbackCommands.add(new SpawnpointCommand());
        SimpleCommandMap.fallbackCommands.add(new ClearCommand());
        SimpleCommandMap.fallbackCommands.add(new GameRuleCommand());
        SimpleCommandMap.fallbackCommands.add(new EnchantCommand());
        SimpleCommandMap.fallbackCommands.add(new TestForCommand());
        SimpleCommandMap.fallbackCommands.add(new EffectCommand());
        SimpleCommandMap.fallbackCommands.add(new ScoreboardCommand());
    }
}
