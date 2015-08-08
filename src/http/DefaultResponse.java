package http;

import org.json.simple.JSONObject;

/**
 * @Author Sergey Pensov
 */
public class DefaultResponse {
    public static final JSONObject ERROR_INCORRECT_REQUEST;

    static {
        JSONObject response = new JSONObject();
        response.put("errorCode", 1);
        response.put("errorDescription", "No request type specified");
        ERROR_INCORRECT_REQUEST = response;
    }
}
