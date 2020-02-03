package cloud.tiny.tinyinswing.examples.multipleeditors;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MultipleEditorsExample {

  private MultipleEditorsExample() {}

  public static void main(final String[] args) throws ExecutionException, InterruptedException {
    final Config config = Config.embedded().putProperty("menubar", "false");
    // create both editors in parallel
    final CompletableFuture<TinyMCE> f1 = TinyMCE.futureEditor(config);
    final CompletableFuture<TinyMCE> f2 = TinyMCE.futureEditor(config);
    final TinyMCE e1 = f1.get();
    final TinyMCE e2 = f2.get();
    // create buttons to pass the editor content between the two editors
    final JButton toTheRight = new JButton(">>");
    toTheRight.addActionListener(a -> {
      e2.setHtml(e1.getHtml());
    });
    final JButton toTheLeft = new JButton("<<");
    toTheLeft.addActionListener(a -> {
      e1.setHtml(e2.getHtml());
    });
    // create a frame to put everything in
    final JFrame frame = new JFrame("Multiple Editors Example");
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
    frame.add(e2.component(), c);
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
  }
}
