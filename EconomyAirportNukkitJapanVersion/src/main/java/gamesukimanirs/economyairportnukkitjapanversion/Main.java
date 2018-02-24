package gamesukimanirs.economyairportnukkitjapanversion;

import java.io.File;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.SignChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import tedo.EconomySystemAPI.EconomySystemAPI;

public class Main extends PluginBase implements Listener{

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		String PluginName = "EconomyAirportNukkitJapanVersion";
		String version = "1.0.0";
		getLogger().info(PluginName + "-v" + version + "を読み込みました。作者:gamesukimanIRS");
    	getLogger().warning("製作者偽りと二次配布、他人用の改造、改造配布はおやめ下さい。");
    	getLogger().info("このプラグインを使用する際はどこかにプラグイン名「" + PluginName + "」と作者名「gamesukimanIRS」を記載する事を推奨します。");
    	getDataFolder().mkdir();
    	Config warpconfig = new Config(new File(this.getDataFolder(), "warp.yml"),Config.YAML);
    	this.ea = (EconomySystemAPI)this.getServer().getPluginManager().getPlugin("EconomySystemAPI");
    	this.warp = warpconfig;
	}

	private Config warp;
	private EconomySystemAPI ea;

	@EventHandler
	public void onBlockTouch(PlayerInteractEvent event){
		Player Player = event.getPlayer();
		Block Block = event.getBlock();
		int Id = Block.getId();
		if(Id == 323) {
			Level level = Player.getLevel();
			BlockEntity Sign =level.getBlockEntity(new Vector3(Block.x, Block.y,  Block.z));
			String[] text = ((BlockEntitySign) Sign).getText();
			try {
				String Top = text[0];
				if(Top.equals("[WARP]")) {
					String wsk = text[1];
					wsk = wsk.substring(5);
					wsk = wsk.toLowerCase();
					if(this.warp.exists(wsk)) {
						@SuppressWarnings("unchecked")
						LinkedHashMap<String,String> warpii = (LinkedHashMap<String, String>) this.warp.get(wsk);
						String xs = warpii.get("x");
						String ys = warpii.get("y");
						String zs = warpii.get("z");
						String ls = warpii.get("l");
						String mki = text[2];
						double x = Double.parseDouble(xs);
						double y = Double.parseDouble(ys);
						double z = Double.parseDouble(zs);
						Level l = this.getServer().getLevelByName(ls);
						String mi = mki.substring(9);
						long m = Long.parseLong(mi);
						this.ea.delMoney(Player.getName(), m);
						Position pos = new Position(x, y, z, l);
						Player.teleport(pos);
						Player.sendMessage("ワープ「:"+wsk+"へテレポートしました。テレポート料金:¥"+m+" -");
					}else {
						Player.sendMessage("§cこのワープは現在存在しません。");
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {

			}
		}
	}

	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			switch (command.getName()) {
				case "setwarp":
					try {
						if(args[0] != null) {
							args[0] = args[0].toLowerCase();
							if(!this.warp.exists(args[0])) {
								double xi = ((Player)sender).getX();
								double yi = ((Player)sender).getY();
								double zi = ((Player)sender).getZ();
								String x = String.valueOf(xi);
								String y = String.valueOf(yi);
								String z = String.valueOf(zi);
								String l = ((Player)sender).getLevel().getFolderName();
								String wname = args[0];
								wname = wname.toLowerCase();
								LinkedHashMap<String, String> warpinfo = new LinkedHashMap<String, String>(){{
									put("x",x);
									put("y",y);
									put("z",z);
									put("l",l);
								}};
								this.warp.set(wname, warpinfo);
								this.warp.save();
								sender.sendMessage("ワープを登録しました。ワープ名: "+wname);
								return true;
							}else {
								sender.sendMessage("ワープ名:"+args[0]+" は既に登録されています");
								return false;
							}
						}else {
							sender.sendMessage(" /setwarp ワープ名");
							return false;
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						sender.sendMessage(" /setwarp ワープ名");
						return false;
					}

				case "delwarp":
					try {
						if(args[0] != null) {
							if(this.warp.exists(args[0])) {
								String wname = args[0];
								wname = wname.toLowerCase();
								this.warp.remove(args[0]);
								this.warp.save();
								sender.sendMessage("ワープを削除しました。ワープ名: "+wname);
								return true;
							}else {
								sender.sendMessage("ワープ「"+args[0]+"」は存在しません");
								return false;
							}
						}else {
							sender.sendMessage(" /delwarp ワープ名");
							return false;
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						sender.sendMessage(" /delwarp ワープ名");
						return false;
					}
			}
		}else {
			this.getLogger().notice("サーバー内で実行してください。");
			return false;
		}
		return true;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		String[] text = e.getLines();
		try {
			if(text[0].equals("[WARP]")) {
				Player p = e.getPlayer();
				if(p.isOp()) {
					if(text[1] != null && text[1] != "") {
						if(this.warp.exists(text[1])) {
							if(text[2] != null && text[2] != "") {
								if(isNumber(text[2])) {
									if(text[3] != null && text[3] != "") {
										e.setLine(0, "§e[WARP]");
										e.setLine(1, "§a行先:"+text[1]);
										e.setLine(2, "§b移動料金¥"+text[2]);
										e.setLine(3,"§f"+text[3]);
										p.sendMessage("§bワープ看板を設置しました。ワープ先: "+text[1].substring(5)+" ワープ料金: "+text[2].substring(9));
									}else {
										e.setLine(1, "§a行先:b"+text[1]);
										e.setLine(2, "§b移動料金¥"+text[2]);
										p.sendMessage("§bワープ看板を設置しました。ワープ先: "+text[1].substring(5)+" ワープ料金: "+text[2].substring(9));
									}
								}else {
									p.sendMessage("§cこのハゲー！数字じゃないだろー！");
									e.setLine(0, "§c無効なワープ看板");
								}
							}else {
								p.sendMessage("§cこのハゲー！この形式違うだろー！");
								e.setLine(0, "§c無効なワープ看板");
							}
						}else {
							p.sendMessage("§cこのハゲー！このワープ違うだろー！");
							e.setLine(0, "§c無効なワープ看板");
						}
					}else {
						p.sendMessage("§cこのハゲー！この形式違うだろー！");
						e.setLine(0, "§c無効なワープ看板");
					}
				}else {//OPじゃなかったら
					p.sendMessage("§c貴様など..ゲフンゲフンあなたには看板を立てる権限なんてないのです");
					e.setLine(0, "§c無効なワープ看板");
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e1) {

		}
	}

	public boolean isNumber(String num) {
	    try {
	        Integer.parseInt(num);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}