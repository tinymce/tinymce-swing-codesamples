package cloud.tiny.tinyinswing.examples.events.basicjstojava;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.jsdata.Codecs;
import java.awt.BorderLayout;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

// If you want to send events containing basic types like String, Boolean,
// Double, Integer or Long then this example shows the simplest way.
public class BasicJsToJavaEventsExample {

  private BasicJsToJavaEventsExample() {}

  public static void main(String args[]) throws InterruptedException, ExecutionException {
    // create the editor using the javascript config.js and a event listener
    final Config config = Config.embedded()
        .setInitConf(BasicJsToJavaEventsExample.class, "config.js");
    // create the editor
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
    // Set the editor content
    editor.setHtml("");
    // create a text area to report when we receive events in Java
    final JTextArea eventLog = new JTextArea(10, 40);
    eventLog.setEditable(false);
    eventLog.setLineWrap(true);
    eventLog.setText("Events Received\n");
    // display the editor with the event messages panel
    final JFrame frame = new JFrame("Javascript to Java Events Example");
    frame.add(editor.component(), BorderLayout.CENTER);
    frame.add(new JScrollPane(eventLog), BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    TinyMCE.shutdownOnClose(frame);
    frame.pack();
    frame.setVisible(true);
    // add some event listeners
    editor.on("my-string-event", Codecs.stringCodec, evt -> {
      eventLog.append("• Received my-string-event: \"" + evt.data + "\"\n");
    });
    editor.on("my-double-event", Codecs.doubleCodec, evt -> {
      eventLog.append("• Received my-double-event: " + evt.data + "\n");
    });
    editor.on("my-boolean-event", Codecs.booleanCodec, evt -> {
      eventLog.append("• Received my-boolean-event: " + evt.data + "\n");
    });
    editor.on("my-integer-event", Codecs.integerCodec, evt -> {
      eventLog.append("• Received my-integer-event: " + evt.data + "\n");
    });
    editor.on("my-long-event", Codecs.longCodec, evt -> {
      eventLog.append("• Received my-long-event: " + evt.data + "\n");
    });
  }
}
