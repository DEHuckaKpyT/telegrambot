package nocom.dehucka.telegrambot.zbot.repository;

import nocom.dehucka.telegrambot.zbot.model.TelegramMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 19.03.2022.
 *
 * @author Denis Matytsin
 */
public interface TelegramMessageRepository extends JpaRepository<TelegramMessage, Long> {

    Optional<TelegramMessage> findFirstByChatIdAndCommandIsNotNullOrderByCreateDateDesc(Long chatId);
}
