<?xml version="1.0" encoding="UTF-8" ?>
<settings> 
  <data_type>csv</data_type>
  <font>Tahoma</font> 
  <hide_bullets_count>240</hide_bullets_count>     
  <add_time_stamp>1</add_time_stamp>
  
   <background>
    <border_color>000000</border_color>
    <border_alpha>10</border_alpha>
   </background>
     
  <plot_area>   
    <margins>   
      <left>50</left> 
      <top>60</top>   
      <right>40</right>  
      <bottom>65</bottom> 
    </margins>

  </plot_area>
  
  <grid>       
    <x>                                                             
      <alpha>10</alpha> 
      <approx_count>9</approx_count> 
    </x>
    <y_left>      
      <alpha>10</alpha>   
    </y_left>
  </grid>

  
  <axes>                              
    <x>                               
      <color>0D8ECF</color>          
      <width>1</width>   
    </x>
    <y_left>   
      <color>0D8ECF</color>  
      <width>1</width>  
    </y_left>
  </axes>  
  
  <indicator>           
    <color>0D8ECF</color>
    <line_alpha>50</line_alpha>
    <selection_color>0D8ECF</selection_color>
    <selection_alpha>20</selection_alpha>     
    <x_balloon_text_color>FFFFFF</x_balloon_text_color>
  </indicator>
    
  <legend>
    <text_color_hover>FF0000</text_color_hover>    
  </legend>  
  
  <zoom_out_button>
     <text_color_hover>FF0F00</text_color_hover>      
  </zoom_out_button> 
  
  <labels>               
    <label>
      <x>0</x>  
      <y>25</y> 
      <align>center</align>     
      <text_size>13</text_size>              
      <text>                                 
        <![CDATA[<b>台站：${data.stationName}    测点：${data.pointName}    分量：${data.itemName}    采样率：${data.sampleName}</b>]]>
      </text>        
    </label>
    
    <label>
      <x>0</x> 
      <y>390</y>
      <width>800</width> 
      <align>right</align>  
      <text_color>#999999</text_color>

      <text_size>11</text_size>  
      <text>                   
        <![CDATA[源: <a href="http://" target="_blank"><u>河北省地震局</u></a>]]>
      </text>        
    </label>
  </labels>
  
  <export_as_image>                                           
    <file>export.do</file>  
    <target></target> 
    <x>0</x>
    <y>25</y>
    <text_color>0D8ECF</text_color>
  </export_as_image>
  
  <strings>
    <no_data><![CDATA[没有加载任何数据. ]]></no_data>
    <export_as_image><![CDATA[导出图片 ]]></export_as_image>
    <error_in_data_file><![CDATA[数据文件中有错误]]></error_in_data_file>
    <collecting_data><![CDATA[正在收集数据... ]]></collecting_data>
    <wrong_zoom_value><![CDATA[错误的缩放值]]></wrong_zoom_value>
  </strings>
  
  <graphs>     
    <graph gid="1">  
      <color>0D8ECF</color>  
      <color_hover>FF0F00</color_hover>
      <selected>1</selected>   
      <visible_in_legend>0</visible_in_legend>
    </graph>   
  </graphs>  

</settings>