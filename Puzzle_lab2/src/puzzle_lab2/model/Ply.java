package puzzle_lab2.model;

/**
 * Model qui represente un mouvement
 * @author Marc-Antoine
 *
 */
public class Ply {

	/*VARIABLE*/
	//Case de depart
	private Case caseStart;
	//Case de fin
	private Case caseEnd;
	
	/**
	 * Constructeur par default
	 */
	public Ply(){
		this.caseStart = new Case();
		this.caseEnd = new Case();
	}
	
	/**
	 * Constructeur
	 * @param caseStart : Case de depart
	 * @param caseEnd : Case de fin
	 */
	public Ply(Case caseStart, Case caseEnd){
		this.caseStart = caseStart;
		this.caseEnd = caseEnd;
	}

	/**
	 * GET case de depart
	 * @return la case de depart
	 */
	public Case getCaseStart() {
		return caseStart;
	}

	/**
	 * SET case de depart
	 * @param caseStart : case de depart
	 */
	public void setCaseStart(Case caseStart) {
		this.caseStart = caseStart;
	}

	/**
	 * GET case de fin
	 * @return la case de fin
	 */
	public Case getCaseEnd() {
		return caseEnd;
	}

	/**
	 * SET case de fin
	 * @param caseEnd : case de fin
	 */
	public void setCaseEnd(Case caseEnd) {
		this.caseEnd = caseEnd;
	}
}
