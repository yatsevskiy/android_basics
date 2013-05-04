package my.samples.service.lifecycle.aidl;

import my.samples.service.lifecycle.aidl.MyCallBack;

interface MyBinder {
	int getPid();
	void basicTypes(boolean aBoolean, int anInt, long aLong, float aFloat,
			double aDouble, String aString);
	void register(MyCallBack aMyCallBack);
	void unregister(MyCallBack aMyCallBack);
}