package gr.scriptum.eprotocol.ws;

/**
 *  Makes a Serach Request with actual content or comma separated keywords to the Document Management System
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class RequestSearch extends Request{

	private String content;
	private String [] keywords;
	private Integer offset;
	private Integer limit;
	
	public RequestSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestSearch(Request req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	public RequestSearch(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.SEARCH);
		// TODO Auto-generated constructor stub
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}
