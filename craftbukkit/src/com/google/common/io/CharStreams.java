// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.Writer;
import java.io.EOFException;
import java.util.Arrays;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.nio.CharBuffer;
import java.io.Closeable;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.InputStream;
import java.io.IOException;
import com.google.common.base.Preconditions;
import java.io.StringReader;
import com.google.common.annotations.Beta;

@Beta
public final class CharStreams
{
    private static final int BUF_SIZE = 2048;
    
    public static InputSupplier<StringReader> newReaderSupplier(final String value) {
        Preconditions.checkNotNull(value);
        return new InputSupplier<StringReader>() {
            public StringReader getInput() {
                return new StringReader(value);
            }
        };
    }
    
    public static InputSupplier<InputStreamReader> newReaderSupplier(final InputSupplier<? extends InputStream> in, final Charset charset) {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(charset);
        return new InputSupplier<InputStreamReader>() {
            public InputStreamReader getInput() throws IOException {
                return new InputStreamReader(in.getInput(), charset);
            }
        };
    }
    
    public static OutputSupplier<OutputStreamWriter> newWriterSupplier(final OutputSupplier<? extends OutputStream> out, final Charset charset) {
        Preconditions.checkNotNull(out);
        Preconditions.checkNotNull(charset);
        return new OutputSupplier<OutputStreamWriter>() {
            public OutputStreamWriter getOutput() throws IOException {
                return new OutputStreamWriter(out.getOutput(), charset);
            }
        };
    }
    
    public static <W extends Appendable> void write(final CharSequence from, final OutputSupplier<W> to) throws IOException {
        Preconditions.checkNotNull(from);
        boolean threw = true;
        final W out = to.getOutput();
        try {
            ((Appendable)out).append(from);
            threw = false;
        }
        finally {
            Closeables.close((Closeable)out, threw);
        }
    }
    
    public static <R extends Readable, W extends java.lang.Appendable> long copy(final InputSupplier<R> from, final OutputSupplier<W> to) throws IOException {
        boolean threw = true;
        final R in = from.getInput();
        try {
            final W out = to.getOutput();
            try {
                final long count = copy((Readable)in, (Appendable)out);
                threw = false;
                return count;
            }
            finally {
                Closeables.close((Closeable)out, threw);
            }
        }
        finally {
            Closeables.close((Closeable)in, threw);
        }
    }
    
    public static <R extends java.lang.Readable> long copy(final InputSupplier<R> from, final Appendable to) throws IOException {
        boolean threw = true;
        final R in = from.getInput();
        try {
            final long count = copy((Readable)in, to);
            threw = false;
            return count;
        }
        finally {
            Closeables.close((Closeable)in, threw);
        }
    }
    
    public static long copy(final Readable from, final Appendable to) throws IOException {
        final CharBuffer buf = CharBuffer.allocate(2048);
        long total = 0L;
        while (true) {
            final int r = from.read(buf);
            if (r == -1) {
                break;
            }
            buf.flip();
            to.append(buf, 0, r);
            total += r;
        }
        return total;
    }
    
    public static String toString(final Readable r) throws IOException {
        return toStringBuilder(r).toString();
    }
    
    public static <R extends java.lang.Readable> String toString(final InputSupplier<R> supplier) throws IOException {
        return toStringBuilder(supplier).toString();
    }
    
    private static StringBuilder toStringBuilder(final Readable r) throws IOException {
        final StringBuilder sb = new StringBuilder();
        copy(r, sb);
        return sb;
    }
    
    private static <R extends java.lang.Readable> StringBuilder toStringBuilder(final InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        final R r = supplier.getInput();
        try {
            final StringBuilder result = toStringBuilder((Readable)r);
            threw = false;
            return result;
        }
        finally {
            Closeables.close((Closeable)r, threw);
        }
    }
    
    public static <R extends java.lang.Readable> String readFirstLine(final InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        final R r = supplier.getInput();
        try {
            final String line = new LineReader((Readable)r).readLine();
            threw = false;
            return line;
        }
        finally {
            Closeables.close((Closeable)r, threw);
        }
    }
    
    public static <R extends java.lang.Readable> List<String> readLines(final InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        final R r = supplier.getInput();
        try {
            final List<String> result = readLines((Readable)r);
            threw = false;
            return result;
        }
        finally {
            Closeables.close((Closeable)r, threw);
        }
    }
    
    public static List<String> readLines(final Readable r) throws IOException {
        final List<String> result = new ArrayList<String>();
        final LineReader lineReader = new LineReader(r);
        String line;
        while ((line = lineReader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
    
    public static <R extends java.lang.Readable, T> T readLines(final InputSupplier<R> supplier, final LineProcessor<T> callback) throws IOException {
        boolean threw = true;
        final R r = supplier.getInput();
        try {
            final LineReader lineReader = new LineReader((Readable)r);
            String line;
            while ((line = lineReader.readLine()) != null && callback.processLine(line)) {}
            threw = false;
        }
        finally {
            Closeables.close((Closeable)r, threw);
        }
        return callback.getResult();
    }
    
    public static InputSupplier<Reader> join(final Iterable<? extends InputSupplier<? extends Reader>> suppliers) {
        return new InputSupplier<Reader>() {
            public Reader getInput() throws IOException {
                return new MultiReader(suppliers.iterator());
            }
        };
    }
    
    public static InputSupplier<Reader> join(final InputSupplier<? extends Reader>... suppliers) {
        return join(Arrays.asList(suppliers));
    }
    
    public static void skipFully(final Reader reader, long n) throws IOException {
        while (n > 0L) {
            final long amt = reader.skip(n);
            if (amt == 0L) {
                if (reader.read() == -1) {
                    throw new EOFException();
                }
                --n;
            }
            else {
                n -= amt;
            }
        }
    }
    
    public static Writer asWriter(final Appendable target) {
        if (target instanceof Writer) {
            return (Writer)target;
        }
        return new AppendableWriter(target);
    }
}
