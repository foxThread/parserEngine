package parser.utils;

import java.util.Random;

public class Threads{

    public static  void randomSleep(int minSeconds,int maxSeconds)  {
        try{
        Random r=new Random();
        int timeToSleep=r.nextInt(maxSeconds-minSeconds)+minSeconds;
        System.out.println("Waiting for "+Integer.toString(timeToSleep)+" seconds");
        
        Thread.sleep(timeToSleep*1000); 
        }
        catch(InterruptedException e){
            
            Thread.currentThread().interrupt();
            }
        

    }


    public static void pauseThread(int minSeconds,int maxSeconds){
       
       
       

       Random r=new Random();
        int timeToSleep=(r.nextInt(maxSeconds-minSeconds)+minSeconds)*1000;
        System.out.println("Waiting for "+Integer.toString(timeToSleep/1000)+" seconds");

        long start =System.currentTimeMillis();
        while((System.currentTimeMillis()-start)<timeToSleep){
                      
            }
            System.out.println("Exiting from pause");    

 
            
    

    
    


  }

}