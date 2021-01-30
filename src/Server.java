import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) {

        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);

            while (true) { // always running
                // Receive a message from the client
                byte[] data = new byte[1024]; // buffer with size 1024 bytes
                DatagramPacket receivedPacket = new DatagramPacket(data, data.length); // create an empty packet of size 1024 bytes, this is an empty datagram packet

                serverSocket.receive(receivedPacket); // fill empty packet with data

                String receivedSentence = new String(receivedPacket.getData()); // extract data part and convert to string


                // Extracting the IP address and port number of the client
                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();

                System.out.println("Receiving from Client: " + port + ": " + receivedSentence);

                // sending a reply
                String sendingSentence = "Welcome from Server";

                // fill new buffer with reply data and convert to string
                byte[] sendingDataBuffer = new byte[1024];
                sendingDataBuffer = sendingSentence.getBytes();

                // create packet for outgoing data
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, port);
                // send packet to socket over network
                serverSocket.send(sendingPacket);
            }

        } catch (Exception ex) {


        }
    }
}