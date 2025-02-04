
var audioPlayer = document.querySelector('.green-audio-player');//播放器
var playPause = audioPlayer.querySelector('#playPause');//播放按钮
var playpauseBtn = audioPlayer.querySelector('.play-pause-btn');//播放按钮的父层div
var loading = audioPlayer.querySelector('.loading');//等待图标
var progress = audioPlayer.querySelector('.progress');//绿色进度
var sliders = audioPlayer.querySelectorAll('.slider');//播放进度条
var volumeBtn = audioPlayer.querySelector('.volume-btn');//音量按钮
var volumeControls = audioPlayer.querySelector('.volume-controls');//音量调节菜单
var volumeProgress = volumeControls.querySelector('.slider .progress');//音量绿色条
var player = audioPlayer.querySelector('audio');//歌曲源
var currentTime = audioPlayer.querySelector('.current-time');//当前播放到哪
var totalTime = audioPlayer.querySelector('.total-time');//总时间
var speaker = audioPlayer.querySelector('#speaker');//音量调节点

var lastSongBtn=document.querySelector('#lastSong');
var nextSongBtn=document.querySelector('#nextSong');
var playModeBtn=document.querySelector('#playMode');
var playModeIcon=document.querySelector('#playModeIcon');
var likeBtn=document.querySelector('#like');
var likeIcon=document.querySelector('#likeIcon');

var draggableClasses = ['pin'];
var currentlyDragged = null;
var num=0;

window.addEventListener("storage", function(e)
{
    if(e.newValue!=null&&localStorage.getItem("songChanged")!=null)
        $.ajax({
            url: "/getSongByID",
            type: 'post',
            async:false,
            dataType: 'json',
            data: {
                "songID":e.newValue
            },
            success: function(data){
                if(data!=null)
                {
                    var li_play='<li class="songInfo" data-songid='+data.songid+'  onmouseenter="listOnMouseEnter(this)" onmouseleave="listOnMouseLeave(this)" onclick="playListClick(this.dataset.songid)" style="border-bottom: solid 1px rgba(214,216,212,0.3);">'+data.songname+'</li>';
                    var li_length='<li class="lengthInfo"  style="border-bottom: solid 1px rgba(214,216,212,0.3);">'+data.length+'</li>';
                    $("#playList").append(li_play);
                    $("#lengthList").append(li_length);
                }
                else
                    alert("找不到该歌曲?");
            },
            error: function(){
                alert("找不到该歌曲！");
            }
        });
});
function listOnMouseEnter(el)
{
    var playList=$('#playList').find('li');
    var lengthList=$('#lengthList').find('li');
    for(var i=0;i<playList.length;i++)
    {
        if(el==playList[i])
        {
            el.style.backgroundColor='rgba(214,216,212,0.1)';
            lengthList[i].style.backgroundColor='rgba(214,216,212,0.1)';
            if(el.style.color!="rgb(32, 167, 116)")
            {
                el.style.color="#d2d81b";
                lengthList[i].style.color="#d2d81b";
            }
            break;
        }
    }
}
function listOnMouseLeave(el)
{
    var playList=$('#playList').find('li');
    var lengthList=$('#lengthList').find('li');
    for(var i=0;i<playList.length;i++)
    {
        if(el==playList[i])
        {
            el.style.backgroundColor='rgba(214,216,212,0)';
            lengthList[i].style.backgroundColor='rgba(214,216,212,0)';
            if(el.style.color!="rgb(32, 167, 116)")
            {
                el.style.color="#D6D8D4";
                lengthList[i].style.color="#D6D8D4";
            }
            break;
        }
    }
}
function playListClick(songID)
{
    $.ajax({
        url: "/getSongByID",
        type: 'post',
        dataType: 'json',
        data: {
            "songID":songID
        },
        success: function(data){
            player.dataset.id=data.songid;
            player.src=data.songpath;
            player.dataset.name=data.songname;
            playPause.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTI2MzY5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE2NDUiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMjA5LjQxNyA2MmgxNTAuMDA4djkwMGgtMTUwLjAwOHYtOTAweiIgcC1pZD0iMTY0NiIgZmlsbD0iI0Q2RDhENCI+PC9wYXRoPjxwYXRoIGQ9Ik02NTkuNDE3IDYyaDE0OS45ODV2OTAwaC0xNDkuOTg1di05MDB6IiBwLWlkPSIxNjQ3IiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        },
        error: function(){
            alert("???");
        }
    })
}
window.onbeforeunload=function leave()
{
    $.ajax({
        url: "/closePlayer",
        type: 'get',
        dataType: 'json',
        success: function(){
        },
        error: function(){
            alert("关闭事件没执行");
        }
    })
};
window.addEventListener('mousedown', function (event) {

    if (!isDraggable(event.target)) return false;

    currentlyDragged = event.target;
    var handleMethod = currentlyDragged.dataset.method;

    this.addEventListener('mousemove', window[handleMethod], false);

    window.addEventListener('mouseup', function () {
        currentlyDragged = false;
        window.removeEventListener('mousemove', window[handleMethod], false);
    }, false);
});

