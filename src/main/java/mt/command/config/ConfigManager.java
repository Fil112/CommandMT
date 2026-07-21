package mt.command.config;

import mt.command.CommandMT;
import mt.command.command.DynamicCommand;
import mt.command.enums.CommandType;
import mt.command.executor.AliasExecutor;
import mt.command.executor.BroadcastExecutor;
import mt.command.executor.ConsoleExecutor;
import mt.command.executor.IExecutor;
import mt.command.executor.RunCommandExecutor;
import mt.command.executor.TextExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final CommandMT plugin = CommandMT.getInstance();

    public void loadCommands() {
        File commandFolder = new File(plugin.getDataFolder(), "command");

        // Если папки нет — создаем её и выгружаем оба примера
        if (!commandFolder.exists()) {
            commandFolder.mkdirs();
            plugin.saveResource("command/example_ru.mt", false);
            plugin.saveResource("command/example_en.mt", false);
        }

        File[] mtFiles = commandFolder.listFiles((dir, name) -> name.endsWith(".mt"));

        if (mtFiles == null || mtFiles.length == 0) {
            plugin.getLogger().warning("Файлы команд (.mt) не найдены!");
            return;
        }

        int loadedCount = 0;

        for (File file : mtFiles) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (String key : config.getKeys(false)) {
                try {
                    String cmdName = config.getString(key + ".command");
                    if (cmdName == null) continue;

                    // Очищаем от случайных слэшей, чтобы регистрация в ядре прошла корректно
                    cmdName = cmdName.replace("/", "");

                    String typeStr = config.getString(key + ".type", "TEXT").toUpperCase();
                    CommandType type = CommandType.valueOf(typeStr);

                    IExecutor executor = null;

                    // Подбираем нужный обработчик под тип команды
                    if (type == CommandType.TEXT) {
                        List<String> textLines = config.getStringList(key + ".text");
                        executor = new TextExecutor(textLines);

                    } else if (type == CommandType.CONSOLE_COMMAND) {
                        List<String> runCmd = config.getStringList(key + ".runcmd");
                        executor = new ConsoleExecutor(runCmd);

                    } else if (type == CommandType.RUN_COMMAND) {
                        List<String> runCmd = config.getStringList(key + ".runcmd");
                        executor = new RunCommandExecutor(runCmd);

                    } else if (type == CommandType.BROADCAST_TEXT) {
                        List<String> textLines = config.getStringList(key + ".text");
                        executor = new BroadcastExecutor(textLines);

                    } else if (type == CommandType.ALIAS) {
                        String targetCommand = config.getString(key + ".alias-to");
                        executor = new AliasExecutor(targetCommand);
                    }

                    if (executor != null) {
                        // Вытягиваем алиасы, если они прописаны в конфиге
                        List<String> aliases = new ArrayList<>();
                        if (config.contains(key + ".aliases")) {
                            aliases = config.getStringList(key + ".aliases");
                        }

                        // Собираем динамическую команду со всеми параметрами
                        DynamicCommand command = new DynamicCommand(
                                cmdName,
                                "Кастомная команда: " + key,
                                "/" + cmdName,
                                aliases,
                                executor
                        );

                        plugin.getCommandManager().registerCommand(command);
                        loadedCount++;
                    }

                } catch (Exception e) {
                    plugin.getLogger().warning("Сбой при загрузке команды '" + key + "' из файла " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        plugin.getLogger().info("Зарегистрировано команд: " + loadedCount);
    }
}