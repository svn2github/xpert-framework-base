package com.base.mb.exemplo;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.base.bo.exemplo.PersonBO;
import com.base.modelo.exemplo.Person;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class PersonMB extends AbstractBaseBean<Person> implements Serializable {

    @EJB
    private PersonBO personBO;

    @Override
    public PersonBO getBO() {
        return personBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
