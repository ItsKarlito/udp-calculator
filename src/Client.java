import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try
        {
            DatagramSocket clientSocket = new DatagramSocket();

            // Send a message to server

            //InetAddress IPAddress = InetAddress.getByName("localhost"); // localhost loopback connection 127.0.0.1
            InetAddress IPAddress = InetAddress.getByAddress(new byte[] {127,0,0,1});
            int port = 9876;

            String sendingSentence = "Welcome from Client: " + clientSocket.getLocalPort();

            // fill new buffer with reply data and convert to string
            byte[] sendingDataBuffer = new byte[1024];
            sendingDataBuffer = sendingSentence.getBytes();

            // create packet for outgoing data
            DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, port);
            // send packet to socket over network
            clientSocket.send(sendingPacket);


            // Receive server reply
            byte[] data = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(data, data.length);
            clientSocket.receive(receivedPacket);
            String receivedSentence = new String(receivedPacket.getData());
            System.out.println(receivedSentence);

        }
        catch (Exception ex)
        {

        }
    }
}
