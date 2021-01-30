import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args){

        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //need this to fully terminate application
        frame.setTitle("GUI Application");
        frame.getContentPane().setLayout(null); //use to correctly draw other components

        // imagine every component as a rectangle
        JLabel label = new JLabel(""); // create empty label component
        label.setBounds(20, 20, 400, 20); // relative to top left corner of application window
        frame.getContentPane().add(label); // add this at end of each component after creating a new component

        label.setFont(new Font("Times", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        label.setText("Hello World");

        JTextField textField= new JTextField(""); // used to type one line of text
        textField.setFont(new Font("Times", Font.BOLD, 14)); //optional
        textField.setBounds(20, 70, 150, 20);
        frame.getContentPane().add(textField);

        JTextArea textArea = new JTextArea(); // mandatory line
        textArea.setBounds(20, 150, 300, 300); // mandatory line
        frame.getContentPane().add(textArea); // mandatory line

        //textArea.setText(); //clears everything
        textArea.setText("Hello"); // overwrites old text in textArea
        textArea.append(" World"); // appends to end of old text

        JButton button = new JButton("Click");
        button.setBounds(20, 120, 100, 23);
        frame.getContentPane().add(button);

        button.addActionListener(new ActionListener() { // click button event listener
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String textFieldData = textField.getText(); // data as string

                    int x = Integer.parseInt(textFieldData); // convert sting to int
                    //double y =Double.parseDouble(textFieldData);

                    textArea.append("\n" + x + " + 10 = " + (x + 10));

                    label.setText("Done"); // change .getText to .getInt
                }
                catch (NumberFormatException ex) { // more specific error instead of general "Exception"
                    label.setText("ERROR: Invalid Input"); // change .getText to .getInt
                }
            }
        });

        frame.setVisible(true); //draw stuff first then show window, prevents issues
    }
}
