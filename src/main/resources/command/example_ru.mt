# ========================================== #
#     Конфигурационный файл команд CommandMT #
# ========================================== #

# Пример 1: Текстовая команда (TEXT)
# Выводит сообщения игроку, вызвавшему команду.
discord_command:
  command: /discord
  type: TEXT
  aliases:
    - /ds
    - /dc
  text:
    - "&7=========================================="
    - "&9&lDiscord &7| Присоединяйтесь к нашему сообществу!"
    - "&fСсылка: &bhttps://discord.gg/example"
    - "&7=========================================="

# Пример 2: Выполнение команды от консоли (CONSOLE_COMMAND)
# Запускает указанные команды от имени консоли сервера.
# Переменная $player автоматически заменяется на ник игрока.
free_bonus:
  command: /bonus
  type: CONSOLE_COMMAND
  runcmd:
    - "give $player golden_apple 1"
    - "tell $player &aВы получили свой ежедневный бонус!"

# Пример 3: Выполнение команды от имени игрока (RUN_COMMAND)
# Заставляет игрока выполнить одну или несколько команд.
spawn_command:
  command: /hub
  type: RUN_COMMAND
  aliases:
    - /spawn
    - /lobby
  runcmd:
    - "/tp 0 100 0"

# Пример 4: Объявление на весь сервер (BROADCAST_TEXT)
# Рассылает сообщение всем игрокам онлайн.
event_start:
  command: /startevent
  type: BROADCAST_TEXT
  text:
    - "&e[Ивент] &fИгрок &b$player &fзапустил ивент на спавне!"
    - "&e[Ивент] &fИспользуйте &a/hub &fчтобы присоединиться."

# Пример 5: Сокращение / Псевдоним (ALIAS)
# Пробрасывает команду и перенаправляет все переданные аргументы.
creative_mode:
  command: /gmc
  type: ALIAS
  alias-to: "gamemode creative"