// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ELF
{
    private static final int ELF_MAGIC = 2135247942;
    public static final int ELFCLASSNONE = 0;
    public static final int ELFCLASS32 = 1;
    public static final int ELFCLASS64 = 2;
    public static final int ELFDATANONE = 0;
    public static final int ELFDATA2LSB = 1;
    public static final int ELFDATA2MSB = 2;
    public static final int SHT_SYMTAB = 2;
    public static final int SHT_STRTAB = 3;
    public static final int SHT_NOBITS = 8;
    public static final int SHF_WRITE = 1;
    public static final int SHF_ALLOC = 2;
    public static final int SHF_EXECINSTR = 4;
    public static final int PF_X = 1;
    public static final int PF_W = 2;
    public static final int PF_R = 4;
    public static final int PT_LOAD = 1;
    public static final short ET_EXEC = 2;
    public static final short EM_MIPS = 8;
    private Seekable data;
    public ELFIdent ident;
    public ELFHeader header;
    public PHeader[] pheaders;
    public SHeader[] sheaders;
    private byte[] stringTable;
    private boolean sectionReaderActive;
    private Symtab _symtab;
    
    private void readFully(final byte[] array) throws IOException {
        int i = array.length;
        int n = 0;
        while (i > 0) {
            final int read = this.data.read(array, n, i);
            if (read == -1) {
                throw new IOException("EOF");
            }
            n += read;
            i -= read;
        }
    }
    
    private int readIntBE() throws IOException {
        final byte[] array = new byte[4];
        this.readFully(array);
        return (array[0] & 0xFF) << 24 | (array[1] & 0xFF) << 16 | (array[2] & 0xFF) << 8 | (array[3] & 0xFF) << 0;
    }
    
    private int readInt() throws IOException {
        int intBE = this.readIntBE();
        if (this.ident != null && this.ident.data == 1) {
            intBE = ((intBE << 24 & 0xFF000000) | (intBE << 8 & 0xFF0000) | (intBE >>> 8 & 0xFF00) | (intBE >> 24 & 0xFF));
        }
        return intBE;
    }
    
    private short readShortBE() throws IOException {
        final byte[] array = new byte[2];
        this.readFully(array);
        return (short)((array[0] & 0xFF) << 8 | (array[1] & 0xFF) << 0);
    }
    
    private short readShort() throws IOException {
        short shortBE = this.readShortBE();
        if (this.ident != null && this.ident.data == 1) {
            shortBE = (short)(((shortBE << 8 & 0xFF00) | (shortBE >> 8 & 0xFF)) & 0xFFFF);
        }
        return shortBE;
    }
    
    private byte readByte() throws IOException {
        final byte[] array = { 0 };
        this.readFully(array);
        return array[0];
    }
    
    public ELF(final String s) throws IOException, ELFException {
        this(new Seekable.File(s, false));
    }
    
    public ELF(final Seekable data) throws IOException, ELFException {
        this.data = data;
        this.ident = new ELFIdent();
        this.header = new ELFHeader();
        this.pheaders = new PHeader[this.header.phnum];
        for (short n = 0; n < this.header.phnum; ++n) {
            data.seek(this.header.phoff + n * this.header.phentsize);
            this.pheaders[n] = new PHeader();
        }
        this.sheaders = new SHeader[this.header.shnum];
        for (short n2 = 0; n2 < this.header.shnum; ++n2) {
            data.seek(this.header.shoff + n2 * this.header.shentsize);
            this.sheaders[n2] = new SHeader();
        }
        if (this.header.shstrndx < 0 || this.header.shstrndx >= this.header.shnum) {
            throw new ELFException("Bad shstrndx");
        }
        data.seek(this.sheaders[this.header.shstrndx].offset);
        this.readFully(this.stringTable = new byte[this.sheaders[this.header.shstrndx].size]);
        for (short n3 = 0; n3 < this.header.shnum; ++n3) {
            final SHeader sHeader = this.sheaders[n3];
            sHeader.name = this.getString(sHeader.nameidx);
        }
    }
    
    private String getString(final int n) {
        return this.getString(n, this.stringTable);
    }
    
    private String getString(int n, final byte[] array) {
        final StringBuffer sb = new StringBuffer();
        if (n < 0 || n >= array.length) {
            return "<invalid strtab entry>";
        }
        while (n >= 0 && n < array.length && array[n] != 0) {
            sb.append((char)array[n++]);
        }
        return sb.toString();
    }
    
    public SHeader sectionWithName(final String s) {
        for (int i = 0; i < this.sheaders.length; ++i) {
            if (this.sheaders[i].name.equals(s)) {
                return this.sheaders[i];
            }
        }
        return null;
    }
    
    public Symtab getSymtab() throws IOException {
        if (this._symtab != null) {
            return this._symtab;
        }
        if (this.sectionReaderActive) {
            throw new ELFException("Can't read the symtab while a section reader is active");
        }
        final SHeader sectionWithName = this.sectionWithName(".symtab");
        if (sectionWithName == null || sectionWithName.type != 2) {
            return null;
        }
        final SHeader sectionWithName2 = this.sectionWithName(".strtab");
        if (sectionWithName2 == null || sectionWithName2.type != 3) {
            return null;
        }
        final byte[] array = new byte[sectionWithName2.size];
        final DataInputStream dataInputStream = new DataInputStream(sectionWithName2.getInputStream());
        dataInputStream.readFully(array);
        dataInputStream.close();
        return this._symtab = new Symtab(sectionWithName.offset, sectionWithName.size, array);
    }
    
    private static String toHex(final int n) {
        return "0x" + Long.toString(n & 0xFFFFFFFFL, 16);
    }
    
    public class ELFIdent
    {
        public byte klass;
        public byte data;
        public byte osabi;
        public byte abiversion;
        
        ELFIdent() throws IOException {
            if (ELF.this.readIntBE() != 2135247942) {
                throw new ELFException("Bad Magic");
            }
            this.klass = ELF.this.readByte();
            if (this.klass != 1) {
                throw new ELFException("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF does not suport 64-bit binaries");
            }
            this.data = ELF.this.readByte();
            if (this.data != 1 && this.data != 2) {
                throw new ELFException("Unknown byte order");
            }
            ELF.this.readByte();
            this.osabi = ELF.this.readByte();
            this.abiversion = ELF.this.readByte();
            for (int i = 0; i < 7; ++i) {
                ELF.this.readByte();
            }
        }
    }
    
    public class ELFHeader
    {
        public short type;
        public short machine;
        public int version;
        public int entry;
        public int phoff;
        public int shoff;
        public int flags;
        public short ehsize;
        public short phentsize;
        public short phnum;
        public short shentsize;
        public short shnum;
        public short shstrndx;
        
        ELFHeader() throws IOException {
            this.type = ELF.this.readShort();
            this.machine = ELF.this.readShort();
            this.version = ELF.this.readInt();
            if (this.version != 1) {
                throw new ELFException("version != 1");
            }
            this.entry = ELF.this.readInt();
            this.phoff = ELF.this.readInt();
            this.shoff = ELF.this.readInt();
            this.flags = ELF.this.readInt();
            this.ehsize = ELF.this.readShort();
            this.phentsize = ELF.this.readShort();
            this.phnum = ELF.this.readShort();
            this.shentsize = ELF.this.readShort();
            this.shnum = ELF.this.readShort();
            this.shstrndx = ELF.this.readShort();
        }
    }
    
    public class PHeader
    {
        public int type;
        public int offset;
        public int vaddr;
        public int paddr;
        public int filesz;
        public int memsz;
        public int flags;
        public int align;
        
        PHeader() throws IOException {
            this.type = ELF.this.readInt();
            this.offset = ELF.this.readInt();
            this.vaddr = ELF.this.readInt();
            this.paddr = ELF.this.readInt();
            this.filesz = ELF.this.readInt();
            this.memsz = ELF.this.readInt();
            this.flags = ELF.this.readInt();
            this.align = ELF.this.readInt();
            if (this.filesz > this.memsz) {
                throw new ELFException("ELF inconsistency: filesz > memsz (" + toHex(this.filesz) + " > " + toHex(this.memsz) + ")");
            }
        }
        
        public boolean writable() {
            return (this.flags & 0x2) != 0x0;
        }
        
        public InputStream getInputStream() throws IOException {
            return new BufferedInputStream(new SectionInputStream(this.offset, this.offset + this.filesz));
        }
    }
    
    public class SHeader
    {
        int nameidx;
        public String name;
        public int type;
        public int flags;
        public int addr;
        public int offset;
        public int size;
        public int link;
        public int info;
        public int addralign;
        public int entsize;
        
        SHeader() throws IOException {
            this.nameidx = ELF.this.readInt();
            this.type = ELF.this.readInt();
            this.flags = ELF.this.readInt();
            this.addr = ELF.this.readInt();
            this.offset = ELF.this.readInt();
            this.size = ELF.this.readInt();
            this.link = ELF.this.readInt();
            this.info = ELF.this.readInt();
            this.addralign = ELF.this.readInt();
            this.entsize = ELF.this.readInt();
        }
        
        public InputStream getInputStream() throws IOException {
            return new BufferedInputStream(new SectionInputStream(this.offset, (this.type == 8) ? 0 : (this.offset + this.size)));
        }
        
        public boolean isText() {
            return this.name.equals(".text");
        }
        
        public boolean isData() {
            return this.name.equals(".data") || this.name.equals(".sdata") || this.name.equals(".rodata") || this.name.equals(".ctors") || this.name.equals(".dtors");
        }
        
        public boolean isBSS() {
            return this.name.equals(".bss") || this.name.equals(".sbss");
        }
    }
    
    public class ELFException extends IOException
    {
        ELFException(final String s) {
            super(s);
        }
    }
    
    private class SectionInputStream extends InputStream
    {
        private int pos;
        private int maxpos;
        
        SectionInputStream(final int pos, final int maxpos) throws IOException {
            if (ELF.this.sectionReaderActive) {
                throw new IOException("Section reader already active");
            }
            ELF.this.sectionReaderActive = true;
            this.pos = pos;
            ELF.this.data.seek(this.pos);
            this.maxpos = maxpos;
        }
        
        private int bytesLeft() {
            return this.maxpos - this.pos;
        }
        
        public int read() throws IOException {
            final byte[] array = { 0 };
            return (this.read(array, 0, 1) == -1) ? -1 : (array[0] & 0xFF);
        }
        
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            final int read = ELF.this.data.read(array, n, Math.min(n2, this.bytesLeft()));
            if (read > 0) {
                this.pos += read;
            }
            return read;
        }
        
        public void close() {
            ELF.this.sectionReaderActive = false;
        }
    }
    
    public class Symtab
    {
        public Symbol[] symbols;
        
        Symtab(final int n, final int n2, final byte[] array) throws IOException {
            ELF.this.data.seek(n);
            final int n3 = n2 / 16;
            this.symbols = new Symbol[n3];
            for (int i = 0; i < n3; ++i) {
                this.symbols[i] = new Symbol(array);
            }
        }
        
        public Symbol getSymbol(final String s) {
            Symbol symbol = null;
            for (int i = 0; i < this.symbols.length; ++i) {
                if (this.symbols[i].name.equals(s)) {
                    if (symbol == null) {
                        symbol = this.symbols[i];
                    }
                    else {
                        System.err.println("WARNING: Multiple symbol matches for " + s);
                    }
                }
            }
            return symbol;
        }
        
        public Symbol getGlobalSymbol(final String s) {
            for (int i = 0; i < this.symbols.length; ++i) {
                if (this.symbols[i].binding == 1 && this.symbols[i].name.equals(s)) {
                    return this.symbols[i];
                }
            }
            return null;
        }
    }
    
    public class Symbol
    {
        public String name;
        public int addr;
        public int size;
        public byte info;
        public byte type;
        public byte binding;
        public byte other;
        public short shndx;
        public SHeader sheader;
        public static final int STT_FUNC = 2;
        public static final int STB_GLOBAL = 1;
        
        Symbol(final byte[] array) throws IOException {
            this.name = ELF.this.getString(ELF.this.readInt(), array);
            this.addr = ELF.this.readInt();
            this.size = ELF.this.readInt();
            this.info = ELF.this.readByte();
            this.type = (byte)(this.info & 0xF);
            this.binding = (byte)(this.info >> 4);
            this.other = ELF.this.readByte();
            this.shndx = ELF.this.readShort();
        }
    }
}
