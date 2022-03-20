package nocom.dehucka.telegrambot.zbot.command.manager.chain;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
public interface CommandChainManager {

    String getNextCommand(String command);

    String getNextCommand(Long chatId);

    String getPreviousCommand(Long chatId);
}
