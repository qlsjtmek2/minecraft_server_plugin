// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Annotation;

public interface ChannelHandler
{
    void beforeAdd(final ChannelHandlerContext p0) throws Exception;
    
    void afterAdd(final ChannelHandlerContext p0) throws Exception;
    
    void beforeRemove(final ChannelHandlerContext p0) throws Exception;
    
    void afterRemove(final ChannelHandlerContext p0) throws Exception;
    
    void exceptionCaught(final ChannelHandlerContext p0, final Throwable p1) throws Exception;
    
    @Inherited
    @Documented
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Sharable {
    }
}
