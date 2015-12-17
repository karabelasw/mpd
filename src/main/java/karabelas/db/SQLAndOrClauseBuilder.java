package karabelas.db;


import org.apache.commons.lang3.StringUtils;

public class SQLAndOrClauseBuilder {

	
/**
 * 
 * @param searchWords is not empty or null
 * @param delimiter is not empty, if null then defaults to using single space between strings
 * @return an Array of String Objects.  These are used as the parameters for Apache DBUtils queryrunner
 */
	public static Object[] buildParametersFromString(String searchWords, String delimiter){
		Object[] arrayOfParameter = StringUtils.split(searchWords, delimiter);
		return arrayOfParameter;
		
	}
	
/**
 * Precondition: (parameter String...) cannot be null
 * @param strParams -- not null
 * @return an Array with size equivalent to variable array parameter
 * Taking each string passed in and placing it in the array which is returned (arrayOfParameter)
 */
		public static Object[] buildParametersFromStrings(String... strParams){			
			
			Object[] arrayOfParameter = new Object[strParams.length];
			for(int i=0; i<arrayOfParameter.length; i++){
				arrayOfParameter[i]=strParams[i];
			}
			
			return arrayOfParameter;
			
		}
	
	/**
	 * @param numberOfParameters > 0
	 * @param dbFieldName is not empty or null
	 * @return: will return a string in the form of:
	 * 'fieldName Like ? (if numberOfParameters == 1)
	 * 'fieldName Like ? OR fieldName Like ? (NumberOfParameters > 1)
	 */
	public static String buildLikeOrClause(int numberOfParameters, String dbFieldName){
		
		StringBuffer orClauseWithParamHolder = new StringBuffer(100);		
		
		for(int i=0; i < numberOfParameters; i++){			
			orClauseWithParamHolder.append(dbFieldName + " Like ?");
			if(numberOfParameters == (i+1))
				break;
			else{					
				orClauseWithParamHolder.append(" OR ");
			}					
		}
		
		System.out.println(orClauseWithParamHolder.toString());
		return orClauseWithParamHolder.toString();
	}
	
	/**
	 * @param numberOfParameters > 0
	 * @param dbFieldName is not empty or null
	 * @return: will return a string in the form of:
	 * 'fieldName Like ? (if numberOfParameters == 1)
	 * 'fieldName Like ? AND fieldName Like ? (NumberOfParameters > 1)
	 */
	public static String buildLikeAndClause(int numberOfParameters, String dbFieldName){
		
		StringBuffer andClauseWithParamHolder = new StringBuffer(100);
		
		for(int i=0; i < numberOfParameters; i++){
			
			andClauseWithParamHolder.append(dbFieldName + " Like ?");
			if(numberOfParameters == (i+1))
				break;
			else{
				andClauseWithParamHolder.append(" AND ");
			}				
		}
		
		System.out.println(andClauseWithParamHolder.toString());
		return andClauseWithParamHolder.toString();
	}	
	
	/**
	 * Takes in an array of Strings and prepends/appends % to each String
	 * @param searchStrings is not empty or null
	 * @return an array of String Objects with starting and ending with % character
	 * to be used in MySQL Like %searchWord% clause.
	 */
	
	public static Object[] appendWildCardToSearchWords(Object[] searchStrings){
				
		for(int i=0; i < searchStrings.length; i++){
			searchStrings[i] = "%"+searchStrings[i]+"%";							
		}		
		return searchStrings;
	}

	
}
