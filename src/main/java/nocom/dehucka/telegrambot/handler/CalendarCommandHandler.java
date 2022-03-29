package nocom.dehucka.telegrambot.handler;

import com.google.common.collect.Lists;
import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static nocom.dehucka.telegrambot.zbot.util.SerializingUtils.serializeCallbackData;

/**
 * Created on 29.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
public class CalendarCommandHandler extends CommandHandler {

    @Override
    public String getCommand() {
        return "/calendar";
    }

    @Override
    public List<PartialBotApiMethod> getMessage(Long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("*Календарь*");

        if(update.hasCallbackQuery()){

            
        }


        sendMessage.setReplyMarkup(getKeyboard());
        return Collections.singletonList(sendMessage);

//        EditMessageReplyMarkup edit = new EditMessageReplyMarkup(chatId.toString(),
//                                                                 update.getCallbackQuery().getMessage().getMessageId(),
//                                                                 update.getCallbackQuery().getInlineMessageId(),
//                                                                 getKeyboard2());

//        return Collections.singletonList(edit);
    }

    private ReplyKeyboard getKeyboard() {
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            buttonRow.add(getButton(String.valueOf(i)));
        }
        for (int i = 0; i < 7; i++) {
            buttonRow2.add(getButton(String.valueOf(i)));
        }

        return new InlineKeyboardMarkup(Lists.newArrayList(buttonRow,buttonRow2));
    }

    private InlineKeyboardButton getButton(String number) {
        return InlineKeyboardButton.builder()
                                   .text(number)
                                   .callbackData(serializeCallbackData("/calendar", number))
                                   .build();
    }

    private InlineKeyboardMarkup getKeyboard2() {
        InlineKeyboardButton button = new InlineKeyboardButton();

        String callbackData = serializeCallbackData("/start");

        button.setText("test");
        button.setCallbackData(callbackData);
        return new InlineKeyboardMarkup(Lists.newArrayList(Collections.singletonList(button),
                                                           Collections.singletonList(button)));
    }
}
