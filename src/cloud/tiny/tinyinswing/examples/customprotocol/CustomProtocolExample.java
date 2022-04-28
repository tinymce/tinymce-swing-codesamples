package cloud.tiny.tinyinswing.examples.customprotocol;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomProtocolHandler;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomURLRequest;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomURLResponse;

import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class CustomProtocolExample {

  private CustomProtocolExample() {}

  private static void createAndShowGUI() {
    try {
      // A custom protocol handlers will intercept all requests for a specific protocol `<protocol>://<path>`
      // In this example we are adding a `localimg` protocol to handler urls like: `localimg://my_image.png`
      TinyMCE.addProtocolHandler("localimg", new CustomProtocolHandler() {
        // CustomProtocolHandlers use the onRequest method to take a customUrlRequest and return a CustomUrlResponse
        @Override
        public CustomURLResponse onRequest(CustomURLRequest customURLRequest) {
          // The CustomURLRequest object contains the Url, method and headers of the request
          // The CustomURLResponse object takes an array of bytes as the response content, an http status code (int), and, optionally, extra headers
          final CustomURLResponse r;
          if (customURLRequest.getUrl().endsWith(".png")) {
            // `localImage` is a base64 encoded image string
            String data = Utils.localImage.substring(Utils.localImage.indexOf(",") + 1);
            r = new CustomURLResponse(Base64.getDecoder().decode(data), 200);
          } else {
            r = new CustomURLResponse("".getBytes(), 404);
          }
          return r;
        }
      });
      // Create a new embedded configuration
      final Config config = Config.embedded().addPlugin("link");
      // Create a new editor with the default configuration
      final TinyMCE editor = TinyMCE.futureEditor(config).get();
      // Set the editor content. The `localimg` protocol will be handled by our custom protocol handler defined previously
      editor.setHtml("<p><img src='localimg://my_image.png'></p>" +
          "<p><img src='localimg://not_an_image.txt'></p>");
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
    SwingUtilities.invokeLater(CustomProtocolExample::createAndShowGUI);
  }
}
