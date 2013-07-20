package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class used to wrap objects.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ValueWrapper {

	/**
	 * Method used to get Type of wrapped object.
	 * 
	 * @return Type.
	 */
	public Type getType() {
		return type;
	}

	private Object value;
	private Type type;

	/**
	 * Default constructor.
	 * 
	 * @param value
	 *            Value that needs to be wrapped.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
		this.type = determineType(value);
	}

	/**
	 * Method used to return value it has wrapped.
	 * 
	 * @return value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method used to set value of this object.
	 * 
	 * @param value
	 *            New value.
	 */
	public void setValue(Object value) {
		this.value = value;
		this.type = determineType(value);
	}

	/**
	 * Method used to increment value of this object by <code>incValue</code>.
	 * 
	 * @param incValue
	 *            Value by which are we incrementing our value.
	 */
	public void increment(Object incValue) {
		this.arithmetic(incValue, '+');
	}

	/**
	 * Method used to decrement value of this object by <code>decValue</code>.
	 * 
	 * @param decValue
	 *            Value by which are we decrementing our value.
	 */
	public void decrement(Object decValue) {
		this.arithmetic(decValue, '-');
	}

	/**
	 * Method used to multiply value of this object by <code>mulValue</code>.
	 * 
	 * @param mulValue
	 *            Value by which are we multiplying our value.
	 */
	public void multiply(Object mulValue) {
		this.arithmetic(mulValue, '*');
	}

	/**
	 * Method used to divide value of this object by <code>divValue</code>.
	 * 
	 * @param divValue
	 *            Value by which are we dividing our value.
	 */
	public void divide(Object divValue) {
		this.arithmetic(divValue, '/');
	}

	/**
	 * Method used to compare value of this object by <code>withValue</code>.
	 * 
	 * @param withValue
	 *            Value by which are we comparing our value.
	 * @return 0 if both values are same.
	 */
	public int numCompare(Object withValue) {
		ValueWrapper temp = new ValueWrapper(value);
		temp.decrement(withValue);
		return (int) temp.getValue();
	}

	private void arithmetic(Object argument1, char op) {
		Object argument = argument1;
		Type argType = determineType(argument);
		if (argType == Type.UNKNOWN || type == Type.UNKNOWN) {
			throw new IllegalArgumentException(
					"Both objects must be null, Integer, Double or String!");
		}
		if (type == Type.STRING) {
			if (isDouble((String) value)) {
				this.value = Double.parseDouble((String) value);
				this.type = Type.DOUBLE;
			} else {
				this.value = Integer.parseInt((String) value);
				this.type = Type.INT;
			}
		} else if (type == Type.NULL) {
			this.value = Integer.valueOf(0);
			this.type = Type.INT;
		}

		if (argType == Type.STRING) {
			if (isDouble((String) argument)) {
				argument = Double.parseDouble((String) argument);
				argType = Type.DOUBLE;
			} else {
				argument = Integer.parseInt((String) argument);
				argType = Type.INT;
			}
		} else if (argType == Type.NULL) {
			argument = Integer.valueOf(0);
			argType = Type.INT;
		}
		if (type == Type.INT && argType == Type.INT) {
			switch (op) {
			case '+':
				this.value = intInc(argument);
				break;
			case '-':
				this.value = intDec(argument);
				break;
			case '*':
				this.value = intMul(argument);
				break;
			case '/':
				this.value = intDiv(argument);
				break;
			default:
				break;
			}
		} else {
			switch (op) {
			case '+':
				this.value = doubInc(argument, argType);
				break;
			case '-':
				this.value = doubDec(argument, argType);
				break;
			case '*':
				this.value = doubMul(argument, argType);
				break;
			case '/':
				this.value = doubDiv(argument, argType);
				break;
			default:
				break;
			}
			this.type = Type.DOUBLE;
		}
	}

	private Integer intInc(Object argument) {
		return (Integer) this.value + (Integer) argument;
	}

	private Double doubInc(Object argument1, Type argType) {
		Object argument = argument1;
		if (argType == Type.DOUBLE && this.type == Type.DOUBLE) {
			return (Double) this.value + (Double) argument;
		} else if (argType == Type.DOUBLE) {
			return (Integer) this.value + (Double) argument;
		} else {
			return (Double) this.value + (Integer) argument;
		}
	}

	private Integer intDec(Object argument) {
		return (Integer) this.value - (Integer) argument;
	}

	private Double doubDec(Object argument, Type argType) {
		if (argType == Type.DOUBLE && this.type == Type.DOUBLE) {
			return (Double) this.value - (Double) argument;
		} else if (argType == Type.DOUBLE) {
			return (Integer) this.value - (Double) argument;
		} else {
			return (Double) this.value - (Integer) argument;
		}
	}

	private Integer intMul(Object argument) {
		return (Integer) this.value * (Integer) argument;
	}

	private Double doubMul(Object argument, Type argType) {
		if (argType == Type.DOUBLE && this.type == Type.DOUBLE) {
			return (Double) this.value * (Double) argument;
		} else if (argType == Type.DOUBLE) {
			return (Integer) this.value * (Double) argument;
		} else {
			return (Double) this.value * (Integer) argument;
		}
	}

	private Integer intDiv(Object argument) {
		return (Integer) this.value / (Integer) argument;
	}

	private Double doubDiv(Object argument, Type argType) {
		if (argType == Type.DOUBLE && this.type == Type.DOUBLE) {
			return (Double) this.value / (Double) argument;
		} else if (argType == Type.DOUBLE) {
			return (Integer) this.value / (Double) argument;
		} else {
			return (Double) this.value / (Integer) argument;
		}
	}

	private boolean isDouble(String value) {
		if (value.contains(".") || value.contains("E")) {
			return true;
		} else {
			return false;
		}
	}

	private static Type determineType(Object value) {
		if (value == null) {
			return Type.NULL;
		} else if (value instanceof Integer) {
			return Type.INT;
		} else if (value instanceof Double) {
			return Type.DOUBLE;
		} else if (value instanceof String) {
			return Type.STRING;
		} else {
			return Type.UNKNOWN;
		}
	}

	/**
	 * Enumeration used to describe current object stored in this
	 * {@link ValueWrapper}.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static enum Type {
		INT, DOUBLE, STRING, NULL, UNKNOWN;
	}
}
