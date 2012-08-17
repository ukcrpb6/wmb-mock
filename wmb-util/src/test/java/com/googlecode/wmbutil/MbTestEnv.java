package com.googlecode.wmbutil;

import com.google.guiceberry.GuiceBerryModule;
import com.google.inject.AbstractModule;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbTestEnv extends AbstractModule {
    @Override protected void configure() {
        install(new GuiceBerryModule());
        Thread.currentThread().setContextClassLoader(new ClassLoader() {
            @Override protected Class<?> findClass(String s) throws ClassNotFoundException {
                return super.findClass(s);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
    }
}
