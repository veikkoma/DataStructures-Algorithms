package oy.tol.tra;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class showing your daily schedule using a timer.
 */
public class DailyTasks {

   private QueueInterface<String> dailyTaskQueue = null;
   private Timer timer = null;
   private static final int TASK_DELAY_IN_SECONDS = 1 * 1000;

   private DailyTasks() {
   }
      public static void main(String [] args) {
      LinkedListImplementation<String> list = new LinkedListImplementation<>();
      list.add("A");
      list.add("B");
      list.add("C");
      list.add("D");
      System.out.println(list.toString());
      list.remove("C");
      System.out.println(list.toString());
   }


   /**
    * Execute from the command line:  <code>java -jar target/04-queue-1.0-SNAPSHOT-jar-with-dependencies.jar</code>
    * @param args Not used.
    */
   public static void main(String[] args) {
      DailyTasks tasks = new DailyTasks();
      tasks.run();
   }

   /**
    *
    */
   private void run() {
      try {
         dailyTaskQueue = new QueueImplementation<>();

         readTasks();
         timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {

             @Override
            public void run(){
               if(!dailyTaskQueue.isEmpty() ){
                  System.out.println("Task: " + dailyTaskQueue.element());
                  dailyTaskQueue.dequeue();
                  System.out.println(dailyTaskQueue.size());
               } else {
                  timer.cancel();
                  System.out.println("No more tasks");
               }
            }
         }, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);

      } catch (IOException e) {
          System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      }
   }

   private void readTasks() throws IOException {
      String tasks;
      tasks = new String(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt").readAllBytes());
      String[] allTasks = tasks.split("\\r?\\n");
      for (String task: allTasks) {
         dailyTaskQueue.enqueue(task);
      System.out.println(dailyTaskQueue.size());
   }
}
}
