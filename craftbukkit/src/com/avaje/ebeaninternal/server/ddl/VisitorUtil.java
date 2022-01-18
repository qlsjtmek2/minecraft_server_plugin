// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiEbeanServer;

public class VisitorUtil
{
    public static void visit(final SpiEbeanServer server, final BeanVisitor visitor) {
        visit(server.getBeanDescriptors(), visitor);
    }
    
    public static void visit(final List<BeanDescriptor<?>> descriptors, final BeanVisitor visitor) {
        visitor.visitBegin();
        for (final BeanDescriptor<?> desc : descriptors) {
            if (desc.getBaseTable() != null) {
                visitBean(desc, visitor);
            }
        }
        visitor.visitEnd();
    }
    
    public static void visitBean(final BeanDescriptor<?> desc, final BeanVisitor visitor) {
        if (visitor.visitBean(desc)) {
            final BeanProperty[] propertiesId = desc.propertiesId();
            for (int i = 0; i < propertiesId.length; ++i) {
                visit(visitor, propertiesId[i]);
            }
            final BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
            if (unidirectional != null) {
                visit(visitor, unidirectional);
            }
            final BeanProperty[] propertiesNonTransient = desc.propertiesNonTransient();
            for (int j = 0; j < propertiesNonTransient.length; ++j) {
                final BeanProperty p = propertiesNonTransient[j];
                if (!p.isFormula() && !p.isSecondaryTable()) {
                    visit(visitor, p);
                }
            }
            visitor.visitBeanEnd(desc);
        }
    }
    
    private static void visit(final BeanVisitor visitor, final BeanProperty p) {
        final PropertyVisitor pv = visitor.visitProperty(p);
        if (pv != null) {
            visit(p, pv);
        }
    }
    
    public static void visit(final BeanProperty[] p, final PropertyVisitor pv) {
        for (int i = 0; i < p.length; ++i) {
            visit(p[i], pv);
        }
    }
    
    public static void visit(final BeanProperty p, final PropertyVisitor pv) {
        if (p instanceof BeanPropertyAssocMany) {
            pv.visitMany((BeanPropertyAssocMany<?>)p);
        }
        else if (p instanceof BeanPropertyAssocOne) {
            final BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>)p;
            if (assocOne.isEmbedded()) {
                pv.visitEmbedded(assocOne);
                final BeanProperty[] embProps = assocOne.getProperties();
                for (int i = 0; i < embProps.length; ++i) {
                    pv.visitEmbeddedScalar(embProps[i], assocOne);
                }
            }
            else if (assocOne.isOneToOneExported()) {
                pv.visitOneExported(assocOne);
            }
            else {
                pv.visitOneImported(assocOne);
            }
        }
        else if (p instanceof BeanPropertyCompound) {
            final BeanPropertyCompound compound = (BeanPropertyCompound)p;
            pv.visitCompound(compound);
            final BeanProperty[] properties = compound.getScalarProperties();
            for (int i = 0; i < properties.length; ++i) {
                pv.visitCompoundScalar(compound, properties[i]);
            }
        }
        else {
            pv.visitScalar(p);
        }
    }
}
