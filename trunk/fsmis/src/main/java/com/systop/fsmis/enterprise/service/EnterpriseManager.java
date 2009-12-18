package com.systop.fsmis.enterprise.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Enterprise;

/**
 * 企业信息管理类
 * @author DU
 *
 */
@Service
public class EnterpriseManager extends BaseGenericsManager<Enterprise> {

	/**
	 * 保存企业信息,并验证许可证号及编号的唯一性
	 */
	@Transactional
	public void save(Enterprise enterprise) {
		if (getDao().exists(enterprise, "businessLicense")) {
			if (enterprise.getBusinessLicense() != null) {
				throw new ApplicationException("添加的营业执照号已存在。");
			}
		}
		if (getDao().exists(enterprise, "produceLicense")) {
			if (enterprise.getProduceLicense() != null) {
				throw new ApplicationException("添加的生产许可证号已存在。");
			}
		}
		if (getDao().exists(enterprise, "sanitationLicense")) {
			if (enterprise.getSanitationLicense() != null) {
				throw new ApplicationException("添加的卫生许可证号已存在。");
			}
		}
		if (getDao().exists(enterprise, "code")) {
			if (enterprise.getCode() != null) {
				throw new ApplicationException("添加的企业编号已存在。");
			}
		}
		super.save(enterprise);
	}
}
