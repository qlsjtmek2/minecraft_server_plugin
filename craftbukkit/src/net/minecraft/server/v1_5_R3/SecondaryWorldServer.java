// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;

public class SecondaryWorldServer extends WorldServer
{
    public SecondaryWorldServer(final MinecraftServer minecraftserver, final IDataManager idatamanager, final String s, final int i, final WorldSettings worldsettings, final WorldServer worldserver, final MethodProfiler methodprofiler, final IConsoleLogManager iconsolelogmanager, final org.bukkit.World.Environment env, final ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, iconsolelogmanager, env, gen);
        this.worldMaps = worldserver.worldMaps;
        this.scoreboard = worldserver.getScoreboard();
    }
}
