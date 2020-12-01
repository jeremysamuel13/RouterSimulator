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
 * PacketNode class
 * Holds a packet in a node so it can be used in a queue (the router buffer)
 */

public class PacketNode {

    private PacketNode link;
    private Packet packet;

    /**
     * Default constructor
     */
    public PacketNode(){
        Packet packet = new Packet();
        setPacket(packet);
    }

    /**
     * constructor that sets the packet of the node
     * @param p
     * the packet that is to be held by the node
     */
    public PacketNode(Packet p){
        setPacket(p);
    }

    /**
     * set the link of the node
     * @param link
     * the node that this node is to be linked with
     */
    public void setLink(PacketNode link) {
        this.link = link;
    }

    /**
     * sets the packet of the node
     * @param packet
     * the packet that is to be held by the node
     */
    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    /**
     * gets the packet that the node is holding
     * @return
     * returns the packet that the node is holding
     */
    public Packet getPacket() {
        return packet;
    }

    /**
     * gets the node's link
     * @return
     * returns the node that this node is linked to
     */
    public PacketNode getLink() {
        return link;
    }

    /**
     * creates a string representation of the packet in the node
     * @return
     * returns a string representation of the packet in the node
     */
    public String toString(){
        return getPacket().toString();
    }
}
