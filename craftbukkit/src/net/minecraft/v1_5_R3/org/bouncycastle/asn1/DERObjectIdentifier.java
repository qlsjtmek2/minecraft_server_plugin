// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.asn1;

public class DERObjectIdentifier extends ASN1Primitive
{
    String a;
    private static ASN1ObjectIdentifier[][] b;
    
    public DERObjectIdentifier(final String a) {
        if (!a(a)) {
            throw new IllegalArgumentException("string " + a + " not an OID");
        }
        this.a = a;
    }
    
    public String b() {
        return this.a;
    }
    
    public int hashCode() {
        return this.a.hashCode();
    }
    
    boolean a(final ASN1Primitive asn1Primitive) {
        return asn1Primitive instanceof DERObjectIdentifier && this.a.equals(((DERObjectIdentifier)asn1Primitive).a);
    }
    
    public String toString() {
        return this.b();
    }
    
    private static boolean a(final String s) {
        if (s.length() < 3 || s.charAt(1) != '.') {
            return false;
        }
        final char char1 = s.charAt(0);
        if (char1 < '0' || char1 > '2') {
            return false;
        }
        boolean b = false;
        for (int i = s.length() - 1; i >= 2; --i) {
            final char char2 = s.charAt(i);
            if ('0' <= char2 && char2 <= '9') {
                b = true;
            }
            else {
                if (char2 != '.') {
                    return false;
                }
                if (!b) {
                    return false;
                }
                b = false;
            }
        }
        return b;
    }
    
    static {
        DERObjectIdentifier.b = new ASN1ObjectIdentifier[255][];
    }
}
