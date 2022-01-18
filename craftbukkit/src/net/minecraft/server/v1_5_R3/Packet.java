// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashSet;
import java.util.HashMap;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.io.EOFException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.util.Set;
import java.util.Map;

public abstract class Packet
{
    public static IntHashMap l;
    private static Map a;
    private static Set b;
    private static Set c;
    protected IConsoleLogManager m;
    public final long timestamp;
    public static long o;
    public static long p;
    public static long q;
    public static long r;
    public boolean lowPriority;
    private int packetID;
    
    public Packet() {
        this.timestamp = System.currentTimeMillis();
        this.lowPriority = false;
        this.packetID = Packet.a.get(this.getClass());
    }
    
    static void a(final int i, final boolean flag, final boolean flag1, final Class oclass) {
        if (Packet.l.b(i)) {
            throw new IllegalArgumentException("Duplicate packet id:" + i);
        }
        if (Packet.a.containsKey(oclass)) {
            throw new IllegalArgumentException("Duplicate packet class:" + oclass);
        }
        Packet.l.a(i, oclass);
        Packet.a.put(oclass, i);
        if (flag) {
            Packet.b.add(i);
        }
        if (flag1) {
            Packet.c.add(i);
        }
    }
    
    public static Packet a(final IConsoleLogManager iconsolelogmanager, final int i) {
        try {
            final Class oclass = (Class)Packet.l.get(i);
            return (oclass == null) ? null : oclass.newInstance();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            iconsolelogmanager.severe("Skipping packet with id " + i);
            return null;
        }
    }
    
    public static void a(final DataOutputStream dataoutputstream, final byte[] abyte) throws IOException {
        dataoutputstream.writeShort(abyte.length);
        dataoutputstream.write(abyte);
    }
    
    public static byte[] b(final DataInputStream datainputstream) throws IOException {
        final short short1 = datainputstream.readShort();
        if (short1 < 0) {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        final byte[] abyte = new byte[short1];
        datainputstream.readFully(abyte);
        return abyte;
    }
    
    public final int n() {
        return this.packetID;
    }
    
    public static Packet a(final IConsoleLogManager iconsolelogmanager, final DataInputStream datainputstream, final boolean flag, final Socket socket) throws IOException {
        final boolean flag2 = false;
        Packet packet = null;
        final int i = socket.getSoTimeout();
        int j;
        try {
            j = datainputstream.read();
            if (j == -1) {
                return null;
            }
            if ((flag && !Packet.c.contains(j)) || (!flag && !Packet.b.contains(j))) {
                throw new IOException("Bad packet id " + j);
            }
            packet = a(iconsolelogmanager, j);
            if (packet == null) {
                throw new IOException("Bad packet id " + j);
            }
            packet.m = iconsolelogmanager;
            if (packet instanceof Packet254GetInfo) {
                socket.setSoTimeout(1500);
            }
            packet.a(datainputstream);
            ++Packet.o;
            Packet.p += packet.a();
        }
        catch (EOFException eofexception) {
            iconsolelogmanager.severe("Reached end of stream");
            return null;
        }
        catch (SocketTimeoutException exception) {
            iconsolelogmanager.info("Read timed out");
            return null;
        }
        catch (SocketException exception2) {
            iconsolelogmanager.info("Connection reset");
            return null;
        }
        PacketCounter.a(j, packet.a());
        ++Packet.o;
        Packet.p += packet.a();
        socket.setSoTimeout(i);
        return packet;
    }
    
    public static void a(final Packet packet, final DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.write(packet.n());
        packet.a(dataoutputstream);
        ++Packet.q;
        Packet.r += packet.a();
    }
    
    public static void a(final String s, final DataOutputStream dataoutputstream) throws IOException {
        if (s.length() > 32767) {
            throw new IOException("String too big");
        }
        dataoutputstream.writeShort(s.length());
        dataoutputstream.writeChars(s);
    }
    
    public static String a(final DataInputStream datainputstream, final int i) throws IOException {
        final short short1 = datainputstream.readShort();
        if (short1 > i) {
            throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + i + ")");
        }
        if (short1 < 0) {
            throw new IOException("Received string length is less than zero! Weird string!");
        }
        final StringBuilder stringbuilder = new StringBuilder();
        for (int j = 0; j < short1; ++j) {
            stringbuilder.append(datainputstream.readChar());
        }
        return stringbuilder.toString();
    }
    
    public abstract void a(final DataInputStream p0) throws IOException;
    
    public abstract void a(final DataOutputStream p0) throws IOException;
    
    public abstract void handle(final Connection p0);
    
    public abstract int a();
    
    public boolean e() {
        return false;
    }
    
    public boolean a(final Packet packet) {
        return false;
    }
    
    public boolean a_() {
        return false;
    }
    
    public String toString() {
        final String s = this.getClass().getSimpleName();
        return s;
    }
    
    public static ItemStack c(final DataInputStream datainputstream) throws IOException {
        ItemStack itemstack = null;
        final short short1 = datainputstream.readShort();
        if (short1 >= 0) {
            final byte b0 = datainputstream.readByte();
            final short short2 = datainputstream.readShort();
            itemstack = new ItemStack(short1, b0, short2);
            itemstack.tag = d(datainputstream);
        }
        return itemstack;
    }
    
    public static void a(final ItemStack itemstack, final DataOutputStream dataoutputstream) throws IOException {
        if (itemstack == null || itemstack.getItem() == null) {
            dataoutputstream.writeShort(-1);
        }
        else {
            dataoutputstream.writeShort(itemstack.id);
            dataoutputstream.writeByte(itemstack.count);
            dataoutputstream.writeShort(itemstack.getData());
            NBTTagCompound nbttagcompound = null;
            if (itemstack.getItem().usesDurability() || itemstack.getItem().r()) {
                nbttagcompound = itemstack.tag;
            }
            a(nbttagcompound, dataoutputstream);
        }
    }
    
