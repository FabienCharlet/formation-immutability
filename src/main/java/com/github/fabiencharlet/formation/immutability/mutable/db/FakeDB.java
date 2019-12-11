package com.github.fabiencharlet.formation.immutability.mutable.db;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FakeDB<T> {

	private final Map<String, Object> dummyDb = new HashMap<>();

	public void insert(String id, T o) {

		if (dummyDb.containsKey(id)) {

			throw new RuntimeException(id + " already in DB");
		}

		dummyDb.put(id, o);
	}


	@SuppressWarnings("unchecked")
	public T select(String id) {

		if ( ! dummyDb.containsKey(id)) {

			throw new RuntimeException(id + " does not exist in DB");
		}

		T o = (T) dummyDb.get(id);

		try {
			Method cloneMethod = o.getClass().getMethod("clone");
			cloneMethod.setAccessible(true);

			return (T) cloneMethod.invoke(o);

		} catch (Exception e) {
			throw new RuntimeException(id + " error while cloning", e);
		}
	}

	public String firstId() {

		return dummyDb.keySet().iterator().next();
	}

	public void update(String id, T o) {

		if ( ! dummyDb.containsKey(id)) {
			throw new RuntimeException(id + " does not exist in DB");
		}

		dummyDb.put(id, o);
	}

	public void delete(String id) {

		if ( ! dummyDb.containsKey(id)) {
			throw new RuntimeException(id + " does not exist in DB");
		}

		dummyDb.remove(id);
	}
}
