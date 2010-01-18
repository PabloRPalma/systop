package com.systop.fsmis.fscase.jointtask.service;


import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.JointTaskAttach;
/**
 * 联合整治任务附件service层类
 * @author ShangHua
 *
 */
@Service
public class JointTaskAttachManager extends BaseGenericsManager<JointTaskAttach> {

	
	/**
   * 根据联合整治任务Id获取任务附件表的信息
	 * @param jointTaskId 联合整治任务Id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<JointTaskAttach> getTaskAttachs(Integer jointTaskId) {
		String hql = "from JointTaskAttach jta where jta.jointTask.id = ? ";
		return getDao().query(hql, jointTaskId);
	}

	/**
	 * 删除联合整治任务信息，如存在附件则删除附件
	 * @param taskAttach 联合整治任务附件实体
	 * @param realPath 附件地址的绝对路径
	 */
	@Transactional
	public void removeAttach(JointTaskAttach taskAttach, String realPath) {
		if (StringUtils.isNotBlank(realPath)) {// 检查附件保存的路径是否存在
			File file = new File(realPath);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
		super.remove(taskAttach);
	}
}
