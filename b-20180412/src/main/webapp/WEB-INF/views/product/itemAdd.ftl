<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<style>
.star-rating .caption{
    display: none!important;
}
</style>

<form id="addForm" method="post" class="form-horizontal" role="form" action="/productItem/doAddChapter">
    <div class="form-group">
        <input type="hidden" name="productId" value="${productId}">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="parentId">
                <option value="0">作为一级目录</option>
                ${chapterTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 中文序号 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="chapterId" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="pictureUrl" id="pictureUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pictureUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 重要程度 </label>
        <div class="col-sm-9">
            <input  value="0" type="number" name="importanceLevel" class="rating" min=0 max=10 step=1 data-size="xs">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1" checked disabled> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="0"  disabled> 否
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否免费 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="isFree" id="" value="1"> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="isFree" id="" value="0" checked> 否
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 排序 </label>
        <div class="col-sm-9">
            <input type="number" datatype="s" sucmsg="haha" id="form-field-3" name="listOrder" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

</form>

<link href="${rc.contextPath}/resources/js/starRating/star-rating.min.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });

    $('.rating').rating({
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
