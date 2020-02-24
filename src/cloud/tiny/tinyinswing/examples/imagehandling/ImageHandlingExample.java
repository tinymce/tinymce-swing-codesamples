package cloud.tiny.tinyinswing.examples.imagehandling;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.*;

import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.nio.file.*;
import java.io.*;

public class ImageHandlingExample {

  private ImageHandlingExample() {}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {

    // Create a protocol handler for "afile" instead of "file" (as chrome won't report file) and use Javascript to rewrite the URLs (see config.js)
    TinyMCE.addProtocolHandler("afile", new CustomProtocolHandler() {
      @Override
      public CustomURLResponse onRequest(CustomURLRequest request) {
        String strPath = request.getUrl().substring(8);
        System.out.println("Requesting " + strPath);
        try {
          Path path = Paths.get(strPath);
          byte[] bytes = Files.readAllBytes(path);
          return new CustomURLResponse(bytes, 200);
        } catch (IOException e) {
          e.printStackTrace();
          // If we did not find the image, we return a 404
          return new CustomURLResponse("".getBytes(), 404);
        } catch (SecurityException e) {
          e.printStackTrace();
          // If we failed to access the image, we return a 404
          return new CustomURLResponse("".getBytes(), 404);
        }
      }
    });


    // Make a map to store images in base64
    final HashMap<String, String> imageCache = new HashMap<>();

    /* Set up our custom protocol handler
    You can create a custom protocol handler that will return a custom response to request using
    the specified protocol. In this case, everything going to localimg://* will be handled here
    */
    TinyMCE.addProtocolHandler("localimg", new CustomProtocolHandler() {
      @Override
      public CustomURLResponse onRequest(CustomURLRequest request) {
        System.out.println("Requesting " + request.getUrl());
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

    final Config config = Config.embedded().enableDebugging(9222)
      .setInitConf(ImageHandlingExample.class, "config.js")
      /* Set up an Image Upload Handler
      You must provide an imageUploadHandler that will take a TinyBlob object and return
      an Optional<String> of the new path of the stored image if stored successfully or
      an empty Option if the storing failed
        */
      .setImageUploadHandler(new ImageUploadHandler() {
        @Override
        public Optional<String> processImage(TinyBlob blob) {
          System.out.println("Processing uploaded image " + blob.getName());
          // After receiving the image blob we store the name and the base64 value in our map
          imageCache.put(blob.getName(), blob.getBase64());
          // And return the path of where the image is stored
          // We will use custom protocols to provide access to our image at localimg://<name of the image>
          return Optional.of("localimg://" + blob.getName());
        }
      });
    // Create a new editor with the above configuration
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
    
    // Set the editor content, update this URL to point at a local file
    editor.setHtml("<img src=\"file:///home/james/images/baboon.png\">");
    // Create a button to get the content of the editor
    final JButton printToConsole = new JButton("Debug");
    printToConsole.addActionListener(e -> editor.debug().main().showDebugger());
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
