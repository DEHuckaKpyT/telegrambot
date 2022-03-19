package nocom.dehucka.telegrambot.zbot.exception;

/**
 * Created on 19.03.2022.
 *
 * @author Denis Matytsin
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
