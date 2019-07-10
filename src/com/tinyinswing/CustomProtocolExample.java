package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomProtocolHandler;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomURLRequest;
import cloud.tiny.tinymceforswing.api.config.wrappers.CustomURLResponse;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class CustomProtocolExample {

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new embedded configuration
    final Config embeddedBased = Config.embedded().addPlugin("link").addPlugin("print").addPlugin("powerpaste").addProtocolHandler("localimg", new CustomProtocolHandler() {
      @Override
      public CustomURLResponse onRequest(CustomURLRequest customURLRequest) {
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
    // Create a new editor with the default configuration
    final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();
    // Set the editor content
    editor.setBody("<p><img src='localimg://my_image.png'></p>" +
        "<p><img src='localimg://not_an_image.txt'></p>");
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
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
