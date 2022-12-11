<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="member.register"/></title>
</head>
<body>
<p>
    <spring:message code="register.done">
        <spring:argument value="${registerRequest.name}"/> <%-- argument : {0} 위치에 삽입할 값 설정 --%>
        <spring:argument value="${registerRequest.email}"/>
    </spring:message>
    <%--
        두 표현식을 콤마로 구분
        <spring:message code="register.done" arguments="${registerRequest.name}, ${registerRequest.email}/">
     --%>
</p>
<p>
    <a href="<c:url value='/main'/>">
        [<spring:message code="go.main"/>]
    </a>
</p>
</body>
</html>
