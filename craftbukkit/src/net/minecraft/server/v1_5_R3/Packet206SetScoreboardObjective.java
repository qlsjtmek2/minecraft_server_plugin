// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class Packet206SetScoreboardObjective extends Packet
{
    public String a;
    public String b;
    public int c;
    
    public Packet206SetScoreboardObjective() {
    }
    
    public Packet206SetScoreboardObjective(final ScoreboardObjective scoreboardObjective, final int c) {
        this.a = scoreboardObjective.getName();
        this.b = scoreboardObjective.getDisplayName();
        this.c = c;
    }
    
    public void a(final DataInputStream dataInputStream) throws IOException {
        this.a = Packet.a(dataInputStream, 1000);
        this.b = Packet.a(dataInputStream, 1000);
        this.c = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) throws IOException {
        Packet.a(this.a, dataOutputStream);
        Packet.a(this.b, dataOutputStream);
        dataOutputStream.writeByte(this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.a.length() + 2 + this.b.length() + 1;
    }
}
