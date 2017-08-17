<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-教员统计</title>
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
						<li class="active">统计分析</li>
						<li class="active">教员统计</li>
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
												<td><input type="text" id="name" class="form-control input-small" placeholder=""
													value="${statTeacherSearchVO.name }"></td>
												<td>军官证号：</td>
												<td><input type="text" id="certificate_code" class="form-control input-middle" placeholder=""
													value="${statTeacherSearchVO.certificate_code }"></td>
												<td>
													<button class="btn btn-primary btn-sm" id="btnSearch">
														<i class="ace-icon fa fa-search"></i> 查询
													</button>
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
							<table id="simple-table" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th width=40>#</th>
										<th width="100">军官证号</th>
										<th width="100">姓名</th>
										<th width="100">授课次数</th>
										<th width="100">授课课时</th>
										<th width="100">授课人数</th>
										<th width="140">学员平均成绩</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${list }" var="stat" varStatus="st">
										<tr>
											<td>${st.index+1 }</td>
											<td>${stat.certificate_code}</td>
											<td>${stat.name }</td>
											<td>${stat.teach_count }</td>
											<td>${stat.teach_time }</td>
											<td>${stat.teach_num }</td>
											<td><fmt:formatNumber value="${stat.average_score }" pattern="0.00"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- /.span -->
					</div>
					<!-- /.row -->
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
					$("#btnSearch").bind('click', search);
				})

				// 查询方法
				var search = function() {
					var url = "index.htm?";
					if ($("#certificate_code").val() != '')
						url += "&certificate_code=" + $("#certificate_code").val();
					if ($("#name").val() != '')
						url += "&name=" + $("#name").val();
					window.location = encodeURI(url);
				}
			</script>
</body>
</html>
