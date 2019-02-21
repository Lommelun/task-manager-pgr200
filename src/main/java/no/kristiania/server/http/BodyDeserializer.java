package no.kristiania.server.http;

import com.google.gson.*;
import no.kristiania.shared.dto.BodyDTO;
import no.kristiania.shared.dto.TaskDTO;
import no.kristiania.shared.dto.ContributorDTO;

import java.lang.reflect.Type;

class BodyDeserializer implements JsonDeserializer<BodyDTO> {

    @Override
    public BodyDTO deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("type");
        String type = jsonType.getAsString();

        BodyDTO modelType = null;

        switch(type) {
            case "user": {
                modelType = new TaskDTO();
                int id = jsonObject.get("task_id").getAsInt();
                int status = jsonObject.get("status").getAsInt();
                String name = jsonObject.get("name").getAsString();

                ((TaskDTO) modelType).setId(id);
                ((TaskDTO) modelType).setStatus(status);
                ((TaskDTO) modelType).setName(name);
                break;
            }
            case "task": {
                modelType = new ContributorDTO();
                int id = jsonObject.get("user_id").getAsInt();
                String name = jsonObject.get("name").getAsString();

                ((ContributorDTO) modelType).setId(id);
                ((ContributorDTO) modelType).setName(name);
                break;
            }
            default:
        }

        return modelType;
    }
}