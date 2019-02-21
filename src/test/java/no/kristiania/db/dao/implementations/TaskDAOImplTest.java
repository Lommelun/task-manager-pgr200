package no.kristiania.db.dao.implementations;

import no.kristiania.db.test.util.DbUtils;
import no.kristiania.server.db.impl.dao.TaskDAOImpl;
import no.kristiania.server.db.impl.dao.ContributorDAOImpl;
import no.kristiania.server.db.pojo.Contributor;
import no.kristiania.server.db.pojo.Task;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaskDAOImplTest {

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
    public void getTaskByIdShouldReturnCorrectTask() {
        Task task1 = new Task("test52332", 1);
        Task task2 = new Task("test22452", 2);

        taskDAO.add(task1);
        taskDAO.add(task2);

        assertEquals(taskDAO.get(task1.getId()), task1);
    }

    @Test
    public void getTaskByNameShouldReturnCorrectTask() {
        Task task1 = new Task("test52332", 1);
        Task task2 = new Task("test22452", 2);

        taskDAO.add(task1);
        taskDAO.add(task2);

        assertEquals(taskDAO.get(task1.getName()), task1);
    }

    @Test
    public void getAllTasksShouldReturnAllTasks() {
        Task task1 = new Task("test52332", 1);
        ArrayList<Task> tasksList = new ArrayList<>();
        taskDAO.add(task1);
        tasksList.add(task1);
        Task task2 = new Task("test12298", 2);
        taskDAO.add(task2);
        tasksList.add(task2);
        Task task3 = new Task("test38482", 1);
        taskDAO.add(task3);
        tasksList.add(task3);

        assertEquals(tasksList, taskDAO.getAllTasks());
    }

    @Test()
    public void addTaskShouldAddTask() {
        Task task = new Task("test52332", 1);

        taskDAO.add(task);
        assertEquals(task, taskDAO.get(task.getId()));
    }

    @Test
    public void updateTaskShouldChangeName() {
        Task task = new Task("test52332", 1);

        taskDAO.add(task);
        assertEquals(task, taskDAO.get(task.getId()));

        task.setName("test1234");
        taskDAO.update(task);
        assertEquals(task.getName(), taskDAO.get(task.getName()).getName());
    }

    @Test
    public void updateTaskShouldChangeStatus() {
        Task task = new Task("test52332", 1);

        taskDAO.add(task);
        assertEquals(task, taskDAO.get(task.getId()));

        task.setStatus(2);
        taskDAO.update(task);
        assertEquals(task.getStatus(), taskDAO.get(task.getName()).getStatus());
    }

    @Test
    public void deleteTaskShouldDeleteCorrectTask() {
        Task task1 = new Task("test52332", 1);
        Task task2 = new Task("test12298", 2);
        Task task3 = new Task("test38482", 1);

        ArrayList<Task> tasksList = new ArrayList<>();
        tasksList.add(task2);
        tasksList.add(task3);

        taskDAO.add(task1);
        taskDAO.add(task2);
        taskDAO.add(task3);

        taskDAO.delete(task1.getId());

        assertEquals(tasksList, taskDAO.getAllTasks());
    }

    @Test
    public void assignToShouldAssignTo() {
        Task task1 = new Task("task52332", 1);
        Task task2 = new Task("task52334", 2);

        Contributor contributor1 = new Contributor("Even");
        Contributor contributor2 = new Contributor("Anh");
        Contributor contributor3 = new Contributor("Nhi");

        taskDAO.add(task1);
        taskDAO.add(task2);
        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);
        contributorDAO.add(contributor3);

        ArrayList<Contributor> people = new ArrayList<>();
        people.add(contributorDAO.get(contributor1.getName()));
        people.add(contributorDAO.get(contributor2.getName()));


        taskDAO.assignTo(contributor1, task1);
        taskDAO.assignTo(contributor2, task1);
        taskDAO.assignTo(contributor3, task2);
        assertEquals(taskDAO.usersAssignedToTask(task1), people);
    }

    @Test
    public void contributorsAssignedToTaskShouldReturnContributorsAssignedToTask() {
        Task task1 = new Task("task52332", 1);
        Task task2 = new Task("task12298", 2);
        Task task3 = new Task("task38482", 1);
        taskDAO.add(task1);
        taskDAO.add(task2);
        taskDAO.add(task3);

        Contributor contributor1 = new Contributor("Even");
        Contributor contributor2 = new Contributor("Anh");
        Contributor contributor3 = new Contributor("Nhi");
        contributorDAO.add(contributor1);
        contributorDAO.add(contributor2);
        contributorDAO.add(contributor3);

        taskDAO.assignTo(contributor1, task1);
        taskDAO.assignTo(contributor2, task1);
        taskDAO.assignTo(contributor3, task2);


        ArrayList<Contributor> people = new ArrayList<>();
        people.add(contributorDAO.get(contributor1.getName()));
        people.add(contributorDAO.get(contributor2.getName()));

        assertEquals(taskDAO.usersAssignedToTask(task1), people);
    }
}