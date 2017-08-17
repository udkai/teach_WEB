<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${webTitle }-课程类型管理</title>
    <%@ include file="../common/header.jsp" %>
    <link href="${staticServer }/assets/zTree/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
</head>

<body class="no-skin">
<%@ include file="../common/top.jsp" %>

<div class="main-container" id="main-container">
    <%@ include file="../common/menu.jsp" %>
    <div class="main-content">
        <div class="main-content-inner">
            <!-- #section:basics/content.breadcrumbs -->
            <div class="breadcrumbs  breadcrumbs-fixed" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
                    <li class="active">基础数据管理</li>
                    <li class="active">课程类型管理</li>
                </ul>
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        课程类型管理
                        <small><i class="ace-icon fa fa-angle-double-right"></i> 修改课程类型
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="column">
                    <div class="col-xs-12">
                        <form id="moduleForm" name="moduleForm" class="form-horizontal" action="update.htm"
                              method="post">
                            <input type="hidden" name="backUrl" value="${backUrl }">
                            <input type="hidden" name="id" value="${baseCourseType.id }">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程类型id：</label>
                                <div class="col-sm-9">
                                    <input id="id" type="text" class="col-xs-10 col-sm-5" placeholder=""
                                           value="${baseCourseType.id}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程类型名称：</label>
                                <div class="col-sm-9">
                                    <input id="name" name="name" type="text" class="col-xs-10 col-sm-5" placeholder=""
                                           value="${baseCourseType.name}"
                                           maxlength="20"> <label id="nameTip"></label>
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
            <%@ include file="../common/js.jsp" %>

            <script type="text/javascript">
                $(document).ready(function () {
                    $("#moduleForm").validate({
                        debug: true,
                        errorElement: "label",
                        errorClass: "valiError",
                        errorPlacement: function (error, element) {
                            error.appendTo($("#" + element.attr('id') + "Tip"));
                        },
                        rules: {
                            name: {
                                required: true,
                                maxlength: 20
                            }
                        },
                        messages: {},
                        submitHandler: function (form) {
                            form.submit();
                        }
                    });
                });
            </script>
</body>
</html>
