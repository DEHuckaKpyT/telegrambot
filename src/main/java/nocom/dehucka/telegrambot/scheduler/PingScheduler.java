package nocom.dehucka.telegrambot.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created on 12.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
@RequiredArgsConstructor
public class PingScheduler {

    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 29000)
    public void pingSelf() {
//        String string = restTemplate.getForObject("https://dehucka-kpyt.herokuapp.com/actuator", String.class);
    }
}
