<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-教员档案管理</title>
<%@ include file="../common/header.jsp"%>
	<style>
		.overlay-text{
			text-align: center;
			color: white;
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
						<li class="active">教员档案管理</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box widget-color-blue">
								<!-- #section:custom/widget-box.options -->
								<div class="widget-header">
									<h5 class="widget-title bigger lighter">
										<i class="ace-icon fa fa-table"></i> 操作面板
									</h5>
								</div>

								<!-- /section:custom/widget-box.options -->
								<div class="widget-body">
									<div class="widget-main">
										<table class="searchField" style="margin: 4px; padding: 4px;">
											<tr>

												<td>姓名：</td>
												<td><input type="text" id="txtName" class="form-control input-small"
														   placeholder=""
														   value="${baseUserInfoSearchVO.name }"></td>
												<td>军官证号：</td>
												<td><input type="text" id="txtCode" class="form-control input-small"
														   placeholder=""
														   value="${baseUserInfoSearchVO.certificate_code }"></td>
												<td>
													<button class="btn btn-primary btn-sm" id="btnSearch">
														<i class="ace-icon fa fa-search"></i> 查询
													</button>
													<c:if test="${bln:isP('BaseTeacherInfoAdd')}">
														<button type="button" class="btn btn-success btn-sm" id="btnAdd">
															<i class="ace-icon fa fa-plus bigger-110"></i>新增
														</button>
													</c:if>
													<a href="#teacher-modal" class="btn btn-warning btn-sm" data-backdrop="static"
													   data-toggle="modal">
														<i class="ace-icon fa fa-file-excel-o"></i>导入</a>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<table id="treeTable" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th width=60>序号</th>
										<th >姓名</th>
										<th width=120>军官证号</th>
										<th width=120>部别</th>
										<th width=120>手机</th>
										<th width=120>军衔</th>
										<th width=120>类型</th>
										<th width="241">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${baseTeacherInfoList }" var="baseTeacherInfo" varStatus="st">
										<tr>
											<td>${st.index+1}</td>
											<td><a href="toView.htm?id=${baseTeacherInfo.id}&backUrl=${backUrl}">${baseTeacherInfo.name}</a></td>
											<td>${baseTeacherInfo.certificate_code}</td>
											<td>${baseTeacherInfo.department_level}</td>
											<td>${baseTeacherInfo.telephone}</td>
											<td>${baseTeacherInfo.ranks}</td>
											<td>
												<c:if test="${baseTeacherInfo.type == 2}"><span class="label label-success">教务管理员</span></c:if>
												<c:if test="${baseTeacherInfo.type == 3}"><span class="label label-primary">上课教员</span></c:if>
											</td>
											<td><c:if test="${bln:isP('BaseTeacherInfoUpdate')}">
													<a href="toUpdate.htm?id=${baseTeacherInfo.id}&backUrl=${backUrl}"> 修改</i>
													</a>
												</c:if> <c:if test="${bln:isP('BaseTeacherInfoDelete')}">
													<a href="javascript:delModule(${baseTeacherInfo.id });"> 删除 </a>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- /.span -->
					</div>

					<div class="row">
						<div class="col-xs-12">${ pageNavigate.pageModel}</div>
					</div>
				</div>
				<!-- /.main-content -->
			</div>
			<div id="teacher-modal" class="modal fade" tabindex="-21">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								导入教员档案
							</div>
						</div>
						<div class="modal-body no-padding">
							<div class="widget-box" style="border: none">
								<div class="widget-body">
									<div class="widget-main">
										<div class="form-group">
											<div class="col-xs-12" style="margin-bottom: 10px">
												<span>请下载模板，按照模板格式整理数据：</span>
												<a href="<c:url value="/assets/templates/import-teacherinfo-template.xls"/>" target="_blank">导入教员档案模板.xls</a>
											</div>
										</div>
										<c:url value="/base/teacherinfo/importTeacherInfo.htm" var="import_url"/>
										<form:form action="${import_url}"
												   enctype="multipart/form-data" method="post" id="teacher-upload-form">
											<input type="hidden" name="file_type" value="teacher">

											<div class="form-group">
												<div class="col-xs-12">
													<input name="excel_file" type="file" id="teacher-import-input"
														   accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
												</div>
												<div class="col-xs-12" align="center">
													<button class="btn btn-white btn-primary" type="submit">
														<i class="ace-icon fa fa-cloud-upload bigger-110"></i> 确定
													</button>
													<button class="btn btn-white btn-primary" type="button"
															id="closeicon-modal"
															data-dismiss="modal">
														<i class="ace-icon fa fa-undo bigger-110"></i> 取消
													</button>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer no-margin-top"></div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<div id="import-result-modal" class="modal fade" tabindex="-21">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								导入结果
							</div>
						</div>
						<div class="modal-body no-padding">
							<div class="widget-box" style="border: none">
								<div class="widget-body">
									<div class="widget-main">
										<div class="form-group">
										</div>
										<form:form action="" id="import-result-form">
											<div class="form-group">
												<div class="col-xs-12" id="result-text">
												</div>
												<div class="col-xs-12" align="center">
													<button class="btn btn-white btn-primary" type="button" id="result-btn">
														<i class="ace-icon fa fa-check bigger-110"></i> 确认
													</button>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer no-margin-top"></div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.main-container -->
			<%@ include file="../common/js.jsp"%>
			<script src="<c:url value="/assets/js/jquery.form.js"/>"></script>

			<script type="text/javascript">
				$(function() {
					$("#btnSearch").bind('click', searchModule);
					$("#btnAdd").bind('click', addUser);

                    var $teacher_import_input = $('#teacher-import-input');
                    $teacher_import_input.ace_file_input({
                        style: 'well',
                        btn_choose: '点击选择Excel文件',
                        btn_change: null,
                        no_icon: 'ace-icon fa fa-file-excel-o',
                        droppable: false,
                        thumbnail: 'small',//large | fit
                        maxSize: 10240000,
                        allowExt: ['xls', 'xlsx', 'xlt', 'xlw', 'xlc', 'xlm'],
                        allowMime: ['application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'],
                        before_remove: function () {
                            return true;
                        }

                    }).on('change', function () {
                        if ($teacher_import_input.val() == '') {
                            $teacher_import_input.ace_file_input('reset_input');
                            return false;
                        }
                    }).on('file.error.ace', function (ev, info) {
                        if (info.error_count['ext'] || info.error_count['mime']) {
                            $.notify("请选择Excel文件!");
                            return false;
                        }
                        if (info.error_count['size']) {
                            $.notify("文件不超过100M!");
                            return false;
                        }
                    });

                    var teacher_upload_form = $("#teacher-upload-form");
                    //导入提交
                    teacher_upload_form.on('submit', function () {
                        var upload_check = $teacher_import_input.val();
                        if (upload_check == '') {
                            $teacher_import_input.ace_file_input('reset_input');
                            $.notify("请选择文件!");
                            return false;
                        }
                        $teacher_import_input.ace_file_input('loading', true);
                        $(".ace-file-overlay").append('<div class="overlay-text">正在导入中...</div>');
                        teacher_upload_form.ajaxSubmit({
                            type: 'post', // 提交方式 get/post
                            url: teacher_upload_form.attr('action'),
                            success: function (result) {
                                var dataObj = eval("(" + result + ")");
                                console.log(dataObj)
                                console.log(dataObj.success)

                                $teacher_import_input.ace_file_input('loading', false);
                                var msg = '';
                                if (dataObj.success) {
                                    $("#teacher-modal").modal("hide");  //关闭上传窗口
                                    $teacher_import_input.val('');
                                    $teacher_import_input.ace_file_input('reset_input');

                                    msg = '导入成功';
                                    $("#result-text").html(msg);
                                    $("#import-result-modal").modal("show");
//
                                    //$.notify(msg);
                                } else {
                                    $("#teacher-modal").modal("hide");   //关闭上传窗口
                                    $teacher_import_input.val('');
                                    $teacher_import_input.ace_file_input('reset_input');
                                    $teacher_import_input.ace_file_input('loading', false);

                                    msg = dataObj.msgText + '<br/>' + dataObj.errorInfo;
                                    $("#result-text").html(msg);
                                    $("#import-result-modal").modal("show");

                                }
                            }
                        });
                        return false;
                    });
                    $("#result-btn").on('click', function () {
                        $("#import-result-modal").modal("hide");
                        $("#btnSearch").click();
                    })
				})

				// 查询方法
				var searchModule = function() {
                    var url = "index.htm?___=_";

                    if ($("#txtName").val() != '')
                        url += "&name=" + $("#txtName").val();

                    if ($("#txtCode").val() != '')
                        url += "&certificate_code=" + $("#txtCode").val();
					window.location = encodeURI(url);
				}
				// 删除
				var delModule = function(id) {
					bootbox.confirm("<h4 class='red'>你确定要删除该教员档案吗？</h4>", function(result) {
						if (result) {
							window.location = "delete.htm?id=" + id + "&backUrl=${backUrl}";
						}
					})
				}
				//新增
				var addUser = function(id) {
					window.location = 'toAdd.htm?backUrl=${backUrl }';
				}
			</script>
</body>
</html>
