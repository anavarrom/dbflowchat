package dbflow.chat.web.websocket;

import static dbflow.chat.config.WebsocketConfiguration.IP_ADDRESS;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import dbflow.chat.domain.Chat;
import dbflow.chat.domain.Message;

@Controller
public class ChatService implements ApplicationListener<SessionDisconnectEvent>{
	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/newMessage")
    @SendTo("/chat/newMessageOK")
    public Message sendMessage(@Payload Message messageDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
    	// chatDTO.setUserLogin(principal.getName());
    	// chatDTO.setSessionId(stompHeaderAccessor.getSessionId());
    	// chatDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
    	// chatDTO.setTime(Instant.now());
    	messageDTO.setReceivedDate(LocalDate.now());
        log.debug("Sending user message {}", messageDTO);
        return messageDTO;
    }

    @MessageMapping("/chat/newMessage2")
    @SendTo("/chat/newMessageOK")
    public Message sendMessage2(@Payload String messageDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
    	// chatDTO.setUserLogin(principal.getName());
    	// chatDTO.setSessionId(stompHeaderAccessor.getSessionId());
    	// chatDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
    	// chatDTO.setTime(Instant.now());
    	Message msg = new Message();
    	msg.setBody(messageDTO);    	
    	msg.setReceivedDate(LocalDate.now());
        log.debug("Sending user message {}", msg);
        return msg;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        // ActivityDTO activityDTO = new ActivityDTO();
        // activityDTO.setSessionId(event.getSessionId());
        // activityDTO.setPage("logout");
        // messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
    }
}
