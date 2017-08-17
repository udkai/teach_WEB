<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${webTitle }-知识点统计</title>
    <%@ include file="../common/header.jsp" %>
    <link rel="stylesheet" href="<c:url value="/assets/highcharts/css/highcharts.css"/>"/>
    <style>
        /*.highcharts-color-0 {*/
            /*fill: #6cc135;*/
            /*stroke: #6cc135;*/
        /*}*/
    </style>
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
                    <li class="active">统计分析</li>
                    <li class="active">知识点统计</li>
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
                                            <td>课程：</td>
                                            <td>
                                                <select id="cmbCourse" type="text" class="form-control">
                                                    <option value="">--选择--</option>
                                                    <c:forEach items="${listCourse}" var="course">
                                                        <option value="${course.value}"<c:if test="${examPaperSearchVO.course_id == course.value}">selected</c:if> >${course.content}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>章节：</td>
                                            <td>
                                                <select id="cmbTopic" type="text" class="form-control">
                                                    <option value="">--选择--</option>
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
                <div class="row">
                    <div class="col-xs-12">
                        <div id="container" style="min-width: 400px;height: 400px;">
                        </div>
                    </div>
                </div>
                <div class="row panel panel-primary col-sm-8 col-sm-offset-2 no-padding">
                    <div class="panel-heading">
                        详细情况
                    </div>
                    <div class="panel panel-body">
                        <div id="detail" style="">
                            共 ${list.size()} 个知识点,答题学员 ${answer_student_List.size()} 人,答题 ${answer_subject_num} 道。
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.main-content -->
        </div>
        <!-- /.main-container -->
        <%@ include file="../common/js.jsp" %>
        <script src="<c:url value="/assets/highcharts/js/highcharts.js"/>"></script>

        <script type="text/javascript">
            $(function () {
                /*横轴长度*/
                var xAxis_length = ${list.size()-1};
                var xAxis = [], true_score = [], total_score = [],countRatio = [];;
                <c:forEach items="${list}" var="category">
                xAxis.push('${category.category_name}');
                true_score.push(<fmt:formatNumber value="${category.true_score}" pattern="0.00"/>);
                total_score.push(<fmt:formatNumber value="${category.total_score}" pattern="0.00"/>);
                countRatio.push(<fmt:formatNumber value="${category.true_score / category.total_score * 100}" pattern="0.00"/>);
                </c:forEach>
                $('#container').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '知识点统计图'
                    },
                    subtitle: {
                        text: ''
                    },
                    xAxis: {
                        categories: xAxis,
                        max: xAxis_length,
                        tickWidth: 100
                    },
                    yAxis: [{
                        min: 0,
                        title: {
                            text: '统计'
                        }
                    }, {
                        title: {
                            text: '得分率',
                            style: {
                                color: '#FFFFFF'
                            }
                        },
                        labels: {
                            max: 100,
                            formatter: function () {
                                if (this.value > 100) {
                                    return '100 %';
                                } else {
                                    return this.value + ' %';
                                }
                            },
                            style: {
                                color: '#FFFFFF'
                            }
                        },
                        opposite: true,
                        tickPositions: [0, 25, 50, 75, 100]
                    }],
                    credits: {
                        enabled: false //去除版权信息
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormatter: function () {
                            if (this.series.index == 2) {
                                return '<tr><td style="color:{series.color};padding:0">' + this.series.name + ': </td>' +
                                    '<td style="padding:0"><b>' + this.y + ' %</b></td></tr>';
                            }else{
                                return '<tr><td style="color:{series.color};padding:0">' + this.series.name + ': </td>' +
                                    '<td style="padding:0"><b>' + this.y + ' 分</b></td></tr>';
                            }

                        },
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0,
                            dataLabels: {
                                enabled: true,
                                format: '{point.y}'
                            }
                        },
                        line: {
                            dataLabels: {
                                enabled: true,
                                format: '{point.y} %'
                            }
                        }
                    },
                    series: [{
                        name: '正确得分',
                        data: true_score,
                        visible: true
                    }, {
                        name: '总分',
                        data: total_score,
                        visible: true
                    }, {
                        name: '得分率',
                        type: 'line',
                        yAxis: 1,
                        data: countRatio,
                        tooltip: {
                            valueSuffix: ' %'
                        }
                    }],
                    lang: {
                        contextButtonTitle: '导出按钮文字',
                        decimalPoint: '小数点',
                        downloadJPEG: '导出JPEG',
                        downloadPDF: '导出PDF',
                        downloadPNG: '导出PNG',
                        downloadSVG: '导出SVG',
                        drillUpText: '上钻',
                        invalidDate: '无效的时间',
                        loading: '加载中',
                        months: '月份',
                        noData: '没有数据',
                        numericSymbolMagnitude: '国际单位符基数',
                        numericSymbols: '国际单位符',
                        printChart: '打印图表',
                        resetZoom: '重置缩放比例',
                        resetZoomTitle: '重置缩放标题',
                        shortMonths: '月份缩写',
                        shortWeekdays: '星期缩写',
                        thousandsSep: '千分号',
                        weekdays: '星期'
                    }
                });
            })
            // 查询方法
            var searchModule = function() {
                var url = "index.htm?___=_";
                if($("#cmbTopic").val() != ''){
                    url += '&title=' + $("#cmbTopic").val();
                }
                if($("#cmbCourse").val() != ''){
                    url += '&course_id=' + $("#cmbCourse").val();
                }
                window.location = encodeURI(url);
            }
        </script>
</body>
</html>
