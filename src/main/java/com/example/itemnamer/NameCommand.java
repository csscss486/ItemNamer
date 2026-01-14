package com.example.itemnamer;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Implements the /name command.
 * Usage: /name <player> <new name...>
 */
public class NameCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public NameCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("itemname.use")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        // Validate arguments
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /name <player> <new name>");
            return true;
        }

        // Find target player (online)
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' is not online.");
            return true;
        }

        // Join remaining args as the new display name and translate color codes
        String rawName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        String displayName = ChatColor.translateAlternateColorCodes('&', rawName);

        // Get item in target's main hand
        ItemStack held = target.getInventory().getItemInMainHand();
        if (held == null || held.getType() == Material.AIR) {
            sender.sendMessage(ChatColor.RED + "Target player is not holding any item in their main hand.");
            return true;
        }

        // Safely obtain or create item meta
        ItemMeta meta = held.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(held.getType());
            if (meta == null) {
                sender.sendMessage(ChatColor.RED + "This item type cannot have a display name.");
                return true;
            }
        }

        // Apply the display name and update the item
        meta.setDisplayName(displayName);
        held.setItemMeta(meta);

        sender.sendMessage(ChatColor.GREEN + "Renamed item in " + target.getName() + "'s hand to " + displayName);
        if (target != sender) {
            target.sendMessage(ChatColor.GREEN + "Your held item's name was changed to " + displayName + " by " + sender.getName());
        }

        return true;
    }
}
