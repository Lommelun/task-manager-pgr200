package no.kristiania.db.dao.implementations;

import no.kristiania.db.test.util.DbUtils;
import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.impl.dao.TaskDAOImpl;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.pojo.Contributor;
import no.kristiania.server.db.pojo.Task;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserDAOImplTest {

    private TaskDAOImpl taskDAO;
    private ContributorDAOImpl contributorDAO;
    private JdbcDataSource dataSource;

    @Before
    public void initiate() {
        dataSource = DbUtils.initializeTestDb();
        taskDAO = new TaskDAOImpl(dataSource);
        contributorDAO = new ContributorDAOImpl(dataSource);
    }

    @After
    public void resetTestDB() {
        DbUtils.resetTestDB(dataSource);
    }

    @Test
    public void getUserByIdShouldReturnCorrectUser() {
        Contributor contributor1 = new Contributor("Cont1230");
        Contributor contributor2 = new Contributor("Cont14506");

        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);

        assertEquals(contributorDAO.get(contributor1.getId()), contributor1);
    }

    @Test
    public void getUserByNameShouldReturnCorrectUser() {
        Contributor contributor1 = new Contributor("Cont1230", 1);
        Contributor contributor2 = new Contributor("Cont14506", 2);

        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);

        Contributor compare = contributorDAO.get(contributor1.getName());

        assertEquals(compare, contributor1);
    }

    @Test
    public void getAllUsersShouldReturnAllUsers() {
        Contributor contributor1 = new Contributor("test52332", 1);
        ArrayList<Contributor> people = new ArrayList<>();
        contributorDAO.add(contributor1);
        people.add(contributor1);
        Contributor contributor2 = new Contributor("test12298", 2);
        contributorDAO.add(contributor2);
        people.add(contributor2);
        Contributor contributor3 = new Contributor("test38482", 3);
        contributorDAO.add(contributor3);
        people.add(contributor3);

        assertEquals(people, contributorDAO.getAllUsers());
    }

    @Test
    public void addUserShouldAddUser() {
        Contributor contributor1 = new Contributor("user5132", 1);
        contributorDAO.add(contributor1);

        assertEquals(contributor1, contributorDAO.get(contributor1.getName()));
    }


    @Test
    public void updateUserShouldChangeName() {
        Contributor contributor = new Contributor(("Anh"));

        contributorDAO.add(contributor);
        assertEquals(contributor, contributorDAO.get(contributor.getName()));

        contributor.setName("Even");
        contributorDAO.update(contributor);
        assertEquals(contributor.getName(), contributorDAO.get(contributor.getName()).getName());

    }

    @Test
    public void deleteUserShouldDeleteCorrectUser() {
        Contributor contributor1 = new Contributor("Even");
        Contributor contributor2 = new Contributor("Anh");
        Contributor contributor3 = new Contributor("Nhi");

        ArrayList<Contributor> people = new ArrayList<>();
        people.add(contributor2);
        people.add(contributor3);

        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);
        contributorDAO.add(contributor3);

        contributorDAO.delete(contributor1.getId());

        assertEquals(people, contributorDAO.getAllUsers());

    }

    @Test
    public void assignToShouldAssignTo() {
        Contributor contributor1 = new Contributor("Even");
        Contributor contributor2 = new Contributor("Anh");

        Task task1 = new Task("task52332", 1);
        Task task2 = new Task("task52334", 2);
        Task task3 = new Task("task52335", 2);

        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);

        taskDAO.add(task1);
        taskDAO.add(task2);
        taskDAO.add(task3);

        ArrayList<Task> tasksList = new ArrayList<>();
        tasksList.add(taskDAO.get(task1.getName()));
        tasksList.add(taskDAO.get(task2.getName()));

        contributorDAO.assignTo(task1, contributor1);
        contributorDAO.assignTo(task2, contributor1);
        contributorDAO.assignTo(task3, contributor2);
        assertEquals(contributorDAO.tasksAssignedToUser(contributor1), tasksList);
    }

    @Test
    public void tasksAssignedToUserShouldReturnTasksAssignedToUser() {
        Contributor contributor1 = new Contributor("Even");
        Contributor contributor2 = new Contributor("Anh");
        Contributor contributor3 = new Contributor("Nhi");
        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);
        contributorDAO.add(contributor3);

        Task task1 = new Task("task52332", 1);
        Task task2 = new Task("task12298", 2);
        Task task3 = new Task("task38482", 1);
        taskDAO.add(task1);
        taskDAO.add(task2);
        taskDAO.add(task3);

        contributorDAO.assignTo(task1, contributor1);
        contributorDAO.assignTo(task2, contributor1);
        contributorDAO.assignTo(task3, contributor2);

        ArrayList<Task> tasksList = new ArrayList<>();
        tasksList.add(taskDAO.get(task1.getName()));
        tasksList.add(taskDAO.get(task2.getName()));

        assertEquals(contributorDAO.tasksAssignedToUser(contributor1), tasksList);

    }
}