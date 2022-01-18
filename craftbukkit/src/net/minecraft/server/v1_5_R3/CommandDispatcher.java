// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;

public class CommandDispatcher extends CommandHandler implements ICommandDispatcher
{
    public CommandDispatcher() {
        this.a(new CommandTime());
        this.a(new CommandGamemode());
        this.a(new CommandDifficulty());
        this.a(new CommandGamemodeDefault());
        this.a(new CommandKill());
        this.a(new CommandToggleDownfall());
        this.a(new CommandWeather());
        this.a(new CommandXp());
        this.a(new CommandTp());
        this.a(new CommandGive());
        this.a(new CommandEffect());
        this.a(new CommandEnchant());
        this.a(new CommandMe());
        this.a(new CommandSeed());
        this.a(new CommandHelp());
        this.a(new CommandDebug());
        this.a(new CommandTell());
        this.a(new CommandSay());
        this.a(new CommandSpawnpoint());
        this.a(new CommandGamerule());
        this.a(new CommandClear());
        this.a(new CommandTestFor());
        this.a(new CommandScoreboard());
        if (MinecraftServer.getServer().T()) {
            this.a(new CommandOp());
            this.a(new CommandDeop());
            this.a(new CommandStop());
            this.a(new CommandSaveAll());
            this.a(new CommandSaveOff());
            this.a(new CommandSaveOn());
            this.a(new CommandBanIp());
            this.a(new CommandPardonIP());
            this.a(new CommandBan());
            this.a(new CommandBanList());
            this.a(new CommandPardon());
            this.a(new CommandKick());
            this.a(new CommandList());
            this.a(new CommandWhitelist());
        }
        else {
            this.a(new CommandPublish());
        }
        CommandAbstract.a(this);
    }
    
    public void a(final ICommandListener commandListener, final int n, final String s, final Object... array) {
        boolean b = true;
        if (commandListener instanceof TileEntityCommand && !MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("commandBlockOutput")) {
            b = false;
        }
        if (b) {
            for (final EntityPlayer entityPlayer : MinecraftServer.getServer().getPlayerList().players) {
                if (entityPlayer != commandListener && MinecraftServer.getServer().getPlayerList().isOp(entityPlayer.name)) {
                    entityPlayer.sendMessage("" + EnumChatFormat.GRAY + "" + EnumChatFormat.ITALIC + "[" + commandListener.getName() + ": " + entityPlayer.a(s, array) + "]");
                }
            }
        }
        if (commandListener != MinecraftServer.getServer()) {
            MinecraftServer.getServer().getLogger().info("[" + commandListener.getName() + ": " + MinecraftServer.getServer().a(s, array) + "]");
        }
        if ((n & 0x1) != 0x1) {
            commandListener.sendMessage(commandListener.a(s, array));
        }
    }
}
