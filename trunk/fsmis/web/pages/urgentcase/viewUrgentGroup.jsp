<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.systop.fsmis.model.*,com.systop.common.modules.security.user.model.*,com.systop.fsmis.urgentcase.UcConstants"  %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<link href="${ctx}/pages/urgentcase/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
	Map<String, UrgentResult> groupMap = (HashMap<String, UrgentResult>)request.getAttribute("groupMap");
	User logUser = (User)request.getAttribute("loginUser");
%>
<table width="810" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="78"><table width="249" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="45" background="${ctx}/pages/urgentcase/images/zuzhi_05.gif">
          <div align="center" id="ZhiHuiBu" style="padding: 10px 5px 10px 5px;">
            <%if (groupMap.get(UcConstants.LEADERSHIP) != null && groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup() != null){%>
            <div align="center"><b><%=groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getName()%></b></div><br>
            	<%if (groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.LEADERSHIP).getUrgentGroup().getMobel()%><br>
				<%}%>
            <%}%>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
<table width="810" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="center"><img src="${ctx}/pages/urgentcase/images/zuzhi_07.gif" width="800" height="60" /></td>
  </tr>
</table>
<table width="810" height="119" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td width="81" valign="top"><table width="90" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="80">&nbsp;</td>
      </tr>
      <tr>
        <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_12.gif">
       	 <div align="center" id="Office" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.OFFICE) != null && groupMap.get(UcConstants.OFFICE).getUrgentGroup() != null){%>
              	<div align="center"><b><%=groupMap.get(UcConstants.OFFICE).getUrgentGroup().getName()%></b></div><br>
              	<%if (groupMap.get(UcConstants.OFFICE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.OFFICE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.OFFICE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.OFFICE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.OFFICE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.OFFICE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
			<%}%>
		  </div>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
		  	<div align="center" id="JingjieBaowei" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.DEFEND) != null && groupMap.get(UcConstants.DEFEND).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> operators = groupMap.get(UcConstants.DEFEND).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (operators.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!operators.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
              	<%if (groupMap.get(UcConstants.DEFEND).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.DEFEND).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.DEFEND).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.DEFEND).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.DEFEND).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.DEFEND).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
		  </div>
		  </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
          	<div align="center" id="YiLiaoJiuHu" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.MEDICAL_RESCUE) != null && groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_res = groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_res.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getName()%></b>
              		 	 </a>
              		  <%}%>
              		  <%if (!opers_res.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.MEDICAL_RESCUE).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.MEDICAL_RESCUE).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
           <div align="center" id="HouQinBaoZhang" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.REAR_SERVICE) != null && groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_rear = groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_rear.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_rear.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.REAR_SERVICE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.REAR_SERVICE).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.REAR_SERVICE).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
          <div align="center" id="ShanHouChuLi" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.AFTER_HANDLE) != null && groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_after = groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_after.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_after.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.AFTER_HANDLE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.AFTER_HANDLE).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.AFTER_HANDLE).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
           <div align="center" id="DiaoChaChuLi" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE) != null && groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_hand = groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_hand.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_hand.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
           <div align="center" id="XinWenBaoDao" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.NEWS_REPORT) != null && groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_news = groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_news.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_news.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.NEWS_REPORT).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.NEWS_REPORT).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.NEWS_REPORT).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="80" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
          <div align="center" id="ZhuanJiaJiShu" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY) != null && groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_tech = groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_tech.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_tech.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
    <td width="159" valign="top"><div align="center">
      <table width="90" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="180" valign="top" background="${ctx}/pages/urgentcase/images/zuzhi_11.gif">
         	<div align="center" id="JieDai" style="padding: 10px 5px 10px 5px;">
       	 	<%if (groupMap.get(UcConstants.RECEIVE) != null && groupMap.get(UcConstants.RECEIVE).getUrgentGroup() != null){%>
              	<div align="center">
              		<c:if test="${model.status ne '3' && model.status ne '4'}">
              		  <% 
              		  	Set<User> opers_rec = groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getUsers(); 
              		  %>
              		  <%if (opers_rec.contains(logUser)) {%>
              		  	<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getId()%>&model.id=${model.id}" target="main">
              				<b><%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getName()%></b>
              		  	</a>
              		  <%}%>
              		  <%if (!opers_rec.contains(logUser)) {%>
              		  	<b><%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getName()%></b>
              		  <%}%>
              		</c:if>
              		<c:if test="${model.status eq '3' || model.status eq '4'}">
              			<b><%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getName()%></b>
              		</c:if>
              	</div><br>
				<%if (groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getPrincipal() != null){%>
					<%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getPrincipal()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getPhone() != null){%>
					<%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getPhone()%><br>
				<%}%>
				<%if (groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getMobel() != null){%>
					<%=groupMap.get(UcConstants.RECEIVE).getUrgentGroup().getMobel()%><br>
				<%}%>
				<br><br>
				<br><br>
				<%if (groupMap.get(UcConstants.RECEIVE).getHandleTime() != null){%>
					<b>已处理</b>
				<%}%>
				<%if (groupMap.get(UcConstants.RECEIVE).getHandleTime() == null){%>
					<b><font color="#990099">未处理</font></b>
				<%}%>
			<%}%>
			</div>
		  </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div></td>
  </tr>
</table>
</body>
</html>
