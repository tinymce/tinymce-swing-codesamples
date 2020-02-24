(function() {
  return {
    menubar: false,
    plugins: [
      'advlist autolink lists link image charmap print preview anchor',
      'searchreplace visualblocks code fullscreen',
      'insertdatetime media table powerpaste code help wordcount'
    ],
    toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | image | help',
    init_instance_callback: function (editor) {
      console.log('Init instance callback');
      editor.parser.addNodeFilter('img', function (nodes) {
        console.log('Parser node filter for images', nodes);
        for (var i = 0; i < nodes.length; i++) {
          var node = nodes[i];
          var src = node.attr('src');
          if (/^file:\/\//.test(src)) {
            console.log('Updating ' + src);
            node.attr({
              'src': 'afile' + src.substring(4),
              'data-mce-src': src
            });
          }
        }
      });
    }
  };
})()