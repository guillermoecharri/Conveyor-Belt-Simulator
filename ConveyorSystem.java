/*
 * Name: Guillermo Echarri
 * Course: CNT 4714 Spring 2019
 * Assignment Title: Project 2 - Multi-threaded programming in Java
 * Date: February 17, 2019
 * Class: Section 01
 */
package conveyorsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConveyorSystem extends Thread {
    
    public static void main(String[] args) throws InterruptedException {
        
        int numStations = 0;
        ArrayList<Integer> workloads = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            //if file is blank, end program
            if(scanner.hasNext() == false){
                System.out.println("ERROR: config.txt is blank.");
                System.exit(0);
            }
            numStations = scanner.nextInt();
            while(scanner.hasNext()){
                workloads.add(scanner.nextInt());
            }
        } catch (FileNotFoundException ex) {
            //if no file is found, end program
            System.out.println("ERROR: config.txt was not found.");
            System.exit(0);
        }
        
        if(numStations != workloads.size()){
            //if the number of stations and the number of workloads don't match, end program
            System.out.println("ERROR: Number of stations does not match the number of workloads!");
            System.exit(0);
        }
        
        ConveyorStation[] stations = new ConveyorStation[numStations];
        Conveyor[] conveyors = new Conveyor[numStations];
        
        //make all the conveyors
        int i;
        for(i = 0; i < numStations; i++){
            conveyors[i] = new Conveyor(i);
        }
        
        System.out.println(" * * * SIMULATION BEGINS * * *\n");
        
        //make all the stations
        for(i = 0; i < numStations; i++){
            //some logic to find the conveyor that should be the output of each station
            int outputIndex;
            if(i != 0){
                outputIndex = i - 1;
            }
            else{
                outputIndex = numStations - 1;
            }
            //makes the stations
            stations[i] = new ConveyorStation(i, conveyors[i], conveyors[outputIndex], workloads.get(i));
        }
        
        //start running all of the threads
        for(i = 0; i < numStations; i++){
            stations[i].start();
        }
        for(i = 0; i < numStations; i++){
            stations[i].join();
        }
        
        System.out.println(" * * * SIMULATION ENDS * * *\n");
    }
    
}
