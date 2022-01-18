// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.io.BufferedInputStream;
import java.net.Socket;

public class RemoteControlSession extends RemoteConnectionThread
{
    private boolean g;
    private Socket h;
    private byte[] i;
    private String j;
    
    RemoteControlSession(final IMinecraftServer minecraftServer, final Socket h) {
        super(minecraftServer);
        this.g = false;
        this.i = new byte[1460];
        this.h = h;
        try {
            this.h.setSoTimeout(0);
        }
        catch (Exception ex) {
            this.running = false;
        }
        this.j = minecraftServer.a("rcon.password", "");
        this.info("Rcon connection from: " + h.getInetAddress());
    }
    
    public void run() {
        try {
            while (this.running) {
                final int read = new BufferedInputStream(this.h.getInputStream()).read(this.i, 0, 1460);
                if (10 > read) {
                    return;
                }
                int n = 0;
                if (StatusChallengeUtils.b(this.i, 0, read) != read - 4) {
                    return;
                }
                n += 4;
                final int b = StatusChallengeUtils.b(this.i, n, read);
                n += 4;
                final int b2 = StatusChallengeUtils.b(this.i, n);
                n += 4;
                switch (b2) {
                    case 3: {
                        final String a = StatusChallengeUtils.a(this.i, n, read);
                        final int n2 = n + a.length();
                        if (0 != a.length() && a.equals(this.j)) {
                            this.g = true;
                            this.a(b, 2, "");
                            continue;
                        }
                        this.g = false;
                        this.f();
                        continue;
                    }
                    case 2: {
                        if (this.g) {
                            final String a2 = StatusChallengeUtils.a(this.i, n, read);
                            try {
                                this.a(b, this.server.h(a2));
                            }
                            catch (Exception ex) {
                                this.a(b, "Error executing: " + a2 + " (" + ex.getMessage() + ")");
                            }
                            continue;
                        }
                        this.f();
                        continue;
                    }
                    default: {
                        this.a(b, String.format("Unknown request %s", Integer.toHexString(b2)));
                        continue;
                    }
                }
            }
        }
        catch (SocketTimeoutException ex3) {}
        catch (IOException ex4) {}
        catch (Exception ex2) {
            System.out.println(ex2);
        }
        finally {
            this.g();
        }
    }
    
    private void a(final int n, final int n2, final String s) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1248);
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(Integer.reverseBytes(s.length() + 10));
        dataOutputStream.writeInt(Integer.reverseBytes(n));
        dataOutputStream.writeInt(Integer.reverseBytes(n2));
        dataOutputStream.writeBytes(s);
        dataOutputStream.write(0);
        dataOutputStream.write(0);
        this.h.getOutputStream().write(byteArrayOutputStream.toByteArray());
    }
    
    private void f() {
        this.a(-1, 2, "");
    }
    
    private void a(final int n, String substring) {
        int n2 = substring.length();
        do {
            final int n3 = (4096 <= n2) ? 4096 : n2;
            this.a(n, 0, substring.substring(0, n3));
            substring = substring.substring(n3);
            n2 = substring.length();
        } while (n2);
    }
    
    private void g() {
        if (null == this.h) {
            return;
        }
        try {
            this.h.close();
        }
        catch (IOException ex) {
            this.warning("IO: " + ex.getMessage());
        }
        this.h = null;
    }
}
