package api.v1.ide.stomp.service;

import api.v1.ide.stomp.controller.EditCodeRequest;
import api.v1.ide.stomp.service.dto.EditCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EditCodeService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @Transactional
    public void sendMessage(EditCodeDto editCodeDto) {
        String topic = channelTopic.getTopic();
        redisTemplate.convertAndSend(topic, editCodeDto);
    }
}