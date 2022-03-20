package nocom.dehucka.telegrambot.zbot.service.telergammessage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.zbot.model.TelegramMessage;
import nocom.dehucka.telegrambot.zbot.repository.TelegramMessageRepository;
import nocom.dehucka.telegrambot.zbot.service.telergammessage.argument.CreateTelegramMessageArgument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
@Service
@RequiredArgsConstructor
public class TelegramMessageServiceImpl implements TelegramMessageService {

    private final TelegramMessageRepository repository;

    @Override
    @Transactional
    public TelegramMessage create(@NonNull CreateTelegramMessageArgument argument) {
        return repository.save(TelegramMessage.builder()
                                              .chatId(argument.getChatId())
                                              .command(argument.getCommand())
                                              .messageText(argument.getMessageText())
                                              .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelegramMessage> getLastMessageWithCommand(@NonNull Long chatId) {
        return repository.findFirstByChatIdAndCommandIsNotNullOrderByCreateDateDesc(chatId);
    }
}
