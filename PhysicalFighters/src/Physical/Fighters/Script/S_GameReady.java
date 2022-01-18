// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.Script;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Date;
import Physical.Fighters.MinerModule.AUC;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MajorModule.AbilityList;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MinerModule.TimerBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class S_GameReady
{
    @SuppressWarnings("unused")
	private MainScripter mainscripter;
    private S_ScriptTimer stimer;
    public static int peoplecount;
    PhysicalFighters M;
    public static boolean skip;
    
    public S_GameReady(final MainScripter mainscripter) {
        this.stimer = new S_ScriptTimer();
        S_GameReady.peoplecount = 0;
        this.mainscripter = mainscripter;
    }
    
    public void GameReady(final CommandSender p) {
        if (p.isOp()) {
            if (MainScripter.Scenario == MainScripter.ScriptStatus.NoPlay) {
                MainScripter.Scenario = MainScripter.ScriptStatus.ScriptStart;
                Bukkit.broadcastMessage(PhysicalFighters.p + "����� ��c�ɷ��� ��6������ ��c���ۡ�6�մϴ�.");
                this.stimer.StartTimer(9);
            } else {
                p.sendMessage(PhysicalFighters.w + "�̹� ������ ���۵Ǿ��ֽ��ϴ�.");
            }
        } else {
            p.sendMessage(PhysicalFighters.w + "����� ������ �����ϴ�.");
        }
    }
    
    public void GameReadyStop() {
        this.stimer.StopTimer();
    }
    
    static void access$1(final S_GameReady s_GameReady, final int peoplecount) {
        S_GameReady.peoplecount = peoplecount;
    }
    
    public final class S_ScriptTimer extends TimerBase
    {
        @Override
        public void EventStartTimer() {
        }
        
        @Override
        public void EventRunningTimer(final int count) {
            if (!PhysicalFighters.DebugMode) {
                switch (count) {
                    case 0: {
                        final Player[] templist = Bukkit.getOnlinePlayers();
                        for (int l = 0; l < templist.length; ++l) {
                            if (!MainScripter.ExceptionList.contains(templist[l])) {
                                if (l < AbilityBase.GetAbilityCount()) {
                                    MainScripter.PlayerList.add(templist[l]);
                                } else {}
                            }
                        }
                        S_GameReady.access$1(S_GameReady.this, templist.length - MainScripter.ExceptionList.size());
                        if (S_GameReady.peoplecount <= AbilityBase.GetAbilityCount()) {	
                        	for (Player player : MainScripter.PlayerList) {
                        		if (!MainScripter.ExceptionList.contains(player)) {
                            		player.sendMessage("");
                            		player.sendMessage(String.format(PhysicalFighters.p + "�� �ο���: ��c%d�� ", MainScripter.PlayerList.size()));
                            		player.sendMessage("");
                        		}
                        	}
                        } else {
                        	for (Player player : MainScripter.PlayerList) {
                        		if (!MainScripter.ExceptionList.contains(player)) {
                            		player.sendMessage("");
                                    player.sendMessage(String.format(PhysicalFighters.p + "�� �ο���: ��c%d�� ", MainScripter.PlayerList.size()));
                                    player.sendMessage(PhysicalFighters.p + "��f�ο��� �ɷ��� �������� �����ϴ�. Error ó���Ⱥе��� �ɷ���");
                                    player.sendMessage(PhysicalFighters.p + "��f������ ������ ��� ���� ���� ��󿡼� ���ܵ˴ϴ�.");
                                    player.sendMessage("");
                        		}
                        	}
                        }
                        if (MainScripter.PlayerList.size() <= 1) {
                            Bukkit.broadcastMessage(PhysicalFighters.w + "���� �÷��̾ �����ϴ�. ���� ���� ����.");
                            MainScripter.Scenario = MainScripter.ScriptStatus.NoPlay;
                            S_GameReady.this.stimer.StopTimer();
                            for (Player player : MainScripter.PlayerList)
                            	player.sendMessage(PhysicalFighters.p + "��� ������ ��c��ҡ�6�˴ϴ�.");
                            MainScripter.PlayerList.clear();
                            return;
                        }
                        break;
                    }
                    case 3: {
                 		EventManager.DamageGuard = true;
                		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw unload world_pvp");
                		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw load world_pvp");
                		
                    	for (Player player : MainScripter.PlayerList) {
                    		if (!MainScripter.ExceptionList.contains(player)) {
                        		player.sendMessage("");
                        		player.sendMessage(String.format(PhysicalFighters.p + "��cPhysical Fighters ��6�÷�����", new Object[0]));
                        		player.sendMessage(PhysicalFighters.p + "����: ��c����");
                        		player.sendMessage(PhysicalFighters.p + "����: ��e����(VisualAbility)");
                        		player.sendMessage("");
                        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp �ɷ���_���� " + player.getName());
                    		}
                    	}
                        break;
                    }
                    case 7: {
                        if (!PhysicalFighters.NoAbilitySetting) {
                        	for (Player player : MainScripter.PlayerList)
                        		player.sendMessage(PhysicalFighters.p + "��f�ɷ� ���� �ʱ�ȭ �� ��÷ �غ�...");
                            for (final AbilityBase ab : AbilityList.AbilityList) {
                                ab.SetRunAbility(false);
                                ab.SetPlayer(null, false);
                            }
                            skip = false;
                    		Timer timer = new Timer();
                    		Date timeToRun = new Date(System.currentTimeMillis() + 30000);
                    		timer.schedule(new TimerTask() {
                    			public void run() {
                    				if (skip == false) {
                                		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "va skip");
                        				return;
                    				}
                    			}
                    		}, timeToRun);
                            break;
                        }
                     	for (Player player : MainScripter.PlayerList) {
                     		player.sendMessage(PhysicalFighters.p + "�ɷ��� ��÷���� �ʽ��ϴ�.");
                     		player.sendMessage(PhysicalFighters.p + "��f�������� �ɷ��� �̹� �ο��Ǿ��ٸ� �����˴ϴ�.");	
                     	}
                        MainScripter.OKSign.clear();
                        for (final Player pl : MainScripter.PlayerList) {
                            MainScripter.OKSign.add(pl);
                        }
                        for (final AbilityBase ab : AbilityList.AbilityList) {
                            ab.SetRunAbility(true);
                        }
                        MainScripter.s_GameStart.GameStart();
                        this.StopTimer();
                        break;
                    }
                    case 9: {
                        MainScripter.Scenario = MainScripter.ScriptStatus.AbilitySelect;
                        if (S_GameReady.peoplecount < AbilityBase.GetAbilityCount()) {
                            for (final Player p : MainScripter.PlayerList) {
                                if (this.RandomAbility(p) == null) {
                                    p.sendMessage(PhysicalFighters.w + "�ɷ��� ������ �����մϴ�.");
                                } else {
                                    p.sendMessage(ChatColor.GOLD + "/va help " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                                    p.sendMessage(ChatColor.GOLD + "/va yes " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                                    p.sendMessage(ChatColor.GOLD + "/va no " + ChatColor.WHITE + "- �ɷ��� 1ȸ�ȿ� �����մϴ�.");
                                }
                            }
                            MainScripter.s_GameWarnning.GameWarnningStart();
                            break;
                        }
                        for (Player player : MainScripter.PlayerList)
                        	player.sendMessage(PhysicalFighters.p + "��f�ɷ� �������� �÷��̾� ���� ���ų� �����Ƿ� ��� ��cȮ����f�˴ϴ�.");
                        for (final Player p : MainScripter.PlayerList) {
                            if (this.RandomAbility(p) == null) {
                                p.sendMessage(PhysicalFighters.w + "�ɷ��� ������ �����մϴ�.");
                            }
                            else {
                                MainScripter.OKSign.add(p);
                                p.sendMessage(PhysicalFighters.a + "��ſ��� ��b�ɷ¡�f�� �ο��Ǿ����ϴ�. " + ChatColor.YELLOW + "/va help" + ChatColor.WHITE + "�� Ȯ���ϼ���.");
                            }
                        }
                        MainScripter.s_GameStart.GameStart();
                        break;
                    }
                }
                return;
            }
            final Player[] templist = Bukkit.getOnlinePlayers();
            for (int l = 0; l < templist.length; ++l) {
                if (!MainScripter.ExceptionList.contains(templist[l])) {
                    if (l < AbilityBase.GetAbilityCount()) {
                        MainScripter.PlayerList.add(templist[l]);
                    } else {}
                }
            }
            S_GameReady.access$1(S_GameReady.this, templist.length - MainScripter.ExceptionList.size());
            if (S_GameReady.peoplecount <= AbilityBase.GetAbilityCount()) {
                EventManager.DamageGuard = !PhysicalFighters.DebugMode;
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(String.format(PhysicalFighters.p + "�� �ο���: ��c%d�� ", S_GameReady.peoplecount));
            } else {
                for (Player player : MainScripter.PlayerList) {
                	player.sendMessage(String.format(PhysicalFighters.p + "�� �ο���: ��c%d�� ", S_GameReady.peoplecount));
                	player.sendMessage(PhysicalFighters.p + "��f�ο��� �ɷ��� �������� �����ϴ�. ���ɷ��ڰ� ����ϴ�.");	
                }
            }
            if (MainScripter.PlayerList.size() == 0) {
            	for (Player player : MainScripter.PlayerList) {
            		player.sendMessage(PhysicalFighters.w + "�÷��̾ �����ϴ�.");
                    player.sendMessage(PhysicalFighters.p + "��� ������ ��c��ҡ�6�˴ϴ�.");
                }
                MainScripter.Scenario = MainScripter.ScriptStatus.NoPlay;
                S_GameReady.this.stimer.StopTimer();
                MainScripter.PlayerList.clear();
                return;
            }
            for (final AbilityBase ab : AbilityList.AbilityList) {
                ab.SetRunAbility(false);
                ab.SetPlayer(null, false);
            }
            MainScripter.Scenario = MainScripter.ScriptStatus.AbilitySelect;
            for (final Player p : MainScripter.PlayerList) {
                if (this.RandomAbility(p) == null) {
                    p.sendMessage(PhysicalFighters.w + "���ɷ��ڰ� ����ϴ�.");
                } else {
                    AUC.InfoTextOut(p);
                    p.sendMessage(ChatColor.GOLD + "/va yes " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                    p.sendMessage(ChatColor.GOLD + "/va no " + ChatColor.WHITE + "- �ɷ��� 1ȸ�ȿ� �����մϴ�.");
                }
            }
            for (final Player p : MainScripter.ExceptionList) {
                p.sendMessage(PhysicalFighters.p + "��a�ɷ� ��÷���Դϴ�");
            }
            this.EndTimer();
        }
        
        @Override
        public void EventEndTimer() {
        }
        
        private AbilityBase RandomAbility(final Player p) {
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
                if (AbilityList.AbilityList.get(Findex).GetPlayer() == null && (MainScripter.PlayerList.size() > 6 || AbilityList.AbilityList.get(Findex) != AbilityList.mirroring)) {
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
    }
}
