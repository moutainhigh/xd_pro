<#include "/common/form.ftl">
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="full-screen" content="yes" />
<meta name="screen-orientation" content="portrait" />
<meta name="x5-fullscreen" content="true" />
<meta name="360-fullscreen" content="true" />
<title>编辑器</title>
<style>
.portrait {
	width: 60px;
	height: 60px;
}
.portrait1 {
	width: 60px;
	height: 60px;
}
.portrait2 {
	width: 60px;
	height: 60px;
}
</style>
<link rel="stylesheet" href="/resources/ruide/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/ruide/css/fileinput.min.css"
	media="all" />
<link rel="stylesheet" href="/resources/ruide/css/simditor.css" />
<link rel="stylesheet" href="/resources/ruide/css/simditor-mention.css" />
<link rel="stylesheet" href="/resources/ruide/css/simditor-html.css" />
<link rel="stylesheet" href="/resources/ruide/css/metro.css" />
<link rel="stylesheet" href="/resources/ruide/css/process.css">
<link rel="stylesheet" href="/resources/ruide/css/output.css">
<link rel="stylesheet" href="/resources/ruide/css/uploadImage.css">
<link rel="stylesheet" href="/resources/ruide/css/index-base.css">
<link rel="stylesheet" href="/resources/ruide/css/index-left.css">
<link rel="stylesheet" href="/resources/ruide/css/index-mid.css">
<link rel="stylesheet" href="/resources/ruide/css/index-right.css">
<link rel="stylesheet"
	href="${rc.contextPath}/resources/assets/css/mycss/ace.min.css" />
<link rel="stylesheet"
	href="${rc.contextPath}/resources/assets/css/mycss/font-awesome.min.css" />
<script type="text/javascript"
	src="/resources/ruide/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/util.js"></script>
<script type="text/javascript" src="/resources/ruide/js/jquery.form.js"></script>
<script type="text/javascript"
	src="/resources/ruide/js/xd.common-1.0.0.js"></script>
<script type="text/javascript"
	src="/resources/ruide/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/resources/ruide/js/bootstrap-typeahead.js"></script>
<script type="text/javascript" src="/resources/ruide/js/TweenMax.min.js"></script>
<script type="text/javascript"
	src="/resources/ruide/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
	src="/resources/ruide/js/beautify-html.js"></script>
<script type="text/javascript" src="/resources/ruide/simditor/module.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/hotkeys.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/uploader.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/simditor.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/simditor-mention.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/simditor-html.js"></script>
<script type="text/javascript" src="/resources/ruide/simditor/upload.js"></script>
<script type="text/javascript"
	src="/resources/ruide/simditor/myatbutton.js"></script>
<!-- <script type="text/javascript" src="/resources/ruide/js/index.js"></script> -->
<script
	src="${rc.contextPath}/resources/js/artdialog/artDialog.js?skin=blue"></script>
<script
	src="${rc.contextPath}/resources/js/artdialog/plugins/iframeTools.js"></script>

<script src="/resources/laydate/laydate.js"></script>
</head>
<body>
	<div class="mainBody">
		<div class="right">
			<form name="form1" id="editForm" method="post" class="form-horizontal" role="form"
				action="/student/doEdit">

				<input type="hidden" name="id" value="${student.id}" />

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1"> 学员姓名 </label>
					<div class="col-sm-9">
						<input type="text" datatype="s" sucmsg="haha" id="name"
							name="author" value="${student.author}" id="form-field-2"
							placeholder="" class="col-xs-10 col-sm-5" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-3"> 学员头像 </label>
					<div class="col-sm-9">
						<img class="portrait1" src="${student.portrait}" alt="学员头像">
						<input type="hidden" value="${student.portrait}" datatype="s" sucmsg="haha" id="pic1"
							name="portrait" placeholder="" class="col-xs-10 col-sm-5" /> <a
							onclick="uploadProtrait1()">上传</a>
					</div>
					<br/>
					<div class="col-sm-9" style="color: red">*建议上传100*100像素的jpg、png、gif图片</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">相关专业</label>
					<div class="col-sm-9">
						<select id="majorId" name="majorId" class="form-control">
							<#list professionList as profession>
							<option value="${profession.id}"<#if student.majorId ==
								profession.id>selected</#if> >${profession.title}</option> </#list>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1"> 发布时间 </label>
					<div class="col-sm-9">
						<div class="inline layinput">
							<input name="publishTime" value="${student.publishTime}"
								style="width: 285px; height: 34px;" class="laydate-icon"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1"> 感悟简介 </label>
					<div class="col-sm-9">
						<input type="text" datatype="s" sucmsg="haha" id="name"
							value="${student.thinkDesc}" name="thinkDesc" id="form-field-2"
							placeholder="" class="col-xs-10 col-sm-5" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-3"> 正文图片（可选） </label>
					<div class="col-sm-9">
						<img class="portrait2" src="${student.contentImage}" alt="正文图片">
						<input type="hidden" datatype="s" sucmsg="haha"
							value="${student.contentImage}" id="pic2" name="contentImage"
							placeholder="" class="col-xs-10 col-sm-5" /> <a
							onclick="uploadProtrait2()">上传</a>
					</div>
					<br />
					<div class="col-sm-9" style="color: red">*建议上传576*280像素的jpg、png、gif图片</div>
				</div>

				<div class="operationArea">
					<ul class="choose">
						<li class="richText on" data-type="richText">文本</li>
					</ul>
					<div class="rightMain">
						<div class="richText-box">
							<textarea id="editor" name="content" placeholder="这里输入内容"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div id="tempStore" style="display: none;">${student.content}</div>
	</div>
	<script>
		$(function() {

			var editor = new Simditor({
				textarea : $('#editor'),
				toolbar : [ 'title', 'bold', 'italic', 'underline',
						'strikethrough', 'color', 'table', 'link', 'hr', 'ol',
						'html' ],
				toolbarFloat : true,
				toolbarFloatOffset : 2
			});

			function out() {
				this.initRight = function() {
					$(".rightMain").children().hide();
					$(".richText-box").show();
					$(".richText").addClass('on');

					$("#editor").val($('#tempStore').html());
					$(".simditor-body").html($('#tempStore').html()); 
					/* $("#editor").val('${student.content}');
					$(".simditor-body").html('${student.content}');  */
				}
			}

			var _out = new out();
			_out.initRight();
			window.mainjs = _out;

		});
		function uploadProtrait1() {
			fileUploadCallBack(function(url) {
				$(".portrait1").attr("src", url);
				$("#pic1").val(url);
			}, 'picture', 3, 'png,jpg,gif');
		}
		function uploadProtrait2() {
			fileUploadCallBack(function(url) {
				$(".portrait2").attr("src", url);
				$("#pic2").val(url);
			}, 'picture', 3, 'png,jpg,gif');
		}
	</script>
	<@fileUpload></@fileUpload>
</body>
</html>