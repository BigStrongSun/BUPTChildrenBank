package service.test;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.TaskService;

public class TaskServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMaxTaskId() {
        TaskService taskService = new TaskService();
        assertEquals(11,taskService.getMaxTaskId());
    }

    @Test
    public void modifyTask() {
        TaskService taskService = new TaskService();
    }

    @Test
    public void modifyTaskStatus() {
        TaskService taskService = new TaskService();
    }

    @Test
    public void saveTasks() {
        TaskService taskService = new TaskService();
    }

    @Test
    public void deleteTask() {
        TaskService taskService = new TaskService();
    }

    @Test
    public void getTaskById() {
        TaskService taskService = new TaskService();
    }
}