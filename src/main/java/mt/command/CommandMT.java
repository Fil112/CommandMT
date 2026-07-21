package mt.command;

import mt.command.command.CommandManager;
import mt.command.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMT extends JavaPlugin {

    // Держим инстанс под рукой, чтобы легко стучаться из других классов
    private static CommandMT instance;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;

        // Создаем базовые файлы и папки, если это первый запуск
        saveDefaultConfig();

        // Подрубаем наш менеджер для инъекции команд прямо в CommandMap ядра
        this.commandManager = new CommandManager();

        // Парсим и грузим все .mt файлы из папки
        new ConfigManager().loadCommands();

        getLogger().info("CommandMT успешно запущен!");
    }

    @Override
    public void onDisable() {
        // Вычищаем наши команды из ядра при выключении
        if (commandManager != null) {
            commandManager.unregisterAllCommands();
        }
        getLogger().info("CommandMT выключен.");
    }

    public static CommandMT getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}