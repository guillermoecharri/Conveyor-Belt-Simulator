/*
 * Name: Guillermo Echarri
 * Course: CNT 4714 Spring 2019
 * Assignment Title: Project 2 - Multi-threaded programming in Java
 * Date: February 17, 2019
 * Class: Section 01
 */
package conveyorsystem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConveyorStation extends Thread{
    
    private int stationId;
    private Conveyor conveyorBeltIn;
    private Conveyor conveyorBeltOut;
    private int workload;
    
    public ConveyorStation(int stationId, Conveyor conveyorBeltIn, Conveyor conveyorBeltOut, int workload){
        this.stationId = stationId;
        this.conveyorBeltIn = conveyorBeltIn;
        System.out.println("Station " + stationId + ": In-Connection set to conveyor " + conveyorBeltIn.getConveyorId());
        this.conveyorBeltOut = conveyorBeltOut;
        System.out.println("Station " + stationId + ": Out-Connection set to conveyor " + conveyorBeltOut.getConveyorId());
        this.workload = workload;
        System.out.println("Station " + stationId + ": Workload set. Station " + stationId + " has " + workload + " package groups to move.");
    }
    
    public void run(){
        //run until station runs out of work
        while(workload != 0){
            if(conveyorBeltIn.lock.tryLock() == true){
                System.out.println("Station " + stationId + ": granted access to conveyor " + conveyorBeltIn.getConveyorId());
                
                //Try and get the other required lock
                if(conveyorBeltOut.lock.tryLock() == true){
                    //got the other lock
                    System.out.println("Station " + stationId + ": granted access to conveyor " + conveyorBeltOut.getConveyorId());
                    //now able to do work so
                    doWork();
                }
                else{
                    //couldn't get both locks, so let go of the first one
                    System.out.println("Station " + stationId + ": released access to conveyor " + conveyorBeltIn.getConveyorId());
                    conveyorBeltIn.lock.unlock();
                }
            }
        }
        //workload == 0
        System.out.println("\n * * Station " + stationId + " Workload successfully completed. * *\n");
    }
    
    private void doWork(){
        //transmit packages... 
        System.out.println("Station " + stationId + ": successfully moves packages on conveyor " + conveyorBeltIn.getConveyorId());
        System.out.println("Station " + stationId + ": successfully moves packages on conveyor " + conveyorBeltOut.getConveyorId());
        
        //decrement workload
        workload--; 
        System.out.println("Station " + stationId + ": has " + workload + " package groups left to move.");
        
        //release conveyors
        conveyorBeltIn.lock.unlock();
        conveyorBeltOut.lock.unlock();
        
        try {
            //the station is tired, time for a nap.
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            //
        }
    }
    
}
