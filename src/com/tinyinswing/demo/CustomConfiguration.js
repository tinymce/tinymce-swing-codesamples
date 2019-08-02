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
    expand_cmd_patterns: [
      { text: 'test', replace: 'This is a test paragraph.'},
      { text: 'tolist', cmd: 'InsertUnorderedList'},
      { text: 'table', replace: '<table><tr><td>Look Im a table!</td></tr></table>'},
      { text: 'now', cmd: 'mceInsertTime'}
    ],
    expand_cmd_default(editor, word) {
      editor.selection.getRng().deleteContents();
      window.java.trigger('javalink', word);
    },
    init_instance_callback(editor) {
      editor.setContent("Initial content from callback");
    },
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