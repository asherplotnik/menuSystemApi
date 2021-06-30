package app.core.util;

import java.util.List;

import app.core.enums.OrderType;

public class OrderPayload {
	public List<EntryObj> entries;
	public String note;
	public OrderType orderType;
}
