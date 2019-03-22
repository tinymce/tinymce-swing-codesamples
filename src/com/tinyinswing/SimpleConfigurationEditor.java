package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class SimpleConfigurationEditor {

  private SimpleConfigurationEditor() {}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // Create a new cloud configuration by adding your API key and setting the initial configuration
    final Config config = Config.cloud("my API Key").setInitConf(SimpleConfigurationEditor.class, "config.js");
    // Create a new editor with the default cloud configuration
    final CompletableFuture<TinyMCE> futureEditor = TinyMCE.futureEditor(config);
    futureEditor.thenAccept(new Consumer<TinyMCE>() {
      @Override
      public void accept(final TinyMCE tinyMCE) {
        tinyMCE.setBody("Hello World");
      }
    });
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
}
