package au.edu.adelaide.enigma.provanance;


/**
 * This class receives the environmental information form Information Service,
 * then quantizes it into a number that represent the level of granularity of 
 * provenance information that need to be collected. 
 * 
 * @author Mehdi Sarikhani
 *
 */
public class InformationServiceQuantizer {
	
	
	
    private static InformationServiceQuantizer instance = null;
    private InformationServiceQuantizer() { }
    public static synchronized InformationServiceQuantizer getInstance() {
        if (instance == null) {
            instance = new InformationServiceQuantizer();
        }
        return instance;
    }
//	public InformationServiceQuantizer(){
//		setGranularityLevel(9);
//	} 

	/*
	 * granularityLevel:
	 * 
	 *   0 = fine-grained - DEFAULT 
	 *   1 = medium-grained
	 *   2 = coarse-grained 
	 *   
	 *   9 = No Provenance
	 */
	public int granularityLevel ;
	
	public int getGranularityLevel() {
		return granularityLevel;
	}
	public void setGranularityLevel(int granularityLevel) {
		this.granularityLevel = granularityLevel;
	}
}
