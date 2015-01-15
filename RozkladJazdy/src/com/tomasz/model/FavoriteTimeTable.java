package com.example.rozkladjazdy;

public class FavoriteTimeTable { //_id, busStopId, lineId, nazwa ||' LINIA: ' || LINIA AS NAZWA from ULUBION
	private int id;
	private String busStopId;
	private String lineId;
	private String nazwa;
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

}
