// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MajorModule;

import Physical.Fighters.AbilityList.Fly;
import Physical.Fighters.AbilityList.Angel;
import Physical.Fighters.AbilityList.Boom;
import Physical.Fighters.AbilityList.Assassin;
import Physical.Fighters.AbilityList.Hulk;
import Physical.Fighters.AbilityList.Nonuck;
import Physical.Fighters.AbilityList.Fallingarrow;
import Physical.Fighters.AbilityList.Killtolevelup;
import Physical.Fighters.AbilityList.SuperFan;
import Physical.Fighters.AbilityList.Crocodile;
import Physical.Fighters.AbilityList.Booster;
import Physical.Fighters.AbilityList.Fireball;
import Physical.Fighters.AbilityList.Blitzcrank;
import Physical.Fighters.AbilityList.Cuma;
import Physical.Fighters.AbilityList.Kijaru;
import Physical.Fighters.AbilityList.Kaiji;
import Physical.Fighters.AbilityList.Fish;
import Physical.Fighters.AbilityList.Zoro;
import Physical.Fighters.AbilityList.Time2;
import Physical.Fighters.AbilityList.Pagi;
import Physical.Fighters.AbilityList.Kimimaro;
import Physical.Fighters.AbilityList.Tranceball;
import Physical.Fighters.AbilityList.Minato;
import Physical.Fighters.AbilityList.Roclee;
import Physical.Fighters.AbilityList.Gaara;
import Physical.Fighters.AbilityList.Ckyomi;
import Physical.Fighters.AbilityList.Sasuke;
import Physical.Fighters.AbilityList.Guard;
import Physical.Fighters.AbilityList.Trash;
import Physical.Fighters.AbilityList.Explosion;
import Physical.Fighters.AbilityList.Akainu;
import Physical.Fighters.AbilityList.Poseidon;
import Physical.Fighters.AbilityList.CP9;
import Physical.Fighters.AbilityList.Flower;
import Physical.Fighters.AbilityList.Ace;
import Physical.Fighters.AbilityList.Poison;
import Physical.Fighters.AbilityList.Zombie;
import Physical.Fighters.AbilityList.Apollon;
import Physical.Fighters.AbilityList.Demigod;
import Physical.Fighters.AbilityList.Assimilation;
import Physical.Fighters.AbilityList.Thor;
import Physical.Fighters.AbilityList.ShockWave;
import Physical.Fighters.AbilityList.Clocking;
import Physical.Fighters.AbilityList.Aegis;
import Physical.Fighters.AbilityList.Berserker;
import Physical.Fighters.AbilityList.Phoenix;
import Physical.Fighters.AbilityList.Bishop;
import Physical.Fighters.AbilityList.NuclearPunch;
import Physical.Fighters.AbilityList.Time;
import Physical.Fighters.AbilityList.Medic;
import Physical.Fighters.AbilityList.Jumper;
import Physical.Fighters.AbilityList.Blind;
import Physical.Fighters.AbilityList.ReverseAlchemy;
import Physical.Fighters.AbilityList.Mirroring;
import Physical.Fighters.AbilityList.Blaze;
import Physical.Fighters.AbilityList.Feather;
import Physical.Fighters.AbilityList.AC;
import Physical.Fighters.AbilityList.BBom;
import Physical.Fighters.AbilityList.Blaze2;
import Physical.Fighters.AbilityList.Bread;
import Physical.Fighters.AbilityList.Coin;
import Physical.Fighters.AbilityList.Creeper;
import Physical.Fighters.AbilityList.Door;
import Physical.Fighters.AbilityList.Fire;
import Physical.Fighters.AbilityList.Hureeper;
import Physical.Fighters.AbilityList.Magnet;
import Physical.Fighters.AbilityList.MOorDO;
import Physical.Fighters.AbilityList.MY;
import Physical.Fighters.AbilityList.nemi;
import Physical.Fighters.AbilityList.Spider;
import Physical.Fighters.AbilityList.Wall;
import Physical.Fighters.AbilityList.Water;
import Physical.Fighters.MainModule.AbilityBase;
import java.util.ArrayList;

