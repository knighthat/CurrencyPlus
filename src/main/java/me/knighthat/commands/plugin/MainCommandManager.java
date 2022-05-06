package me.knighthat.commands.plugin;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import me.knighthat.plugin.PluginGetters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class MainCommandManager implements CommandExecutor, PluginGetters {

    @Getter
    private final @NonNull ArrayList<PluginCommand> commands = new ArrayList<>();

    @Getter
    private final CurrencyPlus plugin;

    public MainCommandManager(CurrencyPlus plugin) {

        plugin.getCommand("currencyplus").setExecutor(this);

        this.plugin = plugin;
        getCommands().add(new Reload(plugin));
        getCommands().add(new Give(plugin));
        getCommands().add(new Take(plugin));
        getCommands().add(new Set(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {

            PluginCommand cmd = getCommand(sender, args[0]);
            if (cmd != null)
                cmd.execute(sender, args);
        }

        return true;
    }

    private @Nullable PluginCommand getCommand(@NonNull CommandSender sender, @NonNull String name) {

        for (PluginCommand cmd : commands)
            if (cmd.getName().equalsIgnoreCase(name)) {

                if (cmd.isRequiresPlayer() && !(sender instanceof Player))
                    break;

                String[] perms = {"currencyplus.*", "currencyplus.command.*", cmd.getPermission()};

                for (String permission : perms)
                    if (sender.hasPermission(permission))
                        return cmd;

                String message = getMessages().message("no-permission");
                sender.sendMessage(message.replace("%perm", cmd.getPermission()));
            }

        return null;
    }
}

@Getter
abstract class PluginCommand implements PluginGetters {

    private final CurrencyPlus plugin;
    private final boolean requiresPlayer;
    private final @NonNull String path;

    protected PluginCommand(CurrencyPlus plugin, boolean requiresPlayer) {

        this.plugin = plugin;
        this.requiresPlayer = requiresPlayer;
        this.path = getName() + "-command";
    }

    public @NonNull String getName() {
        String name = getClass().getSimpleName();
        return name.toLowerCase(Locale.ROOT);
    }

    public @NonNull String getPermission() {

        String node = "currencyplus.command.";
        return node + getName();
    }

    public abstract void execute(@NonNull CommandSender sender, @NonNull String[] args);


    protected @Nullable Player getPlayer(@NonNull CommandSender sender, @NonNull String playerName) {

        for (Player player : getPlugin().getServer().getOnlinePlayers())
            if (player.getName().equals(playerName))
                return player;

        String message = getMessages().message("player-not-exists");
        sender.sendMessage(message);

        return null;
    }

    protected void messageToSender(@NonNull CommandSender sender, @NonNull Player recipient, double amount) {

        String message = getMessages().message(getPath() + ".sender", recipient);
        sender.sendMessage(getConfig().convertBalance(message, amount));
    }

    protected void messageToRecipient(@NonNull CommandSender sender, @NonNull Player recipient, double amount) {

        String message = getMessages().message(getPath() + ".recipient", recipient);
        message = getConfig().convertBalance(message, amount);

        recipient.sendMessage(message.replace("%executor", sender.getName()));
    }
}