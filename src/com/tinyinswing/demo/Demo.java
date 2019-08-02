package com.tinyinswing.demo;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.ActionApi;
import cloud.tiny.tinymceforswing.api.events.TinyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

public class Demo extends JFrame {

  public Demo() {
    try {
      init(this);
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void init(JFrame locationRelativeTo) throws ExecutionException, InterruptedException {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 450);

    TinyListener insertLink = new TinyListener("javalink", new ActionApi() {
      @Override
      public void run(TinyMCE tinyMCE, String s) {
        final JDialog popup = new JDialog();
        popup.setTitle("Insert link");
        popup.setSize(300, 60);
        popup.setModal(true);
        final JPanel panel = new JPanel(new FlowLayout());
        final JTextField textfield = new JTextField();
        if (!s.isEmpty()) {
          textfield.setText(s);
        }
        textfield.setPreferredSize(new Dimension(200, 24));
        panel.add(textfield);
        final JButton button = new JButton("Insert");
        button.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            popup.dispose();
          }
        });
        panel.add(button);
        popup.setContentPane(panel);
        popup.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(WindowEvent windowEvent) {
            super.windowClosed(windowEvent);
            tinyMCE.insertHtml("<a href='http://google.com'>" + textfield.getText() + "</a>");
          }
        });
        popup.setLocationRelativeTo(locationRelativeTo);
        popup.setVisible(true);
      }
    });

    final Config embeddedBased = Config.embedded().setInitConf(this.getClass(), "CustomConfiguration.js").addListener(insertLink);
    final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();

    final JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(editor.component(), BorderLayout.CENTER);
    setContentPane(contentPane);
    setVisible(true);
  }

  public static void main(String args[]) {
    new Demo();
  }
}
