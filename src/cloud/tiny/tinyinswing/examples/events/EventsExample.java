package cloud.tiny.tinyinswing.examples.events;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.config.wrappers.ActionApi;
import cloud.tiny.tinymceforswing.api.events.TinyListener;
import cloud.tiny.tinyinswing.shared.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class EventsExample {

  private EventsExample() {}

  public static void main(String args[]) throws InterruptedException, ExecutionException {
    // Creating a TinyListener
    // TinyListeners allows user to execute Java code when the editor calls the window.java.trigger function
    // In this case, we will open a modal dialog to ask the user for a URL which we will insert in our content
    // TinyListener take two arguments: the name of the trigger and an ActionApi implementation
    TinyListener insertLink = new TinyListener("javalink", new ActionApi() {
      // Our ActionApi implementation takes an instance of the editor and a 
      // string sent when the trigger was called, in this case the selected text.
      @Override
      public void run(TinyMCE tinymce, String value) {
        String url = JOptionPane.showInputDialog(tinymce.component(), "Please input a URL.");
        tinymce.insertHtml("<a href=\"" + url + "\">" + value + "</a>" );
      }
    });
    // create the editor using the javascript config.js and a event listener
    final Config config = Config.embedded()
        .setInitConf(EventsExample.class, "config.js")
        .addListener(insertLink);
    final TinyMCE editor = TinyMCE.futureEditor(config).get();
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
