package cloud.tiny.tinyinswing.examples.dialog;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class DialogExample {

  private DialogExample() {}

  private static void openDialog(final TinyMCE editor, final JEditorPane pane, final JButton edit) {
    editor.setHtml(pane.getText());
    final JDialog dialog = new JDialog();
    dialog.setTitle("Edit content");
    final JButton ok = new JButton("Ok");
    ok.addActionListener(a -> {
      pane.setText(editor.getHtml());
      dialog.dispose();
    });
    final JButton apply = new JButton("Apply");
    apply.addActionListener(a -> {
      pane.setText(editor.getHtml());
    });
    final JButton cancel = new JButton("Cancel");
    cancel.addActionListener(a -> {
      dialog.dispose();
    });
    final JPanel controls = new JPanel(new FlowLayout());
    controls.add(cancel);
    controls.add(apply);
    controls.add(ok);
    dialog.add(editor.component(), BorderLayout.CENTER);
    dialog.add(controls, BorderLayout.SOUTH);
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent evt) {
        editor.dispose();
        edit.setEnabled(true);
      }
    });
    dialog.pack();
    dialog.setVisible(true);
  }

  private static void displayDialog(final JEditorPane pane, final JButton edit) {
    final Config config = Config.embedded();
    TinyMCE.futureEditor(config).thenAccept(editor -> openDialog(editor, pane, edit));
  }

  private static void createAndShowGUI() {
    final JEditorPane editable = new JEditorPane();
    editable.setContentType("text/html");
    editable.setText(Utils.sampleText);
    editable.setEditable(false);
    final JButton edit = new JButton("Edit");
    edit.addActionListener(a -> {
      edit.setEnabled(false);
      displayDialog(editable, edit);
    });
    final JPanel panel = new JPanel(new BorderLayout());
    panel.add(editable, BorderLayout.CENTER);
    final JPanel controls = new JPanel(new FlowLayout());
    controls.add(edit);
    panel.add(controls, BorderLayout.SOUTH);

    final JFrame frame = new JFrame("Dialog Example");
    frame.add(panel);
    frame.add(panel);
    frame.setSize(new Dimension(800, 600));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    TinyMCE.shutdownOnClose(frame);
    frame.setVisible(true);
  }

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(DialogExample::createAndShowGUI);
  }
}
