package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;

public class DialogExample extends JFrame {

  public DialogExample() {
    //
    JEditorPane editable = new JEditorPane();
    editable.setContentType("text/html");
    editable.setText(Utils.sampleText);
    editable.setEditable(false);
    JButton edit = new JButton("Edit");
    edit.addActionListener(a -> {
      DialogEditor.displayDialog(editable, edit);
      edit.setEnabled(false);
    });
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(editable, BorderLayout.CENTER);
    JPanel controls = new JPanel(new FlowLayout());
    controls.add(edit);
    panel.add(controls, BorderLayout.SOUTH);
    this.add(panel);
    this.add(panel);
    this.setSize(new Dimension(800, 600));
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static void main(String args[]) {
    new DialogExample();
  }
}

class DialogEditor extends JDialog {

  public DialogEditor(TinyMCE editor, JEditorPane pane) {
    //
    editor.setBody(pane.getText());
    JButton save = new JButton("Save");
    save.addActionListener(a -> {
      pane.setText(editor.getBody());
    });
    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(a -> {
      this.dispose();
    });
    JPanel holder = new JPanel(new BorderLayout());
    JPanel controls = new JPanel(new FlowLayout());
    controls.add(cancel);
    controls.add(save);
    holder.add(editor.component(), BorderLayout.CENTER);
    holder.add(controls, BorderLayout.SOUTH);
    this.add(holder);
    this.setSize(800, 600);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  public static void displayDialog(JEditorPane pane, JButton edit) {
    final Config config = Config.cloud("my API Key");
    TinyMCE.futureEditor(config).thenAccept(editor -> {
      new DialogEditor(editor, pane);
      edit.setEnabled(true);
    });
  }
}
