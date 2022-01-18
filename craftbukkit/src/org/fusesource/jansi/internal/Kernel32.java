// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi.internal;

import org.fusesource.hawtjni.runtime.ClassFlag;
import org.fusesource.hawtjni.runtime.PointerMath;
import java.io.IOException;
import org.fusesource.hawtjni.runtime.ArgFlag;
import org.fusesource.hawtjni.runtime.JniArg;
import org.fusesource.hawtjni.runtime.MethodFlag;
import org.fusesource.hawtjni.runtime.JniMethod;
import org.fusesource.hawtjni.runtime.FieldFlag;
import org.fusesource.hawtjni.runtime.JniField;
import org.fusesource.hawtjni.runtime.Library;
import org.fusesource.hawtjni.runtime.JniClass;

@JniClass(conditional = "defined(_WIN32) || defined(_WIN64)")
public class Kernel32
{
    private static final Library LIBRARY;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short FOREGROUND_BLUE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short FOREGROUND_GREEN;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short FOREGROUND_RED;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short FOREGROUND_INTENSITY;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short BACKGROUND_BLUE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short BACKGROUND_GREEN;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short BACKGROUND_RED;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short BACKGROUND_INTENSITY;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_LEADING_BYTE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_TRAILING_BYTE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_GRID_HORIZONTAL;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_GRID_LVERTICAL;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_GRID_RVERTICAL;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_REVERSE_VIDEO;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static short COMMON_LVB_UNDERSCORE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static int FORMAT_MESSAGE_FROM_SYSTEM;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static int STD_INPUT_HANDLE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static int STD_OUTPUT_HANDLE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static int STD_ERROR_HANDLE;
    @JniField(flags = { FieldFlag.CONSTANT })
    public static int INVALID_HANDLE_VALUE;
    
    @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
    private static final native void init();
    
    @JniMethod(cast = "void *")
    public static final native long malloc(@JniArg(cast = "size_t") final long p0);
    
    public static final native void free(@JniArg(cast = "void *") final long p0);
    
    public static final native int SetConsoleTextAttribute(@JniArg(cast = "HANDLE") final long p0, final short p1);
    
    public static final native int CloseHandle(@JniArg(cast = "HANDLE") final long p0);
    
    public static final native int GetLastError();
    
    public static final native int FormatMessageW(final int p0, @JniArg(cast = "void *") final long p1, final int p2, final int p3, @JniArg(cast = "void *", flags = { ArgFlag.NO_IN, ArgFlag.CRITICAL }) final byte[] p4, final int p5, @JniArg(cast = "void *", flags = { ArgFlag.NO_IN, ArgFlag.CRITICAL, ArgFlag.SENTINEL }) final long[] p6);
    
    public static final native int GetConsoleScreenBufferInfo(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final CONSOLE_SCREEN_BUFFER_INFO p1);
    
    @JniMethod(cast = "HANDLE", flags = { MethodFlag.POINTER_RETURN })
    public static final native long GetStdHandle(final int p0);
    
    public static final native int SetConsoleCursorPosition(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, @JniArg(flags = { ArgFlag.BY_VALUE }) final COORD p1);
    
    public static final native int FillConsoleOutputCharacterW(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final char p1, final int p2, @JniArg(flags = { ArgFlag.BY_VALUE }) final COORD p3, final int[] p4);
    
    public static final native int WriteConsoleW(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final char[] p1, final int p2, final int[] p3, @JniArg(cast = "LPVOID", flags = { ArgFlag.POINTER_ARG }) final long p4);
    
    public static final native int GetConsoleMode(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final int[] p1);
    
    public static final native int SetConsoleMode(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final int p1);
    
    public static final native int _getch();
    
    public static final native int SetConsoleTitle(@JniArg(flags = { ArgFlag.UNICODE }) final String p0);
    
    public static final native int GetConsoleOutputCP();
    
    public static final native int SetConsoleOutputCP(final int p0);
    
    private static final native int ReadConsoleInputW(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final long p1, final int p2, final int[] p3);
    
