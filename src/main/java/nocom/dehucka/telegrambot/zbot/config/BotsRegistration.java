package nocom.dehucka.telegrambot.zbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Created on 18.03.2022.
 *
 * @author Denis Matytsin
 */
@Slf4j
@Configuration
public class BotsRegistration {

    @Autowired
    public void registerBots(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
        log.info("Bot " + telegramBot.getBotUsername() + " started");
    }
}
