package me.espoo.NameSings;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;

public class PacketUpdateSignWrapper {
	private final PacketContainer updateSignPacket;

	public PacketUpdateSignWrapper(PacketContainer updateSignPacket) {
		this.updateSignPacket = updateSignPacket;
	}

	public int getX() {
		return ((Integer) this.updateSignPacket.getIntegers().read(0)).intValue();
	}

	public short getY() {
		return ((Integer) this.updateSignPacket.getIntegers().read(1)).shortValue();
	}

	public int getZ() {
		return ((Integer) this.updateSignPacket.getIntegers().read(2)).intValue();
	}

	public void setX(int value) {
		this.updateSignPacket.getIntegers().write(0, Integer.valueOf(value));
	}

	public void setY(short value) {
		this.updateSignPacket.getIntegers().write(1, Integer.valueOf(value));
	}

	public void setZ(int value) {
		this.updateSignPacket.getIntegers().write(2, Integer.valueOf(value));
	}

	public String[] getLines() {
		return (String[]) this.updateSignPacket.getStringArrays().read(0);
	}

	public void setLines(String[] lines) {
		if (lines == null)
			throw new IllegalArgumentException("The lines array cannot be null!.");
		if (lines.length != 4)
			throw new IllegalArgumentException("The lines array must be four elements long.");
		this.updateSignPacket.getStringArrays().write(0, lines);
	}

	public PacketContainer getPacket() {
		return this.updateSignPacket;
	}
}