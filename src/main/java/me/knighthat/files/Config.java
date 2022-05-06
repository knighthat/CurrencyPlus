package me.knighthat.files;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.jetbrains.annotations.Range;

import java.text.MessageFormat;

public class Config extends PluginFiles {

    public Config(CurrencyPlus plugin) {
        super(plugin);
    }

    public @NonNull String formatBalance(double amount) {
        return String.format(getBalanceFormat(), amount);
    }

    public @NonNull String getBalanceFormat() {
        return MessageFormat.format("%.{0}f", getFractionalDigits());
    }

    public int getFractionalDigits() {

        String path = "fractional-digits";

        if (get().contains(path))
            if (get().isInt(path))
                return get().getInt(path);

        return 2;
    }

    public @NonNull String convertBalance(@Range(from = 0, to = Long.MAX_VALUE) double amount) {

        String format = get().getString("format", "{0}{1}"),
                form = get().getString(amount > 1 ? "plural" : "singular"),
                amtStr = String.format(getBalanceFormat(), amount);

        return MessageFormat.format(format, form, amtStr);
    }

    public @NonNull String convertBalance(@NonNull String message, double amount) {

        String result = convertBalance(amount);

        message = message.replace("%amount", result);
        message = message.replace("%balance", result);

        return message;
    }
}
