package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.*;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class DefaultImageHandling {

  // Make a map to store images in base64
  HashMap<String, String> imageCache = new HashMap<>();

  public DefaultImageHandling() throws ExecutionException, InterruptedException {

    HashMap<String, String> properties = new HashMap<>();
    properties.put("plugins", "image");
    properties.put("toolbar", "image");

    final Config config = Config.embedded().
        setProperties(properties).
        /* Set up an Image Upload Handler
        You must provide an imageUploadHandler that will take a TinyBlob object and return
        an Optional<String> of the new path of the stored image if stored successfully or
        an empty Option if the storing failed
         */
        setImageUploadHandler(new ImageUploadHandler() {
          @Override
          public Optional<String> processImage(TinyBlob blob) {
            // After receiving the image blob we store the name and the base64 value in our map
            imageCache.put(blob.getName(), blob.getBase64());
            // And return the path of where the image is stored
            // We will use custom protocols to provide access to our image at localimg://<name of the image>
            return Optional.of("localimg://" + blob.getName());
          }
        }).
        /* Set up our custom protocol handler
        You can create a custom protocol handler that will return a custom response to request using
        the specified protocol. In this case, everything going to localimg://* will be handled here
        */
        addProtocolHandler("localimg", new CustomProtocolHandler() {
          @Override
          public CustomURLResponse onRequest(CustomURLRequest request) {
            // Here we assume that every request is localimg://<name of the image> and we will use <name of the image>
            // to look for the image in our cache map
            String imagePath = request.getUrl().substring(11);
            if (imageCache.containsKey(imagePath)) {
              // We create a custom response with the content of the image
              return new CustomURLResponse(Base64.getDecoder().decode(imageCache.get(imagePath)), 200);
            } else {
              // If we did not find the image, we return a 404
              return new CustomURLResponse("".getBytes(), 404);
            }
          }
        });
    // Create a new editor with the default embedded configuration
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
    // Set the editor content
    editor.setBody(Utils.welcomeText);
    // The editor is best viewed using a BorderLayout
    final JPanel holder = new JPanel(new BorderLayout());
    holder.add(editor.component(), BorderLayout.CENTER);
    final JButton printToConsole = new JButton("Print to console");
    // Get the content of the editor
    printToConsole.addActionListener(e -> System.out.println(editor.getBody()));
    holder.add(printToConsole, BorderLayout.SOUTH);

    final JFrame frame = new JFrame();
    frame.add(holder);
    frame.setSize(new Dimension(800, 600));
    frame.setVisible(true);
    TinyMCE.shutdownOnClose(frame);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    new DefaultImageHandling();
  }
}
