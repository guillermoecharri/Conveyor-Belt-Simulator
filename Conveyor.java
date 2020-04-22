/*
 * Name: Guillermo Echarri
 * Course: CNT 4714 Spring 2019
 * Assignment Title: Project 2 - Multi-threaded programming in Java
 * Date: February 17, 2019
 * Class: Section 01
 */
package conveyorsystem;

import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
    
    private int conveyorId;
    public ReentrantLock lock = new ReentrantLock();
    
    public Conveyor(int conveyorId){
        this.conveyorId = conveyorId;
    }

    public int getConveyorId(){
        return conveyorId;
    }
}
