<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${webTitle }-盘亏单列表</title>
    <%@ include file="../common/header.jsp"%>
    <link rel="stylesheet" href="${staticServer }/assets/components/jquery-ui/jquery-ui.css?version=${versionNo}" />
    <link rel="stylesheet" type="text/css" href="${staticServer }/assets/datetimepicker/jquery.datetimepicker.css?version=${versionNo}" />

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
                    <li class="active">统计查询</li>
                    <li class="active">商品统计</li>
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
                                            <td>统计时间：</td>
                                            <td><input type="text" id="created_date_from" class="form-control " placeholder="" value="${statProductSearchVO.created_date_from }"></td>
                                            <td align="center">至</td>
                                            <td><input type="text" id="created_date_to" class="form-control" placeholder="" value="${statProductSearchVO.created_date_to }"></td>
                                            <td>商品名称：</td>
                                            <td><form:select path="statProductSearchVO.product_code" class="form-control" id="product_code">
                                                    <option value="">---请选择---</option>
                                                    <form:options items="${product_code }" itemValue="value" itemLabel="content" />
                                                </form:select>
                                            </td>
                                            <td align="right">商品分类：</td>
                                            <td><form:select path="statProductSearchVO.category_code" class="form-control" id="category_code">
                                                <option value="">---请选择---</option>
                                                <form:options items="${category }" itemValue="id" itemLabel="name" />
                                            </form:select></td>
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
                            <th>商品名称</th>
                            <th>规格</th>
                            <th>销售数量</th>
                            <th>销售总价</th>
                            <th>退货率</th>
                            <th>商品所属分类名称</th>
                            </thead>
                            <tbody>
                            <c:forEach items="${statproductlist}" var="list">
                                <tr>
                                    <td>${list.pro_name}</td>
                                    <td>${list.spec}</td>
                                    <td>${list.quantity_total}</td>
                                    <td>${list.total_price}</td>
                                    <td>${list.return_rate}%</td>
                                    <td>${list.category_name}</td>
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
    </div>
</div>
<!-- /.main-container -->
<%@ include file="../common/js.jsp"%>
<script src="${staticServer }/assets/components/jquery-ui/jquery-ui.js"></script>
<script src="${staticServer }/assets/datetimepicker/jquery.datetimepicker.js" type="text/javascript"></script>

<script type="text/javascript">
    $(function() {
        $("#btnSearch").bind('click', searchLoss);

        $('#created_date_from').datetimepicker({
            lang : 'ch',
            timepicker : false,
            format : 'Y-m-d',
            formatDate : 'Y-m-d'
        });
        $('#created_date_to').datetimepicker({
            lang : 'ch',
            timepicker : false,
            format : 'Y-m-d',
            formatDate : 'Y-m-d'
        });
    })

    // 查询方法
    var searchLoss = function() {
        var url = "index.htm?";
        if ($("#product_code").val() != '')
            url += "&product_code=" + $("#product_code").val();
        if ($("#created_date_from").val() != '')
            url += "&created_date_from=" + $("#created_date_from").val();
        if ($("#created_date_to").val() != '')
            url += "&created_date_to=" + $("#created_date_to").val();
        if ($("#category_code").val() != '')
            url += "&category_code=" + $("#category_code").val();
        window.location = encodeURI(url);
    }
</script>
</body>
<style>
</style>
</html>
