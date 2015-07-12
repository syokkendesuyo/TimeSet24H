package net.jp.minecraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 時間を簡単に設定できるコマンドを追加するプラグイン
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
			// /ts time world
			//     args[0] args[1]
			if(args.length == 0){
				help(sender , cmd);
				return true;
			}
			else if(args.length >2){
				sender.sendMessage(ChatColor.RED + "□ 引数が多すぎます。/ts <0-24> <WorldName>");
				return true;
			}
			//Boolean型を用意
			Boolean tf = true;
			if(isNumber(args[0]) == tf){
				int time = Integer.parseInt(args[0]);
				if(sender.hasPermission("ts.use") && sender instanceof Player){
					Player player = (Player) sender;
					if(args.length == 1){
						World world = player.getWorld();
						setTime(time,world,sender);
					}
					else if(args.length == 2){
						try{
							World world = player.getWorld();
							setTime(time,world,sender);
						}
						catch(NullPointerException ex){
							sender.sendMessage(ChatColor.RED + "□ "+ args[1] + " というワールドは存在しません。");
						}
					}
				}
				//コンソールからのコマンド
				else if(sender instanceof ConsoleCommandSender){
					if(args.length == 1){
						sender.sendMessage(ChatColor.RED + "□ コンソールからの場合、ワールドを指定して下さい。/ts <0-24> <WorldName>");
					}
					else if(args.length == 2){
						try{
							World world = Bukkit.getWorld(args[1]);
							setTime(time,world,sender);
						}
						catch(NullPointerException ex){
							sender.sendMessage(ChatColor.RED + "□ "+ args[1] + " というワールドは存在しません。");
						}
					}
				}
				else{
					sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
				}
			}
			else{
				sender.sendMessage(ChatColor.RED + "□ 0から24までの整数以外は受け付けることができません。 使用方法：/ts <0～24>。");
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

	public void setTime(int time ,World world , CommandSender sender){
		if(time>=0 && time<=24){
			sender.sendMessage(ChatColor.AQUA + "□ " + world.getName()  +"の時間を" + time + "時に設定しました。");
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
}