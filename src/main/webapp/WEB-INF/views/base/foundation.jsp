<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${webTitle }-基地信息</title>
    <%@ include file="../common/header.jsp" %>
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
                    <li class="active">基地信息</li>
                </ul>
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        基础数据管理
                        <small><i class="ace-icon fa fa-angle-double-right"></i> 基地信息管理
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <form id="foundationForm" name="foundationForm" class="form-horizontal" action="save.htm"
                              method="post">
                            <input type="hidden" name="backUrl" value="${backUrl }">
                            <c:if test="${baseFoundation != null}">
                                <input type="hidden" name="id" value="${baseFoundation.id }">
                            </c:if>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">基地编号：</label>
                                <div class="col-sm-9">
                                    <input id="code" name="code" type="text" class="col-xs-10 col-sm-5" placeholder=""
                                           value="${baseFoundation.code}" maxlength="10"> <label id="codeTip"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">基地描述：</label>
                                <div class="col-sm-9">
                                    <textarea id="detail" name="detail" rows="10"
                                              class="col-xs-10 col-sm-5">${baseFoundation.detail}</textarea><label
                                        id="detailTip"></label>
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
                    $("#foundationForm").validate({
                        //debug : true,
                        errorElement: "label",
                        errorClass: "valiError",
                        errorPlacement: function (error, element) {
                            error.appendTo($("#" + element.attr('id') + "Tip"));
                        },
                        rules: {
                            code: {
                                required: true,
                                maxlength: 10,
                            },
                            detail: {
                                required: true
                            }

                        },
                        submitHandler: function () {
                            return true;
                        }
                    });
                });
            </script>
</body>
</html>
