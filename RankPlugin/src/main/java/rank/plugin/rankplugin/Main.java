package rank.plugin.rankplugin;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class Main extends PluginBase implements Listener{

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		String PluginName = "RankPlugin";
		String version = "1.0.0";
		getLogger().info(PluginName + "-v" + version + "を読み込みました。作者:gamesukimanIRS");
    	getLogger().warning("§o§l製作者偽りと二次配布、他人用の改造、改造配布はおやめ下さい。");
    	getLogger().info("このプラグインを使用する際はどこかにプラグイン名「" + PluginName + "」と作者名「gamesukimanIRS」を記載する事を推奨します。");
    	this.getDataFolder().mkdir();//専用フォルダ作成
    	Config rank = new Config(new File(this.getDataFolder(), "ranks.yml"),Config.YAML);//Config育成
    	this.rank = rank;
	}

	private Config rank;



	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		switch	(command.getName()) {
			case "setrank":
				try {
					if(args[0] == null) {
						sender.sendMessage("使用方法: /setrank <プレイヤー> <ランク名>");
						return false;
					} else {
						if(args[1] == null) {
							sender.sendMessage("使用方法: /setrank <プレイヤー> <ランク名>");
							return false;
						} else {
							String name = args[0];
							name = name.toLowerCase();
							String newprefix = args[1];
							if(this.rank.exists(name)) {
								this.rank.set(name, newprefix);
								this.rank.save();
								sender.sendMessage(name+"さんの称号を「"+newprefix+"§r」に変更しました。");
								sender.sendMessage(name+"さんがリログインすると変更が適用されます。");
								return true;
							} else {
								sender.sendMessage(name+"さんはまだサーバーに来ていません。");
								sender.sendMessage("名前はフルネームで入力してください。");
								return true;
							}
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e) {
					sender.sendMessage("使用方法: /setrank <プレイヤー> <ランク名>");
					return false;
				}
				//break;
			case "rankserch":
				try {
					if(args[0] == null) {
						sender.sendMessage("使用方法: /rankserch <プレイヤー>");
						return false;
					} else {
						String name = args[0];
						name = name.toLowerCase();
						if(this.rank.exists(name)) {
							Object prefixs = this.rank.get(name);
							String prefix = prefixs.toString();
							sender.sendMessage(name+"さんの称号は「"+prefix+"§r」です。");
							return true;
						} else {
							sender.sendMessage(name+"さんはまだサーバーに来ていません。");
							sender.sendMessage("名前はフルネームで入力してください。");
							return true;
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e) {
					sender.sendMessage("使用方法: /rankserch <プレイヤー名>");
					return false;
				}
				//break;
		}
		return true;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		String names = player.getName();
		if(this.rank.exists(name)) {
			//新規参加じゃあないのね
			if(player.isOp()) {
				event.setJoinMessage("§a"+names+"§6(OP)§bが§eログイン§bしました");
			}else {
				event.setJoinMessage("§a"+names+"§bが§eログイン§bしました");
			}
		}else {
			this.getServer().broadcastMessage("§bINFO>>§a新しいプレイヤー: "+names+"がログインしました");
			this.rank.set(name, "§b鯖§a民");
			this.rank.save();
		}
		Object prefixs = this.rank.get(name);
		String prefix = prefixs.toString();
		prefix = "§6[§r"+prefix+"§6]§r";
		player.setDisplayName(prefix+name);
		player.setNameTag(prefix+name);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		if(player.isOp()) {
			event.setQuitMessage("§a"+name+"§6(OP)§bが§cログアウト§bしました");
		}else {
			event.setQuitMessage("§a"+name+"§bが§cログアウト§bしました");
		}
	}
}