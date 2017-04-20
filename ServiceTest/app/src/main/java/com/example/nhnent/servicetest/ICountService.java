/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/nhnent/android-study/ServiceStudy/app/src/main/aidl/com/example/nhnent/servicestudy/ICountService.aidl
 */
package com.example.nhnent.servicetest;
// Declare any non-default types here with import statements

public interface ICountService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.nhnent.servicetest.ICountService
{
private static final String DESCRIPTOR = "com.example.nhnent.servicestudy.ICountService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.nhnent.servicestudy.ICountService interface,
 * generating a proxy if needed.
 */
public static com.example.nhnent.servicetest.ICountService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.nhnent.servicetest.ICountService))) {
return ((com.example.nhnent.servicetest.ICountService)iin);
}
return new com.example.nhnent.servicetest.ICountService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getCurrentNumber:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrentNumber();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.nhnent.servicetest.ICountService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int getCurrentNumber() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentNumber, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getCurrentNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public int getCurrentNumber() throws android.os.RemoteException;
}
