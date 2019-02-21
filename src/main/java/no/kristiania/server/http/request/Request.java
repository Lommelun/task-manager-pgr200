package no.kristiania.server.http.request;

import com.google.gson.*;
import no.kristiania.shared.dto.BodyDTO;

import java.util.Map;

public class Request {
    public String verb, resource, version;
    private Map<String, String> headers;
    private BodyDTO body;


    public Request(String[] s, Map<String, String> headers, String body) {
        verb = s[0];
        resource = s[1];
        version = s[2];

        this.headers = headers;
        if (headers.get("Content-Type") != null && headers.get("Content-Type").equals("application/json")) {
            try {
                this.body = deserializeBody(body);
            } catch (JsonParseException e) {
                this.body = null;
            }
        }
    }

    private BodyDTO deserializeBody(String body) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BodyDTO.class, new BodyDeserializer());
        Gson gson = gsonBuilder.create();

        return gson.fromJson(body, BodyDTO.class);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public BodyDTO getBody() {
        return body;
    }
}
