<%-- ActionError Messages - usually set in Actions --%>
<s:if test="hasActionErrors()">
    <div class="error" id="errorMessages" style="border:1px solid #b6c0db">
    <table>
    	<tr><td></td></tr>
    </table>    
      <s:iterator value="actionErrors">
      
        <img src="<c:url value="/images/icons/warning.gif"/>" class="icon" />
        <s:property escape="false"/><br>
      </s:iterator>
   </div>
</s:if>

<%-- FieldError Messages - usually set by validation rules --%>
<s:if test="hasFieldErrors()">
   <div class="error" id="errorMessages" style="border:1px solid #b6c0db">   
    <table> 
      <s:iterator value="fieldErrors">
          <s:iterator value="value">
          <tr><td><img src="<c:url value="/images/icons/warning.gif"/>" class="icon" />
             <s:property escape="false"/><br></td>
          </s:iterator>
      </s:iterator>
	  </table>   
   </div>
</s:if>

<%-- Success Messages --%>
<c:if test="${not empty messages}">
    <div class="message" id="successMessages" style="border:1px solid #b6c0db">    
        <c:forEach var="msg" items="${messages}">
            <img src="<c:url value="/images/icons/information.gif"/>" class="icon" />
            <c:out value="${msg}" escapeXml="false"/><br>
        </c:forEach>
    </div>
    <c:remove var="messages" scope="session"/>
</c:if>
