package no.kristiania.server.db.pojo;

public class Contributor {
    private String name;
    private int id;

    public Contributor(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Contributor(String name) {
        this.name = name;
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contributor)) return false;
        Contributor contributor = (Contributor) obj;
        return this.id == contributor.id;
    }
}
