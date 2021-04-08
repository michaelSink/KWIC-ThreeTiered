import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class gui {

    public static void main(String[] args) {

         ISender stub = new ClientStub("127.0.0.1", 4370);
         //Creates frame to  be shown to user
        JFrame frame = new JFrame("KWIC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JButton compute = new JButton("Submit");
        JButton reset = new JButton("Reset");
        
        JLabel textLabel = new JLabel("Enter Text");
        JTextField textArea = new JTextField();

        JLabel noiseWords = new JLabel("Enter noise words. (Commas seperated list)");
        JTextArea noiseArea = new JTextArea();
        JScrollPane noiseAreaScroll = new JScrollPane(noiseArea);

         noiseArea.setText("a, an, the, and, or, of, to, be, is, in, out, by, as, at, off");

         JLabel keywordLabel = new JLabel("Search Output");
         JTextArea keywordArea = new JTextArea();
         JScrollPane keywordScroll = new JScrollPane(keywordArea);

        JLabel outputLabel = new JLabel("Final Output");
        JTextArea outputArea = new JTextArea();
        JScrollPane outputScrollArea = new JScrollPane(outputArea);
      
         JButton sendDB = new JButton("Send");
         JButton resetDB = new JButton("Reset");

         JLabel dbContent = new JLabel("Send to Database");
         JTextArea dbTextArea = new JTextArea();
         JScrollPane dbTextScrollable = new JScrollPane(dbTextArea);
         

        //Event listeners for submitting
        compute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               stub.send("KEYWORDS\n" + textArea.getText().trim());
               keywordArea.setText(stub.getResponse());
            }
         });

         sendDB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                  stub.send("NOISE\n" + noiseArea.getText());
                  stub.send("DB\n" + dbTextArea.getText() + "\n");
                  outputArea.setText(stub.getResponse());
            }
         });

         //Event listeners for resetting input
         reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               textArea.setText("");
               keywordArea.setText("");
            }
         });

         resetDB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               outputArea.setText("");
               noiseArea.setText("");
               dbTextArea.setText("");
            }
         });

         //Add components to frame
         frame.add(compute);
         frame.add(reset);
         frame.add(textLabel);
         frame.add(textArea);
         frame.add(keywordLabel);
         frame.add(keywordScroll);

         frame.add(sendDB);
         frame.add(resetDB);
         frame.add(dbContent);
         frame.add(dbTextScrollable);
         frame.add(noiseWords);
         frame.add(noiseAreaScroll);

         frame.add(outputLabel);
         frame.add(outputScrollArea);

         //Set layout and show
         frame.setLayout(new GridLayout(7, 2));
         frame.setVisible(true);

    }

}