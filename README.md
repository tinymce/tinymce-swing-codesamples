
# TinyMCE in Swing: Code samples

These code samples can be used to easily start using TinyMCE in Swing through the TinyMCES Swing Integration. Be sure to obtain a copy of the Integration to start using TinyMCE as your new Rich Text Editor.

## Getting Started

The following steps will guide you as you deploy the Tiny Swing Integration in your project and start using TinyMCE as your Rich Text Editor in Swing.

### Prerequisites

Get a copy of the TinyMCE-Swing Integration

### Installing

Import all the contents of the TinyMCE-Swing zip file as Java libraries

## Understanding these samples

These code samples showcase how to easily create and configure an editor in a couple of lines.

In order to create an editor you have to pass a configuration object with the editor details.

```java
// Create a new cloud configuration by adding your API key
final Config cloudBased = Config.cloud("<my-api-key>");
// Create a new editor with the default cloud configuration
final TinyMCE editor = TinyMCE.futureEditor(cloudBased).get();
```
The configuration will specify where the TinyMCE editor will be loaded from. There are three options:
-Cloud deployment - The editor is served by the Tiny CDN
-Embedded deployment - The editor is served by an embedded webserver
-External deployment - The editor is served externally by the user

### Cloud deployment

Cloud deployments download the editor code from the Tiny CDN. They require an API key and you can specify the channel of your deployment. The default channel is `stable`.

```java
final Config config = Config.cloud("<my-api-key>");
```

### Embedded deployment

Embedded deployments will run an embedded Jetty server from which to provide the editor code. You don't have to provide any additional details as the Integration code comes with a prepackaged version of the editor.

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
tinyMCE.setBody("Hello World");
```

or

```java
final CompletableFuture<TinyMCE> futureEditor = TinyMCE.futureEditor(config);
futureEditor.thenAccept(new Consumer<TinyMCE>() {
  @Override
  public void accept(TinyMCE tinyMCE) {
    tinyMCE.setBody("Hello World");
  }
});
```

## Extending your editor

The TinyMCE editor can be customized via the configuration object to better suit particular use-cases.

### Custom configurations

The TinyMCE editor takes a Javascript object where each of its properties is a configuration option. The Integration allows users to programmatically provide a configuration or extend a configuration via Java.

### JavaScript Configuration

You can provide a configuration as a Java String containing JavaScript code that, when eval'd will return a JavaScript object.

```java
final String configString = "{menubar: false, plugins: 'advlist autolink lists link image charmap print preview anchor', toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help'}";
final Config config = Config.cloud("my API Key").setInitConf(configString);
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```

### Java Configuration

Alternatively to providing a JavaScript configuration, a user may simply provide a Map of properties as a configuration.

```java
HashMap<String, String> properties = new HashMap<>();
properties.put("menubar", "false");
properties.put("plugins", "advlist autolink lists link image anchor textcolor searchreplace visualblocks media table paste help wordcount");
properties.put("toolbar", "undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help");
final Config config = Config.cloud("my API Key").putProperties(properties);
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```

Plugins can also be selected by passing a List of plugins to be used by the editor.

```java
List<String> plugins = new ArrayList<>();
plugins.add("advlist");
plugins.add("lists");
// And so on...
final Config config = Config.cloud("my API Key").setPlugins(plugins);
final TinyMCE editor = TinyMCE.futureEditor(config).get();
```