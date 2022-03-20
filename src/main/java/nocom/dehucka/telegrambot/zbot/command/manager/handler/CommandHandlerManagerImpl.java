package nocom.dehucka.telegrambot.zbot.command.manager.handler;

import nocom.dehucka.telegrambot.zbot.command.handler.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
public class CommandHandlerManagerImpl implements CommandHandlerManager {

    private final Map<String, CommandHandler> handlersByCommand;

    @Autowired
    public CommandHandlerManagerImpl(List<CommandHandler> handlersByCommand) {
        this.handlersByCommand = handlersByCommand.stream().collect(Collectors.toMap(CommandHandler::getCommand, Function.identity()));
    }

    @Override
    public List<SendMessage> getMessage(Long chatId, String command, Update update) {
        return handlersByCommand.getOrDefault(command, handlersByCommand.get("/help")).getMessage(chatId, update);
    }

    @Override
    public List<SendMessage> getIntroMessage(Long chatId, String command, Update update) {
        return handlersByCommand.getOrDefault(command, handlersByCommand.get("/help")).getIntroMessage(chatId, update);
    }
}
