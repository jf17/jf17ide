package ru.jf17.ide;

import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class JavaWindow  extends JFrame {


    private String workFolder;
    private boolean isOpen;
    private String pathOpenFile;
    File file ;

    public JavaWindow(File pathOpenFile_IN) throws HeadlessException {
        this.file = pathOpenFile_IN;


        isOpen = false;


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


        fileMenu.addSeparator();

        JMenuItem cmdItem = new JMenuItem("CMD");
        fileMenu.add(cmdItem);

        JMenuItem openFileDirectoryItem = new JMenuItem("Open file directory");
        fileMenu.add(openFileDirectoryItem);





        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(isOpen) {
                    try {
                        textArea.write(new FileWriter(pathOpenFile));

                        JOptionPane.showMessageDialog(null, "File saved !");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "File is NOT open!");
                }



            }});

        cmdItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                OSValidator validator = new OSValidator();

                if (isOpen && validator.isWindows()) {

                    Process p = null;
                    try {

                        if (workFolder != null) {

                            File workdirFile = new File(workFolder);
                            String[] str = {};
                            p = Runtime.getRuntime().exec("cmd /c start cmd.exe", str, workdirFile);

                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else if(isOpen && validator.isUnix()){

                    String command= "/usr/bin/xterm";

                    Process p = null;
                    try {

                        if (workFolder != null) {

                            ProcessBuilder pb =  new ProcessBuilder(command);
                            pb.directory(new File(workFolder));
                            p = pb.start();

                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });


                    workFolder=file.getParent();
                    //    /*
                    try {
                        // What to do with the file, e.g. display it in a TextArea
                        pathOpenFile = file.getAbsolutePath();
                        isOpen=true;

                        Reader reader = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF8");

                        textArea.read(reader,file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                    }




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
        setTitle(file.getName() +" - JF17 IDE " );
      //  setDefaultCloseOperation();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
        provider.addCompletion(new BasicCompletion(provider, "interface "));
        provider.addCompletion(new BasicCompletion(provider, "import "));
        provider.addCompletion(new BasicCompletion(provider, "package "));
        provider.addCompletion(new BasicCompletion(provider, "public "));
        provider.addCompletion(new BasicCompletion(provider, "private "));
        provider.addCompletion(new BasicCompletion(provider, "extends "));
        provider.addCompletion(new BasicCompletion(provider, "implements "));
        provider.addCompletion(new BasicCompletion(provider, "final "));
        provider.addCompletion(new BasicCompletion(provider, "String "));
        provider.addCompletion(new BasicCompletion(provider, "Integer "));
        provider.addCompletion(new BasicCompletion(provider, "boolean "));
        provider.addCompletion(new BasicCompletion(provider, "true"));
        provider.addCompletion(new BasicCompletion(provider, "false"));
        provider.addCompletion(new BasicCompletion(provider, "void "));
        provider.addCompletion(new BasicCompletion(provider, "List<"));
        provider.addCompletion(new BasicCompletion(provider, "ArrayList<"));
        provider.addCompletion(new BasicCompletion(provider, "Map<"));
        provider.addCompletion(new BasicCompletion(provider, "HashMap<"));
        provider.addCompletion(new BasicCompletion(provider, "try{\n"));
        provider.addCompletion(new BasicCompletion(provider, "catch{\n"));
        provider.addCompletion(new BasicCompletion(provider, "new "));
        provider.addCompletion(new BasicCompletion(provider, "return "));
        // Spring Anotations :
        provider.addCompletion(new BasicCompletion(provider, "Bean "));
        provider.addCompletion(new BasicCompletion(provider, "Data "));
        provider.addCompletion(new BasicCompletion(provider, "Autowired "));
        provider.addCompletion(new BasicCompletion(provider, "Service "));
        provider.addCompletion(new BasicCompletion(provider, "Repository "));
        provider.addCompletion(new BasicCompletion(provider, "Component "));
        provider.addCompletion(new BasicCompletion(provider, "Controller "));
        provider.addCompletion(new BasicCompletion(provider, "RestController "));

        provider.addCompletion(new ShorthandCompletion(provider, "hello",
                "class HelloWorld {\n" +
                        "    public static void main(String[] args){\n" +
                        "\t\tSystem.out.println(\"Hello World!\");\n" +
                        "    }\n" +
                        "}\n", "Hello World!"));

        provider.addCompletion(new ShorthandCompletion(provider, "print",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "main",
                "public static void main(String[] args){ \n            // your code ", "public static void main ..."));

        return provider;

    }












}
