// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet208SetScoreboardDisplayObjective extends Packet
{
    public int a;
    public String b;
    
    public Packet208SetScoreboardDisplayObjective() {
    }
    
    public Packet208SetScoreboardDisplayObjective(final int a, final ScoreboardObjective scoreboardObjective) {
        this.a = a;
        if (scoreboardObjective == null) {
            this.b = "";
        }
        else {
            this.b = scoreboardObjective.getName();
        }
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readByte();
        this.b = Packet.a(datainputstream, 16);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a);
        Packet.a(this.b, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 3 + this.b.length();
    }
}
