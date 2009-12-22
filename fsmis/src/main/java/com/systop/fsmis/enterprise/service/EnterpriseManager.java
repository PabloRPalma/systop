package com.systop.fsmis.enterprise.service;

import java.io.File;

import org.apache.commons.lang.xwork.StringUtils;
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
			if (StringUtils.isNotEmpty(enterprise.getBusinessLicense())) {
				throw new ApplicationException("添加的营业执照号已存在。");
			}
		}
		if (getDao().exists(enterprise, "produceLicense")) {
			if (StringUtils.isNotEmpty(enterprise.getProduceLicense())) {
				throw new ApplicationException("添加的生产许可证号已存在。");
			}
		}
		if (getDao().exists(enterprise, "sanitationLicense")) {
			if (StringUtils.isNotEmpty(enterprise.getSanitationLicense())) {
				throw new ApplicationException("添加的卫生许可证号已存在。");
			}
		}
		if (getDao().exists(enterprise, "code")) {
			if (StringUtils.isNotEmpty(enterprise.getCode())) {
				throw new ApplicationException("添加的企业编号已存在。");
			}
		}
		super.save(enterprise);
	}
	
	/**
	 * 删除企业信息，如存在照片则删除照片
	 * @param enterprise 企业
	 * @param realPath 照片地址
	 */
	@Transactional
	public void remove(Enterprise enterprise, String realPath) {
		if (enterprise != null && !enterprise.getPhotoUrl().isEmpty()) {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(enterprise);
	}
}
