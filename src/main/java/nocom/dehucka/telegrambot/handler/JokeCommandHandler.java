package nocom.dehucka.telegrambot.handler;

import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import nocom.dehucka.telegrambot.zbot.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
public class JokeCommandHandler extends CommandHandler {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getCommand() {
        return "/joke";
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, Update update) {
        String fooResourceUrl = "http://rzhunemogu.ru/Rand.aspx?CType=1";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        if (response.getStatusCode() != HttpStatus.OK) throw new CustomException("Шутки, кажется, пока не работают :(");

        String body = response.getBody();

        return Collections.singletonList(SendMessage.builder()
                                                     .chatId(chatId.toString())
                                                     .text(body.substring(53, body.length() - 17))
                                                     .build());
    }
}
