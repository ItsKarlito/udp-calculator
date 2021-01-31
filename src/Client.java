import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        setupGUI();
    }

    private static void setupGUI() {
        // Main window
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 455, 165); // set position and dimension
        frame.setResizable(false); // lock window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fully terminate application when user clicks close button
        frame.setTitle("UDP Calculator"); // set window title
        frame.getContentPane().setLayout(null); // set layout to correctly draw components

        // First number label
        JLabel firstNumberLabel = new JLabel("First Number"); // create label component
        firstNumberLabel.setBounds(20, 20, 200, 20); // set position relative to top left corner of window and set dimensions
        frame.getContentPane().add(firstNumberLabel); // add component to layout

        // Second number label
        JLabel secondNumberLabel = new JLabel("Second Number");
        secondNumberLabel.setBounds(20, 45, 200, 20);
        frame.getContentPane().add(secondNumberLabel);

        // Result label
        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(20, 70, 400, 20);
        frame.getContentPane().add(resultLabel);

        // First number textField
        JTextField firstNumberTextField = new JTextField("");
        firstNumberTextField.setBounds(152, 20, 150, 20);
        frame.getContentPane().add(firstNumberTextField);

        // Second number textField
        JTextField secondNumberTextField = new JTextField("");
        secondNumberTextField.setBounds(152, 45, 150, 20);
        frame.getContentPane().add(secondNumberTextField);

        // Add button
        JButton addButton = new JButton("+");
        addButton.setBounds(20, 100, 65, 23);
        frame.getContentPane().add(addButton);
        addButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 0))); // do something when button is clicked

        // Subtract button
        JButton subtractButton = new JButton("-");
        subtractButton.setBounds(90, 100, 65, 23);
        frame.getContentPane().add(subtractButton);
        subtractButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 1)));

        // Multiply button
        JButton multiplyButton = new JButton("x");
        multiplyButton.setBounds(160, 100, 65, 23);
        frame.getContentPane().add(multiplyButton);
        multiplyButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 2)));

        // Divide button
        JButton divideButton = new JButton("÷");
        divideButton.setBounds(230, 100, 65, 23);
        frame.getContentPane().add(divideButton);
        divideButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 3)));

        // Maximum button
        JButton maxButton = new JButton("Max");
        maxButton.setBounds(300, 100, 65, 23);
        frame.getContentPane().add(maxButton);
        maxButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 4)));

        // Minimum button
        JButton minButton = new JButton("Min");
        minButton.setBounds(370, 100, 65, 23);
        frame.getContentPane().add(minButton);
        minButton.addActionListener(e -> resultLabel.setText(sendMessage(firstNumberTextField.getText() + "," + secondNumberTextField.getText() + "," + 5)));

        // Set window visibility
        frame.setVisible(true);
    }

    private static String sendMessage(String msg) {
        try {
            String[] messageComponents = msg.split(","); // split message into core components
            Double.parseDouble(messageComponents[0]); // test if input data is valid, error will redirect to catch block
            Double.parseDouble(messageComponents[1]); // test if input data is valid, error will redirect to catch block
            Double.parseDouble(messageComponents[2]); // test if input data is valid, error will redirect to catch block

            DatagramSocket clientSocket = new DatagramSocket(); // create UDP socket

            InetAddress IPAddress = InetAddress.getByName("localhost"); // create an IP address
            int port = 9876; // create an port number

            // Send data to server
            byte[] outgoingData = new byte[1024]; // create buffer with 1024 bytes of size
            outgoingData = msg.getBytes(); // fill buffer with data
            DatagramPacket outgoingPacket = new DatagramPacket(outgoingData, outgoingData.length, IPAddress, port); // create packet for outgoing data
            clientSocket.send(outgoingPacket); // send packet across network using socket

            // Receive result from server
            byte[] incomingData = new byte[1024]; // create buffer with 1024 bytes of size
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length); // create packet for incoming data
            clientSocket.receive(incomingPacket); // receive packet from network using socket
            String packetContent = new String(incomingPacket.getData()); // store packet content
            System.out.println("Message from Server: " + packetContent); // display packet content
            return packetContent.trim(); // return packet content with leading and trailing white spaces removed

        } catch (NumberFormatException ex) {
            return "ERROR: Invalid Input"; // return error
        } catch (Exception ignored) {
            return "ERROR: Unknown"; // return error
        }
    }
}