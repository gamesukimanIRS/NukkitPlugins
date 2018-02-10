package gamesukimanirs.firecancelld.plugin;

/*import cn.nukkit.raknet.protocol.Packet;
import cn.nukkit.block.BlockLava;
import cn.nukkit.event.Event;*/
import cn.nukkit.event.EventHandler;
//import cn.nukkit.event.server.DataPacketReceiveEvent;
//base
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockIgniteEvent;
//import cn.nukkit.event.block.BlockIgniteEvent.BlockIgniteCause;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.weather.LightningStrikeEvent;
//import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener{

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		String PluginName = "FireCancelled";
		String version = "1.0.0";
		getLogger().info(PluginName + "-v" + version + "を読み込みました。作者:gamesukimanIRS");
    	getLogger().warning("製作者偽りと二次配布、他人用の改造、改造配布はおやめ下さい。");
    	getLogger().info("このプラグインを使用する際はどこかにプラグイン名「" + PluginName + "」と作者名「gamesukimanIRS」を記載する事を推奨します。");
	}

	@EventHandler
	public void onLightHit(LightningStrikeEvent event) {
		event.setCancelled();
	}

	@EventHandler
	public void onFireFireFire(BlockIgniteEvent event) {
		/*if(event.getCause() == BlockIgniteCause.FLINT_AND_STEEL) {

		}*/
		event.setCancelled();
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		//LavaID: 10,11
		int bid = event.getBlock().getId();
		if(bid == 10 || bid == 11) {
			String name = event.getPlayer().getName();
			this.getServer().broadcastMessage("§o§c§l[FireBlock-阻止通知]§r§c"+name + "さんが溶岩を置こうとしました。この行動が運営によるものであればこのメッセージは無視してください。鯖民の場合、警戒してください。");
			event.setCancelled();
		}
	}

	/*@EventHandler
	public void onPacketOkaeri(DataPacketReceiveEvent event) {
		DataPacket pk = event.getPacket();
		if(pk == UseItemPacket) {

		}
	}*/
}