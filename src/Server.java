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

                System.out.println("Message from Client: " + receivedSentence);

                // Extracting numbers and operation from client message
                String[] messageComponents = receivedSentence.split(",");
                double firstNumber = Double.parseDouble(messageComponents[0]);
                double secondNumber = Double.parseDouble(messageComponents[1]);
                double operator = Double.parseDouble(messageComponents[2]);

                double result = 0;
                switch ((int) operator) {
                    case 0: // add
                        result = firstNumber + secondNumber;
                        break;
                    case 1: // subtract
                        result = firstNumber - secondNumber;
                        break;
                    case 2: // multiply
                        result = firstNumber * secondNumber;
                        break;
                    case 3: // divide
                        result = firstNumber / secondNumber;
                        break;
                    case 4: // max
                        result = Math.max(firstNumber, secondNumber);
                        break;
                    case 5: // min
                        result = Math.min(firstNumber, secondNumber);
                        break;
                    default:
                        break;
                }

                // sending a reply
                String sendingSentence = "Answer: " + result;
                byte[] sendingDataBuffer = new byte[1024]; // fill new buffer with reply data and convert to string
                sendingDataBuffer = sendingSentence.getBytes();
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, port);   // create packet for outgoing data
                serverSocket.send(sendingPacket); // send packet to socket over network
            }
        } catch (Exception ignored) { // general "Exception"
        }
    }
}