package gamesukimanirs.pluginenabledisabler.plugin;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
//base
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener{

	public void onEnable(){
		String PluginName = "PluginEnableDisablerNukkit";
		String version = "1.0.0";
		getLogger().info(PluginName + "-v" + version + "を読み込みました。作者:gamesukimanIRS");
    	getLogger().warning("製作者偽りと二次配布、他人用の改造、改造配布はおやめ下さい。");
    	getLogger().info("このプラグインを使用する際はどこかにプラグイン名「" + PluginName + "」と作者名「gamesukimanIRS」を記載する事を推奨します。");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(command.getName()) {
		case"enable":
			try {
				if(args[0] != null) {
					Plugin epl = this.getServer().getPluginManager().getPlugin(args[0]);
					if(epl != null) {
						if(this.getServer().getPluginManager().isPluginEnabled(epl) == false) {
							this.getServer().getPluginManager().enablePlugin(epl);
							sender.sendMessage("プラグインを有効化しました。");
							return true;
						}else {
							sender.sendMessage("§cそのプラグインは既に有効化されています");
							return false;
						}
					}else {
						sender.sendMessage("§cそのプラグインはpluginsフォルダに存在しません");
						return false;
					}
				}else {
					sender.sendMessage("使用方法: /enable <PluginName>");
					return false;
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				sender.sendMessage("使用方法: /enable <PluginName>");
				return false;
			}
		case"disable":
			try {
				if(args[0] != null) {
					Plugin dpl = this.getServer().getPluginManager().getPlugin(args[0]);
					if(dpl != null) {
						if(this.getServer().getPluginManager().isPluginEnabled(dpl) == true) {
							sender.sendMessage("プラグインを無効化しました。");
							this.getServer().getPluginManager().disablePlugin(dpl);
							return true;
						}else {
							sender.sendMessage("§cそのプラグインは既に無効化されています");
							return false;
						}
					}else {
						sender.sendMessage("§cそのプラグインはpluginsフォルダに存在しません");
						return false;
					}
				}else {
					sender.sendMessage("使用方法: /disable <PluginName>");
					return false;
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				sender.sendMessage("使用方法: /disable <PluginName>");
				return false;
			}
		}
		return false;
	}
}