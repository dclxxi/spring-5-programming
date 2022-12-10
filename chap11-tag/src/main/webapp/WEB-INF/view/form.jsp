<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
</head>
<body>
<h2>로그인</h2>
<form:form modelAttribute="formCommand"> <%-- 기본값 : method="post" action="<현재 요청 URL>" --%>
    <%-- modelAttribute : 커맨드 객체 이름 설정 (기본값 : command) --%>
    <p>
        <label for="loginType">로그인 타입(form:select)</label>
        <form:select path="loginType" items="${loginTypes}"/>
            <%-- path : 커맨드 객체의 프로퍼티 이름, items : <option>을 생성할 때 사용할 콜렉션 객체 --%>
    </p>
    <p>
        <label for="loginType">로그인 타입(form:options)</label>
        <form:select path="loginType">
            <option value="">--- 선택하세요 ---</option>
            <form:options items="${loginTypes}"/> <%-- items : 값 목록으로 사용할 모델 이름 설정 --%>
        </form:select>
    </p>
    <p>
        <label>선호 OS</label> <%-- checkboxes : 커맨드 객체의 프로퍼티와 고간련된 checkbox 타입의 <input> 목록 생성 --%>
        <form:checkboxes items="${favoriteOsNames}" path="favoriteOs"/>
    </p>
    <p>
        <label>선호 OS</label> <%-- 콜렉션에 저장된 객체가 String이 아닌 경우 (값과 텍스트로 사용할 객체의 프로퍼티 지정) --%>
        <form:checkboxes items="${favoriteOsCodes}" path="favoriteOs" itemValue="code" itemLabel="label"/>
    </p>

    <input type="submit" value="가입 완료">
</form:form>
<hr/>

<h2>select: Code 사용예</h2>
<form:form modelAttribute="formCommand">
    <form:select path="jobCode"> <%-- 콜렉션에 저장된 객체의 특정 프로퍼티를 사용해야 하는 경우 (!= String) --%>
        <option value="">--- 선택하세요 ---</option>
        <form:options items="${jobCodes}" itemLabel="label" itemValue="code"/>
    </form:select>
</form:form>
</body>
</html>
