package nocom.dehucka.telegrambot.scheduler;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.action.SendMessageAction;
import nocom.dehucka.telegrambot.model.TelegramUser;
import nocom.dehucka.telegrambot.service.user.TelegramUserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Component
@RequiredArgsConstructor
public class EveryDayScheduler {

    private final TelegramUserService userService;
    private final SendMessageAction sendMessageAction;

    @Scheduled(cron = "59 54 6 * * ?", zone = "Asia/Vladivostok")
    public void sayGoodMorning() {
        sendMessageAction.sendAllByChatId(userService.getAll()
                                                     .stream()
                                                     .map(TelegramUser::getChatId)
                                                     .collect(Collectors.toSet()),
                                          "Доброе утро <3");
    }

    @Scheduled(cron = "59 54 21 * * ?", zone = "Asia/Vladivostok")
    public void sayGoodNight() {
        sendMessageAction.sendAllByChatId(userService.getAll()
                                                     .stream()
                                                     .map(TelegramUser::getChatId)
                                                     .collect(Collectors.toSet()),
                                          "Спокойной ночи :с");
    }
}
