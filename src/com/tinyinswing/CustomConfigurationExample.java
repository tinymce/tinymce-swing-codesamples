package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public final class CustomConfigurationExample {

  private CustomConfigurationExample(){}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    // This map will hold the editor properties
    final HashMap<String, String> editorProperties = new HashMap<>();
    editorProperties.put("menubar", "false");
    editorProperties.put("plugins", "advlist autolink lists link image charmap print preview anchor textcolor searchreplace visualblocks code insertdatetime media table paste powerpaste code help wordcount");
    editorProperties.put("toolbar", "undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help");
    // Create a new cloud configuration by adding your API key and setting the editor properties
    final Config cloudBased = Config.cloud("my API Key").putProperties(editorProperties);
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
