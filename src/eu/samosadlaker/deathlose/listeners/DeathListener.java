package eu.samosadlaker.deathlose.listeners;

import eu.samosadlaker.deathlose.core.Colors;
import eu.samosadlaker.deathlose.core.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;


public class DeathListener implements Listener {

    private static Main plugin = Main.getPlugin();
    FileConfiguration config = plugin.getConfig();
    String prefix = config.getString("prefix") + " &f» &3";

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){

        if(!(e.getEntity() instanceof Player))
            return;


        Player p = e.getEntity();

        double balance = plugin.eco.getBalance(p);
        double newbalance = 0.0;

        if(p.hasPermission(config.getString("lose.player.permission")))
            newbalance = (balance * (config.getInt("lose.titan.value")) / 100);
        if(p.hasPermission(config.getString("lose.warrior.permission")))
            newbalance = (balance * (config.getInt("lose.warrior.value")) / 100);
        if(p.hasPermission(config.getString("lose.legend.permission")))
            newbalance = (balance * (config.getInt("lose.legend.value")) / 100);
        if(p.hasPermission(config.getString("lose.titan.permission")))
            newbalance = (balance * (config.getInt("lose.titan.value")) / 100);

        plugin.eco.withdrawPlayer(p, newbalance);

        p.sendMessage(Colors.formatColor(prefix + config.getString("deathmessage").replace("{@money}", newbalance + "$")));

    }
}