    private static final native int PeekConsoleInputW(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final long p1, final int p2, final int[] p3);
    
    public static final native int GetNumberOfConsoleInputEvents(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0, final int[] p1);
    
    public static final native int FlushConsoleInputBuffer(@JniArg(cast = "HANDLE", flags = { ArgFlag.POINTER_ARG }) final long p0);
    
    public static INPUT_RECORD[] readConsoleInputHelper(final long handle, final int count, final boolean peek) throws IOException {
        final int[] length = { 0 };
        long inputRecordPtr = 0L;
        try {
            inputRecordPtr = malloc(INPUT_RECORD.SIZEOF * count);
            if (inputRecordPtr == 0L) {
                throw new IOException("cannot allocate memory with JNI");
            }
            final int res = peek ? PeekConsoleInputW(handle, inputRecordPtr, count, length) : ReadConsoleInputW(handle, inputRecordPtr, count, length);
            if (res == 0) {
                throw new IOException("ReadConsoleInputW failed");
            }
            if (length[0] <= 0) {
                return new INPUT_RECORD[0];
            }
            final INPUT_RECORD[] records = new INPUT_RECORD[length[0]];
            for (int i = 0; i < records.length; ++i) {
                INPUT_RECORD.memmove(records[i] = new INPUT_RECORD(), PointerMath.add(inputRecordPtr, i * INPUT_RECORD.SIZEOF), INPUT_RECORD.SIZEOF);
            }
            return records;
        }
        finally {
            if (inputRecordPtr != 0L) {
                free(inputRecordPtr);
            }
        }
    }
    
    public static INPUT_RECORD[] readConsoleKeyInput(final long handle, final int count, final boolean peek) throws IOException {
        INPUT_RECORD[] evts;
        int keyEvtCount;
        do {
            evts = readConsoleInputHelper(handle, count, peek);
            keyEvtCount = 0;
            for (final INPUT_RECORD evt : evts) {
                if (evt.eventType == INPUT_RECORD.KEY_EVENT) {
                    ++keyEvtCount;
                }
            }
        } while (keyEvtCount <= 0);
        final INPUT_RECORD[] res = new INPUT_RECORD[keyEvtCount];
        int i = 0;
        for (final INPUT_RECORD evt2 : evts) {
            if (evt2.eventType == INPUT_RECORD.KEY_EVENT) {
                res[i++] = evt2;
            }
        }
        return res;
    }
    
    static {
        (LIBRARY = new Library("jansi", Kernel32.class)).load();
        init();
    }
    
    @JniClass(flags = { ClassFlag.STRUCT, ClassFlag.TYPEDEF }, conditional = "defined(_WIN32) || defined(_WIN64)")
    public static class SMALL_RECT
    {
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "sizeof(SMALL_RECT)")
        public static int SIZEOF;
        @JniField(accessor = "Left")
        public short left;
        @JniField(accessor = "Top")
        public short top;
        @JniField(accessor = "Right")
        public short right;
        @JniField(accessor = "Bottom")
        public short bottom;
        
        @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
        private static final native void init();
        
        public short width() {
            return (short)(this.right - this.left);
        }
        
