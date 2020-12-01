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
 * Router class
 * Holds packets in a buffer with a linked-list (self-implemented)
 */

public class Router {

    //for buffer queue implementation (head = front, tail = rear)
    public PacketNode head;
    public PacketNode tail;

    //max buffer size
    int max;

    private int size = 0;


    /**
     * default constructor
     */
    public Router(){
        head = null;
        tail = null;
    }

    /**
     * constructor that allows you to indicate the max buffer size
     * @param max
     * the max buffer size
     */
    public Router(int max){
        head = null;
        tail = null;
        this.max = max;
    }

    /**
     * sets the maximum buffer size
     * @param max
     * the maximum buffer size
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * gets the maximum buffer size
     * @return
     * returns the max buffer size
     */
    public int getMax() {
        return max;
    }

    /**
     * adds a packet to the rear of the buffer
     * @param p
     * the packet that is to be added to the router
     */
    public void enqueue(Packet p){
        PacketNode x = new PacketNode(p) ;
        if(head == null){
            head = x;
            tail = x;
        }else{
            tail.setLink(x);
            tail = tail.getLink();
        }

        size++;
    }

    /**
     * removes the packet at the front of the buffer
     * @return
     * returns the removed packet
     * @throws EmptyRouterException
     * throws exception when buffer is empty
     */
    public Packet dequeue() throws EmptyRouterException{
        PacketNode g;
        if(isEmpty()){
            throw new EmptyRouterException("Router is empty");

        }else if (head.getLink() == null){
            g = head;
            head = null;
            tail = null;
        }else{
            g = head;
            head = head.getLink();
        }
        size--;
        return g.getPacket();

    }

    /**
     * gets the packet at the front of the buffer
     * @return
     * returns the packet at the front of the buffer
     * @throws EmptyRouterException
     * throws exception when buffer is empty
     */
    public Packet peek() throws EmptyRouterException{
        if(isEmpty()){
            throw new EmptyRouterException("Router is empty");
        }else {
            return head.getPacket();
        }
    }

    /**
     * gets size of the buffer
     * @return
     * returns size of buffer
     */
    public int size(){
        return size;
    }

    /**
     * checks if buffer is empty
     * @return
     * returns true if buffer is empty, false if otherwise
     */
    public boolean isEmpty(){
        return size() == 0;

    }

    /**
     * creates a string representation of the buffer
     * @return
     * returns a string representation of the buffer
     */
    public String toString(){
        if(this.size() == 0){
            return "{}";
        }

        StringBuilder x = new StringBuilder("{");
        PacketNode cursor = head;
        while(cursor != null){
            if(cursor == tail)
                x.append(cursor.toString());
            else
                x.append(cursor.toString()).append(", ");

            cursor = cursor.getLink();
        }

        x.append("}");

        return x.toString();
    }

    /**
     * Increments the total time that each packet in the buffer has been in
     * the network
     */
    public void incrementPacketTime(){
        PacketNode cursor = head;
        while(cursor != null){
            cursor.getPacket().incrementTotalTime();
            cursor = cursor.getLink();
        }
    }

    /**
     * decrements the front of the buffer
     */
    public void decrementTimeToDest(){
        if(!isEmpty()) {
            peek().lowerTimeToDest();
        }
    }

    /**
     * checks if the buffer has any free space
     * @return
     * returns true if the buffer has space, false if otherwise
     */
    public boolean hasSpace(){
        return size() < getMax();
    }

    /**
     * finds the best router to send a packet to based on the router's buffer
     * space
     * @param routers
     * the collection of routers to look at
     * @return
     * returns the router number to send the packet to
     * @throws FullBufferException
     * throws an exception if all the buffers are full. (results in packet loss)
     */
    public static int sendPacketTo(Router[] routers) throws FullBufferException{
        int best = -1;
        int indices = 0;
        for (Router i: routers) {
            if(i.hasSpace() && (best == -1))
                best = indices;
            else if((best != -1) && (i.size() < routers[best].size()))
                best = indices;

            indices++;
        }

        if(best == -1){
            throw new FullBufferException("buffer is full");
        }
        
        return best;
    }



}
