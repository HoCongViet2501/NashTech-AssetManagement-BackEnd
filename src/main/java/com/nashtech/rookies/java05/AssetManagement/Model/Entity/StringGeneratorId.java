package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.spi.QueryImplementor;

public class StringGeneratorId implements IdentifierGenerator {

	private final String prefix = "SD";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		QueryImplementor<String> query = session.createQuery("SELECT u.id FROM User u", String.class);
		try (Stream<String> stream = query.stream()) {
			long max = stream.map(t -> t.replace(prefix, ""))
					.mapToLong(Long::parseLong)
					.max()
					.orElse(0L);
			return prefix + String.format("%04d", max + 1);
		}

	}

}
