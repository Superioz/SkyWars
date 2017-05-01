package de.quicklp.skywars.game;

import de.quicklp.skywars.SkyWars;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor
public class Game {

    private void start(List<Player> teamPlayer) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();


        Bukkit.getOnlinePlayers().forEach(new Consumer<Player>() {
            @Override
            public void accept(Player p) {
                p.teleport(SkyWars.getInstance().getGameManager().getSpawnpoints().get(0));

            }
        });
    }
}
