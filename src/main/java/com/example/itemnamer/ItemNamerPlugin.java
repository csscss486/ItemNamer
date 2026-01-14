package com.example.itemnamer;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for ItemNamer.
 */
public class ItemNamerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ItemNamer enabled");
        // Register the /name command executor
        if (getCommand("name") != null) {
            getCommand("name").setExecutor(new NameCommand(this));
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemNamer disabled");
    }
}
