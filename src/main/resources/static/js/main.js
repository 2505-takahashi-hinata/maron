
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
    $('.変更').off('click') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });
});

function changeColor() {
  const element = document.getElementById('status-select');
  const value = parseInt(document.getElementById('map.key').value); // 入力された値を数値に変換

  if (value == 1) {
    element.style.backgroundColor = 'red';
}
}
