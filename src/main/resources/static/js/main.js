$(function() {
    $('.deleteMessage') .on('click', function() {
         if(!confirm('削除してよいですか？')){
            return false;
         }
    });
});