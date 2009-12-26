package com.systop.fsmis.office.doctype.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.DocumentType;

/**
 * 内部文章栏目管理类
 * @author ZW
 *
 */
@Service
public class DocumentTypeManager extends BaseGenericsManager<DocumentType> {

	/**
	 * 返回的栏目list
	 */
	@SuppressWarnings("unchecked")
	private List<Map> catalogList;
	
	/**
	 * 返回栏目列表,有层次标识.
	 * 
	 * @param parC2 父级，如果为空为获取顶级栏目
	 * 
	 */
	@SuppressWarnings( { "unchecked" })
	public void getDocumentTypesList(DocumentType parC2, int iSpaceNum/*递归时空格缩进计数*/) {
		if (parC2 == null || catalogList == null) {
			iSpaceNum = 0;
			catalogList = new ArrayList();
			List<DocumentType> parentCatalogs = query("from DocumentType d where d.parentDocumentType is null");
			for (DocumentType parC : parentCatalogs) {
				Map parentMap = new HashMap();
				parentMap.put("id", parC.getId());
				parentMap.put("name", parC.getName());
				catalogList.add(parentMap);
				getDocumentTypesList(parC, iSpaceNum);
			}
		} else {
			for (DocumentType childC : parC2.getChildDocumentTypes()) {
				Map childMap = new HashMap();
				childMap.put("id", childC.getId());
				
				String sSpace = new String("　");
				for(int i = 0; i < iSpaceNum; i++){
					sSpace += sSpace;
				}
				
				childMap.put("name",  sSpace + "> " + childC.getName());
				catalogList.add(childMap);
				iSpaceNum++;
				getDocumentTypesList(childC, iSpaceNum);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getDocumentTypeList() {
		return catalogList;
	}
	@SuppressWarnings("unchecked")
	public void setCatalogList(List catalogList) {
		this.catalogList = catalogList;
	}
}
