package me.knighthat.commands.plugin;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Take extends PluginCommand {


    public Take(CurrencyPlus plugin) {
        super(plugin, false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (args.length < 3) {

            String message = "/currencyplus take <player> [amount]";
            sender.sendMessage(message);
            return;
        }

        try {

            Player player = getPlayer(sender, args[1]);
            double amount = Double.parseDouble(args[2]);

            if (amount < 0) {

                String message = getMessages().message("below-zero");
                sender.sendMessage(message.replace("%input", args[2]));
            } else {

                if (!getVault().take(player, amount)) {

                    String message = getMessages().message(getPath() + ".balance-insufficient", player);
                    sender.sendMessage(message.replace("%amount", "" + amount));

                    return;
                }

                messageToRecipient(sender, player, amount);
                messageToSender(sender, player, amount);
            }
        } catch (NumberFormatException e) {

            String message = getMessages().message("not-a-number");
            sender.sendMessage(message.replace("%input", args[2]));
        } catch (NullPointerException ignored) {
        }
    }

}
