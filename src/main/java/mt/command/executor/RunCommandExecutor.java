package mt.command.executor;

import mt.command.util.TextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RunCommandExecutor implements IExecutor {

    private final List<String> commandsToRun;

    public RunCommandExecutor(List<String> commandsToRun) {
        this.commandsToRun = commandsToRun;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (commandsToRun == null || commandsToRun.isEmpty()) return;

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду может выполнить только игрок!");
            return;
        }

        Player player = (Player) sender;

        for (String cmd : commandsToRun) {
            String finalCmd = TextUtil.replaceVariables(cmd, player);

            // Убеждаемся, что есть слэш, иначе игрок просто напишет это в чат
            if (!finalCmd.startsWith("/")) {
                finalCmd = "/" + finalCmd;
            }

            player.chat(finalCmd);
        }
    }
}