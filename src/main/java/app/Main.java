package app;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static FluentLogger logger = FluentLogger.getLogger("tgbot");

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();
        try {
            DefaultBotOptions options = new DefaultBotOptions();
            options.setProxyPort(8081);
            bot.registerBot(new Bot(options));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        logger.log("tgbot", "info", "Telegram bot started");
    }
}
