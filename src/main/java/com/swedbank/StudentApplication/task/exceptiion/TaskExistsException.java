package com.swedbank.StudentApplication.task.exceptiion;

public class TaskExistsException extends TaskException{

    public TaskExistsException(String message) {
        super("This task already exists.");
    }
}
