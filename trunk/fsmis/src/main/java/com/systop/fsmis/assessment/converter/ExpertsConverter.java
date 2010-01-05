package com.systop.fsmis.assessment.converter;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.view.html.ColumnBuilder;
import org.springframework.stereotype.Service;

import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.model.AsseMember;

/**
 * 风险评估专家信息转化类
 * @author ShangHua
 *
 */
@Service
public class ExpertsConverter implements Cell {
	
  /**
   * 根据中间表中专家类别获取其类别下的专家信息
   */
	@SuppressWarnings("unchecked")
	public String getExportDisplay(TableModel table, Column col) {
		StringBuffer leaders = new StringBuffer();
		StringBuffer members = new StringBuffer();
		String expertsDisplay = StringUtils.EMPTY;
		if (col.getPropertyValue() == null) {
			return StringUtils.EMPTY;
		}
		if (col.getPropertyValue() != null) {
			Set<AsseMember> asseMembers = (Set)col.getPropertyValue();
	  	if (CollectionUtils.isNotEmpty(asseMembers)){
		    for (AsseMember asseMember : asseMembers) {
		    	if (col.getAlias().equals("leaders") && asseMember.getType().equals(AssessMentConstants.EXPERT_LEADER)){
		    		leaders.append(asseMember.getExpert().getName());
		    		leaders.append(",");
		    	}
		    	if (col.getAlias().equals("members") && asseMember.getType().equals(AssessMentConstants.EXPERT_MEMBER)) {
		    		members.append(asseMember.getExpert().getName());
		    		members.append(",");
          }    		
		    }
		    //专家组长
		    if (leaders.length() > 0 && leaders.toString().lastIndexOf(",") > 0){
		    	expertsDisplay = leaders.toString().substring(0, leaders.toString().length() -1 );
		    }
		    //专家成员
		    if (members.length() > 0 && members.toString().lastIndexOf(",") > 0){
		    	expertsDisplay = members.toString().substring(0, members.toString().length() -1 );
		    }		    
	  	}
		}
		return expertsDisplay;
	}

	public String getHtmlDisplay(TableModel table, Column col) {
		ColumnBuilder columnBuilder = new ColumnBuilder(col);
		columnBuilder.tdStart();
		columnBuilder.tdBody(getExportDisplay(table, col));
		columnBuilder.tdEnd();
		return columnBuilder.toString();
	}

}
