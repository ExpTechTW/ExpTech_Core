package core;

import main.whes1015;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class logger {

    public static String loggerData;
    public static Integer status=0;

    public static void log(String level,String process,String data){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        loggerData=loggerData+"["+dtf.format(now)+"][ExpTech/"+level+"/"+process+"] "+data+"\n";
        ChatColor color=null;
        if(Objects.equals(level, "TRACE")){
            color= ChatColor.GREEN;
        }else if(Objects.equals(level, "DEBUG")){
            color= ChatColor.YELLOW;
        }else if(Objects.equals(level, "INFO")){
            color= ChatColor.AQUA;
        }else if(Objects.equals(level, "WARN")){
            color= ChatColor.LIGHT_PURPLE;
        }else if(Objects.equals(level, "ERROR")){
            status=1;
            color= ChatColor.RED;
        }
        if(Objects.equals(whes1015.LogLevel, "INFO")){
            if(Objects.equals(level, "TRACE") ||Objects.equals(level, "DEBUG")) return;
        }else if(Objects.equals(whes1015.LogLevel, "DEBUG")){
            if(Objects.equals(level, "TRACE")) return;
        }
        Bukkit.getConsoleSender().sendMessage(color+"[ExpTech/"+level+"/"+process+"] "+data);
    }
}

