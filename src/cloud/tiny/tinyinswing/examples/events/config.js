(function() {
  return {
    plugins: 'advlist autolink lists link code',
    toolbar: 'undo redo code | insertJavaLink',
    // On setup define our button to call a java function.
    // window.java.trigger takes two arguments: the name of the trigger, and a string
    // trigger must be called with both parameters
    setup(editor) {
      editor.ui.registry.addButton('insertJavaLink', {
        icon: 'link',
        onAction: () => window.java.trigger('javalink', editor.selection.getContent({format : "text"}))
      });
    }
  };
})()