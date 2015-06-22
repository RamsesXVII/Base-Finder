package variantFinder;

import java.util.List;

import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.model.SearchVariantsRequest;
import com.google.api.services.genomics.model.Variant;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class LookUpVariant {

	private Genomics genomics;

	public LookUpVariant(Genomics genomics) {
		this.genomics=genomics;
	}

	public String lookupvariants(String callSetId, String referenceName,
			Long referencePosition) {

		try{
			
		SearchVariantsRequest variantsReq = new SearchVariantsRequest()
		.setCallSetIds(Lists.newArrayList(callSetId))
		.setReferenceName(referenceName)
		.setStart(referencePosition)
		.setEnd(referencePosition + 1);
		System.out.println(variantsReq.toString()+"  richiesta");


		Variant variant = genomics.variants().search(variantsReq)
				.setFields("variants(names,referenceBases,alternateBases,calls(genotype))")
				.execute().getVariants().get(0);

		System.out.println(variant.toString()+ " mutazione");
		String variantName = variant.getNames().get(0);

		List<String> genotype = Lists.newArrayList();
		for (Integer g : variant.getCalls().get(0).getGenotype()) {
			if (g == 0) {
				genotype.add(variant.getReferenceBases());
			} else {
				genotype.add(variant.getAlternateBases().get(g - 1));
			}
		}

		return("the called genotype is " + Joiner.on(',').join(genotype)
				+ " at " + variantName);
	}catch (IllegalStateException e) {
		System.err.println(e.getMessage());
	} catch (Throwable t) {
		t.printStackTrace();
	}
	return null;
}}