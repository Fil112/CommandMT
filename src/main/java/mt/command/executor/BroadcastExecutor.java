package mt.command.executor;

import mt.command.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BroadcastExecutor implements IExecutor {

    private final List<String> textLines;

    public BroadcastExecutor(List<String> textLines) {
        this.textLines = textLines;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (textLines == null || textLines.isEmpty()) return;

        Player player = (sender instanceof Player) ? (Player) sender : null;

        for (String line : textLines) {
            // Красим текст и заменяем переменную на ник отправителя
            String formatted = TextUtil.replaceVariables(line, player);
            // Отправляем на весь сервер
            Bukkit.broadcastMessage(formatted);
        }
    }
}