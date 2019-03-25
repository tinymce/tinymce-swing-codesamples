package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class ExternalExample {

  private ExternalExample(){}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new external configuration by adding the url of your custom TinyMCE editor (tinymce.min.js)
    // TODO: Replace the URL with the URL of your tinyMCE library
    final Config externalBased = Config.external("http://localhost/web/tinymce/tinymce.min.js");
    // Create a new editor with the default cloud configuration
    final TinyMCE editor = TinyMCE.futureEditor(externalBased).get();
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
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    TinyMCE.shutdownOnClose(frame);
    frame.setVisible(true);
  }
}
