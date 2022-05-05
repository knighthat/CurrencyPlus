package me.knighthat.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class CurrencyPlus extends JavaPlugin {

    @Override
    public void onEnable() {


    }

    @Override
    public void onDisable() {
    }

    private boolean isNewerVersions() {

        String verStr = getServer().getVersion();
        int[] indicies = {verStr.lastIndexOf("MC: "), verStr.length()};
        verStr = verStr.substring(indicies[0] + 5, indicies[1] - 1);
        if (verStr.startsWith("1."))
            verStr = verStr.substring(3);

        double version = Double.parseDouble(verStr);
        return version >= 14;
    }
}
