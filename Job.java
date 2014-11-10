//*****************************************************************************
// Author: Jeffrey Norris
// Class: CS 3243-900 Operating Systems
// Due Date: 10/6/14
// Description:
//    Creates the Job class that stores information about each job in a more
//    accessable format, and stores the instructions of the job into an array.
//*****************************************************************************

public class Job
{
   //different variables of each job.
   public int jobNumber;
   public int numberOfLines;
   public int priority;
   public String[] instructions;
   public int add = 0;
   
   public Job(int num, int line, int prior)
   {
      jobNumber = num;
      numberOfLines = line;
      priority = prior;
      instructions = new String[line];
   }
   
   public int getJobNumber()
   {
      return jobNumber;
   }
   
   public int getNumberOfLines()
   {
      return numberOfLines;
   }
   
   public int getPriority()
   {
      return priority;
   }
   
   //Prints out the instructions of the Job.
   public void printInstructions()
   {
      System.out.println("Job: " + jobNumber + " Number of Lines: " + 
         numberOfLines + " Priority: " + priority);
      for(int i=0; i<numberOfLines; i++)
      {
         System.out.println(instructions[i]);
      }
   }
   
   //Adds the next instruction and keeps track of where the next one will go.
   public void push(String instr)
   {
      instructions[add] = instr;
      add++;
   }
   
}