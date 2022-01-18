// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.asn1;

public class ASN1ObjectIdentifier extends DERObjectIdentifier
{
    public ASN1ObjectIdentifier(final String s) {
        super(s);
    }
    
    public ASN1ObjectIdentifier a(final String s) {
        return new ASN1ObjectIdentifier(this.b() + "." + s);
    }
}
