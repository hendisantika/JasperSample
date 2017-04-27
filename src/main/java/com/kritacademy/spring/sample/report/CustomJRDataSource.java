package com.kritacademy.spring.sample.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
/*
* Credit: https://github.com/evgenyigumnov/spring-boot-security-rest-thymleaf-angularjs-bootstrap-jasperreports-jpa-seed
*/
public class CustomJRDataSource<T> implements JRDataSource {

    private Iterator<T> iterator;
    private Object currentObject;

    @Override
    public boolean next() throws JRException {
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
