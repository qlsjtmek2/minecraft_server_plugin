// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collection;

public class Packet209SetScoreboardTeam extends Packet
{
    public String a;
    public String b;
    public String c;
    public String d;
    public Collection e;
    public int f;
    public int g;
    
    public Packet209SetScoreboardTeam() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = new ArrayList();
        this.f = 0;
    }
    
    public Packet209SetScoreboardTeam(final ScoreboardTeam scoreboardTeam, final int f) {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = new ArrayList();
        this.f = 0;
        this.a = scoreboardTeam.getName();
        this.f = f;
        if (f == 0 || f == 2) {
            this.b = scoreboardTeam.getDisplayName();
            this.c = scoreboardTeam.getPrefix();
            this.d = scoreboardTeam.getSuffix();
            this.g = scoreboardTeam.packOptionData();
        }
        if (f == 0) {
            this.e.addAll(scoreboardTeam.getPlayerNameSet());
        }
    }
    
    public Packet209SetScoreboardTeam(final ScoreboardTeam scoreboardTeam, final Collection collection, final int f) {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = new ArrayList();
        this.f = 0;
        if (f != 3 && f != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Players cannot be null/empty");
        }
        this.f = f;
        this.a = scoreboardTeam.getName();
        this.e.addAll(collection);
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 16);
        this.f = datainputstream.readByte();
        if (this.f == 0 || this.f == 2) {
            this.b = Packet.a(datainputstream, 32);
            this.c = Packet.a(datainputstream, 16);
            this.d = Packet.a(datainputstream, 16);
            this.g = datainputstream.readByte();
        }
        if (this.f == 0 || this.f == 3 || this.f == 4) {
            for (short short1 = datainputstream.readShort(), n = 0; n < short1; ++n) {
                this.e.add(Packet.a(datainputstream, 16));
            }
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
        dataoutputstream.writeByte(this.f);
        if (this.f == 0 || this.f == 2) {
            Packet.a(this.b, dataoutputstream);
            Packet.a(this.c, dataoutputstream);
            Packet.a(this.d, dataoutputstream);
            dataoutputstream.writeByte(this.g);
        }
        if (this.f == 0 || this.f == 3 || this.f == 4) {
            dataoutputstream.writeShort(this.e.size());
            final Iterator<String> iterator = this.e.iterator();
            while (iterator.hasNext()) {
                Packet.a(iterator.next(), dataoutputstream);
            }
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 3 + this.a.length();
    }
}
