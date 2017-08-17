<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-会议管理</title>
<%@ include file="../common/header.jsp"%>
<link href="${staticServer }/assets/zTree/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
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
						<li class="active">办公自动化管理</li>
						<li class="active">会议管理</li>
					</ul>
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="page-header">
						<h1>
							会议管理 <small><i class="ace-icon fa fa-angle-double-right"></i> 修改会议 </small>
						</h1>
					</div>
					<!-- /.page-header -->

					<div class="column">
						<div class="col-xs-12">
							<form id="moduleForm" name="moduleForm" class="form-horizontal" action="update.htm" method="post">
								<input type="hidden" name="backUrl" value="${backUrl }"> <input type="hidden" name="id"
									value="${oaMeeting.id }">
								<div class="form-group">
									<label class="col-sm-3 control-label">标题：</label>
									<div class="col-sm-9">
										<input id="title" name="title" type="text" class="col-xs-10 col-sm-5" placeholder=""
											value="${oaMeeting.title}" maxlength="30"> <label id="titleTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">内容：</label>
									<div class="col-sm-9">
										<textarea id="content" name="content" type="text" class="col-xs-10" placeholder="" maxlength="500" rows="10">${oaMeeting.content}</textarea>
										<label id="contentTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">会议地点：</label>
									<div class="col-sm-9">
										<input id="place" name="place" type="text" class="col-xs-10 col-sm-5" placeholder=""
											value="${oaMeeting.place}" maxlength="30"> <label id="placeTip"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">会议日期：</label>
									<div class="col-sm-9">
										<input id="date" name="date" type="text" class="col-xs-10 col-sm-5" placeholder="" value="${oaMeeting.date}"
											maxlength="30"> <label id="dateTip"></label>
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
						$("#moduleForm").validate({
							debug : true,
							errorElement : "label",
							errorClass : "valiError",
							errorPlacement : function(error, element) {
								error.appendTo($("#" + element.attr('id') + "Tip"));
							},
							rules : {
								title : {
									required : true,
									maxlength : 30
								},
								content : {
									required : true,
									maxlength : 500
								}
							},
							messages : {},
							submitHandler : function(form) {
								form.submit();
							}
						});
					});
				</script>
</body>
</html>
