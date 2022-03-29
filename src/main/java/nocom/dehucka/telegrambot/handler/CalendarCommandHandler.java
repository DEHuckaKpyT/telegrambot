package nocom.dehucka.telegrambot.handler;

import com.google.common.collect.Lists;
import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static nocom.dehucka.telegrambot.zbot.util.SerializingUtils.deserializeCallbackData;
import static nocom.dehucka.telegrambot.zbot.util.SerializingUtils.serializeCallbackData;

/**
 * Created on 29.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
public class CalendarCommandHandler extends CommandHandler {

    private final List<String> DAYS = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7",
                                                         "8", "9", "10", "11", "12", "13", "14",
                                                         "15", "16", "17", "18", "19", "20", "21",
                                                         "22", "23", "24", "25", "26", "27", "28");

    private Map<String, Boolean> daysSelected = new HashMap<>() {{
        put("8:00 - 9:00", false);
        put("9:00 - 10:00", false);
        put("10:00 - 11:00", false);
        put("11:00 - 12:00", false);
        put("12:00 - 13:00", false);
        put("13:00 - 14:00", false);
        put("14:00 - 15:00", false);
        put("15:00 - 16:00", false);
        put("16:00 - 17:00", false);
        put("17:00 - 18:00", false);
    }};

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

        if (!update.hasCallbackQuery()) {
            sendMessage.setReplyMarkup(getMonthKeyboard());
            return Collections.singletonList(sendMessage);
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        String[] strings = deserializeCallbackData(callbackQuery.getData());

        if (strings.length == 1) {
            sendMessage.setReplyMarkup(getMonthKeyboard());
            return Collections.singletonList(sendMessage);
        }

        String value = strings[1];

        if (value.equals("month")) {
            EditMessageReplyMarkup edit = new EditMessageReplyMarkup(chatId.toString(),
                                                                     callbackQuery.getMessage().getMessageId(),
                                                                     callbackQuery.getInlineMessageId(),
                                                                     getMonthKeyboard());
            AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                                                            .callbackQueryId(callbackQuery.getId())
                                                            .text("Отобразить только что выбранное время")
                                                            .showAlert(false)
                                                            .cacheTime(7)
                                                            .build();

            return Lists.newArrayList(edit, answer);
        }

        if (DAYS.contains(value)) {
            EditMessageReplyMarkup edit = new EditMessageReplyMarkup(chatId.toString(),
                                                                     callbackQuery.getMessage().getMessageId(),
                                                                     callbackQuery.getInlineMessageId(),
                                                                     getDayKeyboard());
            return Collections.singletonList(edit);
        }

        if (daysSelected.containsKey(value)) {
            daysSelected.put(value, !daysSelected.get(value));

            EditMessageReplyMarkup edit = new EditMessageReplyMarkup(chatId.toString(),
                                                                     callbackQuery.getMessage().getMessageId(),
                                                                     callbackQuery.getInlineMessageId(),
                                                                     getDayKeyboard());
            return Collections.singletonList(edit);
        }

        return null;
    }

    private InlineKeyboardMarkup getMonthKeyboard() {
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();
        buttonRow.add(InlineKeyboardButton.builder()
                                          .text("такой-то месяц такого-то года")
                                          .callbackData(serializeCallbackData("/calendar"))
                                          .build());

        List<InlineKeyboardButton> buttonRow0 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow4 = new ArrayList<>();

        List<InlineKeyboardButton> buttonRow5 = new ArrayList<>();
        buttonRow5.add(InlineKeyboardButton.builder()
                                           .text("<")
                                           .callbackData(serializeCallbackData("/calendar"))
                                           .build());
        buttonRow5.add(InlineKeyboardButton.builder()
                                           .text("Выбрать месяц")
                                           .callbackData(serializeCallbackData("/calendar"))
                                           .build());
        buttonRow5.add(InlineKeyboardButton.builder()
                                           .text(">")
                                           .callbackData(serializeCallbackData("/calendar"))
                                           .build());

        buttonRow0.add(getButton("ПН"));
        buttonRow0.add(getButton("ВТ"));
        buttonRow0.add(getButton("СР"));
        buttonRow0.add(getButton("ЧТ"));
        buttonRow0.add(getButton("ПТ"));
        buttonRow0.add(getButton("СБ"));
        buttonRow0.add(getButton("ВС"));

        for (int i = 1; i <= 7; i++) {
            buttonRow1.add(getButton(String.valueOf(i)));
        }
        for (int i = 8; i <= 14; i++) {
            buttonRow2.add(getButton(String.valueOf(i)));
        }
        for (int i = 15; i <= 21; i++) {
            buttonRow3.add(getButton(String.valueOf(i)));
        }
        for (int i = 22; i <= 28; i++) {
            buttonRow4.add(getButton(String.valueOf(i)));
        }

        return new InlineKeyboardMarkup(Lists.newArrayList(buttonRow, buttonRow0, buttonRow1, buttonRow2, buttonRow3, buttonRow4, buttonRow5));
    }

    private InlineKeyboardButton getButton(String value) {
        return InlineKeyboardButton.builder()
                                   .text(value)
                                   .callbackData(serializeCallbackData("/calendar", value))
                                   .build();
    }

    private InlineKeyboardMarkup getDayKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttonRow0 = new ArrayList<>();
        buttonRow0.add(InlineKeyboardButton.builder()
                                           .text("Возможное")
                                           .callbackData(serializeCallbackData("/calendar"))
                                           .build());
        buttonRow0.add(InlineKeyboardButton.builder()
                                           .text("Выбранное")
                                           .callbackData(serializeCallbackData("/calendar"))
                                           .build());

        buttons.add(buttonRow0);

        for (int i = 0; i < 10; i++) {
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            String buttonText = (i + 8) + ":00 - " + (i + 9) + ":00";

            if (daysSelected.get(buttonText)) {
                buttonRow.add(getButton("+"));
                buttonRow.add(getButton(buttonText));

            } else {
                buttonRow.add(getButton(buttonText));
                buttonRow.add(getButton("-"));
            }

            buttons.add(buttonRow);
        }

        buttons.add(Collections.singletonList(InlineKeyboardButton.builder()
                                                                  .text("Выбрать ещё")
                                                                  .callbackData(serializeCallbackData("/calendar", "month"))
                                                                  .build()));

        return new InlineKeyboardMarkup(buttons);
    }
}
