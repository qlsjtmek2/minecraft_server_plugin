// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet202Abilities extends Packet
{
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private float e;
    private float f;
    
    public Packet202Abilities() {
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = false;
    }
    
    public Packet202Abilities(final PlayerAbilities playerAbilities) {
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = false;
        this.a(playerAbilities.isInvulnerable);
        this.b(playerAbilities.isFlying);
        this.c(playerAbilities.canFly);
        this.d(playerAbilities.canInstantlyBuild);
        this.a(playerAbilities.a());
        this.b(playerAbilities.b());
    }
    
    public void a(final DataInputStream dataInputStream) {
        final byte byte1 = dataInputStream.readByte();
        this.a((byte1 & 0x1) > 0);
        this.b((byte1 & 0x2) > 0);
        this.c((byte1 & 0x4) > 0);
        this.d((byte1 & 0x8) > 0);
        this.a(dataInputStream.readByte() / 255.0f);
        this.b(dataInputStream.readByte() / 255.0f);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        int n = 0;
        if (this.d()) {
            n = (byte)(n | 0x1);
        }
        if (this.f()) {
            n = (byte)(n | 0x2);
        }
        if (this.g()) {
            n = (byte)(n | 0x4);
        }
        if (this.h()) {
            n = (byte)(n | 0x8);
        }
        dataOutputStream.writeByte(n);
        dataOutputStream.writeByte((int)(this.e * 255.0f));
        dataOutputStream.writeByte((int)(this.f * 255.0f));
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2;
    }
    
    public boolean d() {
        return this.a;
    }
    
    public void a(final boolean a) {
        this.a = a;
    }
    
    public boolean f() {
        return this.b;
    }
    
    public void b(final boolean b) {
        this.b = b;
    }
    
    public boolean g() {
        return this.c;
    }
    
    public void c(final boolean c) {
        this.c = c;
    }
    
    public boolean h() {
        return this.d;
    }
    
    public void d(final boolean d) {
        this.d = d;
    }
    
    public void a(final float e) {
        this.e = e;
    }
    
    public void b(final float f) {
        this.f = f;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
