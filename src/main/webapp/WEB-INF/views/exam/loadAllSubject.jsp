<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/taglib.jsp" %>

<!DOCTYPE html>
<c:if test="${empty allList}">
    <div class="no-subject">
        没有查询到数据
    </div>
</c:if>
<c:forEach items="${allList}" var="allSubject" varStatus="allSubjectSt">
    <div data-id="${allSubject.id}" data-type="${allSubject.type}"
         class="panel select-item panel-primary">
        <div class="panel-heading">
            <c:if test="${allSubject.type eq '01'}">单选题</c:if>
            <c:if test="${allSubject.type eq '02'}">多选题</c:if>
            <c:if test="${allSubject.type eq '03'}">填空（顺序）</c:if>
            <c:if test="${allSubject.type eq '04'}">填空（非顺序）</c:if>
            <c:if test="${allSubject.type eq '05'}">简答题</c:if>
            <c:if test="${allSubject.type eq '06'}">论述题</c:if>
            <span class="pull-right show-score">分值：<span name="score">0</span>&nbsp;分</span>
        </div>
        <div class="panel panel-body">
            <div class="subject-topic">
                <div class="radio">
                    <label class="no-padding-left">
                        <input name="select-radio" type="radio" class="ace">
                        <span class="lbl topic">${allSubject.topic}</span>
                    </label>
                </div>
            </div>
            <div class="options">
                <c:if test="${allSubject.type eq '01' || allSubject.type eq '02'}">
                    <c:forEach items="${allSubject.optionList}" var="option">
                        <div>
                                ${option}
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${allSubject.type eq '04'}">
                    <div>
                            ${allSubject.answer}
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</c:forEach>

</html>
