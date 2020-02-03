package cloud.tiny.tinyinswing.examples.basic.embedded;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class EmbeddedExample {

  private EmbeddedExample(){}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new embedded configuration
    final Config embeddedBased = Config.embedded();
    // Create a new editor with the default configuration
    final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();
    // Set the editor content
    editor.setHtml(Utils.welcomeText);
    // Create a button to get the content of the editor
    final JButton printToConsole = new JButton("Print to console");
    printToConsole.addActionListener(e -> System.out.println(editor.getHtml()));
    // Add the editor and button to the JFrame
    final JFrame frame = new JFrame();
    frame.add(editor.component(), BorderLayout.CENTER);
    frame.add(printToConsole, BorderLayout.SOUTH);
    frame.pack();
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // ensure the editor resources are cleaned up when the window is closed
    TinyMCE.shutdownOnClose(frame);
    // display the editor
    frame.setVisible(true);
  }
}
