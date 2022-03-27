package nocom.dehucka.telegrambot.handler;

import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import nocom.dehucka.telegrambot.zbot.util.SerializingUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
    public List<SendMessage> getIntroMessage(Long chatId, Update update) {
        return getMessage(chatId, update);
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("*Текущие возможности:*");
        sendMessage.setReplyMarkup(getKeyboard());
        return Collections.singletonList(sendMessage);
    }

    private ReplyKeyboard getKeyboard() {
        InlineKeyboardButton button = new InlineKeyboardButton();

        String callbackData = SerializingUtils.serializeCallbackData("/joke");

        button.setText("Получить шуточку");
        button.setCallbackData(callbackData);
        return new InlineKeyboardMarkup(Collections.singletonList(Collections.singletonList(button)));
    }
}
