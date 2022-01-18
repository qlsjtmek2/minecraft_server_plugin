// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;
import java.security.MessageDigest;
import java.util.zip.Checksum;
import java.util.List;
import java.io.Closeable;
import java.io.IOException;
import com.google.common.base.Preconditions;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.File;
import com.google.common.annotations.Beta;

@Beta
public final class Files
{
    private static final int TEMP_DIR_ATTEMPTS = 10000;
    
    public static BufferedReader newReader(final File file, final Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }
    
    public static BufferedWriter newWriter(final File file, final Charset charset) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }
    
    public static InputSupplier<FileInputStream> newInputStreamSupplier(final File file) {
        Preconditions.checkNotNull(file);
        return new InputSupplier<FileInputStream>() {
            public FileInputStream getInput() throws IOException {
                return new FileInputStream(file);
            }
        };
    }
    
    public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(final File file) {
        return newOutputStreamSupplier(file, false);
    }
    
    public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(final File file, final boolean append) {
        Preconditions.checkNotNull(file);
        return new OutputSupplier<FileOutputStream>() {
            public FileOutputStream getOutput() throws IOException {
                return new FileOutputStream(file, append);
            }
        };
    }
    
    public static InputSupplier<InputStreamReader> newReaderSupplier(final File file, final Charset charset) {
        return CharStreams.newReaderSupplier(newInputStreamSupplier(file), charset);
    }
    
    public static OutputSupplier<OutputStreamWriter> newWriterSupplier(final File file, final Charset charset) {
        return newWriterSupplier(file, charset, false);
    }
    
    public static OutputSupplier<OutputStreamWriter> newWriterSupplier(final File file, final Charset charset, final boolean append) {
        return CharStreams.newWriterSupplier(newOutputStreamSupplier(file, append), charset);
    }
    
    public static byte[] toByteArray(final File file) throws IOException {
        Preconditions.checkArgument(file.length() <= 2147483647L);
        if (file.length() == 0L) {
            return ByteStreams.toByteArray(newInputStreamSupplier(file));
        }
        final byte[] b = new byte[(int)file.length()];
        boolean threw = true;
        final InputStream in = new FileInputStream(file);
        try {
            ByteStreams.readFully(in, b);
            threw = false;
        }
        finally {
            Closeables.close(in, threw);
        }
        return b;
    }
    
    public static String toString(final File file, final Charset charset) throws IOException {
        return new String(toByteArray(file), charset.name());
    }
    
    public static void copy(final InputSupplier<? extends InputStream> from, final File to) throws IOException {
        ByteStreams.copy(from, newOutputStreamSupplier(to));
    }
    
    public static void write(final byte[] from, final File to) throws IOException {
        ByteStreams.write(from, newOutputStreamSupplier(to));
    }
    
    public static void copy(final File from, final OutputSupplier<? extends OutputStream> to) throws IOException {
        ByteStreams.copy(newInputStreamSupplier(from), to);
    }
    
    public static void copy(final File from, final OutputStream to) throws IOException {
        ByteStreams.copy(newInputStreamSupplier(from), to);
    }
    
    public static void copy(final File from, final File to) throws IOException {
        copy(newInputStreamSupplier(from), to);
    }
    
    public static <R extends Readable> void copy(final InputSupplier<R> from, final File to, final Charset charset) throws IOException {
        CharStreams.copy(from, newWriterSupplier(to, charset));
    }
    
    public static void write(final CharSequence from, final File to, final Charset charset) throws IOException {
        write(from, to, charset, false);
    }
    
    public static void append(final CharSequence from, final File to, final Charset charset) throws IOException {
        write(from, to, charset, true);
    }
    
    private static void write(final CharSequence from, final File to, final Charset charset, final boolean append) throws IOException {
        CharStreams.write(from, newWriterSupplier(to, charset, append));
    }
    
    public static <W extends Appendable> void copy(final File from, final Charset charset, final OutputSupplier<W> to) throws IOException {
        CharStreams.copy(newReaderSupplier(from, charset), to);
    }
    
    public static void copy(final File from, final Charset charset, final Appendable to) throws IOException {
        CharStreams.copy(newReaderSupplier(from, charset), to);
    }
    
    public static boolean equal(final File file1, final File file2) throws IOException {
        if (file1 == file2 || file1.equals(file2)) {
            return true;
        }
        final long len1 = file1.length();
        final long len2 = file2.length();
        return (len1 == 0L || len2 == 0L || len1 == len2) && ByteStreams.equal(newInputStreamSupplier(file1), newInputStreamSupplier(file2));
    }
    
    public static File createTempDir() {
        final File baseDir = new File(System.getProperty("java.io.tmpdir"));
        final String baseName = System.currentTimeMillis() + "-";
        for (int counter = 0; counter < 10000; ++counter) {
            final File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + baseName + "0 to " + baseName + 9999 + ')');
    }
    
    public static void touch(final File file) throws IOException {
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to update modification time of " + file);
        }
    }
    
    public static void createParentDirs(final File file) throws IOException {
        final File parent = file.getCanonicalFile().getParentFile();
        if (parent == null) {
            return;
        }
        parent.mkdirs();
        if (!parent.isDirectory()) {
            throw new IOException("Unable to create parent directories of " + file);
        }
    }
    
    public static void move(final File from, final File to) throws IOException {
        Preconditions.checkNotNull(to);
        Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", from, to);
        if (!from.renameTo(to)) {
            copy(from, to);
            if (!from.delete()) {
                if (!to.delete()) {
                    throw new IOException("Unable to delete " + to);
                }
                throw new IOException("Unable to delete " + from);
            }
        }
    }
    
    @Deprecated
    public static void deleteDirectoryContents(final File directory) throws IOException {
        Preconditions.checkArgument(directory.isDirectory(), "Not a directory: %s", directory);
        if (!directory.getCanonicalPath().equals(directory.getAbsolutePath())) {
            return;
        }
        final File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Error listing files for " + directory);
        }
        for (final File file : files) {
            deleteRecursively(file);
        }
    }
    
    @Deprecated
    public static void deleteRecursively(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryContents(file);
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }
    
    public static String readFirstLine(final File file, final Charset charset) throws IOException {
        return CharStreams.readFirstLine(newReaderSupplier(file, charset));
    }
    
    public static List<String> readLines(final File file, final Charset charset) throws IOException {
        return CharStreams.readLines(newReaderSupplier(file, charset));
    }
    
    public static <T> T readLines(final File file, final Charset charset, final LineProcessor<T> callback) throws IOException {
        return CharStreams.readLines(newReaderSupplier(file, charset), callback);
    }
    
    public static <T> T readBytes(final File file, final ByteProcessor<T> processor) throws IOException {
        return ByteStreams.readBytes(newInputStreamSupplier(file), processor);
    }
    
    public static long getChecksum(final File file, final Checksum checksum) throws IOException {
        return ByteStreams.getChecksum(newInputStreamSupplier(file), checksum);
    }
    
    public static byte[] getDigest(final File file, final MessageDigest md) throws IOException {
        return ByteStreams.getDigest(newInputStreamSupplier(file), md);
    }
    
    public static MappedByteBuffer map(final File file) throws IOException {
        return map(file, FileChannel.MapMode.READ_ONLY);
    }
    
    public static MappedByteBuffer map(final File file, final FileChannel.MapMode mode) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.toString());
        }
        return map(file, mode, file.length());
    }
    
    public static MappedByteBuffer map(final File file, final FileChannel.MapMode mode, final long size) throws FileNotFoundException, IOException {
        final RandomAccessFile raf = new RandomAccessFile(file, (mode == FileChannel.MapMode.READ_ONLY) ? "r" : "rw");
        boolean threw = true;
        try {
            final MappedByteBuffer mbb = map(raf, mode, size);
            threw = false;
            return mbb;
        }
        finally {
            Closeables.close(raf, threw);
        }
    }
    
    private static MappedByteBuffer map(final RandomAccessFile raf, final FileChannel.MapMode mode, final long size) throws IOException {
        final FileChannel channel = raf.getChannel();
        boolean threw = true;
        try {
            final MappedByteBuffer mbb = channel.map(mode, 0L, size);
            threw = false;
            return mbb;
        }
        finally {
            Closeables.close(channel, threw);
        }
    }
    
    private static boolean sep(final char[] a, final int pos) {
        return pos >= a.length || a[pos] == '/';
    }
}
