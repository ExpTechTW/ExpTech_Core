package commands;

import core.logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class commands implements CommandExecutor{

    private final JavaPlugin plugin;

    public commands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Objects.equals(label, "et")) {
                if (Objects.equals(args[0], "reload")) {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                } else if (Objects.equals(args[0], "disable")) {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                } else {
                    logger.log("WARN", "Core_onCommand", "Unknown Command");
                }
        }
        return true;
    }
}