nextSongBtn.addEventListener('click',getNextSong);
lastSongBtn.addEventListener('click',function () {
    $.ajax({
        url: "/getLastSong",
        type: 'post',
        dataType: 'json',
        data:{
            "songID":player.dataset.id
        },
        success: function(data){
            player.dataset.id=data.songid;
            player.src=data.songpath;
            player.dataset.name=data.songname;
            playPause.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTI2MzY5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE2NDUiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMjA5LjQxNyA2MmgxNTAuMDA4djkwMGgtMTUwLjAwOHYtOTAweiIgcC1pZD0iMTY0NiIgZmlsbD0iI0Q2RDhENCI+PC9wYXRoPjxwYXRoIGQ9Ik02NTkuNDE3IDYyaDE0OS45ODV2OTAwaC0xNDkuOTg1di05MDB6IiBwLWlkPSIxNjQ3IiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        },
        error: function(){
            alert("上一首按钮监听");
        }
    })
});
playModeBtn.addEventListener('click',function () {
    $.ajax({
        url: "/changePlayMode",
        type: 'post',
        dataType: 'json',
        data: {
            "mode":playModeIcon.dataset.mode,
            "songID":player.dataset.id
        },
        success: function(){
        }
    });
    if(playModeIcon.dataset.mode=="0")
    {
        playModeIcon.dataset.mode="1";
        playModeIcon.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTEyMjI1IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjExNTMiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNzUzLjU2NDczMSAzMzcuNDcxMDM1Yy00NS44Njk3IDAtMTYwLjI1OTk4NCAxMTMuODQ5OTc4LTI0My43ODkzOTkgMTk0LjU0ODkyOEMzODMuMTM0MDI3IDY1NC4zODM4NDggMjYzLjUwODUwOSA3NzMuMjg0ODY1IDE2Ny43NjQ5MTEgNzczLjI4NDg2NWwtNTguODkyMjk1IDBjLTI0LjA2ODE2MiAwLTQzLjU4MTU4OC0xOS41MjY3MjktNDMuNTgxNTg4LTQzLjU4MTU4OHMxOS41MTM0MjYtNDMuNTgxNTg4IDQzLjU4MTU4OC00My41ODE1ODhsNTguODkyMjk1IDBjNjAuNTA0MDAyIDAgMTgzLjAwMjk2NC0xMjEuNjgxMzQgMjgxLjQzMjc0MS0yMTYuNzg0MzQ4IDExOS43OTY0MS0xMTUuNzQ0MTE3IDIyMy4yNTQ3MTMtMjE5LjAyOTQ4MiAzMDQuMzY4MTAyLTIxOS4wMjk0ODJsNTYuMjA5MTg2IDAtNTkuNjQxMzU1LTU3LjgyODA1N2MtMTcuMDMzOTU1LTE2Ljk5MzAyMy0xNy4wNjA1NjEtNDIuOTAyMTEyLTAuMDU3MzA1LTU5LjkyNzg4MSAxNy4wMDIyMzItMTcuMDMwODg1IDQ0LjU5NjcwNy0xNy4wNjQ2NTQgNjEuNjMxNjg2LTAuMDY1NDkybDEzNC4yMDc2MzEgMTMzLjg3NDAzM2M4LjE5MjU4OSA4LjE3MjEyMyAxMi43OTQzOTcgMTkuMjM4MTU3IDEyLjc5NDM5NyAzMC44MDM1NjMgMCAxMS41NjQzODMtNC42MDE4MDggMjIuNjA0ODM0LTEyLjc5NDM5NyAzMC43NzY5NTdMODExLjcwNjk0MyA0NjEuNzI1OTljLTguNTA1NzIxIDguNDg2Mjc4LTE5LjY0NjQ1NiAxMi41MjIxOTgtMzAuNzg3MTkgMTIuNTIyMTk4LTExLjE2NjMxNyAwLTIyLjMzMzY1OC00LjY3NjUwOS0zMC44NDQ0OTUtMTMuMTk5NjI3LTE3LjAwMzI1Ni0xNy4wMjU3NjktMTYuOTc1NjI3LTQ1LjQzMjc0OSAwLjA1NzMwNS02Mi40MjU3NzFsNTkuNjQxMzU1LTYxLjE1MTc1NUw3NTMuNTY0NzMxIDMzNy40NzEwMzV6TTgxMS43MDY5NDMgNTYxLjY2MTA1Yy0xNy4wMzQ5NzgtMTYuOTk5MTYzLTQ0LjYyOTQ1My0xNi45NzI1NTctNjEuNjMxNjg2IDAuMDU4MzI4LTE3LjAwMzI1NiAxNy4wMjQ3NDUtMTYuOTc1NjI3IDQ2LjI1NzUzMyAwLjA1NzMwNSA2My4yNTA1NTZsNTkuNjQxMzU1IDYxLjE1MDczMi01Ni4yMDkxODYgMGMtMzUuNzkzMjA0IDAtOTUuNTkwMTAyLTUyLjk0Njg4Ni0xNTQuODc2MzctMTA4LjM3MzI0My0xNy41NzYzMDctMTYuNDM1MzIxLTQ1LjE2MTU3Mi0xNi4zNDIyLTYxLjU5NDg0NyAxLjIyNjk0NC0xNi40NDQ1MzEgMTcuNTY4MTIxLTE1LjUyMzU1NSA0Ni4zOTM2MzMgMi4wNTM3NzYgNjIuODIzODM3IDkwLjMyMjEyMiA4NC40NTg1NzcgMTUxLjI0NjcwMyAxMzEuNDg0NjEzIDIxNC40MTc0NDEgMTMxLjQ4NDYxM2w1Ni4yMDkxODYgMC01OS42NDEzNTUgNTcuODI0OTg3Yy0xNy4wMzM5NTUgMTYuOTkzMDIzLTE3LjA2MDU2MSA0My43MzYxMDctMC4wNTczMDUgNjAuNzYxODc1IDguNTExODYxIDguNTIzMTE3IDE5LjY3ODE3OCAxMi4zNjk3MjUgMzAuODQ0NDk1IDEyLjM2OTcyNSAxMS4xNDA3MzUgMCAyMi4yODE0NjktNC40NTM0MjkgMzAuNzg3MTktMTIuOTM5NzA3TDk0NS45MTQ1NzQgNzU3LjMxMTA1NWM4LjE5MjU4OS04LjE3MzE0NyAxMi43OTQzOTctMTkuMzE1OTI4IDEyLjc5NDM5Ny0zMC44ODEzMzQgMC0xMS41NjQzODMtNC42MDE4MDgtMjIuNjgyNjA1LTEyLjc5NDM5Ny0zMC44NTU3NTJMODExLjcwNjk0MyA1NjEuNjYxMDV6TTEwOC44NzE1OTMgMzM3LjQ3MTAzNWw1OC44OTIyOTUgMGM0NS45MzIxMjIgMCAxMTQuNDAxNTQgNTguNDU1MzQzIDE2OC45MTUxMDggMTA3Ljk0MjQzMSA4LjM1MjIyNSA3LjU3NjU1OSAxOC44MzI5MjcgMTIuMTQwNTA1IDI5LjI5MjE0IDEyLjE0MDUwNSAxMS44NTI5NTYgMCAyMy42NzMxNjYtNC4zOTQwNzcgMzIuMjcwOTg0LTEzLjg1NzYxMyAxNi4xODI1NjQtMTcuODA3NTc0IDE0Ljg1OTQyOS00Ni44MjM0MjItMi45NTgzNzgtNjIuOTk4ODIzLTg1LjI0NzU0Ni03Ny4zODEzOTEtMTU2LjU2MTc1NS0xMzAuMzg4NjUyLTIyNy41MTk4NTQtMTMwLjM4ODY1MmwtNTguODkyMjk1IDBjLTI0LjA2ODE2MiAwLTQzLjU4MTU4OCAxOS41MjY3MjktNDMuNTgxNTg4IDQzLjU4MTU4OFM4NC44MDQ0NTUgMzM3LjQ3MTAzNSAxMDguODcxNTkzIDMzNy40NzEwMzV6IiBwLWlkPSIxMTU0IiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        //变为随机播放
    }
    else if(playModeIcon.dataset.mode=="1")
    {
        playModeIcon.dataset.mode="2";
        playModeIcon.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTAwNTM2IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9Ijk4MSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwvc3R5bGU+PC9kZWZzPjxwYXRoIGQ9Ik03NzMuNTk3ODY3IDIwMy43NzZsLTAuMjczMDY3IDAuNDQzNzMzYTMzLjYyMTMzMyAzMy42MjEzMzMgMCAwIDAtMjAuMzQzNDY3LTYuNDg1MzMzYy0xOC45NzgxMzMgMC43ODUwNjctMzMuNzIzNzMzIDE2LjcyNTMzMy0zMi45NzI4IDM1Ljc3MTczM2EzMy44MjYxMzMgMzMuODI2MTMzIDAgMCAwIDE1LjYzMzA2NyAyNi43OTQ2NjdsLTAuMTAyNCAwLjE3MDY2N2EzNzUuMjI3NzMzIDM3NS4yMjc3MzMgMCAwIDEgMTY2LjI5NzYgMzExLjg3NjI2NmMwIDIwNy4wMTg2NjctMTY4LjQ0OCAzNzUuNDY2NjY3LTM3NS40NjY2NjcgMzc1LjQ2NjY2Ny0yNy44MTg2NjcgMC0yNy44MTg2NjcgMC01NC4zNDAyNjYtMi45MDEzMzMtOTAuNDUzMzMzLTkuODk4NjY3LTE1Mi42MTAxMzMtNTguOTgyNC0xNTUuNTQ1Ni02MS4yMDEwNjdhMzc1LjMzMDEzMyAzNzUuMzMwMTMzIDAgMCAxLTE2NS41ODA4LTMxMS4zNjQyNjdjMC0yMDEuNDIwOCAxNTkuNTM5Mi0zNjUuODQxMDY3IDM1OC44NDM3MzMtMzc0LjY0NzQ2NmwtNDEuOTg0IDQwLjgyMzQ2NiAwLjgxOTIgMC44NTMzMzRhMzMuODYwMjY3IDMzLjg2MDI2NyAwIDAgMC0xNS4yNTc2IDI5LjIxODEzM2MwLjc1MDkzMyAxOC45NzgxMzMgMTYuNzI1MzMzIDMzLjc1Nzg2NyAzNS42NjkzMzMgMzMuMDQxMDY3YTMzLjc5MiAzMy43OTIgMCAwIDAgMjYuMDA5Ni0xNC40NzI1MzRsMC4zMDcyIDAuMzQxMzM0IDEyOC42NDg1MzQtMTI0Ljk5NjI2N0w1MTUuNjg2NCAzNC4wOTkybC0wLjQwOTYgMC40MDk2YTMzLjY4OTYgMzMuNjg5NiAwIDAgMC0yNi4zMTY4LTExLjQwMDUzM2MtMTguOTQ0IDAuNzUwOTMzLTMzLjY4OTYgMTYuNzI1MzMzLTMyLjk3MjggMzUuNzAzNDY2YTMzLjg5NDQgMzMuODk0NCAwIDAgMCAxMy4wMzg5MzMgMjUuMTU2MjY3bDQ1LjIyNjY2NyA0NS4yNjA4QzI3NS4xODI5MzMgMTM1LjcxNDEzMyA4Mi42MDI2NjcgMzMxLjc0MTg2NyA4Mi42MDI2NjcgNTcyLjM0NzczM2MwIDE0Ny42MjY2NjcgNzMuMTEzNiAyODUuMTQ5ODY3IDE5My4xOTQ2NjYgMzY2LjExNDEzNCAzLjA3MiAyLjU2IDc3LjU1MDkzMyA2Mi4xOTA5MzMgMTg4Ljc1NzMzNCA3NC4zMDgyNjYgMjkuNDIyOTMzIDMuMjA4NTMzIDMxLjEyOTYgMy4zMTA5MzMgNjEuNzgxMzMzIDMuMzEwOTM0IDI0NC42Njc3MzMgMCA0NDMuNzMzMzMzLTE5OS4wNjU2IDQ0My43MzMzMzMtNDQzLjczMzMzNEE0NDMuMzkyIDQ0My4zOTIgMCAwIDAgNzczLjU5Nzg2NyAyMDMuNzc2IiBwLWlkPSI5ODIiIGZpbGw9IiNENkQ4RDQiPjwvcGF0aD48cGF0aCBkPSJNNTQyLjA3MTQ2NyA0MDAuODI3NzMzYTMzLjI4IDMzLjI4IDAgMCAwLTE3LjEzNDkzNCA2LjA3NTczNGwtMC4xNzA2NjYtMC4zMDcyLTEwNi44MDMyIDY5LjcwMDI2NiAwLjE3MDY2NiAwLjI3MzA2N2EzMy45OTY4IDMzLjk5NjggMCAwIDAtMTUuODM3ODY2IDI5LjY5NmMwLjc1MDkzMyAxOC45NDQgMTYuNzI1MzMzIDMzLjcyMzczMyAzNS42NjkzMzMgMzIuOTcyOGEzMy4xMDkzMzMgMzMuMTA5MzMzIDAgMCAwIDE3LjEwMDgtNi4wNDE2bDAuMjA0OCAwLjI3MzA2NyA1My45OTg5MzMtMzUuMjU5NzM0djI0OS44NTZoMC4wMzQxMzRjMCAwLjQ3Nzg2Ny0wLjIzODkzMyAwLjg4NzQ2Ny0wLjIwNDggMS4zNjUzMzRhMzQuMjAxNiAzNC4yMDE2IDAgMSAwIDY4LjQwMzItMS4zNjUzMzRoMC4wMzQxMzN2LTAuMTcwNjY2YzAtMC40MDk2IDAuMjA0OC0wLjc1MDkzMyAwLjIwNDgtMS4xNjA1MzQgMC0wLjI3MzA2Ny0wLjE3MDY2Ny0wLjQ3Nzg2Ny0wLjIwNDgtMC43ODUwNjZWNDM1LjJjMC0wLjQ3Nzg2NyAwLjIwNDgtMC44ODc0NjcgMC4yMDQ4LTEuMzMxMmEzNC40MDY0IDM0LjQwNjQgMCAwIDAtMzUuNjY5MzMzLTMzLjA0MTA2NyIgcC1pZD0iOTgzIiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        //变为单曲播放
    }
    else
    {
        playModeIcon.dataset.mode="0";
        playModeIcon.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTE5OTI5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjEzMjQiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNOTQ2LjQ4MzUzMiA0NjcuMjE4MDc0IDc5OS41MzM2OTIgNjE2LjQ3NDQ0NmMtNy45OTgxNjEgOC4xMjE5ODEtMTguOTE4ODg1IDEyLjY5NzE4My0zMC4zMTg1MTYgMTIuNjk3MTgzLTExLjM5OTYzMSAwLTIyLjMyMDM1NS00LjU3NTIwMi0zMC4zMTg1MTYtMTIuNjk3MTgzTDU5MS45NDU3OTcgNDY3LjIxODA3NGMtMTYuNDgzNDE2LTE2Ljc0NDM1OS0xNi4yNzA1NjktNDMuNjc2NzU1IDAuNDcxNzQ0LTYwLjE2NTI4OCAxNi43MzkyNDMtMTYuNDgzNDE2IDQzLjY2NTQ5OS0xNi4yNzU2ODUgNjAuMTY1Mjg4IDAuNDcxNzQ0bDgyLjEzNDY0OSA4My40MjYwNjFjLTEwLjY1MzY0MS0xNTQuNDE1ODgyLTEzNy4zOTIxNi0yNzYuNzU3MjU1LTI5MS43NTg5MjMtMjc2Ljc1NzI1NS0xNjEuMzMxMzg1IDAtMjkyLjU4MTY2MiAxMzMuNTkyNjI0LTI5Mi41ODE2NjIgMjk3LjgwNjY2M1MyODEuNjI4MTkyIDgwOS44MDU2NCA0NDIuOTU5NTc3IDgwOS44MDU2NGM1OS4zODA0MTIgMCAxMTYuNTIyODU0LTE3Ljk3OTQ5IDE2NS4yNDE0MzgtNTIuMDAxMzUxIDE5LjI3MTkyNi0xMy40NDUyMiA0NS43OTQ5OTktOC43MzM5MTggNTkuMjQwMjE5IDEwLjUyNzc3NCAxMy40NTEzNiAxOS4yNjY4MDkgOC43MzQ5NDIgNDUuNzg5ODgyLTEwLjUyNjc1MSA1OS4yNDAyMTktNjMuMDc4NjQxIDQ0LjAzOTAwNi0xMzcuMDYxNjMyIDY3LjMyMTI2OC0yMTMuOTU0OTA3IDY3LjMyMTI2OC0yMDguMjQ2OTA1IDAtMzc3LjY2OTU3Mi0xNzEuNzYzOTkyLTM3Ny42Njk1NzItMzgyLjg5MzU1czE2OS40MjE2NDQtMzgyLjg5MzU1IDM3Ny42Njk1NzItMzgyLjg5MzU1YzE5Ni4xMTU2MSAwIDM1Ny43NDE3MDcgMTUyLjM2MDA2IDM3NS45MDQzNjkgMzQ2LjQ1NDY0bDY2Ljk4MTUzMS02OC4wMzU1MzZjMTYuNDk5Nzg5LTE2Ljc1MjU0NiA0My40MzczMDItMTYuOTUwMDQ0IDYwLjE2NTI4OC0wLjQ3MTc0NEM5NjIuNzU1MTI0IDQyMy41NDEzMTkgOTYyLjk2Nzk3MiA0NTAuNDczNzE1IDk0Ni40ODM1MzIgNDY3LjIxODA3NHoiIHAtaWQ9IjEzMjUiIGZpbGw9IiNENkQ4RDQiPjwvcGF0aD48L3N2Zz4=";
        //变为循环播放
    }
});
likeBtn.addEventListener('click',function () {
    if(likeIcon.dataset.state=="0")
    {
        likeIcon.dataset.state="1";
        likeIcon.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MDg2MTc5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYzOSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwvc3R5bGU+PC9kZWZzPjxwYXRoIGQ9Ik01MTUuNiA5MzkuNVMzNy41IDYwOS41IDM3LjUgMzQ2LjlDMzcuNSAxNzguNSAxNTIgODQuMyAyNzkuOSA4NC4zczIzNS43IDE0MS40IDIzNS43IDE0MS40UzY0My42IDg0LjMgNzU4IDg0LjNjMTE0LjUgMCAyMjkgODcuNSAyMjkgMjY5LjQtNi43IDI1NS44LTQ3MS40IDU4NS44LTQ3MS40IDU4NS44IiBwLWlkPSI2NDAiIGZpbGw9IiNENkQ4RDQiPjwvcGF0aD48L3N2Zz4=";
    }
    else
    {
        likeIcon.dataset.state="0";
        likeIcon.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MDkzMTk3IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjgxMCIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwvc3R5bGU+PC9kZWZzPjxwYXRoIGQ9Ik03MjguMTc3Nzc4IDExMy43Nzc3NzhjLTEzMC44NDQ0NDQgMC0yMTAuNDg4ODg5IDEwMi40LTIxMC40ODg4ODkgMTAyLjRTNDQ5LjQyMjIyMiAxMTMuNzc3Nzc4IDMwNy4yIDExMy43Nzc3NzggNTYuODg4ODg5IDE5My40MjIyMjIgNTYuODg4ODg5IDM4Ni44NDQ0NDRjMCAyNzMuMDY2NjY3IDQ2MC44IDU2OC44ODg4ODkgNDYwLjggNTY4Ljg4ODg4OVM5NjcuMTExMTExIDY0Mi44NDQ0NDQgOTY3LjExMTExMSAzOTIuNTMzMzMzQzk2Ny4xMTExMTEgMTg3LjczMzMzMyA4MzYuMjY2NjY3IDExMy43Nzc3NzggNzI4LjE3Nzc3OCAxMTMuNzc3Nzc4TTUxNy42ODg4ODkgODg3LjQ2NjY2N1MxMTMuNzc3Nzc4IDYwOC43MTExMTEgMTEzLjc3Nzc3OCAzODYuODQ0NDQ0YzAtMTQyLjIyMjIyMiA5Ni43MTExMTEtMjIxLjg2NjY2NyAyMDQuOC0yMjEuODY2NjY2QzQyNi42NjY2NjcgMTY0Ljk3Nzc3OCA1MTcuNjg4ODg5IDI4NC40NDQ0NDQgNTE3LjY4ODg4OSAyODQuNDQ0NDQ0czEwOC4wODg4ODktMTE5LjQ2NjY2NyAyMDQuOC0xMTkuNDY2NjY2Yzk2LjcxMTExMSAwIDE5My40MjIyMjIgNzMuOTU1NTU2IDE5My40MjIyMjIgMjI3LjU1NTU1NS01LjY4ODg4OSAyMTYuMTc3Nzc4LTM5OC4yMjIyMjIgNDk0LjkzMzMzMy0zOTguMjIyMjIyIDQ5NC45MzMzMzQiIGZpbGw9IiNENkQ4RDQiIHAtaWQ9IjgxMSI+PC9wYXRoPjwvc3ZnPg==";
    }
});

