# ========================================== #
#      CommandMT Configuration File Example  #
# ========================================== #

# Example 1: Simple text message (TEXT)
# Displays custom formatted text to the player who executed the command.
discord_command:
  command: /discord
  type: TEXT
  aliases:
    - /ds
    - /dc
  text:
    - "&7=========================================="
    - "&9&lDiscord &7| Join our official server!"
    - "&fLink: &bhttps://discord.gg/example"
    - "&7=========================================="

# Example 2: Execute as console (CONSOLE_COMMAND)
# Executes commands with server console permissions.
# The $player variable gets automatically replaced with the player's name.
free_bonus:
  command: /bonus
  type: CONSOLE_COMMAND
  runcmd:
    - "give $player golden_apple 1"
    - "tell $player &aYou have received your daily bonus!"

# Example 3: Execute as player (RUN_COMMAND)
# Forces the player to run one or multiple commands.
spawn_command:
  command: /hub
  type: RUN_COMMAND
  aliases:
    - /spawn
    - /lobby
  runcmd:
    - "/tp 0 100 0"

# Example 4: Broadcast message (BROADCAST_TEXT)
# Sends a chat message to every online player on the server.
event_start:
  command: /startevent
  type: BROADCAST_TEXT
  text:
    - "&e[Event] &fPlayer &b$player &fhas started an event at spawn!"
    - "&e[Event] &fUse &a/hub &fto join now."

# Example 5: Command shortcut (ALIAS)
# Forwards the command along with any arguments passed by the player.
creative_mode:
  command: /gmc
  type: ALIAS
  alias-to: "gamemode creative"