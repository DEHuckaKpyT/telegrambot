package nocom.dehucka.telegrambot.handler;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.service.user.TelegramUserService;
import nocom.dehucka.telegrambot.service.user.argument.CreateTelegramUserArgument;
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
@RequiredArgsConstructor
public class StartCommandHandler extends CommandHandler {

    private final TelegramUserService userService;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, Update update) {
        userService.createIfNotExists(CreateTelegramUserArgument.builder()
                                                                .chatId(chatId)
                                                                .build());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("*Бот DEHucku*");
        return Collections.singletonList(sendMessage);
    }
}
