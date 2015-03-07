package com.tomasz.model;

public class FavoriteTimeTable {
	private int id;
	private String busStopId;
	private String lineId;
	private String nazwa;
	private int icon;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	 @Override
	    public String toString() {
	        return this.nazwa ;
	    }

	public String getBusStopId() {
		return busStopId;
	}

	public void setBusStopId(String busStopId) {
		this.busStopId = busStopId;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
