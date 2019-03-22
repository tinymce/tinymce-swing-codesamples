package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MultipleEditors {

  public MultipleEditors() throws ExecutionException, InterruptedException {
    final Config config = Config.cloud("my API Key").putProperty("menubar", "false");
    final CompletableFuture<TinyMCE> f1 = TinyMCE.futureEditor(config);
    final CompletableFuture<TinyMCE> f2 = TinyMCE.futureEditor(config);
    TinyMCE e1 = f1.get();
    TinyMCE e2 = f2.get();
    JFrame frame = new JFrame();
    JPanel holder = new JPanel(new FlowLayout());
    JPanel h1 = new JPanel(new BorderLayout());
    JPanel h2 = new JPanel(new BorderLayout());
    h1.setPreferredSize(new Dimension(400, 400));
    h2.setPreferredSize(new Dimension(400, 400));
    h1.add(e1.component(), BorderLayout.CENTER);
    h2.add(e2.component(), BorderLayout.CENTER);

    JButton toTheRight = new JButton(">>");
    toTheRight.addActionListener(a -> {
      e2.setBody(e1.getBody());
    });
    JButton toTheLeft = new JButton("<<");
    toTheLeft.addActionListener(a -> {
      e1.setBody(e2.getBody());
    });
    JPanel controls = new JPanel(new BorderLayout());
    // Add the buttons
    controls.add(toTheRight, BorderLayout.NORTH);
    controls.add(toTheLeft, BorderLayout.SOUTH);
    // Add the components
    holder.add(h1); // First editor
    holder.add(controls); // Buttons
    holder.add(h2); // Second editor
    frame.add(holder);
    frame.setSize(900, 440);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public static void main(String args[]) throws ExecutionException, InterruptedException {
    new MultipleEditors();
  }
}
