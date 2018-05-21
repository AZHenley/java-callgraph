package gr.gousiosg.javacg.stat;

import java.util.LinkedList;

public class CallGraphNode {
	
	String fileName; // File of the code that is doing the calling
	String fullClassName; // Full path to the class with the method doing the calling
	String methodName; // Name of the method that is doing the calling
	String lineNumber; // Line number of the method signature of the method that is doing the calling (it is sometimes off by a few lines, always greater than the number expected)
	String calledFullClassName; // Full path to the class with the method being called
	String calledMethodName; //Name of the method that is being called
	
	LinkedList<String> methodsCalledFromThis;
	LinkedList<String> methodsThatCallThis;

	
	public CallGraphNode(String fN, String fCN, String mN, String lN, String cFCN, String cMN) {
		fileName = fN;
		fullClassName = fCN;
		methodName = mN;
		lineNumber = lN;
		calledFullClassName = cFCN;
		calledMethodName = cMN;
		
		methodsCalledFromThis = new LinkedList<String>();
		methodsCalledFromThis.add(calledFullClassName + "$$$" + calledMethodName); 
	}
	
	public void AddMethodCalledFromThis() {
		
	}
	
	public void AddMethodThatCallsThis() {
		
	}

}
