<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${htmlReport != null}">
    ${htmlReport}
</c:if>
<c:if test="${pdfFileUrl != null}">
    <a href='${pdfFileUrl}'>${pdfFileUrl}</a>
</c:if>
