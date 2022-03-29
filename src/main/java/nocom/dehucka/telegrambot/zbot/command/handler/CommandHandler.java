package nocom.dehucka.telegrambot.zbot.command.handler;

import com.google.common.collect.Lists;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created on 28.02.2022.
 *
 * @author Denis Matytsin
 */
public abstract class CommandHandler {

    public abstract String getCommand();

    public abstract List<PartialBotApiMethod> getMessage(Long chatId, Update update);

    public List<PartialBotApiMethod> getIntroMessage(Long chatId, Update update){
        return Collections.emptyList();
    }
}
