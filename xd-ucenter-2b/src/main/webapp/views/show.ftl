<!--2015/7/8-->
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <link href="${baseCssOP}/showNotice.css" rel="stylesheet"/>
    <title></title>
</head>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
<input type="hidden" id="type" value="${type}">
<#if (type==2)><!--data格式复杂可能会导致页面展示出错-->
	<input type="hidden" id ="data" value="${data}">
</#if>
<script type="text/javascript">
	if($("#type").val()=="2"){
		window.location.href=$("#data").val();
	}
</script>
<div class="main" id="stage">
    <div id="page">
        ${data}
    </div>
</div>
</div>
</body>
</html>