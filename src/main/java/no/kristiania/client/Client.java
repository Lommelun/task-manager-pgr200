package no.kristiania.client;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("welcome to your Task manager, please insert your command");
        System.out.println("Please write the number linked to the command you wish to perform");
        System.out.println("\t 1  - Create task");
        System.out.println("\t 2  - list tasks");
        System.out.println("\t 3  - Assign a task to an user");
        System.out.println("\t 4  - Update a task with id");
        System.out.println("\t 5  - Delete a task with id");
        System.out.println("\t 6  - Create user");
        System.out.println("\t 7  - list users");
        System.out.println("\t 8  - Assign an user to a task");
        System.out.println("\t 9  - Update an user profile with their id");
        System.out.println("\t10  - Delete an user profile with their id");
        System.out.println("\t11  - list all users assigned to the task id");
        System.out.println("\t12  - list all tasks assigned to the user id");

        int command = scn.nextInt();
        switch (command) {
            case 1:
                addTask();
                break;
            case 2:
                showListofTasks();
                break;
            case 3:
                assignTaskToUser();
                break;
            case 4:
                updateTaskWithId();
                break;
            case 5:
                deleteTaskWithID();
                break;
            case 6:
                addUser();
                break;
            case 7:
                showListOfUsers();
                break;
            case 8:
                AssignUserToTask();
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
        }
    }
}
