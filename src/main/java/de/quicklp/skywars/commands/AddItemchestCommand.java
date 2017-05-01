package de.quicklp.skywars.commands;

import de.quicklp.skywars.SkyWars;
import de.quicklp.skywars.config.LanguageManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// /additemchest [x y z]
public class AddItemchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(p.hasPermission("skywars.additemchest")) {
            if(args.length == 3) {

                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);

                World world = p.getWorld();
                world.getBlockAt(x, y, z).setType(Material.CHEST);
                // TODO set Config entry

                SkyWars.getInstance().getGameManager().setLootChest(world.getName(), x, y, z);
                p.sendMessage(LanguageManager.get("lootchest_set"));

                return true;
            }
            else {
                p.sendMessage(LanguageManager.get("lootchest_usage", label));
                return true;
            }
        }
        else {
            p.sendMessage(LanguageManager.get("no_permission"));
            return true;
        }
    }

}
