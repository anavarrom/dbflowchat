package dbflow.chat.web.rest;

import dbflow.chat.DbflowchatApp;
import dbflow.chat.config.TestSecurityConfiguration;
import dbflow.chat.domain.Chat;
import dbflow.chat.repository.ChatRepository;
import dbflow.chat.service.ChatService;
import dbflow.chat.service.dto.ChatDTO;
import dbflow.chat.service.mapper.ChatMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dbflow.chat.domain.enumeration.ChatType;
/**
 * Integration tests for the {@link ChatResource} REST controller.
 */
@SpringBootTest(classes = { DbflowchatApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class ChatResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MESSAGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MESSAGE = LocalDate.now(ZoneId.systemDefault());

    private static final ChatType DEFAULT_TYPE = ChatType.NOTIFICATION;
    private static final ChatType UPDATED_TYPE = ChatType.NOTIFICATION;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatService chatService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChatMockMvc;

    private Chat chat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createEntity(EntityManager em) {
        Chat chat = new Chat()
            .subject(DEFAULT_SUBJECT)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastMessage(DEFAULT_LAST_MESSAGE)
            .type(DEFAULT_TYPE);
        return chat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createUpdatedEntity(EntityManager em) {
        Chat chat = new Chat()
            .subject(UPDATED_SUBJECT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastMessage(UPDATED_LAST_MESSAGE)
            .type(UPDATED_TYPE);
        return chat;
    }

    @BeforeEach
    public void initTest() {
        chat = createEntity(em);
    }

    @Test
    @Transactional
    public void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);
        restChatMockMvc.perform(post("/api/chats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isCreated());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testChat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChat.getLastMessage()).isEqualTo(DEFAULT_LAST_MESSAGE);
        assertThat(testChat.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat with an existing ID
        chat.setId(1L);
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMockMvc.perform(post("/api/chats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList
        restChatMockMvc.perform(get("/api/chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastMessage").value(hasItem(DEFAULT_LAST_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chat.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastMessage").value(DEFAULT_LAST_MESSAGE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChat() throws Exception {
        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();
        // Disconnect from session so that the updates on updatedChat are not directly saved in db
        em.detach(updatedChat);
        updatedChat
            .subject(UPDATED_SUBJECT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastMessage(UPDATED_LAST_MESSAGE)
            .type(UPDATED_TYPE);
        ChatDTO chatDTO = chatMapper.toDto(updatedChat);

        restChatMockMvc.perform(put("/api/chats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testChat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testChat.getLastMessage()).isEqualTo(UPDATED_LAST_MESSAGE);
        assertThat(testChat.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc.perform(put("/api/chats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        restChatMockMvc.perform(delete("/api/chats/{id}", chat.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
