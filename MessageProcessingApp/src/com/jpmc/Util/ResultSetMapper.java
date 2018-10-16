package com.jpmc.Util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jpmc.DO.Product;

/**
 * Mapper class to convert result set in to POJO
 * 
 * @author Chaitali
 *
 * @param <T>
 */
public class ResultSetMapper<T> {
	@SuppressWarnings("unchecked")
	public List<T> mapRersultSetToObject(ResultSet rs, Class outputClass) {
		List<T> outputList = null;
		try {
			// make sure resultset is not null
			if (rs != null) {
				// get the resultset metadata
				ResultSetMetaData rsmd = rs.getMetaData();
				// get all the attributes of outputClass
				Field[] fields = outputClass.getDeclaredFields();
				while (rs.next()) {
					T bean = (T) outputClass.newInstance();
					for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
						// getting the SQL column name
						String columnName = rsmd.getColumnName(_iterator + 1);
						// reading the value of the SQL column
						Object columnValue = rs.getObject(_iterator + 1);
						for (Field field : fields) {
							String propertyName = field.getName();
							if (propertyName.equalsIgnoreCase(columnName) && columnValue != null) {
								bean = setProperty(bean, field.getName(), columnValue);
								break;
							}
						}
					}
					if (outputList == null) {
						outputList = new ArrayList<T>();
					}
					outputList.add(bean);
				}

			} else {
				return null;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return outputList;
	}

	private T setProperty(T bean, String name, Object columnValue) {
		if (bean instanceof Product) {
			switch (name) {
			case "pid":
				((Product) bean).setPid((Integer) columnValue);
				break;
			case "type":
				((Product) bean).setType((String) columnValue);
				break;
			case "value":
				((Product) bean).setValue((Integer) columnValue);
				break;
			case "quantity":
				((Product) bean).setQuantity((Integer) columnValue);
				break;
			case "sales":
				((Product) bean).setSales((Integer) columnValue);
				break;
			}

		}

		return bean;
	}

}
