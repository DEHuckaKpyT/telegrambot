package nocom.dehucka.telegrambot.handler;

import nocom.dehucka.telegrambot.zbot.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Created on 19.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
public class StartCommandHandler extends CommandHandler {

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setParseMode("markdown");
        sendMessage.setText("Здесь что-то будет немного позже :/");
        return sendMessage;
    }
}
