<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${tutor.author }：${tutor.title}_专业导师_瑞德在线</title>
<meta name="description" content="${tutor.htmlContent}">
<link href="${baseImgOP}/rd-ico.png" rel="icon">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/base.css">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/style.css">
<script type="text/javascript" src="${baseJsOP}/jquery-1.7.min.js"></script>
<!--jq1.7Release-->
<script type="text/javascript" src="${baseJsOP}/service.js"></script>
<script type="text/javascript" src="${baseJsOP}/service-main.js"></script>
<script type="text/javascript" src="${baseJsOP}/thumbs.js"></script>
</head>
<body>
	<!--header-->
	<div class="top-wrap">
		<div class="top">
			<!--top logo nav-->
			<div class="top-left">
				<!--top logo-->
				<a class="logo" href="/" title="">
					<img alt="" src="${baseImgOP}/rd-logo.png" width="155" height="45">
				</a>
				<!--top nav-->
				<div class="nav">
					<ul>
						<li><a href="/">首页</a></li>
						<li><a href="/zyds"<#if (tutor.type =
								1)> class="active" </#if>>专业导师</a></li>
						<li><a href="/zyjs"<#if (tutor.type =
								2)> class="active" </#if>>专业介绍</a></li>
						<li><a href="/xxhd">线下活动</a></li>
					</ul>
				</div>
			</div>
			<!--top phone-->
			<div class="top-right">
				<div class="phone-logo">
					<img src="${baseImgOP}/phone.png" width="172" height="20">
				</div>
			</div>
		</div>
	</div>
	<!--content-->
	<div class="content-wrap">
		<div class="content">
			<div class="w1002">
				<div class="cont-left">
				<div class="bread">当前位置：<a href="/">首页</a>>><a href="/zyds">专业导师</a>>><span>${tutor.author }：${tutor.title}</span></div>
				<#if tutor??>
					<div class="detail-content">
						<h1 class="center-h2">${tutor.author }</h1>
						<h3 class="center-h3">(${tutor.title })</h3>
						<div class="detail-info detail-info-r">
							<p>
								<span>作者：瑞德在线  发布时间:
									${tutor.publishTime?string("yyyy-MM-dd") } </span>
							</p>
						</div>
						<#if (tutor.contentImage)??> 
							<div class="detail-imgage">
								<img src="${tutor.contentImage }" alt="${tutor.author }：${tutor.title}"> 
							</div>
						</#if> 
						<div class="detail-img">${tutor.content }</div>
						<p class="zan">
							<span>阅读(${tutor.readQuantity }) </span><span><a
								id="thumbsId" href="javascript:void(0)"
								onclick="doThumbs('${tutor.id}','${tutor.thumbNums}','1')">赞(${tutor.thumbNums})</a></span>
						</p>
					</div>
					<#else>
						<img alt="no found" style="width:100%"" src="${baseImgOP}/404.png">
					</#if>
				</div>
				<div class="cont-right stu-cl">
					<div class="stu-gnosol cont-article">
						<h3>学员感悟</h3>
					</div>
					<#if (stuList?size > 0) > <#list stuList as stu>
					<div class="cont-article-list stu-gnosol-list">
						<a href="/xygw/${stu.id }.html">
							<p class="stu-info">
								<img src="${stu.portrait}"><span>${stu.author}</span><span>${stu.tutorMajorModel.title}</span>
							</p>
							<p class="stu-mes">感悟:${stu.thinkDesc}</p>
							<p class="stu-time">${stu.publishTime?string("yyyy-MM-dd")}</p>
						</a>
					</div>
					</#list> <#else>
					<div class="none-date">已经没有数据了。</div>
					</#if>
				</div>
			</div>
		</div>
	</div>

	<!--footer-->
	<div class="footer-wrap">
		<div class="footer-son">
			<div class="w1002">
				<div class="org-introduce-son">
					<h2 class="footer-h2 organization">机构介绍</h2>
					<p>瑞德机构—专注于职业教育，学历教育的互联网教育公司。</p>
					<p>瑞德机构的培训课程和服务范围广阔，提供全国精品直播课程、录播课程、面授课程等，业务涵盖上班族学历 MBA 注册会计师
						教师资格证等</p>
				</div>
				<div class="cnzz">
					<p>www.51readee.com 2015-2018 © All Rights Reserved.</p>
					<p>
						经营许可证编号：京ICP备17039644号
						<script type="text/javascript">
							var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
									: " http://");
							document
									.write(unescape("%3Cspan id='cnzz_stat_icon_1262291076'%3E%3C/span%3E%3Cscript src='"
											+ cnzz_protocol
											+ "s22.cnzz.com/z_stat.php%3Fid%3D1262291076%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
						</script>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!--在线客服-->
	<div class="main-im">
		<div id="open_im"></div>
	</div>
	<!-- 回到顶部 -->
	<div id="elevator_item">
		<a id="elevator" onclick="return false;" title="回到顶部"></a>
	</div>
</body>
<script type="text/javascript" src="${baseJsOP}/top.js"></script>
</html>