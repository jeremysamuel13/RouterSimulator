/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #4
 */

/**
 * Packet class
 * Contains packets that hold information that is used to direct where the
 * packet goes
 */

public class Packet {

    static int packetCount;
    int id;
    int packetSize;
    int timeArrive;
    int timeToDest;
    int totalTime;

    /**
     * default constructor
     */
    public Packet(){
        packetCount++;
        setId(packetCount);
    }

    /**
     * increases the total time that the packet has been in the network
     */
    public void incrementTotalTime(){
        totalTime++;
    }

    /**
     * gets the total time the packet has been in the network
     * @return
     * returns the total time the packet has been in the network
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * gets the packet count
     * @return
     * returns the packet count
     */
    public static int getPacketCount() {
        return packetCount;
    }

    /**
     * gets the id of the packet
     * @return
     * returns the id of the packet
     */
    public int getId() {
        return id;
    }

    /**
     * gets the size of the packet
     * @return
     * returns the size of the packet
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * gets the time the packet was created
     * @return
     * returns the time the packet was created
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * gets the amount of time it will take to get to the packet destination
     * @return
     * returns the amount of time it will take to get to the packet destination
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     * sets the packet count
     * @param packetCount
     * the amount of packets
     */
    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    /**
     * sets the id of the packet
     * @param id
     * the id of the packet
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets the size of the packet
     * @param packetSize
     * the size of the packet
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
        setTimeToDest(packetSize/100);
    }

    /**
     * sets the time in which the packet was created
     * @param timeArrive
     * the time in which the packet was created
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /**
     * sets the the amount of time it will take to get to the packet destination
     * @param timeToDest
     * the amount of time it will take to get to the packet destination
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * creates a string representation of the packet
     * @return
     * returns a string representation of the packet
     */
    public String toString(){
        return ("[" + id +  ", " + timeArrive + ", " + timeToDest + "]");
    }

    /**
     * decrements timeToDest
     */
    public void lowerTimeToDest(){
        if(timeToDest > 0)
            timeToDest--;
    }

}
