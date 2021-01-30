import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        setupGUI();
    }

    private static void setupGUI() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 455, 165); // set position and dimensions
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //need this to fully terminate application
        frame.setTitle("UDP Calculator");
        frame.getContentPane().setLayout(null); //use to correctly draw other components

        // imagine every component as a rectangle
        JLabel firstNumberLabel = new JLabel(""); // create empty label component
        firstNumberLabel.setBounds(20, 20, 200, 20); // relative to top left corner of application window
        frame.getContentPane().add(firstNumberLabel); // add this at end of each component after creating a new component
        firstNumberLabel.setText("First Number");

        // imagine every component as a rectangle
        JLabel secondNumberLabel = new JLabel(""); // create empty label component
        secondNumberLabel.setBounds(20, 45, 200, 20); // relative to top left corner of application window
        frame.getContentPane().add(secondNumberLabel); // add this at end of each component after creating a new component
        secondNumberLabel.setText("Second Number");

        // imagine every component as a rectangle
        JLabel resultLabel = new JLabel(""); // create empty label component
        resultLabel.setBounds(20, 70, 400, 20); // relative to top left corner of application window
        frame.getContentPane().add(resultLabel); // add this at end of each component after creating a new component
        resultLabel.setText("");

        JTextField firstNumberTextField = new JTextField(""); // used to type one line of text
        firstNumberTextField.setBounds(152, 20, 150, 20);
        frame.getContentPane().add(firstNumberTextField);

        JTextField secondNumberTextField = new JTextField(""); // used to type one line of text
        secondNumberTextField.setBounds(152, 45, 150, 20);
        frame.getContentPane().add(secondNumberTextField);

        JButton addButton = new JButton("+");
        addButton.setBounds(20, 100, 65, 23);
        frame.getContentPane().add(addButton);

        JButton subtractButton = new JButton("-");
        subtractButton.setBounds(90, 100, 65, 23);
        frame.getContentPane().add(subtractButton);

        JButton multiplyButton = new JButton("x");
        multiplyButton.setBounds(160, 100, 65, 23);
        frame.getContentPane().add(multiplyButton);

        JButton divideButton = new JButton("÷");
        divideButton.setBounds(230, 100, 65, 23);
        frame.getContentPane().add(divideButton);

        JButton maxButton = new JButton("Max");
        maxButton.setBounds(300, 100, 65, 23);
        frame.getContentPane().add(maxButton);

        JButton minButton = new JButton("Min");
        minButton.setBounds(370, 100, 65, 23);
        frame.getContentPane().add(minButton);

        addButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 0)));
        subtractButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 1)));
        multiplyButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 2)));
        divideButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 3)));
        maxButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 4)));
        minButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 5)));

        frame.setVisible(true); //draw stuff first then show window, prevents issues
    }

    private static String sendMessage(String msg) {
        try {
            String[] messageComponents = msg.split(",");
            Double.parseDouble(messageComponents[0]); // Use to test if input data is valid
            Double.parseDouble(messageComponents[1]); // Use to test if input data is valid
            Double.parseDouble(messageComponents[2]); // Use to test if input data is valid

            DatagramSocket clientSocket = new DatagramSocket();

            // Send a message to server
            InetAddress IPAddress = InetAddress.getByName("localhost"); //InetAddress IPAddress = InetAddress.getByAddress(new byte[] {127,0,0,1}); // localhost loopback connection 127.0.0.1
            int port = 9876;

            byte[] sendingDataBuffer = new byte[1024]; // fill new buffer with reply data and convert to string
            sendingDataBuffer = msg.getBytes();

            DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, port); // create packet for outgoing data
            clientSocket.send(sendingPacket);// send packet to socket over network

            // Receive server reply
            byte[] data = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(data, data.length);
            clientSocket.receive(receivedPacket);
            String receivedSentence = new String(receivedPacket.getData());
            System.out.println("Message from Server: " + receivedSentence);
            return receivedSentence.trim();

        } catch (NumberFormatException ex) {
            return "ERROR: Invalid Input";
        } catch (Exception ignored) {
            return "ERROR: Unknown";
        }
    }
}