package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures: public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
	private String _name;
	private String _problemType;
	private HashMap<String, RavensFigure> _figures;
	private HashMap<String, RavensFigure> _answers = new HashMap<String,RavensFigure>();
	private boolean _isVerbal;
	private HashMap<RavensFigure, RavensFigure> _ravenspair;
	private RavensFigure _ravensFigure_a;
	private RavensFigure _ravensFigure_b;
	private RavensFigure _ravensFigure_c;

	/**
	 * The default constructor for your Agent. Make sure to execute any
	 * processing necessary before your Agent starts solving problems here.
	 * 
	 * Do not add any variables to this signature; they will not be used by
	 * main().
	 * 
	 */
	public Agent() {

	}

	/**
	 * The primary method for solving incoming Raven's Progressive Matrices. For
	 * each problem, your Agent's Solve() method will be called. At the
	 * conclusion of Solve(), your Agent should return an int representing its
	 * answer to the question: 1, 2, 3, 4, 5, or 6. Strings of these ints are
	 * also the Names of the individual RavensFigures, obtained through
	 * RavensFigure.getName(). Return a negative number to skip a problem.
	 * 
	 * Make sure to return your answer *as an integer* at the end of Solve().
	 * Returning your answer as a string may cause your program to crash.
	 * 
	 * @param problem
	 *            the RavensProblem your agent should solve
	 * @return your Agent's answer to this problem
	 */
	public int Solve(RavensProblem problem) {
		_name = problem.getName();
		_problemType = problem.getProblemType();
		_figures = problem.getFigures();
		_isVerbal = problem.hasVerbal();
		_ravenspair = new HashMap<>(); // this should give us a pair of match

		if (_name.contains("Basic Problem B")) // only solving for Basic problem
												// B
		{
			GetFigures();
			if (_isVerbal) {
				HashMap<String, RavensObject> aObject = _ravensFigure_a.getObjects();
				HashMap<String, RavensObject> bObject = _ravensFigure_b.getObjects();
				HashMap<String, RavensObject> cObject = _ravensFigure_c.getObjects();

				System.out.println("\n"+problem.getName());
				System.out.println(CompareObjects(aObject, bObject, cObject));
				return CompareObjects(aObject, bObject, cObject);
			}
		}

		return -1;
	}

	private int CompareObjects(HashMap<String, RavensObject> aObject, HashMap<String, RavensObject> bObject, HashMap<String, RavensObject> cObject)
	{
		ArrayList<HashMap<String, String>> attr_a = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String, String>> attr_b = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String, String>> attr_c = new ArrayList<HashMap<String,String>>();
		
		attr_a = SetAttr(aObject);
		attr_b = SetAttr(bObject);
		attr_c = SetAttr(cObject);
		System.out.println(attr_a);
		System.out.println(attr_b);
		System.out.println(attr_c);
		return GetExpectedResult(attr_a, attr_b);
	}
	
	private ArrayList<HashMap<String, String>> SetAttr(HashMap<String, RavensObject> rObject)
	{
		ArrayList<HashMap<String, String>> attr = new ArrayList<HashMap<String,String>>();
		for(String s : rObject.keySet())
		{
			RavensObject srObject = rObject.get(s);
			attr.add(srObject.getAttributes());
		}
		return attr;
	}

	private int GetExpectedResult(ArrayList<HashMap<String, String>> attr_a, ArrayList<HashMap<String, String>> attr_b)
	{
		int ans = -1;
		for(int i = 0; i < attr_a.size(); i++)
		{
			HashMap<String, String> aHash = attr_a.get(i);
			HashMap<String, String>  bHash = attr_b.get(i);
			ans = Sort(aHash, bHash);
		}
		return ans;
	}
	
	private int Sort(HashMap<String, String> aHash, HashMap<String, String> bHash)
	{
		HashMap<String, String> same = new HashMap<>();
		HashMap<String, String> diff = new HashMap<>();
		for(String akey : aHash.keySet())
		{
			if(bHash.containsKey(akey))
			{
				if(aHash.get(akey).equals(bHash.get(akey)))
				{
					same.put(akey, aHash.get(akey));
				}
				else
				{
					diff.put(akey, bHash.get(akey));
				}
			}
		}
		System.out.println("Same: "+same);
		System.out.println("Diff: "+diff);
		
		return GetResult(same, diff);
	}
	
	private int GetResult(HashMap<String, String> same, HashMap<String, String> diff)
	{
		int ans = -1;
		for(RavensFigure rf : _answers.values())
		{
			HashMap<String, RavensObject> object = rf.getObjects();
			for(String s : object.keySet())
			{
				RavensObject sObject = object.get(s);
				HashMap<String, String> attr = sObject.getAttributes();
				if(diff.isEmpty())
				{
					if(same.equals(attr))
					{
						ans = Integer.parseInt(rf.getName());
					}
				}
			}
		}
		return ans;
	}
	
	private void GetFigures() {
		// get first two and compare attributes .. third one we evaluate for..
		for (String name : _figures.keySet()) {
			RavensFigure rf = _figures.get(name);
			if (rf.getName().equals("A")) {
				_ravensFigure_a = rf;
			} else if (rf.getName().equals("B")) {
				_ravensFigure_b = rf;
			} else if (rf.getName().equals("C")) {
				_ravensFigure_c = rf;
			}
			else
			{
				_answers.put(name, rf);
			}
		}
	}
}
