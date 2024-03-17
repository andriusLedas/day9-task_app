package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.group.Group;
import com.swedbank.StudentApplication.group.GroupService;
import com.swedbank.StudentApplication.group.exception.GroupNotFoundException;
import com.swedbank.StudentApplication.task.exceptiion.TaskExistsException;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tasks")
public class TaskController {
    private TaskService taskService;
    private GroupService groupService;

    @Autowired
    public TaskController(TaskService taskService, GroupService groupService){

        this.taskService = taskService;
        this.groupService = groupService;
    }

    @GetMapping
    ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Task> getById(@PathVariable("id") Long id)
            throws TaskNotFoundException
    {
        Task task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Task> saveTask(@RequestBody Task task)
            throws TaskExistsException
    {
        Task savedTask = taskService.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateTask(@RequestBody Task task)
        throws TaskNotFoundException
    {
        Task updatedTask = taskService.update(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id)
            throws TaskNotFoundException
    {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/groups/{gid}")
    public ResponseEntity<?> addTaskToGroup (@PathVariable long id, @PathVariable long gid)
        throws TaskNotFoundException, GroupNotFoundException
    {
        Task task = taskService.findById(id);
        Group group = groupService.findById(gid);

        Set<Task> groupTasks = group.getTasks();
        groupTasks.add(task);
        task.setGroup(group);
        taskService.saveAndFlush(task);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/groups/{gid}")
    public ResponseEntity<?> removeTaskFromGroup (@PathVariable long id, @PathVariable long gid)
        throws TaskNotFoundException, GroupNotFoundException
    {
        Task task = taskService.findById(id);
        Group group = groupService.findById(gid);
        Set<Task> tasks = group.getTasks();

        if (tasks.contains(task)) {
            tasks.remove(task);
            group.setTasks(tasks);

            taskService.saveAndFlush(task);
        }
        return ResponseEntity.ok().build();
    }
}
