package eventlistener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.network;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.whes1015.DATA;

public class eventlistener implements Listener {
    public static List<String> discord=new ArrayList<>();
    private final JavaPlugin plugin;

    public eventlistener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        JsonElement Data = JsonParser.parseString(DATA.toString());
        JsonObject data = Data.getAsJsonObject();
        data.addProperty("Type", "PlayerJoinEvent");
        data.addProperty("FormatVersion", 1);
        data.addProperty("Player", player.getName());
        data.addProperty("Uuid", String.valueOf(player.getUniqueId()));
        data.addProperty("Ip", String.valueOf(player.getAddress()));
        data.addProperty("Op", player.isOp());
        JsonObject jsonObject = network.Post(data);
        assert jsonObject != null;
        if(Objects.equals(jsonObject.get("state").getAsString(), "Warn")) return;
        if (jsonObject.get("response").getAsJsonObject().get("response").getAsString().equals("Unable to confirm")) {
            if(plugin.getConfig().getBoolean("FocusCertified")) {
                discord.add(player.getName());
                player.setGameMode(GameMode.ADVENTURE);
                player.setNoDamageTicks(999999999);
            }
            event.getPlayer().sendMessage(ChatColor.RED + "\u7121\u6cd5\u78ba\u8a8d\u4f60\u7684\u5e33\u865f\u6578\u64da\uff0c\u6578\u64da\u6709\u907a\u5931\u98a8\u96aa\uff0c\u5efa\u8b70\u76e1\u5feb\u7d81\u5b9a\u5e33\u865f");
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (discord.contains(player.getName())) {
            event.getPlayer().sendMessage(ChatColor.RED + "\u4f3a\u670d\u5668\u5df2\u555f\u7528\u5f37\u5236\u8a8d\u8b49\uff0c\u4f7f\u7528 /dc <\u9a57\u8b49\u78bc> \u5b8c\u6210\u8a8d\u8b49");
            event.setCancelled(true);
        }else {
            if(plugin.getConfig().getBoolean("FocusCertified")&&player.getGameMode()!=Bukkit.getServer().getDefaultGameMode()) {
                Objects.requireNonNull(Bukkit.getPlayer(player.getName())).setGameMode(Bukkit.getServer().getDefaultGameMode());
                player.setNoDamageTicks(0);
            }
        }
    }
}
