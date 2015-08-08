package http;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author Sergey Pensov
 */
public abstract class APIRequestHandler {

    public abstract JSONObject handleRequest(HttpServletRequest request) throws IOException;

}