public class AbilityList
{
    public static final ArrayList<AbilityBase> AbilityList;
    public static final Feather feather;
    public static final Blaze blaze;
    public static final Mirroring mirroring;
    public static final ReverseAlchemy reversealchemy;
    public static final Blind blind;
    public static final Jumper jumper;
    public static final Medic medic;
    public static final Time time;
    public static final NuclearPunch nuclearpunch;
    public static final Bishop bishop;
    public static final Phoenix phoenix;
    public static final Berserker berserker;
    public static final Aegis aegis;
    public static final Clocking clocking;
    public static final ShockWave shockwave;
    public static final Thor thor;
    public static final Assimilation assimilation;
    public static final Demigod demigod;
    public static final Apollon apolln;
    public static final Zombie zombie;
    public static final Poison poison;
    public static final Ace ace;
    public static final Flower flower;
    public static final CP9 cp9;
    public static final Poseidon poseidon;
    public static final Akainu akainu;
    public static final Explosion explosion;
    public static final Trash trash;
    public static final Guard guard;
    public static final Sasuke sasuke;
    public static final Ckyomi ckyomi;
    public static final Gaara gaara;
    public static final Roclee roclee;
    public static final Minato minato;
    public static final Tranceball tranceball;
    public static final Kimimaro kimimaro;
    public static final Pagi pagi;
    public static final Time2 time2;
    public static final Zoro zoro;
    public static final Fish fish;
    public static final Kaiji kaiji;
    public static final Kijaru kijaru;
    public static final Cuma cuma;
    public static final Blitzcrank blitzcrank;
    public static final Fireball fireball;
    public static final Booster booster;
    public static final Crocodile crocodile;
    public static final SuperFan SuperFan;
    public static final Killtolevelup Killtolevelup;
    public static final Fallingarrow Fallingarrow;
    public static final Nonuck Nonuck;
    public static final Hulk Hulk;
    public static final Assassin Assassin;
    public static final Boom Boom;
    public static final Angel Angel;
    public static final AC AC;
    public static final BBom BBom;
    public static final Blaze2 Blaze2;
    public static final Bread Bread;
    public static final Coin Coin;
    public static final Creeper Creeper;
    public static final Door Door;
    public static final Fire Fire;
    public static final Hureeper Hureeper;
    public static final Magnet Magnet;
    public static final MOorDO MOorDO;
    public static final MY MY;
    public static final nemi nemi;
    public static final Spider Spider;
    public static final Wall Wall;
    public static final Water Water;
    public static final Fly Fly;
    
    static {
        AbilityList = new ArrayList<AbilityBase>();
        feather = new Feather();
        blaze = new Blaze();
        mirroring = new Mirroring();
        reversealchemy = new ReverseAlchemy();
        blind = new Blind();
        jumper = new Jumper();
        medic = new Medic();
        time = new Time();
        nuclearpunch = new NuclearPunch();
        bishop = new Bishop();
        phoenix = new Phoenix();
        berserker = new Berserker();
        aegis = new Aegis();
        clocking = new Clocking();
        shockwave = new ShockWave();
        thor = new Thor();
        assimilation = new Assimilation();
        demigod = new Demigod();
        apolln = new Apollon();
        zombie = new Zombie();
        poison = new Poison();
        ace = new Ace();
        flower = new Flower();
        cp9 = new CP9();
        poseidon = new Poseidon();
        akainu = new Akainu();
        explosion = new Explosion();
        trash = new Trash();
        guard = new Guard();
        sasuke = new Sasuke();
        ckyomi = new Ckyomi();
        gaara = new Gaara();
        roclee = new Roclee();
        minato = new Minato();
        tranceball = new Tranceball();
        kimimaro = new Kimimaro();
        pagi = new Pagi();
        time2 = new Time2();
        zoro = new Zoro();
        fish = new Fish();
        kaiji = new Kaiji();
        kijaru = new Kijaru();
        cuma = new Cuma();
        blitzcrank = new Blitzcrank();
        fireball = new Fireball();
        booster = new Booster();
        crocodile = new Crocodile();
        SuperFan = new SuperFan();
        Killtolevelup = new Killtolevelup();
        Fallingarrow = new Fallingarrow();
        Nonuck = new Nonuck();
        Hulk = new Hulk();
        Assassin = new Assassin();
        Boom = new Boom();
        Angel = new Angel();
        AC = new AC();
        BBom = new BBom();
        Blaze2 = new Blaze2();
        Bread = new Bread();
        Coin = new Coin();
        Creeper = new Creeper();
        Door = new Door();
        Fire = new Fire();
        Hureeper = new Hureeper();
        Magnet = new Magnet();
        MOorDO = new MOorDO();
        MY = new MY();
        nemi = new nemi();
        Spider = new Spider();
        Wall = new Wall();
        Water = new Water();
        Fly = new Fly();
    }
}
