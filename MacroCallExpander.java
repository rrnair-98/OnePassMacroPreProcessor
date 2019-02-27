import java.util.ArrayList;
public class MacroCallExpander{
	private String line;
	private ArrayList<String> mdt;
	private MacroNameEntry entry;
	public MacroCallExpander(String line, ArrayList<String> macroDefinitionTable, MacroNameEntry mnte){
		this.line = line;
		this.mdt = macroDefinitionTable;
		
		this.entry = mnte;

	}

	public String expand(ArrayList<String> ala){
		// since i dont want to start from macro definition
		int i = this.entry.getMdtOffset() ;  
		String str;
		StringBuilder expandedMacro = new StringBuilder();


		while( !(str=this.mdt.get(i)).equals("MEND") ){
			str = entry.replaceAllAlaEntries(str, ala);

			expandedMacro.append(str).append("\n");
			i++;
		}
		return expandedMacro.toString();

	}




}