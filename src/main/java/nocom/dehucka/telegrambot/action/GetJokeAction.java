package nocom.dehucka.telegrambot.action;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.zbot.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
@RequiredArgsConstructor
public class GetJokeAction {

    private final RestTemplate restTemplate;

    public String getJoke() {
        String fooResourceUrl = "http://rzhunemogu.ru/Rand.aspx?CType=1";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        if (response.getStatusCode() != HttpStatus.OK) throw new CustomException("Шутки, кажется, пока не работают :(");

        String body = response.getBody();
        String joke = body.substring(53, body.length() - 17);

        return joke;
    }
}
