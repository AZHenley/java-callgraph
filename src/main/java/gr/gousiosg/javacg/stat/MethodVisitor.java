/*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gr.gousiosg.javacg.stat;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;

/**
 * The simplest of method visitors, prints any invoked method
 * signature for all method invocations.
 * 
 * Class copied with modifications from CJKM: http://www.spinellis.gr/sw/ckjm/
 */
public class MethodVisitor extends EmptyVisitor {

    JavaClass visitedClass;
    private MethodGen mg;
    private ConstantPoolGen cp;
    private String format;
    
    private String fileName;
    private String className;
    private String methodName;
    private String lineNumber;

    public MethodVisitor(MethodGen m, JavaClass jc) {
        visitedClass = jc;
        mg = m;
        cp = mg.getConstantPool();
        format = visitedClass.getSourceFileName() + "!!!" + visitedClass.getClassName() + "!!!" + mg.getName() + 
                "!!!" + mg.getLineNumbers()[0].getLineNumber().getLineNumber() + "!!!" + "%s!!!%s!!!%s";
        fileName = visitedClass.getSourceFileName();
        className = visitedClass.getClassName();
        methodName = mg.getName();
        lineNumber = Integer.toString(mg.getLineNumbers()[0].getLineNumber().getLineNumber()); 
    }

    public void start() {
        if (mg.isAbstract() || mg.isNative())
            return;
        for (InstructionHandle ih = mg.getInstructionList().getStart(); 
                ih != null; ih = ih.getNext()) {
            Instruction i = ih.getInstruction();
            
            if (!visitInstruction(i))
                i.accept(this);
        }
    }

    private boolean visitInstruction(Instruction i) {
        short opcode = i.getOpcode();
        return ((InstructionConst.getInstruction(opcode) != null)
                && !(i instanceof ConstantPushInstruction) 
                && !(i instanceof ReturnInstruction));
    }

    public CallGraphNode processMethod(String fN, String fCN, String mN, String lN) {
    	return JCallGraph.addNode(fCN + "!!!" + mN, new CallGraphNode(fN, fCN, mN, lN));
    }
    
    @Override
    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
        System.out.println(String.format(format,"M",i.getClassName(cp),i.getMethodName(cp)));
        CallGraphNode node = processMethod(fileName, className, methodName, lineNumber);
        node.AddMethodCalledFromThis(i.getClassName(cp), i.getMethodName(cp));
        CallGraphNode otherNode = JCallGraph.allNodes.get(i.getClassName(cp) + "!!!" + i.getMethodName(cp));
        if(otherNode != null)
        	otherNode.AddMethodThatCallsThis(className, methodName);
    }

    @Override
    public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
        System.out.println(String.format(format,"I",i.getClassName(cp),i.getMethodName(cp)));
        CallGraphNode node = processMethod(fileName, className, methodName, lineNumber);
        node.AddMethodCalledFromThis(i.getClassName(cp), i.getMethodName(cp));
        CallGraphNode otherNode = JCallGraph.allNodes.get(i.getClassName(cp) + "!!!" + i.getMethodName(cp));
        if(otherNode != null)
        	otherNode.AddMethodThatCallsThis(className, methodName);
    }

    @Override
    public void visitINVOKESPECIAL(INVOKESPECIAL i) {
        System.out.println(String.format(format,"O",i.getClassName(cp),i.getMethodName(cp)));
        CallGraphNode node = processMethod(fileName, className, methodName, lineNumber);
        node.AddMethodCalledFromThis(i.getClassName(cp), i.getMethodName(cp));
        CallGraphNode otherNode = JCallGraph.allNodes.get(i.getClassName(cp) + "!!!" + i.getMethodName(cp));
        if(otherNode != null)
        	otherNode.AddMethodThatCallsThis(className, methodName);
    }

    @Override
    public void visitINVOKESTATIC(INVOKESTATIC i) {
        System.out.println(String.format(format,"S",i.getClassName(cp),i.getMethodName(cp)));
        CallGraphNode node = processMethod(fileName, className, methodName, lineNumber);
        node.AddMethodCalledFromThis(i.getClassName(cp), i.getMethodName(cp));
        CallGraphNode otherNode = JCallGraph.allNodes.get(i.getClassName(cp) + "!!!" + i.getMethodName(cp));
        if(otherNode != null)
        	otherNode.AddMethodThatCallsThis(className, methodName);
    }

    @Override
    public void visitINVOKEDYNAMIC(INVOKEDYNAMIC i) {
        System.out.println(String.format(format,"D",i.getClassName(cp),i.getMethodName(cp)));
        CallGraphNode node = processMethod(fileName, className, methodName, lineNumber);
        node.AddMethodCalledFromThis(i.getClassName(cp), i.getMethodName(cp));
        CallGraphNode otherNode = JCallGraph.allNodes.get(i.getClassName(cp) + "!!!" + i.getMethodName(cp));
        if(otherNode != null)
        	otherNode.AddMethodThatCallsThis(className, methodName);
    }
}
