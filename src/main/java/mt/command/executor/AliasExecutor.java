package mt.command.executor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AliasExecutor implements IExecutor {

    private final String targetCommand;

    public AliasExecutor(String targetCommand) {
        this.targetCommand = targetCommand;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (targetCommand == null || targetCommand.isEmpty()) return;

        // Собираем базовую команду и приклеиваем к ней все аргументы, которые ввел игрок
        StringBuilder fullCommand = new StringBuilder(targetCommand);
        for (String arg : args) {
            fullCommand.append(" ").append(arg);
        }

        String finalCmd = fullCommand.toString();

        // Убираем лишний слэш для консоли, если он там есть
        if (finalCmd.startsWith("/")) {
            finalCmd = finalCmd.substring(1);
        }

        // Выполняем команду
        if (sender instanceof Player) {
            ((Player) sender).chat("/" + finalCmd);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCmd);
        }
    }
}