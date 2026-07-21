package mt.command.command;

import mt.command.CommandMT;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class CommandManager {

    private CommandMap commandMap;
    private Map<String, Command> knownCommands;

    @SuppressWarnings("unchecked")
    public CommandManager() {
        try {
            // Пытаемся вытащить CommandMap сервера через рефлексию
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            this.commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Вытаскиваем мапу knownCommands, где хранятся зарегистрированные команды
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            this.knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);

        } catch (Exception e) {
            CommandMT.getInstance().getLogger().severe("Не удалось получить CommandMap или knownCommands! Инъекция невозможна.");
            e.printStackTrace();
        }
    }

    // Метод для регистрации нашей команды на лету
    public void registerCommand(Command command) {
        if (commandMap != null) {
            // Регистрируем с префиксом mt
            commandMap.register("mt", command);
        }
    }

    // Метод для полного удаления всех наших команд из ядра сервера
    public void unregisterAllCommands() {
        if (knownCommands == null || commandMap == null) return;

        int removedCount = 0;

        // Используем итератор для безопасного удаления элементов из мапы во время перебора
        Iterator<Map.Entry<String, Command>> iterator = knownCommands.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> entry = iterator.next();

            // Если команда принадлежит нашему плагину
            if (entry.getValue() instanceof DynamicCommand) {
                // Снимаем команду с регистрации на уровне Bukkit
                entry.getValue().unregister(commandMap);
                // Удаляем ее из внутренней мапы сервера
                iterator.remove();
                removedCount++;
            }
        }

        CommandMT.getInstance().getLogger().info("Очищено старых команд (включая алиасы): " + removedCount);
    }
}