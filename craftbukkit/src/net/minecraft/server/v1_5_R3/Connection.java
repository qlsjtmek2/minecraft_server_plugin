// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class Connection
{
    public abstract boolean a();
    
    public void a(final Packet51MapChunk packet51MapChunk) {
    }
    
    public void onUnhandledPacket(final Packet packet) {
    }
    
    public void a(final String s, final Object[] array) {
    }
    
    public void a(final Packet255KickDisconnect packet255KickDisconnect) {
        this.onUnhandledPacket(packet255KickDisconnect);
    }
    
    public void a(final Packet1Login packet1Login) {
        this.onUnhandledPacket(packet1Login);
    }
    
    public void a(final Packet10Flying packet10Flying) {
        this.onUnhandledPacket(packet10Flying);
    }
    
    public void a(final Packet52MultiBlockChange packet52MultiBlockChange) {
        this.onUnhandledPacket(packet52MultiBlockChange);
    }
    
    public void a(final Packet14BlockDig packet14BlockDig) {
        this.onUnhandledPacket(packet14BlockDig);
    }
    
    public void a(final Packet53BlockChange packet53BlockChange) {
        this.onUnhandledPacket(packet53BlockChange);
    }
    
    public void a(final Packet20NamedEntitySpawn packet20NamedEntitySpawn) {
        this.onUnhandledPacket(packet20NamedEntitySpawn);
    }
    
    public void a(final Packet30Entity packet30Entity) {
        this.onUnhandledPacket(packet30Entity);
    }
    
    public void a(final Packet34EntityTeleport packet34EntityTeleport) {
        this.onUnhandledPacket(packet34EntityTeleport);
    }
    
    public void a(final Packet15Place packet15Place) {
        this.onUnhandledPacket(packet15Place);
    }
    
    public void a(final Packet16BlockItemSwitch packet16BlockItemSwitch) {
        this.onUnhandledPacket(packet16BlockItemSwitch);
    }
    
    public void a(final Packet29DestroyEntity packet29DestroyEntity) {
        this.onUnhandledPacket(packet29DestroyEntity);
    }
    
    public void a(final Packet22Collect packet22Collect) {
        this.onUnhandledPacket(packet22Collect);
    }
    
    public void a(final Packet3Chat packet3Chat) {
        this.onUnhandledPacket(packet3Chat);
    }
    
    public void a(final Packet23VehicleSpawn packet23VehicleSpawn) {
        this.onUnhandledPacket(packet23VehicleSpawn);
    }
    
    public void a(final Packet18ArmAnimation packet18ArmAnimation) {
        this.onUnhandledPacket(packet18ArmAnimation);
    }
    
    public void a(final Packet19EntityAction packet19EntityAction) {
        this.onUnhandledPacket(packet19EntityAction);
    }
    
    public void a(final Packet2Handshake packet2Handshake) {
        this.onUnhandledPacket(packet2Handshake);
    }
    
    public void a(final Packet253KeyRequest packet253KeyRequest) {
        this.onUnhandledPacket(packet253KeyRequest);
    }
    
    public void a(final Packet252KeyResponse packet252KeyResponse) {
        this.onUnhandledPacket(packet252KeyResponse);
    }
    
    public void a(final Packet24MobSpawn packet24MobSpawn) {
        this.onUnhandledPacket(packet24MobSpawn);
    }
    
    public void a(final Packet4UpdateTime packet4UpdateTime) {
        this.onUnhandledPacket(packet4UpdateTime);
    }
    
    public void a(final Packet6SpawnPosition packet6SpawnPosition) {
        this.onUnhandledPacket(packet6SpawnPosition);
    }
    
    public void a(final Packet28EntityVelocity packet28EntityVelocity) {
        this.onUnhandledPacket(packet28EntityVelocity);
    }
    
    public void a(final Packet40EntityMetadata packet40EntityMetadata) {
        this.onUnhandledPacket(packet40EntityMetadata);
    }
    
    public void a(final Packet39AttachEntity packet39AttachEntity) {
        this.onUnhandledPacket(packet39AttachEntity);
    }
    
    public void a(final Packet7UseEntity packet7UseEntity) {
        this.onUnhandledPacket(packet7UseEntity);
    }
    
    public void a(final Packet38EntityStatus packet38EntityStatus) {
        this.onUnhandledPacket(packet38EntityStatus);
    }
    
    public void a(final Packet8UpdateHealth packet8UpdateHealth) {
        this.onUnhandledPacket(packet8UpdateHealth);
    }
    
    public void a(final Packet9Respawn packet9Respawn) {
        this.onUnhandledPacket(packet9Respawn);
    }
    
    public void a(final Packet60Explosion packet60Explosion) {
        this.onUnhandledPacket(packet60Explosion);
    }
    
    public void a(final Packet100OpenWindow packet100OpenWindow) {
        this.onUnhandledPacket(packet100OpenWindow);
    }
    
    public void handleContainerClose(final Packet101CloseWindow packet101CloseWindow) {
        this.onUnhandledPacket(packet101CloseWindow);
    }
    
    public void a(final Packet102WindowClick packet102WindowClick) {
        this.onUnhandledPacket(packet102WindowClick);
    }
    
    public void a(final Packet103SetSlot packet103SetSlot) {
        this.onUnhandledPacket(packet103SetSlot);
    }
    
    public void a(final Packet104WindowItems packet104WindowItems) {
        this.onUnhandledPacket(packet104WindowItems);
    }
    
    public void a(final Packet130UpdateSign packet130UpdateSign) {
        this.onUnhandledPacket(packet130UpdateSign);
    }
    
    public void a(final Packet105CraftProgressBar packet105CraftProgressBar) {
        this.onUnhandledPacket(packet105CraftProgressBar);
    }
    
    public void a(final Packet5EntityEquipment packet5EntityEquipment) {
        this.onUnhandledPacket(packet5EntityEquipment);
    }
    
    public void a(final Packet106Transaction packet106Transaction) {
        this.onUnhandledPacket(packet106Transaction);
    }
    
    public void a(final Packet25EntityPainting packet25EntityPainting) {
        this.onUnhandledPacket(packet25EntityPainting);
    }
    
    public void a(final Packet54PlayNoteBlock packet54PlayNoteBlock) {
        this.onUnhandledPacket(packet54PlayNoteBlock);
    }
    
    public void a(final Packet200Statistic packet200Statistic) {
        this.onUnhandledPacket(packet200Statistic);
    }
    
    public void a(final Packet17EntityLocationAction packet17EntityLocationAction) {
        this.onUnhandledPacket(packet17EntityLocationAction);
    }
    
    public void a(final Packet70Bed packet70Bed) {
        this.onUnhandledPacket(packet70Bed);
    }
    
    public void a(final Packet71Weather packet71Weather) {
        this.onUnhandledPacket(packet71Weather);
    }
    
    public void a(final Packet131ItemData packet131ItemData) {
        this.onUnhandledPacket(packet131ItemData);
    }
    
    public void a(final Packet61WorldEvent packet61WorldEvent) {
        this.onUnhandledPacket(packet61WorldEvent);
    }
    
    public void a(final Packet254GetInfo packet254GetInfo) {
        this.onUnhandledPacket(packet254GetInfo);
    }
    
    public void a(final Packet41MobEffect packet41MobEffect) {
        this.onUnhandledPacket(packet41MobEffect);
    }
    
    public void a(final Packet42RemoveMobEffect packet42RemoveMobEffect) {
        this.onUnhandledPacket(packet42RemoveMobEffect);
    }
    
    public void a(final Packet201PlayerInfo packet201PlayerInfo) {
        this.onUnhandledPacket(packet201PlayerInfo);
    }
    
    public void a(final Packet0KeepAlive packet0KeepAlive) {
        this.onUnhandledPacket(packet0KeepAlive);
    }
    
    public void a(final Packet43SetExperience packet43SetExperience) {
        this.onUnhandledPacket(packet43SetExperience);
    }
    
    public void a(final Packet107SetCreativeSlot packet107SetCreativeSlot) {
        this.onUnhandledPacket(packet107SetCreativeSlot);
    }
    
    public void a(final Packet26AddExpOrb packet26AddExpOrb) {
        this.onUnhandledPacket(packet26AddExpOrb);
    }
    
    public void a(final Packet108ButtonClick packet108ButtonClick) {
    }
    
    public void a(final Packet250CustomPayload packet250CustomPayload) {
    }
    
    public void a(final Packet35EntityHeadRotation packet35EntityHeadRotation) {
        this.onUnhandledPacket(packet35EntityHeadRotation);
    }
    
    public void a(final Packet132TileEntityData packet132TileEntityData) {
        this.onUnhandledPacket(packet132TileEntityData);
    }
    
    public void a(final Packet202Abilities packet202Abilities) {
        this.onUnhandledPacket(packet202Abilities);
    }
    
    public void a(final Packet203TabComplete packet203TabComplete) {
        this.onUnhandledPacket(packet203TabComplete);
    }
    
    public void a(final Packet204LocaleAndViewDistance packet204LocaleAndViewDistance) {
        this.onUnhandledPacket(packet204LocaleAndViewDistance);
    }
    
    public void a(final Packet62NamedSoundEffect packet62NamedSoundEffect) {
        this.onUnhandledPacket(packet62NamedSoundEffect);
    }
    
    public void a(final Packet55BlockBreakAnimation packet55BlockBreakAnimation) {
        this.onUnhandledPacket(packet55BlockBreakAnimation);
    }
    
    public void a(final Packet205ClientCommand packet205ClientCommand) {
    }
    
    public void a(final Packet56MapChunkBulk packet56MapChunkBulk) {
        this.onUnhandledPacket(packet56MapChunkBulk);
    }
    
    public boolean b() {
        return false;
    }
    
    public void a(final Packet206SetScoreboardObjective packet206SetScoreboardObjective) {
        this.onUnhandledPacket(packet206SetScoreboardObjective);
    }
    
    public void a(final Packet207SetScoreboardScore packet207SetScoreboardScore) {
        this.onUnhandledPacket(packet207SetScoreboardScore);
    }
    
    public void a(final Packet208SetScoreboardDisplayObjective packet208SetScoreboardDisplayObjective) {
        this.onUnhandledPacket(packet208SetScoreboardDisplayObjective);
    }
    
    public void a(final Packet209SetScoreboardTeam packet209SetScoreboardTeam) {
        this.onUnhandledPacket(packet209SetScoreboardTeam);
    }
    
    public void a(final Packet63WorldParticles packet63WorldParticles) {
        this.onUnhandledPacket(packet63WorldParticles);
    }
}
