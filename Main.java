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

public class Main {
	
	// static variables and constants only here.
	static String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "N", "M", "O", "P", "Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z"}; 
	static Stack<String> stack = new Stack<String>(); 
	static Set<String> DFSvisited = new HashSet<String>(); 
	static Set<String> tmpvisited = new HashSet<String>(); 
	static HashMap<String, String> DFSstore = new HashMap<String, String>(); 
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
		// TO DO
		System.out.println("Type: "); 
		String oneLine = keyboard.nextLine(); 
		//if quit 
		if (oneLine.equals("quit") == true){
			//some flag
		}
		//uppercase 
		//String to Array 
		else{
		
			String[] words = oneLine.split(" "); 
			String start = words[0]; 
			String end = words [1]; 
			//System.out.println(start + end);
			
			//call each function 
			System.out.println("DFS"); 
			stack.push(start.toUpperCase()); 
			getWordLadderDFS(start.toUpperCase(), end.toUpperCase()); 
			System.out.println("BFS");
			getWordLadderBFS(start.toUpperCase(), end.toUpperCase());
			///(start, end); 
		}
		return null;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		//stack.push(start); 
		//DFSvisited.add(start); 
		
		
		/*if(DFSvisited.contains(start)){
			//System.out.println("Help!"); 
			return null; 
		}*/ 
		
		if(stack.isEmpty()){
			System.out.println("Done");
			//print 
			String tempPrint = end;  
			ArrayList<String> toPrint = new ArrayList<String>(); 
			toPrint.add(end); 
			while (!tempPrint.equals(start)){
				tempPrint = DFSstore.get(tempPrint); 
				toPrint.add(tempPrint); 
			}
			//update later!
			System.out.println(toPrint);
			return null; 
		}
		
		if(start.equals(end)){
			stack.clear();
		}
		//StringNode root = new StringNode(null, start, null); 
		//Tree tree  = new Tree(root); 
		if(!stack.isEmpty()){
			String rung = start; 
			//System.out.println("Stack: " + stack.toString());
			//String rung = stack.pop(); 
			System.out.println("Checking " + rung);
			tmpvisited.add(rung); 
			//DFSvisited.add(rung); 
			//System.out.println("Going");
			//For each letter in the word
			for (int i = 0 ; i < start.length(); i++){
				//check to see if one letter exists
				String checkW = ""; 
				String beg = rung.substring(0,i); 
				String last = rung.substring(i+1, start.length()); 
				for (int a = 0; a < alpha.length; a ++){
					checkW = beg + alpha[a] + last; 
					//System.out.println(checkW); 
					//if exist, add to queue 
					if (dict.contains(checkW) == true && !checkW.equals(rung) && !DFSvisited.contains(checkW) && !tmpvisited.contains(checkW)){
						//System.out.println("Hello");
						//queue.add(checkW); 
						stack.push(checkW); 
						//System.out.println("Stack: " + stack.toString());
						//DFSvisited.add(checkW); 
						tmpvisited.add(checkW); 
						DFSstore.put(checkW, rung); 
						//recursion!!!!
						getWordLadderDFS(checkW, end); 
						//stack.pop(); 
					}
				}
			}
			DFSvisited.add(rung); 
			//clear tmp 
			stack.pop(); 
			tmpvisited.clear();
			System.out.println("finished with " + rung);
			//System.out.println(DFSvisited.toString());
		}
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		Queue<String> queue = new LinkedList<String>(); 
		Set<String> visited = new HashSet(); 
		HashMap<String, String> store = new HashMap<String, String>(); 
		Stack<String> path = new Stack<String>(); 
		queue.add(start); 
		path.add(start); 
		//StringNode root = new StringNode(null, start, null); 
		//Tree tree  = new Tree(root); 
		while(!queue.isEmpty()){
			String rung = queue.remove(); 
			//System.out.println("Checking " + rung);
			visited.add(rung); 
			//System.out.println("Going");
			//For each letter in the word
			for (int i = 0 ; i < start.length(); i++){
				//check to see if one letter exists
				String checkW = ""; 
				String beg = rung.substring(0,i); 
				String last = rung.substring(i+1, start.length()); 
				for (int a = 0; a < alpha.length; a ++){
					checkW = beg + alpha[a] + last; 
					//System.out.println(checkW); 
					//if exist, add to queue 
					if (checkW.equals(end)){
						//System.out.println("Found"); 
						//return (null); 
					}
					if (dict.contains(checkW) == true && !checkW.equals(rung) && !queue.contains(checkW) && !visited.contains(checkW)){
						queue.add(checkW); 
						store.put(checkW, rung); 
						//StringNode leaf = new StringNode(rung, checkW); 
						visited.add(checkW); 
						//System.out.println(queue.toString());
					}
				}
			}
		}
		//print 
		String tempPrint = end;  
		ArrayList<String> toPrint = new ArrayList<String>(); 
		toPrint.add(end); 
		while (!tempPrint.equals(start)){
			tempPrint = store.get(tempPrint); 
			toPrint.add(tempPrint); 
		}
		System.out.println(toPrint);
		// TODO more code
		//queue 
		//while queue not empty 
			//remove 
			//for each letter 
				//put one letter off in queue 
		//dequeue 
		System.out.println("Lost or DNE?");
		return null; // replace this line later with real return
	}
    
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
		
	}
	// TODO
	// Other private static methods here
}
