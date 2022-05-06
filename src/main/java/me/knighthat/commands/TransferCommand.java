package me.knighthat.commands;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import me.knighthat.plugin.PluginGetters;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

public class TransferCommand implements CommandExecutor, PluginGetters {

    @Getter
    private final CurrencyPlus plugin;

    private final @NonNull String path = "pay-command.";

    public TransferCommand(CurrencyPlus plugin) {

        plugin.getCommand("pay").setExecutor(this);

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {

            String message = getMessages().message("player-only");
            sender.sendMessage(message);
            return true;
        }

        if (!sender.hasPermission("currencyplus.command.pay")) {

            String message = getMessages().message("no-permission");
            sender.sendMessage(message.replace("%perm", "currencyplus.commands.pay"));
            return true;
        }

        if (args.length < 2) {

            String message = "/{0} <player> [amount]";
            sender.sendMessage(MessageFormat.format(message, label));

            return true;
        }

        try {

            Player executor = (Player) sender, recipient = getPlayer(executor, args[0]);
            if (executor.equals(recipient)) {

                String message = getMessages().message(path + "no-self-pay");
                sender.sendMessage(message);

                return true;
            }

            double amount = Double.parseDouble(args[1]);

            if (amount < 0) {

                String message = getMessages().message("below-zero");
                executor.sendMessage(message.replace("%input", args[2]));
            }

            double tax = getConfig().get().getInt("transfer-fee", 0),
                    afterTax = amount * (1 + tax / 100);

            if (sender.hasPermission("currencyplus.tax.bypass")) {
                tax = 0;
                afterTax = amount;
            }

            if (getVault().take(executor, afterTax)) {

                getVault().give(recipient, amount);

                String toSender = getMessages().message(path + ".sender", executor);
                toSender = toSender.replace("%tax", "" + tax);
                toSender = toSender.replace("%total", getConfig().convertBalance(afterTax));
                executor.sendMessage(getConfig().convertBalance(toSender, amount));

                String toRecipient = getMessages().message(path + ".recipient", recipient);
                toRecipient = toRecipient.replace("%executor", sender.getName());
                recipient.sendMessage(getConfig().convertBalance(toRecipient, amount));
            } else {

                String message = getMessages().message(path + "balance-insufficient", executor);
                executor.sendMessage(message);
            }

        } catch (NumberFormatException nfe) {

            String message = getMessages().message("not-a-number");
            sender.sendMessage(message.replace("%input", args[2]));
        } catch (NullPointerException ignored) {
        }

        return true;
    }

    private @Nullable Player getPlayer(@NonNull CommandSender sender, @NonNull String playerName) {

        for (Player player : Bukkit.getOnlinePlayers())
            if (player.getName().equals(playerName))
                return player;

        String message = getMessages().message("player-not-exist");
        sender.sendMessage(message);

        return null;
    }
}
