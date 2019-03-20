package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class CloudExample extends JFrame {

  public CloudExample() throws ExecutionException, InterruptedException {
    // Create a new cloud configuration by adding your API key
    final Config cloudBased = Config.cloud("my API Key");
    // Create a new editor with the default cloud configuration
    final TinyMCE editor = TinyMCE.futureEditor(cloudBased).get();
    // Set the editor content
    editor.setBody(Utils.welcomeText);
    // The editor is best viewed using a BorderLayout
    JPanel holder = new JPanel(new BorderLayout());
    holder.add(editor.component(), BorderLayout.CENTER);
    JButton printToConsole = new JButton("Print to console");
    // Get the content of the editor
    printToConsole.addActionListener(e -> System.out.println(editor.getBody()));
    holder.add(printToConsole, BorderLayout.SOUTH);
    this.add(holder);
    this.setSize(new Dimension(800, 600));
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static void main(String args[]) throws ExecutionException, InterruptedException {
    new CloudExample();
  }
}
