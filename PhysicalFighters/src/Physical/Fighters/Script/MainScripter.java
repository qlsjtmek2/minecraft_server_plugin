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
    			sender.sendMessage(PhysicalFighters.w + "콘솔에선 실행이 불가능 합니다.");
            }
            else if (data[0].equalsIgnoreCase("ob")) {
    			sender.sendMessage(PhysicalFighters.w + "콘솔에선 실행이 불가능 합니다.");
            }
            else if (data[0].equalsIgnoreCase("yes")) {
    			sender.sendMessage(PhysicalFighters.w + "콘솔에선 실행이 불가능 합니다.");
            }
            else if (data[0].equalsIgnoreCase("no")) {
    			sender.sendMessage(PhysicalFighters.w + "콘솔에선 실행이 불가능 합니다.");
            }
            else if (data[0].equalsIgnoreCase("abi")) {
    			sender.sendMessage(PhysicalFighters.w + "콘솔에선 실행이 불가능 합니다.");
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
                       		p.sendMessage(" §e---- §6능력 목록 §e-- §6페이지 §c0§6~§c8 §e----");
                            for (int code = page * 8; code < (page + 1) * 8; ++code) {
                                if (code < AbilityList.AbilityList.size()) {
                                    final AbilityBase a = AbilityList.AbilityList.get(code);
                                    p.sendMessage(String.format(ChatColor.GREEN + "[%d] " + ChatColor.WHITE + "%s", code, a.GetAbilityName()));
                                }
                            }
                    		p.sendMessage("");
                    		p.sendMessage("§6명령어 §cva ablist [0~8] §6를 쳐서 다음페이지로 넘어가세요.");
                            return;
                        }
                	} else {
                        p.sendMessage(PhysicalFighters.w + "페이지가 올바르지 않습니다.");
                        return;
                	}
                } catch (NumberFormatException e) {
                    p.sendMessage(PhysicalFighters.w + "페이지가 올바르지 않습니다.");
                    return;
                }
            }
            p.sendMessage(ChatColor.GOLD + "/va ablist [0~8] " + ChatColor.WHITE + "- 능력 목록을 봅니다.");
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
        					p.sendMessage(PhysicalFighters.w + "능력 코드가 올바르지 않습니다.");
        					return;
    					}
    				} catch (NumberFormatException e) {
    					p.sendMessage(PhysicalFighters.w + "능력 코드가 올바르지 않습니다.");
    					return;
    				}
    				if (abicode == -1) {
    					for (AbilityBase ab : AbilityList.AbilityList) {
    						if (ab.PlayerCheck(pn)) {
    							ab.SetPlayer(null, true);
    						}
    					}
    					pn.sendMessage(PhysicalFighters.a + "당신의 능력이 모두 §c해제§f되었습니다.");
    					p.sendMessage(String.format(PhysicalFighters.a + "§c%s" + ChatColor.WHITE + "§f님의 능력을 모두 §c해제§f했습니다.", new Object[] { pn.getName() }));
    		            for (Player player : MainScripter.PlayerList)
    		            	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 누군가의 능력을 모두 §c해제§6했습니다.", new Object[] { p.getName() }));
    					return;
    				}
    				if ((abicode >= 0) && (abicode < AbilityList.AbilityList.size())) {
    					AbilityBase a = (AbilityBase)AbilityList.AbilityList.get(abicode);
    					if (d[1].equalsIgnoreCase("null")) {
    						a.SetPlayer(null, true);
    						p.sendMessage(String.format(PhysicalFighters.a + "§c%s §f능력 초기화 완료", new Object[] { a.GetAbilityName() }));
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
    					p.sendMessage(String.format(PhysicalFighters.a + ChatColor.RED + "%s " + ChatColor.WHITE + "님에게 " + ChatColor.GREEN + "%s " + ChatColor.WHITE + "능력 할당이 완료되었습니다.", new Object[] { pn.getName(), a.GetAbilityName() }));
    		            for (Player player : MainScripter.PlayerList)
    		            	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 누군가에게 능력을 강제로 §c할당§6했습니다.", new Object[] { p.getName() }));
    					String s;
    					if ((p instanceof Player))
    						s = p.getName();
    					else
    						s = "서버 개설자";
    					PhysicalFighters.log.info(String.format("%s님이 %s님에게 %s 능력을 할당했습니다.", new Object[] { s, pn.getName(), 
    									a.GetAbilityName() }));
    				} else {
    					p.sendMessage(ChatColor.RED + "능력 코드가 올바르지 않습니다.");
    				}
    			} else {
    				p.sendMessage(ChatColor.RED + "존재하지 않는 플레이어입니다.");
    			}
    		} else {
    	         p.sendMessage(ChatColor.GOLD + "/va abi <닉네임> <능력코드>" + ChatColor.WHITE + "- 능력을 해제, 설정, 관리합니다.");
    		}
    }
    
    public final void vauti(final CommandSender p) {
        if (p.isOp()) {
        	p.sendMessage(" §e---- §6오피 명령어 §e-- §6페이지 §c1§6/§c1 §e----");
            p.sendMessage(ChatColor.GRAY + "명령어는 /va [명령어]로 사용합니다.");
            p.sendMessage(ChatColor.GREEN + "alist : " + ChatColor.WHITE + 
              "능력자 목록을 봅니다. 옵 전용.");
            p.sendMessage(ChatColor.GREEN + "elist : " + ChatColor.WHITE + 
              "능력 확정이 안된 사람들을 보여줍니다. 옵 전용.");
            p.sendMessage(ChatColor.GREEN + "ablist [페이지(0~2)] : " + 
              ChatColor.WHITE + "능력 목록 및 능력 코드를 보여줍니다. 옵 전용.");
            p.sendMessage(ChatColor.GREEN + "abi [닉네임] [능력 코드] : " + 
              ChatColor.WHITE + "특정 플레이어에게 능력을 강제로 할당합니다. 같은 능력을 " + 
              "여럿이서 가질수는 없으며 이미 할당된 능력을 타인에게 " + 
              "주면 기존에 갖고있던 사람의 능력은 사라지게 됩니다. " + 
              "액티브 능력은 두 종류 이상 중복해서 줄수 없습니다. " + 
              "게임을 시작하지 않더라도 사용이 가능한 명령입니다. " + 
              "닉네임칸에 null을 쓰면 해당 능력에 등록된 플레이어가 " + 
              "해제되며 명령 코드에 -1을 넣으면 해당 플레이어가 가진" + "모든 능력이 해제됩니다.");
        }
    }
    
    public final void vago(final CommandSender p) {
        if (p.isOp()) {
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(PhysicalFighters.p + "OP에 의해 §c초반 무적§6이 §c해제§6되었습니다. 이제 데미지를 입습니다.");
            EventManager.DamageGuard = false;
        }
    }
    
    public final void vainv(final CommandSender p) {
        if (p.isOp()) {
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(PhysicalFighters.p + "OP에 의해 §c초반 무적§6이 §c설정§6되었습니다. 이제 데미지를 입지 않습니다.");
            EventManager.DamageGuard = true;
        }
    }
    
    public final void vahungry(final CommandSender p) {
        if (p.isOp()) {
            if (!PhysicalFighters.NoFoodMode) {
                PhysicalFighters.NoFoodMode = true;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§6OP에 의해 §c배고픔 무한§6이 §c설정§6되었습니다.");
            }
            else {
                PhysicalFighters.NoFoodMode = false;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§6OP에 의해 §c배고픔 무한§6이 §c해제§6되었습니다.");
            }
        }
    }
    
    public final void vadura(final CommandSender p) {
        if (p.isOp()) {
            if (!PhysicalFighters.InfinityDur) {
                PhysicalFighters.InfinityDur = true;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§6OP에 의해 §c내구도 무한§6이 §c설정§6되었습니다.");
            }
            else {
                PhysicalFighters.InfinityDur = false;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§6OP에 의해 §c내구도 무한§6이 §c해제§6되었습니다.");
            }
        }
    }
    
    public final void vadebug(CommandSender p) {
    	if (p.isOp()) {
        	p.sendMessage(" §e---- §6디버그 §e-- §6페이지 §c1§6/§c1 §e----");
    		p.sendMessage("§6/va tc §f- 모든 능력의 지속 효과 및 쿨타임을 초기화 합니다.");
    		p.sendMessage("§6/va kill <닉네임> §f- 플러그인 내에서 이 플레이어를 사망 처리합니다.");
    		p.sendMessage("§6/va skip §f- 모든 능력을 강제로 확정시킵니다.");
    	}
    }
    
    public final void vamaker(CommandSender p) {
    	if (p.isOp()) {
    		p.sendMessage(PhysicalFighters.a + "§aPhysical Fighters §f제작자");
    		p.sendMessage(PhysicalFighters.a + "본 모드는 '§b제온§f'님이 배포하신 '§dVisualAbility§f'의 모듈을 사용하고있습니다.");
    	}
    }
    
    public final void vaskip(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario == ScriptStatus.AbilitySelect) {
                MainScripter.OKSign.clear();
                for (Player pl : MainScripter.PlayerList) {
                    MainScripter.OKSign.add(pl);
                	pl.sendMessage(String.format(PhysicalFighters.p + "능력을 강제로 §c확정§6시켰습니다.", p.getName()));
                }
                MainScripter.s_GameStart.GameStart();
            } else {
                p.sendMessage(PhysicalFighters.w + "능력 추첨중이 아닙니다.");
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
            	player.sendMessage(String.format(PhysicalFighters.p + "§6관리자 §c%s§6님이 쿨타임및 지속시간을 §c초기화§6했습니다.", p.getName()));
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
                    	player.sendMessage(String.format(PhysicalFighters.p + "§c%s§6님이 §c%s§6님을 §c사망§6처리했습니다.", p.getName(), pn.getName()));
                }
            } else {
                p.sendMessage(PhysicalFighters.w + "명령이 올바르지 않습니다.");
            }
        }
    }
    
    public final void vaelist(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario == ScriptStatus.AbilitySelect) {
                p.sendMessage(ChatColor.GOLD + "=- 확정하지 않은 사람 -=");
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
                p.sendMessage(PhysicalFighters.w + "능력 추첨중에만 가능합니다.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "당신은 권한이 없습니다.");
        }
    }
    
    public final void vaalist(final CommandSender p) {
        if (p.isOp()) {
            p.sendMessage(ChatColor.GOLD + "=- 능력을 스캔했습니다. -=");
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
                p.sendMessage("아직 능력자가 없습니다.");
            }
            p.sendMessage(ChatColor.GREEN + "---------------");
        } else {
            p.sendMessage(PhysicalFighters.w + "당신은 권한이 없습니다.");
        }
    }
    
    public final void vastop(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario != ScriptStatus.NoPlay) {
                S_GameStart.PlayDistanceBuffer = 0;
                if (MainScripter.Scenario != ScriptStatus.GameStart) {
                    Bukkit.broadcastMessage(String.format(PhysicalFighters.p + "§c%s§6님이 능력자를 §c중단§6시켰습니다.", p.getName()));
                } else {
                    Bukkit.broadcastMessage(String.format(PhysicalFighters.p + "§c%s§6님이 능력자를 §c중단§6시켰습니다.", p.getName()));
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
        			player.sendMessage(PhysicalFighters.p + "모든 설정이 §c취소§6됩니다.");
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
                p.sendMessage(PhysicalFighters.w + "아직 게임을 시작하지 않았습니다.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "당신은 권한이 없습니다.");
        }
    }
    
    public final static void vacstop(String message) {
      	if (MainScripter.Scenario != ScriptStatus.NoPlay) {
      		S_GameStart.PlayDistanceBuffer = 0;
    		if (MainScripter.Scenario != ScriptStatus.GameStart) {
    			Bukkit.broadcastMessage(PhysicalFighters.p + message + "§6능력자가 §c종료 §6되었습니다.");
    		} else {
    			Bukkit.broadcastMessage(PhysicalFighters.p + message + "§6능력자가 §c종료 §6되었습니다.");
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
    			player.sendMessage(PhysicalFighters.p + "모든 설정이 §c취소§6됩니다.");
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
    			Bukkit.broadcastMessage(PhysicalFighters.p + "§c" + p.getName() + "§6 님이 우승하여 능력자가 §c종료 §6되었습니다.");
    		} else {
    			Bukkit.broadcastMessage(PhysicalFighters.p + "§c" + p.getName() + "§6 님이 우승하여 능력자가 §c종료 §6되었습니다.");
    		}
    		int x = 30;
            
            x++; System.out.println(x);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 8000");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 핫타임코인 give " + p.getName() + " 1");
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
                    p.sendMessage(PhysicalFighters.a + "게임 예외처리가 §b해제§f되었습니다.");
                    return;
                } else {
                	ExceptionList.add(p);
                    p.sendMessage(PhysicalFighters.a + "게임 예외처리가 §b완료§f되었습니다.");
                    p.sendMessage(PhysicalFighters.a + "§e[ /참여 ]§f를 다시 입력하시면 §c해제§f됩니다.");
                    return;
                }
        	} else {
                p.sendMessage(PhysicalFighters.w + "입문자는 게임 참여가 불가능합니다.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "게임 시작 이후는 옵저버 처리가 불가능합니다.");
        }
    }
    
    public final void vayes(final Player p) {
        if (MainScripter.Scenario == ScriptStatus.AbilitySelect && !ExceptionList.contains(p) && !MainScripter.OKSign.contains(p)) {
            MainScripter.OKSign.add(p);
            p.sendMessage(PhysicalFighters.a + "능력이 확정되었습니다. 다른 사람을 기다리세요.");
            for (Player player : MainScripter.PlayerList) {
        		if (!MainScripter.ExceptionList.contains(player)) {
                    player.sendMessage(String.format(PhysicalFighters.p + "§c%s" + ChatColor.WHITE + "§6님이 능력을 §c확정§6했습니다.", p.getName()));
                    player.sendMessage(String.format(PhysicalFighters.p + "§6남은 인원: §c" + ChatColor.WHITE + "%d명", MainScripter.PlayerList.size() - MainScripter.OKSign.size()));
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
                p.sendMessage(PhysicalFighters.w + "능력의 갯수가 부족합니다.");
                return;
            }
            AUC.InfoTextOut(p);
            MainScripter.OKSign.add(p);
            p.sendMessage(PhysicalFighters.a + "능력이 자동으로 확정되었습니다. 다른 사람을 기다리세요.");
            for (Player player : MainScripter.PlayerList) {
         		if (!MainScripter.ExceptionList.contains(player)) {
         			player.sendMessage(String.format(PhysicalFighters.p + "§c%s" + ChatColor.WHITE + "§6님이 능력을 §c확정§6했습니다.", p.getName()));
         			player.sendMessage(String.format(PhysicalFighters.p + "§6남은 인원: §c" + ChatColor.WHITE + "%d명", MainScripter.PlayerList.size() - MainScripter.OKSign.size()));	
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
