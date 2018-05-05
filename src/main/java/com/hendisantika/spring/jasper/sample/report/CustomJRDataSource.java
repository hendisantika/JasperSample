package com.hendisantika.spring.jasper.sample.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/05/18
 * Time: 08.54
 * To change this template use File | Settings | File Templates.
 */
public class CustomJRDataSource<T> implements JRDataSource {

    private Iterator<T> iterator;
    private Object currentObject;

    @Override
    public boolean next() {
        if (iterator.hasNext()) {
            currentObject = iterator.next();
            return true;
        }
        return false;

    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(currentObject.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (pd.getName().equals(jrField.getName())) {
                    Method reader = pd.getReadMethod();
                    return reader.invoke(currentObject);
                }
            }
        } catch (Exception e) {
            throw new JRException(e);
        }

        throw new JRException("Field " + jrField.getName() + " error");
    }

    public CustomJRDataSource<T> using(List<T> list) {
        this.iterator = list.iterator();
        return this;
    }
}
