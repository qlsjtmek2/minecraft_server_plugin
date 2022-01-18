// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

public class ClassWriter implements ClassVisitor
{
    public static final int COMPUTE_MAXS = 1;
    public static final int COMPUTE_FRAMES = 2;
    static final int NOARG_INSN = 0;
    static final int SBYTE_INSN = 1;
    static final int SHORT_INSN = 2;
    static final int VAR_INSN = 3;
    static final int IMPLVAR_INSN = 4;
    static final int TYPE_INSN = 5;
    static final int FIELDORMETH_INSN = 6;
    static final int ITFMETH_INSN = 7;
    static final int LABEL_INSN = 8;
    static final int LABELW_INSN = 9;
    static final int LDC_INSN = 10;
    static final int LDCW_INSN = 11;
    static final int IINC_INSN = 12;
    static final int TABL_INSN = 13;
    static final int LOOK_INSN = 14;
    static final int MANA_INSN = 15;
    static final int WIDE_INSN = 16;
    static final byte[] TYPE;
    static final int CLASS = 7;
    static final int FIELD = 9;
    static final int METH = 10;
    static final int IMETH = 11;
    static final int STR = 8;
    static final int INT = 3;
    static final int FLOAT = 4;
    static final int LONG = 5;
    static final int DOUBLE = 6;
    static final int NAME_TYPE = 12;
    static final int UTF8 = 1;
    static final int TYPE_NORMAL = 13;
    static final int TYPE_UNINIT = 14;
    static final int TYPE_MERGED = 15;
    ClassReader cr;
    int version;
    int index;
    final ByteVector pool;
    Item[] items;
    int threshold;
    final Item key;
    final Item key2;
    final Item key3;
    Item[] typeTable;
    private short typeCount;
    private int access;
    private int name;
    String thisName;
    private int signature;
    private int superName;
    private int interfaceCount;
    private int[] interfaces;
    private int sourceFile;
    private ByteVector sourceDebug;
    private int enclosingMethodOwner;
    private int enclosingMethod;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private Attribute attrs;
    private int innerClassesCount;
    private ByteVector innerClasses;
    FieldWriter firstField;
    FieldWriter lastField;
    MethodWriter firstMethod;
    MethodWriter lastMethod;
    private final boolean computeMaxs;
    private final boolean computeFrames;
    boolean invalidFrames;
    
    public ClassWriter(final int flags) {
        this.index = 1;
        this.pool = new ByteVector();
        this.items = new Item[256];
        this.threshold = (int)(0.75 * this.items.length);
        this.key = new Item();
        this.key2 = new Item();
        this.key3 = new Item();
        this.computeMaxs = ((flags & 0x1) != 0x0);
        this.computeFrames = ((flags & 0x2) != 0x0);
    }
    
    public ClassWriter(final ClassReader classReader, final int flags) {
        this(flags);
        classReader.copyPool(this);
        this.cr = classReader;
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = this.newClass(name);
        this.thisName = name;
        if (signature != null) {
            this.signature = this.newUTF8(signature);
        }
        this.superName = ((superName == null) ? 0 : this.newClass(superName));
        if (interfaces != null && interfaces.length > 0) {
            this.interfaceCount = interfaces.length;
            this.interfaces = new int[this.interfaceCount];
            for (int i = 0; i < this.interfaceCount; ++i) {
                this.interfaces[i] = this.newClass(interfaces[i]);
            }
        }
    }
    
    public void visitSource(final String file, final String debug) {
        if (file != null) {
            this.sourceFile = this.newUTF8(file);
        }
        if (debug != null) {
            this.sourceDebug = new ByteVector().putUTF8(debug);
        }
    }
    
    public void visitOuterClass(final String owner, final String name, final String desc) {
        this.enclosingMethodOwner = this.newClass(owner);
        if (name != null && desc != null) {
            this.enclosingMethod = this.newNameType(name, desc);
        }
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        final ByteVector bv = new ByteVector();
        bv.putShort(this.newUTF8(desc)).putShort(0);
        final AnnotationWriter aw = new AnnotationWriter(this, true, bv, bv, 2);
        if (visible) {
            aw.next = this.anns;
            this.anns = aw;
        }
        else {
            aw.next = this.ianns;
            this.ianns = aw;
        }
        return aw;
    }
    
