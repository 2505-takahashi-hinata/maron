
$(function() {
    $('.deleteMessage') .on('click', function() {
         if(!confirm('削除してよいですか？')){
            return false;
         }
    });
});

$(document).ready(function() {
    //コメント削除ボタン
    $('.deleteComment').on('click',function(){
        if (confirm ('コメントを削除しますか？')) {
            return true;
        }
        return false;
    });
});

$(function() {
    $('.変更') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });

