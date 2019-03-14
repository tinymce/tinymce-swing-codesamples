package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.config.Config;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SimpleConfigurationEditor extends JFrame {

  public SimpleConfigurationEditor() throws ExecutionException, InterruptedException {
    // Create a new cloud configuration by adding your API key and setting the initial configuration
    final Config config = Config.cloud("my API Key").setInitConf(getConfiguration());
    // Create a new editor with the default cloud configuration
    final CompletableFuture<TinyMCE> futureEditor = TinyMCE.futureEditor(config);
    futureEditor.thenAccept(new Consumer<TinyMCE>() {
      @Override
      public void accept(TinyMCE tinyMCE) {
        tinyMCE.setBody("Hello World");
      }
    });
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
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

  public String getConfiguration() {
    final InputStream s = this.getClass().getResourceAsStream("config.js");
    if(s == null) throw new RuntimeException("Could not read resource");
    return new BufferedReader(new InputStreamReader(s)).lines().collect(Collectors.joining(System.lineSeparator()));
  }

  public static void main(String args[]) throws ExecutionException, InterruptedException {
    new SimpleConfigurationEditor();
  }
}
