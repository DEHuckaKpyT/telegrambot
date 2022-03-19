package nocom.dehucka.telegrambot.zbot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Created on 28.02.2022.
 *
 * @author Denis Matytsin
 */
public abstract class CommandHandler {

    public abstract String getCommand();

    public abstract SendMessage getMessage(Long chatId);
}
