package com.skill.exception;

/**
 * 秒杀相关的所有业务异常
 */
public class SeckillException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}