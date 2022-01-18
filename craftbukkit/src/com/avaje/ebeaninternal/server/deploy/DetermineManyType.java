// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import scala.collection.mutable.Buffer;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class DetermineManyType
{
    private final boolean withScalaSupport;
    private final ManyType scalaBufMany;
    private final ManyType scalaSetMany;
    private final ManyType scalaMapMany;
    
    public DetermineManyType(final boolean withScalaSupport) {
        this.withScalaSupport = withScalaSupport;
        if (withScalaSupport) {
            final CollectionTypeConverter bufConverter = new ScalaBufferConverter();
            final CollectionTypeConverter setConverter = new ScalaSetConverter();
            final CollectionTypeConverter mapConverter = new ScalaMapConverter();
            this.scalaBufMany = new ManyType(ManyType.Underlying.LIST, bufConverter);
            this.scalaSetMany = new ManyType(ManyType.Underlying.SET, setConverter);
            this.scalaMapMany = new ManyType(ManyType.Underlying.MAP, mapConverter);
        }
        else {
            this.scalaBufMany = null;
            this.scalaSetMany = null;
            this.scalaMapMany = null;
        }
    }
    
    public ManyType getManyType(final Class<?> type) {
        if (type.equals(List.class)) {
            return ManyType.JAVA_LIST;
        }
        if (type.equals(Set.class)) {
            return ManyType.JAVA_SET;
        }
        if (type.equals(Map.class)) {
            return ManyType.JAVA_MAP;
        }
        if (this.withScalaSupport) {
            if (type.equals(Buffer.class)) {
                return this.scalaBufMany;
            }
            if (type.equals(scala.collection.mutable.Set.class)) {
                return this.scalaSetMany;
            }
            if (type.equals(scala.collection.mutable.Map.class)) {
                return this.scalaMapMany;
            }
        }
        return null;
    }
}
