package nocom.dehucka.telegrambot.action;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.zbot.config.TelegramBot;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
@RequiredArgsConstructor
public class SendMessageAction {

    private final TelegramBot telegramBot;

    public void sendAllByChatId(Set<Long> chatIds, String text) {
        chatIds.forEach(chatId -> telegramBot.send(chatId, text));
    }
}
