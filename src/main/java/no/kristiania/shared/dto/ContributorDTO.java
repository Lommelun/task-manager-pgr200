package no.kristiania.shared.dto;

import com.google.gson.annotations.SerializedName;

public class ContributorDTO extends BodyDTO {
    @SerializedName("name")
    private String name;

    @SerializedName("user_id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
