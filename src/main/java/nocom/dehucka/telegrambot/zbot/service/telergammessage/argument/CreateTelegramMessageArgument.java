package nocom.dehucka.telegrambot.zbot.service.telergammessage.argument;

import lombok.Builder;
import lombok.Value;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
@Value
@Builder
public class CreateTelegramMessageArgument {

    Long chatId;
    String command;
    String messageText;
}
