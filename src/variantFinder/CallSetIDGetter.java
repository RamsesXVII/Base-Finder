package variantFinder;

import java.util.List;

import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.model.CallSet;
import com.google.api.services.genomics.model.SearchCallSetsRequest;
import com.google.common.collect.Lists;

public class CallSetIDGetter {
	
	private Genomics genomics;
	
	public CallSetIDGetter(Genomics genomics){	
		this.genomics=genomics;
	}

	public String getCallSetId(String datasetId, String sample) {
		/*
		 * Cerca il call set ID di sample
		 */
		try{
		SearchCallSetsRequest callSetsReq = new SearchCallSetsRequest()
		.setVariantSetIds(Lists.newArrayList(datasetId))
		.setName(sample);

		List<CallSet> callSets = genomics.callsets().search(callSetsReq)
				.setFields("callSets(id)").execute().getCallSets();
	
		if (callSets == null || callSets.size() != 1) {
			return("Searching for " + sample
					+ " didn't return the right number of call sets");
		}
		return callSets.get(0).getId();
		
		}catch (Throwable t) {
	      t.printStackTrace();
	    }
		return null;
	  }
	}
