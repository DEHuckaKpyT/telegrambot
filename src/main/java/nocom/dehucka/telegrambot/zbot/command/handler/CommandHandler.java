package nocom.dehucka.telegrambot.zbot.command.handler;

import com.google.common.collect.Lists;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

/**
 * Created on 28.02.2022.
 *
 * @author Denis Matytsin
 */
public abstract class CommandHandler {

    public abstract String getCommand();

    public abstract List<SendMessage> getMessage(Long chatId, Update update);

    public List<SendMessage> getIntroMessage(Long chatId, Update update){
        return Collections.emptyList();
    }
}
