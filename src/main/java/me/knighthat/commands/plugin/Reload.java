package me.knighthat.commands.plugin;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload extends PluginCommand {

    public Reload(CurrencyPlus plugin) {
        super(plugin, false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        getConfig().reload();
        getMessages().reload();
        getVault().update();

        String message = getMessages().message("reload");
        sender.sendMessage(message);
    }
}
