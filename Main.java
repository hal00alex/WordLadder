/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main3 {
	
	// static variables and constants only here.
	static String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "N", "M", "O", 
			"P", "Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z"}; 
	
	static Stack<String> stack = new Stack<String>(); 
	static Set<String> DFSvisited = new HashSet<String>(); 
	static Set<String> tmpvisited = new HashSet<String>(); 
	static ArrayList<String> Dne = new ArrayList<String>();
	public static boolean found = false;
	
	static boolean DFSflag; 
	static String end_DFS; 
	static String start_DFS; 
	
	static HashMap<String, String> DFSstore = new HashMap<String, String>(); 
	static Set<String> dictDFS; 
	
	public static void main(String[] args) throws Exception {
		
		//input scanner 
		Scanner kb;	
		//output .txt
		PrintStream ps;	
		
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		
		//now our turn
		initialize();
		parse(kb); 
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		//?stack stuff? 
		//?DFSVisited?  
		
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) { 
		String oneLine = keyboard.nextLine(); 
		//if quit 
		if (oneLine.contains("/quit")){
			return null; 
		}
		
		else {
		
			String[] words = oneLine.split(" "); 
			String start = words[0]; 
			String end = words [1]; 

			DFSflag = false; 
			start_DFS = start.toUpperCase(); 
			end_DFS = end.toUpperCase();
			dictDFS = makeDictionary();
			
			Dne = new ArrayList<String>(); 
			Dne.add(start_DFS);
			Dne.add(end_DFS); 
			//check and return if words are equal
			//special print for same word 
			if (start.equals(end)){
				System.out.println("a ladder 1-rung " + start_DFS.toLowerCase() + " and " + end_DFS.toLowerCase());
				ArrayList<String> returnArr = new ArrayList<String>(); 
				returnArr.add(start); 
				returnArr.add(end); 
				System.out.println(returnArr.toString());
				return returnArr; 
			}
			
			ArrayList<String> DFS_result = new ArrayList<String>();
			DFS_result = getWordLadderDFS(start.toUpperCase(), end.toUpperCase());
			printLadder(DFS_result); 
						
			//System.out.println("BFS");
			
			ArrayList<String> BFS_result = new ArrayList<String>();
			BFS_result = getWordLadderBFS(start.toUpperCase(), end.toUpperCase());
			printLadder(BFS_result); 
			
		}
		return null;
	}

	/**
	 * Prints the ladder from a DFS search using the DFS Store
	 * HashMap created during implementation
	 * @return
	 */
	public static ArrayList<String> PrintDFS(){
		String tempPrint = end_DFS;  
		ArrayList<String> toPrint = new ArrayList<String>();
		
		toPrint.add(end_DFS); 
		
		while (!tempPrint.equals(start_DFS)){
			tempPrint = DFSstore.get(tempPrint); 
			toPrint.add(tempPrint); 
		}
		
		//System.out.println(toPrint);
		//printLadder(toPrint); 
		return toPrint; 
	}
	
	/**
	 * Implements a recursive depth first search that will take the parameter
	 * "start" and see if the word end can be found in a depth first traversal
	 * @param start the word to start from
	 * @param end the word to be found
	 * @param dict the dictionary being passed in 
	 * @return an ArrayList with the word ladder if there is a path or
	 * 		   null if there is not a path between start and end 
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		Set<String> dict = dictDFS; 
		
		try{
			DFSvisited.add(start); 
		}
		catch (StackOverflowError e){
			//return null;
			try{
				return null; 
			}
			catch(StackOverflowError n){
				System.out.println("What");
				int x; 
				x = 10; 
			}
		}
		
		if (start.equals(end)) {
			found = true;
		}
		
		
		for (int k = 0; k < start.length(); k++) {
			for (char c = 'A'; c <= 'Z' && !found; c++) {
				String checkW = start.substring(0, k) + c + start.substring(k + 1);
				if(!DFSvisited.contains(checkW) && dict.contains(checkW) && !checkW.equals(start)){
					DFSstore.put(checkW, start);
					//System.out.println(checkW);
					try {
						getWordLadderDFS(checkW, end);
					}
					
					catch (StackOverflowError f){
						return Dne; 
					}
				}
			}
			
		}
		
		//path
		if (found) return PrintDFS();
		return Dne; 
		
	}

	
	/**
	 * Implements a breadth first search that will take the parameter
	 * "start" and see if the word end can be found in a breadth first traversal
	 * @param start the word to start from
	 * @param end the word to go to
	 * @return
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		Set<String> dict = makeDictionary();
		
		Queue<String> queue = new LinkedList<String>(); 
		Set<String> visited = new HashSet(); 
		HashMap<String, String> store = new HashMap<String, String>(); 
		Stack<String> path = new Stack<String>(); 
		
		queue.add(start); 
		path.add(start); 
		
		while(!queue.isEmpty()){
			String rung = queue.remove(); 
			
			visited.add(rung); 
			
			for (int i = 0 ; i < start.length(); i++){
				
				String checkW = ""; 
				String beg = rung.substring(0,i); 
				String last = rung.substring(i+1, start.length()); 
				
				for (int a = 0; a < alpha.length; a ++){
					checkW = beg + alpha[a] + last; 
					
					//if exist, create list and return 
					if (checkW.equals(end)){
						store.put(checkW, rung);
						String tempPrint = end;  
						ArrayList<String> toPrint = new ArrayList<String>(); 
						toPrint.add(end);
						while (!tempPrint.equals(start.toUpperCase())){
							tempPrint = store.get(tempPrint); 
							toPrint.add(tempPrint); 
						}

						return (toPrint); 
					}
					
					if (dict.contains(checkW) && !checkW.equals(rung) && !queue.contains(checkW) && !visited.contains(checkW)){
						queue.add(checkW); 
						store.put(checkW, rung); 
						visited.add(checkW); 

					}
				}
			}
		}
		
		//no path: System.out.println("All Explored");
		ArrayList<String> Dne = new ArrayList<String>(); 
		Dne.add(start); 
		Dne.add(end); 
		return Dne; 
	}
    
    
    
    /**
     * Creates the dictionary
     * @return a HashSet that has the dictionary
     * GIVEN 
     */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	
	
	public static void printLadder(ArrayList<String> ladder) {
		
		if (ladder == null || ladder.size() == 2){
			System.out.println("no word ladder can be found between " + start_DFS.toLowerCase() + " and " + end_DFS.toLowerCase());
			return; 
		}
		
		System.out.println("a " + ladder.size() + "-rung word ladder exists between " + start_DFS.toLowerCase() + " and " + end_DFS.toLowerCase());
		
		for (int i = ladder.size() - 1; i > -1; i--){
			System.out.println(ladder.get(i).toLowerCase());
		}
	}
}
