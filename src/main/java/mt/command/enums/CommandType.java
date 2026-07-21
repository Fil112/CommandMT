package mt.command.enums;

public enum CommandType {
    TEXT,               // Просто выводит текст игроку
    CONSOLE_COMMAND,    // Выполнение команды от имени консоли
    RUN_COMMAND,        // Заставляет игрока выполнить команду
    BROADCAST_TEXT,     // Вывод текста на весь сервер
    ALIAS               // Псевдоним для другой команды
}