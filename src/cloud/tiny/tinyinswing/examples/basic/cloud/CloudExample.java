package cloud.tiny.tinyinswing.examples.basic.cloud;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Cloud;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public final class CloudExample {

  // This API key can be set in the Ant build.xml or you can set it here directly
  public static String CLOUD_API_KEY = Optional.ofNullable(System.getenv("CLOUD_API_KEY"))
      .orElse("no-api-key");
  // This cloud channel can be set in the Ant build.xml or you can set it here directly
  public static String CLOUD_CHANNEL = Optional.ofNullable(System.getenv("CLOUD_CHANNEL"))
      .orElse("6");

  private CloudExample() {}

  private static void createAndShowGUI() {
    try {
      // Create a new cloud configuration
      final Config cloudBased = Config.cloud(CLOUD_API_KEY, CLOUD_CHANNEL);
      // Create a new editor with the default cloud configuration
      final TinyMCE editor = TinyMCE.futureEditor(cloudBased).get();
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
    SwingUtilities.invokeLater(CloudExample::createAndShowGUI);
  }
}
