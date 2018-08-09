package ru.jf17.ide;

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
import java.io.*;

public class Main extends JFrame{

   private String workFolder;

    public Main() {


        JPanel contentPane = new JPanel(new BorderLayout());
        final RSyntaxTextArea textArea = new RSyntaxTextArea(40, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
       // textArea.setFont(Font.decode("UTF8"));

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

        JMenuItem cmdItem = new JMenuItem("CMD");
        fileMenu.add(cmdItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        cmdItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Process p = null;
                try {

                    if(workFolder!=null){

                        File workdirFile= new File(workFolder);
                        String []str={};
                        p = Runtime.getRuntime().exec("cmd /c start cmd.exe",str,workdirFile);

                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
              /*  try {
                 p.waitFor();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }*/
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyCustomFilter myFilter = new MyCustomFilter();
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileFilter(myFilter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();

                    workFolder=file.getParent();
                    //    /*
                    try {
                        // What to do with the file, e.g. display it in a TextArea

                        Reader reader = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF8");

                       textArea.read(reader,file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                    }

                    //   */
                }
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(saveItem);

        contentPane.add(new RTextScrollPane(textArea));

        // A CompletionProvider is what knows of all possible completions, and
        // analyzes the contents of the text area at the caret position to
        // determine what completion choices should be presented. Most instances
        // of CompletionProvider (such as DefaultCompletionProvider) are designed
        // so that they can be shared among multiple text components.
        CompletionProvider provider = createCompletionProvider();

        // An AutoCompletion acts as a "middle-man" between a text component
        // and a CompletionProvider. It manages any options associated with
        // the auto-completion (the popup trigger key, whether to display a
        // documentation window along with completion choices, etc.). Unlike
        // CompletionProviders, instances of AutoCompletion cannot be shared
        // among multiple text components.
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

    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
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
                } catch (Exception e) {
                    /* Never happens */ }
                new Main().setVisible(true);
              //  new Main().setVisible(true);
              //  new Main().setVisible(true);
            }
        });
    }
}
