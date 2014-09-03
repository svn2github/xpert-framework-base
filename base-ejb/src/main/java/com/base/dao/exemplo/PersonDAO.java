package com.base.dao.exemplo;

import com.xpert.persistence.dao.BaseDAO;
import com.base.modelo.exemplo.Person;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface PersonDAO extends BaseDAO<Person> {
    
}
