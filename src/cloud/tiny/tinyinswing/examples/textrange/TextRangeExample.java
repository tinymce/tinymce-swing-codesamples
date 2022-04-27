package cloud.tiny.tinyinswing.examples.textrange;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.editor.TextRange;
import cloud.tiny.tinyinswing.shared.Utils;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.swing.*;


public class TextRangeExample {

  private TextRangeExample() {}

  private static void createAndShowGUI() {
    try {
      final Config config = Config.embedded().putProperty("menubar", "false")
          .putProperty("skin", "tinymce-5");
      // create both editors in parallel
      final CompletableFuture<TinyMCE> f1 = TinyMCE.futureEditor(config);
      final TinyMCE e1 = f1.get();
      e1.setHtml(Utils.sampleText);
      final JTextArea e2 = new JTextArea(10, 60);
      // create buttons to pass the editor content between the two editors
      final JButton toTheRight = new JButton(">>");
      toTheRight.addActionListener(a -> {
        e2.setText(e1.getText());
        TextRange r = e1.getTextRange();
        e2.setSelectionStart(r.start);
        e2.setSelectionEnd(r.end);
        e2.requestFocus();
      });
      final JButton toTheLeft = new JButton("<<");
      toTheLeft.addActionListener(a -> {
        e1.setText(e2.getText());
        TextRange r = TextRange.create(e2.getSelectionStart(), e2.getSelectionEnd());
        e1.setTextRange(r);
        e1.component().requestFocus();
      });
      // create a frame to put everything in
      final JFrame frame = new JFrame("Text Range Example");
      // layout the frame
      frame.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(5, 5, 5, 5);
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.CENTER;
      c.weightx = 1;
      c.weighty = 2;
      c.gridwidth = 1;
      c.gridheight = 2;
      c.gridy = 0;
      c.gridx = 0;
      frame.add(e1.component(), c);
      c.gridx = 2;
      frame.add(new JScrollPane(e2), c);
      c.fill = GridBagConstraints.NONE;
      c.weightx = 0;
      c.weighty = 1;
      c.gridheight = 1;
      c.gridx = 1;
      c.gridy = 0;
      c.anchor = GridBagConstraints.PAGE_END;
      frame.add(toTheRight, c);
      c.gridy = 1;
      c.anchor = GridBagConstraints.PAGE_START;
      frame.add(toTheLeft, c);
      // ensure that resources are cleaned up on frame close
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      TinyMCE.shutdownOnClose(frame);
      // size and show the frame
      frame.pack();
      frame.setVisible(true);
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(TextRangeExample::createAndShowGUI);
  }
}
