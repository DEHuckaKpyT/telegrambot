package nocom.dehucka.telegrambot.service.user;

import lombok.RequiredArgsConstructor;
import nocom.dehucka.telegrambot.model.TelegramUser;
import nocom.dehucka.telegrambot.model.UserSettings;
import nocom.dehucka.telegrambot.repository.TelegramUserRepository;
import nocom.dehucka.telegrambot.service.user.argument.CreateTelegramUserArgument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository repository;

    @Override
    @Transactional
    public TelegramUser create(CreateTelegramUserArgument argument) {
        return repository.save(TelegramUser.builder()
                                           .chatId(argument.getChatId())
                                           .settings(new UserSettings())
                                           .build());
    }

    @Override
    public TelegramUser createIfNotExists(CreateTelegramUserArgument argument) {
        Long chatId = argument.getChatId();

        return repository.findByChatId(chatId)
                         .orElseGet(() -> repository.save(TelegramUser.builder()
                                                                      .chatId(chatId)
                                                                      .settings(new UserSettings())
                                                                      .build()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelegramUser> getAll() {
        return repository.findAll();
    }
}
