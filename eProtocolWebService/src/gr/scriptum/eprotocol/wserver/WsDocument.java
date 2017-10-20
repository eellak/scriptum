package gr.scriptum.eprotocol.wserver;

import java.util.Arrays;


/**
 * This is what a document looks like when a client needs to upload it by calling receiveProtocol()
 * method
 *@author Mike Mountrakis mountrakis@uit.gr
 *
 */

public class WsDocument{
	String   title;
	String   filename;
	byte     content[];
	Integer  byteFileSize;
	Integer  index;
	
	
	public WsDocument(){
	}
	
	public final String getTitle() {
		return title;
	}
	public final void setTitle(String title) {
		this.title = title;
	}
	public final String getFilename() {
		return filename;
	}
	public final void setFilename(String filename) {
		this.filename = filename;
	}
	public final byte[] getContent() {
		return content;
	}
	public final void setContent(byte[] content) {
		this.content = content;
	}
	public final Integer getByteFileSize() {
		return byteFileSize;
	}
	public final void setByteFileSize(Integer byteFileSize) {
		this.byteFileSize = byteFileSize;
	}
	public final Integer getIndex() {
		return index;
	}
	public final void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "WsDocument [title=" + title + ", filename=" + filename
				+ ", content=" + Arrays.toString(content) + ", byteFileSize="
				+ byteFileSize + ", index=" + index + "]";
	}
	
}
