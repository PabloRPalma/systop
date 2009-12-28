package com.systop.fsmis.expert.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Expert;
import com.systop.fsmis.model.ExpertCategory;

/**
 * 专家信息管理Manager
 * @author ShangHua
 *
 */
@Service
public class ExpertManager extends BaseGenericsManager<Expert>{
 
	/**
	 * 保存专家信息,并验证手机号的唯一性
	 */
	@Transactional
	public void save(Expert expert) {
		if (getDao().exists(expert, "mobile")) {
			if (StringUtils.isNotEmpty(expert.getMobile())) {
				throw new ApplicationException("手机号【" + expert.getMobile() + "】已存在。");
			}
		}
		super.save(expert);
	}
	
	/**
   * 查询所有的专家类别
   * @return
   */
	@SuppressWarnings("unchecked")
  public List getExpertCategory() {
    List list = getDao().query("from ExpertCategory ec");
  	if (CollectionUtils.isNotEmpty(list)){
  		return toMapArray(list);	
  	}
    return list;
  }
  
  /**
   * 查询结果中的List中的ExpertCategory对象转换为Map对象.
   * @param list List of ExpertCategory
   * @return list of map
   */
  @SuppressWarnings("unchecked")
  public static List<Map> toMapArray(List<ExpertCategory> list) {
    List<Map> result = new ArrayList(list.size());
    for (ExpertCategory expertCategory : list) {
      Map item = new HashMap();
      item.put("ecId", expertCategory.getId());
      item.put("ecName", expertCategory.getName()); 
      result.add(item);
    }
    return result;
  }
  
	/**
	 * 删除专家信息，如存在照片则删除照片
	 * @param expert 专家
	 * @param realPath 照片地址
	 */
	@Transactional
	public void remove(Expert expert, String realPath) {
		if (expert != null && StringUtils.isNotBlank(expert.getPhotoPath())) {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(expert);
	}
}
