// 
// Decompiled by Procyon v0.5.29
// 

package Cro.Zombie.Data;

import java.util.HashMap;

public class GameData
{
    public static HashMap<String, String> Zombie;
    public static HashMap<String, String> Delay;
    public static HashMap<String, Integer> bullet;
    public static HashMap<String, Integer> bullet2;
    public static HashMap<String, Integer> Play;
    public static HashMap<String, Integer> Kill;
    
    static {
        GameData.Zombie = new HashMap<String, String>();
        GameData.Delay = new HashMap<String, String>();
        GameData.bullet = new HashMap<String, Integer>();
        GameData.bullet2 = new HashMap<String, Integer>();
        GameData.Play = new HashMap<String, Integer>();
        GameData.Kill = new HashMap<String, Integer>();
    }
}
