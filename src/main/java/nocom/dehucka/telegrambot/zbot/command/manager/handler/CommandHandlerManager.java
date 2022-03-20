package nocom.dehucka.telegrambot.zbot.command.manager.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
public interface CommandHandlerManager {

    List<SendMessage> getMessage(Long chatId, String command, Update update);

    List<SendMessage> getIntroMessage(Long chatId, String command, Update update);
}
