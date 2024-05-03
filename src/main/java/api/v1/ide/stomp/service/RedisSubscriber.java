package api.v1.ide.stomp.service;

import api.v1.ide.stomp.service.dto.EditCodeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

            EditCodeDto editCodeDto = objectMapper.readValue(publishMessage, EditCodeDto.class);
            messagingTemplate.convertAndSend("/topic/edit/" + editCodeDto.roomId(), editCodeDto.code());
        } catch (Exception e) {
            log.error("error={}",e.getMessage());
        }
    }
}
