package com.base.dao.exemplo.impl;

import com.base.application.BaseDAOImpl;
import com.base.dao.exemplo.PersonDAO;
import com.base.modelo.exemplo.Person;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class PersonDAOImpl extends BaseDAOImpl<Person> implements PersonDAO {
}
