<?xml version="1.0" encoding="UTF-8"?>
<settings>
	<margins>0</margins>                                                   
	<max_series>14410</max_series>
	<add_time_stamp>1</add_time_stamp>
	<redraw>true</redraw>
	<max_grid_count>9</max_grid_count>
	<equal_spacing>1</equal_spacing>
	<legend_position>top</legend_position>
	<legend_width>75</legend_width>
	<number_format>
	    <decimal_separator>.</decimal_separator>
	</number_format>
	<export_as_image> 
      <file>export.do</file>
	</export_as_image>
	<data_sets> 
		<data_set did="0">
			<title><![CDATA[ 　　　　　　　　　　　　　　 <b>${data.stationName}　　测点${data.pointId}　　${data.itemName}　　(${data.sampleName})</b> ]]></title>
			<short><![CDATA[]]></short>
			<color>7f8da9</color>
			<file_name><![CDATA[${data.url}]]></file_name>
			<csv>
				<separator>;</separator>
				<date_format>${data.dateFormat}</date_format>
				<columns>
					<column>date</column>
					<column>close</column>           
				</columns>
			</csv>
		</data_set>
	</data_sets>

	<charts>
		<chart cid="0">
			<height>60</height>
			<bg_color>f5f5f5,ffffff</bg_color>
			<border_color>CCCCCC</border_color>
			<border_alpha>100</border_alpha>
     
			<values>
				<y_left>
					<bg_color>ffffff</bg_color>
					<text_color>000000</text_color>
				</y_left>
			</values>	      

			<legend>
				<show_date>true</show_date>
				<text_color>002b6d</text_color>
				<text_size>12</text_size>
			</legend>

			<column_width>70</column_width>

			<graphs>
				<graph gid="0">
					<type>line</type>
					<data_sources>
						<close>close</close>
					</data_sources>
					<connect>false</connect>
					<legend>
					    <!--注意这里有很多空格！！-->
						<date key="true" title="true"><![CDATA[当前值：<b>{close}</b>]]></date>
						<period key="false" title="false"><![CDATA[起始值：<b>{open}</b> 最小值：<b>{low}</b> 最大值:<b>{high}</b> 结束值:<b>{close}</b>]]></period>
					</legend>    
					<cursor_color>002b6d</cursor_color>
					<positive_color>7f8da9</positive_color>
					<negative_color>db4c3c</negative_color>
					<fill_alpha></fill_alpha>
					     
				</graph>  			
			</graphs>
		</chart>
	</charts>
  

	<data_set_selector>
		<enabled>0</enabled>
	</data_set_selector>
  
	<period_selector>    
		<button>
			<bg_color_hover>FEC514</bg_color_hover>
			<bg_color_selected>DB4C3C</bg_color_selected>
			<text_color_selected>FFFFFF</text_color_selected>
		</button>
  
		<periods>		
		    <period type="${data.periods}" count="1" selected="true">1${data.periodName}</period>
		    <period type="${data.periods}" count="4">4${data.periodName}</period>
		    <period type="${data.periods}" count="8">8${data.periodName}</period>
			
			<period type="MAX">全部</period>
		</periods>
		
		<periods_title>缩放:</periods_title>
		<custom_period_title>定制区间:</custom_period_title> 
	</period_selector>


	<date_formats>
		<!-- [24] (12 / 24) The time in the legend and x axis might be displayed using 12 or 24 hour format -->
		<hour_format>24</hour_format>
		<legend>
	       <seconds>YYYY-MM-DD hh:mm:ss</seconds>
           <minutes>YYYY-MM-DD hh:mm:00</minutes>
	       <hours>YYYY-MM-DD hh:00</hours>
	       <days>YYYY-MM-DD</days>
	       <months>YYYY-MM</months>
	       <years>YYYY</years>
        </legend>
        <x_axis>
           <seconds>${data.dateFormat}</seconds>
           <minutes>${data.dateFormat}</minutes>
	       <hours>${data.dateFormat}</hours>
	       <days>${data.dateFormat}</days>
	       <months>${data.dateFormat}</months>
	       <years>${data.dateFormat}</years>
        </x_axis>
	</date_formats>  

	<header>
		<enabled>1</enabled>
		<text><![CDATA[{title}]]></text>
		<text_size>14</text_size>
	</header>
   
	<scroller>
	    <#if data.sampleRate != '02'>
	    <graph_data_source>close</graph_data_source>
		<graph_color>666666</graph_color>
		<graph_fill_alpha>0</graph_fill_alpha>
		<graph_selected_fill_alpha>1</graph_selected_fill_alpha>
		<connect>false</connect>	
		<playback>
           <enabled>true</enabled>
           <color>002b6d</color>
           <color_hover>db4c3c</color_hover>
           <speed>50</speed>
           <max_speed>100</max_speed>
           <speed_indicator>
              <color>002b6d</color>
           </speed_indicator>
        </playback>
        <#else>
         <playback>
			<enabled>0</enabled>
		</playback>
		</#if>
		<resize_button_color>002b6d</resize_button_color>
	    <bg_color>efefef</bg_color>
		<border_color>CCCCCC</border_color>
		<border_alpha>100</border_alpha>
		
	</scroller>
	<strings>
	    <no_data><![CDATA[没有加载任何数据. ]]></no_data>
	    <export_as_image><![CDATA[导出图片 ]]></export_as_image>
	    <error_in_data_file><![CDATA[数据文件中有错误]]></error_in_data_file>
	    <collecting_data><![CDATA[正在收集数据... ]]></collecting_data>
	    <wrong_zoom_value><![CDATA[错误的缩放值]]></wrong_zoom_value>
	    <processing_data><![CDATA[请稍候，正在处理数据，这可能需要一点时间...]]></processing_data>
	    <loading_data><![CDATA[加载数据...]]></loading_data>
	    <months>
	        <month>1月</month>
	        <month>2月</month>
	        <month>3月</month>
	        <month>4月</month>
	        <month>5月</month>
	        <month>6月</month>
	        <month>7月</month>
	        <month>8月</month>
	        <month>9月</month>
	        <month>10月</month>
	        <month>11月</month>
	        <month>12月</month>
	    </months>
  </strings>
</settings>
