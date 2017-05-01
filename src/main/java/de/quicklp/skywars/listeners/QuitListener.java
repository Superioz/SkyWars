package de.quicklp.skywars.listeners;

import de.quicklp.skywars.SkyWars;
import de.quicklp.skywars.utils.CountdownUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(Bukkit.getOnlinePlayers().size() > SkyWars.getInstance().getGameManager().getMinPlayers()) {
            CountdownUtil.stopCountdown();
        }
    }

}