    public void visitAttribute(final Attribute attr) {
        attr.next = this.attrs;
        this.attrs = attr;
    }
    
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        if (this.innerClasses == null) {
            this.innerClasses = new ByteVector();
        }
        ++this.innerClassesCount;
        this.innerClasses.putShort((name == null) ? 0 : this.newClass(name));
        this.innerClasses.putShort((outerName == null) ? 0 : this.newClass(outerName));
        this.innerClasses.putShort((innerName == null) ? 0 : this.newUTF8(innerName));
        this.innerClasses.putShort(access);
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        return new FieldWriter(this, access, name, desc, signature, value);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        return new MethodWriter(this, access, name, desc, signature, exceptions, this.computeMaxs, this.computeFrames);
    }
    
    public void visitEnd() {
    }
    
    public byte[] toByteArray() {
        int size = 24 + 2 * this.interfaceCount;
        int nbFields = 0;
        for (FieldWriter fb = this.firstField; fb != null; fb = fb.next) {
            ++nbFields;
            size += fb.getSize();
        }
        int nbMethods = 0;
        for (MethodWriter mb = this.firstMethod; mb != null; mb = mb.next) {
            ++nbMethods;
            size += mb.getSize();
        }
        int attributeCount = 0;
        if (this.signature != 0) {
            ++attributeCount;
            size += 8;
            this.newUTF8("Signature");
        }
        if (this.sourceFile != 0) {
            ++attributeCount;
            size += 8;
            this.newUTF8("SourceFile");
        }
        if (this.sourceDebug != null) {
            ++attributeCount;
            size += this.sourceDebug.length + 4;
            this.newUTF8("SourceDebugExtension");
        }
        if (this.enclosingMethodOwner != 0) {
            ++attributeCount;
            size += 10;
            this.newUTF8("EnclosingMethod");
        }
        if ((this.access & 0x20000) != 0x0) {
            ++attributeCount;
            size += 6;
            this.newUTF8("Deprecated");
        }
        if ((this.access & 0x1000) != 0x0 && (this.version & 0xFFFF) < 49) {
            ++attributeCount;
            size += 6;
            this.newUTF8("Synthetic");
        }
        if (this.innerClasses != null) {
            ++attributeCount;
            size += 8 + this.innerClasses.length;
            this.newUTF8("InnerClasses");
        }
        if (this.anns != null) {
            ++attributeCount;
            size += 8 + this.anns.getSize();
            this.newUTF8("RuntimeVisibleAnnotations");
        }
        if (this.ianns != null) {
            ++attributeCount;
            size += 8 + this.ianns.getSize();
            this.newUTF8("RuntimeInvisibleAnnotations");
        }
        if (this.attrs != null) {
            attributeCount += this.attrs.getCount();
            size += this.attrs.getSize(this, null, 0, -1, -1);
        }
        size += this.pool.length;
        final ByteVector out = new ByteVector(size);
        out.putInt(-889275714).putInt(this.version);
        out.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
        out.putShort(this.access).putShort(this.name).putShort(this.superName);
        out.putShort(this.interfaceCount);
        for (int i = 0; i < this.interfaceCount; ++i) {
            out.putShort(this.interfaces[i]);
        }
        out.putShort(nbFields);
        for (FieldWriter fb = this.firstField; fb != null; fb = fb.next) {
            fb.put(out);
        }
        out.putShort(nbMethods);
        for (MethodWriter mb = this.firstMethod; mb != null; mb = mb.next) {
            mb.put(out);
        }
        out.putShort(attributeCount);
        if (this.signature != 0) {
            out.putShort(this.newUTF8("Signature")).putInt(2).putShort(this.signature);
        }
        if (this.sourceFile != 0) {
            out.putShort(this.newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
        }
        if (this.sourceDebug != null) {
            final int len = this.sourceDebug.length - 2;
            out.putShort(this.newUTF8("SourceDebugExtension")).putInt(len);
            out.putByteArray(this.sourceDebug.data, 2, len);
        }
        if (this.enclosingMethodOwner != 0) {
            out.putShort(this.newUTF8("EnclosingMethod")).putInt(4);
            out.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
        }
        if ((this.access & 0x20000) != 0x0) {
            out.putShort(this.newUTF8("Deprecated")).putInt(0);
        }
        if ((this.access & 0x1000) != 0x0 && (this.version & 0xFFFF) < 49) {
            out.putShort(this.newUTF8("Synthetic")).putInt(0);
        }
        if (this.innerClasses != null) {
            out.putShort(this.newUTF8("InnerClasses"));
            out.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
            out.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
        }
        if (this.anns != null) {
            out.putShort(this.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(out);
        }
        if (this.ianns != null) {
            out.putShort(this.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(out);
        }
        if (this.attrs != null) {
            this.attrs.put(this, null, 0, -1, -1, out);
        }
        if (this.invalidFrames) {
            final ClassWriter cw = new ClassWriter(2);
            new ClassReader(out.data).accept(cw, 4);
            return cw.toByteArray();
        }
        return out.data;
    }
    
    Item newConstItem(final Object cst) {
        if (cst instanceof Integer) {
            final int val = (int)cst;
            return this.newInteger(val);
        }
        if (cst instanceof Byte) {
            final int val = (int)cst;
            return this.newInteger(val);
        }
        if (cst instanceof Character) {
            final int val = (char)cst;
            return this.newInteger(val);
        }
        if (cst instanceof Short) {
            final int val = (int)cst;
            return this.newInteger(val);
        }
        if (cst instanceof Boolean) {
            final int val = ((boolean)cst) ? 1 : 0;
            return this.newInteger(val);
        }
        if (cst instanceof Float) {
            final float val2 = (float)cst;
            return this.newFloat(val2);
        }
        if (cst instanceof Long) {
            final long val3 = (long)cst;
            return this.newLong(val3);
        }
        if (cst instanceof Double) {
            final double val4 = (double)cst;
            return this.newDouble(val4);
        }
        if (cst instanceof String) {
            return this.newString((String)cst);
        }
        if (cst instanceof Type) {
            final Type t = (Type)cst;
            return this.newClassItem((t.getSort() == 10) ? t.getInternalName() : t.getDescriptor());
        }
        throw new IllegalArgumentException("value " + cst);
    }
    
    public int newConst(final Object cst) {
        return this.newConstItem(cst).index;
    }
    
    public int newUTF8(final String value) {
        this.key.set(1, value, null, null);
        Item result = this.get(this.key);
        if (result == null) {
            this.pool.putByte(1).putUTF8(value);
            result = new Item(this.index++, this.key);
            this.put(result);
        }
        return result.index;
    }
    
    Item newClassItem(final String value) {
        this.key2.set(7, value, null, null);
        Item result = this.get(this.key2);
        if (result == null) {
            this.pool.put12(7, this.newUTF8(value));
            result = new Item(this.index++, this.key2);
            this.put(result);
        }
        return result;
    }
    
    public int newClass(final String value) {
        return this.newClassItem(value).index;
    }
    
    Item newFieldItem(final String owner, final String name, final String desc) {
        this.key3.set(9, owner, name, desc);
        Item result = this.get(this.key3);
        if (result == null) {
            this.put122(9, this.newClass(owner), this.newNameType(name, desc));
            result = new Item(this.index++, this.key3);
            this.put(result);
        }
        return result;
    }
    
    public int newField(final String owner, final String name, final String desc) {
        return this.newFieldItem(owner, name, desc).index;
    }
    
    Item newMethodItem(final String owner, final String name, final String desc, final boolean itf) {
        final int type = itf ? 11 : 10;
        this.key3.set(type, owner, name, desc);
        Item result = this.get(this.key3);
        if (result == null) {
            this.put122(type, this.newClass(owner), this.newNameType(name, desc));
            result = new Item(this.index++, this.key3);
            this.put(result);
        }
        return result;
    }
    
    public int newMethod(final String owner, final String name, final String desc, final boolean itf) {
        return this.newMethodItem(owner, name, desc, itf).index;
    }
    
    Item newInteger(final int value) {
        this.key.set(value);
        Item result = this.get(this.key);
        if (result == null) {
            this.pool.putByte(3).putInt(value);
            result = new Item(this.index++, this.key);
            this.put(result);
        }
        return result;
    }
    
    Item newFloat(final float value) {
        this.key.set(value);
        Item result = this.get(this.key);
        if (result == null) {
            this.pool.putByte(4).putInt(this.key.intVal);
            result = new Item(this.index++, this.key);
            this.put(result);
        }
        return result;
    }
    
    Item newLong(final long value) {
        this.key.set(value);
        Item result = this.get(this.key);
        if (result == null) {
            this.pool.putByte(5).putLong(value);
            result = new Item(this.index, this.key);
            this.put(result);
            this.index += 2;
        }
        return result;
    }
    
    Item newDouble(final double value) {
        this.key.set(value);
        Item result = this.get(this.key);
        if (result == null) {
            this.pool.putByte(6).putLong(this.key.longVal);
            result = new Item(this.index, this.key);
            this.put(result);
            this.index += 2;
        }
        return result;
    }
    
    private Item newString(final String value) {
        this.key2.set(8, value, null, null);
        Item result = this.get(this.key2);
        if (result == null) {
            this.pool.put12(8, this.newUTF8(value));
            result = new Item(this.index++, this.key2);
            this.put(result);
        }
        return result;
    }
    
    public int newNameType(final String name, final String desc) {
        this.key2.set(12, name, desc, null);
        Item result = this.get(this.key2);
        if (result == null) {
            this.put122(12, this.newUTF8(name), this.newUTF8(desc));
            result = new Item(this.index++, this.key2);
            this.put(result);
        }
        return result.index;
    }
    
    int addType(final String type) {
        this.key.set(13, type, null, null);
        Item result = this.get(this.key);
        if (result == null) {
            result = this.addType(this.key);
        }
        return result.index;
    }
    
    int addUninitializedType(final String type, final int offset) {
        this.key.type = 14;
        this.key.intVal = offset;
        this.key.strVal1 = type;
        this.key.hashCode = (Integer.MAX_VALUE & 14 + type.hashCode() + offset);
        Item result = this.get(this.key);
        if (result == null) {
            result = this.addType(this.key);
        }
        return result.index;
    }
    
    private Item addType(final Item item) {
        ++this.typeCount;
        final Item result = new Item(this.typeCount, this.key);
        this.put(result);
        if (this.typeTable == null) {
            this.typeTable = new Item[16];
        }
        if (this.typeCount == this.typeTable.length) {
            final Item[] newTable = new Item[2 * this.typeTable.length];
            System.arraycopy(this.typeTable, 0, newTable, 0, this.typeTable.length);
            this.typeTable = newTable;
        }
        return this.typeTable[this.typeCount] = result;
    }
    
    int getMergedType(final int type1, final int type2) {
        this.key2.type = 15;
        this.key2.longVal = (type1 | type2 << 32);
        this.key2.hashCode = (Integer.MAX_VALUE & 15 + type1 + type2);
        Item result = this.get(this.key2);
        if (result == null) {
            final String t = this.typeTable[type1].strVal1;
            final String u = this.typeTable[type2].strVal1;
            this.key2.intVal = this.addType(this.getCommonSuperClass(t, u));
            result = new Item(0, this.key2);
            this.put(result);
        }
        return result.intVal;
    }
    
    protected Class<?> classForName(final String name) throws ClassNotFoundException {
        try {
            final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return Class.forName(name, false, contextClassLoader);
            }
        }
        catch (Throwable t) {}
        return Class.forName(name, false, this.getClass().getClassLoader());
    }
    
    protected String getCommonSuperClass(final String type1, final String type2) {
        Class c;
        Class d;
        try {
            c = this.classForName(type1.replace('/', '.'));
            d = this.classForName(type2.replace('/', '.'));
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        if (c.isAssignableFrom(d)) {
            return type1;
        }
        if (d.isAssignableFrom(c)) {
            return type2;
        }
        if (c.isInterface() || d.isInterface()) {
            return "java/lang/Object";
        }
        do {
            c = c.getSuperclass();
        } while (!c.isAssignableFrom(d));
        return c.getName().replace('.', '/');
    }
    
    private Item get(final Item key) {
        Item i;
        for (i = this.items[key.hashCode % this.items.length]; i != null && !key.isEqualTo(i); i = i.next) {}
        return i;
    }
    
    private void put(final Item i) {
        if (this.index > this.threshold) {
            final int ll = this.items.length;
            final int nl = ll * 2 + 1;
            final Item[] newItems = new Item[nl];
            for (int l = ll - 1; l >= 0; --l) {
                Item k;
                for (Item j = this.items[l]; j != null; j = k) {
                    final int index = j.hashCode % newItems.length;
                    k = j.next;
                    j.next = newItems[index];
                    newItems[index] = j;
                }
            }
            this.items = newItems;
            this.threshold = (int)(nl * 0.75);
        }
        final int index2 = i.hashCode % this.items.length;
        i.next = this.items[index2];
        this.items[index2] = i;
    }
    
    private void put122(final int b, final int s1, final int s2) {
        this.pool.put12(b, s1).putShort(s2);
    }
    
    static {
        final byte[] b = new byte[220];
        final String s = "AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHAFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII";
        for (int i = 0; i < b.length; ++i) {
            b[i] = (byte)(s.charAt(i) - 'A');
        }
        TYPE = b;
    }
}
