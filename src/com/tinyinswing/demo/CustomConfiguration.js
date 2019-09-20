(function() {
  return {
    link_list: [
      {title:'page 1', value: 'http://google.com'},
      {title:'tiny', value: 'http://tinymce.com'}
    ],
    height: 400,
    width: 200,
    selector: '#mytextarea',
    plugins: ['searchreplace wordcount code media emoticons help expand_cmd'],
    toolbar: 'undo redo | link image | forecolor backcolor | insertJavaLink',
    // Expand_cmd plugin patterns for replacement
    expand_cmd_patterns: [
      { text: 'test', replace: 'This is a test paragraph.'},
      { text: 'tolist', cmd: 'InsertUnorderedList'},
      { text: 'table', replace: '<table><tr><td>Look Im a table!</td></tr></table>'},
      { text: 'now', cmd: 'mceInsertTime'}
    ],
    // Default behaviour triggers when the command is executed on an unmatched pattern
    // In this case, it will call the trigger 'javalink' for execution on the java side
    expand_cmd_default(editor, word) {
      // We delete the selected word
      editor.selection.getRng().deleteContents();
      // And we execute a 'javalink' on the word
      window.java.trigger('javalink', word);
    },
    // On setup define our button to call a java function.
    // window.java.trigger takes two arguments: the name of the trigger, and a string
    // trigger must be called with both parameters
    setup(editor) {
      editor.ui.registry.addButton('insertJavaLink', {
        text: 'Java Link',
        onAction: () => {
          window.java.trigger('javalink', '');
        }
      });
    }
  };
})()