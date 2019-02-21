package no.kristiania.server.http.handlers;

import com.google.gson.Gson;
import no.kristiania.server.db.Connector;
import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.dao.TaskDAO;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.impl.dao.TaskDAOImpl;
import no.kristiania.server.db.pojo.Contributor;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.shared.dto.AssignRequestDTO;

import java.sql.SQLException;
import java.util.List;

public class AssignRequestHandler {
    public static String assign(AssignRequestDTO body) throws SQLException {
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        boolean result = contributorDAO.assignTo(body.getTask(), body.getOwner());

        return result ? "HTTP/1.1 201 CREATED\r\n" : "HTTP/1.1 400 Bad Request\r\n";
    }

    public static String getTasksAssignedTo(int id) throws SQLException {
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        List<Task> tasks = contributorDAO.tasksAssignedToUser(id);

        String json = !tasks.isEmpty() ? new Gson().toJson(tasks) : "{}";
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String getUsersAssignedTo(int id) throws SQLException {
        TaskDAO contributorDAO = new TaskDAOImpl(Connector.getInstance().getDataSource());

        List<Contributor> tasks = contributorDAO.usersAssignedToTask(id);

        String json = !tasks.isEmpty() ? new Gson().toJson(tasks) : "{}";
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }
}
