package cloud.tiny.tinyinswing.examples.events.basicjavatojs;

import cloud.tiny.tinymceforswing.TinyMCE;
import cloud.tiny.tinymceforswing.api.config.Config;
import cloud.tiny.tinymceforswing.api.jsdata.Codecs;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

// If you want to send events containing basic types like String, Boolean,
// Double, Integer or Long then this example shows the simplest way.
public class BasicJavaToJsEventsExample {

  private BasicJavaToJsEventsExample() {}

  private static void createAndShowGUI() {
    try {
      // create the editor using the javascript config.js and a event listener
      final Config config = Config.embedded()
          .setInitConf(BasicJavaToJsEventsExample.class, "config.js");
      // create the editor
      final TinyMCE editor = TinyMCE.futureEditor(config).get();
      // Set the editor content
      editor.setHtml("");
      // create a text area to report when we fire events in java
      final JTextArea eventLog = new JTextArea(10, 40);
      eventLog.setEditable(false);
      eventLog.setLineWrap(true);
      eventLog.setText("Events fired\n");
      // create a panel of buttons for firing events
      final Random random = new Random();
      final JPanel buttons = new JPanel(new FlowLayout());
      final JButton fireStringEvent = new JButton("Fire String Event");
      fireStringEvent.addActionListener(evt -> {
        final String value = String.format("%016x", random.nextLong());
        eventLog.append("• Firing string-type event: " + value + "\n");
        editor.fire("my-string-event", Codecs.stringCodec, value);
      });
      buttons.add(fireStringEvent);
      final JButton fireDoubleEvent = new JButton("Fire Double Event");
      fireDoubleEvent.addActionListener(evt -> {
        final double value = random.nextDouble();
        eventLog.append("• Firing double-type event: " + value + "\n");
        editor.fire("my-double-event", Codecs.doubleCodec, value);
      });
      buttons.add(fireDoubleEvent);
      final JButton fireBooleanEvent = new JButton("Fire Boolean Event");
      fireBooleanEvent.addActionListener(evt -> {
        final boolean value = random.nextBoolean();
        eventLog.append("• Firing boolean-type event: " + value + "\n");
        editor.fire("my-boolean-event", Codecs.booleanCodec, value);
      });
      buttons.add(fireBooleanEvent);
      final JButton fireIntegerEvent = new JButton("Fire Integer Event");
      fireIntegerEvent.addActionListener(evt -> {
        final int value = random.nextInt();
        eventLog.append("• Firing integer-type event: " + value + "\n");
        editor.fire("my-integer-event", Codecs.integerCodec, value);
      });
      buttons.add(fireIntegerEvent);
      final JButton fireLongEvent = new JButton("Fire Long Event");
      fireLongEvent.addActionListener(evt -> {
        // Javascript can't support the complete range of long in Java as the 
        // value must be storable in a floating point number without loss in precision.
        // The maximum supported value is 2^53-1 (9007199254740991)
        final long value = (random.nextBoolean() ? -1l : 1l) * (long)Math.floor(random.nextDouble() * Math.pow(2.0, 53.0));
        eventLog.append("• Firing long-type event: " + value + "\n");
        editor.fire("my-long-event", Codecs.longCodec, value);
      });
      buttons.add(fireLongEvent);
      // display the editor with the event buttons and messages panel
      final JFrame frame = new JFrame("Java to Javascript Events Example");
      frame.add(buttons, BorderLayout.NORTH);
      frame.add(editor.component(), BorderLayout.CENTER);
      frame.add(new JScrollPane(eventLog), BorderLayout.SOUTH);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      TinyMCE.shutdownOnClose(frame);
      frame.pack();
      frame.setVisible(true);
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    SwingUtilities.invokeLater(BasicJavaToJsEventsExample::createAndShowGUI);
  }
}
