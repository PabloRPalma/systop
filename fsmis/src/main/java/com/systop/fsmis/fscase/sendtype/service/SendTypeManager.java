package com.systop.fsmis.fscase.sendtype.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.SendType;

/**
 * 用于对SendType的查询和数据修改
 * @author Lunch
 */
@Service
public class SendTypeManager extends BaseGenericsManager<SendType> {

	/**
	 * 查询派遣环节,并根据sortId进行排序
	 * @return
	 */
	public List<SendType> orderSendType() {
		String hql = "from SendType s order by s.sortId";
		return query(hql);
	}
	
	/**
	 * 根据ID判断指定的SendType有没有关联的区县派遣方式
	 * @param id
	 * @return
	 */
	public boolean hasCountySendTypes(Integer id){
		String hql = "select count(c.id) from CountySendType c where c.sendType.id = ?";
		Object count = getDao().findObject(hql, id);
		if (count instanceof Long) {
			logger.debug("sendType.id={},共涉及区县排序方式{}个", new Object[]{id, (Long)count});
			if (((Long)count) > 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据ID判断指定的SendType有没有关联的案件
	 * @param id
	 * @return
	 */
	public boolean hasFsCase(Integer id){
		String hql = "select count(f.id) from FsCase f where f.sendType.id = ?";
		Object count = getDao().findObject(hql, id);
		if (count instanceof Long) {
			logger.debug("sendType.id={},共涉及案件{}起", new Object[]{id, (Long)count});
			if (((Long)count) > 0){
				return true;
			}
		}
		return false;
	}
	

}
