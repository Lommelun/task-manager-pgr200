package no.kristiania.server.http;

import com.google.gson.Gson;
import no.kristiania.server.db.Connector;
import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.dao.TaskDAO;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.impl.dao.TaskDAOImpl;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.shared.dto.TaskDTO;

import java.sql.SQLException;
import java.util.List;

class TaskHandler {
    static String insert(TaskDTO task) throws SQLException {
        boolean result;
        TaskDAO taskDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        if (task.getId() > 0) {
            result = taskDAO.update(new Task(task.getName(), task.getStatus(), task.getId()));
        } else {
            result = taskDAO.add(new Task(task.getName(), task.getStatus())) > 0;
        }

        return result ? "HTTP/1.1 201 CREATED\r\n" : "HTTP/1.1 400 Bad Request\r\n";
    }

    static String delete(TaskDTO task) throws SQLException {
        TaskDAO taskDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        return taskDAO.delete(task.getId())
                ? "HTTP/1.1 200 OK\r\n"
                : "HTTP/1.1 400 Bad Request\r\n";
    }

    static String getAll() throws SQLException {
        TaskDAO task = new TaskDAOImpl(Connector.getInstance().getDataSource());

        List<Task> tasks = task.getAllTasks();
        String json = new Gson().toJson(tasks);
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    static String get(int id) throws SQLException {
        TaskDAO taskDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        String json = new Gson().toJson(taskDAO.get(id));
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String get(String search) throws SQLException {
        TaskDAO taskDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        String json = new Gson().toJson(taskDAO.get(search));
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String delete(int id) throws SQLException {
        TaskDAO taskDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        return taskDAO.delete(id)
                ? "HTTP/1.1 200 OK\r\n"
                : "HTTP/1.1 404 Not Found\r\n";
    }
}
