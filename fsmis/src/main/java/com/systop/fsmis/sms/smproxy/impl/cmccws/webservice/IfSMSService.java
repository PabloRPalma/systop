/**
 * IfSMSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.systop.fsmis.sms.smproxy.impl.cmccws.webservice;

public interface IfSMSService extends java.rmi.Remote {
    public int[] send(java.lang.String account, java.lang.String password, java.lang.String[] destAddr, java.lang.String content) throws java.rmi.RemoteException;
    public java.lang.String[] testService(java.lang.String[] str) throws java.rmi.RemoteException;
    public java.lang.String[] receive(java.lang.String account, java.lang.String password, java.lang.String destAddr) throws java.rmi.RemoteException;
    public java.lang.String[] querySmReport(java.lang.String account, java.lang.String password, int smId) throws java.rmi.RemoteException;
    public int[] sendState(java.lang.String account, java.lang.String password, java.lang.String[] destAddr, java.lang.String content, int needStateReport) throws java.rmi.RemoteException;
}
