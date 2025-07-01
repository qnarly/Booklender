package kg.attractor.java.server;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.user.User;

import java.util.Optional;

public interface RouteHandlerAuth {
    void handle(HttpExchange exchange, Optional<User> user);
}
