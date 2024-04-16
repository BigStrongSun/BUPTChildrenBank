package domain;

/**
 * This is a entity class contains information about task.
 *
 * <p>This class contains information about task.
 * It has seven attributes: taskId,taskName,taskDescription,startTime,endTime,taskStatus,money
 * and provides corresponding get and set methods.
 *
 * @author Yuxinyue Qian
 *
 */
public class Task {
    private int taskId;
    private String taskName;
    private String taskDescription;
    private String startTime;
    private String endTime;
    private String taskStatus;
    private double money;
    private int parentId;
    private int childId;


    /**
     * Retrieves the ID of the task.
     *
     * @return The ID of the task.
     */
    public int getTaskId() {
        return taskId;
    }
    /**
     * Sets the ID of the task.
     *
     * @param taskId The ID of the task.
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getTaskDescription() {
        return taskDescription;
    }
    /**
     * Sets the description of the task.
     *
     * @param taskDescription The description of the task.
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Retrieves the name of the task.
     *
     * @return The name of the task.
     */
    public String getTaskName() {
        return taskName;
    }
    /**
     * Sets the name of the task.
     *
     * @param taskName The name of the task.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    /**
     * Retrieves the start time of the task.
     *
     * @return The start time of the task.
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * Sets the start time of the task.
     *
     * @param startTime The start time of the task.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /**
     * Retrieves the end time of the task.
     *
     * @return The end time of the task.
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     * Sets the end time of the task.
     *
     * @param endTime The end time of the task.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /**
     * Retrieves the status of the task.
     *
     * @return The status of the task.
     */
    public String getTaskStatus() {
        return taskStatus;
    }
    /**
     * Sets the status of the task.
     *
     * @param taskStatus The status of the task.
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    /**
     * Retrieves the monetary value associated with the task.
     *
     * @return The monetary value associated with the task.
     */
    public double getMoney() {
        return money;
    }
    /**
     * Sets the monetary value associated with the task.
     *
     * @param money The monetary value associated with the task.
     */
    public void setMoney(double money) {
        this.money = money;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }
}
