package com.systop.fsmis.sms.smproxy.impl.cmccws.webservice;

public class IfSMSServiceProxy implements IfSMSService {
  private String _endpoint = null;
  private IfSMSService ifSMSService = null;
  
  public IfSMSServiceProxy() {
    _initIfSMSServiceProxy();
  }
  
  public IfSMSServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIfSMSServiceProxy();
  }
  
  private void _initIfSMSServiceProxy() {
    try {
      ifSMSService = (new IfSMSServiceServiceLocator()).getIfSMSService();
      if (ifSMSService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ifSMSService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ifSMSService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ifSMSService != null)
      ((javax.xml.rpc.Stub)ifSMSService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public IfSMSService getIfSMSService() {
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService;
  }
  
  public int[] send(java.lang.String account, java.lang.String password, java.lang.String[] destAddr, java.lang.String content) throws java.rmi.RemoteException{
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService.send(account, password, destAddr, content);
  }
  
  public java.lang.String[] testService(java.lang.String[] str) throws java.rmi.RemoteException{
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService.testService(str);
  }
  
  public java.lang.String[] receive(java.lang.String account, java.lang.String password, java.lang.String destAddr) throws java.rmi.RemoteException{
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService.receive(account, password, destAddr);
  }
  
  public java.lang.String[] querySmReport(java.lang.String account, java.lang.String password, int smId) throws java.rmi.RemoteException{
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService.querySmReport(account, password, smId);
  }
  
  public int[] sendState(java.lang.String account, java.lang.String password, java.lang.String[] destAddr, java.lang.String content, int needStateReport) throws java.rmi.RemoteException{
    if (ifSMSService == null)
      _initIfSMSServiceProxy();
    return ifSMSService.sendState(account, password, destAddr, content, needStateReport);
  }
  
  
}