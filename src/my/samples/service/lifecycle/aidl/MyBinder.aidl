package my.samples.service.lifecycle.aidl;

interface MyBinder {
	int getPid();
	void basicTypes(boolean aBoolean, int anInt, long aLong, float aFloat,
			double aDouble, String aString);
}