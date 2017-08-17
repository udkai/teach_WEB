<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${webTitle }-毕业展示管理</title>
<%@ include file="../common/header.jsp"%>
	<style>
		.replaceImg {
			cursor: -webkit-zoom-in;
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
						<li class="active">教学辅助</li>
						<li class="active">毕业展示管理</li>
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
												<td>班级：</td>
												<td><form:select path="graduationShowSearchVO.class_id" class="form-control input-middle"
														id="class_id">
														<form:option value="" label="-选择-" />
														<form:options items="${listClass}" itemValue="value" itemLabel="content" />
													</form:select></td>
												<td>
													<button class="btn btn-primary btn-sm" id="btnSearch">
														<i class="ace-icon fa fa-search"></i> 查询
													</button> <c:if test="${bln:isP('GraduationShowAdd')}">
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
										<th width=120>班级</th>
										<th width=120>说明</th>
										<th width=120>发布人</th>
										<th width=120>发布时间</th>
										<th width="241">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${graduationShowList }" var="show" varStatus="st">
										<tr>
											<td>${st.index+1}</td>
											<td>${show.class_name}</td>
											<td>${show.replaceDetail}</td>
											<td>${show.create_person}</td>
											<td><fmt:formatDate value="${show.create_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td><c:if test="${bln:isP('GraduationShowUpdate')}">
													<a href="toUpdate.htm?id=${show.id}&backUrl=${backUrl}"> 修改</i>
													</a>
												</c:if> <c:if test="${bln:isP('GraduationShowDelete')}">
													<a href="javascript:delModule(${show.id });"> 删除 </a>
												</c:if>
												<a href="toView.htm?id=${show.id}&backUrl=${backUrl}"> 查看展示</i>
												</a>
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
			<div id="modal-replace-img" class="modal fade" role="dialog" aria-hidden="true">
				<div class="modal-dialog" style="width: 500px;margin-top: 10%">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span>
								<span class="sr-only"></span>
							</button>
							<h4 class="modal-title">查看图片</h4>
						</div>
						<div class="modal-body">
							<img id="show_img" src="" width="498px" height="auto" style="margin-left: -15px;"/>
						</div>
					</div>
				</div>
			</div>
			<!-- /.main-container -->
			<%@ include file="../common/js.jsp"%>

			<script type="text/javascript">
				$(function() {
					$("#btnSearch").bind('click', searchModule);
					$("#btnAdd").bind('click', addUser);

                    $(".replaceImg").on('click', function () {
                        $("#show_img").attr("src", $(this).attr("data-src"));
                        $('#modal-replace-img').modal('show');
                    });
				})

				// 查询方法
				var searchModule = function() {
					var url = "index.htm?___=_";

					if ($("#class_id").val() != '')
						url += "&class_id=" + $("#class_id").val();
					window.location = encodeURI(url);
				}
				// 删除
				var delModule = function(id) {
					bootbox.confirm("你确定要删除该信息吗？", function(result) {
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