
# TinyMCE in Swing: Code samples

These code samples can be used to easily start using TinyMCE in Swing through the TinyMCE Swing Integration.
Be sure to obtain a copy of the Integration to start using TinyMCE as your new Rich Text Editor.

## Getting Started

The following steps will guide you as you deploy the Tiny Swing Integration in your project and start using TinyMCE as your Rich Text Editor in Swing.

### Prerequisites

Get a copy of the TinyMCE-Swing Integration by contacting our [support team](https://support.tiny.cloud/).

### Installing

Put the `lib` directory of the TinyMCE Swing zip file into the top directory of this repository.

Also ensure that [Apache Ant](https://ant.apache.org/) is installed.

### Running examples

See what examples are available by running `ant -projecthelp`
```text
12:33 $ ant -projecthelp
Buildfile: <...path...>/tinymce-swing-codesamples/build.xml

    Build and run code samples for "TinyMCE for Swing".
  
Main targets:

 clean                     Clean up.
 compile                   Compile the source.
 example-basic-cloud       Run the basic cloud example (requires setting API key).
 example-basic-embedded    Run the basic embedded example.
 example-basic-external    Run the basic external example (requires hosting TinyMCE).
 example-config-js         Run the example of configuring TinyMCE from a Javascript file.
 example-config-props      Run the example of configuring TinyMCE directly in Java.
 example-custom-protocol   Run the example of configuring a custom protocol handler.
 example-dialog            Run the example of accessing the editor from a dialog.
 example-events            Run the events example.
 example-image-handling    Run the example of a simple image upload handler.
 example-multiple-editors  Run the example of multiple editors.
Default target: example-basic-embedded
 ```

 Running an example simply requires specifying `ant <target>` where `<target>` is one of the `example-*` targets.
 
 For example:
 ```
 ant example-basic-embedded
 ```

## Understanding these samples

These code samples showcase how to easily create and configure an editor in a couple of lines.

In order to create an editor you have to pass a configuration object with the editor details.

```java
// Create a embedded configuration
final Config embeddedBased = Config.embedded();
// Create a new editor with the default embedded configuration
final TinyMCE editor = TinyMCE.futureEditor(embeddedBased).get();
```
The configuration will specify where the TinyMCE editor will be loaded from. There are three options:

- Cloud deployment - The editor is served by the Tiny CDN.
- Embedded deployment - The editor is served by a custom protocol handler from the Jar.
- External deployment - The editor is served externally by the user.

### Cloud deployment

Cloud deployments download the editor code from the Tiny CDN. They require an API key and you can specify the channel of your deployment. The default channel is `stable`.

```java
final Config config = Config.cloud("<my-api-key>");
```

### Embedded deployment

Embedded deployments will use a custom protocol handler to serve the editor directly from the Jar. You don't have to provide any additional details as the Integration code comes with a prepackaged version of the editor.

```java
final Config config = Config.embedded();
```

### External deployment

External deployments rely on the editor being available from an external source. It can be a local copy or a custom deployment available to the users.

```java
final Config config = Config.external("https://myserver/tiny/tinymce.min.js");
```

### Getting the editor in Java

The editor loads asynchronously so getting a new instance of the editor will return a CompletableFuture that will be resolved when the editor is ready.

```java
final TinyMCE tinyMCE = TinyMCE.futureEditor(config).get();
tinyMCE.setHtml("<p>Hello World</p>");
```

or

```java
final CompletableFuture<TinyMCE> futureEditor = TinyMCE.futureEditor(config);
futureEditor.thenAccept(new Consumer<TinyMCE>() {
  @Override
  public void accept(TinyMCE tinyMCE) {
    tinyMCE.setHtml("<p>Hello World</p>");
  }
});
```

## Extending your editor

The TinyMCE editor can be customized via the configuration object to better suit particular use-cases.

The TinyMCE editor takes a Javascript object where each of its properties is a configuration option. The Integration allows users to programmatically provide a configuration or extend a configuration via Java.

### JavaScript Configuration

You can provide a configuration as a Java String containing JavaScript code that, when eval'd will return a JavaScript object.

```java
final String configString = "(function () { return { menubar: false, plugins: 'advlist autolink lists link image charmap print preview anchor', toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help' }; })()";
final Config config = Config.embedded().setInitConf(configString);
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```

### Java Configuration

Alternatively to providing a JavaScript configuration, a user may simply provide properties to the configuration.

```java
final Config config = Config.embedded()
    .putProperty("menubar", "false")
    .putProperty("plugins", "advlist autolink lists link image anchor textcolor searchreplace visualblocks media table paste help wordcount")
    .putProperty("toolbar", "undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help");
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```

Plugins can also be selected by adding plugins to be used by the editor.

```java
final Config config = Config.embedded().addPlugin("advlist").addPlugin("lists");
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```