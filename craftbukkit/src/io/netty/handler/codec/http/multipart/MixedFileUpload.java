// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.ByteBufHolder;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;

public class MixedFileUpload implements FileUpload
{
    private FileUpload fileUpload;
    private final long limitSize;
    private final long definedSize;
    
    public MixedFileUpload(final String name, final String filename, final String contentType, final String contentTransferEncoding, final Charset charset, final long size, final long limitSize) {
        this.limitSize = limitSize;
        if (size > this.limitSize) {
            this.fileUpload = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
        }
        else {
            this.fileUpload = new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
        }
        this.definedSize = size;
    }
    
    @Override
    public void addContent(final ByteBuf buffer, final boolean last) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload && this.fileUpload.length() + buffer.readableBytes() > this.limitSize) {
            final DiskFileUpload diskFileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
            if (((MemoryFileUpload)this.fileUpload).getByteBuf() != null) {
                diskFileUpload.addContent(((MemoryFileUpload)this.fileUpload).getByteBuf(), false);
            }
            this.fileUpload = diskFileUpload;
        }
        this.fileUpload.addContent(buffer, last);
    }
    
    @Override
    public void delete() {
        this.fileUpload.delete();
    }
    
    @Override
    public byte[] get() throws IOException {
        return this.fileUpload.get();
    }
    
    @Override
    public ByteBuf getByteBuf() throws IOException {
        return this.fileUpload.getByteBuf();
    }
    
    @Override
    public Charset getCharset() {
        return this.fileUpload.getCharset();
    }
    
    @Override
    public String getContentType() {
        return this.fileUpload.getContentType();
    }
    
    @Override
    public String getContentTransferEncoding() {
        return this.fileUpload.getContentTransferEncoding();
    }
    
    @Override
    public String getFilename() {
        return this.fileUpload.getFilename();
    }
    
    @Override
    public String getString() throws IOException {
        return this.fileUpload.getString();
    }
    
    @Override
    public String getString(final Charset encoding) throws IOException {
        return this.fileUpload.getString(encoding);
    }
    
    @Override
    public boolean isCompleted() {
        return this.fileUpload.isCompleted();
    }
    
    @Override
    public boolean isInMemory() {
        return this.fileUpload.isInMemory();
    }
    
    @Override
    public long length() {
        return this.fileUpload.length();
    }
    
    @Override
    public boolean renameTo(final File dest) throws IOException {
        return this.fileUpload.renameTo(dest);
    }
    
    @Override
    public void setCharset(final Charset charset) {
        this.fileUpload.setCharset(charset);
    }
    
    @Override
    public void setContent(final ByteBuf buffer) throws IOException {
        if (buffer.readableBytes() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
        }
        this.fileUpload.setContent(buffer);
    }
    
    @Override
    public void setContent(final File file) throws IOException {
        if (file.length() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
        }
        this.fileUpload.setContent(file);
    }
    
    @Override
    public void setContent(final InputStream inputStream) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload) {
            this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
        }
        this.fileUpload.setContent(inputStream);
    }
    
    @Override
    public void setContentType(final String contentType) {
        this.fileUpload.setContentType(contentType);
    }
    
    @Override
    public void setContentTransferEncoding(final String contentTransferEncoding) {
        this.fileUpload.setContentTransferEncoding(contentTransferEncoding);
    }
    
    @Override
    public void setFilename(final String filename) {
        this.fileUpload.setFilename(filename);
    }
    
    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.fileUpload.getHttpDataType();
    }
    
    @Override
    public String getName() {
        return this.fileUpload.getName();
    }
    
    @Override
    public int compareTo(final InterfaceHttpData o) {
        return this.fileUpload.compareTo(o);
    }
    
    @Override
    public String toString() {
        return "Mixed: " + this.fileUpload.toString();
    }
    
    @Override
    public ByteBuf getChunk(final int length) throws IOException {
        return this.fileUpload.getChunk(length);
    }
    
    @Override
    public File getFile() throws IOException {
        return this.fileUpload.getFile();
    }
    
    @Override
    public FileUpload copy() {
        return this.fileUpload.copy();
    }
    
    @Override
    public ByteBuf data() {
        return this.fileUpload.data();
    }
    
    @Override
    public int refCnt() {
        return this.fileUpload.refCnt();
    }
    
    @Override
    public FileUpload retain() {
        this.fileUpload.retain();
        return this;
    }
    
    @Override
    public FileUpload retain(final int increment) {
        this.fileUpload.retain(increment);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.fileUpload.release();
    }
    
    @Override
    public boolean release(final int decrement) {
        return this.fileUpload.release(decrement);
    }
}