playpauseBtn.addEventListener('click', togglePlay);//播放按钮点击监听
player.addEventListener('timeupdate', updateProgress);//更新时间
player.addEventListener('volumechange', updateVolume);//改变音量
player.addEventListener('loadedmetadata', function ()
{
    totalTime.textContent = formatTime(player.duration);
    if(player.paused)
    {
        player.play();
    }
    else
        alert("在播放??");
    var playList=document.getElementsByClassName("songInfo");
    var lengthList=document.getElementsByClassName("lengthInfo");
    for(var i=0;i<playList.length;i++)
    {
        playList[i].style.color="#D6D8D4";
        lengthList[i].style.color="#D6D8D4";
        if(playList[i].dataset.songid==player.dataset.id)
        {
            playList[i].style.color="#20A774";
            lengthList[i].style.color="#20A774";
        }
    }

    $.ajax({
        url: "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=1&w="+player.dataset.name,
        type:"get",
        dataType:'jsonp',
        jsonp: "callback",
        jsonpCallback:'callback',
        scriptCharset: 'GBK',//解决中文乱码
        success: function callback(data){
            var albumid=data.data.song.list[0].albumid;
            document.getElementsByTagName('img')[0].src="http://imgcache.qq.com/music/photo/album_300/"+albumid%100+"/300_albumpic_"+albumid+"_0.jpg";
        },
        error: function(){
            alert("qq音乐接口");
        }
    });
    $.ajax({
        url: "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.search.catalogSug&query="+player.dataset.name,
        type:"get",
        dataType:'jsonp',
        jsonp: "callback",
        scriptCharset: 'GBK',//解决中文乱码
        success: function callback(data){
            var songid=data.song[0].songid;
            $.ajax({
                url: "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.song.lry&songid="+songid,
                type:"get",
                dataType:'jsonp',
                jsonp: "callback",
                success: function(data) {
                    var lrc = data.lrcContent;
                    var str = parseLyric(lrc);

                    document.getElementsByClassName("lrc")[0].scrollTop=0;
                    $("#gc").find("li").remove()
                    var li='<li>'+" "+'</li>';
                    $('#gc').append(li);

                    for (var i = 0, li; i < str.length; i++) {
                        li = '<li>' + str[i][1] + '</li>';
                        $('#gc').append(li);
                    }
                    player.ontimeupdate = function ()
                    {
                        var i=0;
                        var l;
                        for (i,l = str.length; i < l; i++)//判断到第i句歌词 对应下标为i的li标签
                            if (this.currentTime <str[i][0])
                                break;
                        for(var j=0,k=$("#gc").find("li").length;j<k;j++)
                            $("#gc").find("li")[j].style.color="#D6D8D4"
                        $("#gc").find("li")[i].style.color="#20A774";
                        if(i>8)
                        {
                            document.getElementsByClassName("lrc")[0].scrollTop=document.getElementsByTagName('li')[i].offsetTop-216;
                        }
                        if(i>num)//每次歌曲时间更新后 该时间对应的那句歌词如果比之前存储的num大 说明到了下一句歌词
                        {
                            num++;
                        }

                    };
                },
                error: function(){
                    alert("歌词ajax");
                }
            })
        },
        error: function(){
            alert("找歌曲ajax");
        }
    })
});
player.addEventListener('canplay', makePlay);//显示播放按钮而不是转圈

