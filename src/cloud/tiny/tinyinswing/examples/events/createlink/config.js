(function() {
  return {
    plugins: 'advlist autolink lists link code',
    toolbar: 'undo redo code | insertJavaLink',
    skin: 'tinymce-5',
    // On setup define our button to fire a JavaLink event
    setup(editor) {
      editor.ui.registry.addButton('insertJavaLink', {
        icon: 'link',
        onAction: () => editor.fire('JavaLink', { text: editor.selection.getContent({ format : "text" }) })
      });
    }
  };
})()