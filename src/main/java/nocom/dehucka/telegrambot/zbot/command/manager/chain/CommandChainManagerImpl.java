package nocom.dehucka.telegrambot.zbot.command.manager.chain;

import com.fasterxml.jackson.core.type.TypeReference;
import nocom.dehucka.telegrambot.zbot.model.TelegramMessage;
import nocom.dehucka.telegrambot.zbot.service.telergammessage.TelegramMessageService;
import nocom.dehucka.telegrambot.zbot.util.SerializingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
@Component
public class CommandChainManagerImpl implements CommandChainManager {

    private final TelegramMessageService messageService;
    private final Map<String, String> commandChains;

    @Autowired
    public CommandChainManagerImpl(TelegramMessageService messageService) throws IOException {
        this.messageService = messageService;
        this.commandChains = SerializingUtils.deserializeJson("chain.json", new TypeReference<Map<String, String>>() {});
    }

    @Override
    public String getNextCommand(String command) {
        return commandChains.getOrDefault(command, "/help");
    }

    @Override
    public String getNextCommand(Long chatId) {
        return getNextCommand(getPreviousCommand(chatId));
    }

    @Override
    public String getPreviousCommand(Long chatId) {
        return messageService.getLastMessageWithCommand(chatId)
                             .map(TelegramMessage::getCommand)
                             .orElse("/help");
    }
}