//歌曲结束 播放下一首
player.addEventListener('ended', getNextSong);

volumeBtn.addEventListener('click', function () {
    volumeBtn.classList.toggle('open');//在类名中移除 若不存在则添加
    volumeControls.classList.toggle('hidden');
});

window.addEventListener('resize', directionAware);

//进度条的点击事件
sliders.forEach(function (slider) {
    var pin = slider.querySelector('.pin');
    slider.addEventListener('click', window[pin.dataset.method]);
});

directionAware();

function getNextSong() {
    player.currentTime = 0;
    $.ajax({
        url: "/getNextSong",
        type: 'post',
        dataType: 'json',
        data: {
            "songID":player.dataset.id
        },
        success: function(data){
            player.dataset.id=data.songid;
            player.src=data.songpath;
            player.dataset.name=data.songname;
            playPause.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTI2MzY5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE2NDUiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMjA5LjQxNyA2MmgxNTAuMDA4djkwMGgtMTUwLjAwOHYtOTAweiIgcC1pZD0iMTY0NiIgZmlsbD0iI0Q2RDhENCI+PC9wYXRoPjxwYXRoIGQ9Ik02NTkuNDE3IDYyaDE0OS45ODV2OTAwaC0xNDkuOTg1di05MDB6IiBwLWlkPSIxNjQ3IiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        },
        error: function(){
            alert("???");
        }
    })
}

