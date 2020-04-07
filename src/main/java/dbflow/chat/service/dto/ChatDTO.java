package dbflow.chat.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import dbflow.chat.domain.enumeration.ChatType;

/**
 * A DTO for the {@link dbflow.chat.domain.Chat} entity.
 */
public class ChatDTO implements Serializable {
    
    private Long id;

    private String owner;

    private Long ownerId;

    private String to;

    private Long toId;

    private String subject;

    private LocalDate createdDate;

    private LocalDate lastMessage;

    private ChatType type;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LocalDate lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChatDTO chatDTO = (ChatDTO) o;
        if (chatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            ", ownerId=" + getOwnerId() +
            ", to='" + getTo() + "'" +
            ", toId=" + getToId() +
            ", subject='" + getSubject() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastMessage='" + getLastMessage() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
