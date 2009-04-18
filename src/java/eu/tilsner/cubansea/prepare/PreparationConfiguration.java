package eu.tilsner.cubansea.prepare;

public interface PreparationConfiguration {
	/**
	 * Determines to what weight the title of a result shall be
	 * considered when creating clusters.
	 * 
	 * @return The weight of the title.
	 */
	public int getResultTitleWeight();

	/**
	 * Determines to what weight the summary of a result shall be
	 * considered when creating clusters.
	 * 
	 * @return The weight of the summary.
	 */
	public int getResultSummaryWeight();
	
	/**
	 * Determines to what weight the url of a result shall be
	 * considered when creating clusters.
	 * 
	 * @return The weight of the url.
	 */
	public int getResultUrlWeight();
}
