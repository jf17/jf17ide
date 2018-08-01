package ru.jf17.jide;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * autocomplete-2.6.1.jar
 * rsyntaxtextarea-2.6.1.jar
 * 
 */



public class Main extends JFrame{

 

    public Main() {

        JPanel contentPane = new JPanel(new BorderLayout());
        RSyntaxTextArea textArea = new RSyntaxTextArea(40, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

       // Font font = new Font("Verdana", Font.PLAIN, 11);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
       // fileMenu.setFont(font);
       
       JMenuItem saveItem = new JMenuItem("Save");
       

        JMenuItem openItem = new JMenuItem("Open");
       // newMenu.setFont(font);
        fileMenu.add(openItem);
        
        
       JMenuItem saveAsItem = new JMenuItem("Save as ..");
       // newMenu.setFont(font);
        fileMenu.add(saveAsItem);
       
        JMenuItem newItem = new JMenuItem("New");
       // newMenu.setFont(font);
        fileMenu.add(newItem);


        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        
        
        
           exitItem.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });
        
        
        
        

        menuBar.add(fileMenu);
        menuBar.add(saveItem);


        contentPane.add(new RTextScrollPane(textArea));



        CompletionProvider provider = createCompletionProvider();

        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);

        int mask = InputEvent.CTRL_MASK;
        ac.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, mask));

        setJMenuBar(menuBar);

        setContentPane(contentPane);
        setTitle("JF17 IDE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }


    private CompletionProvider createCompletionProvider() {


        DefaultCompletionProvider provider = new DefaultCompletionProvider();

  

        // JF17 template
        provider.addCompletion(new BasicCompletion(provider, "class "));
        provider.addCompletion(new BasicCompletion(provider, "import "));
        provider.addCompletion(new BasicCompletion(provider, "package "));
        provider.addCompletion(new BasicCompletion(provider, "public "));
        provider.addCompletion(new BasicCompletion(provider, "private "));
        provider.addCompletion(new BasicCompletion(provider, "extends "));
        provider.addCompletion(new BasicCompletion(provider, "final "));
        provider.addCompletion(new BasicCompletion(provider, "String "));
        provider.addCompletion(new BasicCompletion(provider, "Integer "));
        provider.addCompletion(new BasicCompletion(provider, "boolean "));
        provider.addCompletion(new BasicCompletion(provider, "true"));
        provider.addCompletion(new BasicCompletion(provider, "false"));
        provider.addCompletion(new BasicCompletion(provider, "void "));
        provider.addCompletion(new BasicCompletion(provider, "List<"));
        provider.addCompletion(new BasicCompletion(provider, "ArrayList<"));
        provider.addCompletion(new BasicCompletion(provider, "try{\n"));
        provider.addCompletion(new BasicCompletion(provider, "catch{\n"));
        provider.addCompletion(new BasicCompletion(provider, "new "));
        provider.addCompletion(new BasicCompletion(provider, "return "));
        provider.addCompletion(new BasicCompletion(provider, "Data "));
        provider.addCompletion(new BasicCompletion(provider, "Autowired "));

        provider.addCompletion(new ShorthandCompletion(provider, "print",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "main",
                "public static void main(String[] args){ \n            // your code ", "public static void main(String[] args){ \n            // your code"));





        return provider;

    }

    public static void main(String[] args) {
        // Instantiate GUI on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    String laf = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(laf);
                } catch (Exception e) { /* Never happens */ }
                new Main().setVisible(true);
            }
        });
    }

    
}
