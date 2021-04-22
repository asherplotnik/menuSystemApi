package app.core.util;

import java.time.LocalDateTime;
import java.util.List;

import app.core.enums.OrderType;

public class OrderPayload {
	public List<EntryObj> entries;
	public LocalDateTime time;
	public String note;
	public OrderType orderType;
}
