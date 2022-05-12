package cloud.tiny.tinyinswing.examples.basic.external;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ExternalExample {

  // This URL can be set in the Ant build.xml or you can set it here directly
  public static String TINYMCE_URL = Optional.ofNullable(System.getenv("TINYMCE_URL"))
      .orElse("http://localhost/web/tinymce/tinymce.min.js");

  private ExternalExample(){}

  private static void createAndShowGUI() {
    try {
      // Create a new external configuration by adding the url of your custom TinyMCE editor (tinymce.min.js)
      final Config externalBased = Config.external(TINYMCE_URL);
      // Create a new editor with the default configuration
      final TinyMCE editor = TinyMCE.futureEditor(externalBased).get();
      // Set the editor content
      editor.setHtml(Utils.sampleText);
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
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(ExternalExample::createAndShowGUI);
  }
}