function isDraggable(el) {
    var canDrag = false;
    var classes = Array.from(el.classList);//获取传入的元素类名
    draggableClasses.forEach(function (draggable) {
        if (classes.indexOf(draggable) !== -1) canDrag = true;//检查标签列表是否包含可拖动标签
    });
    return canDrag;
}

function inRange(event) {
    var rangeBox = getRangeBox(event);
    var rect = rangeBox.getBoundingClientRect();
    var direction = rangeBox.dataset.direction;
    if (direction == 'horizontal') {
        var min = rangeBox.offsetLeft;
        var max = min + rangeBox.offsetWidth;
        if (event.clientX < min || event.clientX > max) return false;
    } else {
        var min = rect.top;
        var max = min + rangeBox.offsetHeight;
        if (event.clientY < min || event.clientY > max) return false;
    }
    return true;
}

//更细绿色进度条
function updateProgress() {
    var current = player.currentTime;
    var percent = current / player.duration * 100;
    progress.style.width = percent + '%';

    currentTime.textContent = formatTime(current);
    totalTime.textContent=formatTime(player.duration-current);
}

function updateVolume() {
    volumeProgress.style.height = player.volume * 100 + '%';
    if (player.volume >= 0.5) {
        speaker.attributes.d.value = 'M14.667 0v2.747c3.853 1.146 6.666 4.72 6.666 8.946 0 4.227-2.813 7.787-6.666 8.934v2.76C20 22.173 24 17.4 24 11.693 24 5.987 20 1.213 14.667 0zM18 11.693c0-2.36-1.333-4.386-3.333-5.373v10.707c2-.947 3.333-2.987 3.333-5.334zm-18-4v8h5.333L12 22.36V1.027L5.333 7.693H0z';
    } else if (player.volume < 0.5 && player.volume > 0.05) {
        speaker.attributes.d.value = 'M0 7.667v8h5.333L12 22.333V1L5.333 7.667M17.333 11.373C17.333 9.013 16 6.987 14 6v10.707c2-.947 3.333-2.987 3.333-5.334z';
    } else if (player.volume <= 0.05) {
        speaker.attributes.d.value = 'M0 7.667v8h5.333L12 22.333V1L5.333 7.667';
    }
}

