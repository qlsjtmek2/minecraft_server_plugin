// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import java.util.List;
import com.avaje.ebean.enhance.agent.FieldMeta;
import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebean.enhance.asm.Opcodes;

public class GetterSetterMethods implements Opcodes, EnhanceConstants
{
    public static void add(final ClassVisitor cv, final ClassMeta classMeta) {
        final List<FieldMeta> localFields = classMeta.getLocalFields();
        for (int x = 0; x < localFields.size(); ++x) {
            final FieldMeta fieldMeta = localFields.get(x);
            fieldMeta.addPublicGetSetMethods(cv, classMeta, true);
        }
        final List<FieldMeta> inheritedFields = classMeta.getInheritedFields();
        for (int i = 0; i < inheritedFields.size(); ++i) {
            final FieldMeta fieldMeta2 = inheritedFields.get(i);
            fieldMeta2.addPublicGetSetMethods(cv, classMeta, false);
        }
    }
}
