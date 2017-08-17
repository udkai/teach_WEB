<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-试卷管理</title>
<%@ include file="../common/header.jsp"%>
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
						<li class="active">在线考试管理</li>
						<li class="active">试卷管理</li>
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

												<td>名称：</td>
												<td><input type="text" id="txtTitle" class="form-control"
														   placeholder=""
														   value="${examPaperSearchVO.title }"></td>

												<td>课程：</td>
												<td>
													<select id="cmbCourse" type="text" class="form-control">
														<option value="">--选择--</option>
														<c:forEach items="${listCourse}" var="course">
															<option value="${course.value}"<c:if test="${examPaperSearchVO.course_id == course.value}">selected</c:if> >${course.content}</option>
														</c:forEach>
													</select>
												</td>

												<td>班级：</td>
												<td>
													<select id="cmbClass" type="text" class="form-control">
													<option value="">--选择--</option>
													<c:forEach items="${listClass}" var="classid">
														<option value="${classid.value}"<c:if test="${examPaperSearchVO.class_id == classid.value}">selected</c:if> >${classid.content}</option>
													</c:forEach>
												</select>
												</td>
												<td>审核状态：</td>
												<td>
													<select id="cmbStatus" type="text" class="form-control">
														<option value="">--选择--</option>
														<option value="10">已出卷未选题</option>
														<option value="20">已选题待审核</option>
														<option value="30">已审核通过</option>
														<option value="40">审核不通过</option>
													</select>
												</td>
												<td>
													<button class="btn btn-primary btn-sm" id="btnSearch">
														<i class="ace-icon fa fa-search"></i> 查询
													</button>
													<c:if test="${bln:isP('ExamPaperAdd')}">
														<button type="button" class="btn btn-success btn-sm" id="btnAdd">
															<i class="ace-icon fa fa-plus bigger-110"></i>新增
														</button>
													</c:if>
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
							<table id="treeTable" class="table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th width=40>#</th>
										<th width=120>试卷名称</th>
										<th width=220>课程</th>
										<th width=120>班级</th>
										<th >考试时间</th>
										<th width=120>试卷类型</th>
										<th width=120>状态</th>
										<th width="241">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${examPaperList }" var="examPaper" varStatus="st">
										<tr>
											<td>${st.index+1}</td>
											<td>${examPaper.title}</td>
											<td>${examPaper.course_name}</td>
											<td>${examPaper.class_name}</td>
											<td>${examPaper.exam_date}</td>
											<td>
												<c:if test="${examPaper.type == 1}"><span class="label label-primary">正式考试</span></c:if>
												<c:if test="${examPaper.type == 2}"><span class="label label-success">模拟考试</span></c:if>
												<c:if test="${examPaper.type == 3}"><span class="label label-default">课后习题</span></c:if>
											</td>
											<td>
												<c:if test="${examPaper.status == 10}"><span class="label label-primary">已出卷未选题</span></c:if>
												<c:if test="${examPaper.status == 20}"><span class="label label-info">已选题待审核</span></c:if>
												<c:if test="${examPaper.status == 30}"><span class="label label-success">审核通过</span></c:if>
												<c:if test="${examPaper.status == 40}"><span class="label label-danger">审核未通过</span></c:if>
											</td>
											<td><c:if test="${bln:isP('ExamPaperUpdate') && examPaper.status != 30}">
													<a href="toUpdate.htm?id=${examPaper.id}&backUrl=${backUrl}"> 调整试卷</i>
													</a>
												</c:if>
												<c:if test="${bln:isP('ExamPaperAudit')  && examPaper.status == 20}">
													<a href="toAudit.htm?id=${examPaper.id}&backUrl=${backUrl}"> 审核</i>
													</a>
												</c:if>
												<c:if test="${bln:isP('ExamPaperDelete')}">
													<a href="javascript:delModule(${examPaper.id });"> 删除 </a>
												</c:if>
													<a href="toView.htm?id=${examPaper.id}&backUrl=${backUrl}"> 查看试卷 </a>
											</td>
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
			<!-- /.main-container -->
			<%@ include file="../common/js.jsp"%>

			<script type="text/javascript">
				$(function() {
					$("#btnSearch").bind('click', searchModule);
					$("#btnAdd").bind('click', addUser);
				})

				// 查询方法
				var searchModule = function() {
					var url = "index.htm?___=_";
                    if($("#txtTitle").val() != ''){
                        url += '&title=' + $("#txtTitle").val();
                    }
                    if($("#cmbCourse").val() != ''){
                        url += '&course_id=' + $("#cmbCourse").val();
                    }
                    if($("#cmbClass").val() != ''){
                        url += '&class_id=' + $("#cmbClass").val();
                    }
                    if($("#cmbStatus").val() != ''){
                        url += '&status=' + $("#cmbStatus").val();
                    }
					window.location = encodeURI(url);
				}
				// 删除
				var delModule = function(id) {
					bootbox.confirm("<h4 class='red'>你确定要删除该试卷吗？</h4>", function(result) {
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
