$(function() {
    $('.変更') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });