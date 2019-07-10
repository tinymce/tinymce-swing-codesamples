package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Cloud;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public final class CloudExample {

  private CloudExample() {}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new cloud configuration by adding your API key
    final Config cloudBased = Config.cloud("<my_api_key>", "5-testing")
      .setPlugins(Arrays.asList("link", "print", "tinymcespellchecker", "media", "powerpaste"));
    // Create a new editor with the default cloud configuration
    final TinyMCE editor = TinyMCE.futureEditor(cloudBased).get();
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
