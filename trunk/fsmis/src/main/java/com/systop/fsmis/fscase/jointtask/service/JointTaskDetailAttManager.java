package com.systop.fsmis.fscase.jointtask.service;


import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.JointTaskDetailAttach;
/**
 * 联合整治任务明细附件service层类
 * @author ShangHua
 *
 */
@Service
public class JointTaskDetailAttManager extends BaseGenericsManager<JointTaskDetailAttach> {

	
	/**
   * 根据联合整治任务明细Id获取任务明细附件表的信息
	 * @param jointTaskId 联合整治任务明细Id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<JointTaskDetailAttach> getTaskDetails(Integer jointTaskDetailId) {
		String hql = "from JointTaskDetailAttach jtda where jtda.jointTaskDetail.id = ? ";
		return getDao().query(hql, jointTaskDetailId);
	}

	/**
	 * 删除联合整治任务明细信息，如存在附件则删除附件
	 * @param taskDetailAttach 联合整治任务明细附件实体
	 * @param realPath 附件地址的绝对路径
	 */
	@Transactional
	public void removeDetailAttach(JointTaskDetailAttach taskDetailAttach, String realPath) {
		if (StringUtils.isNotBlank(realPath)) {// 检查附件保存的路径是否存在
			File file = new File(realPath);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
		super.remove(taskDetailAttach);
	}
}
