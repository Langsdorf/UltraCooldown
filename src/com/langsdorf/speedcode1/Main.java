package com.langsdorf.speedcode1;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	
	public Cooldown cool;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		cool = new Cooldown(getConfig(), new File(getDataFolder(), "config.yml"));
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void a(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/delay")) {
			if (!cool.temDelay(e.getPlayer().getName())) {
				cool.addDelay(e.getPlayer().getName());
				e.getPlayer().sendMessage("§eDelay adicionado!");
			} else {
				if (cool.acabouDelay(e.getPlayer().getName(), TimeUnit.DAYS.toMillis(30))) {
					e.getPlayer().sendMessage("§aAcabou delay!");
					cool.removeDelay(e.getPlayer().getName());
				} else {
					e.getPlayer().sendMessage("§cNão acabou o delay, falta §b" + cool.getDelayString(e.getPlayer().getName(), TimeUnit.DAYS.toMillis(30)));
				}
			}
			e.setCancelled(true);
		}
	}

}
