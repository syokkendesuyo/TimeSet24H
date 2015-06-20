package net.jp.minecraft.plugin;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * よく使うコマンドやアクションをチェスト画面を利用して操作しよう！ってプラグイン
 * AdminInventoryTools
 * @author syokkendesuyo
 */


public class TimeSet extends JavaPlugin implements Listener {


	/**
	 * プラグインが有効になったときに呼び出されるメソッド
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ts")){
			if(args.length == 0){
				help(sender , cmd);
			}
			else if(args.length == 1){
				//Boolean型を用意
				Boolean tf = true;
				if(isNumber(args[0]) == tf){
					int time = Integer.parseInt(args[0]);
					if(sender.hasPermission("ts.use") && sender instanceof Player){
						if(time>=0 && time<=24){
							sender.sendMessage(ChatColor.AQUA + "□ 時間を" + time + "時に設定しました。");
							Player player = (Player) sender;
							World world = player.getWorld();
							if(time<6){
								int col= time+18;
								int set = col*1000;
								world.setTime(set);
							}
							else{
								int col2= time-6;
								int set2 = col2*1000;
								world.setTime(set2);
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "□ 数値は0から24までで入力してください。");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
					}
				}
				else{
					sender.sendMessage(ChatColor.RED + "□ 0から24までの整数以外は受け付けることができません。 使用方法：/ts <0～24>。");
				}
			}
			return true;
		}
		return false;
	}
	public void help(CommandSender sender , Command cmd){
		sender.sendMessage("");
		sender.sendMessage(ChatColor.YELLOW + "＝＝＝＝＝ TimeSet24H ＝＝＝＝＝");
		sender.sendMessage(ChatColor.UNDERLINE + "Auther : syokkendesuyo");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.WHITE + "□ /ts help ：当プラグインのヘルプ");
		sender.sendMessage(ChatColor.WHITE + "□ /ts <0～24> ：時間を0から24時に設定します");
		sender.sendMessage(ChatColor.YELLOW + "＝＝＝＝＝ ＝＝＝＝＝ ＝＝＝＝＝");
		sender.sendMessage("");
	}

	//int型であるか判定
	public boolean isNumber(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException event) {
			return false;
		}
	}
}