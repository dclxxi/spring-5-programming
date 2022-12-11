<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- <spring:message> 사용하기 위해 태그 라이브러리 설정 추가 --%>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="member.register"/></title> <%-- MessageSource 빈을 이용해서 메시지 출력 (getMessage()) --%>
</head>
<body>
<h2><spring:message code="term"/></h2>
<p>약관 내용</p>
<form action="step2" method="post">
    <label>
        <input type="checkbox" name="agree" value="true">
        <spring:message code="term.agree"/>
    </label>
    <input type="submit" value="<spring:message code="next.btn" />"/>
</form>
</body>
</html>
