package nocom.dehucka.telegrambot.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created on 12.03.2022.
 *
 * @author Denis Matytsin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PingScheduler {

    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 29000)
    public void pingSelf() {
        try {
            String string = restTemplate.getForObject("https://dehucka-kpyt.herokuapp.com/actuator", String.class);
            log.info("Ping was executed");
        } catch (Exception ex) {
            log.error("Error while ping self\n" + ex.getMessage());
        }
    }
}
