# ItemNamer

Small Spigot/Paper plugin to rename the item in a player's main hand.

Build
```
mvn clean package
```

Install
- Drop the generated `itemnamer-1.0.0.jar` into your server's `plugins` folder and restart.

Usage
- `/name <player> <new name>` — renames the item the player is holding. Supports color codes using `&` (e.g. `&aHello`).

Permissions
- `itemname.use` — allows using the command (default: op).
