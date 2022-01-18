// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Dimension;
import java.text.DecimalFormat;
import javax.swing.JComponent;

public class GuiStatsComponent extends JComponent
{
    private static final DecimalFormat a;
    private int[] b;
    private int c;
    private String[] d;
    private final MinecraftServer e;
    
    public GuiStatsComponent(final MinecraftServer e) {
        this.b = new int[256];
        this.c = 0;
        this.d = new String[11];
        this.e = e;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        new Timer(500, new GuiStatsListener(this)).start();
        this.setBackground(Color.BLACK);
    }
    
    private void a() {
        final long n = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        this.d[0] = "Memory use: " + n / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.d[1] = "Threads: " + NetworkManager.a.get() + " + " + NetworkManager.b.get();
        this.d[2] = "Avg tick: " + GuiStatsComponent.a.format(this.a(this.e.i) * 1.0E-6) + " ms";
        this.d[3] = "Avg sent: " + (int)this.a(this.e.e) + ", Avg size: " + (int)this.a(this.e.f);
        this.d[4] = "Avg rec: " + (int)this.a(this.e.g) + ", Avg size: " + (int)this.a(this.e.h);
        if (this.e.worldServer != null) {
            for (int i = 0; i < this.e.worldServer.length; ++i) {
                this.d[5 + i] = "Lvl " + i + " tick: " + GuiStatsComponent.a.format(this.a(this.e.j[i]) * 1.0E-6) + " ms";
                if (this.e.worldServer[i] != null && this.e.worldServer[i].chunkProviderServer != null) {
                    final StringBuilder sb = new StringBuilder();
                    final String[] d = this.d;
                    final int n2 = 5 + i;
                    d[n2] = sb.append(d[n2]).append(", ").append(this.e.worldServer[i].chunkProviderServer.getName()).toString();
                    final StringBuilder sb2 = new StringBuilder();
                    final String[] d2 = this.d;
                    final int n3 = 5 + i;
                    d2[n3] = sb2.append(d2[n3]).append(", Vec3: ").append(this.e.worldServer[i].getVec3DPool().d()).append(" / ").append(this.e.worldServer[i].getVec3DPool().c()).toString();
                }
            }
        }
        this.b[this.c++ & 0xFF] = (int)(this.a(this.e.f) * 100.0 / 12500.0);
        this.repaint();
    }
    
    private double a(final long[] array) {
        long n = 0L;
        for (int i = 0; i < array.length; ++i) {
            n += array[i];
        }
        return n / array.length;
    }
    
    public void paint(final Graphics graphics) {
        graphics.setColor(new Color(16777215));
        graphics.fillRect(0, 0, 456, 246);
        for (int i = 0; i < 256; ++i) {
            final int n = this.b[i + this.c & 0xFF];
            graphics.setColor(new Color(n + 28 << 16));
            graphics.fillRect(i, 100 - n, 1, n);
        }
        graphics.setColor(Color.BLACK);
        for (int j = 0; j < this.d.length; ++j) {
            final String s = this.d[j];
            if (s != null) {
                graphics.drawString(s, 32, 116 + j * 16);
            }
        }
    }
    
    static {
        a = new DecimalFormat("########0.000");
    }
}
