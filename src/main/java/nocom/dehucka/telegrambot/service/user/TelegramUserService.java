package nocom.dehucka.telegrambot.service.user;

import nocom.dehucka.telegrambot.model.TelegramUser;
import nocom.dehucka.telegrambot.service.user.argument.CreateTelegramUserArgument;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
public interface TelegramUserService {

    TelegramUser create(CreateTelegramUserArgument argument);

    TelegramUser createIfNotExists(CreateTelegramUserArgument argument);

    List<TelegramUser> getAll();
}
