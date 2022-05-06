package me.knighthat.commands;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import me.knighthat.plugin.PluginGetters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor, PluginGetters {

    @Getter
    private final CurrencyPlus plugin;

    public BalanceCommand(CurrencyPlus plugin) {

        plugin.getCommand("balance").setExecutor(this);

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {

                String playerRequired = getMessages().message("player-only");
                sender.sendMessage(playerRequired);
                return true;
            }

            Player player = (Player) sender;
            player.sendMessage(balanceMessage(player, "self-balance"));

        } else {

            for (Player target : plugin.getServer().getOnlinePlayers())
                if (target.getName().equals(args[0])) {

                    sender.sendMessage(balanceMessage(target, "player-balance"));
                    return true;
                }

            String noOne = getMessages().message("player-not-exists");
            sender.sendMessage(noOne);
        }

        return true;
    }

    private @NonNull String balanceMessage(@NonNull Player player, @NonNull String path) {

        String message = getMessages().message(path, player);
        double balance = getVault().get(player);

        return getConfig().convertBalance(message, balance);
    }
}
