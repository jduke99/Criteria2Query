<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>

  <head>
    
    <meta charset="utf-8">
    <title>ELIIE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Bootstrap是Twitter推出的一个用于前端开发的开源工具包。它由Twitter的设计师Mark Otto和Jacob Thornton合作开发，是一个CSS/HTML框架。目前，Bootstrap最新版本为3.0 。Bootstrap中文网致力于为广大国内开发者提供详尽的中文文档、代码实例等，助力开发者掌握并使用这一框架。">
    <meta name="keywords" content="Bootstrap,CSS,CSS框架,CSS framework,javascript,bootcss,bootstrap开发,bootstrap代码,bootstrap入门">
    <meta name="author" content="Bootstrap中文网">
    <meta name="robots" content="index,follow">
    <meta name="application-name" content="bootcss.com">
    <meta property="qc:admins" content="1603466166416277433363757477167" />
    
    <!-- Site CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://static.bootcss.com/www/assets/css/site.min.css?v5" rel="stylesheet">
    <style>
    .job-hot {
    	position: absolute;
    	color: #d9534f;
    	right: 0;
    	top: 15px;
    }
    .eliback{
    	color:white;
    	background-color:#1E90FF;
    	position: relative;
    }
     .navback{
    	
    	background-color:#000000;
    	
    }
    .gradient{
    background: #000000;
    background: -moz-linear-gradient(top,  #000000 0%, #1E90FF 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#000000), color-stop(100%,#1E90FF));
    background: -webkit-linear-gradient(top,  #000000 0%,#1E90FF 100%);
    background: -o-linear-gradient(top,  #000000 0%,#1E90FF 100%);
    background: -ms-linear-gradient(top,  #000000 0%,#1E90FF 100%);
    background: linear-gradient(to bottom,  #000000 0%,#1E90FF 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#000000', endColorstr='#1E90FF',GradientType=0 );
}
    </style>
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- Favicons -->
    <link rel="apple-touch-icon-precomposed" href="https://static.bootcss.com/www/assets/ico/apple-touch-icon-precomposed.png">
    <link rel="shortcut icon" href="https://static.bootcss.com/www/assets/ico/favicon.png">
    
    <script>
      var _hmt = _hmt || [];
    </script>
    
    <link rel="canonical" href="http://www.bootcss.com/" />
    <script>
    var qqgroup = '318630708';
    </script>
    
  </head>
  <body>

    <div class="navbar navbar-inverse navbar-fixed-top navback">
      <div class="container">
        <div class="navbar-header">
          <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand hidden-sm" href="http://www.bootcss.com" onclick="_hmt.push(['_trackEvent', 'navbar', 'click', 'navbar-首页'])">EliIE</a>
        </div>
        <div class="navbar-collapse collapse" role="navigation">
          <ul class="nav navbar-nav">
          
          </ul>
          <ul class="nav navbar-nav navbar-right hidden-sm">
            <li><a href="/about/" onclick="_hmt.push(['_trackEvent', 'navbar', 'click', 'about'])">About</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="gradient masthead">
      <div class="container">
        <h1>EliIE</h1>
        <h3>Eligibility Criteria Information Extraction system, An Open-Source Information Extraction System for Clinical Trial Eligibility Criteria</h3>
        <p class="masthead-button-links">
          <a class="btn btn-lg btn-success" href="http://v3.bootcss.com/" target="_blank" role="button" onclick="_hmt.push(['_trackEvent', 'masthead', 'click', 'masthead-Bootstrap3中文文档'])">&nbsp;&nbsp;Start&nbsp;&nbsp;</a>
        </p>
      </div>
    </div>


    <div class="container projects">

      <div class="projects-header page-header">
        <h2>EliIE Application</h2>
        <p>All these application are based on EliIE</p>
      </div>

      <div class="row">

        <div class="col-sm-6 col-md-4 col-lg-4 ">
          <div class="thumbnail">
            <a href="http://codeguide.bootcss.com" title="Bootstrap 编码规范" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'codeguide'])"><img class="lazy" src="https://static.bootcss.com/www/assets/img/null.png?v2" width="300" height="150" data-src="https://static.bootcss.com/www/assets/img/codeguide.png" alt="Headroom.js"></a>
            <div class="caption">
              <h3>
                <a href="http://codeguide.bootcss.com" title="Bootstrap 编码规范：编写灵活、稳定、高质量的 HTML 和 CSS 代码的规范。" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'codeguide'])">Bootstrap 编码规范<br><small>by @mdo</small></a>
              </h3>
              <p>
              Bootstrap 编码规范：编写灵活、稳定、高质量的 HTML 和 CSS 代码的规范。
              </p>
            </div>
          </div>
        </div>


        <div class="col-sm-6 col-md-4 col-lg-4 ">
          <div class="thumbnail">
            <a href="https://yarn.bootcss.com/" title="Yarn 中文文档" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'Yarn'])"><img class="lazy" src="https://static.bootcss.com/www/assets/img/null.png?v2" width="300" height="150" data-src="https://static.bootcss.com/www/assets/img/yarn.png" alt="Yarn 中文文档"></a>
            <div class="caption">
              <h3>
                <a href="https://yarn.bootcss.com/" title="Yarn 中文文档" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'Yarn'])">Yarn <br><small>中文手册</small></a>
              </h3>
              <p>
              Yarn 是一个快速、可靠、安全的依赖管理工具。是 NPM 的替代品。
              </p>
            </div>
          </div>
        </div>


        <div class="col-sm-6 col-md-4 col-md-4 col-lg-4 ">
          <div class="thumbnail">
            <a href="http://www.bootcdn.cn/" title="Bootstrap中文网开放CDN服务" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'bootcdn'])"><img class="lazy" src="https://static.bootcss.com/www/assets/img/null.png?v2" width="300" height="150" data-src="https://static.bootcss.com/www/assets/img/bootcdn.png" alt="BootCDN"></a>
            <div class="caption">
              <h3>
                <a href="http://www.bootcdn.cn/" title="Bootstrap中文网开放CDN服务" target="_blank" onclick="_hmt.push(['_trackEvent', 'tile', 'click', 'bootcdn'])">BootCDN<br><small>开放CDN服务</small></a>
              </h3>
              <p>Bootstrap中文网联合又拍云存储共同推出了开放CDN服务 - BootCDN，我们对广泛的前端开源库提供了稳定的存储和带宽的支持，例如Bootstrap、jQuery等。
              </p>
            </div>
          </div>
        </div>






