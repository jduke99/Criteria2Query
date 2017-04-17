<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<title>微信按摩椅－临时页面</title>
<link rel="shortcut icon" href="../img/chair.ico">
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>

<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<!--[if lt IE 9]> 
           <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script> 
           <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script> 
       <![endif]-->

</head>
<script type="text/javascript">
    //添加用户
    function addData() {
        var form = document.forms[0];
        form.action = "${pageContext.request.contextPath}/bill/addbill";
        form.method = "post";
        form.submit();
    }
</script>

</head>
<body>
    <form>
        <table>
            <tr>
                <td>椅子编号</td>
                <td>
                    <input type="text" name="chairid">
                </td>
            </tr>
            <tr>
                <td>时长</td>
                <td>
                    <input type="text" name="runtime">
                </td>
            </tr>
             <tr>
                <td>开始时间</td>
                <td>
                    <input type="text" value="2016-12-27 17:57:36" name="starttime">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="button" value="提交" onclick="addData()">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>