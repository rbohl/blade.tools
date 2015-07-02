
public class DLDLImplDLUtilgetEntriesTest {

	public void testMethod(DLImpl dlImpl){
		DL dl = new DLImpl();
		//var call
		dl.getEntries(hits);
		dl.getEntries();
		
		//static call
		DL.getEntries();
		
		//passed var call
		dlImpl.getEntries();
	}
	

	public List<Object> getEntries(Hits hits) {
		//field call
		return dlUtil.getEntries(hits);
	}
	
	private DLUtil dlUtil;

}
