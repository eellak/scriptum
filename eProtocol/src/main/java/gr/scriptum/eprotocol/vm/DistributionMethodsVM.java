package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.eprotocol.csv.DistributionMethodConverter;

@AfterCompose(superclass=true)
@Init(superclass=true)
public class DistributionMethodsVM
		extends GenericSearchVM<DistributionMethod, DistributionMethodDAO, DistributionMethodConverter> {

	@Override
	public String getEntityPage() {
		return DistributionMethodVM.PAGE;
	}

}
