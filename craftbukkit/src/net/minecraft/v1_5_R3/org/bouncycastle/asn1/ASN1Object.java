// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.asn1;

public abstract class ASN1Object implements ASN1Encodable
{
    public int hashCode() {
        return this.a().hashCode();
    }
    
    public boolean equals(final Object o) {
        return this == o || (o instanceof ASN1Encodable && this.a().equals(((ASN1Encodable)o).a()));
    }
    
    public abstract ASN1Primitive a();
}
