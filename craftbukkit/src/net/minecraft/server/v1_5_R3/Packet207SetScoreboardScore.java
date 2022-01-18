// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet207SetScoreboardScore extends Packet
{
    public String a;
    public String b;
    public int c;
    public int d;
    
    public Packet207SetScoreboardScore() {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = 0;
    }
    
    public Packet207SetScoreboardScore(final ScoreboardScore scoreboardScore, final int d) {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = 0;
        this.a = scoreboardScore.getPlayerName();
        this.b = scoreboardScore.getObjective().getName();
        this.c = scoreboardScore.getScore();
        this.d = d;
    }
    
    public Packet207SetScoreboardScore(final String a) {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = 0;
        this.a = a;
        this.b = "";
        this.c = 0;
        this.d = 1;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = Packet.a(dataInputStream, 16);
        this.d = dataInputStream.readByte();
        if (this.d != 1) {
            this.b = Packet.a(dataInputStream, 16);
            this.c = dataInputStream.readInt();
        }
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        Packet.a(this.a, dataOutputStream);
        dataOutputStream.writeByte(this.d);
        if (this.d != 1) {
            Packet.a(this.b, dataOutputStream);
            dataOutputStream.writeInt(this.c);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.a.length() + 2 + this.b.length() + 4 + 1;
    }
}
