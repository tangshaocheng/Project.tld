package com.energysh.egame.dao;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class MMySQLDialect extends MySQLDialect {

	public MMySQLDialect() {
		super();
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_INTEGER.getName());
		registerHibernateType(Types.LONGVARBINARY, Hibernate.STRING.getName());
		registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());
	}
}
