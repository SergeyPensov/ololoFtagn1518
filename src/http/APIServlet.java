package http;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Sergey Pensov
 */
public class APIServlet extends HttpServlet {
    static final Map<String, APIRequestHandler> apiRequestHandlers;

    static {
        Map<String, APIRequestHandler> map = new HashMap<>();
        map.put("getSpritePositions", new GetSpritePositions());
        map.put("getMoveForOne", new GetMoveOne());
        map.put("getMoveAll", new GetMoveAll());
        apiRequestHandlers = Collections.unmodifiableMap(map);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject responseObject;
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String requestType = request.getParameter("requestType");
        if (requestType == null) {
            responseObject = DefaultResponse.ERROR_INCORRECT_REQUEST;
            out.print(responseObject);
            out.flush();
            return;
        }

        APIRequestHandler apiRequestHandler = apiRequestHandlers.get(requestType);
        if (apiRequestHandler == null) {
            responseObject = DefaultResponse.ERROR_INCORRECT_REQUEST;
            out.print(responseObject);
            out.flush();
            return;
        }

        responseObject = apiRequestHandler.handleRequest(request);
        out.print(responseObject);
        out.flush();
    }
}
