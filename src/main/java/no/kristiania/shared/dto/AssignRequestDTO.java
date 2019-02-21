package no.kristiania.shared.dto;

public class AssignRequestDTO extends BodyDTO {
    private int owner;
    private int task;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }
}
