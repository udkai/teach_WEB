<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-基础字典管理</title>
<%@ include file="../common/header.jsp"%>
<style>
.option-input {
	width: 100% !important;
}
</style>
</head>

<body class="no-skin">
	<%@ include file="../common/top.jsp"%>
	<div class="main-container" id="main-container">
		<%@ include file="../common/menu.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs  breadcrumbs-fixed" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">基础数据管理</li>
						<li class="active">基础字典管理</li>
					</ul>
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="page-header">
						<h1>
							基础字典管理 <small><i class="ace-icon fa fa-angle-double-right"></i> 新增基础字典 </small>
						</h1>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<form id="dicForm" name="dicForm" class="form-horizontal" action="add.htm" method="post">
								<input type="hidden" name="backUrl" value="${backUrl }">
								<div class="form-group">
									<label class="col-sm-3 control-label">字典类型：</label>
									<div class="col-sm-9 ">
										<select name="type" class="col-xs-10 col-sm-5" id="type">
											<option value="">请选择</option>
											<option value="1">文化程度</option>
											<option value="2">职务等级</option>
											<option value="3">军衔</option>
											<option value="4">技术等级</option>
										</select> <label id="typeTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">编号：</label>
									<div class="col-sm-9">
										<input id="code" name="code" type="text" class="col-xs-10 col-sm-5" placeholder="" value=""> <label
											id="codeTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">名称：</label>
									<div class="col-sm-9">
										<input id="name" name="name" type="text" class="col-xs-10 col-sm-5" placeholder="" value=""> <label
											id="nameTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">排序：</label>
									<div class="col-sm-9">
										<input id="display_order" name="display_order" type="text" class="col-xs-10 col-sm-5" placeholder="" value="">
										<label id="display_orderTip"></label>
									</div>
								</div>

								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<button class="btn btn-primary" type="submit">
											<i class="ace-icon fa fa-save bigger-110"></i> 保存
										</button>
										<button class="btn" type="button" onclick="history.back(-1)">
											<i class="ace-icon fa fa-undo bigger-110"></i> 取消
										</button>
									</div>
								</div>
							</form>

						</div>
					</div>
					<!-- /.main-content -->
				</div>
				<!-- /.main-container -->
				<%@ include file="../common/js.jsp"%>

				<script type="text/javascript">
					$(document).ready(function() {
						$("#dicForm").validate({
							//debug : true,
							errorElement : "label",
							errorClass : "valiError",
							errorPlacement : function(error, element) {
								error.appendTo($("#" + element.attr('id') + "Tip"));
							},
							rules : {
								type : {
									required : true
								},
								code : {
									required : true
								},
								name : {
									required : true
								},
								display_order : {
									number : true,
									required : true
								}
							},
							messages : {
								code : {
									remote : "编号已存在！"
								}
							},
							submitHandler : function() {
								form.submit();
							}
						});
					});
				</script>
</body>
</html>
