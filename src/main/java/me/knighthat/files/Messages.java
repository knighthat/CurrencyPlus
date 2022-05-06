package me.knighthat.files;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import me.knighthat.plugin.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Messages extends PluginFiles {

    public Messages(CurrencyPlus plugin) {
        super(plugin);
    }

    public @NonNull String string(@NonNull String path) {
        return Utils.color(get().getString(path, ""));
    }

    public @NonNull String getPrefix() {
        return string("prefix");
    }

    public @NonNull String message(@NonNull String path) {

        if (!get().contains(path))
            return "";

        return getPrefix() + string(path);
    }

    public @NonNull String message(@NonNull String path, @Nullable Player player) {

        String result = message(path);

        if (player != null) {

            result = result.replace("%player", player.getName());
            result = result.replace("%display", player.getDisplayName());
        }

        return result;
    }
}
