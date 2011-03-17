package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ScriptumDocument;

import java.util.ArrayList;
import java.util.UUID;

public class ResponseSearch extends Response{

	ArrayList<ScriptumDocument> searchResults = new ArrayList<ScriptumDocument>();
	
	public ResponseSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseSearch(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseSearch(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseSearch(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseSearch(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ScriptumDocument> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList<ScriptumDocument> searchResults) {
		this.searchResults = searchResults;
	}
	
	public void addDocument(ScriptumDocument doc){
		this.searchResults.add(doc);
	}
	

}
