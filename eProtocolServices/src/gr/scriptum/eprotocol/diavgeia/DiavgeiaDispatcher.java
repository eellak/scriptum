package gr.scriptum.eprotocol.diavgeia;

import java.util.List;

import gr.scriptum.domain.OutgoingProtocol;

public interface DiavgeiaDispatcher {
	List<DiavgeiaReceipt> sendOutgoingProtocol( OutgoingProtocol outProtocol, 
			                                    DiavgeiaReferenceData diavgeiaData ) throws Exception;
}
