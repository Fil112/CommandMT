package mt.command.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TextUtil {

    // Классическая замена амперсандов на цвета Bukkit
    public static String colorize(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    // Удобный метод для покраски сразу целого списка строк (для многострочного текста)
    public static List<String> colorize(List<String> list) {
        if (list == null) return null;
        return list.stream().map(TextUtil::colorize).collect(Collectors.toList());
    }

    // Реплейсер переменных
    public static String replaceVariables(String text, Player player) {
        if (text == null) return "";

        String result = text;

        // Если действие совершает игрок, а не консоль — заменяем $player
        if (player != null) {
            result = result.replace("$player", player.getName());
        }

        // В конце обязательно красим текст перед отдачей
        return colorize(result);
    }
}