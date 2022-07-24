package com.nashtech.rookies.java05.AssetManagement.generator;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyGenerator implements IdentifierGenerator{

	private String prefix = "SD";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		String query = "SELECT u.id FROM User u";
		Stream<String> ids = session.createQuery(query, String.class).stream();
		Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
		return prefix + (String.format("%04d", max + 1));

	}

}
