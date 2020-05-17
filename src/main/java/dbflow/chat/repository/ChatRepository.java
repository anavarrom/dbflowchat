package dbflow.chat.repository;

import dbflow.chat.domain.Chat;
import dbflow.chat.service.dto.ChatDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Chat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	// User data access services
	Page<Chat> findByOwnerOrToOrderByLastMessage(String owner, String to, Pageable pageable);
}
