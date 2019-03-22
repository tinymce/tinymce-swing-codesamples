package com.tinyinswing;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;

public final class DialogExample {

  private DialogExample() {}

  public static void main(final String[] args) {

    final JEditorPane editable = new JEditorPane();
    editable.setContentType("text/html");
    editable.setText(Utils.sampleText);
    editable.setEditable(false);
    final JButton edit = new JButton("Edit");
    edit.addActionListener(a -> {
      DialogEditor.displayDialog(editable, edit);
      edit.setEnabled(false);
    });
    final JPanel panel = new JPanel(new BorderLayout());
    panel.add(editable, BorderLayout.CENTER);
    final JPanel controls = new JPanel(new FlowLayout());
    controls.add(edit);
    panel.add(controls, BorderLayout.SOUTH);

    final JFrame frame = new JFrame();
    frame.add(panel);
    frame.add(panel);
    frame.setSize(new Dimension(800, 600));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    TinyMCE.shutdownOnClose(frame);
    frame.setVisible(true);
  }


}

class DialogEditor extends JDialog {

  public DialogEditor(final TinyMCE editor, final JEditorPane pane) {
    //
    editor.setBody(pane.getText());
    final JButton save = new JButton("Save");
    save.addActionListener(a -> {
      pane.setText(editor.getBody());
    });
    final JButton cancel = new JButton("Cancel");
    cancel.addActionListener(a -> {
      this.dispose();
    });
    final JPanel holder = new JPanel(new BorderLayout());
    final JPanel controls = new JPanel(new FlowLayout());
    controls.add(cancel);
    controls.add(save);
    holder.add(editor.component(), BorderLayout.CENTER);
    holder.add(controls, BorderLayout.SOUTH);
    this.add(holder);
    this.setSize(800, 600);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  public static void displayDialog(final JEditorPane pane, final JButton edit) {
    final Config config = Config.cloud("my API Key");
    TinyMCE.futureEditor(config).thenAccept(editor -> {
      new DialogEditor(editor, pane);
      edit.setEnabled(true);
    });
  }
}
