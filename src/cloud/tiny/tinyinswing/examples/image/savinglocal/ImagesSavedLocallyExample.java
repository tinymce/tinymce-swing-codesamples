package cloud.tiny.tinyinswing.examples.image.savinglocal;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import javax.swing.*;


public class ImagesSavedLocallyExample {

  private ImagesSavedLocallyExample(){}

  private static void createAndShowGUI(final String contentDir) {
    try {
      // Create a new embedded configuration
      final Path contentPath = Paths.get(contentDir);
      Files.createDirectories(contentPath);
      final Config config = Config.embedded()
          .setContentPath(contentPath)
          .setImageSaverLocal(contentPath)
          .addPlugin("image").addPlugin("imagetools")
          .putProperty("toolbar", "image");
      // Create a new editor with the default configuration
      final TinyMCE editor = TinyMCE.futureEditor(config).get();
      // Set the editor content
      editor.setHtml("<p><img src=\"tiny-logo.png\"></p>");
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
    } catch (ExecutionException | InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    if (args.length != 1) {
      System.err.println("Expected the path to the document");
      return;
    }
    SwingUtilities.invokeLater(() -> { createAndShowGUI(args[0]); } );
  }
}
