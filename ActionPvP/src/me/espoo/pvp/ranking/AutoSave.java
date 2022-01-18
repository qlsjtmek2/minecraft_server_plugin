// 
// Decompiled by Procyon v0.5.30
// 

package me.espoo.pvp.ranking;

import me.espoo.pvp.API;
import me.espoo.pvp.main;
import me.espoo.pvp.yml.PVPPlayer;

public class AutoSave implements Runnable
{
    @Override
    public void run() {
        for (PVPPlayer player : API.pvpplayer.values()) {
            player.saveRpgPlayer();
        }
        main.user.saveConfig();
        System.out.println("[ActionPvP] 유저 파일 자동 저장 완료");
    }
}
