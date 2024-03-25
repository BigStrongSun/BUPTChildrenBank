package service;

import domain.Task;
import util.JSONController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a control class for task.
 *
 * <p> This class provides methods for managing tasks. It allows adding, modifying,
 * deleting, and retrieving tasks stored in a list.
 *
 * @author Yuxinyue Qian
 *
 */
public class TaskService {
    private List<Task> tasks;
    private JSONController json = new JSONController("task.txt");
    private Task task;
    private boolean doAdd;

    /**
     *  Initialize service.TaskService
     */
    public TaskService() {
        tasks = json.readArray(Task.class);
    }

//    public void addTaskById(int taskId) {
//        // 检查任务列表是否为空
//        if (tasks == null) {
//            tasks = new ArrayList<>();
//        }
//
//        // 检查指定的taskId是否已经存在
//        boolean taskIdExists = false;
//        for (Task task : tasks) {
//            if (task.getTaskId() == taskId) {
//                taskIdExists = true;
//                break;
//            }
//        }
//
//        // 如果taskId不存在，则创建新任务并添加到任务列表中
//        if (!taskIdExists) {
//            Task newTask = new Task();
//            newTask.setTaskId(taskId);
//            // 设置其他新任务的属性
//            tasks.add(newTask);
//            json.writeArray(tasks);
//            System.out.println("任务已成功添加！");
//        } else {
//            System.out.println("具有相同taskId的任务已存在！");
//        }
//    }

    /**
     * Retrieves the maximum task ID from the task list.
     *
     * @return The maximum task ID, or 0 if the task list is empty.
     */
    public int getMaxTaskId() {
        int maxId = 0;
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                int taskId = task.getTaskId();
                if (taskId > maxId) {
                    maxId = taskId;
                }
            }
        }
        return maxId;
    }

    /**
     * Modifies an existing task with the provided task information.
     *
     * @param task The modified task object.
     * @return 1 if the modification is successful, 0 otherwise.
     */
    public int modifyTask(Task task) {
        int taskId = task.getTaskId();
        if (task != null) {
            // 根据任务id查找对应的任务对象
            Task existingTask = getTaskById(taskId);
            existingTask.setTaskName(task.getTaskName());
            existingTask.setParentId(task.getParentId());
            existingTask.setChildId(task.getChildId());
            existingTask.setTaskDescription(task.getTaskDescription());
            existingTask.setStartTime(task.getStartTime());
            existingTask.setEndTime(task.getEndTime());
            existingTask.setTaskStatus(task.getTaskStatus());
            existingTask.setMoney(task.getMoney());
            if (saveTasks()) {
                System.out.println("修改成功");
                JOptionPane.showMessageDialog(null, "Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            } else {
                System.out.println("修改失败");
                JOptionPane.showMessageDialog(null, "Failure", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Modifies the status of an existing task.
     *
     * @param task The task object containing the modified status.
     * @return 1 if the modification is successful, 0 otherwise.
     */
    public int modifyTaskStatus(Task task) {
        int taskId = task.getTaskId();
        if (task != null) {
            // 根据任务id查找对应的任务对象
            Task existingTask = getTaskById(taskId);
            existingTask.setTaskStatus(task.getTaskStatus());
            if (saveTasks()) {
                System.out.println("确认成功");
                JOptionPane.showMessageDialog(null, "Confirmation success", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            } else {
                System.out.println("确认失败");
                JOptionPane.showMessageDialog(null, "Confirmation failure", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Saves the task list to the JSON file.
     *
     * @return true if the task list is successfully saved, false otherwise.
     */
    public boolean saveTasks() {
        return json.writeArray(tasks);
    }

    /**
     * Deletes a task with the specified task ID from the task list.
     *
     * @param taskId The ID of the task to delete.
     */
    public void deleteTask(int taskId) {
        // 检查任务列表是否为空
        if (tasks == null) {
//            tasks = new ArrayList<>();
            System.out.println("任务列表为空，没有要删除的函数");
            return;
        }

        // 遍历任务列表，查找指定的taskId
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTaskId() == taskId) {
                // 找到指定的taskId，从任务列表中删除该任务
                iterator.remove();
                json.writeArray(tasks); // 更新任务列表到文件中
                System.out.println("任务已成功删除！");
                JOptionPane.showMessageDialog(null, "任务已成功删除", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // 没有找到指定的taskId
        System.out.println("没有找到具有taskId的任务！");
        JOptionPane.showMessageDialog(null, "没有找到具有taskId的任务", "提示", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Retrieves a task from the task list based on the specified task ID.
     * If the task does not exist, a new task is created and added to the list.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task object with the specified task ID.
     */
    public Task getTaskById(int taskId) {
        // 检查任务列表是否为空
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        // 遍历任务列表，查找指定的taskId
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task; // 如果找到指定的taskId，直接返回任务对象
            }
        }
        // 如果taskId不存在，则创建新任务并添加到任务列表中
        Task newTask = new Task();
        newTask.setTaskId(taskId);
        // 设置其他新任务的属性
        tasks.add(newTask);
        json.writeArray(tasks);
        return newTask; // 返回新创建的任务对象
    }
}


