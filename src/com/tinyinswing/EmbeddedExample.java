package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class EmbeddedExample extends JFrame {

  public EmbeddedExample() throws ExecutionException, InterruptedException {
    // Create a new embedded configuration
    final Config embeddedBased = Config.embedded();
    // Create a new editor with the default configuration
    final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();
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
    new EmbeddedExample();
  }
}
