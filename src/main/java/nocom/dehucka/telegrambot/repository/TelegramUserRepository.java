package nocom.dehucka.telegrambot.repository;

import nocom.dehucka.telegrambot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    boolean existsByChatId(Long chatId);

    Optional<TelegramUser> findByChatId(Long chatId);
}
