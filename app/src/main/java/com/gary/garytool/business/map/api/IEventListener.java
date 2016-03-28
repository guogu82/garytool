package com.gary.garytool.business.map.api;

public interface IEventListener {

	/**
	 * 事件监听接口。
	 *
	 * @param e Event基类的子类
	 */
	public void Run(Event e);
}
