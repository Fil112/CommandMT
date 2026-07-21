package mt.command.command;

import mt.command.executor.IExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DynamicCommand extends Command {

    private final IExecutor executor;

    public DynamicCommand(String name, String description, String usageMessage, List<String> aliases, IExecutor executor) {
        super(name, description, usageMessage, aliases);
        this.executor = executor;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (executor != null) {
            executor.execute(sender, args);
        }
        return true;
    }
}