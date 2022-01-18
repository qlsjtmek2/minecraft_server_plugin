// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.asn1;

public abstract class ASN1Primitive extends ASN1Object
{
    public final boolean equals(final Object o) {
        return this == o || (o instanceof ASN1Encodable && this.a(((ASN1Encodable)o).a()));
    }
    
    public ASN1Primitive a() {
        return this;
    }
    
    public abstract int hashCode();
    
    abstract boolean a(final ASN1Primitive p0);
}
