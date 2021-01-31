import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876); // create UDP socket

            while (true) { // always listen for incoming data

                // Receive data from client
                byte[] data = new byte[1024]; // create buffer with 1024 bytes of size
                DatagramPacket incomingPacket = new DatagramPacket(data, data.length); // create packet for incoming data
                serverSocket.receive(incomingPacket); // receive packet from network using socket
                String packetContent = new String(incomingPacket.getData()); // store packet content

                InetAddress IPAddress = incomingPacket.getAddress(); // extract IP address from incoming packet
                int port = incomingPacket.getPort(); // extract port number from incoming packet

                System.out.println("Message from Client: " + packetContent); // display packet content

                // Extracting core message components from packet content
                String[] messageComponents = packetContent.split(",");
                double firstNumber = Double.parseDouble(messageComponents[0]);
                double secondNumber = Double.parseDouble(messageComponents[1]);
                double operator = Double.parseDouble(messageComponents[2]);

                // Computing result
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

                // Send result to client
                String formattedResult = "Answer: " + result;
                byte[] outgoingData = new byte[1024]; // create buffer with 1024 bytes of size
                outgoingData = formattedResult.getBytes(); // fill buffer with data
                DatagramPacket sendingPacket = new DatagramPacket(outgoingData, outgoingData.length, IPAddress, port); // create packet for outgoing data
                serverSocket.send(sendingPacket); // send packet across network using socket
            }
        } catch (Exception ignored) {
        }
    }
}