function getRangeBox(event) {
    var rangeBox = event.target;
    var el = currentlyDragged;
    //点击进度条 获取其范围
    if (event.type == 'click' && isDraggable(event.target)) {
        rangeBox = event.target.parentElement.parentElement;
    }
    //拖动进度条的范围
    if (event.type == 'mousemove') {
        rangeBox = el.parentElement.parentElement;
    }
    return rangeBox;
}

function getCoefficient(event) {
    var slider = getRangeBox(event);
    var rect = slider.getBoundingClientRect();
    var K = 0;
    if (slider.dataset.direction == 'horizontal') {

        var offsetX = event.clientX - slider.offsetLeft;
        var width = slider.clientWidth;
        K = offsetX / width;
    } else if (slider.dataset.direction == 'vertical') {

        var height = slider.clientHeight;
        var offsetY = event.clientY - rect.top;
        K = 1 - offsetY / height;
    }
    return K;
}

function rewind(event) {
    //判断是否在进度条可点击范围
    if (inRange(event)) {
        player.currentTime = player.duration * getCoefficient(event);
    }
}

function changeVolume(event) {
    if (inRange(event)) {
        player.volume = getCoefficient(event);
    }
}

function formatTime(time) {
    var min = Math.floor(time / 60);
    var sec = Math.floor(time % 60);
    return min + ':' + (sec < 10 ? '0' + sec : sec);
}

