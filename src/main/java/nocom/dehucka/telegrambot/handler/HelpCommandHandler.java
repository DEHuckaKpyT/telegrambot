package nocom.dehucka.telegrambot.handler;

import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

/**
 * Created on 19.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
public class HelpCommandHandler extends CommandHandler {

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, Update update) {
        SendMessage sendMessage = SendMessage.builder()
                                             .chatId(chatId.toString())
                                             .parseMode("markdown")
                                             .text("*Бот DEHucku*\n" + "Чтобы посмотреть возможности, введите /options")
                                             .build();

        return Collections.singletonList(sendMessage);
    }
}
