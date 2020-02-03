package cloud.tiny.tinyinswing.examples.basic.external;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class ExternalExample {

  private ExternalExample(){}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new external configuration by adding the url of your custom TinyMCE editor (tinymce.min.js)
    // TODO: Replace the URL with the URL of your tinyMCE library
    final Config externalBased = Config.external("http://localhost/web/tinymce/tinymce.min.js");
    // Create a new editor with the default configuration
    final TinyMCE editor = TinyMCE.futureEditor(externalBased).get();
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
