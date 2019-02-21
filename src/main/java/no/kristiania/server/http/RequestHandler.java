package no.kristiania.server.http;

import no.kristiania.server.http.handlers.AssignRequestHandler;
import no.kristiania.server.http.handlers.ContributorHandler;
import no.kristiania.server.http.handlers.TaskHandler;
import no.kristiania.server.http.request.Request;
import no.kristiania.server.http.request.RequestParser;
import no.kristiania.shared.dto.AssignRequestDTO;
import no.kristiania.shared.dto.BodyDTO;
import no.kristiania.shared.dto.TaskDTO;
import no.kristiania.shared.dto.ContributorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

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

        } catch (IllegalArgumentException | SQLException e) {
            response = "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    private String post(String resource, BodyDTO body) throws SQLException {
        switch (resource) {
            case "/api/task": {
                if (!(body instanceof TaskDTO)) {
                    return "HTTP/1.1 400 Bad Request\r\n";
                }
                return TaskHandler.insert((TaskDTO) body);
            }
            case "/api/user": {
                if (!(body instanceof ContributorDTO)) {
                    return "HTTP/1.1 400 Bad Request\r\n";
                }
                return ContributorHandler.insert((ContributorDTO) body);
            }
            case "/api/user/assign":
            case "/api/task/assign": {
                if (!(body instanceof AssignRequestDTO)) {
                    return "HTTP/1.1 400 Bad Request\r\n";
                }
                return AssignRequestHandler.assign((AssignRequestDTO) body);
            }
            default:
                return "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    private String delete(String resource, BodyDTO body) throws SQLException {
        int id = -1;
        if (resource.matches("^/api/.*/[0-9]+$")) {
            id = Integer.parseInt(resource.split("/")[3]);
            resource = resource.replace(Integer.toString(id), "");
        }

        switch (resource) {
            case "/api/task": {
                if (!(body instanceof TaskDTO)) {
                    return "HTTP/1.1 400 Bad Request\r\n";
                }
                return TaskHandler.delete((TaskDTO) body);
            }
            case "/api/user": {
                if (!(body instanceof ContributorDTO)) {
                    return "HTTP/1.1 400 Bad Request\r\n";
                }
                return ContributorHandler.delete((ContributorDTO) body);
            }
            case "/api/user/":
                if (id > -1) return ContributorHandler.delete(id);
            case "/api/task/":
                if (id > -1) return TaskHandler.delete(id);
            default:
                return "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    private String get(String resource) throws SQLException {
        int id = -1;
        String search = null;
        if (resource.matches("^/api/.*/[0-9]+$")) {
            id = Integer.parseInt(resource.split("/")[3]);
            resource = resource.replace(Integer.toString(id), "");
        }
        if (resource.matches("^/api/.*/[a-åA-Å]+$")) {
            search = resource.split("/")[3];
            resource = resource.replace(search, "");
        }

        switch (resource) {
            case "/": {
                String body = "Could not find the requested resource.\n" +
                        "Please try one of the following endpoints:\n" +
                        "\t/api/task/{$task_id}\n         -- Get task info for $task" +
                        "\t/api/tasks\n             -- Get all tasks" +
                        "\t/api/tasks/{$user_id}    -- Get tasks for $user\n" +
                        "\t/api/user/{$user_id}     -- Get user info for $user\n" +
                        "\t/api/users\n             -- Get all users" +
                        "\t/api/users/{$task_id}    -- Get users for $task\n" +
                        "\r\n";

                return "HTTP/1.1 200 OK\n" +
                        "Content-Type: text/plain\n" +
                        "Content-Length: " + body.length() + "\r\n" +
                        "\r\n" +
                        body;
            }
            case "/api/tasks":
                return TaskHandler.getAll();
            case "/api/users":
                return ContributorHandler.getAll();
            case "/api/user/": // {user_id}
                if (id > -1) return ContributorHandler.get(id);
                if (search != null) return ContributorHandler.get(search);
            case "/api/task/": // {task_id}
                if (id > -1) return TaskHandler.get(id);
            case "/api/tasks/": // {user_id}
                if (id > -1) return AssignRequestHandler.getTasksAssignedTo(id);
            case "/api/users/": // {task_id}
                if (id > -1) return AssignRequestHandler.getUsersAssignedTo(id);
            default:
                return "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    public byte[] reply() {
        return response.getBytes();
    }
}
