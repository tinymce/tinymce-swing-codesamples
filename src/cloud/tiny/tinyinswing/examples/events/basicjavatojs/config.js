(function() {
  return {
    plugins: 'code',
    toolbar: 'undo redo | blocks | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
    // On setup define our listeners to record events based on basic types
    setup(editor) {
      editor.on('my-string-event', (evt) => {
        // we show a message in the editor every time we receive the event
        editor.insertContent('<p>Received <b>my-string-event</b>: &quot;' + evt.value + '&quot;</p>');
      });
      editor.on('my-double-event', (evt) => {
        // we show a message in the editor every time we receive the event
        editor.insertContent('<p>Received <b>my-double-event</b>: ' + evt.value + '</p>');
      });
      editor.on('my-boolean-event', (evt) => {
        // we show a message in the editor every time we receive the event
        editor.insertContent('<p>Received <b>my-boolean-event</b>: ' + evt.value + '</p>');
      });
      editor.on('my-integer-event', (evt) => {
        // we show a message in the editor every time we receive the event
        editor.insertContent('<p>Received <b>my-integer-event</b>: ' + evt.value + '</p>');
      });
      editor.on('my-long-event', (evt) => {
        // we show a message in the editor every time we receive the event
        editor.insertContent('<p>Received <b>my-long-event</b>: ' + evt.value + '</p>');
      });
    }
  };
})()