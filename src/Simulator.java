/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #4
 */

import java.util.*;

/**
 * Simulator class
 * Simulates the network based on the given parameters
 */
public class Simulator {

    public static final int MAX_PACKETS = 3;

    Router dispatcher = new Router();
    int currentTime;
    int totalServiceTime;
    int totalPacketsArrived;
    int packetsDropped;
    double arrivalProb;
    int numIntRouters;
    int maxBufferSize;
    int maxPacketSize;
    int minPacketSize;
    int bandwidth;
    int duration;

    //queue for choosing which router sends a packet to destination
    ArrayList<Router> queue = new ArrayList<>();

    //array that holds all the routers (amount of routers based on
    // numIntRouters)
    Router[] routers;

    /**
     * Main method
     * Gets info for simulation and then simulates. Keeps asking the user if
     * they want to do another simulation until they type 'n'
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        boolean active = true;
        String input = "y";

        while(active) {

            switch (input.toLowerCase()) {
                case "y" -> {
                    try {
                        System.out.println("Starting simulator...");
                        Simulator s = new Simulator();
                        s.getInfo();
                        s.simulate();
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input type");
                    }
                }
                case "n" -> {
                    System.out.println("Program terminating successfully...");
                    active = false;

                    //used exit method in order to prevent scanner input again
                    System.exit(0);
                }
                default -> System.out.println("Invalid input");
            }

            System.out.println("Do you want to try another simulation? " +
                    "(y/n): ");
            input = scan.nextLine();

        }

    }

    /**
     * Simulates the network based on the parameters in the object
     * @return
     * returns the average service time to the hundredth place (ex. 1.11)
     */
    public double simulate() {

        //creates all the routers based on the numIntRouters
        for (int i = 0; i < routers.length; i++) {
            routers[i] = new Router(maxBufferSize);
        }

        //simulation cycle
        while (currentTime != duration) {
            currentTime++;
            System.out.println("Time: " + currentTime);

            for (Router i: routers) {
                i.incrementPacketTime();
            }

            //packets arrive based on arrival prob
            int arrived = 0;
            for (int i = 0; i < MAX_PACKETS; i++) {
                // each packet has prob of arrival prob to arrive
                if (Math.random() < arrivalProb) {
                    Packet p = new Packet();
                    p.setPacketSize(randInt(minPacketSize, maxPacketSize));
                    p.setTimeArrive(currentTime);
                    p.setTimeToDest(p.getPacketSize() / 100);
                    dispatcher.enqueue(p);
                    System.out.println("Packet " + p.getId() + " arrives at " +
                            "dispatcher with size " + p.getPacketSize() + ".");
                    arrived++;
                }
            }
            if(arrived == 0)
                System.out.println("No packets arrived.");

            //assigns packets to router with fairness in mind
            while (!dispatcher.isEmpty()) {
                Packet g = dispatcher.dequeue();
                try {
                    int x = Router.sendPacketTo(routers);
                    routers[x].enqueue(g);
                    System.out.println("Packet " + g.getId() + " sent to " +
                            "Router " + (x + 1) + ".");
                } catch (FullBufferException e) {
                    packetsDropped++;
                    System.out.println("Network is congested. Packet " +
                            g.getId() + " is dropped.");
                }
            }

            //decrements the timeToDesk of the top of each router buffer
            for (Router i : routers) {
                i.decrementTimeToDest();
            }

            //adds router to router queue
            for (Router i : routers) {
                if((!(i.isEmpty())) && !queue.contains(i) &&
                        i.peek().getTimeToDest() == 0){
                    queue.add(i);
                }
            }

            //pushes packets to destination router
            for (int i = 0; i < bandwidth; i++){
                if(!(queue.isEmpty())) {
                    Packet x = queue.remove(0).dequeue();
                    totalServiceTime = totalServiceTime + x.getTotalTime();
                    System.out.println("Packet " + x.getId() + " has " +
                            "successfully reached its destination: +" +
                            x.getTotalTime());
                    totalPacketsArrived++;

                }
            }

            routersToString();
        }

        double g = (double) totalServiceTime/totalPacketsArrived;
        double avgInNetwork = Math.round(g * 100.0) / 100.0;


        System.out.println("Simulation ending...");
        System.out.println("Total service time: " + totalServiceTime);
        System.out.println("Total packets served: " + totalPacketsArrived);
        System.out.println("Average service time per packet: " + avgInNetwork);
        System.out.println("Total packets dropped: " + packetsDropped);

        return avgInNetwork;
    }

    /**
     * Prints the state of the routers
     */
    public void routersToString(){
        StringBuilder g = new StringBuilder();
        int count = 1;
        for (Router i: routers) {
            g.append("R").append(count).append(": ").
                    append(i.toString()).append("\n");
            count++;
        }
        System.out.println(g);
    }

    /**
     * generates a random integer within the interval [minVal, maxVal]
     * @param minVal
     * minimum value
     * @param maxVal
     * maximum value
     * @return
     * returns a random integer within the interval [minVal, maxVal]
     */
    private static int randInt(int minVal, int maxVal){
        return (int) ((Math.random() * ((maxVal + 1) - minVal)) + minVal);

    }

    /**
     * Asks user for the parameters to use in the simulation
     * @throws IllegalArgumentException
     * Whenever an invalid input is entered. (Ex. negative values)
     */
    public void getInfo() throws IllegalArgumentException,
            InputMismatchException{
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of Intermediate routers: ");
        numIntRouters = scan.nextInt();
        if(!(numIntRouters > 0))
            throw new IllegalArgumentException("Only positive integers " +
                    "allowed");

        System.out.println("Enter the arrival probability of a packet: ");
        arrivalProb = scan.nextDouble();
        if(!(arrivalProb > 0 && arrivalProb <= 1.0))
            throw new IllegalArgumentException("Invalid probability: Enter a " +
                    "value in the range (0,1]");

        System.out.println("Enter the maximum buffer size of a router: ");
        maxBufferSize = scan.nextInt();
        if(!(maxBufferSize > 0))
            throw new IllegalArgumentException("Only positive integers " +
                    "allowed");

        System.out.println("Enter the minimum size of a packet: ");
        minPacketSize = scan.nextInt();
        if(!(minPacketSize >= 0))
            throw new IllegalArgumentException("Only positive integers or " +
                    "zero allowed");

        System.out.println("Enter the maximum size of a packet: ");
        maxPacketSize = scan.nextInt();
        if(!(maxPacketSize > 0))
            throw new IllegalArgumentException("Only positive integers " +
                    "allowed");
        if(maxPacketSize < minPacketSize)
            throw new IllegalArgumentException("Max packet size has to be " +
                    "greater than min packet size");

        System.out.println("Enter the bandwidth size: ");
        bandwidth = scan.nextInt();
        if(!(bandwidth > 0))
            throw new IllegalArgumentException("Only positive integers " +
                    "allowed");

        System.out.println("Enter the simulation duration: ");
        duration = scan.nextInt();
        if(!(duration > 0))
            throw new IllegalArgumentException("Only positive integers" +
                    "allowed");

        //prevent next scanLine from getting skipped
        scan.nextLine();

        routers = new Router[numIntRouters];
    }


}
