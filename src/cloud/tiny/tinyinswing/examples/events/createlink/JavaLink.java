package cloud.tiny.tinyinswing.examples.events.createlink;

import cloud.tiny.tinymceforswing.api.event.Event;
import cloud.tiny.tinymceforswing.api.jsdata.JsObj;
import cloud.tiny.tinymceforswing.api.jsdata.Prop;

// Events must be annotated with cloud.tiny.tinymceforswing.api.event.Event
// They may specify a name in the annotation but otherwise the class name is used as the event name.
@Event public class JavaLink {
  // Properties that are part of the Javascript object must be annotated with cloud.tiny.tinymceforswing.api.jsdata.Prop
  // They may specify 2 optional values:
  //   key - the name of the property on the javascript object, if not specified the field name is used.
  //   pos - the position in the arguments of the constructor annotated with JsObj, if not specified the declaration order is used.
  @Prop public final String text;

  // A constructor that is annotated with cloud.tiny.tinymceforswing.api.jsdata.JsObj must exist to produce the event object from Javascript.
  // The constructor must have exactly the same number of arguments as fields annotated with Prop
  // The types of the arguments must match the fields annotated with Prop and be in the same order as they are declared (or in the order specified by their pos() attribute).
  @JsObj public JavaLink(final String text) {
    this.text = text;
  }
}