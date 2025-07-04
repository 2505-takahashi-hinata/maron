$(document).ready(function() {
    //コメント削除ボタン
    $('.deleteComment').on('click',function(){
        if (confirm ('コメントを削除しますか？')) {
            return true;
        }
        return false;
    });
});