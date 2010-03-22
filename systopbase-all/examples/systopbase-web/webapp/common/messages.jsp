<%-- ActionError Messages - usually set in Actions --%>
<script type="text/javascript">
	function closeDiv() {
		document.all.errorMessages.style.display="none";
	}
</script>
<s:if test="hasActionErrors()">
    <div class="error" id="errorMessages" style="border:1px solid #b6c0db">
    <table>
    	<tr><td></td></tr>
    </table>    
      <s:iterator value="actionErrors">
      
        <img src="${ctx}/images/icons/warning.gif" class="icon" />
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
          <tr><td><img src="${ctx}/images/icons/warning.gif" class="icon" />
             <s:property escape="false"/><br></td>
          </s:iterator>
      </s:iterator>
	  </table>   
   </div>
</s:if>

<%-- Success Messages --%>
<s:if test="hasActionMessages()">
    <div class="message" id="successMessages" style="border:1px solid #b6c0db">
    <table>
    	<tr><td></td></tr>
    </table>    
      <s:iterator value="actionMessages">
        <img src="${ctx}/images/icons/information.gif" class="icon" />
        <s:property escape="false"/><br>
      </s:iterator>
   </div>
</s:if>
