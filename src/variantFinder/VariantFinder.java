package variantFinder;



import com.google.api.services.genomics.Genomics;


public class VariantFinder {
	private Genomics genomics;

	public VariantFinder(Genomics genomics) {
		this.genomics=genomics;
	}

	
	// nome non va troppo bene TODO
	public String getVariantsAtPosition(String datasetId, String sample,
			String referenceName, Long referencePosition) {

			CallSetIDGetter cs= new CallSetIDGetter(genomics);
			String callSetId= cs.getCallSetId(datasetId,sample);

			// 2. Once we have the call set ID,
			// lookup the variants that overlap the position we are interested in
			LookUpVariant lk= new LookUpVariant(genomics);
			String variants= lk.lookupvariants(callSetId,referenceName,referencePosition);
			
			return variants;
			
	}
}

	
