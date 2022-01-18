// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.Script;

import java.util.Random;
import java.util.List;
import Physical.Fighters.MainModule.EventManager;
import org.bukkit.Bukkit;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import Physical.Fighters.MinerModule.AUC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import Physical.Fighters.MainModule.CommandManager;
import org.bukkit.World;
import Physical.Fighters.PhysicalFighters;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.LinkedList;
import Physical.Fighters.MinerModule.CommandInterface;

public class MainScripter implements CommandInterface
{
    public static ScriptStatus Scenario;
    public static LinkedList<Player> ExceptionList;
    public static ArrayList<Player> PlayerList;
    public static ArrayList<Player> OKSign;
    public PhysicalFighters va;
    public World gameworld;
    public static S_GameReady s_GameReady;
    public static S_GameStart s_GameStart;
    public static S_GameProgress s_GameProgress;
    public static S_GameWarnning s_GameWarnning;
    public static PhysicalFighters M;
    
    static {
        MainScripter.Scenario = ScriptStatus.NoPlay;
        MainScripter.PlayerList = new ArrayList<Player>();
    }
    
    public MainScripter(final PhysicalFighters va, final CommandManager cm) {
        MainScripter.ExceptionList = new LinkedList<Player>();
        MainScripter.OKSign = new ArrayList<Player>();
        this.va = va;
        cm.RegisterCommand(this);
        MainScripter.s_GameReady = new S_GameReady(this);
        MainScripter.s_GameStart = new S_GameStart(this);
        MainScripter.s_GameProgress = new S_GameProgress(this);
        MainScripter.s_GameWarnning = new S_GameWarnning(this);
    }
    
