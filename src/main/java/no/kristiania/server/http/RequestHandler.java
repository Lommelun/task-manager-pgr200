package no.kristiania.server.http;

import com.google.gson.Gson;
import no.kristiania.server.db.dao.TaskDAO;
import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.impl.dao.TaskDAOImpl;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.pojo.Contributor;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.shared.dto.BodyDTO;
import no.kristiania.shared.dto.TaskDTO;
import no.kristiania.shared.dto.ContributorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import static no.kristiania.server.db.Connector.getDataSource;

public class RequestHandler {
    private String response;

    public RequestHandler(BufferedReader input) throws IOException {
        try {
            Request request = new RequestParser(input).parse();

            switch (request.verb) {
                case "GET":
                    response = get(request.resource);
                    break;
                case "POST":
                    response = post(request.resource, request.getBody());
                    break;
                case "DELETE":
                    response = delete(request.resource, request.getBody());
                    break;
                default:
                    response = "HTTP/1.1 400 Bad Request\r\n";
            }

        } catch (IllegalArgumentException e) {
            response = "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    private String post(String resource, BodyDTO body) {
        try {

            switch (resource) {
                case "/api/task": {
                    if (!(body instanceof TaskDTO)) {
                        return "HTTP/1.1 400 Bad Request\r\n";
                    }
                    TaskDTO task = (TaskDTO) body;
                    new TaskDAOImpl(getDataSource())
                            .add(new Task(task.getName(), task.getStatus()));
                    new TaskDAOImpl(getDataSource())
                            .update(new Task(task.getName(), task.getStatus(), task.getId()));

                    // TODO Conditional based on db result.
                    return "HTTP/1.1 200 OK\r\n";
                }
                case "/api/user": {
                    if (!(body instanceof ContributorDTO)) {
                        return "HTTP/1.1 400 Bad Request\r\n";
                    }
                    ContributorDTO contributor = (ContributorDTO) body;
                    new ContributorDAOImpl(getDataSource())
                            .add(new Contributor(contributor.getName()));
                    new ContributorDAOImpl(getDataSource())
                            .update(new Contributor(contributor.getName(), contributor.getId()));

                    // TODO Conditional based on db result.
                    return "HTTP/1.1 200 OK\r\n";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "HTTP/1.1 400 Bad Request\r\n";
    }

    private String delete(String resource, BodyDTO body) {
        try {

            switch (resource) {
                case "/api/task": {
                    if (!(body instanceof TaskDTO)) {
                        return "HTTP/1.1 400 Bad Request\r\n";
                    }
                    TaskDAO task = new TaskDAOImpl(getDataSource());
                    task.delete(((TaskDTO) body).getId());

                    // TODO Conditional based on db result.
                    return "HTTP/1.1 200 OK\r\n";
                }
                case "/api/user": {
                    if (!(body instanceof ContributorDTO)) {
                        return "HTTP/1.1 400 Bad Request\r\n";
                    }
                    ContributorDAO user = new ContributorDAOImpl(getDataSource());
                    user.delete(((ContributorDTO) body).getId());

                    // TODO Conditional based on db result.
                    return "HTTP/1.1 200 OK\r\n";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "HTTP/1.1 400 Bad Request\r\n";
    }

    private String get(String resource) {
        try {

            switch (resource) {
                case "/": {
                    String body = "Could not find the requested resource,\n" +
                            "Please try one of the following endpoints:\n" +
                            "\t/api/task\n" +
                            "\t/api/task/{id}\n" +
                            "\t/api/tasks\n" +
                            "\t/api/user\n" +
                            "\t/api/user/{id}\n" +
                            "\t/api/users\n" +
                            "\r\n";

                    return "HTTP/1.1 200 OK\n" +
                            "Content-Type: text/plain\n" +
                            "Content-Length: " + body.length() + "\r\n" +
                            "\r\n" +
                            body;
                }
                case "/api/tasks": {
                    TaskDAO task = new TaskDAOImpl(getDataSource());
                    String json = new Gson().toJson(task.getAllTasks());

                    return "HTTP/1.1 200 OK\n" +
                            "Content-Type: application/json\n" +
                            "Content-Length: " + json.length() + "\r\n" +
                            json;
                }
                case "/api/users": {
                    ContributorDAO user = new ContributorDAOImpl(getDataSource());
                    String json = new Gson().toJson(user.getAllUsers());

                    return "HTTP/1.1 200 OK\n" +
                            "Content-Type: application/json\n" +
                            "Content-Length: " + json.length() + "\r\n" +
                            json;
                }
            }

            String[] slug = resource.split("/");
            if (!slug[0].equals("api") && slug[1] != null) return "HTTP/1.1 404 Not Found\r\n";
            int id = Integer.parseInt(slug[slug.length - 1]);

            switch (slug[1]) {
                case "task": {
                    TaskDAO task = new TaskDAOImpl(getDataSource());
                    task.get(id);

                    return "HTTP/1.1 200 OK\r\n";
                }
                case "user": {
                    ContributorDAO user = new ContributorDAOImpl(getDataSource());
                    user.get(id);

                    return "HTTP/1.1 200 OK\r\n";
                }
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return "HTTP/1.1 400 Bad Request\r\n";
    }

    public byte[] reply() {
        return response.getBytes();
    }
}
