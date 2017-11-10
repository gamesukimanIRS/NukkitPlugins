package economyairport.nukkitjapanversion.economyairportnukkitjapanversion;//僕はッ！ここにッ！いるよッ！という合図


//テーブル(java)
import java.io.File;

import cn.nukkit.Player;
//お皿
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
//副菜
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
//テーブルクロス
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.SignChangeEvent;

//import文(use)文

//今回のメインディッシュ
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;

public/*パブリック*/ class Main/*メインクラス*/ extends PluginBase/*プラグインベース*/ implements Listener/*イベントもやるよ*/{

	public void onEnable(){//起動時処理
		this.getServer().getPluginManager().registerEvents(this, this);//イベント起きたら通知くれ申し込み
		//起動分セット
		String PluginName = "EconomyAirportNukkitJapanVersion";
		String version = "1.0.0";
		getLogger().info(PluginName + "-v" + version + "を読み込みました。作者:gamesukimanIRS");
    	getLogger().warning("製作者偽りと二次配布、他人用の改造、改造配布はおやめ下さい。");
    	getLogger().info("このプラグインを使用する際はどこかにプラグイン名「" + PluginName + "」と作者名「gamesukimanIRS」を記載する事を推奨します。");
    	//ここまで
    	getDataFolder().mkdir();//専用フォルダ作成
    	Config warpconfig = new Config(new File(this.getDataFolder(), "warp.yml"),Config.YAML);//Config育成
    	this.warp = warpconfig;//this.warpの下準備
	}

	private Config warp;//this.warpでアクセスできるように

	@EventHandler
	public void onBlockTouch(PlayerInteractEvent event){//ブロック触ったとき
		Player Player = event.getPlayer();//触ったプレイヤーオブジェクト取得
		Block Block = event.getBlock();//触ったブロック取得
		int Id = Block.getId();//触ったブロックのID取得
		if(Id == 63 || Id == 68) {//看板IDと照合、あってるかどうか調べる
			Level level = Player.getLevel();
			BlockEntity Sign =level.getBlockEntity(new Vector3(Block.x, Block.y,  Block.z));
			String[] text = ((BlockEntitySign) Sign).getText();
			try {
				String Top = text[0];
				if(Top == "[WARP]") {
					String wsk = text[1];
					wsk = wsk.substring(3);
					wsk = wsk.toLowerCase();
					if(this.warp.exists(wsk)) {
						//ワープ地点があったら
						//メモ、0がx、1y、2z、3l(Level)、4m(Money)
						//コンフィグのこのワープの情報を配列取得
						Object warpii = this.warp.get(wsk);
						String[] warpi = (String[]) warpii;
						//各配列をStringに
						String xs = warpi[0];
						String ys = warpi[1];
						String zs = warpi[2];
						String ls = warpi[3];
						String mki = text[2];
						//こいつらを各種形に変更
						double x = Double.parseDouble(xs);//座標(Double)
						double y = Double.parseDouble(ys);//同上
						double z = Double.parseDouble(zs);//同上
						Level l = this.getServer().getLevelByName(ls);//レベル(Level)
						String mi = mki.substring(5);
						double m = Double.parseDouble(mi);//マネー(double)
						EconomyAPI.getInstance().reduceMoney(Player, m);//先払い(^q^)
						Position pos = new Position(x, y, z, l);//場所指定
						Player.teleport(pos);//てれぽーと
						Player.sendMessage("ワープ「:"+wsk+"へテレポートしました。テレポート料金:¥"+m+" -");//事後報告
					}else {
						//なかったら
						Player.sendMessage("§cこのワープは現在存在しません。");//(´・∀・｀)ﾍｯｻﾞﾏｧ
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
							double xi = ((Player)sender).getX();
							double yi = ((Player)sender).getY();
							double zi = ((Player)sender).getZ();
							String x = String.valueOf(xi);
							String y = String.valueOf(yi);
							String z = String.valueOf(zi);
							String l = ((Player)sender).getLevel().getFolderName();
							String wname = args[0];
							wname = wname.toLowerCase();
							String warpinfo[] = {x, y, z, l};
							this.warp.set(wname, warpinfo);
							this.warp.save();
							sender.sendMessage("ワープを登録しました。ワープ名: "+wname);
							return true;
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
							}
						}else {
							sender.sendMessage(" /delwarp ワープ名");
							return false;
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
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
	public void onSCE(SignChangeEvent e) {//看板立てた事を感知
		String[] text = e.getLines();//看板文字取得
		try {
			if(text[0] == "[WARP]") {//一行目がwarp看板か検査
				Player p = e.getPlayer();//Playerを取得
				if(p.isOp()) {//OPか判別
					if(text[1] != null || text[1] != "") {//ワープ先記載してるかどうか
						if(this.warp.exists(text[1])) {//ワープ先が実在するか
							if(text[2] != null || text[2] != "") {//ワープ料金を記載しているか
								if(isNumber(text[2])) {//ワープ料金は数字か
									if(text[3] != null || text[3] != "") {
										e.setLine(1, "行先:"+text[2]);
										e.setLine(2, "移動料金￥"+text[2]);
										e.setLine(3,"§f"+text[3]);
										p.sendMessage("ワープ看板を設置しました。ワープ先: "+text[1]+" ワープ料金: "+text[2]);
									}else {
										e.setLine(1, "行先:"+text[2]);
										e.setLine(2, "移動料金￥"+text[2]);
										p.sendMessage("ワープ看板を設置しました。ワープ先: "+text[1]+" ワープ料金: "+text[2]);
									}
								}else {
									p.sendMessage("このハゲー！数字じゃないだろー！");
									e.setLine(0, "§c無効なワープ看板");
								}
							}else {
								p.sendMessage("このハゲー！この形式違うだろー！");
								e.setLine(0, "§c無効なワープ看板");
							}
						}else {
							p.sendMessage("このハゲー！このワープ違うだろー！");
							e.setLine(0, "§c無効なワープ看板");
						}
					}else {
						p.sendMessage("このハゲー！この形式違うだろー！");
						e.setLine(0, "§c無効なワープ看板");
					}
				}else {//OPじゃなかったら
					p.sendMessage("§c貴様など..ゲフンゲフンあなたには看板を立てる権限なんてないのです");//(´・∀・｀)ﾍｯｻﾞﾏｧ
					e.setLine(0, "§c無効なワープ看板");
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e1) {

		}
	}

	public boolean isNumber(String num) {//健康飲料が売れるかチェック(番号判定処理)
	    try {//健康飲料お試し配布(試験処理)
	        Integer.parseInt(num);//健康飲料飲んでもらう(番号に変換)
	        return true;//おいしい=売れる(可能true)
	        } catch (NumberFormatException e) {//うげー、まずい(変換不可能)
	        return false;//売れない(不可能false)
	    }
	}
}