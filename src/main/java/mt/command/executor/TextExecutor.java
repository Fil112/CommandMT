package mt.command.executor;

import mt.command.util.TextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TextExecutor implements IExecutor {

    private final List<String> textLines;

    public TextExecutor(List<String> textLines) {
        this.textLines = textLines;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (textLines == null || textLines.isEmpty()) return;

        Player player = (sender instanceof Player) ? (Player) sender : null;

        for (String line : textLines) {
            // Если отправляет консоль, player будет null, TextUtil просто покрасит текст
            sender.sendMessage(TextUtil.replaceVariables(line, player));
        }
    }
}