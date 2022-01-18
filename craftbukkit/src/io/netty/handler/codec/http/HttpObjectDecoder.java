// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.handler.codec.TooLongFrameException;
import java.util.List;
import io.netty.handler.codec.DecoderResult;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ReplayingDecoder;

public abstract class HttpObjectDecoder extends ReplayingDecoder<State>
{
    private final int maxInitialLineLength;
    private final int maxHeaderSize;
    private final int maxChunkSize;
    private final boolean chunkedSupported;
    private ByteBuf content;
    private HttpMessage message;
    private long chunkSize;
    private int headerSize;
    private int contentRead;
    
    protected HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }
    
    protected HttpObjectDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize, final boolean chunkedSupported) {
        super(State.SKIP_CONTROL_CHARS);
        if (maxInitialLineLength <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + maxInitialLineLength);
        }
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize);
        }
        if (maxChunkSize < 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        }
        this.maxInitialLineLength = maxInitialLineLength;
        this.maxHeaderSize = maxHeaderSize;
        this.maxChunkSize = maxChunkSize;
        this.chunkedSupported = chunkedSupported;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf buffer) throws Exception {
        switch (this.state()) {
            case SKIP_CONTROL_CHARS: {
                try {
                    skipControlCharacters(buffer);
                    this.checkpoint(State.READ_INITIAL);
                }
                finally {
                    this.checkpoint();
                }
            }
            case READ_INITIAL: {
                try {
                    final String[] initialLine = splitInitialLine(readLine(buffer, this.maxInitialLineLength));
                    if (initialLine.length < 3) {
                        this.checkpoint(State.SKIP_CONTROL_CHARS);
                        return null;
                    }
                    this.message = this.createMessage(initialLine);
                    this.checkpoint(State.READ_HEADER);
                }
                catch (Exception e) {
                    return this.invalidMessage(e);
                }
            }
            case READ_HEADER: {
                try {
                    final State nextState = this.readHeaders(buffer);
                    this.checkpoint(nextState);
                    if (nextState == State.READ_CHUNK_SIZE) {
                        if (!this.chunkedSupported) {
                            throw new IllegalArgumentException("Chunked messages not supported");
                        }
                        return this.message;
                    }
                    else {
                        if (nextState == State.SKIP_CONTROL_CHARS) {
                            return this.reset();
                        }
                        final long contentLength = HttpHeaders.getContentLength(this.message, -1L);
                        if (contentLength == 0L || (contentLength == -1L && this.isDecodingRequest())) {
                            this.content = Unpooled.EMPTY_BUFFER;
                            return this.reset();
                        }
                        switch (nextState) {
                            case READ_FIXED_LENGTH_CONTENT: {
                                if (contentLength > this.maxChunkSize || HttpHeaders.is100ContinueExpected(this.message)) {
                                    this.checkpoint(State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS);
                                    this.chunkSize = HttpHeaders.getContentLength(this.message, -1L);
                                    return this.message;
                                }
                                break;
                            }
                            case READ_VARIABLE_LENGTH_CONTENT: {
                                if (buffer.readableBytes() > this.maxChunkSize || HttpHeaders.is100ContinueExpected(this.message)) {
                                    this.checkpoint(State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS);
                                    return this.message;
                                }
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Unexpected state: " + nextState);
                            }
                        }
                        return null;
                    }
                }
                catch (Exception e) {
                    return this.invalidMessage(e);
                }
            }
            case READ_VARIABLE_LENGTH_CONTENT: {
                int toRead = this.actualReadableBytes();
                if (toRead > this.maxChunkSize) {
                    toRead = this.maxChunkSize;
                }
                return new Object[] { this.message, new DefaultHttpContent(buffer.readBytes(toRead)) };
            }
            case READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS: {
                int toRead = this.actualReadableBytes();
                if (toRead > this.maxChunkSize) {
                    toRead = this.maxChunkSize;
                }
                final ByteBuf content = buffer.readBytes(toRead);
                if (!buffer.isReadable()) {
                    this.reset();
                    return new DefaultLastHttpContent(content);
                }
                return new DefaultHttpContent(content);
            }
            case READ_FIXED_LENGTH_CONTENT: {
                return this.readFixedLengthContent(buffer);
            }
            case READ_FIXED_LENGTH_CONTENT_AS_CHUNKS: {
                long chunkSize = this.chunkSize;
                final int readLimit = this.actualReadableBytes();
                if (readLimit == 0) {
                    return null;
                }
                int toRead2 = readLimit;
                if (toRead2 > this.maxChunkSize) {
                    toRead2 = this.maxChunkSize;
                }
                if (toRead2 > chunkSize) {
                    toRead2 = (int)chunkSize;
                }
                final ByteBuf content2 = buffer.readBytes(toRead2);
                if (chunkSize > toRead2) {
                    chunkSize -= toRead2;
                }
                else {
                    chunkSize = 0L;
                }
                this.chunkSize = chunkSize;
                if (chunkSize == 0L) {
                    this.reset();
                    return new DefaultLastHttpContent(content2);
                }
                return new DefaultHttpContent(content2);
            }
            case READ_CHUNK_SIZE: {
                try {
                    final String line = readLine(buffer, this.maxInitialLineLength);
                    final int chunkSize2 = getChunkSize(line);
                    this.chunkSize = chunkSize2;
                    if (chunkSize2 == 0) {
                        this.checkpoint(State.READ_CHUNK_FOOTER);
                        return null;
                    }
                    if (chunkSize2 > this.maxChunkSize) {
                        this.checkpoint(State.READ_CHUNKED_CONTENT_AS_CHUNKS);
                    }
                    else {
                        this.checkpoint(State.READ_CHUNKED_CONTENT);
                    }
                }
                catch (Exception e) {
                    return this.invalidChunk(e);
                }
            }
            case READ_CHUNKED_CONTENT: {
                assert this.chunkSize <= 2147483647L;
                final HttpContent chunk = new DefaultHttpContent(buffer.readBytes((int)this.chunkSize));
                this.checkpoint(State.READ_CHUNK_DELIMITER);
                return chunk;
            }
            case READ_CHUNKED_CONTENT_AS_CHUNKS: {
                assert this.chunkSize <= 2147483647L;
                int chunkSize3 = (int)this.chunkSize;
                final int readLimit2 = this.actualReadableBytes();
                if (readLimit2 == 0) {
                    return null;
                }
                int toRead3 = chunkSize3;
                if (toRead3 > this.maxChunkSize) {
                    toRead3 = this.maxChunkSize;
                }
                if (toRead3 > readLimit2) {
                    toRead3 = readLimit2;
                }
                final HttpContent chunk2 = new DefaultHttpContent(buffer.readBytes(toRead3));
                if (chunkSize3 > toRead3) {
                    chunkSize3 -= toRead3;
                }
                else {
                    chunkSize3 = 0;
                }
                this.chunkSize = chunkSize3;
                if (chunkSize3 == 0) {
                    this.checkpoint(State.READ_CHUNK_DELIMITER);
                }
                return chunk2;
            }
            case READ_CHUNK_DELIMITER: {
                while (true) {
                    final byte next = buffer.readByte();
                    if (next == 13) {
                        if (buffer.readByte() == 10) {
                            this.checkpoint(State.READ_CHUNK_SIZE);
                            return null;
                        }
                        continue;
                    }
                    else {
                        if (next == 10) {
                            this.checkpoint(State.READ_CHUNK_SIZE);
                            return null;
                        }
                        this.checkpoint();
                    }
                }
                break;
            }
            case READ_CHUNK_FOOTER: {
                try {
                    final LastHttpContent trailer = this.readTrailingHeaders(buffer);
                    if (this.maxChunkSize == 0) {
                        return this.reset();
                    }
                    this.reset();
                    return trailer;
                }
                catch (Exception e) {
                    return this.invalidChunk(e);
                }
            }
            case BAD_MESSAGE: {
                buffer.skipBytes(this.actualReadableBytes());
                return null;
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    protected boolean isContentAlwaysEmpty(final HttpMessage msg) {
        if (msg instanceof HttpResponse) {
            final HttpResponse res = (HttpResponse)msg;
            final int code = res.getStatus().code();
            if (code >= 100 && code < 200) {
                return code != 101 || res.headers().contains("Sec-WebSocket-Accept");
            }
            switch (code) {
                case 204:
                case 205:
                case 304: {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Object reset() {
        final HttpMessage message = this.message;
        final ByteBuf content = this.content;
        LastHttpContent httpContent;
        if (content == null || !content.isReadable()) {
            httpContent = LastHttpContent.EMPTY_LAST_CONTENT;
        }
        else {
            httpContent = new DefaultLastHttpContent(content);
        }
        final Object[] messages = { message, httpContent };
        this.content = null;
        this.message = null;
        this.checkpoint(State.SKIP_CONTROL_CHARS);
        return messages;
    }
    
    private HttpMessage invalidMessage(final Exception cause) {
        this.checkpoint(State.BAD_MESSAGE);
        if (this.message != null) {
            this.message.setDecoderResult(DecoderResult.failure(cause));
        }
        else {
            (this.message = this.createInvalidMessage()).setDecoderResult(DecoderResult.failure(cause));
        }
        return this.message;
    }
    
    private HttpContent invalidChunk(final Exception cause) {
        this.checkpoint(State.BAD_MESSAGE);
        final HttpContent chunk = new DefaultHttpContent(Unpooled.EMPTY_BUFFER);
        chunk.setDecoderResult(DecoderResult.failure(cause));
        return chunk;
    }
    
    private static void skipControlCharacters(final ByteBuf buffer) {
        char c;
        do {
            c = (char)buffer.readUnsignedByte();
        } while (Character.isISOControl(c) || Character.isWhitespace(c));
        buffer.readerIndex(buffer.readerIndex() - 1);
    }
    
    private Object readFixedLengthContent(final ByteBuf buffer) {
        final long length = HttpHeaders.getContentLength(this.message, -1L);
        assert length <= 2147483647L;
        int toRead = (int)length - this.contentRead;
        if (toRead > this.actualReadableBytes()) {
            toRead = this.actualReadableBytes();
        }
        this.contentRead += toRead;
        if (length < this.contentRead) {
            return new Object[] { this.message, new DefaultHttpContent(buffer.readBytes(toRead)) };
        }
        if (this.content == null) {
            this.content = buffer.readBytes((int)length);
        }
        else {
            this.content.writeBytes(buffer, (int)length);
        }
        return this.reset();
    }
    
    private State readHeaders(final ByteBuf buffer) {
        this.headerSize = 0;
        final HttpMessage message = this.message;
        final HttpHeaders headers = message.headers();
        String line = this.readHeader(buffer);
        String name = null;
        String value = null;
        if (!line.isEmpty()) {
            headers.clear();
            do {
                final char firstChar = line.charAt(0);
                if (name != null && (firstChar == ' ' || firstChar == '\t')) {
                    value = value + ' ' + line.trim();
                }
                else {
                    if (name != null) {
                        headers.add(name, value);
                    }
                    final String[] header = splitHeader(line);
                    name = header[0];
                    value = header[1];
                }
                line = this.readHeader(buffer);
            } while (!line.isEmpty());
            if (name != null) {
                headers.add(name, value);
            }
        }
        State nextState;
        if (this.isContentAlwaysEmpty(message)) {
            HttpHeaders.removeTransferEncodingChunked(message);
            nextState = State.SKIP_CONTROL_CHARS;
        }
        else if (HttpHeaders.isTransferEncodingChunked(message)) {
            nextState = State.READ_CHUNK_SIZE;
        }
        else if (HttpHeaders.getContentLength(message, -1L) >= 0L) {
            nextState = State.READ_FIXED_LENGTH_CONTENT;
        }
        else {
            nextState = State.READ_VARIABLE_LENGTH_CONTENT;
        }
        return nextState;
    }
    
    private LastHttpContent readTrailingHeaders(final ByteBuf buffer) {
        this.headerSize = 0;
        String line = this.readHeader(buffer);
        String lastHeader = null;
        if (!line.isEmpty()) {
            final LastHttpContent trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
            do {
                final char firstChar = line.charAt(0);
                if (lastHeader != null && (firstChar == ' ' || firstChar == '\t')) {
                    final List<String> current = trailer.trailingHeaders().getAll(lastHeader);
                    if (!current.isEmpty()) {
                        final int lastPos = current.size() - 1;
                        final String newString = current.get(lastPos) + line.trim();
                        current.set(lastPos, newString);
                    }
                }
                else {
                    final String[] header = splitHeader(line);
                    final String name = header[0];
                    if (!name.equalsIgnoreCase("Content-Length") && !name.equalsIgnoreCase("Transfer-Encoding") && !name.equalsIgnoreCase("Trailer")) {
                        trailer.trailingHeaders().add(name, header[1]);
                    }
                    lastHeader = name;
                }
                line = this.readHeader(buffer);
            } while (!line.isEmpty());
            return trailer;
        }
        return LastHttpContent.EMPTY_LAST_CONTENT;
    }
    
    private String readHeader(final ByteBuf buffer) {
        final StringBuilder sb = new StringBuilder(64);
        int headerSize = this.headerSize;
    Label_0136:
        while (true) {
            char nextByte = (char)buffer.readByte();
            ++headerSize;
            switch (nextByte) {
                case '\r': {
                    nextByte = (char)buffer.readByte();
                    ++headerSize;
                    if (nextByte == '\n') {
                        break Label_0136;
                    }
                    break;
                }
                case '\n': {
                    break Label_0136;
                }
            }
            if (headerSize >= this.maxHeaderSize) {
                throw new TooLongFrameException("HTTP header is larger than " + this.maxHeaderSize + " bytes.");
            }
            sb.append(nextByte);
        }
        this.headerSize = headerSize;
        return sb.toString();
    }
    
    protected abstract boolean isDecodingRequest();
    
    protected abstract HttpMessage createMessage(final String[] p0) throws Exception;
    
    protected abstract HttpMessage createInvalidMessage();
    
    private static int getChunkSize(String hex) {
        hex = hex.trim();
        for (int i = 0; i < hex.length(); ++i) {
            final char c = hex.charAt(i);
            if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
                hex = hex.substring(0, i);
                break;
            }
        }
        return Integer.parseInt(hex, 16);
    }
    
    private static String readLine(final ByteBuf buffer, final int maxLineLength) {
        final StringBuilder sb = new StringBuilder(64);
        int lineLength = 0;
        while (true) {
            byte nextByte = buffer.readByte();
            if (nextByte == 13) {
                nextByte = buffer.readByte();
                if (nextByte == 10) {
                    return sb.toString();
                }
                continue;
            }
            else {
                if (nextByte == 10) {
                    return sb.toString();
                }
                if (lineLength >= maxLineLength) {
                    throw new TooLongFrameException("An HTTP line is larger than " + maxLineLength + " bytes.");
                }
                ++lineLength;
                sb.append((char)nextByte);
            }
        }
    }
    
    private static String[] splitInitialLine(final String sb) {
        final int aStart = findNonWhitespace(sb, 0);
        final int aEnd = findWhitespace(sb, aStart);
        final int bStart = findNonWhitespace(sb, aEnd);
        final int bEnd = findWhitespace(sb, bStart);
        final int cStart = findNonWhitespace(sb, bEnd);
        final int cEnd = findEndOfString(sb);
        return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), (cStart < cEnd) ? sb.substring(cStart, cEnd) : "" };
    }
    
    private static String[] splitHeader(final String sb) {
        int length;
        int nameEnd;
        int nameStart;
        for (length = sb.length(), nameStart = (nameEnd = findNonWhitespace(sb, 0)); nameEnd < length; ++nameEnd) {
            final char ch = sb.charAt(nameEnd);
            if (ch == ':') {
                break;
            }
            if (Character.isWhitespace(ch)) {
                break;
            }
        }
        int colonEnd;
        for (colonEnd = nameEnd; colonEnd < length; ++colonEnd) {
            if (sb.charAt(colonEnd) == ':') {
                ++colonEnd;
                break;
            }
        }
        final int valueStart = findNonWhitespace(sb, colonEnd);
        if (valueStart == length) {
            return new String[] { sb.substring(nameStart, nameEnd), "" };
        }
        final int valueEnd = findEndOfString(sb);
        return new String[] { sb.substring(nameStart, nameEnd), sb.substring(valueStart, valueEnd) };
    }
    
    private static int findNonWhitespace(final String sb, final int offset) {
        int result;
        for (result = offset; result < sb.length() && Character.isWhitespace(sb.charAt(result)); ++result) {}
        return result;
    }
    
    private static int findWhitespace(final String sb, final int offset) {
        int result;
        for (result = offset; result < sb.length() && !Character.isWhitespace(sb.charAt(result)); ++result) {}
        return result;
    }
    
    private static int findEndOfString(final String sb) {
        int result;
        for (result = sb.length(); result > 0 && Character.isWhitespace(sb.charAt(result - 1)); --result) {}
        return result;
    }
    
    enum State
    {
        SKIP_CONTROL_CHARS, 
        READ_INITIAL, 
        READ_HEADER, 
        READ_VARIABLE_LENGTH_CONTENT, 
        READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS, 
        READ_FIXED_LENGTH_CONTENT, 
        READ_FIXED_LENGTH_CONTENT_AS_CHUNKS, 
        READ_CHUNK_SIZE, 
        READ_CHUNKED_CONTENT, 
        READ_CHUNKED_CONTENT_AS_CHUNKS, 
        READ_CHUNK_DELIMITER, 
        READ_CHUNK_FOOTER, 
        BAD_MESSAGE;
    }
}
