package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.person.PersonController;
import com.swedbank.StudentApplication.task.exceptiion.TaskExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tasks")
public class TaskController {

    private static Logger log = LoggerFactory.getLogger(PersonController.class);
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/save")
    ResponseEntity<Task> saveTask(@RequestBody Task task) throws TaskExistsException {
        Task savedTask = taskService.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @PatchMapping("/update/{tid}")
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        Task updatedTask = taskService.update(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }


}
