// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public interface ExampleExpression extends Expression
{
    ExampleExpression includeZeros();
    
    ExampleExpression caseInsensitive();
    
    ExampleExpression useStartsWith();
    
    ExampleExpression useContains();
    
    ExampleExpression useEndsWith();
    
    ExampleExpression useEqualTo();
}