    @Override
    public boolean onCommandEvent(CommandSender sender, Command command, String label, String[] data) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (data[0].equalsIgnoreCase("help")) {
                AUC.InfoTextOut(p);
            }
            else if (data[0].equalsIgnoreCase("start")) {
                this.gameworld = Bukkit.getWorld("world_pvp");
                MainScripter.s_GameReady.GameReady(sender);
            }
            else if (data[0].equalsIgnoreCase("ob")) {
                this.vaob(p);
            }
            else if (data[0].equalsIgnoreCase("yes")) {
                this.vayes(p);
            }
            else if (data[0].equalsIgnoreCase("no")) {
                this.vano(p);
            }
            else if (data[0].equalsIgnoreCase("stop")) {
                this.vastop((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("alist")) {
                this.vaalist((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("elist")) {
                this.vaelist((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("tc")) {
                this.vatc((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("kill")) {
                this.vakill((CommandSender)p, data);
            }
            else if (data[0].equalsIgnoreCase("debug")) {
                this.vadebug((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("skip")) {
                this.vaskip((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("maker")) {
                this.vamaker((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("uti")) {
                this.vauti((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("abi")) {
                this.vaabi(p, data);
            }
            else if (data[0].equalsIgnoreCase("ablist")) {
                this.vaablist((CommandSender)p, data);
            }
            else if (data[0].equalsIgnoreCase("go")) {
                this.vago((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("inv")) {
                this.vainv((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("hung")) {
                this.vahungry((CommandSender)p);
            }
            else if (data[0].equalsIgnoreCase("dura")) {
                this.vadura((CommandSender)p);
            }
            return true;
        } else {
            if (data[0].equalsIgnoreCase("help")) {
    			sender.sendMessage(PhysicalFighters.w + "�ֿܼ��� ������ �Ұ��� �մϴ�.");
            }
            else if (data[0].equalsIgnoreCase("ob")) {
    			sender.sendMessage(PhysicalFighters.w + "�ֿܼ��� ������ �Ұ��� �մϴ�.");
            }
            else if (data[0].equalsIgnoreCase("yes")) {
    			sender.sendMessage(PhysicalFighters.w + "�ֿܼ��� ������ �Ұ��� �մϴ�.");
            }
            else if (data[0].equalsIgnoreCase("no")) {
    			sender.sendMessage(PhysicalFighters.w + "�ֿܼ��� ������ �Ұ��� �մϴ�.");
            }
            else if (data[0].equalsIgnoreCase("abi")) {
    			sender.sendMessage(PhysicalFighters.w + "�ֿܼ��� ������ �Ұ��� �մϴ�.");
            }
            else if (data[0].equalsIgnoreCase("start")) {
                this.gameworld = Bukkit.getWorld("world_pvp");
                MainScripter.s_GameReady.GameReady(sender);
            }
            else if (data[0].equalsIgnoreCase("stop")) {
                this.vastop(sender);
            }
            else if (data[0].equalsIgnoreCase("alist")) {
                this.vaalist(sender);
            }
            else if (data[0].equalsIgnoreCase("elist")) {
                this.vaelist(sender);
            }
            else if (data[0].equalsIgnoreCase("tc")) {
                this.vatc(sender);
            }
            else if (data[0].equalsIgnoreCase("kill")) {
                this.vakill(sender, data);
            }
            else if (data[0].equalsIgnoreCase("debug")) {
                this.vadebug(sender);
            }
            else if (data[0].equalsIgnoreCase("skip")) {
                this.vaskip(sender);
            }
            else if (data[0].equalsIgnoreCase("uti")) {
                this.vauti(sender);
            }
            else if (data[0].equalsIgnoreCase("maker")) {
                this.vamaker(sender);
            }
            else if (data[0].equalsIgnoreCase("ablist")) {
                this.vaablist(sender, data);
            }
            else if (data[0].equalsIgnoreCase("inv")) {
                this.vainv(sender);
            }
            else if (data[0].equalsIgnoreCase("go")) {
                this.vago(sender);
            } return true;
        }
    }
    
    public final void vaablist(final CommandSender p, final String[] d) {
        if (p.isOp()) {
            int page = 0;
            if (d.length == 2) {
                try {
                	if (PhysicalFighters.isStringDouble(d[1])) {
                        page = Integer.valueOf(d[1]);
                        if (page >= 0 && page <= 8) {
                       		p.sendMessage(" ��e---- ��6�ɷ� ��� ��e-- ��6������ ��c0��6~��c8 ��e----");
                            for (int code = page * 8; code < (page + 1) * 8; ++code) {
                                if (code < AbilityList.AbilityList.size()) {
                                    final AbilityBase a = AbilityList.AbilityList.get(code);
                                    p.sendMessage(String.format(ChatColor.GREEN + "[%d] " + ChatColor.WHITE + "%s", code, a.GetAbilityName()));
                                }
                            }
                    		p.sendMessage("");
                    		p.sendMessage("��6��ɾ� ��cva ablist [0~8] ��6�� �ļ� ������������ �Ѿ����.");
                            return;
                        }
                	} else {
                        p.sendMessage(PhysicalFighters.w + "�������� �ùٸ��� �ʽ��ϴ�.");
                        return;
                	}
                } catch (NumberFormatException e) {
                    p.sendMessage(PhysicalFighters.w + "�������� �ùٸ��� �ʽ��ϴ�.");
                    return;
                }
            }
            p.sendMessage(ChatColor.GOLD + "/va ablist [0~8] " + ChatColor.WHITE + "- �ɷ� ����� ���ϴ�.");
        }
    }
    
    public final void vaabi(CommandSender p, String[] d)
    {
    	if (p.isOp())
    		if (d.length == 3) {
    			Player pn = Bukkit.getServer().getPlayerExact(d[1]);
    			if ((pn != null) || (d[1].equalsIgnoreCase("null"))) {
    				int abicode = 0;
    				try {
    					abicode = Integer.valueOf(d[2]).intValue();
    					if (abicode < 0) {
        					p.sendMessage(PhysicalFighters.w + "�ɷ� �ڵ尡 �ùٸ��� �ʽ��ϴ�.");
        					return;
    					}
    				} catch (NumberFormatException e) {
    					p.sendMessage(PhysicalFighters.w + "�ɷ� �ڵ尡 �ùٸ��� �ʽ��ϴ�.");
    					return;
    				}
    				if (abicode == -1) {
    					for (AbilityBase ab : AbilityList.AbilityList) {
    						if (ab.PlayerCheck(pn)) {
    							ab.SetPlayer(null, true);
    						}
    					}
    					pn.sendMessage(PhysicalFighters.a + "����� �ɷ��� ��� ��c������f�Ǿ����ϴ�.");
    					p.sendMessage(String.format(PhysicalFighters.a + "��c%s" + ChatColor.WHITE + "��f���� �ɷ��� ��� ��c������f�߽��ϴ�.", new Object[] { pn.getName() }));
    		            for (Player player : MainScripter.PlayerList)
    		            	player.sendMessage(String.format(PhysicalFighters.p + "��c%s ��6���� �������� �ɷ��� ��� ��c������6�߽��ϴ�.", new Object[] { p.getName() }));
    					return;
    				}
    				if ((abicode >= 0) && (abicode < AbilityList.AbilityList.size())) {
    					AbilityBase a = (AbilityBase)AbilityList.AbilityList.get(abicode);
    					if (d[1].equalsIgnoreCase("null")) {
    						a.SetPlayer(null, true);
    						p.sendMessage(String.format(PhysicalFighters.a + "��c%s ��f�ɷ� �ʱ�ȭ �Ϸ�", new Object[] { a.GetAbilityName() }));
    						return;
    					}

    					if (PhysicalFighters.AbilityOverLap) {
    						if ((a.GetAbilityType() == AbilityBase.Type.Active_Continue) || (a.GetAbilityType() == AbilityBase.Type.Active_Immediately)) {
    							for (AbilityBase ab : AbilityList.AbilityList) {
    								if ((!ab.PlayerCheck(pn)) || (
    										(ab.GetAbilityType() != AbilityBase.Type.Active_Continue) && 
    										(ab.GetAbilityType() != AbilityBase.Type.Active_Immediately))) continue;
    								ab.SetPlayer(null, true);
    							}
    						}
    					} else {
    						for (AbilityBase ab : AbilityList.AbilityList) {
    							if (ab.PlayerCheck(pn)) {
    								ab.SetPlayer(null, true);
    							}
    						}
    					}
    					a.SetPlayer(pn, true);
    					a.SetRunAbility(true);
    					p.sendMessage(String.format(PhysicalFighters.a + ChatColor.RED + "%s " + ChatColor.WHITE + "�Կ��� " + ChatColor.GREEN + "%s " + ChatColor.WHITE + "�ɷ� �Ҵ��� �Ϸ�Ǿ����ϴ�.", new Object[] { pn.getName(), a.GetAbilityName() }));
    		            for (Player player : MainScripter.PlayerList)
    		            	player.sendMessage(String.format(PhysicalFighters.p + "��c%s ��6���� ���������� �ɷ��� ������ ��c�Ҵ��6�߽��ϴ�.", new Object[] { p.getName() }));
    					String s;
    					if ((p instanceof Player))
    						s = p.getName();
    					else
    						s = "���� ������";
    					PhysicalFighters.log.info(String.format("%s���� %s�Կ��� %s �ɷ��� �Ҵ��߽��ϴ�.", new Object[] { s, pn.getName(), 
    									a.GetAbilityName() }));
    				} else {
    					p.sendMessage(ChatColor.RED + "�ɷ� �ڵ尡 �ùٸ��� �ʽ��ϴ�.");
    				}
    			} else {
    				p.sendMessage(ChatColor.RED + "�������� �ʴ� �÷��̾��Դϴ�.");
    			}
    		} else {
    	         p.sendMessage(ChatColor.GOLD + "/va abi <�г���> <�ɷ��ڵ�>" + ChatColor.WHITE + "- �ɷ��� ����, ����, �����մϴ�.");
    		}
    }
    
    public final void vauti(final CommandSender p) {
        if (p.isOp()) {
        	p.sendMessage(" ��e---- ��6���� ��ɾ� ��e-- ��6������ ��c1��6/��c1 ��e----");
            p.sendMessage(ChatColor.GRAY + "��ɾ�� /va [��ɾ�]�� ����մϴ�.");
            p.sendMessage(ChatColor.GREEN + "alist : " + ChatColor.WHITE + 
              "�ɷ��� ����� ���ϴ�. �� ����.");
            p.sendMessage(ChatColor.GREEN + "elist : " + ChatColor.WHITE + 
              "�ɷ� Ȯ���� �ȵ� ������� �����ݴϴ�. �� ����.");
            p.sendMessage(ChatColor.GREEN + "ablist [������(0~2)] : " + 
              ChatColor.WHITE + "�ɷ� ��� �� �ɷ� �ڵ带 �����ݴϴ�. �� ����.");
            p.sendMessage(ChatColor.GREEN + "abi [�г���] [�ɷ� �ڵ�] : " + 
              ChatColor.WHITE + "Ư�� �÷��̾�� �ɷ��� ������ �Ҵ��մϴ�. ���� �ɷ��� " + 
              "�����̼� �������� ������ �̹� �Ҵ�� �ɷ��� Ÿ�ο��� " + 
              "�ָ� ������ �����ִ� ����� �ɷ��� ������� �˴ϴ�. " + 
              "��Ƽ�� �ɷ��� �� ���� �̻� �ߺ��ؼ� �ټ� �����ϴ�. " + 
              "������ �������� �ʴ��� ����� ������ ����Դϴ�. " + 
              "�г���ĭ�� null�� ���� �ش� �ɷ¿� ��ϵ� �÷��̾ " + 
              "�����Ǹ� ��� �ڵ忡 -1�� ������ �ش� �÷��̾ ����" + "��� �ɷ��� �����˴ϴ�.");
        }
    }
    
    public final void vago(final CommandSender p) {
        if (p.isOp()) {
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(PhysicalFighters.p + "OP�� ���� ��c�ʹ� ������6�� ��c������6�Ǿ����ϴ�. ���� �������� �Խ��ϴ�.");
            EventManager.DamageGuard = false;
        }
    }
    
    public final void vainv(final CommandSender p) {
        if (p.isOp()) {
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(PhysicalFighters.p + "OP�� ���� ��c�ʹ� ������6�� ��c������6�Ǿ����ϴ�. ���� �������� ���� �ʽ��ϴ�.");
            EventManager.DamageGuard = true;
        }
    }
    
    public final void vahungry(final CommandSender p) {
        if (p.isOp()) {
            if (!PhysicalFighters.NoFoodMode) {
                PhysicalFighters.NoFoodMode = true;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "��6OP�� ���� ��c����� ���ѡ�6�� ��c������6�Ǿ����ϴ�.");
            }
            else {
                PhysicalFighters.NoFoodMode = false;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "��6OP�� ���� ��c����� ���ѡ�6�� ��c������6�Ǿ����ϴ�.");
            }
        }
    }
    
    public final void vadura(final CommandSender p) {
        if (p.isOp()) {
            if (!PhysicalFighters.InfinityDur) {
                PhysicalFighters.InfinityDur = true;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "��6OP�� ���� ��c������ ���ѡ�6�� ��c������6�Ǿ����ϴ�.");
            }
            else {
                PhysicalFighters.InfinityDur = false;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "��6OP�� ���� ��c������ ���ѡ�6�� ��c������6�Ǿ����ϴ�.");
            }
        }
    }
    
    public final void vadebug(CommandSender p) {
    	if (p.isOp()) {
        	p.sendMessage(" ��e---- ��6����� ��e-- ��6������ ��c1��6/��c1 ��e----");
    		p.sendMessage("��6/va tc ��f- ��� �ɷ��� ���� ȿ�� �� ��Ÿ���� �ʱ�ȭ �մϴ�.");
    		p.sendMessage("��6/va kill <�г���> ��f- �÷����� ������ �� �÷��̾ ��� ó���մϴ�.");
    		p.sendMessage("��6/va skip ��f- ��� �ɷ��� ������ Ȯ����ŵ�ϴ�.");
    	}
    }
    
    public final void vamaker(CommandSender p) {
    	if (p.isOp()) {
    		p.sendMessage(PhysicalFighters.a + "��aPhysical Fighters ��f������");
    		p.sendMessage(PhysicalFighters.a + "�� ���� '��b���¡�f'���� �����Ͻ� '��dVisualAbility��f'�� ����� ����ϰ��ֽ��ϴ�.");
    	}
    }
    
    public final void vaskip(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario == ScriptStatus.AbilitySelect) {
                MainScripter.OKSign.clear();
                for (Player pl : MainScripter.PlayerList) {
                    MainScripter.OKSign.add(pl);
                	pl.sendMessage(String.format(PhysicalFighters.p + "�ɷ��� ������ ��cȮ����6���׽��ϴ�.", p.getName()));
                }
                MainScripter.s_GameStart.GameStart();
            } else {
                p.sendMessage(PhysicalFighters.w + "�ɷ� ��÷���� �ƴմϴ�.");
            }
        }
    }
    
    public final void vatc(final CommandSender p) {
        if (p.isOp()) {
            for (final AbilityBase a : AbilityList.AbilityList) {
                a.AbilityDTimerCancel();
                a.AbilityCTimerCancel();
            }
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(String.format(PhysicalFighters.p + "��6������ ��c%s��6���� ��Ÿ�ӹ� ���ӽð��� ��c�ʱ�ȭ��6�߽��ϴ�.", p.getName()));
        }
    }
    
    public final void vakill(final CommandSender p, final String[] d) {
        if (p.isOp()) {
            if (d.length == 2) {
                final Player pn = Bukkit.getServer().getPlayerExact(d[1]);
                if (pn != null) {
                    final AbilityBase a = AbilityBase.FindAbility(pn);
                    if (a != null) {
                        a.AbilityDTimerCancel();
                        a.AbilityCTimerCancel();
                    }
                    pn.damage(5000);
                    for (Player player : MainScripter.PlayerList)
                    	player.sendMessage(String.format(PhysicalFighters.p + "��c%s��6���� ��c%s��6���� ��c�����6ó���߽��ϴ�.", p.getName(), pn.getName()));
                }
            } else {
                p.sendMessage(PhysicalFighters.w + "����� �ùٸ��� �ʽ��ϴ�.");
            }
        }
    }
    
    public final void vaelist(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario == ScriptStatus.AbilitySelect) {
                p.sendMessage(ChatColor.GOLD + "=- Ȯ������ ���� ��� -=");
                p.sendMessage(ChatColor.GREEN + "---------------");
                final List<AbilityBase> pl = AbilityList.AbilityList;
                int count = 0;
                for (int l = 0; l < pl.size(); ++l) {
                    if (pl.get(l).GetPlayer() != null) {
                        final AbilityBase tempab = pl.get(l);
                        if (!MainScripter.OKSign.contains(tempab.GetPlayer())) {
                            p.sendMessage(String.format(ChatColor.GREEN + "%d. " + ChatColor.WHITE + "%s", count, tempab.GetPlayer().getName()));
                            ++count;
                        }
                    }
                }
                p.sendMessage(ChatColor.GREEN + "---------------");
            } else {
                p.sendMessage(PhysicalFighters.w + "�ɷ� ��÷�߿��� �����մϴ�.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "����� ������ �����ϴ�.");
        }
    }
    
    public final void vaalist(final CommandSender p) {
        if (p.isOp()) {
            p.sendMessage(ChatColor.GOLD + "=- �ɷ��� ��ĵ�߽��ϴ�. -=");
            p.sendMessage(ChatColor.GREEN + "---------------");
            final List<AbilityBase> pl = AbilityList.AbilityList;
            int count = 0;
            for (int l = 0; l < pl.size(); ++l) {
                if (pl.get(l).GetPlayer() != null) {
                    final Player temp = Bukkit.getServer().getPlayer(pl.get(l).GetPlayer().getName());
                    final AbilityBase tempab = pl.get(l);
                    if (temp != null) {
                        p.sendMessage(String.format(ChatColor.GREEN + "%d. " + ChatColor.WHITE + "%s : " + ChatColor.RED + "%s " + ChatColor.WHITE + "[" + AUC.TypeTextOut(tempab) + "]", count, temp.getName(), tempab.GetAbilityName()));
                        ++count;
                    }
                }
            }
            if (count == 0) {
                p.sendMessage("���� �ɷ��ڰ� �����ϴ�.");
            }
            p.sendMessage(ChatColor.GREEN + "---------------");
        } else {
            p.sendMessage(PhysicalFighters.w + "����� ������ �����ϴ�.");
        }
    }
    
    public final void vastop(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario != ScriptStatus.NoPlay) {
                S_GameStart.PlayDistanceBuffer = 0;
                if (MainScripter.Scenario != ScriptStatus.GameStart) {
                    Bukkit.broadcastMessage(String.format(PhysicalFighters.p + "��c%s��6���� �ɷ��ڸ� ��c�ߴܡ�6���׽��ϴ�.", p.getName()));
                } else {
                    Bukkit.broadcastMessage(String.format(PhysicalFighters.p + "��c%s��6���� �ɷ��ڸ� ��c�ߴܡ�6���׽��ϴ�.", p.getName()));
                }
                int x = 10;
                
                x++; System.out.println(x);
                MainScripter.Scenario = ScriptStatus.NoPlay;
                x++; System.out.println(x);
                MainScripter.s_GameReady.GameReadyStop();
                x++; System.out.println(x);
                MainScripter.s_GameStart.GameStartStop();
                x++; System.out.println(x);
                MainScripter.s_GameProgress.GameProgressStop();
                x++; System.out.println(x);
                MainScripter.s_GameWarnning.GameWarnningStop();
                x++; System.out.println(x);
                S_GameReady.peoplecount = 0;
        		for (Player player : MainScripter.PlayerList) {
        			if (!(player.isOnline()))
        				continue;
        			for (PotionEffect effect : player.getActivePotionEffects())
        				player.removePotionEffect(effect.getType());
    	        	player.getInventory().remove(Material.GOLD_INGOT);
    	        	player.getInventory().remove(Material.IRON_INGOT);
    				player.getInventory().remove(Material.SNOW_BALL);
    				player.getInventory().remove(Material.BONE);
    		     	player.getInventory().remove(Material.FEATHER);
    		     	player.getInventory().remove(Material.DIAMOND);
    	         	player.getInventory().remove(Material.FISHING_ROD);
    	        	player.getInventory().remove(Material.RAW_FISH);
    				player.setFireTicks(0);
    				player.setHealth(player.getMaxHealth());
        			player.sendMessage(PhysicalFighters.p + "��� ������ ��c��ҡ�6�˴ϴ�.");
        			if (player.getWorld().getName().equalsIgnoreCase("world_pvp"))
        				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp spawn " + player.getName());
        		}
        		x++; System.out.println(x);
                MainScripter.OKSign.clear();
                x++; System.out.println(x);
                EventManager.DamageGuard = false;
                for (int l = 0; l < AbilityList.AbilityList.size(); ++l) {
                    AbilityList.AbilityList.get(l).SetRunAbility(false);
                    AbilityList.AbilityList.get(l).SetPlayer(null, false);
                }
                x++; System.out.println(x);
                MainScripter.PlayerList.clear();
                x++; System.out.println(x);
                PhysicalFighters.Time = 8;
                x++; System.out.println(x);
        		Integer id = S_GameStart.taskIds.remove("Timer");
        		x++; System.out.println(x);
        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
        		System.out.println("END");
            } else {
                p.sendMessage(PhysicalFighters.w + "���� ������ �������� �ʾҽ��ϴ�.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "����� ������ �����ϴ�.");
        }
    }
    
    public final static void vacstop(String message) {
      	if (MainScripter.Scenario != ScriptStatus.NoPlay) {
      		S_GameStart.PlayDistanceBuffer = 0;
    		if (MainScripter.Scenario != ScriptStatus.GameStart) {
    			Bukkit.broadcastMessage(PhysicalFighters.p + message + "��6�ɷ��ڰ� ��c���� ��6�Ǿ����ϴ�.");
    		} else {
    			Bukkit.broadcastMessage(PhysicalFighters.p + message + "��6�ɷ��ڰ� ��c���� ��6�Ǿ����ϴ�.");
    		}
            int x = 20;
            
            x++; System.out.println(x);
    		MainScripter.Scenario = ScriptStatus.NoPlay;
    		x++; System.out.println(x);
    		s_GameReady.GameReadyStop();
    		x++; System.out.println(x);
    		s_GameStart.GameStartStop();
    		x++; System.out.println(x);
    		s_GameProgress.GameProgressStop();
    		x++; System.out.println(x);
    		s_GameWarnning.GameWarnningStop();
    		x++; System.out.println(x);
            S_GameReady.peoplecount = 0;
            x++; System.out.println(x);
    		for (Player player : MainScripter.PlayerList) {
    			if (!(player.isOnline()))
    				continue;
    			for (PotionEffect effect : player.getActivePotionEffects())
    				player.removePotionEffect(effect.getType());
	        	player.getInventory().remove(Material.GOLD_INGOT);
	        	player.getInventory().remove(Material.IRON_INGOT);
				player.getInventory().remove(Material.SNOW_BALL);
				player.getInventory().remove(Material.BONE);
		     	player.getInventory().remove(Material.FEATHER);
		      	player.getInventory().remove(Material.DIAMOND);
	         	player.getInventory().remove(Material.FISHING_ROD);
	        	player.getInventory().remove(Material.RAW_FISH);
				player.setFireTicks(0);
				player.setHealth(player.getMaxHealth());
    			player.sendMessage(PhysicalFighters.p + "��� ������ ��c��ҡ�6�˴ϴ�.");
    			if (player.getWorld().getName().equalsIgnoreCase("world_pvp"))
    				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp spawn " + player.getName());
    		}
    		x++; System.out.println(x);
    		OKSign.clear();
    		x++; System.out.println(x);
    		EventManager.DamageGuard = false;
    		x++; System.out.println(x);
    		for (int l = 0; l < AbilityList.AbilityList.size(); ++l) {
    			AbilityList.AbilityList.get(l).SetRunAbility(false);
    			AbilityList.AbilityList.get(l).SetPlayer(null, false);
    		}
    		x++; System.out.println(x);
    		MainScripter.PlayerList.clear();
    		x++; System.out.println(x);
            PhysicalFighters.Time = 8;
            x++; System.out.println(x);
    		Integer id = S_GameStart.taskIds.remove("Timer");
    		x++; System.out.println(x);
    		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
    		System.out.println("eNd");
      	}
    }
    
    public final static void vawstop(Player p) {
      	if (MainScripter.Scenario != ScriptStatus.NoPlay) {
      		S_GameStart.PlayDistanceBuffer = 0;
    		if (MainScripter.Scenario != ScriptStatus.GameStart) {
    			Bukkit.broadcastMessage(PhysicalFighters.p + "��c" + p.getName() + "��6 ���� ����Ͽ� �ɷ��ڰ� ��c���� ��6�Ǿ����ϴ�.");
    		} else {
    			Bukkit.broadcastMessage(PhysicalFighters.p + "��c" + p.getName() + "��6 ���� ����Ͽ� �ɷ��ڰ� ��c���� ��6�Ǿ����ϴ�.");
    		}
    		int x = 30;
            
            x++; System.out.println(x);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 8000");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ÿ������ give " + p.getName() + " 1");
    		MainScripter.Scenario = ScriptStatus.NoPlay;
    		x++; System.out.println(x);
    		s_GameReady.GameReadyStop();
    		x++; System.out.println(x);
    		s_GameStart.GameStartStop();
    		x++; System.out.println(x);
    		s_GameProgress.GameProgressStop();
    		x++; System.out.println(x);
    		s_GameWarnning.GameWarnningStop();
    		x++; System.out.println(x);
            S_GameReady.peoplecount = 0;
            x++; System.out.println(x);
    		for (Player player : MainScripter.PlayerList) {
    			if (!(player.isOnline()))
    				continue;
    			for (PotionEffect effect : player.getActivePotionEffects())
    				player.removePotionEffect(effect.getType());
	        	player.getInventory().remove(Material.GOLD_INGOT);
	        	player.getInventory().remove(Material.IRON_INGOT);
	        	player.getInventory().remove(Material.SNOW_BALL);
	        	player.getInventory().remove(Material.BONE);
	        	player.getInventory().remove(Material.FEATHER);
	          	player.getInventory().remove(Material.DIAMOND);
	         	player.getInventory().remove(Material.FISHING_ROD);
	        	player.getInventory().remove(Material.RAW_FISH);
				player.setFireTicks(0);
				player.setHealth(player.getMaxHealth());
    			if (player.getWorld().getName().equalsIgnoreCase("world_pvp"))
    				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp spawn " + player.getName());
    		}
    		x++; System.out.println(x);
    		OKSign.clear();
    		x++; System.out.println(x);
    		EventManager.DamageGuard = false;
    		x++; System.out.println(x);
    		for (int l = 0; l < AbilityList.AbilityList.size(); ++l) {
    			AbilityList.AbilityList.get(l).SetRunAbility(false);
    			AbilityList.AbilityList.get(l).SetPlayer(null, false);
    		}
    		x++; System.out.println(x);
    		MainScripter.PlayerList.clear();
    		x++; System.out.println(x);
            PhysicalFighters.Time = 8;
            x++; System.out.println(x);
    		Integer id = S_GameStart.taskIds.remove("Timer");
    		x++; System.out.println(x);
    		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
    		System.out.println("eND");
      	}
    }
    
    public final void vaob(final Player p) {
        if (MainScripter.Scenario == ScriptStatus.NoPlay) {
        	if (!p.hasPermission("newbie.noob") || p.isOp()) {
                if (MainScripter.ExceptionList.contains(p)) {
                    ExceptionList.remove(p);
                    p.sendMessage(PhysicalFighters.a + "���� ����ó���� ��b������f�Ǿ����ϴ�.");
                    return;
                } else {
                	ExceptionList.add(p);
                    p.sendMessage(PhysicalFighters.a + "���� ����ó���� ��b�Ϸ��f�Ǿ����ϴ�.");
                    p.sendMessage(PhysicalFighters.a + "��e[ /���� ]��f�� �ٽ� �Է��Ͻø� ��c������f�˴ϴ�.");
                    return;
                }
        	} else {
                p.sendMessage(PhysicalFighters.w + "�Թ��ڴ� ���� ������ �Ұ����մϴ�.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "���� ���� ���Ĵ� ������ ó���� �Ұ����մϴ�.");
        }
    }
    
    public final void vayes(final Player p) {
        if (MainScripter.Scenario == ScriptStatus.AbilitySelect && !ExceptionList.contains(p) && !MainScripter.OKSign.contains(p)) {
            MainScripter.OKSign.add(p);
            p.sendMessage(PhysicalFighters.a + "�ɷ��� Ȯ���Ǿ����ϴ�. �ٸ� ����� ��ٸ�����.");
            for (Player player : MainScripter.PlayerList) {
        		if (!MainScripter.ExceptionList.contains(player)) {
                    player.sendMessage(String.format(PhysicalFighters.p + "��c%s" + ChatColor.WHITE + "��6���� �ɷ��� ��cȮ����6�߽��ϴ�.", p.getName()));
                    player.sendMessage(String.format(PhysicalFighters.p + "��6���� �ο�: ��c" + ChatColor.WHITE + "%d��", MainScripter.PlayerList.size() - MainScripter.OKSign.size()));
        		}
            }
            if (MainScripter.OKSign.size() == MainScripter.PlayerList.size()) {
                MainScripter.s_GameStart.GameStart();
            }
        }
    }
    
    public final void vano(final Player p) {
        if (MainScripter.Scenario == ScriptStatus.AbilitySelect && !MainScripter.ExceptionList.contains(p) && !MainScripter.OKSign.contains(p)) {
            if (this.reRandomAbility(p) == null) {
                p.sendMessage(PhysicalFighters.w + "�ɷ��� ������ �����մϴ�.");
                return;
            }
            AUC.InfoTextOut(p);
            MainScripter.OKSign.add(p);
            p.sendMessage(PhysicalFighters.a + "�ɷ��� �ڵ����� Ȯ���Ǿ����ϴ�. �ٸ� ����� ��ٸ�����.");
            for (Player player : MainScripter.PlayerList) {
         		if (!MainScripter.ExceptionList.contains(player)) {
         			player.sendMessage(String.format(PhysicalFighters.p + "��c%s" + ChatColor.WHITE + "��6���� �ɷ��� ��cȮ����6�߽��ϴ�.", p.getName()));
         			player.sendMessage(String.format(PhysicalFighters.p + "��6���� �ο�: ��c" + ChatColor.WHITE + "%d��", MainScripter.PlayerList.size() - MainScripter.OKSign.size()));	
         		}
            }
            if (MainScripter.OKSign.size() == MainScripter.PlayerList.size()) {
                MainScripter.s_GameStart.GameStart();
            }
        }
    }
    
    private AbilityBase reRandomAbility(final Player p) {
        final ArrayList<AbilityBase> Alist = new ArrayList<AbilityBase>();
        final Random r = new Random();
        int Findex = r.nextInt(AbilityList.AbilityList.size() - 1);
        int saveindex;
        if (Findex == 0) {
            saveindex = AbilityList.AbilityList.size();
        }
        else {
            saveindex = Findex - 1;
        }
        for (int i = 0; i < AbilityList.AbilityList.size(); ++i) {
            if (AbilityList.AbilityList.get(Findex).PlayerCheck(p)) {
                AbilityList.AbilityList.get(Findex).SetPlayer(null, false);
            }
            else if (AbilityList.AbilityList.get(Findex).GetPlayer() == null && (MainScripter.PlayerList.size() > 6 || AbilityList.AbilityList.get(Findex) != AbilityList.mirroring)) {
                Alist.add(AbilityList.AbilityList.get(Findex));
            }
            if (++Findex == saveindex) {
                break;
            }
            if (Findex == AbilityList.AbilityList.size()) {
                Findex = 0;
            }
        }
        if (Alist.size() == 0) {
            return null;
        }
        if (Alist.size() == 1) {
            Alist.get(0).SetPlayer(p, false);
            return Alist.get(0);
        }
        final int ran2 = r.nextInt(Alist.size() - 1);
        Alist.get(ran2).SetPlayer(p, false);
        return Alist.get(ran2);
    }
    
    public enum ScriptStatus
    {
        NoPlay("NoPlay", 0), 
        ScriptStart("ScriptStart", 1), 
        AbilitySelect("AbilitySelect", 2), 
        GameStart("GameStart", 3);
        
        private ScriptStatus(final String s, final int n) {
        }
    }
    
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
}
