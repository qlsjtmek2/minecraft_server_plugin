// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.internal;

import java.nio.charset.UnmappableCharacterException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.CoderResult;
import java.nio.CharBuffer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.io.InputStream;
import java.io.Reader;

public class InputStreamReader extends Reader
{
    private InputStream in;
    private static final int BUFFER_SIZE = 8192;
    private boolean endOfInput;
    String encoding;
    CharsetDecoder decoder;
    ByteBuffer bytes;
    
    public InputStreamReader(final InputStream in) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(8192);
        this.in = in;
        this.encoding = System.getProperty("file.encoding", "ISO8859_1");
        this.decoder = Charset.forName(this.encoding).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final String enc) throws UnsupportedEncodingException {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(8192);
        if (enc == null) {
            throw new NullPointerException();
        }
        this.in = in;
        try {
            this.decoder = Charset.forName(enc).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        catch (IllegalArgumentException e) {
            throw (UnsupportedEncodingException)new UnsupportedEncodingException(enc).initCause(e);
        }
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final CharsetDecoder dec) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(8192);
        dec.averageCharsPerByte();
        this.in = in;
        this.decoder = dec;
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final Charset charset) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(8192);
        this.in = in;
        this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.bytes.limit(0);
    }
    
    public void close() throws IOException {
        synchronized (this.lock) {
            this.decoder = null;
            if (this.in != null) {
                this.in.close();
                this.in = null;
            }
        }
    }
    
    public String getEncoding() {
        if (!this.isOpen()) {
            return null;
        }
        return this.encoding;
    }
    
    public int read() throws IOException {
        synchronized (this.lock) {
            if (!this.isOpen()) {
                throw new IOException("InputStreamReader is closed.");
            }
            final char[] buf = { '\0' };
            return (this.read(buf, 0, 1) != -1) ? buf[0] : -1;
        }
    }
    
    public int read(final char[] buf, final int offset, final int length) throws IOException {
        synchronized (this.lock) {
            if (!this.isOpen()) {
                throw new IOException("InputStreamReader is closed.");
            }
            if (offset < 0 || offset > buf.length - length || length < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (length == 0) {
                return 0;
            }
            final CharBuffer out = CharBuffer.wrap(buf, offset, length);
            CoderResult result = CoderResult.UNDERFLOW;
            boolean needInput = !this.bytes.hasRemaining();
            while (out.hasRemaining()) {
                if (needInput) {
                    try {
                        if (this.in.available() == 0 && out.position() > offset) {
                            break;
                        }
                    }
                    catch (IOException ex) {}
                    final int to_read = this.bytes.capacity() - this.bytes.limit();
                    final int off = this.bytes.arrayOffset() + this.bytes.limit();
                    final int was_red = this.in.read(this.bytes.array(), off, to_read);
                    if (was_red == -1) {
                        this.endOfInput = true;
                        break;
                    }
                    if (was_red == 0) {
                        break;
                    }
                    this.bytes.limit(this.bytes.limit() + was_red);
                    needInput = false;
                }
                result = this.decoder.decode(this.bytes, out, false);
                if (!result.isUnderflow()) {
                    break;
                }
                if (this.bytes.limit() == this.bytes.capacity()) {
                    this.bytes.compact();
                    this.bytes.limit(this.bytes.position());
                    this.bytes.position(0);
                }
                needInput = true;
            }
            if (result == CoderResult.UNDERFLOW && this.endOfInput) {
                result = this.decoder.decode(this.bytes, out, true);
                this.decoder.flush(out);
                this.decoder.reset();
            }
            if (result.isMalformed()) {
                throw new MalformedInputException(result.length());
            }
            if (result.isUnmappable()) {
                throw new UnmappableCharacterException(result.length());
            }
            return (out.position() - offset == 0) ? -1 : (out.position() - offset);
        }
    }
    
    private boolean isOpen() {
        return this.in != null;
    }
    
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            if (this.in == null) {
                throw new IOException("InputStreamReader is closed.");
            }
            try {
                return this.bytes.hasRemaining() || this.in.available() > 0;
            }
            catch (IOException e) {
                return false;
            }
        }
    }
}
