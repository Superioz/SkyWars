package de.quicklp.skywars.utils;

import de.quicklp.skywars.SkyWars;
import de.quicklp.skywars.config.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class CountdownUtil {

    boolean isRunning = false;

    public static void sendCountdown(int countDown) {
        Bukkit.getOnlinePlayers().forEach((Consumer<Player>) p -> {
            p.sendMessage("");
            p.setLevel(countDown);
        });
    }

    public static void resetCountdown() {
        Bukkit.getOnlinePlayers().forEach((Consumer<Player>) p -> {
            p.sendMessage(ChatUtil.translateColor(LanguageManager.get("countdown-prefix") + " " + LanguageManager.get("countdown-reset")));
            p.setLevel(0);

        });
    }

    public static void startCountdown() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyWars.getInstance(), new Runnable() {
            private int countDown = SkyWars.getInstance().getGameManager().getLobbyTime();
            private int current = SkyWars.getInstance().getGameManager().getLobbyTime();

            @Override
            public void run() {

                // 600 ==
                if(current == MathUtil.getPercentage(50, countDown)) {
                    sendCountdown(current);
                    --current;
                    return;
                }

                if(current == MathUtil.getPercentage(25, countDown)) {
                    sendCountdown(current);
                    --current;
                    return;
                }

                if(current == MathUtil.getPercentage(10, countDown)) {
                    sendCountdown(current);
                    --current;
                    return;
                }

                if(current == 10) {
                    sendCountdown(current);
                    --current;
                    return;
                }
                if(current <= 5) {
                    sendCountdown(current);
                    --current;
                }

            }
        }, 0, 20);
    }

    public static void stopCountdown() {
        resetCountdown();

    }
}
