package no.kristiania.server.http;

import com.google.gson.Gson;
import no.kristiania.server.db.Connector;
import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.pojo.Contributor;
import no.kristiania.shared.dto.ContributorDTO;

import java.sql.SQLException;
import java.util.List;

class ContributorHandler {
    static String insert(ContributorDTO contributor) throws SQLException {
        boolean result;
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        if (contributor.getId() > 0) {
            result = contributorDAO.update(new Contributor(contributor.getName(), contributor.getId()));
        } else {
            result = contributorDAO.add(new Contributor(contributor.getName())) > 0;
        }

        return result ? "HTTP/1.1 201 CREATED\r\n" : "HTTP/1.1 400 Bad Request\r\n";
    }

    static String delete(ContributorDTO contributor) throws SQLException {
        ContributorDAO user = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        return user.delete(contributor.getId())
                ? "HTTP/1.1 200 OK\r\n"
                : "HTTP/1.1 400 Bad Request\r\n";
    }

    static String getAll() throws SQLException {
        ContributorDAO user = new ContributorDAOImpl(Connector.getInstance().getDataSource());
        List<Contributor> users = user.getAllUsers();

        String json = !users.isEmpty() ? new Gson().toJson(users) : "{}";
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String get(int id) throws SQLException {
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        String json = new Gson().toJson(contributorDAO.get(id));
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String get(String search) throws SQLException {
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        String json = new Gson().toJson(contributorDAO.get(search));
        json += "\n";

        return "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "Content-Length: " + json.length() + "\r\n\r\n" +
                json;
    }

    public static String delete(int id) throws SQLException {
        ContributorDAO contributorDAO = new ContributorDAOImpl(Connector.getInstance().getDataSource());

        return contributorDAO.delete(id)
                ? "HTTP/1.1 200 OK\r\n"
                : "HTTP/1.1 404 Not Found\r\n";
    }
}
