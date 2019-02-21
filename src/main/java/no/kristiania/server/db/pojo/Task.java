package no.kristiania.server.db.pojo;

public class Task {
    private String name;
    private int status;
    private final int id;

    public Task(String name, int status, int id) {
        this.name = name;
        this.status = status;
        this.id = id;
    }

    public Task(String name, int status) {
        this.name = name;
        this.status = status;
        this.id = -1;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;
        return id == ((Task) obj).id;
    }
}
