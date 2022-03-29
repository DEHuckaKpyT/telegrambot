package nocom.dehucka.telegrambot.zbot.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nocom.dehucka.telegrambot.zbot.command.manager.chain.CommandChainManager;
import nocom.dehucka.telegrambot.zbot.command.manager.handler.CommandHandlerManager;
import nocom.dehucka.telegrambot.zbot.exception.CustomException;
import nocom.dehucka.telegrambot.zbot.service.telergammessage.TelegramMessageService;
import nocom.dehucka.telegrambot.zbot.service.telergammessage.argument.CreateTelegramMessageArgument;
import nocom.dehucka.telegrambot.zbot.util.SerializingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Created on 28.02.2022.
 *
 * @author Denis Matytsin
 */
@Slf4j
@Configuration
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramMessageService messageService;
    private final CommandChainManager commandChainManager;
    private final CommandHandlerManager commandHandlerManager;

    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;

    @Autowired
    public TelegramBot(@Value("${telegramBot.name}") String botName,
                       @Value("${telegramBot.token}") String botToken,
                       TelegramMessageService messageService,
                       CommandChainManager commandChainManager,
                       CommandHandlerManager commandHandlerManager) {
        this.botUsername = botName;
        this.botToken = botToken;
        this.messageService = messageService;
        this.commandChainManager = commandChainManager;
        this.commandHandlerManager = commandHandlerManager;
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) runCommand(update);

        if (!update.hasMessage()) return;

        Message message = update.getMessage();
        Long chatId = message.getChatId();

        if (!message.hasText()) return;
        String text = message.getText();


        try {
            if (text.startsWith("/")) {
                runCommand(chatId, text, update);
            } else {
                runNextCommand(chatId, text, update);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());

            if (exception instanceof CustomException) {
                send(chatId, exception.getMessage());
            } else {
                send(chatId, "Что-то пошло не так :/");
            }
        }
    }

    public Message send(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setParseMode("markdown");
        sendMessage.setText(text);

        return send(sendMessage);
    }

    public Message send(PartialBotApiMethod method) {
        try {
            if (method instanceof EditMessageReplyMarkup) {
                execute((EditMessageReplyMarkup) method);
            } else if (method instanceof SendMessage) {
                execute((SendMessage) method);
            } else if (method instanceof AnswerCallbackQuery) {
                execute((AnswerCallbackQuery) method);
            }
        } catch (TelegramApiException telegramApiException) {
            log.error(telegramApiException.getMessage());
        }

        return new Message();
    }

    private void runCommand(Long chatId, String command, Update update) {
        messageService.create(CreateTelegramMessageArgument.builder()
                                                           .chatId(chatId)
                                                           .command(command)
                                                           .build());

        List<PartialBotApiMethod> messages = commandHandlerManager.getMessage(chatId, command, update);
        messages.forEach(this::send);

        String nextCommand = commandChainManager.getNextCommand(chatId);
        List<PartialBotApiMethod> introMessages = commandHandlerManager.getIntroMessage(chatId, nextCommand, update);
        introMessages.forEach(this::send);
    }

    private void runCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        String[] strings = SerializingUtils.deserializeCallbackData(update.getCallbackQuery().getData());
        String command = strings[0];

        messageService.create(CreateTelegramMessageArgument.builder()
                                                           .chatId(chatId)
                                                           .command(command)
                                                           .build());

        List<PartialBotApiMethod> messages = commandHandlerManager.getMessage(chatId, command, update);
        messages.forEach(this::send);

        String nextCommand = commandChainManager.getNextCommand(chatId);
        List<PartialBotApiMethod> introMessages = commandHandlerManager.getIntroMessage(chatId, nextCommand, update);
        introMessages.forEach(this::send);
    }

    private void runNextCommand(Long chatId, String text, Update update) {
        messageService.create(CreateTelegramMessageArgument.builder()
                                                           .chatId(chatId)
                                                           .messageText(text)
                                                           .build());

        String nextCommand = commandChainManager.getNextCommand(chatId);
        List<PartialBotApiMethod> messages = commandHandlerManager.getMessage(chatId, nextCommand, update);
        messages.forEach(this::send);
    }
}
