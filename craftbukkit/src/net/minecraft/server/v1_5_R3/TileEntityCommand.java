// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.CommandSender;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import com.google.common.base.Joiner;
import org.bukkit.craftbukkit.v1_5_R3.command.CraftBlockCommandSender;
import org.bukkit.command.BlockCommandSender;

public class TileEntityCommand extends TileEntity implements ICommandListener
{
    private int a;
    public String b;
    private String c;
    private final BlockCommandSender sender;
    
    public TileEntityCommand() {
        this.a = 0;
        this.b = "";
        this.c = "@";
        this.sender = new CraftBlockCommandSender(this);
    }
    
    public void b(final String s) {
        this.b = s;
        this.update();
    }
    
    public int a(final World world) {
        if (world.isStatic) {
            return 0;
        }
        final MinecraftServer minecraftserver = MinecraftServer.getServer();
        if (minecraftserver == null || !minecraftserver.getEnableCommandBlock()) {
            return 0;
        }
        final SimpleCommandMap commandMap = minecraftserver.server.getCommandMap();
        final Joiner joiner = Joiner.on(" ");
        String command = this.b;
        if (this.b.startsWith("/")) {
            command = this.b.substring(1);
        }
        final String[] args = command.split(" ");
        ArrayList<String[]> commands = new ArrayList<String[]>();
        if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("op") || args[0].equalsIgnoreCase("deop") || args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("ban-ip") || args[0].equalsIgnoreCase("pardon") || args[0].equalsIgnoreCase("pardon-ip") || args[0].equalsIgnoreCase("reload")) {
            return 0;
        }
        if (commandMap.getCommand(args[0]) == null) {
            return 0;
        }
        if (this.world.players.isEmpty()) {
            return 0;
        }
        if (!args[0].equalsIgnoreCase("testfor")) {
            commands.add(args);
            ArrayList<String[]> newCommands = new ArrayList<String[]>();
            for (int i = 0; i < args.length; ++i) {
                if (PlayerSelector.isPattern(args[i])) {
                    for (int j = 0; j < commands.size(); ++j) {
                        newCommands.addAll(this.buildCommands(commands.get(j), i));
                    }
                    final ArrayList<String[]> temp = commands;
                    commands = newCommands;
                    newCommands = temp;
                    newCommands.clear();
                }
            }
            int completed = 0;
            for (int k = 0; k < commands.size(); ++k) {
                try {
                    if (commandMap.dispatch(this.sender, joiner.join(Arrays.asList((String[])commands.get(k))))) {
                        ++completed;
                    }
                }
                catch (Throwable exception) {
                    minecraftserver.getLogger().warning(String.format("CommandBlock at (%d,%d,%d) failed to handle command", this.x, this.y, this.z), exception);
                }
            }
            return completed;
        }
        if (args.length < 2) {
            return 0;
        }
        final EntityPlayer[] players = PlayerSelector.getPlayers(this, args[1]);
        if (players != null && players.length > 0) {
            return players.length;
        }
        final EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(args[1]);
        if (player == null) {
            return 0;
        }
        return 1;
    }
    
    private ArrayList<String[]> buildCommands(final String[] args, final int pos) {
        final ArrayList<String[]> commands = new ArrayList<String[]>();
        final EntityPlayer[] players = PlayerSelector.getPlayers(this, args[pos]);
        if (players != null) {
            for (final EntityPlayer player : players) {
                if (player.world == this.world) {
                    final String[] command = args.clone();
                    command[pos] = player.getLocalizedName();
                    commands.add(command);
                }
            }
        }
        return commands;
    }
    
    public String getName() {
        return this.c;
    }
    
    public void c(final String s) {
        this.c = s;
    }
    
    public void sendMessage(final String s) {
    }
    
    public boolean a(final int i, final String s) {
        return i <= 2;
    }
    
    public String a(final String s, final Object... aobject) {
        return s;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setString("Command", this.b);
        nbttagcompound.setInt("SuccessCount", this.a);
        nbttagcompound.setString("CustomName", this.c);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.getString("Command");
        this.a = nbttagcompound.getInt("SuccessCount");
        if (nbttagcompound.hasKey("CustomName")) {
            this.c = nbttagcompound.getString("CustomName");
        }
    }
    
    public ChunkCoordinates b() {
        return new ChunkCoordinates(this.x, this.y, this.z);
    }
    
    public Packet getUpdatePacket() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.b(nbttagcompound);
        return new Packet132TileEntityData(this.x, this.y, this.z, 2, nbttagcompound);
    }
    
    public int d() {
        return this.a;
    }
    
    public void a(final int i) {
        this.a = i;
    }
}
