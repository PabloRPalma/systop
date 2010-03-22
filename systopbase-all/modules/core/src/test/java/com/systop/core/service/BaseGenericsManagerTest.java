package com.systop.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.BaseCoreTestCase;
import com.systop.core.dao.testmodel.TestUser;

/**
 * @author Sam Lee
 * 
 */
public class BaseGenericsManagerTest extends BaseCoreTestCase {
	private SampleManager sampleManager;

	@Autowired
	public void setSampleManager(SampleManager sampleManager) {
		this.sampleManager = sampleManager;
	}

	/**
	 * Test method for {@link BaseGenericsManager#create(Object)}.
	 */
	public void testCreate() {
		TestUser tu = new TestUser();
		sampleManager.create(tu);
		sampleManager.getDao().flush();
		assertNotNull(tu.getId());
		try {
			sampleManager.create(null);
			fail("It must be wrong!Where is exception?");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link BaseGenericsManager#get(Serializable)}.
	 */
	public void testGetSerializable() {
		TestUser tu = sampleManager.get(1);
		assertNotNull(tu);
		tu = sampleManager.get(1000);
		assertNull(tu);
	}

	/**
	 * Test method for {@link BaseGenericsManager#get()}.
	 */
	public void testGet() {
		List<TestUser> l = sampleManager.get();
		assertTrue(l.size() > 1);
	}

	/**
	 * Test method for {@link BaseGenericsManager#remove(Object)}.
	 */
	public void testRemove() {
		TestUser tu = sampleManager.get(1);
		assertNotNull(tu);
		sampleManager.remove(tu);
	}

	/**
	 * Test method for {@link BaseGenericsManager#save(Object)}.
	 */
	public void testSave() {
		TestUser tu = new TestUser();
		sampleManager.save(tu);
		sampleManager.getDao().flush();
		assertNotNull(tu.getId());

		tu = sampleManager.get(10);
		tu.setLastLoginIp("C");
		sampleManager.save(tu);

		sampleManager.getDao().flush();
	}

	/**
	 * Test method for {@link BaseGenericsManager#update(Object)}.
	 */
	public void testUpdate() {
		TestUser tu = sampleManager.get(10);
		tu.setLastLoginIp("C");
		sampleManager.update(tu);

		sampleManager.getDao().flush();
	}

	/**
	 * Test method for {@link BaseGenericsManager#findObject(String, Object[])}.
	 */
	public void testFind() {
		TestUser tu = sampleManager
				.findObject("from TestUser t where t.loginId='USER01'");
		assertNotNull(tu);
	}

}
