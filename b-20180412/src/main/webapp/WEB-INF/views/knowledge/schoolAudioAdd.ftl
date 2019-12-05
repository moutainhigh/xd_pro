<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<style>
.forumCover
{
	width: 156px;
	height: 60px;
}
</style>
<form id="addForm" method="post" class="form-horizontal" role="form" action="/knowledge/school_audio/doAdd">
    <input type="hidden" name="forumType" value="4" />
    <input type="hidden" name="forumClassify" value="3" />
    <input type="hidden" name="authorId" value="1" />

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-xs-9">
            <input type="text" maxlength="20" datatype="s" sucmsg="haha"  name="forumTitle" id="form-field-2" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 封面 </label>
        <div class="col-xs-9">
        	<img class="forumCover" src="" alt="封面图片">
            <input type="hidden" datatype="s" sucmsg=" " id="forumCover" name="forumCover" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploaD('forumCover','picture',3,'jpg,jpeg')">上传</a>
        </div>
        <br/>
        <div class="col-xs-9" style="color:red">*建议上传200*200像素的jpg、png、gif图片</div>
    </div>
    
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 音频文件 </label>
        <div class="col-xs-9">
        	<audio class="forumMedia" src="" controls="controls" style="width:100%">您的浏览器不支持 audio 标签。</audio>
        	<input type="hidden" datatype="s" sucmsg=" " id="forumMedia" name="forumMedia" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploaD('forumMedia','picture',50,'mp3')">上传</a>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="forumContent" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

</form>

<script>
	function uploaD(id,scope,size,type){
		fileUploadCallBack(function(url) {
					$("."+id).attr("src",url);
					$("#"+id).val(url);
				},scope,size,type);
	}
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
