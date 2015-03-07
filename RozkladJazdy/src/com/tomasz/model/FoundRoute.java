package com.tomasz.model;

public class FoundRoute {
	private String numberLine;
	private String depTime;
	private String depTimeOnChangeBusStop;
	private String arriveTime;
	private String arriveTimeOnChangeBusStop;
	private String arriveTimeOnDestSecLine;
	private String changeBusStop;
	private String numberChangeLine;
	public String getNumberLine() {
		return numberLine;
	}
	public void setNumberLine(String numberLine) {
		this.numberLine = numberLine;
	}
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getChangeBusStop() {
		return changeBusStop;
	}
	public void setChangeBusStop(String changeBusStop) {
		this.changeBusStop = changeBusStop;
	}
	public String getNumberChangeLine() {
		return numberChangeLine;
	}
	public void setNumberChangeLine(String numberChangeLine) {
		this.numberChangeLine = numberChangeLine;
	}
	public String getArriveTimeOnChangeBusStop() {
		return arriveTimeOnChangeBusStop;
	}
	public void setArriveTimeOnChangeBusStop(String arriveTimeOnChangeBusStop) {
		this.arriveTimeOnChangeBusStop = arriveTimeOnChangeBusStop;
	}
	public String getDepTimeOnChangeBusStop() {
		return depTimeOnChangeBusStop;
	}
	public void setDepTimeOnChangeBusStop(String depTimeOnChangeBusStop) {
		this.depTimeOnChangeBusStop = depTimeOnChangeBusStop;
	}
	public String getArriveTimeOnDestSecLine() {
		return arriveTimeOnDestSecLine;
	}
	public void setArriveTimeOnDestSecLine(String arriveTimeOnDestSecLine) {
		this.arriveTimeOnDestSecLine = arriveTimeOnDestSecLine;
	}
}
