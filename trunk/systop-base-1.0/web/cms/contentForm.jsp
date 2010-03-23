<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/common/taglibs.jsp"%>

<style>
input,textarea{border:1px solid #cecece;}
</style>

<script type="text/javascript">
dojo.require("dojo.widget.*");
var id = null;

<c:if test="${param.contentId != null}">
   id = <c:out value='${param.contentId}'/>;
</c:if>
dojo.addOnLoad( function(){
    var fckEditor = new FCKeditor( 'body' ) ;
    fckEditor.BasePath = "<c:url value='/fckEditor/'/>";
    fckEditor.Height = 400;
    fckEditor.ReplaceTextarea();
    if(id) {      
        var dh = new DojoHelper();
        dh.setValue("title", '<cms:displayField fieldName="title" contentId="contentId"></cms:displayField>');
        dh.setValue("subtitle", '<cms:displayField fieldName="subtitle" contentId="contentId"></cms:displayField>');
        dh.setValue("summary", '<cms:displayField fieldName="summary" contentId="contentId"></cms:displayField>');
        var date = '<cms:displayField fieldName="expireDate" contentId="contentId"></cms:displayField>';
        if(date == '') {
            dojo.byId('neverExp').checked = true;
        } else {
            dh.setDatePicker("expireDate", new Date(parseInt(date)));
        }
        if('<cms:displayField fieldName="available" contentId="contentId"></cms:displayField>' == 'false') {
            dojo.byId("available").checked = false;
        }
        if('<cms:displayField fieldName="visible" contentId="contentId"></cms:displayField>' == 'false') {
            dojo.byId("visible").checked = false;
        }
        if('<cms:displayField fieldName="isDraft" contentId="contentId"></cms:displayField>' == 'false') {
            dojo.byId("draft").checked = false;
        }
        //dh.setValue("parentid", content.parent);
  }
  
});
var cms = new CMSEditor(cmsDojoAction, 'mainTabContainer');
  
  

function onNeverExp() {
   var dh = new DojoHelper();
   dh.resetDatePicker('expireDate');
}

function onSetExpireDate(date) {   

  if(date && dojo.widget.byId("expireDate").inputNode.value != "") {
    dojo.byId('neverExp').checked = false;
  } else {
    dojo.byId('neverExp').checked = true;
  }
}

function onSave() {
  var editor = FCKeditorAPI.GetInstance('body');
  //alert(parent.getFilenames());
  cms.save(editor.GetXHTML(true));
  $('pathPane').innerHTML = "";
  $('pathPane2').innerHTML = "";
  var backFalgStr = "<div style=' float:right; '><img src='<c:url value='/images/back.gif'/>' />" + "<a href='<c:url value='/cms/index.jsp'/>'>" + "<font color='#003366'><b><fmt:message key='global.backCmsList' /></b></font>" + "</a></div>";
  
  cmsDojoAction.getPath($('parentid').value, function(data) {
      //var img = "<img src='<c:url value='/images/link.gif'/>' />";
      for(i = data.length-1; i >=0 ; i--){   
          $('pathPane').innerHTML +=( "<font color='#003366'><b>" + data[i] + "</b></font> -> " );
          $('pathPane2').innerHTML +=( "<font color='#003366'><b>" + data[i] + "</b></font> -> " );
      }
          $('pathPane').innerHTML += ("<font color='#003366'><b>" + $('title').value + "</b></font>");    
          $('pathPane2').innerHTML += ("<font color='#003366'><b>" + $('title').value + "</b></font>");
          $('pathPane2').innerHTML += (backFalgStr);
  });
}
function onMouseOut(){
  var msg = document.getElementById("message");
  msg.style.display = 'none';
}

function onMouseOver(divId) {
  var msg = document.getElementById("message");
  var img = "<img src='<c:url value='/images/icons/information.gif'/>' />";
  msg.style.background = "#E8F2FE";
  if (divId == 'idTitle') {
    msg.innerHTML = img + "   <fmt:message key="cms.idTitle.warn"/>";
  } else if (divId == 'idSubtitle') {
    msg.innerHTML = img + "<fmt:message key="cms.idSubtitle.warn"/>";
  } else if (divId == 'idExpireDate') {
    msg.innerHTML = img + "<fmt:message key="cms.idExpireDate.warn"/>";
  } else if (divId == 'idSummary') {
    msg.innerHTML = img + "<fmt:message key="cms.idSummary.warn"/>";
  } else if (divId == 'idBody') {
    msg.innerHTML = img + "<fmt:message key="cms.idBody.warn"/>";
  } else if (divId == 'neverExp') {
    msg.innerHTML = img + "<fmt:message key="cms.neverExp.warn"/>";
  } else if (divId == 'available') {
    msg.innerHTML = img + "<fmt:message key="cms.available.warn"/>";
  } else if (divId == 'visible') {
    msg.innerHTML = img + "<fmt:message key="cms.visible.warn"/>";
  } else if (divId == 'draft') {
    msg.innerHTML = img + "<fmt:message key="cms.draft.warn"/>";
  }
  msg.style.padding = '10px';
  msg.style.borderWidth = "10px";
  msg.style.color = '#003366';
  msg.style.border = '1px solid #6290D2';
  msg.style.display = 'block'; 
}

</script>
<div dojoType="ContentPane" id="pathPane2" layoutAlign="bottom"
	style="height:26px; background-color: #E8F2FE"></div>
<table width='100%' style='margin:10px, padding:2px' align="center"
	border="0">
	<tr>
		<td align="right" width="2%"></td>
		<td colspan="5" align="right">
		 <button dojotype="button" onclick="onSave()"><fmt:message key='global.save'/></button>		</td>
		<td width="20%" rowspan="7" align="right" valign="top" style="top : 10px">
		  <div id="message"
			style="height:38px; margin-right: 2px; text-align: left; "> </div></td>
	</tr>
	<tr>
		<td align="right" width="5%"><fmt:message key="cms.content.title" />:		</td>
		<td colspan="5" align="left"><input type='text' id="title"
			name='title' style="width:500px" onmouseover="onMouseOver('idTitle')"
			onmouseout="onMouseOut()" />  
			<c:if test="${param.contentId != null}">
			    <input type='hidden' id="id" name='id' value="<c:out value='${param.contentId}'/>">
			</c:if> 
			<c:if test="${param.parent != null}">
			    <input type='hidden' id="parentid" name='parentid' value="<c:out value='${param.parent}'/>">
		    </c:if>
		    
		    </td>
	</tr>
	<tr>
		<td align="right"><fmt:message key="cms.content.subtitle" />:</td>
		<td colspan="5" align="left"><input type='text' id="subtitle"
			name='subtitle' style="width:500px"
			onmouseover="onMouseOver('idSubtitle')" onmouseout="onMouseOut()" /></td>
	</tr>
	<tr>
		<td align="right"><fmt:message key="cms.content.expireDate" />:</td>
		<td width="15%" align="left" valign="top"
			onmouseover="onMouseOver('idExpireDate')" onmouseout="onMouseOut()"><input
			dojoType="DropdownDatePicker" widgetid="expireDate" id="expireDate"
			name="expireDate" class="thin-input" onValueChanged="onSetExpireDate" />
		&nbsp;</td>
		<td width="10%" align="left" valign="top"
			onmouseover="onMouseOver('neverExp')" onmouseout="onMouseOut()">
		<fmt:message key="cms.content.neverExpired" /> <input
			name="checkbox4" type="checkbox" id="neverExp" onclick="onNeverExp()"
			checked="checked" /></td>
		<td width="8%" align="left" valign="top"
			onmouseover="onMouseOver('available')" onmouseout="onMouseOut()">
		<fmt:message key="cms.content.available" /> <input name="checkbox3"
			type="checkbox" id="available" checked="checked" /></td>
		<td width="8%" align="left" valign="top"
			onmouseover="onMouseOver('visible')" onmouseout="onMouseOut()">
		<fmt:message key="cms.content.visible" /> <input name="checkbox2"
			type="checkbox" id="visible" checked="checked" /></td>
		<td align="left" valign="top" onmouseover="onMouseOver('draft')"
			onmouseout="onMouseOut()"><fmt:message
			key="cms.content.saveAsDraft" /> <input name="checkbox"
			type="checkbox" id="draft" /></td>
	</tr>
	<tr>
		<td align="right"><fmt:message key="cms.content.summary" />:</td>
		<td colspan="5" align="left" valign="top">
		<textarea rows="2"
			cols="75" id="summary" name="summary" style="width:495px"
			onmouseover="onMouseOver('idSummary')" onmouseout="onMouseOut()">
		</textarea></td>
	</tr>
	<tr onmouseover="onMouseOver('idBody')" onmouseout="onMouseOut()">
		<td align="right"></td>
		<td colspan="5" align="left" valign="top">
		<textarea rows="1" style="height:100px"	cols="1" id="body" name="body">
			<cms:displayField fieldName="body" contentId="contentId">
			</cms:displayField>
		</textarea></td>
	</tr>
	<tr>
		<td align="right" width="2%"></td>
		<td colspan="5" align="right">
		   <button dojotype="button"	>
		        <fmt:message key='global.save'/>
		   </button>
		</td>
		<td></td>
	</tr>
</table>