function togglePlay() {
    if (player.paused) {
        playPause.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDk0MTI2MzY5IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE2NDUiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMjA5LjQxNyA2MmgxNTAuMDA4djkwMGgtMTUwLjAwOHYtOTAweiIgcC1pZD0iMTY0NiIgZmlsbD0iI0Q2RDhENCI+PC9wYXRoPjxwYXRoIGQ9Ik02NTkuNDE3IDYyaDE0OS45ODV2OTAwaC0xNDkuOTg1di05MDB6IiBwLWlkPSIxNjQ3IiBmaWxsPSIjRDZEOEQ0Ij48L3BhdGg+PC9zdmc+";
        player.play();
    } else {
        playPause.src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTQ1NDkzOTQxMjU2IiBjbGFzcz0iaWNvbiIgc3R5bGU9IiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE3NjYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMTAyLjQyNTc0MiAxMDIuMzkzNTY1djgxOS4xNDg1MTZsNjE0LjM2MTM4Ny00MDkuNTc0MjU4eiIgZmlsbD0iI0Q2RDhENCIgcC1pZD0iMTc2NyI+PC9wYXRoPjxwYXRoIGQ9Ik0xNTMuNjIyNTI0IDEwMi4zOTM1NjV2ODE5LjE0ODUxNmw2MTQuMzYxMzg3LTQwOS41NzQyNTh6IiBmaWxsPSIjRDZEOEQ0IiBwLWlkPSIxNzY4Ij48L3BhdGg+PHBhdGggZD0iTTI1OS41OTk4NjMgMTUuODcxMDAzVjgzNC41MDc1NTFsNjE5LjQ4MTA2Ni00MDUuNDc4NTE1eiIgZmlsbD0iI0Q2RDhENCIgcC1pZD0iMTc2OSI+PC9wYXRoPjxwYXRoIGQ9Ik0yNjEuMTM1NzY3IDE4OS40MjgwOTRsLTEuNTM1OTA0IDgxOC42MzY1NDlMODc1LjQ5NzE1NCA1OTkuMDAyMzUzeiIgZmlsbD0iI0Q2RDhENCIgcC1pZD0iMTc3MCI+PC9wYXRoPjxwYXRoIGQ9Ik0yMDQuODE5MzA2IDEwMi4zOTM1NjVtLTEwMi4zOTM1NjQgMGExMDIuMzkzNTY1IDEwMi4zOTM1NjUgMCAxIDAgMjA0Ljc4NzEyOSAwIDEwMi4zOTM1NjUgMTAyLjM5MzU2NSAwIDEgMC0yMDQuNzg3MTI5IDBaIiBmaWxsPSIjRDZEOEQ0IiBwLWlkPSIxNzcxIj48L3BhdGg+PHBhdGggZD0iTTgxOS4xODA2OTQgNDA5LjU3NDI1OGMtNTYuMzE2NDYxIDAtMTAyLjM5MzU2NSA0Ni4wNzcxMDQtMTAyLjM5MzU2NSAxMDIuMzkzNTY1czQ2LjA3NzEwNCAxMDAuMzQ1NjkzIDEwMi4zOTM1NjUgMTAyLjM5MzU2NGM1Ny44NTIzNjQgMi4wNDc4NzEgMTAyLjkwNTUzMi00NS4wNTMxNjggMTAyLjM5MzU2NC0xMDIuMzkzNTY0LTAuNTExOTY4LTU2LjMxNjQ2MS00Ni4wNzcxMDQtMTAyLjM5MzU2NS0xMDIuMzkzNTY0LTEwMi4zOTM1NjV6TTIwNC44MTkzMDYgODE5LjE0ODUxN2MtNTYuMzE2NDYxIDAtMTAyLjM5MzU2NSA0Ni4wNzcxMDQtMTAyLjM5MzU2NCAxMDIuMzkzNTY0czQ2LjA3NzEwNCAxMDAuMzQ1NjkzIDEwMi4zOTM1NjQgMTAyLjM5MzU2NWM1My43NTY2MjEgMi4wNDc4NzEgMTAwLjg1NzY2MS00NS4wNTMxNjggMTAyLjM5MzU2NS0xMDIuMzkzNTY1IDEuNTM1OTAzLTU2LjMxNjQ2MS00Ni4wNzcxMDQtMTAyLjM5MzU2NS0xMDIuMzkzNTY1LTEwMi4zOTM1NjR6IiBmaWxsPSIjRDZEOEQ0IiBwLWlkPSIxNzcyIj48L3BhdGg+PC9zdmc+";
        player.pause();
    }
}

