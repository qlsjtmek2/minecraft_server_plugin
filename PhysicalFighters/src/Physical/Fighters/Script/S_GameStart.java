// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.Script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MajorModule.AbilityList;
import Physical.Fighters.MinerModule.TimerBase;

public final class S_GameStart
{
    private MainScripter ms;
    private S_ScriptTimer stimer;
    public static int PlayDistanceBuffer;
    public static PhysicalFighters M;
	static final Map <String, Integer> taskIds = new HashMap<>();
    
    static {
        S_GameStart.PlayDistanceBuffer = 0;
    }
    
    public S_GameStart(final MainScripter ms) {
        this.stimer = new S_ScriptTimer();
        this.ms = ms;
    }
    
    public void GameStart() {
        this.stimer.StartTimer(15);
    }
    
    public void GameStartStop() {
        this.stimer.StopTimer();
        AbilityBase.restrictionTimer.StopTimer();
    }
    
    private void RespawnTeleport() {
        final Location l = this.ms.gameworld.getSpawnLocation();
        l.setY((double)this.ms.gameworld.getHighestBlockYAt((int)l.getX(), (int)l.getZ()));
        for (final Player p : MainScripter.PlayerList) {
    		if (!MainScripter.ExceptionList.contains(p)) {
                p.setFoodLevel(20);
                p.setExhaustion(0.0f);
                p.setHealth(p.getMaxHealth());
                p.setSaturation(10.0f);
    		}
            if (PhysicalFighters.Specialability) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "��a�α��ִ� �ɷ¸� ����˴ϴ�.");
                PhysicalFighters.Specialability = true;
            }
            if (PhysicalFighters.TableGive) {
                p.getInventory().addItem(new ItemStack[] { new ItemStack(116, 1) });
                p.getInventory().addItem(new ItemStack[] { new ItemStack(47, 64) });
            }
            if (PhysicalFighters.WoodGive) {
                p.getInventory().addItem(new ItemStack[] { new ItemStack(17, 64) });
            }
        }
        if (PhysicalFighters.DefaultArmed) {
        	for (Player player : MainScripter.PlayerList)
        		player.sendMessage(PhysicalFighters.p + "��c�⺻ �����6�� �����˴ϴ�.");
        } else {
        	for (Player player : MainScripter.PlayerList)
        		player.sendMessage(PhysicalFighters.p + "��c�⺻ �����6�� �������� �ʽ��ϴ�.");
        }
        if (PhysicalFighters.MaxLevelSurvival) {
        	for (Player player : MainScripter.PlayerList)
        		player.sendMessage(PhysicalFighters.p + "��c���� �����̹� ����6�Դϴ�. ������ ����.");
        }
    }
    
    public final class S_ScriptTimer extends TimerBase
    {
        @Override
        public void EventStartTimer() {
            MainScripter.Scenario = MainScripter.ScriptStatus.GameStart;
        }
        
        @Override
        public void EventRunningTimer(final int count) {
            switch (count) {
                case 0: {
                    MainScripter.s_GameWarnning.GameWarnningStop();
                    break;
                }
                case 3: {
                	for (Player player : MainScripter.PlayerList) {
                       	player.sendMessage(PhysicalFighters.p + "��� �÷��̾���� �ɷ��� ��cȮ����6�߽��ϴ�.");
                       	player.sendMessage(PhysicalFighters.p + "��c�κ��丮 �� ĭ��6�� ����ֽñ� �ٶ��ϴ�.");
                	}
                    break;
                }
                case 5: {
                	for (Player player : MainScripter.PlayerList)
                		player.sendMessage(PhysicalFighters.p + "��� �� ������ ��c���ۡ�6�˴ϴ�.");
                    break;
                }
                case 10: {
                 	for (Player player : MainScripter.PlayerList)
                 		player.sendMessage(PhysicalFighters.p + "5�� ��");
                    break;
                }
                case 11: {
                 	for (Player player : MainScripter.PlayerList)
                 		player.sendMessage(PhysicalFighters.p + "4�� ��");
                    break;
                }
                case 12: {
                 	for (Player player : MainScripter.PlayerList)
                 		player.sendMessage(PhysicalFighters.p + "3�� ��");
                    break;
                }
                case 13: {
                 	for (Player player : MainScripter.PlayerList)
                 		player.sendMessage(PhysicalFighters.p + "2�� ��");
                    break;
                }
                case 14: {
                 	for (Player player : MainScripter.PlayerList)
                 		player.sendMessage(PhysicalFighters.p + "1�� ��");
                    break;
                }
                case 15: {
                	S_GameReady.skip = true;
                    Bukkit.broadcastMessage(PhysicalFighters.p + "��c�ɷ��� ��6������ ��c���ۡ�6�Ǿ����ϴ�.");
                    EventManager.DamageGuard = false;
                    for (Player player : MainScripter.PlayerList) {
                    	 player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_INGOT, 64) });
                    	 player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_INGOT, 64) });
                    }
                    
                    PhysicalFighters.Time = 8;
            		taskIds.put("Timer", new BukkitRunnable()
            		{
            			public void run()
            			{
            				PhysicalFighters.Time -= 1;
                			if (PhysicalFighters.Time == 5) {
                	            for (Player player : MainScripter.PlayerList) {
                	            	player.sendMessage(PhysicalFighters.p + "��6=-=- ��c�÷��̾� ��ġ ��6-=-=");
                	            	player.sendMessage(PhysicalFighters.p + "��6��l��������������������������������������");
                	                final List<AbilityBase> pl = AbilityList.AbilityList;
                	                int count = 0;
                	                for (int l = 0; l < pl.size(); ++l) {
                	                    if (pl.get(l).GetPlayer() != null) {
                	                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                	                        final AbilityBase tempab = pl.get(l);
                	                        if (temp != null && temp.getWorld().getName().equals("world_pvp")) {
                	                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                	                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                	                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                	                            player.sendMessage(String.format(PhysicalFighters.p + "��a%d. ��f%s : ��cX ��f%d, ��cY ��f%d, ��cZ ��f%d", count, tempab.GetPlayer().getName(), x, y, z));
                	                            ++count;
                	                        }
                	                    }
                	                }
                	                player.sendMessage(PhysicalFighters.p + "��6��l��������������������������������������");
                	                player.sendMessage(PhysicalFighters.p + "��6���� ��c���� ���� ��6�ð��� ��c5�� ��6���ҽ��ϴ�.");
                	            }
                			}
                			
            	 			else if (PhysicalFighters.Time == 4) {
                	            for (Player player : MainScripter.PlayerList) {
                	            	player.sendMessage(PhysicalFighters.a + "��6=-=- ��c�÷��̾� ��ġ ��6-=-=");
                	            	player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                final List<AbilityBase> pl = AbilityList.AbilityList;
                	                int count = 0;
                	                for (int l = 0; l < pl.size(); ++l) {
                	                    if (pl.get(l).GetPlayer() != null) {
                	                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                	                        final AbilityBase tempab = pl.get(l);
                	                        if (temp != null && temp.getWorld().getName().equals("world_pvp")) {
                	                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                	                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                	                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                	                            player.sendMessage(String.format(PhysicalFighters.a + "��a%d. ��f%s : ��cX ��f%d, ��cY ��f%d, ��cZ ��f%d", count, tempab.GetPlayer().getName(), x, y, z));
                	                            ++count;
                	                        }
                	                    }
                	                }
                	                player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                player.sendMessage(PhysicalFighters.p + "��6���� ��c���� ���� ��6�ð��� ��c4�� ��6���ҽ��ϴ�.");
                	            }
            	 			}
                			
                			else if (PhysicalFighters.Time == 3) {
                	            for (Player player : MainScripter.PlayerList) {
                	            	player.sendMessage(PhysicalFighters.a + "��6=-=- ��c�÷��̾� ��ġ ��6-=-=");
                	            	player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                final List<AbilityBase> pl = AbilityList.AbilityList;
                	                int count = 0;
                	                for (int l = 0; l < pl.size(); ++l) {
                	                    if (pl.get(l).GetPlayer() != null) {
                	                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                	                        final AbilityBase tempab = pl.get(l);
                	                        if (temp != null && temp.getWorld().getName().equals("world_pvp")) {
                	                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                	                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                	                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                	                            player.sendMessage(String.format(PhysicalFighters.a + "��a%d. ��f%s : ��cX ��f%d, ��cY ��f%d, ��cZ ��f%d", count, tempab.GetPlayer().getName(), x, y, z));
                	                            ++count;
                	                        }
                	                    }
                	                }
                	                player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                player.sendMessage(PhysicalFighters.p + "��6���� ��c���� ���� ��6�ð��� ��c3�� ��6���ҽ��ϴ�.");
                	            }
                			}
                			
                			else if (PhysicalFighters.Time == 2) {
                	            for (Player player : MainScripter.PlayerList) {
                	            	player.sendMessage(PhysicalFighters.a + "��6=-=- ��c�÷��̾� ��ġ ��6-=-=");
                	            	player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                final List<AbilityBase> pl = AbilityList.AbilityList;
                	                int count = 0;
                	                for (int l = 0; l < pl.size(); ++l) {
                	                    if (pl.get(l).GetPlayer() != null) {
                	                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                	                        final AbilityBase tempab = pl.get(l);
                	                        if (temp != null && temp.getWorld().getName().equals("world_pvp")) {
                	                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                	                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                	                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                	                            player.sendMessage(String.format(PhysicalFighters.a + "��a%d. ��f%s : ��cX ��f%d, ��cY ��f%d, ��cZ ��f%d", count, tempab.GetPlayer().getName(), x, y, z));
                	                            ++count;
                	                        }
                	                    }
                	                }
                	                player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                player.sendMessage(PhysicalFighters.p + "��6���� ��c���� ���� ��6�ð��� ��c2�� ��6���ҽ��ϴ�.");
                	            }
                			}
                			
                			else if (PhysicalFighters.Time == 1) {
                	            for (Player player : MainScripter.PlayerList) {
                	            	player.sendMessage(PhysicalFighters.a + "��6=-=- ��c�÷��̾� ��ġ ��6-=-=");
                	            	player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                final List<AbilityBase> pl = AbilityList.AbilityList;
                	                int count = 0;
                	                for (int l = 0; l < pl.size(); ++l) {
                	                    if (pl.get(l).GetPlayer() != null) {
                	                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                	                        final AbilityBase tempab = pl.get(l);
                	                        if (temp != null && temp.getWorld().getName().equals("world_pvp")) {
                	                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                	                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                	                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                	                            player.sendMessage(String.format(PhysicalFighters.a + "��a%d. ��f%s : ��cX ��f%d, ��cY ��f%d, ��cZ ��f%d", count, tempab.GetPlayer().getName(), x, y, z));
                	                            ++count;
                	                        }
                	                    }
                	                }
                	                player.sendMessage(PhysicalFighters.a + "��6��l��������������������������������������");
                	                player.sendMessage(PhysicalFighters.p + "��6���� ��c���� ���� ��6�ð��� ��c1�� ��6���ҽ��ϴ�.");
                	            }
                			}
                			
                   			else if (PhysicalFighters.Time <= 0) {
                				MainScripter.vacstop("�ð��� ��c�ʰ���6�Ǿ� ");
                				return;
                			}
            			}
            		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("PhysicalFighters"), 1200L, 1200L).getTaskId());
            		
                    int c = 0;
                    PhysicalFighters.log.info("�÷��̾���� �ɷ�");
                    for (final AbilityBase a : AbilityList.AbilityList) {
                        if (a.GetPlayer() != null) {
                            PhysicalFighters.log.info(String.format("%d. %s - %s", c, a.GetPlayer().getName(), a.GetAbilityName()));
                            ++c;
                        }
                    }
                    PhysicalFighters.log.info("-------------------------");
                    if (PhysicalFighters.Invincibility) {
                    	for (Player player : MainScripter.PlayerList)
                    		player.sendMessage(PhysicalFighters.p + "���� ���� ��c" + String.valueOf(PhysicalFighters.EarlyInvincibleTime) + " ��6�а��� ��c������6�Դϴ�.");
                        EventManager.DamageGuard = !PhysicalFighters.DebugMode;
                    } else {
                     	for (Player player : MainScripter.PlayerList)
                     		player.sendMessage(PhysicalFighters.p + "��c�ʹ� ������6�� �۵����� �ʽ��ϴ�.");
                    }
                    if (PhysicalFighters.RestrictionTime != 0) {
                        AbilityBase.restrictionTimer.StartTimer(PhysicalFighters.RestrictionTime * 60);
                    } else {
                     	for (Player player : MainScripter.PlayerList)
                     		player.sendMessage(PhysicalFighters.p + "��c���� ī��Ʈ��6�� �������� �ʽ��ϴ�.");
                    }
                    S_GameStart.this.RespawnTeleport();
                    S_GameStart.PlayDistanceBuffer = MainScripter.PlayerList.size() * 50;
                    final List<World> w = (List<World>)Bukkit.getWorlds();
                    for (final World wl : w) {
                        wl.setTime(1L);
                        wl.setStorm(false);
                        if (PhysicalFighters.AutoDifficultySetting) {
                            wl.setDifficulty(Difficulty.EASY);
                        }
                        wl.setWeatherDuration(0);
                        wl.setSpawnFlags(wl.getAllowMonsters(), !PhysicalFighters.NoAnimal);
                        wl.setPVP(true);
                    }
                    for (final AbilityBase b : AbilityList.AbilityList) {
                        b.SetRunAbility(true);
                        b.SetPlayer(b.GetPlayer(), false);
                    }
                    MainScripter.s_GameProgress.GameProgress();
                    if (PhysicalFighters.AutoCoordinateOutput) {}
                    break;
                }
            }
        }
        
        @Override
        public void EventEndTimer() {
        }
    }
}