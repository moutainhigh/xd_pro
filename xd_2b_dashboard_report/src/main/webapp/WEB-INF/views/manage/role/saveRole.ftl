<div class="modal fade" id="save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增角色</h4>
            </div>
        <section class="content">
				<div class="row">
					<div class="col-sm-12">
						<!-- BLANK PAGE-->
						<div style="margin: -20px 15px;" class="nest" id="Blank_PageClose">
							<div class="body-nest" id="Blank_Page_Content">
								<div class="row">
									<!-- edit form column -->
									<div class="col-md-12 personal-info">
										<form id="saveForm" class="form-horizontal" role="form">
											<div class="form-group">
												<label class="col-lg-3 control-label">角色名称:</label>
												<div class="col-lg-8">
													<input id="save_roleName" name="roleName" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">角色描述:</label>
												<div class="col-lg-8">
													<textarea id="save_description" name="description" class="form-control" style="resize: none;"></textarea>
												</div>
											</div>
											<div class="form-group">
			                                    <label class="control-label col-md-3">是否有效:</label>
				                                    <div class="col-md-9">
				                                    	<label><input name="validStatus" type="radio" checked="checked" value="0"/>有效 </label> 
														<label><input name="validStatus" type="radio" value="1"/>无效 </label> 
				                                    </div>
			                                </div>
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" id="saveRole" class="btn btn-primary" >确定</button>
				            				</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- END OF BLANK PAGE -->
        <!-- /.row -->
       </section>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>