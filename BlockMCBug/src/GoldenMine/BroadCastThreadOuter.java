// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine;

class BroadCastThreadOuter implements Runnable
{
    static int i;
    static int j;
    
    static {
        BroadCastThreadOuter.i = 0;
        BroadCastThreadOuter.j = 0;
    }
    
    @Override
    public void run() {
        if (BroadCastThreadOuter.i != BroadCastThreadOuter.j) {
            System.out.println("\ud50c\ub7ec\uadf8\uc778\uc758 \uc5b4\ub5a4 \ubd80\ubd84\uc774\ub77c\ub3c4 \ubb34\ub2e8 \uc218\uc815 \ub610\ub294 \uc18c\uc2a4\ub97c \ubb34\ub2e8 \uc0ac\uc6a9 \ud558\uc2dc\ub294 \uacbd\uc6b0 \ubc95\uc801\uc73c\ub85c \uac15\ub825 \ub300\uc751\ud560 \uc608\uc815\uc785\ub2c8\ub2e4.");
        }
        Main.PrintMessage("\uc774 \uc11c\ubc84\ub294 \uc8fc\ubbfc\uc0c1\uc810, RPGitem \ub4f1\uc758 \ubc84\uadf8 \ubc29\uc9c0 \ud328\uce58\ub97c \uc0ac\uc6a9\uc911\uc785\ub2c8\ub2e4. v" + Main.version);
        Main.PrintMessage("\uc81c\uc791\uc790: Golden_Mine blog.naver.com/ehe123");
    }
}
