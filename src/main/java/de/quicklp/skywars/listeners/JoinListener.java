package de.quicklp.skywars.listeners;

import de.quicklp.skywars.SkyWars;
import de.quicklp.skywars.utils.CountdownUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Bukkit.getOnlinePlayers().size() >= SkyWars.getInstance().getGameManager().getMinPlayers()) {
            CountdownUtil.startCountdown();
        }
    }

}
