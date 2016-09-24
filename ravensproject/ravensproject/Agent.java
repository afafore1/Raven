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

				ArrayList<RavensObject> ravensObject_a = GetRavensObject(aObject);
				ArrayList<RavensObject> ravensObject_b = GetRavensObject(bObject);
				ArrayList<RavensObject> ravensObject_c = GetRavensObject(cObject);

				System.out.println("\n"+problem.getName());
				CompareAandB(ravensObject_a, ravensObject_b);

			}
		}

		return -1;
	}

	private ArrayList<RavensObject> GetRavensObject(HashMap<String, RavensObject> obj) {
		ArrayList<RavensObject> rList = new ArrayList<>();
		for (String key : obj.keySet()) {
			RavensObject r = obj.get(key);
			rList.add(r);
		}
		return rList;
	}

	private void CompareAandB(ArrayList<RavensObject> a, ArrayList<RavensObject> b) {
		HashMap<String, String> attr_a = null;
		HashMap<String, String> attr_b = null;
		for(RavensObject r : a)
		{
			System.out.println(r.getAttributes());
		}
		
		for(RavensObject r : b)
		{
			System.out.println(r.getAttributes());
		}
		
		System.out.println(attr_a);
		System.out.println(attr_b);
		
		//System.out.println("Names : " + a.getName() + " " + b.getName());
	}

	private void GetFigures() {
		// get first two and compare attributes .. third one we evaluate for..
		for (RavensFigure rf : _figures.values()) {
			if (rf.getName().equals("A")) {
				_ravensFigure_a = rf;
			} else if (rf.getName().equals("B")) {
				_ravensFigure_b = rf;
			} else if (rf.getName().equals("C")) {
				_ravensFigure_c = rf;
			}

		}
	}
}
