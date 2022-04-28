(function() {
  return {
    plugins: 'advlist autolink lists link code',
    toolbar: 'undo redo code | insertJavaLink',
    // On setup define our button to fire a JavaLink event
    setup(editor) {
      editor.ui.registry.addButton('insertJavaLink', {
        icon: 'link',
        onAction: () => editor.fire('JavaLink', { text: editor.selection.getContent({ format : "text" }) })
      });
    }
  };
})()