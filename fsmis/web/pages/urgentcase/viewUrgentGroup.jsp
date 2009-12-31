<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.systop.fsmis.model.*,com.systop.fsmis.urgentcase.UcConstants"  %>
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
	Map<String, UrgentGroup> groupMap = (HashMap<String, UrgentGroup>)request.getAttribute("groupMap");
%>
<table width="810" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="78"><table width="249" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="45" background="${ctx}/pages/urgentcase/images/zuzhi_05.gif">
          <div align="center" id="ZhiHuiBu" style="padding: 10px 5px 10px 5px;">
            <%if (groupMap.get(UcConstants.LEADERSHIP) != null){%>
            <div align="center"><b><%=groupMap.get(UcConstants.LEADERSHIP).getName()%></b></div><br>
            
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
              <div align="center"><a target="_blank" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.LEADERSHIP).getId()%>">修改组成员</a></div>
			    </stc:role>
            <%}%>
          </div>        </td>
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
       	 	<%if (groupMap.get(UcConstants.OFFICE) != null){%>
              	<div align="center"><b><%=groupMap.get(UcConstants.OFFICE).getName()%></b></div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.OFFICE).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.DEFEND) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.DEFEND).getId()%>&model.id=${model.id}" target="main">
              			<b><%=groupMap.get(UcConstants.DEFEND).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.DEFEND).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.MEDICAL_RESCUE) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.MEDICAL_RESCUE).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.MEDICAL_RESCUE).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.REAR_SERVICE) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.REAR_SERVICE).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.REAR_SERVICE).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.REAR_SERVICE).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.AFTER_HANDLE) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.AFTER_HANDLE).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.AFTER_HANDLE).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.AFTER_HANDLE).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.ACCIDENT_HANDLE) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.ACCIDENT_HANDLE).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.NEWS_REPORT) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.NEWS_REPORT).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.NEWS_REPORT).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.NEWS_REPORT).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.EXPERT_TECHNOLOGY) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.EXPERT_TECHNOLOGY).getId()%>">修改组成员</a></div>
				</stc:role>
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
       	 	<%if (groupMap.get(UcConstants.RECEIVE) != null){%>
              	<div align="center">
              		<a href="${ctx}/urgentcase/editGroupResult.do?groupId=<%=groupMap.get(UcConstants.RECEIVE).getId()%>&model.id=${model.id}" target="main">
              		<b><%=groupMap.get(UcConstants.RECEIVE).getName()%></b>
              		</a>
              	</div><br>
				
				<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
					<div align="center"><a target="_black" href="${ctx}/emergency/person/index.do?groupId=<%=groupMap.get(UcConstants.RECEIVE).getId()%>">修改组成员</a></div>
				</stc:role>
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
