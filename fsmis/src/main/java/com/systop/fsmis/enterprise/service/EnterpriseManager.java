package com.systop.fsmis.enterprise.service;

import java.io.File;
import java.util.List;

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
	
	/**
	 * 删除企业信息，如存在照片则删除照片
	 * @param enterprise 企业
	 * @param realPath 照片地址
	 */
	@Transactional
	public void remove(Enterprise enterprise, String realPath) {
		if (!enterprise.getPhotoUrl().isEmpty()) {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(enterprise);
	}
	
	/**
	 * 根据企业编号取得该企业实体信息
	 */
	public Enterprise getEnterpriseByCode(String code) {
		String hql = "from Enterprise en where en.code=?";
		List<Enterprise> li = query(hql, code);
		if (!li.isEmpty()) {
			return li.get(0);
		}
		return new Enterprise();
	}
}
