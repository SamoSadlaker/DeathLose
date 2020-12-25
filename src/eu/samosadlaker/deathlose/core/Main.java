package eu.samosadlaker.deathlose.core;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    static Main plugin;
    CommandSender logger = Bukkit.getConsoleSender();
    PluginDescriptionFile pdf = this.getDescription();

    public Economy eco;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable(){
        plugin = this;
        File file = new File(getDataFolder(), "config.yml");
        if(!file.exists()) {
            getConfig().options().copyDefaults(true);
            getConfig().options().copyHeader(true);
            saveDefaultConfig();
        }
        reloadConfig();

        if(!setupEconomy()){
            logger.sendMessage(Colors.formatColor("&b-------------------------------------"));
            logger.sendMessage(Colors.formatColor("&4Disabling plugin &c" + pdf.getName() + " &c. Disabled due to no Vault dependency found!"));
            logger.sendMessage(Colors.formatColor("&c" + pdf.getVersion() + " &f| &c" + pdf.getAuthors().toString() + " &f| &c" + pdf.getWebsite() ));
            logger.sendMessage(Colors.formatColor("&b-------------------------------------"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        logger.sendMessage(Colors.formatColor("&b-------------------------------------"));
        logger.sendMessage(Colors.formatColor("&aPlugin &b" + pdf.getName() + " &asuccessfully enabled"));
        logger.sendMessage(Colors.formatColor("&a" + pdf.getVersion() + " &f| &a" + pdf.getAuthors().toString() + " &f| &a" + pdf.getWebsite() ));
        logger.sendMessage(Colors.formatColor("&b-------------------------------------"));
    }


    @Override
    public void onDisable(){

        logger.sendMessage(Colors.formatColor("&b-------------------------------------"));
        logger.sendMessage(Colors.formatColor("&4Plugin &b" + pdf.getName() + " &4successfully disabled"));
        logger.sendMessage(Colors.formatColor("&c" + pdf.getVersion() + " &f| &c" + pdf.getAuthors().toString() + " &f| &c" + pdf.getWebsite() ));
        logger.sendMessage(Colors.formatColor("&b-------------------------------------"));

    }

    private boolean setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }


}
