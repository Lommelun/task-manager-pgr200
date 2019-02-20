package no.kristiania.server.db.pojo;

public class Task {
    private String name;
    private int status;
    private int id;


    public Task(String name, int status, int id) {
        this.name = name;
        this.status = status;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
