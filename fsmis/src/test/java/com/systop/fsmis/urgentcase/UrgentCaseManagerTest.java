package com.systop.fsmis.urgentcase;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.urgentcase.service.UrgentCaseManager;

public class UrgentCaseManagerTest extends BaseTransactionalTestCase {
	
	@SuppressWarnings("unused")
	@Autowired
	private UrgentCaseManager urgentCaseManager;
}
