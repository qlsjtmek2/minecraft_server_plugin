// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.config;

import java.security.Permission;
import java.util.StringTokenizer;
import net.minecraft.v1_5_R3.org.bouncycastle.util.Strings;
import java.security.BasicPermission;

public class ProviderConfigurationPermission extends BasicPermission
{
    private final String a;
    private final int b;
    
    public ProviderConfigurationPermission(final String s, final String a) {
        super(s, a);
        this.a = a;
        this.b = this.a(a);
    }
    
    private int a(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(Strings.a(s), " ,");
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("threadlocalecimplicitlyca")) {
                n |= 0x1;
            }
            else if (nextToken.equals("ecimplicitlyca")) {
                n |= 0x2;
            }
            else if (nextToken.equals("threadlocaldhdefaultparams")) {
                n |= 0x4;
            }
            else if (nextToken.equals("dhdefaultparams")) {
                n |= 0x8;
            }
            else {
                if (!nextToken.equals("all")) {
                    continue;
                }
                n |= 0xF;
            }
        }
        if (n == 0) {
            throw new IllegalArgumentException("unknown permissions passed to mask");
        }
        return n;
    }
    
    public String getActions() {
        return this.a;
    }
    
    public boolean implies(final Permission permission) {
        if (!(permission instanceof ProviderConfigurationPermission)) {
            return false;
        }
        if (!this.getName().equals(permission.getName())) {
            return false;
        }
        final ProviderConfigurationPermission providerConfigurationPermission = (ProviderConfigurationPermission)permission;
        return (this.b & providerConfigurationPermission.b) == providerConfigurationPermission.b;
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ProviderConfigurationPermission) {
            final ProviderConfigurationPermission providerConfigurationPermission = (ProviderConfigurationPermission)o;
            return this.b == providerConfigurationPermission.b && this.getName().equals(providerConfigurationPermission.getName());
        }
        return false;
    }
    
    public int hashCode() {
        return this.getName().hashCode() + this.b;
    }
}
