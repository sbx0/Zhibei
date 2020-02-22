package cn.sbx0.zhibei.logic.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
public class WebSocketController {
    @Resource
    private SimpMessagingTemplate template;

    /**
     * todo
     *
     * @param message
     */
    @MessageMapping("/ws/sendToServer")
    public void sendServer(String message) {
        log.info("message:{}", message);
    }

    /**
     * todo
     *
     * @param message
     * @return
     */
    @MessageMapping("/ws/sendToPublic")
    @SendTo("/topic/public")
    public String sendAllUser(String message) {
        return message;
    }

    /**
     * todo
     *
     * @param map
     */
    @MessageMapping("/ws/sendToSomeone")
    public void sendMyUser(@RequestBody Map<String, String> map) {
        log.info("map = {}", map);
        WebSocketSession webSocketSession = SocketManager.get(map.get("name"));
        if (webSocketSession != null) {
            log.info("sessionId = {}", webSocketSession.getId());
            template.convertAndSendToUser(map.get("name"), "/queue/msg", map.get("message"));
        }
    }

}