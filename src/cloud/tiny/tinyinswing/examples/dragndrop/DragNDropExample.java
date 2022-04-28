package cloud.tiny.tinyinswing.examples.dragndrop;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.concurrent.ExecutionException;

public class DragNDropExample {

  private static class HtmlSelection implements Transferable {
    private final String html;

    public HtmlSelection(final String html) {
      this.html = html;
    }

    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { DataFlavor.selectionHtmlFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
      return DataFlavor.selectionHtmlFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
      if (DataFlavor.selectionHtmlFlavor.equals(flavor)) {
        return html;
      }
      throw new UnsupportedFlavorException(flavor);
    }
  }

  private static class HtmlTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1l;

    public int getSourceActions(JComponent c) {
      return TransferHandler.COPY;
    }

    protected Transferable createTransferable(JComponent c) {
      return new HtmlSelection(((JTextComponent)c).getSelectedText());
    }
  }

  private static void createAndShowGUI() {
    try {
      // Create a new embedded configuration
      final Config embeddedBased = Config.embedded()
          .putProperty("powerpaste_html_import", "merge");
      // Create a new editor with the default configuration
      final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();
      // Set the editor content
      editor.setHtml(Utils.sampleText);
      // Create a JTextArea which we can drag HTML from
      final JTextArea source = new JTextArea(Utils.sampleText, 50, 70);
      source.setLineWrap(true);
      source.setDragEnabled(true);
      source.setTransferHandler(new HtmlTransferHandler());
      final JScrollPane sourceScroll = new JScrollPane(source);
      // Create a button to get the content of the editor
      final JButton printToConsole = new JButton("Print to console");
      printToConsole.addActionListener(e -> System.out.println(editor.getHtml()));
      // Add the editor and button to the JFrame
      final JFrame frame = new JFrame("Drag from a Java component into the editor");
      frame.add(sourceScroll, BorderLayout.WEST);
      frame.add(editor.component(), BorderLayout.EAST);
      frame.add(printToConsole, BorderLayout.SOUTH);
      frame.pack();
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      // ensure the editor resources are cleaned up when the window is closed
      TinyMCE.shutdownOnClose(frame);
      // display the editor
      frame.setVisible(true);
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(DragNDropExample::createAndShowGUI);
  }
}