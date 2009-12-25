package com.systop.fsmis.corp.service;

import java.io.File;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Corp;

/**
 * 企业信息管理类
 * @author DU
 *
 */
@Service
public class CorpManager extends BaseGenericsManager<Corp> {

	/**
	 * 保存企业信息,并验证许可证号及编号的唯一性
	 */
	@Transactional
	public void save(Corp corp) {
		if (getDao().exists(corp, "businessLicense")) {
			if (StringUtils.isNotEmpty(corp.getBusinessLicense())) {
				throw new ApplicationException("添加的营业执照号已存在。");
			}
		}
		if (getDao().exists(corp, "produceLicense")) {
			if (StringUtils.isNotEmpty(corp.getProduceLicense())) {
				throw new ApplicationException("添加的生产许可证号已存在。");
			}
		}
		if (getDao().exists(corp, "sanitationLicense")) {
			if (StringUtils.isNotEmpty(corp.getSanitationLicense())) {
				throw new ApplicationException("添加的卫生许可证号已存在。");
			}
		}
		if (getDao().exists(corp, "code")) {
			if (StringUtils.isNotEmpty(corp.getCode())) {
				throw new ApplicationException("添加的企业编号已存在。");
			}
		}
		super.save(corp);
	}
	
	/**
	 * 删除企业信息，如存在照片则删除照片
	 * @param corp 企业
	 * @param realPath 照片地址
	 */
	@Transactional
	public void remove(Corp corp, String realPath) {
		if (corp != null && !corp.getPhotoUrl().isEmpty()) {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(corp);
	}
}
