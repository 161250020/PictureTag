<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://cdn.bootcss.com/foundation/5.5.3/css/foundation.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/foundation/5.5.3/js/foundation.min.js"></script>
    <script src="https://cdn.bootcss.com/foundation/5.5.3/js/vendor/modernizr.js"></script>

    <link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">

    <style>
        /* Make the image fully responsive */
        .carousel-inner img {
            width: 100%;
            height: 100%;
        }
    </style>

    <style rel="stylesheet">
        .one,.two{
            width: 50%;
            height: 800px;
            float: left;
            box-sizing: border-box;
        }
    </style>

</head>
<body>
<div class="panel panel-info" style="height: 1000px">
    <!--标题-->
    <div class="panel-heading" id="head">
        <b class="panel-title">图片众包网站 &nbsp; &nbsp;</b>
        <br>
        <br>
        <br>
    </div>



    <!--内容-->
    <div class="panel-body" style="height: 800px; width: 100%">
        <div class="one" align="left">
            <div id="myCarousel" class="carousel slide" style="background-color: #ffc05f;width: 100%;height: 66%">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="www.baidu.com" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="../back1.png" alt="First slide">
                    </div>
                    <div class="item">
                        <img src="../back2.png" alt="Second slide">
                    </div>
                    <div class="item">
                        <img src="../back3.png" alt="Third slide">
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
        <!--登录-->
        <div class="two" align="center" id="divreg">
            <div class="panel-body" style="background-color: #ffc05f;width: 100%;height: 66%" align="center">
                <br>
                <br>
                <br>
                <div class="panel panel-default" style="width: 50%" align="center">
                    <div class="panel-heading">
                        平台用户登录
                    </div>
                    <div class="panel-body">
                        <br>
                        <input type="text" class="form-control" id="account" placeholder="帐号：">
                        <input type="text" class="form-control" id="password" placeholder="密码：">
                        <button onclick="register()" type="button" class="button small" style="width: 100%">登录</button>
                        <div align="right">
                            <a href="homepage/register.html">注册</a>
                            <a href="homepage/modify_password.html">忘记密码</a>
                        </div>
                    </div>
                </div>
                <br>
            </div>
        </div>
    </div>
</div>

<script>
    //登录
    function register(){
        //从服务器端返回判断是否存在这个用户的结果
        var str={
            "username":document.getElementById("account").value.toString(),
            "password":document.getElementById("password").value.toString(),
            "name":null,
            "level":0,
            "receivepro":[],
            "launchpro":[],
            "TaskNumber":0,
            "score":0.0
        };
        var changeobj=JSON.stringify(str);
        $.ajax({
            url : "/Servlet",
            type : "POST",
            data :{
                "gData":changeobj,
                "action":"login"
            },

            success: function(data) {
                //exist用来判断该用户是否存在
                var exist=0;

                //如果返回的值是true
                if(data=="true"){
                    exist=1;
                }

                //针对两种不同的返回值的反应
                if(exist==1){
                    if(document.getElementById("account").value.toString()=="admin"){
                        window.location.href="../admin/view_data.html";
                        location.href="../admin/view_data.html?"+"txt="+encodeURI(document.getElementById("account").value.toString());
                    }
                    else {
                        window.location.href="home_page_alreadyin.html";
                        location.href="home_page_alreadyin.html?"+"txt="+encodeURI(document.getElementById("account").value.toString());
                    }
                }
                else{
                    //清空后提示
                    document.getElementById("account").value="";
                    document.getElementById("password").value="";
                    alert("Invalid username or password!");
                }
            },
            error: function () {
                alert("Wrong!");
            }
        });
    }

</script>

</body>
</html>