        public short height() {
            return (short)(this.bottom - this.top);
        }
        
        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }
    
    @JniClass(flags = { ClassFlag.STRUCT, ClassFlag.TYPEDEF }, conditional = "defined(_WIN32) || defined(_WIN64)")
    public static class COORD
    {
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "sizeof(COORD)")
        public static int SIZEOF;
        @JniField(accessor = "X")
        public short x;
        @JniField(accessor = "Y")
        public short y;
        
        @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
        private static final native void init();
        
        public COORD copy() {
            final COORD rc = new COORD();
            rc.x = this.x;
            rc.y = this.y;
            return rc;
        }
        
        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }
    
    @JniClass(flags = { ClassFlag.STRUCT, ClassFlag.TYPEDEF }, conditional = "defined(_WIN32) || defined(_WIN64)")
    public static class CONSOLE_SCREEN_BUFFER_INFO
    {
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "sizeof(CONSOLE_SCREEN_BUFFER_INFO)")
        public static int SIZEOF;
        @JniField(accessor = "dwSize")
        public COORD size;
        @JniField(accessor = "dwCursorPosition")
        public COORD cursorPosition;
        @JniField(accessor = "wAttributes")
        public short attributes;
        @JniField(accessor = "srWindow")
        public SMALL_RECT window;
        @JniField(accessor = "dwMaximumWindowSize")
        public COORD maximumWindowSize;
        
        public CONSOLE_SCREEN_BUFFER_INFO() {
            this.size = new COORD();
            this.cursorPosition = new COORD();
            this.window = new SMALL_RECT();
            this.maximumWindowSize = new COORD();
        }
        
        @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
        private static final native void init();
        
        public int windowWidth() {
            return this.window.width() + 1;
        }
        
        public int windowHeight() {
            return this.window.height() + 1;
        }
        
        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }
    
    @JniClass(flags = { ClassFlag.STRUCT, ClassFlag.TYPEDEF }, conditional = "defined(_WIN32) || defined(_WIN64)")
    public static class KEY_EVENT_RECORD
    {
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "sizeof(KEY_EVENT_RECORD)")
        public static int SIZEOF;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "CAPSLOCK_ON")
        public static int CAPSLOCK_ON;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "NUMLOCK_ON")
        public static int NUMLOCK_ON;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "SCROLLLOCK_ON")
        public static int SCROLLLOCK_ON;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "ENHANCED_KEY")
        public static int ENHANCED_KEY;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "LEFT_ALT_PRESSED")
        public static int LEFT_ALT_PRESSED;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "LEFT_CTRL_PRESSED")
        public static int LEFT_CTRL_PRESSED;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "RIGHT_ALT_PRESSED")
        public static int RIGHT_ALT_PRESSED;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "RIGHT_CTRL_PRESSED")
        public static int RIGHT_CTRL_PRESSED;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "SHIFT_PRESSED")
        public static int SHIFT_PRESSED;
        @JniField(accessor = "bKeyDown")
        public boolean keyDown;
        @JniField(accessor = "wRepeatCount")
        public short repeatCount;
        @JniField(accessor = "wVirtualKeyCode")
        public short keyCode;
        @JniField(accessor = "wVirtualScanCode")
        public short scanCode;
        @JniField(accessor = "uChar.UnicodeChar")
        public char uchar;
        @JniField(accessor = "dwControlKeyState")
        public int controlKeyState;
        
        @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
        private static final native void init();
        
        public String toString() {
            return "KEY_EVENT_RECORD{keyDown=" + this.keyDown + ", repeatCount=" + this.repeatCount + ", keyCode=" + this.keyCode + ", scanCode=" + this.scanCode + ", uchar=" + this.uchar + ", controlKeyState=" + this.controlKeyState + '}';
        }
        
        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }
    
    @JniClass(flags = { ClassFlag.STRUCT, ClassFlag.TYPEDEF }, conditional = "defined(_WIN32) || defined(_WIN64)")
    public static class INPUT_RECORD
    {
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "sizeof(INPUT_RECORD)")
        public static int SIZEOF;
        @JniField(flags = { FieldFlag.CONSTANT }, accessor = "KEY_EVENT")
        public static short KEY_EVENT;
        @JniField(accessor = "EventType")
        public short eventType;
        @JniField(accessor = "Event.KeyEvent")
        public KEY_EVENT_RECORD keyEvent;
        
        public INPUT_RECORD() {
            this.keyEvent = new KEY_EVENT_RECORD();
        }
        
        @JniMethod(flags = { MethodFlag.CONSTANT_INITIALIZER })
        private static final native void init();
        
        public static final native void memmove(@JniArg(cast = "void *", flags = { ArgFlag.NO_IN, ArgFlag.CRITICAL }) final INPUT_RECORD p0, @JniArg(cast = "const void *", flags = { ArgFlag.NO_OUT, ArgFlag.CRITICAL }) final long p1, @JniArg(cast = "size_t") final long p2);
        
        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }
}