function makePlay() {
    playpauseBtn.style.display = 'block';
    loading.style.display = 'none';
}

function directionAware() {
    if (window.innerHeight < 250) {
        volumeControls.style.bottom = '-54px';
        volumeControls.style.left = '54px';
    } else if (audioPlayer.offsetTop < 154) {
        volumeControls.style.bottom = '-164px';
        volumeControls.style.left = '-3px';
    } else {
        volumeControls.style.bottom = '52px';
        volumeControls.style.left = '-3px';
    }
}
function parseLyric(text)
{
    var lines = text.split('\n'),              //将文本分隔成一行一行，存入数组
        pattern = /\[\d{2}:\d{2}.\d{2}\]/g,    //用于匹配时间的正则表达式，匹配的结果类似[xx:xx.xx]
        result = [];                           //保存最终结果的数组
    while (!pattern.test(lines[0]))
    {          //去掉不含时间的行
        lines = lines.slice(1);
    };
    lines[lines.length - 1].length === 0 && lines.pop();//上面用'\n'生成生成数组时，结果中最后一个为空元素，这里将去掉
    lines.forEach(function(v,i,a)               /*数组元素值*/ /*元素索引*//*数组本身*/
    {
        var time = v.match(pattern),            //提取出时间[xx:xx.xx]
            value = v.replace(pattern, '');    //提取歌词
        time.forEach(function(v1, i1, a1)
        {                                       //因为一行里面可能有多个时间，所以time有可能是[xx:xx.xx][xx:xx.xx][xx:xx.xx]的形式，需要进一步分隔
            var t = v1.slice(1, -1).split(':'); //去掉时间里的中括号得到xx:xx.xx
            result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]);//将结果压入最终数组
        });
    });
    result.sort(function(a, b)
    {                                            //最后将结果数组中的元素按时间大小排序，以便保存之后正常显示歌词

        return a[0] - b[0];
    });
    return result;
}
