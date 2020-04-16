package dbflow.chat.repository;

import dbflow.chat.domain.Message;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
		List<Message> findByFrom(String from, Pageable pageable);
		Page<Message> findAllByChatId(Long chatId, Pageable pageable);
		
		// List<Message> findByFrom(String from, Pageable pageable);		
}
