//*****************************************************************************
// Author: Jeffrey Norris
// Class: CS 3243-900 Operating Systems
// Due Date: 10/6/14
// Description:
//    Creates my Phase 1 OS System. This system is uses two other classes,
//    ReadFile, and Job. Readfile is so that a text file containing Jobs can be
//    input. Job is so that each job can be stored with its defining information
//    and an array of its instructions. This Program creates a HDD object that
//    stores any number of jobs, then uses a Long Term Scheduler algorithm to send
//    those jobs to RAM. There are 3 LTS algorithms, FIFO, SJF, and Priority.
//    all 3 algorithms are run in this case.
//*****************************************************************************

import java.io.IOException;
import java.util.ArrayList;

public class JeffreyOS
{
   public static void main(String []args) throws IOException
   {
      int a;
      // Hardcode the file path here.
      String file_name = "C:/Users/Jeffrey/Dropbox/College 2014 Fall/CS 3243-900 (Operating Sys)/Phase 1/ugradPart1.txt";
      
      //------------------------------------------------------
      // Creates a file reader to go through the given text
      // file and put it into an array.
      //------------------------------------------------------
      try
      {
         ReadFile file = new ReadFile(file_name);
         String[] allLines = file.Openfile();
         int size = allLines.length;
         
         
         //------------------------------------------------------
         // Fills the HDD represented by an arraylist that
         // contaitns all of the jobs as Job Objects.
         //------------------------------------------------------
            
         //Starts the hdd to fill with jobs.
         ArrayList<Job> hdd = new ArrayList<Job>();
         int numberOfJobs=0;
         
         //Breaks the text file into seperate Jobs.
         for(int i=0; i<size; i++)
         {
            String jobInfo = allLines[i];
            String[] data = jobInfo.split(",");
            hdd.add( new Job(Integer.parseInt(data[0].substring(4)) , Integer.parseInt(data[1].trim()) 
               , Integer.parseInt(data[2].trim())) );
            i++;
            for(int j=0; j<hdd.get(numberOfJobs).getNumberOfLines(); j++)
            {
               hdd.get(numberOfJobs).push(allLines[i]);
               i++;
            }
            i--;
            numberOfJobs++;
         }
         
         //------------------------------------------------------
         // Sends the Jobs to RAM depending on which LTS algorithm
         // is chosen.
         //------------------------------------------------------
         
         ArrayList<Job> ram = new ArrayList<Job>();
         
         System.out.println("Sent to RAM by FIFO Algorithm\n");
         ram = ltsFIFO(hdd);
         for(int i=0; i<ram.size(); i++)
         {
            ram.get(i).printInstructions();
         }
         
         System.out.println("\nSent to RAM by SJF Algorithm\n");
         ram = ltsSJF(hdd);
         for(int i=0; i<ram.size(); i++)
         {
            ram.get(i).printInstructions();
         }
         
         System.out.println("\nSent to RAM by Priority Algorithm\n");
         ram = ltsPriority(hdd);
         for(int i=0; i<ram.size(); i++)
         {
            ram.get(i).printInstructions();
         }

         
      }
      
      // Catch statment if the file path is wrong.
      catch (IOException e)
      {
         System.out.println( e.getMessage() );
      }
   }
   
   //------------------------------------------------------
   // Different Long Term Scheduler algorithms.
   //------------------------------------------------------
   
   //Sorts jobs on their job number so that the first job put in is the first job out
   public static ArrayList<Job> ltsFIFO(ArrayList<Job> arr)
   {
      Job temp;
      for(int i=0; i<arr.size()-1; i++)
      {
         for(int j=1; j<arr.size()-i; j++)
         {
            if(arr.get(j-1).getJobNumber() > arr.get(j).getJobNumber())
            {
               temp = arr.get(j-1);
               arr.set(j-1, arr.get(j));
               arr.set(j, temp);
            }
         }
      }
      return arr;
   }
   
   //Sorts jobs based on the number of lines, so that the shortest jobs are run first.
   //Also sorts by priority, so that two Jobs of the same size, the higher priority
   //will be run first.
   public static ArrayList<Job> ltsSJF(ArrayList<Job> arr)
   {
      Job temp;
      
      //Pre sort by priority.
      for(int i=0; i<arr.size()-1; i++)
      {
         for(int j=1; j<arr.size()-i; j++)
         {
            if(arr.get(j-1).getPriority() > arr.get(j).getPriority())
            {
               temp = arr.get(j-1);
               arr.set(j-1, arr.get(j));
               arr.set(j, temp);
            }
         }
      }
      
      //Primary sort by size.
      for(int i=0; i<arr.size()-1; i++)
      {
         for(int j=1; j<arr.size()-i; j++)
         {
            if(arr.get(j-1).getNumberOfLines() > arr.get(j).getNumberOfLines())
            {
               temp = arr.get(j-1);
               arr.set(j-1, arr.get(j));
               arr.set(j, temp);
            }
         }
      }
      return arr;
   }
   
   //Sorts jobs based on their priority so the highest priority will run firsy.
   //Also sort by size of job, so that two Jobs with the same priority, the
   //shortest will go before.
   public static ArrayList<Job> ltsPriority(ArrayList<Job> arr)
   {
      Job temp;
      
      //Pre sort by size.
      for(int i=0; i<arr.size()-1; i++)
      {
         for(int j=1; j<arr.size()-i; j++)
         {
            if(arr.get(j-1).getNumberOfLines() > arr.get(j).getNumberOfLines())
            {
               temp = arr.get(j-1);
               arr.set(j-1, arr.get(j));
               arr.set(j, temp);
            }
         }
      }
      
      //Primary sort by priority.
      for(int i=0; i<arr.size()-1; i++)
      {
         for(int j=1; j<arr.size()-i; j++)
         {
            if(arr.get(j-1).getPriority() > arr.get(j).getPriority())
            {
               temp = arr.get(j-1);
               arr.set(j-1, arr.get(j));
               arr.set(j, temp);
            }
         }
      }
      return arr;
   }
}