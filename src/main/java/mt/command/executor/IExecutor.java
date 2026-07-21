package mt.command.executor;

import org.bukkit.command.CommandSender;

public interface IExecutor {
    // Метод, который будет вызываться при вводе команды
    void execute(CommandSender sender, String[] args);
}