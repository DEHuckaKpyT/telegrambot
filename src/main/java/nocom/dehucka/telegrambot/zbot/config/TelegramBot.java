package nocom.dehucka.telegrambot.zbot.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nocom.dehucka.telegrambot.zbot.exception.CustomException;
import nocom.dehucka.telegrambot.zbot.handler.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 28.02.2022.
 *
 * @author Denis Matytsin
 */
@Slf4j
@Configuration
public class TelegramBot extends TelegramLongPollingBot {

    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;

    private final Map<String, CommandHandler> handlersByCommand;

    @Autowired
    public TelegramBot(@Value("${telegramBot.name}") String botName,
                       @Value("${telegramBot.token}") String botToken,
                       List<CommandHandler> handlersByCommand) {
        this.botUsername = botName;
        this.botToken = botToken;
        this.handlersByCommand = handlersByCommand.stream().collect(Collectors.toMap(CommandHandler::getCommand, Function.identity()));
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) return;

        Message message = update.getMessage();
        Long chatId = message.getChatId();

        if (!message.hasText()) return;
        String text = message.getText();

        try {
            if (text.startsWith("/")) {
                send(handlersByCommand.getOrDefault(text, handlersByCommand.get("/unknown")).getMessage(chatId));
            } else {
                throw new CustomException("Я пока не реагирую на обычные текстовые сообщения :(");
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());

            if (exception instanceof CustomException) {
                send(chatId, ((CustomException) exception).getMessage());
            } else {
                send(chatId, "Что-то пошло не так :/");
            }
        }
    }

    public void send(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setParseMode("markdown");
        sendMessage.setText(text);

        send(sendMessage);
    }

    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException telegramApiException) {
            log.error(telegramApiException.getMessage());
        }
    }
}
