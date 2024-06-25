package com.jackson.puppy.mybatis.dao.sql.provider;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @author Kevin
 * @since 4/16/2018
 */
@Component
public class AbstractSqlProvider {

	protected final static String COMMA = ",";

	protected final static String L_BRACKET = "(";

	protected final static String R_BRACKET = ")";

	protected static String forEachClause(Map<String, Object> map, String keyName, String open, String separator, String close) {
		Object[] array;
		final Object object = map.get(keyName);
		if (object instanceof Collection) {
			array = ((Collection) object).toArray();
		} else if (object != null && object.getClass().isArray()) {
			array = (Object[]) object;
		} else {
			return "";
		}
		final StringBuilder sb = new StringBuilder(open);
		sb.append("#{").append(keyName).append("[0]}");
		for (int i = 1; i < array.length; i++) {
			sb.append(separator);
			sb.append(" #{").append(keyName).append("[").append(i).append("]}");
		}
		sb.append(close);
		return sb.toString();
	}

}
