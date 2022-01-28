package core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

import static eventlistener.eventlistener.discord;
import static main.whes1015.DATA;

public class discord {

    public static void Discord(String player, String ID, Plugin plugin){
        if(Bukkit.getPlayer(player)!=null){
            JsonElement Data = JsonParser.parseString(DATA.toString());
            JsonObject data = Data.getAsJsonObject();
            data.addProperty("Type", "discord");
            data.addProperty("FormatVersion", 1);
            data.addProperty("Uuid", String.valueOf(Objects.requireNonNull(Bukkit.getPlayer(player)).getUniqueId()));
            data.addProperty("ID",ID);
            JsonObject response=network.Post(data);
            System.out.println(response);
            assert response != null;
            if(Objects.equals(response.get("response").getAsString(), "Success")){
                if(plugin.getConfig().getBoolean("FocusCertified")) {
                    discord.remove(player);
                    Objects.requireNonNull(Bukkit.getPlayer(player)).setGameMode(Bukkit.getServer().getDefaultGameMode());
                }
                Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage("\u6210\u529f\u7d81\u5b9a\u5e33\u865f!");
            }else {
                Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage("\u7d81\u5b9a\u5e33\u865f\u7570\u5e38!");
            }
        }
    }
}
