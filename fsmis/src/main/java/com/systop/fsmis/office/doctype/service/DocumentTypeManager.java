package com.systop.fsmis.office.doctype.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.DocumentType;

/**
 * 内部文章栏目管理类
 * 
 * @author ZW
 * 
 */
@Service
public class DocumentTypeManager extends BaseGenericsManager<DocumentType> {

	/**
	 * 返回栏目列表,有层次标识.
	 * 
	 * @param parC2
	 *            父级，如果为空为获取顶级栏目
	 * 
	 */
	@SuppressWarnings( { "unchecked" })
	public ArrayList getDocumentTypesList(int signNumber) {
		// 栏目树
		ArrayList list = new ArrayList();

		Map parent = new HashMap();
		// 默认的根目录
		parent.put("id", null);
		parent.put("name", null);
		// 查询子栏目
		getChildCatalog(list, parent, 0);
		return list;
	}

	/**
	 * 得到子栏目,每一个栏目用一个<code>java.util.Map</code>表示。本方法是一个递归函数
	 * 
	 * @param parent
	 *            父栏目
	 * @param signNumber
	 *            符号的个数,用于区别栏目的级别.
	 */
	@SuppressWarnings("unchecked")
	public void getChildCatalog(ArrayList list, Map parent, int signNumber) {
		// 栏目级别的符号
		String sign = null;
		// 得到子栏目
		List<DocumentType> documentTypes = getByParentId((Integer) parent
				.get("id"));

		// 若查询结果为空,说明此栏目下没有子栏,退出.
		if (documentTypes.isEmpty() || documentTypes.size() == 0) {
			return;
		}
		// 转换所有子栏目为Map对象，防止dwr造成延迟加载，
		for (int i = 0; i < documentTypes.size(); i++) {
			DocumentType documentType = documentTypes.get(i);
			if (i == documentTypes.size() - 1) {
				// 末尾的级别符号
				sign = "└";
				sign = getSign(sign, signNumber);
			} else {
				// 中国的级别符号
				sign = "├";
				sign = getSign(sign, signNumber);
			}
			Map child = new HashMap();
			child.put("name", sign + documentType.getName());
			child.put("id", documentType.getId());
			// 将子栏目加入栏目的树型结构
			list.add(child);
			// 子栏目的级别加1
			int number = signNumber + 1;
			// 递归查询子栏目
			getChildCatalog(list, child, number);
		}
	}

	/**
	 * 根据指定的父栏目id查询子栏目
	 * 
	 * @parentCatalogId 父栏的id
	 */
	@SuppressWarnings("unchecked")
	private List<DocumentType> getByParentId(Integer parentCatalogId) {
		List list = Collections.EMPTY_LIST;
		if (parentCatalogId == null) {
			// 查询二级栏目
			list = getDao().query(
					"from DocumentType d where parentDocumentType is null");
		} else {
			// 查询子栏目
			list = getDao().query(
					"from DocumentType d where d.parentDocumentType.id = ?",
					parentCatalogId);
		}
		return list;
	}

	/**
	 * 计算栏目的级别
	 * 
	 * @param sign
	 *            栏目的级别符号
	 * @param signNumber
	 *            栏目的级别
	 * @return String 栏目的级别
	 */
	public String getSign(String sign, int signNumber) {
		for (int number = 0; number < signNumber; number++) {
			sign = "│" + sign;
		}
		return sign;
	}
}
