package me.espoo.og;

import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.PluginManager;
import me.espoo.main.main;

public class Listeners implements Listener {
	private main _og;
	private final BlockFace[] faces = { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST,
			BlockFace.SOUTH, BlockFace.WEST };

	public Listeners(main og) {
		this._og = og;
		this._og.getServer().getPluginManager().registerEvents(this, og);
	}

	@EventHandler
	public void onFromTo(BlockFromToEvent event) {
		int id = event.getBlock().getTypeId();
		if ((id >= 8) && (id <= 11)) {
			Block b = event.getToBlock();
			int toid = b.getTypeId();
			if ((toid == 0) && (generatesCobble(id, b))) {
				List worlds = this._og.getConfig().getStringList("World");
				if (worlds.contains(event.getBlock().getLocation().getWorld().getName()))
					for (Entity entity : b.getChunk().getEntities())
						if ((entity instanceof Player))
							if (((Player) entity).hasPermission("og.gen")) {
								Random pick = new Random();
								float chance = pick.nextFloat() * 100.0F;

								if ((chance > 0.0F) && (chance <= this._og.stone))
									b.setType(Material.STONE);
								else if ((chance > this._og.stone) && (chance <= this._og.coal))
									b.setType(Material.COAL_ORE);
								else if ((chance > this._og.coal) && (chance <= this._og.iron))
									b.setType(Material.IRON_ORE);
								else if ((chance > this._og.iron) && (chance <= this._og.gold))
									b.setType(Material.GOLD_ORE);
								else if ((chance > this._og.gold) && (chance <= this._og.redstone))
									b.setType(Material.REDSTONE_ORE);
								else if ((chance > this._og.redstone) && (chance <= this._og.lapis))
									b.setType(Material.LAPIS_ORE);
								else if ((chance > this._og.lapis) && (chance <= this._og.emerald))
									b.setType(Material.EMERALD_ORE);
								else if ((chance > this._og.emerald) && (chance <= this._og.diamond))
									b.setType(Material.DIAMOND_ORE);
								else if ((chance > this._og.diamond) && (chance <= 100.0F))
									b.setType(Material.COBBLESTONE);
							} else {
								b.setType(Material.COBBLESTONE);
							}
			}
		}
	}

	public boolean generatesCobble(int id, Block b) {
		int mirrorID1 = (id == 8) || (id == 9) ? 10 : 8;
		int mirrorID2 = (id == 8) || (id == 9) ? 11 : 9;
		for (BlockFace face : this.faces) {
			Block r = b.getRelative(face, 1);
			if ((r.getTypeId() == mirrorID1) || (r.getTypeId() == mirrorID2))
				return true;
		}
		return false;
	}
}