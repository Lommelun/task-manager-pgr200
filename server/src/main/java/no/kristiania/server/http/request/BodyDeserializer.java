package no.kristiania.server.http.request;

import com.google.gson.*;
import no.kristiania.shared.dto.AssignRequestDTO;
import no.kristiania.shared.dto.BodyDTO;
import no.kristiania.shared.dto.TaskDTO;
import no.kristiania.shared.dto.ContributorDTO;

import java.lang.reflect.Type;

class BodyDeserializer implements JsonDeserializer<BodyDTO> {

    @Override
    public BodyDTO deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject;
        if ((jsonObject = json.getAsJsonObject()) == null) {
            return raw(json);
        }

        // Get type field of json object for mapping
        String type = jsonObject.get("type") != null ? jsonObject.get("type").getAsString() : "";

        switch (type) {
            case "task":
                return task(jsonObject);
            case "user":
                return user(jsonObject);
            case "assign":
                return assign(jsonObject);
            default:
                return raw(jsonObject);
        }
    }

    private BodyDTO assign(JsonObject jsonObject) {
        AssignRequestDTO request = new AssignRequestDTO();

        if (jsonObject.get("user_id") != null && jsonObject.get("task_id") != null) {
            request.setOwner(jsonObject.get("user_id").getAsInt());
            request.setTask(jsonObject.get("task_id").getAsInt());
        } else return raw(jsonObject);

        return request;
    }

    private BodyDTO user(JsonObject jsonObject) {
        ContributorDTO contributor = new ContributorDTO();

        if (jsonObject.get("user_id") != null) {
            contributor.setId(jsonObject.get("user_id").getAsInt());
        }

        if (jsonObject.get("name") != null) {
            contributor.setName(jsonObject.get("name").getAsString());
        } else return raw(jsonObject);

        return contributor;
    }

    private BodyDTO task(JsonObject jsonObject) {
        TaskDTO task = new TaskDTO();

        if (jsonObject.get("task_id") != null) {
            task.setId(jsonObject.get("task_id").getAsInt());
        }

        if (jsonObject.get("status") != null && jsonObject.get("name") != null) {
            task.setStatus(jsonObject.get("status").getAsInt());
            task.setName(jsonObject.get("name").getAsString());
        } else return raw(jsonObject);

        return task;
    }

    private BodyDTO raw(JsonElement json) {
        BodyDTO body = new BodyDTO();
        body.setRaw(json.toString());
        return body;
    }
}