// 
// Decompiled by Procyon v0.5.30
// 

package org.json.simple.parser;

import java.util.List;
import java.util.Map;

public interface ContainerFactory
{
    Map createObjectContainer();
    
    List creatArrayContainer();
}
