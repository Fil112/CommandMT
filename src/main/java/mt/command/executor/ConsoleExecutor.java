package mt.command.executor;

import mt.command.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ConsoleExecutor implements IExecutor {

    private final List<String> commandsToRun;

    public ConsoleExecutor(List<String> commandsToRun) {
        this.commandsToRun = commandsToRun;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (commandsToRun == null || commandsToRun.isEmpty()) return;

        Player player = (sender instanceof Player) ? (Player) sender : null;

        for (String cmd : commandsToRun) {
            // Заменяем $player на ник того, кто ввел команду
            String finalCmd = TextUtil.replaceVariables(cmd, player);

            // Если в конфиге случайно написали слэш - убираем его, консоль и так поймет
            finalCmd = finalCmd.startsWith("/") ? finalCmd.substring(1) : finalCmd;

            // Выполняем от лица консоли
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCmd);
        }
    }
}