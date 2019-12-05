<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		订阅 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			订阅列表
		</small>
	</h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;"
	onsubmit="return check(this)">
	<div class="form-group">
		<select id="platformType" name="platformType" class="form-control">
			<option value="-1"<#if platformType ==
				'-1'>selected</#if>>请选择平台</option>
			<option value="3"<#if platformType ==
				'3'>selected</#if>>知乎专栏</option>
			<option value="1"<#if platformType ==
				'1'>selected</#if>>微信公众号</option>
			<option value="2"<#if platformType == '2'>selected</#if>>头条</option>
		</select> <input type="text" class="form-control" id="keyword" name="keyword"
			placeholder="专栏号/公众号/头条号" value="${keyword}" />
	</div>
	<button type="submit" class="btn btn-primary"
		style="border: 0px; width: 90px;">搜索</button>
</form>



<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>知乎专栏</th>
			<th>创建人</th>
			<th>被关注数</th>
			<th>文章</th>
			<th style="width: 250px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list dataObjList as dataObj>
		<tr>
			<td><a href="https://zhuanlan.zhihu.com/${dataObj.object.id}"
				target="_blank">${dataObj.highlight.title}</a></td>
			<td>${dataObj.object.author.name}</td>
			<td>${dataObj.object.followers}</td>
			<td>${dataObj.object.articlesCount}</td>
			<td>
				<#if dataObj.isSubscribe == '0'> 
					<a onclick="subscribe('${platformType }','${dataObj.object.id}')">订阅</a>
				<#else> 
					<a onclick="cancelSubscribe('${platformType }','${dataObj.object.id}')">取消订阅</a>
				</#if>
			</td>
		</tr>
		</#list>
	</tbody>
</table>
<script>
function cancelSubscribe(platformType,signature){
	// 订阅
    $.post("/subscribe/cancelSubscribe", { signature:signature, platformType:platformType},
            function(data){
    	        var result = JSON.parse(data);
                if(result.retCode != 1){
                    alert(result.retDesc);
                } else {
                    alert(result.retDesc);
                }
                location.reload();
            }
    );
}
function subscribe(platformType, signature){
	// 订阅
    $.post("/subscribe/doSubscribe", { signature:signature, platformType:platformType},
            function(data){
    	        var result = JSON.parse(data);
                if(result.retCode != 1){
                    alert(result.retDesc);
                } else {
                    alert(result.retDesc);
                }
                location.reload();
     });
}

function check(obj){  
	var platform = $("#platformType").val();
	if(platform == '-1') {
		alert("请选择平台");
		return false;
	}
	
	var keyword = $("#keyword").val();
	if(/^\s*$/.test(keyword)){
		alert("请输入专栏号/公众号/头条号");
		return false;
	}
	return true;
}  

</script>

</@htmlBody>