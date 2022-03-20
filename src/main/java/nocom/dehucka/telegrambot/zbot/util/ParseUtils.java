package nocom.dehucka.telegrambot.zbot.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created on 20.03.2022.
 *
 * @author Denis Matytsin
 */
public class ParseUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    public static <T> T parseJson(String resourcePath, Class<T> clazz) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        return OBJECT_MAPPER.readValue(resource.getInputStream(), clazz);
    }

    public static <T> T parseJson(String resourcePath, TypeReference<T> typeReference) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        return OBJECT_MAPPER.readValue(resource.getInputStream(), typeReference);
    }

    static {
        OBJECT_MAPPER = (new ObjectMapper()).setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
