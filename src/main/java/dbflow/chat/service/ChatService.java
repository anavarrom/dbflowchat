package dbflow.chat.service;

import dbflow.chat.service.dto.ChatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link dbflow.chat.domain.Chat}.
 */
public interface ChatService {

    /**
     * Save a chat.
     *
     * @param chatDTO the entity to save.
     * @return the persisted entity.
     */
    ChatDTO save(ChatDTO chatDTO);

    /**
     * Get all the chats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatDTO> findOne(Long id);

    /**
     * Delete the "id" chat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the user chats.
     *
     * @param username of the user.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatDTO> findAllUserChats(String username, Pageable pageable);

}
