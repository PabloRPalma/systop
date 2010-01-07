package com.systop.fsmis.assessment.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.AssessmentAttach;

/**
 * 风险评估信息管理类
 * 
 * @author ShangHua
 * 
 */
@Service
public class AssessmentAttachManager extends
		BaseGenericsManager<AssessmentAttach> {
	
	/**
	 * 删除风险评估信息，如存在附件则删除附件
	 * @param assessment 风险评估案件实体
	 * @param realPath 附件地址的绝对路径
	 */
	@Transactional
	public void removeAttach(AssessmentAttach assAttach, String realPath) {
		if (StringUtils.isNotBlank(realPath)) {// 检查附件保存的路径是否存在
			File file = new File(realPath);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
		super.remove(assAttach);
	}
	
	/**
   * 按照风险评估Id查询所有的专家信息
   * @return
   */
	@SuppressWarnings("unchecked")
  public List getExperts(Integer assessmentId) {
    List list = getDao().query("from AsseMember ass where ass.assessment.id = ? ", assessmentId);
  	if (CollectionUtils.isNotEmpty(list)){
  		return toMapArray(list);	
  	}
    return list;
  }
  
  /**
   * 查询结果中的List中的AsseMember对象转换为Map对象.
   * @param list List of AsseMember
   * @return list of map
   */
  @SuppressWarnings("unchecked")
  public static List<Map> toMapArray(List<AsseMember> list) {
    List<Map> result = new ArrayList(list.size());
    for (AsseMember asseMember : list) {
      Map item = new HashMap();
      item.put("expertId", asseMember.getExpert().getId());
      item.put("expertName", asseMember.getExpert().getName()); 
      result.add(item);
    }
    return result;
  }
}
