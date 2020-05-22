package cloud.tiny.tinyinswing.examples.events;

import cloud.tiny.tinyinswing.shared.Utils;
import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import java.util.concurrent.ExecutionException;
import javax.swing.*;


public class EventsExample {

  private EventsExample() {}

  public static void main(String args[]) throws InterruptedException, ExecutionException {
    // create the editor using the javascript config.js and a event listener
    final Config config = Config.embedded()
        .setInitConf(EventsExample.class, "config.js")
        // the event listener is declared for a class which has been annotated (look at JavaLink.java to see how)
        .on(JavaLink.class, evt -> {
          // the event has 4 properties:
          // editor - a reference to tinymce if the editor has been full initialized, otherwise empty.
          // events - a interface that allows managing subscriptions to events and firing new events.
          // name - the event name in lowercase.
          // data - the event data which is an instance of the class used to subscribe to the event (ie JavaLink in this case).
          final TinyMCE editor = evt.editor.get();
          final JavaLink data = evt.data;
          // when we receive the event we show a dialog to get a URL and insert that into the editor around the selected text
          final String url = JOptionPane.showInputDialog(editor.component(), "Please input a URL.");
          editor.insertHtml("<a href=\"" + url + "\">" + data.text + "</a>" );
        });
    // create the editor
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
    // set the editor content
    editor.setHtml(Utils.lorumIpsum);
    // display the editor as the only component in a JFrame
    final JFrame frame = new JFrame("Events Example");
    frame.add(editor.component());
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    TinyMCE.shutdownOnClose(frame);
    frame.pack();
    frame.setVisible(true);
  }
}
