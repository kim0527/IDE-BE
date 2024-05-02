package api.v1.ide.stomp.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StompInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert headerAccessor != null;

        if (headerAccessor.getCommand() == StompCommand.CONNECT) {
            // JWT 토큰 검증하기
        }
        return message;
    }

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT:
                log.info("{}가 Websocket으로 connect에 성공했습니다.",sessionId);
                break;
            case DISCONNECT:
                log.info("{}가 disconnect 되었습니다.",sessionId);
                break;
            default:
                break;
        }

    }

}
