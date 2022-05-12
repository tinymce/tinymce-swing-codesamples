package cloud.tiny.tinyinswing.examples.configuration.properties;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public final class PropertiesConfigurationExample {

  private PropertiesConfigurationExample(){}

  private static void createAndShowGUI() {
    try {
      // Create a new embedded configuration and set TinyMCE's settings
      final Config config = Config.embedded()
        .addPlugins("advlist anchor autolink charmap code fullscreen help image insertdatetime link lists media preview searchreplace table visualblocks wordcount")
        .putProperty("toolbar", "undo redo | blocks | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help")
        .putProperty("menubar", false);
      // Create a new editor with the configuration
      final TinyMCE editor = TinyMCE.futureEditor(config).get();
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
    SwingUtilities.invokeLater(PropertiesConfigurationExample::createAndShowGUI);
  }
}
