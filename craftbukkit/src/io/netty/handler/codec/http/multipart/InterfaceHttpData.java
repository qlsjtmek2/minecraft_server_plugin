// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.multipart;

public interface InterfaceHttpData extends Comparable<InterfaceHttpData>
{
    String getName();
    
    HttpDataType getHttpDataType();
    
    public enum HttpDataType
    {
        Attribute, 
        FileUpload, 
        InternalAttribute;
    }
}
