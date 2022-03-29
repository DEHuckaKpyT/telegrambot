package nocom.dehucka.telegrambot.handler;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.action.GetJokeAction;
import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import nocom.dehucka.telegrambot.zbot.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

/**
 * Created on 23.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
@RequiredArgsConstructor
public class JokeCommandHandler extends CommandHandler {

    private final GetJokeAction getJokeAction;

    @Override
    public String getCommand() {
        return "/joke";
    }

    @Override
    public List<PartialBotApiMethod> getMessage(Long chatId, Update update) {
        String joke = getJokeAction.getJoke();

        return Collections.singletonList(SendMessage.builder()
                                                     .chatId(chatId.toString())
                                                     .text(joke)
                                                     .build());
    }
}
