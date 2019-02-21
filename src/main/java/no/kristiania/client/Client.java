package no.kristiania.client;

import com.google.gson.Gson;
import no.kristiania.shared.dto.TaskDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    static HttpClient httpClient = HttpClientBuilder.create().build();
    static String url = "http://localhost:3000";

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("welcome to your Task manager, please insert your command");
            System.out.println("Please write the number linked to the command you wish to perform");
            System.out.println("\t1  - Create task         |   2  - Create user");
            System.out.println("\t3  - List tasks          |   4  - List users");
            System.out.println("\t5  - Assign task to user |   6  - Assign user to task");
            System.out.println("\t7  - Update task         |   8  - Delete task");
            System.out.println("\t9  - Update user         |   10 - Delete user");
            System.out.println("\t11 - List users for task |   12 - List task for users");
            System.out.println("\t00 - Exit");
            System.out.print("> ");

            try {
                perform(Integer.parseInt(scn.next()));
            } catch (NumberFormatException e) {
                System.out.println("That's not a number, try again.\n");
            }
        }
    }

    private static void perform(int command) {
        try {

            switch (command) {
                case 1:
                    createTask();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    showListofTasks();
                    break;
                case 4:
                    showListOfUsers();
                    break;
                case 5:
                    assignTaskToUser();
                    break;
                case 6:
                    assignUserToTask();
                    break;
                case 7:
                    updateTaskWithId();
                    break;
                case 8:
                    deleteTaskWithID();
                    break;
                case 9:
                    updateUserWithId();
                    break;
                case 10:
                    deleteUserWithID();
                    break;
                case 11:
                    listUsersAssignedtoTask();
                    break;
                case 12:
                    listTasksAssignedtoUser();
                    break;
                case 00:
                    System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Sorry, something went wrong.");
            e.printStackTrace();
        }
    }

    private static void assignUserToTask() {
        // TODO: Not implemented due to time.
    }

    private static void updateTaskWithId() {
        // TODO: Not implemented due to time.
    }

    private static void addUser() {
        // TODO: Not implemented due to time.
    }

    private static void showListOfUsers() throws IOException {
        HttpGet request = new HttpGet(url + "/api/users");
        HttpResponse response = httpClient.execute(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        System.out.println(reader.lines().collect(Collectors.toList())); // TODO: Parse response to DTO's for custom printing views
    }

    private static void updateUserWithId() {
        // TODO: Not implemented due to time.
    }

    private static void deleteUserWithID() {
        // TODO: Not implemented due to time.
    }

    private static void listUsersAssignedtoTask() {
        // TODO: Not implemented due to time.
    }

    private static void listTasksAssignedtoUser() {
        // TODO: Not implemented due to time.
    }

    private static void showListofTasks() {
        // TODO: Not implemented due to time.
    }

    private static void deleteTaskWithID() {
        // TODO: Not implemented due to time.
    }

    private static void assignTaskToUser() {
        // TODO: Not implemented due to time.
    }

    private static void createTask() throws IOException {
        HttpPost request = new HttpPost(url);
        TaskDTO task = new TaskDTO();
        Gson g = new Gson();

        task.setName("task name"); // TODO: should read from stdin
        task.setStatus(1); // TODO: should read from stdin

        String json = g.toJson(task);
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 201) {
            System.out.println("Task created");
        } else System.out.println("Task could not be created"); // TODO: handling of 400, 404...
    }
}
