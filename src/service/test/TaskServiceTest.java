package service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import domain.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.TaskService;

public class TaskServiceTest {
    private TaskService taskService;

    @Before
    public void setUp() throws Exception {
        taskService = new TaskService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMaxTaskId() {
        assertEquals(11, taskService.getMaxTaskId());
    }

    @Test
    public void modifyTask() {
        Task task = new Task();
        task.setTaskId(11);
        task.setTaskName("test_modifyTask_taskName");
        task.setParentId(1);
        task.setChildId(2);
        task.setTaskDescription("test_modifyTask_taskDescription");
        task.setStartTime("2024-04-09 14:04");
        task.setEndTime("2024-07-19 14:04");
        task.setTaskStatus("undone");
        task.setMoney(1);
        assertEquals(1, taskService.modifyTask(task));
        assertEquals("test_modifyTask_taskName", task.getTaskName());
        assertEquals("test_modifyTask_taskDescription", task.getTaskDescription());
        assertEquals("2024-04-09 14:04", task.getStartTime());
        assertEquals("2024-07-19 14:04", task.getEndTime());
        assertEquals("undone", task.getTaskStatus());
        assertEquals("$1", task.getMoney());
    }

    @Test
    public void modifyTaskStatus() {
        Task task = new Task();
        task.setTaskId(11);
        task.setTaskStatus("done");
        assertEquals(1, taskService.modifyTaskStatus(task));
        assertEquals("done", task.getTaskStatus());
    }

    @Test
    public void deleteTask() {
        Task task1 = new Task();
        task1.setTaskId(1);
        Task task2 = new Task();
        task2.setTaskId(2);
        taskService.saveTasks();
        taskService.deleteTask(2);
        assertEquals(1, taskService.getMaxTaskId());
    }

    @Test
    public void getTaskById() {
        Task task1 = new Task();
        task1.setTaskId(1);
        Task task2 = new Task();
        task2.setTaskId(2);
        Task task3 = new Task();
        task3.setTaskId(3);
        taskService.saveTasks();
        Task retrievedTask = taskService.getTaskById(2);
        assertNotNull(retrievedTask);
        assertEquals(2, retrievedTask.getTaskId());
    }
}