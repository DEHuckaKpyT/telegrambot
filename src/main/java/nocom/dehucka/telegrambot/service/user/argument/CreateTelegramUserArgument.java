package nocom.dehucka.telegrambot.service.user.argument;

import lombok.Builder;
import lombok.Value;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Value
@Builder
public class CreateTelegramUserArgument {

    Long chatId;
}
