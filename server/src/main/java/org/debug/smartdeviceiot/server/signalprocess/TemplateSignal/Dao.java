package org.debug.smartdeviceiot.server.signalprocess.TemplateSignal;

public interface Dao<T> {

    T read(final String path);

    void write(final T obj, final String path);

}
