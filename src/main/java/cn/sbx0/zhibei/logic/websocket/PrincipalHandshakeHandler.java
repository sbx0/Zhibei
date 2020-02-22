package cn.sbx0.zhibei.logic.websocket;

import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * todo
 */
@Slf4j
@Component
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private UserBaseService service;

    @Autowired
    public void setService(UserBaseService service) {
        this.service = service;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();
            final String key = service.getLoginUserName(httpRequest);
            if (key == null) return null;
            return () -> key;
        }
        return null;
    }
}
