// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public interface ByteBufIndexFinder
{
    public static final ByteBufIndexFinder NUL = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) == 0;
        }
    };
    public static final ByteBufIndexFinder NOT_NUL = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) != 0;
        }
    };
    public static final ByteBufIndexFinder CR = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) == 13;
        }
    };
    public static final ByteBufIndexFinder NOT_CR = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) != 13;
        }
    };
    public static final ByteBufIndexFinder LF = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) == 10;
        }
    };
    public static final ByteBufIndexFinder NOT_LF = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            return buffer.getByte(guessedIndex) != 10;
        }
    };
    public static final ByteBufIndexFinder CRLF = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            final byte b = buffer.getByte(guessedIndex);
            return b == 13 || b == 10;
        }
    };
    public static final ByteBufIndexFinder NOT_CRLF = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            final byte b = buffer.getByte(guessedIndex);
            return b != 13 && b != 10;
        }
    };
    public static final ByteBufIndexFinder LINEAR_WHITESPACE = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            final byte b = buffer.getByte(guessedIndex);
            return b == 32 || b == 9;
        }
    };
    public static final ByteBufIndexFinder NOT_LINEAR_WHITESPACE = new ByteBufIndexFinder() {
        @Override
        public boolean find(final ByteBuf buffer, final int guessedIndex) {
            final byte b = buffer.getByte(guessedIndex);
            return b != 32 && b != 9;
        }
    };
    
    boolean find(final ByteBuf p0, final int p1);
}
