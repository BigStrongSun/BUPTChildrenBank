package domain.test;

import domain.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {
    @Test
    public void getTaskId() {
        Task task = new Task();
        task.setTaskId(1);
        assertEquals(1, task.getTaskId());
    }
    @Test
    public void setTaskId() {
        Task task = new Task();
        task.setTaskId(1);
        assertEquals(1, task.getTaskId());
    }
    @Test
    public void getTaskDescription() {
        Task task = new Task();
        task.setTaskDescription("Description");
        assertEquals("Description", task.getTaskDescription());
    }

    @Test
    public void setTaskDescription() {
        Task task = new Task();
        task.setTaskDescription("Description");
        assertEquals("Description", task.getTaskDescription());
    }

    @Test
    public void getTaskName() {
        Task task = new Task();
        task.setTaskName("Task 1");
        assertEquals("Task 1", task.getTaskName());
    }

    @Test
    public void setTaskName() {
        Task task = new Task();
        task.setTaskName("Task 1");
        assertEquals("Task 1", task.getTaskName());
    }

    @Test
    public void getStartTime() {
        Task task = new Task();
        task.setStartTime("2022-01-01");
        assertEquals("2022-01-01", task.getStartTime());
    }

    @Test
    public void setStartTime() {
        Task task = new Task();
        task.setStartTime("2022-01-01");
        assertEquals("2022-01-01", task.getStartTime());
    }

    @Test
    public void getEndTime() {
        Task task = new Task();
        task.setEndTime("2022-01-02");
        assertEquals("2022-01-02", task.getEndTime());
    }

    @Test
    public void setEndTime() {
        Task task = new Task();
        task.setEndTime("2022-01-02");
        assertEquals("2022-01-02", task.getEndTime());
    }

    @Test
    public void getTaskStatus() {
        Task task = new Task();
        task.setTaskStatus("Completed");
        assertEquals("Completed", task.getTaskStatus());
    }


    @Test
    public void setTaskStatus() {
        Task task = new Task();
        task.setTaskStatus("Completed");
        assertEquals("Completed", task.getTaskStatus());
    }

    @Test
    public void getMoney() {
        Task task = new Task();
        task.setMoney(100);
        assertEquals(100.0, task.getMoney(),0.0);
    }

    @Test
    public void setMoney() {
        Task task = new Task();
        task.setMoney(100);
        assertEquals(100.0, task.getMoney(),0.0);
    }


    @Test
    public void getParentId() {
        Task task = new Task();
        task.setParentId(1);
        assertEquals(1, task.getParentId());
    }


    @Test
    public void setParentId() {
        Task task = new Task();
        task.setParentId(1);
        assertEquals(1, task.getParentId());
    }


    @Test
    public void getChildId() {
        Task task = new Task();
        task.setChildId(2);
        assertEquals(2, task.getChildId());
    }

    @Test
    public void setChildId() {
        Task task = new Task();
        task.setChildId(2);
        assertEquals(2, task.getChildId());
    }
}