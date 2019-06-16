
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author long
 */
public class Scheduler {

    private List<Task> taskList;
    private List<String> inCompleteList;
    private List<Integer> inCompleteTime;
    private List<Integer> inCompleteDeadline;
    private int currentTime;

    public Scheduler(String fileName) {
        //initialize variables
        currentTime = 0;
        inCompleteList = new ArrayList<>();
        inCompleteTime = new ArrayList<>();
        inCompleteDeadline = new ArrayList<>();
        //read task from file
        File file = new File(fileName);
        try {
            taskList = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String st;
            while ((st = bf.readLine()) != null) {
                String tempList[] = st.split("\t");
                Task tempTask = new Task(tempList[0], Integer.parseInt(tempList[1]), 
                    Integer.parseInt(tempList[2]), Integer.parseInt(tempList[3]), 
                    Integer.parseInt(tempList[4]));
                taskList.add(tempTask);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startSimulation(int time) {

        //re-initialize variables
        currentTime = 0;
        boolean isFirst = true;
        inCompleteList.clear();
        //start simulation
        while (time >= currentTime) {
            System.out.println(currentTime);
            //check current time for new tasks
            if (isFirst) {
                isFirst = false;
                for (Task task : taskList) {
                    if (task.getReleaseTime() == 0) {
                        inCompleteList.add(task.getTaskName());
                        inCompleteTime.add(task.getTaskWcet());
                        inCompleteDeadline.add(task.getDeadline());
                    }
                }
                currentTime++;
                continue;
            } else {
                for (Task task : taskList) {
                    //check if task has been released (time)
                    if (currentTime >= task.getReleaseTime()) {
                        //check period
                        if (task.getTaskPeriod() % (currentTime - task.getReleaseTime()) >= task.getTaskPeriod()) {
                            boolean isInIncompleteList = false;
                            //check if task is in incomplete List
                            for (String string : inCompleteList) {
                                if (string.equals(task.getTaskName()) == true) {
                                    isInIncompleteList = true;
                                    break;
                                }
                            }
                            //add to incomplete List if doesnt exist
                            if (!isInIncompleteList) {
                                System.out.println("add "+task.getTaskName());
                                inCompleteList.add(task.getTaskName());
                                inCompleteTime.add(task.getTaskWcet());
                                inCompleteDeadline.add(task.getDeadline());
                            }
                        }
                    }
                }
            }
            //end check new task

            if (inCompleteList.size()>0) {

                //check deadline of all task in incomplete List
                int count = 0;
                int smallestDeadline = -1;
                if (inCompleteList.size()>0) {
                    smallestDeadline = inCompleteDeadline.get(0);
                    for (Integer deadlineTime : inCompleteDeadline) {
                        if (deadlineTime < smallestDeadline) {
                            count = 1;
                            smallestDeadline = deadlineTime;
                        } else if (deadlineTime == smallestDeadline) {
                            count++;
                        }
                    }                            
                }
                //end check

                
                // decrease time base on earliest deadline
                System.out.println("smallestDeadline : " + smallestDeadline);
                Random rand = new Random();
                int n = rand.nextInt(count);
                count = 0;

                for (int i = 0; i < inCompleteList.size(); i++) {
                    if (inCompleteDeadline.get(i) == smallestDeadline) {
                        if (count == n) {
                            System.out.println(inCompleteList.get(i) + " time needed to finish: " + inCompleteTime.get(i) +" - 1");
                            inCompleteTime.set(i, inCompleteTime.get(i) - 1);
                            if (inCompleteTime.get(i) == 0) {
                                System.out.println(inCompleteList.get(i) + " has done the job at " + currentTime);
                                inCompleteList.remove(i);
                                inCompleteTime.remove(i);
                                inCompleteDeadline.remove(i);
                                break;
                            }
                        } else {
                            count++;
                        }
                    }
                }
                //---------

                //update deadline
                for (int i = 0; i < inCompleteList.size() ; i++) {
                    inCompleteDeadline.set(i, inCompleteDeadline.get(i) - 1);
                    if (inCompleteDeadline.get(i) <= 0) {
                        System.out.println("remove deadline " + inCompleteList.get(i));
                        inCompleteList.remove(i);
                        inCompleteTime.remove(i);
                        inCompleteDeadline.remove(i);
                    }
                }
                //end update deadline    
            }
            

            //increase current time
            currentTime++;
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            Scheduler sc = new Scheduler(args[0]);
            sc.startSimulation(Integer.parseInt(args[1]));
        }
    }
}
