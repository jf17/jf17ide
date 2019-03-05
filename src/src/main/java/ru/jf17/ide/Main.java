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
   private boolean isOpen;
   private String pathOpenFile;

    public Main() {

        isOpen = false;


        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(0,0,0));
        final RSyntaxTextArea textArea = new RSyntaxTextArea(40, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        textArea.setCodeFoldingEnabled(true);


        textArea.setBackground(new Color(40,42,54)); // цвет фона
        textArea.setForeground(new Color(248, 248, 242)); // цвет текста
        textArea.setCurrentLineHighlightColor(new Color(68 ,71 ,90)); //цвет активной линии
       // textArea.setFont(Font.decode("UTF8"));
        Font font1 = new Font("FiraCode", Font.PLAIN, 11);
        textArea.setFont(font1);

        textArea.setTransferHandler(new FileDropHandler());

        // Font font = new Font("Verdana", Font.PLAIN, 11);
        BackgroundMenuBar menuBar = new BackgroundMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(new Color(168, 168, 168));


        // fileMenu.setFont(font);


       // JMenuItem saveItem = new JMenuItem("Save");

        JMenuItem openItemJava = new JMenuItem("Open new Java Window");
        // newMenu.setFont(font);
        fileMenu.add(openItemJava);

        JMenuItem openItemHTML = new JMenuItem("Open new HTML Window");
        // newMenu.setFont(font);
        fileMenu.add(openItemHTML);

        JMenuItem openItemCPP = new JMenuItem("Open new C++ Window");
        // newMenu.setFont(font);
        fileMenu.add(openItemCPP);

      //  JMenuItem saveAsItem = new JMenuItem("Save as ..");
        // newMenu.setFont(font);
      //  fileMenu.add(saveAsItem);

       // JMenuItem newItem = new JMenuItem("New");
        // newMenu.setFont(font);
       // fileMenu.add(newItem);

        fileMenu.addSeparator();

      //  JMenuItem cmdItem = new JMenuItem("CMD");
      //  fileMenu.add(cmdItem);

      //  JMenuItem openFileDirectoryItem = new JMenuItem("Open file directory");
      //  fileMenu.add(openFileDirectoryItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        openItemCPP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyCustomFilterCPP myFilter = new MyCustomFilterCPP();

                JFileChooser fileopen;

                if(workFolder!=null) {
                    File workdirFile= new File(workFolder);
                    fileopen = new JFileChooser(workdirFile);
                }else{
                    fileopen = new JFileChooser();
                }

                fileopen.setFileFilter(myFilter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();

                    workFolder=file.getParent();
                    //    /*

                    // What to do with the file, e.g. display it in a TextArea
                    pathOpenFile = file.getAbsolutePath();
                    isOpen=true;

                    new CppWindow(file);




                    //   */
                }
            }
        });


        openItemHTML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyCustomFilterHTML myFilter = new MyCustomFilterHTML();

                JFileChooser fileopen;

                if(workFolder!=null) {
                    File workdirFile= new File(workFolder);
                    fileopen = new JFileChooser(workdirFile);
                }else{
                    fileopen = new JFileChooser();
                }


                fileopen.setFileFilter(myFilter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();

                    workFolder=file.getParent();
                    //    /*

                    // What to do with the file, e.g. display it in a TextArea
                    pathOpenFile = file.getAbsolutePath();
                    isOpen=true;

                    new HTMLWindow(file);




                    //   */
                }
            }
        });



        openItemJava.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyCustomFilter myFilter = new MyCustomFilter();

                JFileChooser fileopen;

                if(workFolder!=null) {
                    File workdirFile= new File(workFolder);
                    fileopen = new JFileChooser(workdirFile);
                }else{
                    fileopen = new JFileChooser();
                }

                fileopen.setFileFilter(myFilter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();

                    workFolder=file.getParent();
                    //    /*

                        // What to do with the file, e.g. display it in a TextArea
                        pathOpenFile = file.getAbsolutePath();
                        isOpen=true;

                        new JavaWindow(file);




                    //   */
                }
            }
        });

        menuBar.add(fileMenu);
     //   menuBar.add(saveItem);

        RTextScrollPane rtsp = new RTextScrollPane(textArea);


        contentPane.add(rtsp);

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
