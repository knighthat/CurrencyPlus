package me.knighthat.plugin;

import lombok.NonNull;
import me.knighthat.commands.BalanceCommand;
import me.knighthat.commands.TransferCommand;
import me.knighthat.commands.plugin.MainCommandManager;
import me.knighthat.files.Config;
import me.knighthat.files.Messages;
import me.knighthat.vault.ContainerStorage;
import me.knighthat.vault.FileStorage;
import me.knighthat.vault.StorageImpl;
import me.knighthat.vault.VaultAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class CurrencyPlus extends JavaPlugin {

    public final @NonNull Messages messages = new Messages(this);
    public final @NonNull Config config = new Config(this);
    public StorageImpl vault;

    @Override
    public void onEnable() {

        if (!setupEconomy()) {

            getLogger().warning("Switching to local storage...");

            try {

                vault = new ContainerStorage(this);
            } catch (NoClassDefFoundError e) {

                getLogger().warning("Older version detected! Generating file to storage data.");

                vault = new FileStorage(this);
            }
        }

        registerCommands();
    }

    @Override
    public void onDisable() {

        vault.update();
    }

    private void registerCommands() {

        new BalanceCommand(this);
        new MainCommandManager(this);
        new TransferCommand(this);
    }

    private boolean setupEconomy() {

        if (getServer().getPluginManager().getPlugin("Vault") == null) {

            getLogger().warning("Vault is not installed!");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {

            getLogger().warning("Could not link to any Economy plugin! Make sure one is installed");
            return false;
        }

        vault = new VaultAPI(rsp.getProvider());
        return true;
    }
}
