package nocom.dehucka.telegrambot.zbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created on 19.03.2022.
 *
 * @author Denis Matytsin
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    private String command;

    @Column(columnDefinition = "text")
    private String messageText;

    @Column(nullable = false)
    private Date createDate;

    @PrePersist
    private void setCreateDate() {
        this.createDate = new Date();
    }
}
