/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author long
 */
public class Task {
    private String taskName;
    private int taskPeriod;
    private int taskWcet;
    private int deadline;
    private int releaseTime;

    public Task() {
    }

    public Task(String taskName, int taskPeriod, int taskWcet, int deadline, int releaseTime) {
        this.taskName = taskName;
        this.taskPeriod = taskPeriod;
        this.taskWcet = taskWcet;
        this.deadline = deadline;
        this.releaseTime = releaseTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskPeriod() {
        return taskPeriod;
    }

    public void setTaskPeriod(int taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public int getTaskWcet() {
        return taskWcet;
    }

    public void setTaskWcet(int taskWcet) {
        this.taskWcet = taskWcet;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }
    
    public void displayInfo(){
        System.out.println("Task Name : " + getTaskName());
        System.out.println("Task Period : " + getTaskPeriod());
        System.out.println("Task Execution Time : " + getTaskWcet());
        System.out.println("Task Deadline : " + getDeadline());
        System.out.println("Task Release Time : " + getReleaseTime());
    }
}
