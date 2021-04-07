import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class gui {

    public static void main(String[] args) {

         iController controller = new MasterController();

         //Creates frame to  be shown to user
        JFrame frame = new JFrame("KWIC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

         //JScrollPane scroll = new JScrollPane(outputArea);
         JTextArea ta = new JTextArea();

        //Create labels and buttons to be displayed
        JLabel label = new JLabel("Enter Text");
        JLabel outputLabel = new JLabel("Final Output");
        JLabel circularOutput = new JLabel("Circular Output");
        JButton compute = new JButton("Submit");
        JButton reset = new JButton("Reset");

        JLabel noiseWords = new JLabel("Enter noise words. (Commas seperated list)");
         JTextArea noiseArea = new JTextArea();
         JScrollPane noiseAreaScroll = new JScrollPane(noiseArea);

        //Input text area
         JTextArea outputArea = new JTextArea();
         JTextArea circularArea = new JTextArea();
        JScrollPane textArea = new JScrollPane(ta);
        JScrollPane outputScrollArea = new JScrollPane(outputArea);
        JScrollPane circularScrollArea = new JScrollPane(circularArea);
      
        //Event listeners for submitting
        compute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               controller.setNoiseWords(noiseArea.getText().trim());
               controller.compute(ta.getText().trim());
               outputArea.setText(controller.getOutput());
               circularArea.setText(controller.getCircularOutput());
               controller.resetNoiseWords();
            }
         });

         //Event listeners for resetting input
         reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ta.setText("");
               outputArea.setText("");
               circularArea.setText("");
               noiseArea.setText("");
            }
         });

         //Add components to frame
         frame.add(compute);
         frame.add(reset);
         frame.add(label);
         frame.add(textArea);
         frame.add(noiseWords);
         frame.add(noiseAreaScroll);
         frame.add(circularOutput);
         frame.add(circularScrollArea);
         frame.add(outputLabel);
         frame.add(outputScrollArea);

         //Set layout and show
         frame.setLayout(new GridLayout(5, 2));
         frame.setVisible(true);

    }

}