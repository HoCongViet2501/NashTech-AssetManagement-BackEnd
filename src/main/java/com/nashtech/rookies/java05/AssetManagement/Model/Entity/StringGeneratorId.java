package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.spi.QueryImplementor;

public class StringGeneratorId implements IdentifierGenerator {

	private String prefix = "SD";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		QueryImplementor<String> query = session.createQuery("SELECT u.user_id FROM Users u", String.class);
		try (Stream<String> stream = query.stream()) {
			Long max = stream.map(t -> t.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
			return prefix + String.format("%04d", max + 1);
		}

	}

}
