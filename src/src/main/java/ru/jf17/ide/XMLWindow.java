package ru.jf17.ide;

import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

import static org.fife.ui.rsyntaxtextarea.TokenTypes.*;

public class XMLWindow extends JFrame {

    private String workFolder;
    private boolean isOpen;
    private String pathOpenFile;
    File file;


    public XMLWindow(File pathOpenFile_IN) throws HeadlessException {
        this.file = pathOpenFile_IN;


        isOpen = false;


        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(0, 0, 0));
        final RSyntaxTextArea textArea = new RSyntaxTextArea(40, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        textArea.setCodeFoldingEnabled(true);
        // textArea.setFont(Font.decode("UTF8"));
        Font font1 = new Font("FiraCode", Font.PLAIN, 11);
        textArea.setFont(font1);

        textArea.setBackground(new Color(0, 0, 0)); // цвет фона
        textArea.setForeground(new Color(168, 168, 168)); // цвет текста
        textArea.setCurrentLineHighlightColor(new Color(10, 10, 10)); //цвет активной линии
        textArea.setMarginLineColor(new Color(0, 0, 0));
        textArea.setCaretColor(Color.RED);
        textArea.setHighlightSecondaryLanguages(false);
        SyntaxScheme syntScheme = textArea.getSyntaxScheme();
        syntScheme.setStyle(MARKUP_TAG_NAME, new Style(Color.darkGray));
        syntScheme.setStyle(MARKUP_TAG_ATTRIBUTE_VALUE, new Style(new Color(83, 22, 136)));
        syntScheme.setStyle(MARKUP_TAG_DELIMITER, new Style(new Color(48, 48, 48)));

        syntScheme.setStyle(MARKUP_COMMENT, new Style(Color.darkGray)); // комментарии


        // Font font = new Font("Verdana", Font.PLAIN, 11);
        BackgroundMenuBar menuBar = new BackgroundMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(new Color(168, 168, 168));
        // fileMenu.setFont(font);
        JMenu fontMenu = new JMenu("Размер шрифта");
        fontMenu.setForeground(new Color(168, 168, 168));

        JMenuItem fontNORMALItem = new JMenuItem("Default");
        fontMenu.add(fontNORMALItem);

        JMenuItem fontBigItem = new JMenuItem("Big");
        fontMenu.add(fontBigItem);


        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setForeground(new Color(168, 168, 168));

        JMenuItem emptyItem1 = new JMenuItem("   ");
        JMenuItem emptyItem2 = new JMenuItem("   ");


        JMenuItem fontUPItem = new JMenuItem("font size +");
        fontUPItem.setForeground(new Color(168, 168, 168));
        JMenuItem fontDOWNItem = new JMenuItem("font size -");
        fontDOWNItem.setForeground(new Color(168, 168, 168));

        JMenuItem cmdItem = new JMenuItem("CMD");
        fileMenu.add(cmdItem);

        JMenuItem openFileDirectoryItem = new JMenuItem("Open file directory");
        fileMenu.add(openFileDirectoryItem);


        fontNORMALItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                textArea.setFont(new Font(font123.getName(), font123.getStyle(), 13));
            }
        });

        fontBigItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                textArea.setFont(new Font(font123.getName(), font123.getStyle(), 21));
            }
        });

        fontUPItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                int sizetepm = font123.getSize() + 1;
                textArea.setFont(new Font(font123.getName(), font123.getStyle(), sizetepm));

            }
        });

        fontDOWNItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font123 = textArea.getFont();
                int sizetepm = font123.getSize() - 1;
                if (sizetepm < 8) {
                    sizetepm = 8;
                }

                textArea.setFont(new Font(font123.getName(), font123.getStyle(), sizetepm));

            }
        });


        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (isOpen) {
                    try {

                        Writer writerr = new OutputStreamWriter(new FileOutputStream(pathOpenFile), "UTF-8");
                        textArea.write(writerr);
                        writerr.close();
                        //  textArea.write(new FileWriter(pathOpenFile));

                        JOptionPane.showMessageDialog(null, "File saved !");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "File is NOT open!");
                }


            }
        });

        workFolder = file.getParent();
        //    /*
        try {
            // What to do with the file, e.g. display it in a TextArea
            pathOpenFile = file.getAbsolutePath();
            isOpen = true;

            Reader reader = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF8");

            textArea.read(reader, file.getAbsolutePath());
            textArea.setCaretPosition(0);
            reader.close();
        } catch (IOException ex) {
            System.out.println("problem accessing file" + file.getAbsolutePath());
        }


        menuBar.add(fileMenu);
        menuBar.add(fontMenu);
        menuBar.add(saveItem);
        menuBar.add(fontUPItem);
        menuBar.add(fontDOWNItem);
        menuBar.add(emptyItem1);
        menuBar.add(emptyItem2);

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
        setTitle(file.getName() + " - JF17 IDE ");
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

        provider.addCompletion(new BasicCompletion(provider, "class"));


        provider.addCompletion(new ShorthandCompletion(provider, "div",
                "<div>\n\n</div>", "<div>"));

        provider.addCompletion(new ShorthandCompletion(provider, "header",
                "<header>\n\n</header>", "<header>"));

        provider.addCompletion(new ShorthandCompletion(provider, "footer",
                "<footer>\n\n</footer>", "<footer>"));

        provider.addCompletion(new ShorthandCompletion(provider, "script",
                "<script type=\"text/javascript\">\n\n</script>", "<script>"));

        provider.addCompletion(new ShorthandCompletion(provider, "code",
                "<pre><code class=\"code\">\n\n</code></pre>", "<code>"));

        provider.addCompletion(new ShorthandCompletion(provider, "section",
                "<section class=\"content\"> \n\n<section>", "<section>"));


        return provider;

    }
}
