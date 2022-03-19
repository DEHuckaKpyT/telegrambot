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
public class UnknownCommandHandler extends CommandHandler {

    @Override
    public String getCommand() {
        return "/unknown";
    }

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setParseMode("markdown");
        sendMessage.setText("Я не знаю такую команду :(");
        return sendMessage;
    }
}
