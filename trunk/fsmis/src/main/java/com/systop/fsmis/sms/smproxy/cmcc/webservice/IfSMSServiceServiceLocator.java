/**
 * IfSMSServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.systop.fsmis.sms.smproxy.cmcc.webservice;

@SuppressWarnings("serial")
public class IfSMSServiceServiceLocator extends org.apache.axis.client.Service
		implements IfSMSServiceService {

	public IfSMSServiceServiceLocator() {
	}

	public IfSMSServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public IfSMSServiceServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for IfSMSService
	private java.lang.String IfSMSService_address = "http://61.233.42.4:8080/masproxy/services/IfSMSService";

	public java.lang.String getIfSMSServiceAddress() {
		return IfSMSService_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String IfSMSServiceWSDDServiceName = "IfSMSService";

	public java.lang.String getIfSMSServiceWSDDServiceName() {
		return IfSMSServiceWSDDServiceName;
	}

	public void setIfSMSServiceWSDDServiceName(java.lang.String name) {
		IfSMSServiceWSDDServiceName = name;
	}

	public IfSMSService getIfSMSService() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(IfSMSService_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getIfSMSService(endpoint);
	}

	public IfSMSService getIfSMSService(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			IfSMSServiceSoapBindingStub _stub = new IfSMSServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getIfSMSServiceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setIfSMSServiceEndpointAddress(java.lang.String address) {
		IfSMSService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */

	@SuppressWarnings("unchecked")
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (IfSMSService.class.isAssignableFrom(serviceEndpointInterface)) {
				IfSMSServiceSoapBindingStub _stub = new IfSMSServiceSoapBindingStub(
						new java.net.URL(IfSMSService_address), this);
				_stub.setPortName(getIfSMSServiceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	@SuppressWarnings("unchecked")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("IfSMSService".equals(inputPortName)) {
			return getIfSMSService();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://service.mas.hx.com",
				"IfSMSServiceService");
	}

	@SuppressWarnings("unchecked")
	private java.util.HashSet ports = null;

	@SuppressWarnings("unchecked")
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://service.mas.hx.com",
					"IfSMSService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("IfSMSService".equals(portName)) {
			setIfSMSServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
