package nocom.dehucka.telegrambot.handler;

import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
public class TestCommandHandler extends CommandHandler {

    @Override
    public String getCommand() {
        return "/test";
    }

    @Override
    public List<SendMessage> getIntroMessage(Long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("test intro");
        return Collections.singletonList(sendMessage);
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setParseMode("markdown");
        sendMessage.setText("**TEST** MESSAGE \"" + update.getMessage().getText() + "\"");
        return Collections.singletonList(sendMessage);
    }
}
