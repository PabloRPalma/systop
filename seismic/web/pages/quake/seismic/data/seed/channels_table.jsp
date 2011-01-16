<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<fieldset>
   <legend>通道</legend>
   <table WIDTH="95%" BORDER="0"  CELLSPACING="0" CELLPADDING="0" ALIGN="CENTER">
		<TR BGCOLOR="#FFFFFF">
		<td WIDTH="14">&nbsp;</td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHE,BHN,BHZ,SHE,SHN,SHZ"><span class="cha" style="color:red;">ALL</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHE,SHE"><span class="cha" style="color:brown;">??E</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHN,SHN"><span class="cha" style="color:brown;">??N</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHZ,SHZ"><span class="cha" style="color:brown;">??Z</span></td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		</TR>
		<TR BGCOLOR="#FFFFFF">
		<td WIDTH="14">&nbsp;</td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHE,BHN,BHZ"><span class="cha" style="color:green;">B??</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHE" class="chan"><span class="cha" style="color:blue;">BHE</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHN" class="chan"><span class="cha" style="color:blue;">BHN</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="BHZ" class="chan"><span class="cha" style="color:blue;">BHZ</span></td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		</TR>
		<TR BGCOLOR="#FFFFFF">
		<td WIDTH="14">&nbsp;</td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="SHE,SHN,SHZ"><span class="cha" style="color:green;">S??</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="SHE" class="chan"><span class="cha" style="color:blue;">SHE</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="SHN" class="chan"><span class="cha" style="color:blue;">SHN</span></td>
		<td><INPUT TYPE="checkbox" NAME="channels" VALUE="SHZ" class="chan"><span class="cha" style="color:blue;">SHZ</span></td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		<td WIDTH="14">&nbsp;</td>
		</TR>
  	</table>
</fieldset>
<script>
$(function(){
	var cha = ${cha};
	$('.chan').attr('disabled', 'disabled');
	//将存在的通道标示出来
	if(cha) {
		  $('.cha').each(function(idx, item){
			  for(var i = 0; i < cha.length; i++) {
				  var sp = $(item);
				  if(cha[i] == sp.html()) {
					  sp.css("font-weight", "bold");
				  }
			  }
		  });
		  //不存在的通道设置为disable
		  $('.chan').each(function(idx, item){
			  for(var i = 0; i < cha.length; i++) {
				  var cbox = $(item);
				  if(cha[i] == cbox.val()) {
					  cbox.attr('disabled', '');
				  }
			  }
		  }); 
	 }		
});
</script>