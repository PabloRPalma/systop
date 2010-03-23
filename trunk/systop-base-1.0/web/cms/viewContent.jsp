<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="<c:url value='/js/prototype.js'/>"></script>
<style type="text/css">
.sBodyCSS {
	PADDING-RIGHT: 10px;
	OVERFLOW-Y: auto; 
	PADDING-LEFT: 10px; 
	SCROLLBAR-FACE-COLOR: #ffffff; 
	FONT-SIZE: 11pt; 
	PADDING-BOTTOM: 0px; 
	SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; 
	OVERFLOW: auto; 
	SCROLLBAR-SHADOW-COLOR: #919192; 
	COLOR: black; 
	SCROLLBAR-3DLIGHT-COLOR: #ffffff; 
	LINE-HEIGHT: 100%; 
	SCROLLBAR-ARROW-COLOR: #919192; 
	PADDING-TOP: 0px; 
	SCROLLBAR-TRACK-COLOR: #ffffff; 
	FONT-FAMILY: ????; 
	SCROLLBAR-DARKSHADOW-COLOR: #ffffff; 
	LETTER-SPACING: 1pt; 
	HEIGHT: 398px; 
	TEXT-ALIGN: left">
}

</style>
<div dojoType="pathPane" id="pathPane" layoutAlign="bottom"
	style="height:26px; background-color: #E8F2FE"></div>
<table width="97%">
    <tr>
          <td align="right" width="5%">
              
          </td>
          <td  width="70%">             
          </td>
          <td align="right">
             <img src="<c:url value="/images/back.gif"/>"/>
             <a href="<c:url value="/cms/index.jsp"/>">      
             <font color="#003366"><b><fmt:message key='global.backCmsList'/></b></font>
             </a>
          </td>
      </tr>
 
</table>


	<table width="100%" align="center" style="width:95%; margin:20px;border-top:1px solid #6290d2;border-left:1px solid #6290d2;">
	   <tr>
	   <td style="border-right:1px solid #6290d2;border-bottom:1px solid #6290d2">
			<h3><fmt:message key='cms.content.title' />:
			<span id='stitle' style="color:#000099"></span>
			</h3>
		</td></tr>
		<tr>
		<td style="border-right:1px solid #6290d2;border-bottom:1px solid #6290d2">
		<h3><fmt:message key='cms.article'/>:</h3>
		
			<div id="sbody" class="sBodyCSS"></div>
		
	    </td>
	    </tr>
	    </table>
