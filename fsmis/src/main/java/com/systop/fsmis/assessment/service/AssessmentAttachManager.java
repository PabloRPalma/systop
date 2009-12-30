package com.systop.fsmis.assessment.service;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
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
}
