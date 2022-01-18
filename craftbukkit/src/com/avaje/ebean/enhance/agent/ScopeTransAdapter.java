// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.util.ArrayList;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Type;
import com.avaje.ebean.enhance.asm.commons.MethodAdviceAdapter;

public class ScopeTransAdapter extends MethodAdviceAdapter implements EnhanceConstants
{
    private static final Type txScopeType;
    private static final Type scopeTransType;
    private static final Type helpScopeTrans;
    private final AnnotationInfo annotationInfo;
    private final ClassAdapterTransactional owner;
    private boolean transactional;
    private int posTxScope;
    private int posScopeTrans;
    
    public ScopeTransAdapter(final ClassAdapterTransactional owner, final MethodVisitor mv, final int access, final String name, final String desc) {
        super(mv, access, name, desc);
        this.owner = owner;
        AnnotationInfo parentInfo = owner.classAnnotationInfo;
        final AnnotationInfo interfaceInfo = owner.getInterfaceTransactionalInfo(name, desc);
        if (parentInfo == null) {
            parentInfo = interfaceInfo;
        }
        else {
            parentInfo.setParent(interfaceInfo);
        }
        this.annotationInfo = new AnnotationInfo(parentInfo);
        this.transactional = (parentInfo != null);
    }
    
    public boolean isTransactional() {
        return this.transactional;
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
            this.transactional = true;
        }
        final AnnotationVisitor av = super.visitAnnotation(desc, visible);
        return new AnnotationInfoVisitor(null, this.annotationInfo, av);
    }
    
    private void setTxType(final Object txType) {
        this.mv.visitVarInsn(25, this.posTxScope);
        this.mv.visitLdcInsn(txType.toString());
        this.mv.visitMethodInsn(184, "com/avaje/ebean/TxType", "valueOf", "(Ljava/lang/String;)Lcom/avaje/ebean/TxType;");
        this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setType", "(Lcom/avaje/ebean/TxType;)Lcom/avaje/ebean/TxScope;");
        this.mv.visitInsn(87);
    }
    
    private void setTxIsolation(final Object txIsolation) {
        this.mv.visitVarInsn(25, this.posTxScope);
        this.mv.visitLdcInsn(txIsolation.toString());
        this.mv.visitMethodInsn(184, "com/avaje/ebean/TxIsolation", "valueOf", "(Ljava/lang/String;)Lcom/avaje/ebean/TxIsolation;");
        this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setIsolation", "(Lcom/avaje/ebean/TxIsolation;)Lcom/avaje/ebean/TxScope;");
        this.mv.visitInsn(87);
    }
    
    private void setServerName(final Object serverName) {
        this.mv.visitVarInsn(25, this.posTxScope);
        this.mv.visitLdcInsn(serverName.toString());
        this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setServerName", "(Ljava/lang/String;)Lcom/avaje/ebean/TxScope;");
        this.mv.visitInsn(87);
    }
    
    private void setReadOnly(final Object readOnlyObj) {
        final boolean readOnly = (boolean)readOnlyObj;
        this.mv.visitVarInsn(25, this.posTxScope);
        if (readOnly) {
            this.mv.visitInsn(4);
        }
        else {
            this.mv.visitInsn(3);
        }
        this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setReadOnly", "(Z)Lcom/avaje/ebean/TxScope;");
    }
    
    private void setNoRollbackFor(final Object noRollbackFor) {
        final ArrayList<?> list = (ArrayList<?>)noRollbackFor;
        for (int i = 0; i < list.size(); ++i) {
            final Type throwType = (Type)list.get(i);
            this.mv.visitVarInsn(25, this.posTxScope);
            this.mv.visitLdcInsn(throwType);
            this.mv.visitMethodInsn(182, ScopeTransAdapter.txScopeType.getInternalName(), "setNoRollbackFor", "(Ljava/lang/Class;)Lcom/avaje/ebean/TxScope;");
            this.mv.visitInsn(87);
        }
    }
    
    private void setRollbackFor(final Object rollbackFor) {
        final ArrayList<?> list = (ArrayList<?>)rollbackFor;
        for (int i = 0; i < list.size(); ++i) {
            final Type throwType = (Type)list.get(i);
            this.mv.visitVarInsn(25, this.posTxScope);
            this.mv.visitLdcInsn(throwType);
            this.mv.visitMethodInsn(182, ScopeTransAdapter.txScopeType.getInternalName(), "setRollbackFor", "(Ljava/lang/Class;)Lcom/avaje/ebean/TxScope;");
            this.mv.visitInsn(87);
        }
    }
    
    protected void onMethodEnter() {
        if (!this.transactional) {
            return;
        }
        this.owner.transactionalMethod(this.methodName, this.methodDesc, this.annotationInfo);
        this.posTxScope = this.newLocal(ScopeTransAdapter.txScopeType);
        this.posScopeTrans = this.newLocal(ScopeTransAdapter.scopeTransType);
        this.mv.visitTypeInsn(187, ScopeTransAdapter.txScopeType.getInternalName());
        this.mv.visitInsn(89);
        this.mv.visitMethodInsn(183, ScopeTransAdapter.txScopeType.getInternalName(), "<init>", "()V");
        this.mv.visitVarInsn(58, this.posTxScope);
        final Object txType = this.annotationInfo.getValue("type");
        if (txType != null) {
            this.setTxType(txType);
        }
        final Object txIsolation = this.annotationInfo.getValue("isolation");
        if (txIsolation != null) {
            this.setTxIsolation(txIsolation);
        }
        final Object readOnly = this.annotationInfo.getValue("readOnly");
        if (readOnly != null) {
            this.setReadOnly(readOnly);
        }
        final Object noRollbackFor = this.annotationInfo.getValue("noRollbackFor");
        if (noRollbackFor != null) {
            this.setNoRollbackFor(noRollbackFor);
        }
        final Object rollbackFor = this.annotationInfo.getValue("rollbackFor");
        if (rollbackFor != null) {
            this.setRollbackFor(rollbackFor);
        }
        final Object serverName = this.annotationInfo.getValue("serverName");
        if (serverName != null && !serverName.equals("")) {
            this.setServerName(serverName);
        }
        this.mv.visitVarInsn(25, this.posTxScope);
        this.mv.visitMethodInsn(184, ScopeTransAdapter.helpScopeTrans.getInternalName(), "createScopeTrans", "(" + ScopeTransAdapter.txScopeType.getDescriptor() + ")" + ScopeTransAdapter.scopeTransType.getDescriptor());
        this.mv.visitVarInsn(58, this.posScopeTrans);
    }
    
    protected void onMethodExit(final int opcode) {
        if (!this.transactional) {
            return;
        }
        if (opcode == 177) {
            this.visitInsn(1);
        }
        else if (opcode == 176 || opcode == 191) {
            this.dup();
        }
        else {
            if (opcode == 173 || opcode == 175) {
                this.dup2();
            }
            else {
                this.dup();
            }
            this.box(Type.getReturnType(this.methodDesc));
        }
        this.visitIntInsn(17, opcode);
        this.loadLocal(this.posScopeTrans);
        this.visitMethodInsn(184, ScopeTransAdapter.helpScopeTrans.getInternalName(), "onExitScopeTrans", "(Ljava/lang/Object;I" + ScopeTransAdapter.scopeTransType.getDescriptor() + ")V");
    }
    
    static {
        txScopeType = Type.getType("Lcom/avaje/ebean/TxScope;");
        scopeTransType = Type.getType("Lcom/avaje/ebeaninternal/api/ScopeTrans;");
        helpScopeTrans = Type.getType("Lcom/avaje/ebeaninternal/api/HelpScopeTrans;");
    }
}
