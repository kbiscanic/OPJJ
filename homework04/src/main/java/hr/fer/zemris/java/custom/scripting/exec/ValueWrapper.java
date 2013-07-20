package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {

	private Object value;
	private Type type;

	public ValueWrapper(Object value) {
		this.value = value;
		this.type = determineType(value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		this.type = determineType(value);
	}

	public void increment(Object incValue) {
		this.arithmetic(incValue, '+');
	}

	public void decrement(Object decValue) {
		this.arithmetic(decValue, '-');
	}

	public void multiply(Object mulValue) {
		this.arithmetic(mulValue, '*');
	}

	public void divide(Object divValue) {
		this.arithmetic(divValue, '/');
	}

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

	private static enum Type {
		INT, DOUBLE, STRING, NULL, UNKNOWN;
	}
}
