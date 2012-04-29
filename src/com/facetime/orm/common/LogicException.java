package com.facetime.orm.common;

import com.facetime.orm.logic.Logic;

public class LogicException extends InternelException {
	private static final long serialVersionUID = 1497586079589997059L;

	private Logic logic;

	public LogicException(Logic Logic) {
		this.logic = Logic;
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(Logic Logic, String message) {
		super(message);
		this.logic = Logic;
	}

	public LogicException(String message, Throwable cause, Logic Logic) {
		super(message, cause);
		this.logic = Logic;
	}

	public LogicException(Throwable cause, Logic Logic) {
		super(cause);
		this.logic = Logic;
	}

	public Logic getLogic() {
		return this.logic;
	}
}
