package nocom.dehucka.telegrambot.zbot.service.telergammessage;

import lombok.NonNull;
import nocom.dehucka.telegrambot.zbot.model.TelegramMessage;
import nocom.dehucka.telegrambot.zbot.service.telergammessage.argument.CreateTelegramMessageArgument;

import java.util.Optional;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
public interface TelegramMessageService {

    TelegramMessage create(CreateTelegramMessageArgument argument);

    Optional<TelegramMessage> getLastMessageWithCommand(@NonNull Long chatId);
}
