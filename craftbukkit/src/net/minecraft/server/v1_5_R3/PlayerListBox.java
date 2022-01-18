// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Vector;
import javax.swing.JList;

public class PlayerListBox extends JList implements IUpdatePlayerListBox
{
    private MinecraftServer a;
    private int b;
    
    public PlayerListBox(final MinecraftServer a) {
        this.b = 0;
        (this.a = a).a(this);
    }
    
    public void a() {
        if (this.b++ % 20 == 0) {
            final Vector<String> listData = new Vector<String>();
            for (int i = 0; i < this.a.getPlayerList().players.size(); ++i) {
                listData.add(((EntityPlayer)this.a.getPlayerList().players.get(i)).name);
            }
            this.setListData(listData);
        }
    }
}
