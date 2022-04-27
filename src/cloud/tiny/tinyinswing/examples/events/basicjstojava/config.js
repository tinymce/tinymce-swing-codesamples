(function() {
  return {
    plugins: 'code',
    skin: 'tinymce-5',
    toolbar: 'StringEvent DoubleEvent BooleanEvent | IntegerEvent LongEvent',
    // On setup define our buttons to fire events based on basic types
    setup(editor) {
      // String, Boolean and Double have a 1 to 1 correspondence with the Java type so they will always succeed
      editor.ui.registry.addButton('StringEvent', {
        text: 'Fire String Event',
        onAction: () => {
          // generate a random 16 character hexadecimal string
          const value = Array.from(crypto.getRandomValues(new Uint8Array(8)), (dec) => ('0' + dec.toString(16)).substr(-2)).join('');
          editor.insertContent('<p>Firing string event: &quot;' + value + '&quot;</p>');
          // note how the string must be wrapped in a Javascript object as
          // the "value" property - this is because events only accept objects
          editor.fire('my-string-event', { value });
        }
      });
      editor.ui.registry.addButton('DoubleEvent', {
        text: 'Fire Double Event',
        onAction: () => {
          // generate a random double between 0 (inclusive) and 1 (exclusive)
          const value = Math.random();
          editor.insertContent('<p>Firing double event: ' + value + '</p>');
          // note how the double must be wrapped in a Javascript object as
          // the "value" property - this is because events only accept objects
          editor.fire('my-double-event', { value });
        }
      });
      editor.ui.registry.addButton('BooleanEvent', {
        text: 'Fire Boolean Event',
        onAction: () => {
          // generate a random boolean
          const value = Math.random() > 0.5;
          editor.insertContent('<p>Firing boolean event: ' + value + '</p>');
          // note how the boolean must be wrapped in a Javascript object as
          // the "value" property - this is because events only accept objects
          editor.fire('my-boolean-event', { value });
        }
      });
      // the integer types have some restrictions to allow 2-way data transfer
      editor.ui.registry.addButton('IntegerEvent', {
        text: 'Fire Integer Event',
        onAction: () => {
          // Integer must be a whole number within the range of the Java integer, 
          // so a minimum of 2^31 (-2,147,483,648) and a maximum of 2^31-1 (+2,147,483,647)
          const value = Math.floor(Math.random() * Math.pow(2, 32)) - Math.pow(2, 31);
          editor.insertContent('<p>Firing integer event: ' + value + '</p>');
          // note how the integer must be wrapped in a Javascript object as
          // the "value" property - this is because events only accept objects
          editor.fire('my-integer-event', { value });
        }
      });
      editor.ui.registry.addButton('LongEvent', {
        text: 'Fire Long-Integer Event',
        onAction: () => {
          // Long must be a whole number within the accurate range of Javascript's number type,
          // so a minimum of -2^53-1 (-9007199254740991) and a maximum of 2^53-1 (9007199254740991)
          // there is a javascript constant for this: Number.MAX_SAFE_INTEGER
          const value = (Math.random() < 0.5 ? -1 : 1) * Math.floor(Math.random() * (Number.MAX_SAFE_INTEGER + 1));
          editor.insertContent('<p>Firing long event: ' + value + '</p>');
          // note how the long must be wrapped in a Javascript object as
          // the "value" property - this is because events only accept objects
          editor.fire('my-long-event', { value });
        }
      });
    }
  };
})()