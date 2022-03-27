package nocom.dehucka.telegrambot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Created on 25.03.2022.
 *
 * @author Matytsin Denis Olegovich
 */
@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean receiveGoodMorning;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean receiveGoodNight;
}
