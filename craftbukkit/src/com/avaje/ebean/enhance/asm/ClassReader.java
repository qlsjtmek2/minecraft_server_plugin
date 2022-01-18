// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader
{
    static final boolean SIGNATURES = true;
    static final boolean ANNOTATIONS = true;
    static final boolean FRAMES = true;
    static final boolean WRITER = true;
    static final boolean RESIZE = true;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    public final byte[] b;
    private final int[] items;
    private final String[] strings;
    private final int maxStringLength;
    public final int header;
    
    public ClassReader(final byte[] b) {
        this(b, 0, b.length);
    }
    
    public ClassReader(final byte[] b, final int off, final int len) {
        this.b = b;
        this.items = new int[this.readUnsignedShort(off + 8)];
        final int n = this.items.length;
        this.strings = new String[n];
        int max = 0;
        int index = off + 10;
        for (int i = 1; i < n; ++i) {
            this.items[i] = index + 1;
            int size = 0;
            switch (b[index]) {
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12: {
                    size = 5;
                    break;
                }
                case 5:
                case 6: {
                    size = 9;
                    ++i;
                    break;
                }
                case 1: {
                    size = 3 + this.readUnsignedShort(index + 1);
                    if (size > max) {
                        max = size;
                        break;
                    }
                    break;
                }
                default: {
                    size = 3;
                    break;
                }
            }
            index += size;
        }
        this.maxStringLength = max;
        this.header = index;
    }
    
    public int getAccess() {
        return this.readUnsignedShort(this.header);
    }
    
    public String getClassName() {
        return this.readClass(this.header + 2, new char[this.maxStringLength]);
    }
    
    public String getSuperName() {
        final int n = this.items[this.readUnsignedShort(this.header + 4)];
        return (n == 0) ? null : this.readUTF8(n, new char[this.maxStringLength]);
    }
    
    public String[] getInterfaces() {
        int index = this.header + 6;
        final int n = this.readUnsignedShort(index);
        final String[] interfaces = new String[n];
        if (n > 0) {
            final char[] buf = new char[this.maxStringLength];
            for (int i = 0; i < n; ++i) {
                index += 2;
                interfaces[i] = this.readClass(index, buf);
            }
        }
        return interfaces;
    }
    
    void copyPool(final ClassWriter classWriter) {
        final char[] buf = new char[this.maxStringLength];
        final int ll = this.items.length;
        final Item[] items2 = new Item[ll];
        for (int i = 1; i < ll; ++i) {
            int index = this.items[i];
            final int tag = this.b[index - 1];
            final Item item = new Item(i);
            switch (tag) {
                case 9:
                case 10:
                case 11: {
                    final int nameType = this.items[this.readUnsignedShort(index + 2)];
                    item.set(tag, this.readClass(index, buf), this.readUTF8(nameType, buf), this.readUTF8(nameType + 2, buf));
                    break;
                }
                case 3: {
                    item.set(this.readInt(index));
                    break;
                }
                case 4: {
                    item.set(Float.intBitsToFloat(this.readInt(index)));
                    break;
                }
                case 12: {
                    item.set(tag, this.readUTF8(index, buf), this.readUTF8(index + 2, buf), null);
                    break;
                }
                case 5: {
                    item.set(this.readLong(index));
                    ++i;
                    break;
                }
                case 6: {
                    item.set(Double.longBitsToDouble(this.readLong(index)));
                    ++i;
                    break;
                }
                case 1: {
                    String s = this.strings[i];
                    if (s == null) {
                        index = this.items[i];
                        final String[] strings = this.strings;
                        final int n = i;
                        final String utf = this.readUTF(index + 2, this.readUnsignedShort(index), buf);
                        strings[n] = utf;
                        s = utf;
                    }
                    item.set(tag, s, null, null);
                    break;
                }
                default: {
                    item.set(tag, this.readUTF8(index, buf), null, null);
                    break;
                }
            }
            final int index2 = item.hashCode % items2.length;
            item.next = items2[index2];
            items2[index2] = item;
        }
        final int off = this.items[1] - 1;
        classWriter.pool.putByteArray(this.b, off, this.header - off);
        classWriter.items = items2;
        classWriter.threshold = (int)(0.75 * ll);
        classWriter.index = ll;
    }
    
    public ClassReader(final InputStream is) throws IOException {
        this(readClass(is));
    }
    
    public ClassReader(final String name) throws IOException {
        this(ClassLoader.getSystemResourceAsStream(name.replace('.', '/') + ".class"));
    }
    
    private static byte[] readClass(final InputStream is) throws IOException {
        if (is == null) {
            throw new IOException("Class not found");
        }
        byte[] b = new byte[is.available()];
        int len = 0;
        while (true) {
            final int n = is.read(b, len, b.length - len);
            if (n == -1) {
                break;
            }
            len += n;
            if (len != b.length) {
                continue;
            }
            final byte[] c = new byte[b.length + 1000];
            System.arraycopy(b, 0, c, 0, len);
            b = c;
        }
        if (len < b.length) {
            final byte[] c = new byte[len];
            System.arraycopy(b, 0, c, 0, len);
            b = c;
        }
        return b;
    }
    
    public void accept(final ClassVisitor classVisitor, final int flags) {
        this.accept(classVisitor, new Attribute[0], flags);
    }
    
    public void accept(final ClassVisitor classVisitor, final Attribute[] attrs, final int flags) {
        final byte[] b = this.b;
        final char[] c = new char[this.maxStringLength];
        int anns = 0;
        int ianns = 0;
        Attribute cattrs = null;
        int u = this.header;
        int access = this.readUnsignedShort(u);
        String name = this.readClass(u + 2, c);
        int v = this.items[this.readUnsignedShort(u + 4)];
        final String superClassName = (v == 0) ? null : this.readUTF8(v, c);
        final String[] implementedItfs = new String[this.readUnsignedShort(u + 6)];
        int w = 0;
        u += 8;
        for (int i = 0; i < implementedItfs.length; ++i) {
            implementedItfs[i] = this.readClass(u, c);
            u += 2;
        }
        final boolean skipCode = (flags & 0x1) != 0x0;
        final boolean skipDebug = (flags & 0x2) != 0x0;
        final boolean unzip = (flags & 0x8) != 0x0;
        v = u;
        int i = this.readUnsignedShort(v);
        v += 2;
        while (i > 0) {
            int j = this.readUnsignedShort(v + 6);
            v += 8;
            while (j > 0) {
                v += 6 + this.readInt(v + 2);
                --j;
            }
            --i;
        }
        i = this.readUnsignedShort(v);
        v += 2;
        while (i > 0) {
            int j = this.readUnsignedShort(v + 6);
            v += 8;
            while (j > 0) {
                v += 6 + this.readInt(v + 2);
                --j;
            }
            --i;
        }
        String signature = null;
        String sourceFile = null;
        String sourceDebug = null;
        String enclosingOwner = null;
        String enclosingName = null;
        String enclosingDesc = null;
        i = this.readUnsignedShort(v);
        v += 2;
        while (i > 0) {
            final String attrName = this.readUTF8(v, c);
            if ("SourceFile".equals(attrName)) {
                sourceFile = this.readUTF8(v + 6, c);
            }
            else if ("InnerClasses".equals(attrName)) {
                w = v + 6;
            }
            else if ("EnclosingMethod".equals(attrName)) {
                enclosingOwner = this.readClass(v + 6, c);
                final int item = this.readUnsignedShort(v + 8);
                if (item != 0) {
                    enclosingName = this.readUTF8(this.items[item], c);
                    enclosingDesc = this.readUTF8(this.items[item] + 2, c);
                }
            }
            else if ("Signature".equals(attrName)) {
                signature = this.readUTF8(v + 6, c);
            }
            else if ("RuntimeVisibleAnnotations".equals(attrName)) {
                anns = v + 6;
            }
            else if ("Deprecated".equals(attrName)) {
                access |= 0x20000;
            }
            else if ("Synthetic".equals(attrName)) {
                access |= 0x1000;
            }
            else if ("SourceDebugExtension".equals(attrName)) {
                final int len = this.readInt(v + 2);
                sourceDebug = this.readUTF(v + 6, len, new char[len]);
            }
            else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
                ianns = v + 6;
            }
            else {
                final Attribute attr = this.readAttribute(attrs, attrName, v + 6, this.readInt(v + 2), c, -1, null);
                if (attr != null) {
                    attr.next = cattrs;
                    cattrs = attr;
                }
            }
            v += 6 + this.readInt(v + 2);
            --i;
        }
        classVisitor.visit(this.readInt(4), access, name, signature, superClassName, implementedItfs);
        if (!skipDebug && (sourceFile != null || sourceDebug != null)) {
            classVisitor.visitSource(sourceFile, sourceDebug);
        }
        if (enclosingOwner != null) {
            classVisitor.visitOuterClass(enclosingOwner, enclosingName, enclosingDesc);
        }
        for (i = 1; i >= 0; --i) {
            v = ((i == 0) ? ianns : anns);
            if (v != 0) {
                int j = this.readUnsignedShort(v);
                v += 2;
                while (j > 0) {
                    v = this.readAnnotationValues(v + 2, c, true, classVisitor.visitAnnotation(this.readUTF8(v, c), i != 0));
                    --j;
                }
            }
        }
        while (cattrs != null) {
            final Attribute attr = cattrs.next;
            cattrs.next = null;
            classVisitor.visitAttribute(cattrs);
            cattrs = attr;
        }
        if (w != 0) {
            i = this.readUnsignedShort(w);
            w += 2;
            while (i > 0) {
                classVisitor.visitInnerClass((this.readUnsignedShort(w) == 0) ? null : this.readClass(w, c), (this.readUnsignedShort(w + 2) == 0) ? null : this.readClass(w + 2, c), (this.readUnsignedShort(w + 4) == 0) ? null : this.readUTF8(w + 4, c), this.readUnsignedShort(w + 6));
                w += 8;
                --i;
            }
        }
        i = this.readUnsignedShort(u);
        u += 2;
        while (i > 0) {
            access = this.readUnsignedShort(u);
            name = this.readUTF8(u + 2, c);
            final String desc = this.readUTF8(u + 4, c);
            int fieldValueItem = 0;
            signature = null;
            anns = 0;
            ianns = 0;
            cattrs = null;
            int j = this.readUnsignedShort(u + 6);
            u += 8;
            while (j > 0) {
                final String attrName = this.readUTF8(u, c);
                if ("ConstantValue".equals(attrName)) {
                    fieldValueItem = this.readUnsignedShort(u + 6);
                }
                else if ("Signature".equals(attrName)) {
                    signature = this.readUTF8(u + 6, c);
                }
                else if ("Deprecated".equals(attrName)) {
                    access |= 0x20000;
                }
                else if ("Synthetic".equals(attrName)) {
                    access |= 0x1000;
                }
                else if ("RuntimeVisibleAnnotations".equals(attrName)) {
                    anns = u + 6;
                }
                else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
                    ianns = u + 6;
                }
                else {
                    final Attribute attr = this.readAttribute(attrs, attrName, u + 6, this.readInt(u + 2), c, -1, null);
                    if (attr != null) {
                        attr.next = cattrs;
                        cattrs = attr;
                    }
                }
                u += 6 + this.readInt(u + 2);
                --j;
            }
            final FieldVisitor fv = classVisitor.visitField(access, name, desc, signature, (fieldValueItem == 0) ? null : this.readConst(fieldValueItem, c));
            if (fv != null) {
                for (j = 1; j >= 0; --j) {
                    v = ((j == 0) ? ianns : anns);
                    if (v != 0) {
                        int k = this.readUnsignedShort(v);
                        v += 2;
                        while (k > 0) {
                            v = this.readAnnotationValues(v + 2, c, true, fv.visitAnnotation(this.readUTF8(v, c), j != 0));
                            --k;
                        }
                    }
                }
                while (cattrs != null) {
                    final Attribute attr = cattrs.next;
                    cattrs.next = null;
                    fv.visitAttribute(cattrs);
                    cattrs = attr;
                }
                fv.visitEnd();
            }
            --i;
        }
        i = this.readUnsignedShort(u);
        u += 2;
        while (i > 0) {
            final int u2 = u + 6;
            access = this.readUnsignedShort(u);
            name = this.readUTF8(u + 2, c);
            final String desc = this.readUTF8(u + 4, c);
            signature = null;
            anns = 0;
            ianns = 0;
            int dann = 0;
            int mpanns = 0;
            int impanns = 0;
            cattrs = null;
            v = 0;
            w = 0;
            int j = this.readUnsignedShort(u + 6);
            u += 8;
            while (j > 0) {
                final String attrName = this.readUTF8(u, c);
                final int attrSize = this.readInt(u + 2);
                u += 6;
                if ("Code".equals(attrName)) {
                    if (!skipCode) {
                        v = u;
                    }
                }
                else if ("Exceptions".equals(attrName)) {
                    w = u;
                }
                else if ("Signature".equals(attrName)) {
                    signature = this.readUTF8(u, c);
                }
                else if ("Deprecated".equals(attrName)) {
                    access |= 0x20000;
                }
                else if ("RuntimeVisibleAnnotations".equals(attrName)) {
                    anns = u;
                }
                else if ("AnnotationDefault".equals(attrName)) {
                    dann = u;
                }
                else if ("Synthetic".equals(attrName)) {
                    access |= 0x1000;
                }
                else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
                    ianns = u;
                }
                else if ("RuntimeVisibleParameterAnnotations".equals(attrName)) {
                    mpanns = u;
                }
                else if ("RuntimeInvisibleParameterAnnotations".equals(attrName)) {
                    impanns = u;
                }
                else {
                    final Attribute attr = this.readAttribute(attrs, attrName, u, attrSize, c, -1, null);
                    if (attr != null) {
                        attr.next = cattrs;
                        cattrs = attr;
                    }
                }
                u += attrSize;
                --j;
            }
            String[] exceptions;
            if (w == 0) {
                exceptions = null;
            }
            else {
                exceptions = new String[this.readUnsignedShort(w)];
                w += 2;
                for (j = 0; j < exceptions.length; ++j) {
                    exceptions[j] = this.readClass(w, c);
                    w += 2;
                }
            }
            final MethodVisitor mv = classVisitor.visitMethod(access, name, desc, signature, exceptions);
            Label_5488: {
                if (mv != null) {
                    if (mv instanceof MethodWriter) {
                        final MethodWriter mw = (MethodWriter)mv;
                        if (mw.cw.cr == this && signature == mw.signature) {
                            boolean sameExceptions = false;
                            if (exceptions == null) {
                                sameExceptions = (mw.exceptionCount == 0);
                            }
                            else if (exceptions.length == mw.exceptionCount) {
                                sameExceptions = true;
                                for (j = exceptions.length - 1; j >= 0; --j) {
                                    w -= 2;
                                    if (mw.exceptions[j] != this.readUnsignedShort(w)) {
                                        sameExceptions = false;
                                        break;
                                    }
                                }
                            }
                            if (sameExceptions) {
                                mw.classReaderOffset = u2;
                                mw.classReaderLength = u - u2;
                                break Label_5488;
                            }
                        }
                    }
                    if (dann != 0) {
                        final AnnotationVisitor dv = mv.visitAnnotationDefault();
                        this.readAnnotationValue(dann, c, null, dv);
                        if (dv != null) {
                            dv.visitEnd();
                        }
                    }
                    for (j = 1; j >= 0; --j) {
                        w = ((j == 0) ? ianns : anns);
                        if (w != 0) {
                            int k = this.readUnsignedShort(w);
                            w += 2;
                            while (k > 0) {
                                w = this.readAnnotationValues(w + 2, c, true, mv.visitAnnotation(this.readUTF8(w, c), j != 0));
                                --k;
                            }
                        }
                    }
                    if (mpanns != 0) {
                        this.readParameterAnnotations(mpanns, desc, c, true, mv);
                    }
                    if (impanns != 0) {
                        this.readParameterAnnotations(impanns, desc, c, false, mv);
                    }
                    while (cattrs != null) {
                        final Attribute attr = cattrs.next;
                        cattrs.next = null;
                        mv.visitAttribute(cattrs);
                        cattrs = attr;
                    }
                }
                if (mv != null && v != 0) {
                    final int maxStack = this.readUnsignedShort(v);
                    final int maxLocals = this.readUnsignedShort(v + 2);
                    final int codeLength = this.readInt(v + 4);
                    v += 8;
                    final int codeStart = v;
                    final int codeEnd = v + codeLength;
                    mv.visitCode();
                    final Label[] labels = new Label[codeLength + 2];
                    this.readLabel(codeLength + 1, labels);
                    while (v < codeEnd) {
                        w = v - codeStart;
                        int opcode = b[v] & 0xFF;
                        switch (ClassWriter.TYPE[opcode]) {
                            case 0:
                            case 4: {
                                ++v;
                                continue;
                            }
                            case 8: {
                                this.readLabel(w + this.readShort(v + 1), labels);
                                v += 3;
                                continue;
                            }
                            case 9: {
                                this.readLabel(w + this.readInt(v + 1), labels);
                                v += 5;
                                continue;
                            }
                            case 16: {
                                opcode = (b[v + 1] & 0xFF);
                                if (opcode == 132) {
                                    v += 6;
                                    continue;
                                }
                                v += 4;
                                continue;
                            }
                            case 13: {
                                v = v + 4 - (w & 0x3);
                                this.readLabel(w + this.readInt(v), labels);
                                j = this.readInt(v + 8) - this.readInt(v + 4) + 1;
                                v += 12;
                                while (j > 0) {
                                    this.readLabel(w + this.readInt(v), labels);
                                    v += 4;
                                    --j;
                                }
                                continue;
                            }
                            case 14: {
                                v = v + 4 - (w & 0x3);
                                this.readLabel(w + this.readInt(v), labels);
                                j = this.readInt(v + 4);
                                v += 8;
                                while (j > 0) {
                                    this.readLabel(w + this.readInt(v + 4), labels);
                                    v += 8;
                                    --j;
                                }
                                continue;
                            }
                            case 1:
                            case 3:
                            case 10: {
                                v += 2;
                                continue;
                            }
                            case 2:
                            case 5:
                            case 6:
                            case 11:
                            case 12: {
                                v += 3;
                                continue;
                            }
                            case 7: {
                                v += 5;
                                continue;
                            }
                            default: {
                                v += 4;
                                continue;
                            }
                        }
                    }
                    j = this.readUnsignedShort(v);
                    v += 2;
                    while (j > 0) {
                        final Label start = this.readLabel(this.readUnsignedShort(v), labels);
                        final Label end = this.readLabel(this.readUnsignedShort(v + 2), labels);
                        final Label handler = this.readLabel(this.readUnsignedShort(v + 4), labels);
                        final int type = this.readUnsignedShort(v + 6);
                        if (type == 0) {
                            mv.visitTryCatchBlock(start, end, handler, null);
                        }
                        else {
                            mv.visitTryCatchBlock(start, end, handler, this.readUTF8(this.items[type], c));
                        }
                        v += 8;
                        --j;
                    }
                    int varTable = 0;
                    int varTypeTable = 0;
                    int stackMap = 0;
                    int frameCount = 0;
                    int frameMode = 0;
                    int frameOffset = 0;
                    int frameLocalCount = 0;
                    int frameLocalDiff = 0;
                    int frameStackCount = 0;
                    Object[] frameLocal = null;
                    Object[] frameStack = null;
                    boolean zip = true;
                    cattrs = null;
                    j = this.readUnsignedShort(v);
                    v += 2;
                    while (j > 0) {
                        final String attrName = this.readUTF8(v, c);
                        if ("LocalVariableTable".equals(attrName)) {
                            if (!skipDebug) {
                                varTable = v + 6;
                                int k = this.readUnsignedShort(v + 6);
                                w = v + 8;
                                while (k > 0) {
                                    int label = this.readUnsignedShort(w);
                                    if (labels[label] == null) {
                                        final Label label2 = this.readLabel(label, labels);
                                        label2.status |= 0x1;
                                    }
                                    label += this.readUnsignedShort(w + 2);
                                    if (labels[label] == null) {
                                        final Label label3 = this.readLabel(label, labels);
                                        label3.status |= 0x1;
                                    }
                                    w += 10;
                                    --k;
                                }
                            }
                        }
                        else if ("LocalVariableTypeTable".equals(attrName)) {
                            varTypeTable = v + 6;
                        }
                        else if ("LineNumberTable".equals(attrName)) {
                            if (!skipDebug) {
                                int k = this.readUnsignedShort(v + 6);
                                w = v + 8;
                                while (k > 0) {
                                    final int label = this.readUnsignedShort(w);
                                    if (labels[label] == null) {
                                        final Label label4 = this.readLabel(label, labels);
                                        label4.status |= 0x1;
                                    }
                                    labels[label].line = this.readUnsignedShort(w + 2);
                                    w += 4;
                                    --k;
                                }
                            }
                        }
                        else if ("StackMapTable".equals(attrName)) {
                            if ((flags & 0x4) == 0x0) {
                                stackMap = v + 8;
                                frameCount = this.readUnsignedShort(v + 6);
                            }
                        }
                        else if ("StackMap".equals(attrName)) {
                            if ((flags & 0x4) == 0x0) {
                                stackMap = v + 8;
                                frameCount = this.readUnsignedShort(v + 6);
                                zip = false;
                            }
                        }
                        else {
                            for (int k = 0; k < attrs.length; ++k) {
                                if (attrs[k].type.equals(attrName)) {
                                    final Attribute attr = attrs[k].read(this, v + 6, this.readInt(v + 2), c, codeStart - 8, labels);
                                    if (attr != null) {
                                        attr.next = cattrs;
                                        cattrs = attr;
                                    }
                                }
                            }
                        }
                        v += 6 + this.readInt(v + 2);
                        --j;
                    }
                    if (stackMap != 0) {
                        frameLocal = new Object[maxLocals];
                        frameStack = new Object[maxStack];
                        Label_3716: {
                            if (unzip) {
                                int local = 0;
                                if ((access & 0x8) == 0x0) {
                                    if ("<init>".equals(name)) {
                                        frameLocal[local++] = Opcodes.UNINITIALIZED_THIS;
                                    }
                                    else {
                                        frameLocal[local++] = this.readClass(this.header + 2, c);
                                    }
                                }
                                j = 1;
                                while (true) {
                                    final int k = j;
                                    switch (desc.charAt(j++)) {
                                        case 'B':
                                        case 'C':
                                        case 'I':
                                        case 'S':
                                        case 'Z': {
                                            frameLocal[local++] = Opcodes.INTEGER;
                                            continue;
                                        }
                                        case 'F': {
                                            frameLocal[local++] = Opcodes.FLOAT;
                                            continue;
                                        }
                                        case 'J': {
                                            frameLocal[local++] = Opcodes.LONG;
                                            continue;
                                        }
                                        case 'D': {
                                            frameLocal[local++] = Opcodes.DOUBLE;
                                            continue;
                                        }
                                        case '[': {
                                            while (desc.charAt(j) == '[') {
                                                ++j;
                                            }
                                            if (desc.charAt(j) == 'L') {
                                                ++j;
                                                while (desc.charAt(j) != ';') {
                                                    ++j;
                                                }
                                            }
                                            frameLocal[local++] = desc.substring(k, ++j);
                                            continue;
                                        }
                                        case 'L': {
                                            while (desc.charAt(j) != ';') {
                                                ++j;
                                            }
                                            frameLocal[local++] = desc.substring(k + 1, j++);
                                            continue;
                                        }
                                        default: {
                                            frameLocalCount = local;
                                            break Label_3716;
                                        }
                                    }
                                }
                            }
                        }
                        frameOffset = -1;
                    }
                    v = codeStart;
                    while (v < codeEnd) {
                        w = v - codeStart;
                        final Label l = labels[w];
                        if (l != null) {
                            mv.visitLabel(l);
                            if (!skipDebug && l.line > 0) {
                                mv.visitLineNumber(l.line, l);
                            }
                        }
                        while (frameLocal != null && (frameOffset == w || frameOffset == -1)) {
                            if (!zip || unzip) {
                                mv.visitFrame(-1, frameLocalCount, frameLocal, frameStackCount, frameStack);
                            }
                            else if (frameOffset != -1) {
                                mv.visitFrame(frameMode, frameLocalDiff, frameLocal, frameStackCount, frameStack);
                            }
                            if (frameCount > 0) {
                                int tag;
                                if (zip) {
                                    tag = (b[stackMap++] & 0xFF);
                                }
                                else {
                                    tag = 255;
                                    frameOffset = -1;
                                }
                                frameLocalDiff = 0;
                                int delta;
                                if (tag < 64) {
                                    delta = tag;
                                    frameMode = 3;
                                    frameStackCount = 0;
                                }
                                else if (tag < 128) {
                                    delta = tag - 64;
                                    stackMap = this.readFrameType(frameStack, 0, stackMap, c, labels);
                                    frameMode = 4;
                                    frameStackCount = 1;
                                }
                                else {
                                    delta = this.readUnsignedShort(stackMap);
                                    stackMap += 2;
                                    if (tag == 247) {
                                        stackMap = this.readFrameType(frameStack, 0, stackMap, c, labels);
                                        frameMode = 4;
                                        frameStackCount = 1;
                                    }
                                    else if (tag >= 248 && tag < 251) {
                                        frameMode = 2;
                                        frameLocalDiff = 251 - tag;
                                        frameLocalCount -= frameLocalDiff;
                                        frameStackCount = 0;
                                    }
                                    else if (tag == 251) {
                                        frameMode = 3;
                                        frameStackCount = 0;
                                    }
                                    else if (tag < 255) {
                                        j = (unzip ? frameLocalCount : 0);
                                        for (int k = tag - 251; k > 0; --k) {
                                            stackMap = this.readFrameType(frameLocal, j++, stackMap, c, labels);
                                        }
                                        frameMode = 1;
                                        frameLocalDiff = tag - 251;
                                        frameLocalCount += frameLocalDiff;
                                        frameStackCount = 0;
                                    }
                                    else {
                                        frameMode = 0;
                                        int n;
                                        frameLocalDiff = (n = (frameLocalCount = this.readUnsignedShort(stackMap)));
                                        stackMap += 2;
                                        j = 0;
                                        while (n > 0) {
                                            stackMap = this.readFrameType(frameLocal, j++, stackMap, c, labels);
                                            --n;
                                        }
                                        frameStackCount = (n = this.readUnsignedShort(stackMap));
                                        stackMap += 2;
                                        j = 0;
                                        while (n > 0) {
                                            stackMap = this.readFrameType(frameStack, j++, stackMap, c, labels);
                                            --n;
                                        }
                                    }
                                }
                                frameOffset += delta + 1;
                                this.readLabel(frameOffset, labels);
                                --frameCount;
                            }
                            else {
                                frameLocal = null;
                            }
                        }
                        int opcode2 = b[v] & 0xFF;
                        switch (ClassWriter.TYPE[opcode2]) {
                            case 0: {
                                mv.visitInsn(opcode2);
                                ++v;
                                continue;
                            }
                            case 4: {
                                if (opcode2 > 54) {
                                    opcode2 -= 59;
                                    mv.visitVarInsn(54 + (opcode2 >> 2), opcode2 & 0x3);
                                }
                                else {
                                    opcode2 -= 26;
                                    mv.visitVarInsn(21 + (opcode2 >> 2), opcode2 & 0x3);
                                }
                                ++v;
                                continue;
                            }
                            case 8: {
                                mv.visitJumpInsn(opcode2, labels[w + this.readShort(v + 1)]);
                                v += 3;
                                continue;
                            }
                            case 9: {
                                mv.visitJumpInsn(opcode2 - 33, labels[w + this.readInt(v + 1)]);
                                v += 5;
                                continue;
                            }
                            case 16: {
                                opcode2 = (b[v + 1] & 0xFF);
                                if (opcode2 == 132) {
                                    mv.visitIincInsn(this.readUnsignedShort(v + 2), this.readShort(v + 4));
                                    v += 6;
                                    continue;
                                }
                                mv.visitVarInsn(opcode2, this.readUnsignedShort(v + 2));
                                v += 4;
                                continue;
                            }
                            case 13: {
                                v = v + 4 - (w & 0x3);
                                final int label = w + this.readInt(v);
                                final int min = this.readInt(v + 4);
                                final int max = this.readInt(v + 8);
                                v += 12;
                                Label[] table;
                                for (table = new Label[max - min + 1], j = 0; j < table.length; ++j) {
                                    table[j] = labels[w + this.readInt(v)];
                                    v += 4;
                                }
                                mv.visitTableSwitchInsn(min, max, labels[label], table);
                                continue;
                            }
                            case 14: {
                                v = v + 4 - (w & 0x3);
                                final int label = w + this.readInt(v);
                                j = this.readInt(v + 4);
                                v += 8;
                                final int[] keys = new int[j];
                                final Label[] values = new Label[j];
                                for (j = 0; j < keys.length; ++j) {
                                    keys[j] = this.readInt(v);
                                    values[j] = labels[w + this.readInt(v + 4)];
                                    v += 8;
                                }
                                mv.visitLookupSwitchInsn(labels[label], keys, values);
                                continue;
                            }
                            case 3: {
                                mv.visitVarInsn(opcode2, b[v + 1] & 0xFF);
                                v += 2;
                                continue;
                            }
                            case 1: {
                                mv.visitIntInsn(opcode2, b[v + 1]);
                                v += 2;
                                continue;
                            }
                            case 2: {
                                mv.visitIntInsn(opcode2, this.readShort(v + 1));
                                v += 3;
                                continue;
                            }
                            case 10: {
                                mv.visitLdcInsn(this.readConst(b[v + 1] & 0xFF, c));
                                v += 2;
                                continue;
                            }
                            case 11: {
                                mv.visitLdcInsn(this.readConst(this.readUnsignedShort(v + 1), c));
                                v += 3;
                                continue;
                            }
                            case 6:
                            case 7: {
                                int cpIndex = this.items[this.readUnsignedShort(v + 1)];
                                final String iowner = this.readClass(cpIndex, c);
                                cpIndex = this.items[this.readUnsignedShort(cpIndex + 2)];
                                final String iname = this.readUTF8(cpIndex, c);
                                final String idesc = this.readUTF8(cpIndex + 2, c);
                                if (opcode2 < 182) {
                                    mv.visitFieldInsn(opcode2, iowner, iname, idesc);
                                }
                                else {
                                    mv.visitMethodInsn(opcode2, iowner, iname, idesc);
                                }
                                if (opcode2 == 185) {
                                    v += 5;
                                    continue;
                                }
                                v += 3;
                                continue;
                            }
                            case 5: {
                                mv.visitTypeInsn(opcode2, this.readClass(v + 1, c));
                                v += 3;
                                continue;
                            }
                            case 12: {
                                mv.visitIincInsn(b[v + 1] & 0xFF, b[v + 2]);
                                v += 3;
                                continue;
                            }
                            default: {
                                mv.visitMultiANewArrayInsn(this.readClass(v + 1, c), b[v + 3] & 0xFF);
                                v += 4;
                                continue;
                            }
                        }
                    }
                    final Label l = labels[codeEnd - codeStart];
                    if (l != null) {
                        mv.visitLabel(l);
                    }
                    if (!skipDebug && varTable != 0) {
                        int[] typeTable = null;
                        if (varTypeTable != 0) {
                            int k;
                            for (k = this.readUnsignedShort(varTypeTable) * 3, w = varTypeTable + 2, typeTable = new int[k]; k > 0; typeTable[--k] = w + 6, typeTable[--k] = this.readUnsignedShort(w + 8), typeTable[--k] = this.readUnsignedShort(w), w += 10) {}
                        }
                        int k = this.readUnsignedShort(varTable);
                        w = varTable + 2;
                        while (k > 0) {
                            final int start2 = this.readUnsignedShort(w);
                            final int length = this.readUnsignedShort(w + 2);
                            final int index = this.readUnsignedShort(w + 8);
                            String vsignature = null;
                            if (typeTable != null) {
                                for (int a = 0; a < typeTable.length; a += 3) {
                                    if (typeTable[a] == start2 && typeTable[a + 1] == index) {
                                        vsignature = this.readUTF8(typeTable[a + 2], c);
                                        break;
                                    }
                                }
                            }
                            mv.visitLocalVariable(this.readUTF8(w + 4, c), this.readUTF8(w + 6, c), vsignature, labels[start2], labels[start2 + length], index);
                            w += 10;
                            --k;
                        }
                    }
                    while (cattrs != null) {
                        final Attribute attr = cattrs.next;
                        cattrs.next = null;
                        mv.visitAttribute(cattrs);
                        cattrs = attr;
                    }
                    mv.visitMaxs(maxStack, maxLocals);
                }
                if (mv != null) {
                    mv.visitEnd();
                }
            }
            --i;
        }
        classVisitor.visitEnd();
    }
    
    private void readParameterAnnotations(int v, final String desc, final char[] buf, final boolean visible, final MethodVisitor mv) {
        final int n = this.b[v++] & 0xFF;
        int synthetics;
        int i;
        for (synthetics = Type.getArgumentTypes(desc).length - n, i = 0; i < synthetics; ++i) {
            final AnnotationVisitor av = mv.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
            if (av != null) {
                av.visitEnd();
            }
        }
        while (i < n + synthetics) {
            int j = this.readUnsignedShort(v);
            v += 2;
            while (j > 0) {
                final AnnotationVisitor av = mv.visitParameterAnnotation(i, this.readUTF8(v, buf), visible);
                v = this.readAnnotationValues(v + 2, buf, true, av);
                --j;
            }
            ++i;
        }
    }
    
    private int readAnnotationValues(int v, final char[] buf, final boolean named, final AnnotationVisitor av) {
        int i = this.readUnsignedShort(v);
        v += 2;
        if (named) {
            while (i > 0) {
                v = this.readAnnotationValue(v + 2, buf, this.readUTF8(v, buf), av);
                --i;
            }
        }
        else {
            while (i > 0) {
                v = this.readAnnotationValue(v, buf, null, av);
                --i;
            }
        }
        if (av != null) {
            av.visitEnd();
        }
        return v;
    }
    
    private int readAnnotationValue(int v, final char[] buf, final String name, final AnnotationVisitor av) {
        if (av != null) {
            Label_1247: {
                switch (this.b[v++] & 0xFF) {
                    case 68:
                    case 70:
                    case 73:
                    case 74: {
                        av.visit(name, this.readConst(this.readUnsignedShort(v), buf));
                        v += 2;
                        break;
                    }
                    case 66: {
                        av.visit(name, (byte)this.readInt(this.items[this.readUnsignedShort(v)]));
                        v += 2;
                        break;
                    }
                    case 90: {
                        av.visit(name, (this.readInt(this.items[this.readUnsignedShort(v)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
                        v += 2;
                        break;
                    }
                    case 83: {
                        av.visit(name, (short)this.readInt(this.items[this.readUnsignedShort(v)]));
                        v += 2;
                        break;
                    }
                    case 67: {
                        av.visit(name, (char)this.readInt(this.items[this.readUnsignedShort(v)]));
                        v += 2;
                        break;
                    }
                    case 115: {
                        av.visit(name, this.readUTF8(v, buf));
                        v += 2;
                        break;
                    }
                    case 101: {
                        av.visitEnum(name, this.readUTF8(v, buf), this.readUTF8(v + 2, buf));
                        v += 4;
                        break;
                    }
                    case 99: {
                        av.visit(name, Type.getType(this.readUTF8(v, buf)));
                        v += 2;
                        break;
                    }
                    case 64: {
                        v = this.readAnnotationValues(v + 2, buf, true, av.visitAnnotation(name, this.readUTF8(v, buf)));
                        break;
                    }
                    case 91: {
                        final int size = this.readUnsignedShort(v);
                        v += 2;
                        if (size == 0) {
                            return this.readAnnotationValues(v - 2, buf, false, av.visitArray(name));
                        }
                        switch (this.b[v++] & 0xFF) {
                            case 66: {
                                final byte[] bv = new byte[size];
                                for (int i = 0; i < size; ++i) {
                                    bv[i] = (byte)this.readInt(this.items[this.readUnsignedShort(v)]);
                                    v += 3;
                                }
                                av.visit(name, bv);
                                --v;
                                break Label_1247;
                            }
                            case 90: {
                                final boolean[] zv = new boolean[size];
                                for (int i = 0; i < size; ++i) {
                                    zv[i] = (this.readInt(this.items[this.readUnsignedShort(v)]) != 0);
                                    v += 3;
                                }
                                av.visit(name, zv);
                                --v;
                                break Label_1247;
                            }
                            case 83: {
                                final short[] sv = new short[size];
                                for (int i = 0; i < size; ++i) {
                                    sv[i] = (short)this.readInt(this.items[this.readUnsignedShort(v)]);
                                    v += 3;
                                }
                                av.visit(name, sv);
                                --v;
                                break Label_1247;
                            }
                            case 67: {
                                final char[] cv = new char[size];
                                for (int i = 0; i < size; ++i) {
                                    cv[i] = (char)this.readInt(this.items[this.readUnsignedShort(v)]);
                                    v += 3;
                                }
                                av.visit(name, cv);
                                --v;
                                break Label_1247;
                            }
                            case 73: {
                                final int[] iv = new int[size];
                                for (int i = 0; i < size; ++i) {
                                    iv[i] = this.readInt(this.items[this.readUnsignedShort(v)]);
                                    v += 3;
                                }
                                av.visit(name, iv);
                                --v;
                                break Label_1247;
                            }
                            case 74: {
                                final long[] lv = new long[size];
                                for (int i = 0; i < size; ++i) {
                                    lv[i] = this.readLong(this.items[this.readUnsignedShort(v)]);
                                    v += 3;
                                }
                                av.visit(name, lv);
                                --v;
                                break Label_1247;
                            }
                            case 70: {
                                final float[] fv = new float[size];
                                for (int i = 0; i < size; ++i) {
                                    fv[i] = Float.intBitsToFloat(this.readInt(this.items[this.readUnsignedShort(v)]));
                                    v += 3;
                                }
                                av.visit(name, fv);
                                --v;
                                break Label_1247;
                            }
                            case 68: {
                                final double[] dv = new double[size];
                                for (int i = 0; i < size; ++i) {
                                    dv[i] = Double.longBitsToDouble(this.readLong(this.items[this.readUnsignedShort(v)]));
                                    v += 3;
                                }
                                av.visit(name, dv);
                                --v;
                                break Label_1247;
                            }
                            default: {
                                v = this.readAnnotationValues(v - 3, buf, false, av.visitArray(name));
                                break Label_1247;
                            }
                        }
                        break;
                    }
                }
            }
            return v;
        }
        switch (this.b[v] & 0xFF) {
            case 101: {
                return v + 5;
            }
            case 64: {
                return this.readAnnotationValues(v + 3, buf, true, null);
            }
            case 91: {
                return this.readAnnotationValues(v + 1, buf, false, null);
            }
            default: {
                return v + 3;
            }
        }
    }
    
    private int readFrameType(final Object[] frame, final int index, int v, final char[] buf, final Label[] labels) {
        final int type = this.b[v++] & 0xFF;
        switch (type) {
            case 0: {
                frame[index] = Opcodes.TOP;
                break;
            }
            case 1: {
                frame[index] = Opcodes.INTEGER;
                break;
            }
            case 2: {
                frame[index] = Opcodes.FLOAT;
                break;
            }
            case 3: {
                frame[index] = Opcodes.DOUBLE;
                break;
            }
            case 4: {
                frame[index] = Opcodes.LONG;
                break;
            }
            case 5: {
                frame[index] = Opcodes.NULL;
                break;
            }
            case 6: {
                frame[index] = Opcodes.UNINITIALIZED_THIS;
                break;
            }
            case 7: {
                frame[index] = this.readClass(v, buf);
                v += 2;
                break;
            }
            default: {
                frame[index] = this.readLabel(this.readUnsignedShort(v), labels);
                v += 2;
                break;
            }
        }
        return v;
    }
    
    protected Label readLabel(final int offset, final Label[] labels) {
        if (labels[offset] == null) {
            labels[offset] = new Label();
        }
        return labels[offset];
    }
    
    private Attribute readAttribute(final Attribute[] attrs, final String type, final int off, final int len, final char[] buf, final int codeOff, final Label[] labels) {
        for (int i = 0; i < attrs.length; ++i) {
            if (attrs[i].type.equals(type)) {
                return attrs[i].read(this, off, len, buf, codeOff, labels);
            }
        }
        return new Attribute(type).read(this, off, len, null, -1, null);
    }
    
    public int getItem(final int item) {
        return this.items[item];
    }
    
    public int readByte(final int index) {
        return this.b[index] & 0xFF;
    }
    
    public int readUnsignedShort(final int index) {
        final byte[] b = this.b;
        return (b[index] & 0xFF) << 8 | (b[index + 1] & 0xFF);
    }
    
    public short readShort(final int index) {
        final byte[] b = this.b;
        return (short)((b[index] & 0xFF) << 8 | (b[index + 1] & 0xFF));
    }
    
    public int readInt(final int index) {
        final byte[] b = this.b;
        return (b[index] & 0xFF) << 24 | (b[index + 1] & 0xFF) << 16 | (b[index + 2] & 0xFF) << 8 | (b[index + 3] & 0xFF);
    }
    
    public long readLong(final int index) {
        final long l1 = this.readInt(index);
        final long l2 = this.readInt(index + 4) & 0xFFFFFFFFL;
        return l1 << 32 | l2;
    }
    
    public String readUTF8(int index, final char[] buf) {
        final int item = this.readUnsignedShort(index);
        final String s = this.strings[item];
        if (s != null) {
            return s;
        }
        index = this.items[item];
        return this.strings[item] = this.readUTF(index + 2, this.readUnsignedShort(index), buf);
    }
    
    private String readUTF(int index, final int utfLen, final char[] buf) {
        final int endIndex = index + utfLen;
        final byte[] b = this.b;
        int strLen = 0;
        while (index < endIndex) {
            final int c = b[index++] & 0xFF;
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    buf[strLen++] = (char)c;
                    continue;
                }
                case 12:
                case 13: {
                    final int d = b[index++];
                    buf[strLen++] = (char)((c & 0x1F) << 6 | (d & 0x3F));
                    continue;
                }
                default: {
                    final int d = b[index++];
                    final int e = b[index++];
                    buf[strLen++] = (char)((c & 0xF) << 12 | (d & 0x3F) << 6 | (e & 0x3F));
                    continue;
                }
            }
        }
        return new String(buf, 0, strLen);
    }
    
    public String readClass(final int index, final char[] buf) {
        return this.readUTF8(this.items[this.readUnsignedShort(index)], buf);
    }
    
    public Object readConst(final int item, final char[] buf) {
        final int index = this.items[item];
        switch (this.b[index - 1]) {
            case 3: {
                return this.readInt(index);
            }
            case 4: {
                return new Float(Float.intBitsToFloat(this.readInt(index)));
            }
            case 5: {
                return this.readLong(index);
            }
            case 6: {
                return new Double(Double.longBitsToDouble(this.readLong(index)));
            }
            case 7: {
                return Type.getObjectType(this.readUTF8(index, buf));
            }
            default: {
                return this.readUTF8(index, buf);
            }
        }
    }
}
