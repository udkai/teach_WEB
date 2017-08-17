<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-成绩管理</title>
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
						<li class="active">在线考试</li>
						<li class="active">成绩管理</li>
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
												<td><input type="text" id="userinfo_name" class="form-control input-middle" placeholder=""
													value="${examResultSearchVO.userinfo_name}"></td>
												<td>军官证号：</td>
												<td><input type="text" id="userinfo_certificate_code" class="form-control input-middle" placeholder=""
													value="${examResultSearchVO.userinfo_certificate_code }"></td>
												<td>课程：</td>
												<td><form:select path="examResultSearchVO.course_id" class="form-control input-middle" id="course_id">
														<form:option value="" label="-选择-" />
														<form:options items="${listCourse}" itemValue="value" itemLabel="content" />
													</form:select></td>
												<td>班级：</td>
												<td><form:select path="examResultSearchVO.class_id" class="form-control input-middle" id="class_id">
														<form:option value="" label="-选择-" />
														<form:options items="${listClass}" itemValue="value" itemLabel="content" />
													</form:select></td>
												<td>
													<button class="btn btn-primary btn-sm" id="btnSearch">
														<i class="ace-icon fa fa-search"></i> 查询
													</button> <c:if test="${bln:isP('ExamResultAdd')}">
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
							<table id="treeTable" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th width=60>序号</th>
										<th width=100>学员</th>
										<th width=120>军官证号</th>
										<th width=120>课程</th>
										<th width=120>班级</th>
										<th >试卷</th>
										<th width=120>考试时间</th>
										<th width=120>得分</th>
										<th width="241">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${examResultList }" var="result" varStatus="st">
										<tr>
											<td>${st.index+1}</td>
											<td>${result.userinfo_name}</td>
											<td>${result.userinfo_certificate_code}</td>
											<td>${result.course_name}</td>
											<td>${result.class_name}</td>
											<td>${result.paper_name}</td>
											<td>${result.exam_date}</td>
											<td>${result.score}</td>
											<td><c:if test="${result.score != null}">
													<a href="toView.htm?id=${result.id}&paper_id=${result.paper_id}&student_id=${result.userinfo_id}&backUrl=${backUrl}"> 查看详情</i>
													</a>
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

					if ($("#userinfo_name").val() != '')
						url += "&userinfo_name=" + $("#userinfo_name").val();
					if ($("#userinfo_certificate_code").val() != '')
						url += "&userinfo_certificate_code=" + $("#userinfo_certificate_code").val();
					if ($("#course_id").val() != '')
						url += "&course_id=" + $("#course_id").val();
					if ($("#class_id").val() != '')
						url += "&class_id=" + $("#class_id").val();
					window.location = encodeURI(url);
				}
				//新增
				var addUser = function(id) {
					window.location = 'toAdd.htm?backUrl=${backUrl }';
				}
			</script>
</body>
</html>