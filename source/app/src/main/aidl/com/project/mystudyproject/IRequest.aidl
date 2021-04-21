// IRequest.aidl
package com.project.mystudyproject;

// Declare any non-default types here with import statements
import com.project.mystudyproject.IResponse;
interface IRequest {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void connect();
    void disconnect();
    void registerCallback(IResponse response);
    void unregisterCallback();
}