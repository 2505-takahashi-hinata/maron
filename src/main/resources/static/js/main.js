
$(function() {
    $('.deleteMessage') .off('click') .on('click', function() {
         if(!confirm('投稿を削除しますか？')){
            return false;
         }
    });
});

$(document).ready(function() {
    //コメント削除ボタン
    $('.deleteComment').off('click') .on('click',function(){
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
});