    public static NBTTagCompound d(final DataInputStream datainputstream) throws IOException {
        final short short1 = datainputstream.readShort();
        if (short1 < 0) {
            return null;
        }
        final byte[] abyte = new byte[short1];
        datainputstream.readFully(abyte);
        return NBTCompressedStreamTools.a(abyte);
    }
    
    protected static void a(final NBTTagCompound nbttagcompound, final DataOutputStream dataoutputstream) throws IOException {
        if (nbttagcompound == null) {
            dataoutputstream.writeShort(-1);
        }
        else {
            final byte[] abyte = NBTCompressedStreamTools.a(nbttagcompound);
            dataoutputstream.writeShort((short)abyte.length);
            dataoutputstream.write(abyte);
        }
    }
    
    static {
        Packet.l = new IntHashMap();
        Packet.a = new HashMap();
        Packet.b = new HashSet();
        Packet.c = new HashSet();
        a(0, true, true, Packet0KeepAlive.class);
        a(1, true, true, Packet1Login.class);
        a(2, false, true, Packet2Handshake.class);
        a(3, true, true, Packet3Chat.class);
        a(4, true, false, Packet4UpdateTime.class);
        a(5, true, false, Packet5EntityEquipment.class);
        a(6, true, false, Packet6SpawnPosition.class);
        a(7, false, true, Packet7UseEntity.class);
        a(8, true, false, Packet8UpdateHealth.class);
        a(9, true, true, Packet9Respawn.class);
        a(10, true, true, Packet10Flying.class);
        a(11, true, true, Packet11PlayerPosition.class);
        a(12, true, true, Packet12PlayerLook.class);
        a(13, true, true, Packet13PlayerLookMove.class);
        a(14, false, true, Packet14BlockDig.class);
        a(15, false, true, Packet15Place.class);
        a(16, true, true, Packet16BlockItemSwitch.class);
        a(17, true, false, Packet17EntityLocationAction.class);
        a(18, true, true, Packet18ArmAnimation.class);
        a(19, false, true, Packet19EntityAction.class);
        a(20, true, false, Packet20NamedEntitySpawn.class);
        a(22, true, false, Packet22Collect.class);
        a(23, true, false, Packet23VehicleSpawn.class);
        a(24, true, false, Packet24MobSpawn.class);
        a(25, true, false, Packet25EntityPainting.class);
        a(26, true, false, Packet26AddExpOrb.class);
        a(28, true, false, Packet28EntityVelocity.class);
        a(29, true, false, Packet29DestroyEntity.class);
        a(30, true, false, Packet30Entity.class);
        a(31, true, false, Packet31RelEntityMove.class);
        a(32, true, false, Packet32EntityLook.class);
        a(33, true, false, Packet33RelEntityMoveLook.class);
        a(34, true, false, Packet34EntityTeleport.class);
        a(35, true, false, Packet35EntityHeadRotation.class);
        a(38, true, false, Packet38EntityStatus.class);
        a(39, true, false, Packet39AttachEntity.class);
        a(40, true, false, Packet40EntityMetadata.class);
        a(41, true, false, Packet41MobEffect.class);
        a(42, true, false, Packet42RemoveMobEffect.class);
        a(43, true, false, Packet43SetExperience.class);
        a(51, true, false, Packet51MapChunk.class);
        a(52, true, false, Packet52MultiBlockChange.class);
        a(53, true, false, Packet53BlockChange.class);
        a(54, true, false, Packet54PlayNoteBlock.class);
        a(55, true, false, Packet55BlockBreakAnimation.class);
        a(56, true, false, Packet56MapChunkBulk.class);
        a(60, true, false, Packet60Explosion.class);
        a(61, true, false, Packet61WorldEvent.class);
        a(62, true, false, Packet62NamedSoundEffect.class);
        a(63, true, false, Packet63WorldParticles.class);
        a(70, true, false, Packet70Bed.class);
        a(71, true, false, Packet71Weather.class);
        a(100, true, false, Packet100OpenWindow.class);
        a(101, true, true, Packet101CloseWindow.class);
        a(102, false, true, Packet102WindowClick.class);
        a(103, true, false, Packet103SetSlot.class);
        a(104, true, false, Packet104WindowItems.class);
        a(105, true, false, Packet105CraftProgressBar.class);
        a(106, true, true, Packet106Transaction.class);
        a(107, true, true, Packet107SetCreativeSlot.class);
        a(108, false, true, Packet108ButtonClick.class);
        a(130, true, true, Packet130UpdateSign.class);
        a(131, true, false, Packet131ItemData.class);
        a(132, true, false, Packet132TileEntityData.class);
        a(200, true, false, Packet200Statistic.class);
        a(201, true, false, Packet201PlayerInfo.class);
        a(202, true, true, Packet202Abilities.class);
        a(203, true, true, Packet203TabComplete.class);
        a(204, false, true, Packet204LocaleAndViewDistance.class);
        a(205, false, true, Packet205ClientCommand.class);
        a(206, true, false, Packet206SetScoreboardObjective.class);
        a(207, true, false, Packet207SetScoreboardScore.class);
        a(208, true, false, Packet208SetScoreboardDisplayObjective.class);
        a(209, true, false, Packet209SetScoreboardTeam.class);
        a(250, true, true, Packet250CustomPayload.class);
        a(252, true, true, Packet252KeyResponse.class);
        a(253, true, false, Packet253KeyRequest.class);
        a(254, false, true, Packet254GetInfo.class);
        a(255, true, true, Packet255KickDisconnect.class);
    }
}
