package me.espoo.FarmAssist;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import me.espoo.main.main;

public class FarmAssistReplant implements Runnable {
	public final main plugin;
	public Block block;
	public int type;

	public FarmAssistReplant(main instance, Block importBlock, int importType) {
		this.plugin = instance;
		this.block = importBlock;
		this.type = importType;
	}

	public void run() {
		if (this.type == 1) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 60) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(59);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(295));
			}
		} else if (this.type == 2) {
			if (((this.block.getRelative(BlockFace.DOWN).getTypeId() == 2)
					|| (this.block.getRelative(BlockFace.DOWN).getTypeId() == 3)
					|| (this.block.getRelative(BlockFace.DOWN).getTypeId() == 12)) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(83);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(338));
			}
		} else if (this.type == 3) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 88) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(115);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(372));
			}
		} else if (this.type == 4) {
			if (this.block.getTypeId() == 0) {
				if ((this.block.getRelative(BlockFace.NORTH).getTypeId() == 17)
						&& (this.block.getRelative(BlockFace.NORTH).getData() == 3)) {
					this.block.setTypeId(127);
					this.block.setData((byte) 2);
				} else if ((this.block.getRelative(BlockFace.SOUTH).getTypeId() == 17)
						&& (this.block.getRelative(BlockFace.SOUTH).getData() == 3)) {
					this.block.setTypeId(127);
					this.block.setData((byte) 0);
				} else if ((this.block.getRelative(BlockFace.EAST).getTypeId() == 17)
						&& (this.block.getRelative(BlockFace.EAST).getData() == 3)) {
					this.block.setTypeId(127);
					this.block.setData((byte) 3);
				} else if ((this.block.getRelative(BlockFace.WEST).getTypeId() == 17)
						&& (this.block.getRelative(BlockFace.WEST).getData() == 3)) {
					this.block.setTypeId(127);
					this.block.setData((byte) 1);
				}

			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(351, 1, (short) 3));
			}
		} else if (this.type == 5) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 60) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(141);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(391));
			}
		} else if (this.type == 6) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 60) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(142);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(392));
			}
		} else if (this.type == 7) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 60) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(104);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(361));
			}
		} else if (this.type == 8) {
			if ((this.block.getRelative(BlockFace.DOWN).getTypeId() == 60) && (this.block.getTypeId() == 0)) {
				this.block.setTypeId(105);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(362));
			}
		} else if (this.type == 9)
			if ((this.block.getRelative(BlockFace.UP).getTypeId() == 0) && (this.block.getTypeId() == 60)) {
				this.block.getRelative(BlockFace.UP).setTypeId(59);
			} else {
				this.block.getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(295));
			}
	}
}