</div>
</div><!-- /.container -->


    <footer class="footer ">
      <div class="container">
        <div class="row footer-top">
          <div class="col-sm-6 col-lg-6">
            <h4>
              <img src="https://static.bootcss.com/www/assets/img/logo.png">
            </h4>
            <p>本网站所列开源项目的中文版文档全部由<a href="http://www.bootcss.com/">Bootstrap中文网</a>成员翻译整理，并全部遵循 <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">CC BY 3.0</a>协议发布。</p>
          </div>
          <div class="col-sm-6  col-lg-5 col-lg-offset-1">
            <div class="row about">
              <div class="col-xs-3">
                <h4>关于</h4>
                <ul class="list-unstyled">
                  <li><a href="/about/">关于我们</a></li>
                  <li><a href="/ad/">广告合作</a></li>
                  <li><a href="/links/">友情链接</a></li>
                  <li><a href="/hr/">招聘</a></li>
                </ul>
              </div>
              <div class="col-xs-3">
                <h4>联系方式</h4>
                <ul class="list-unstyled">
                  <li><a href="http://weibo.com/bootcss" title="Bootstrap中文网官方微博" target="_blank">新浪微博</a></li>
                  <li><a href="mailto:admin@bootcss.com">电子邮件</a></li>
                </ul>
              </div>
              <div class="col-xs-3">
                <h4>旗下网站</h4>
                <ul class="list-unstyled">
                  <li><a href="http://www.golaravel.com/" target="_blank">Laravel中文网</a></li>
                  <li><a href="http://www.ghostchina.com/" target="_blank">Ghost中国</a></li>
                </ul>
              </div>
              <div class="col-xs-3">
                <h4>赞助商</h4>
                <ul class="list-unstyled">
                  <li><a href="http://www.ucloud.cn/" target="_blank">UCloud</a></li>
                  <li><a href="https://www.upyun.com" target="_blank">又拍云</a></li>
                </ul>
              </div>
            </div>
    
          </div>
        </div>
        <hr/>
        <div class="row footer-bottom">
          <ul class="list-inline text-center">
            <li><a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备11008151号</a></li><li>京公网安备11010802014853</li>
          </ul>
        </div>
      </div>
    </footer>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/unveil/1.3.0/jquery.unveil.min.js"></script>
    <script src="https://cdn.bootcss.com/scrollup/2.4.1/jquery.scrollUp.min.js"></script>
    <script src="https://cdn.bootcss.com/toc/0.3.2/toc.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.matchHeight/0.7.0/jquery.matchHeight-min.js"></script>
    <script src="https://static.bootcss.com/www/assets/js/site.min.js"></script>
    <script>
      $(document).ready(function(){
          $('#qqgroup').text(qqgroup);
      });
    </script>
    

   
    
    <!-- 上面是 www.bootcss.com 网站所使用的统计代码，如果你使用本页面作为自己的模板，请将上面面的统计代码删掉！！！ -->  </body>
</html>
