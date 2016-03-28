package com.gary.garytool.business.map.api;

import java.text.DecimalFormat;

public class MonitorLatLng {

	private Coordinates m_centerCoordinates;
	private Coordinates m_minCoordinates;
	private Coordinates m_maxCoordinates;
	DecimalFormat df=new DecimalFormat("#0.000");
	
	public Coordinates getCenter() {
		return m_centerCoordinates;	
	}
	
	public void setCenter(Coordinates center) {
		m_centerCoordinates=center;
        //m_centerCoordinates.X=Double.valueOf(center.X);
		//m_centerCoordinates.Y=Double.valueOf(center.Y);
	}
	
	public Coordinates getMin() {
		return m_minCoordinates;
	}
	
	public void setMin(Coordinates min) {
		m_minCoordinates=min;
		//m_minCoordinates.X=Double.valueOf(min.X);
		//m_minCoordinates.Y=Double.valueOf(min.Y);
	}
	
	public Coordinates getMax() {
		return m_maxCoordinates;
	}
	
	public void setMax(Coordinates max) {
		m_maxCoordinates=max;
		//m_maxCoordinates.X=Double.valueOf(max.X);
		//m_maxCoordinates.Y=Double.valueOf(max.Y);
	}
}
