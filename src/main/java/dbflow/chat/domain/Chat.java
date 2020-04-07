package dbflow.chat.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import dbflow.chat.domain.enumeration.ChatType;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "jhi_to")
    private String to;

    @Column(name = "to_id")
    private Long toId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "last_message")
    private LocalDate lastMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChatType type;

    @OneToMany(mappedBy = "chat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public Chat owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Chat ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTo() {
        return to;
    }

    public Chat to(String to) {
        this.to = to;
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getToId() {
        return toId;
    }

    public Chat toId(Long toId) {
        this.toId = toId;
        return this;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getSubject() {
        return subject;
    }

    public Chat subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Chat createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastMessage() {
        return lastMessage;
    }

    public Chat lastMessage(LocalDate lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

    public void setLastMessage(LocalDate lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ChatType getType() {
        return type;
    }

    public Chat type(ChatType type) {
        this.type = type;
        return this;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Chat messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Chat addMessage(Message message) {
        this.messages.add(message);
        message.setChat(this);
        return this;
    }

    public Chat removeMessage(Message message) {
        this.messages.remove(message);
        message.setChat(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chat{" +
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
