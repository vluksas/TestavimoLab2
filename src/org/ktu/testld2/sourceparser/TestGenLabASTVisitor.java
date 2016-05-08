package org.ktu.testld2.sourceparser;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.projects.atf.metadata.generic.Type;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TestGenLabASTVisitor extends ASTVisitor{
	
	private List<Class> parsedClasses;
	private String currentSourceName;
	
	public void beginConstructingClassList(String sourceName){
		parsedClasses = new ArrayList<Class>();
		currentSourceName = sourceName;
	}
	public List<Class> getParsedClasses(){
		return parsedClasses;
	}
/* 	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node){
 		
 		System.out.println("Found some sort of method: " + node.getName() + " (returns " + node.getReturnType2());
 		List<SingleVariableDeclaration> params = (List<SingleVariableDeclaration>)node.parameters();
 		for(SingleVariableDeclaration svd : params){
 			System.out.println("This " + node.getName() + " has var " + svd.getName().toString() + " of type " + svd.getType());
 		}
 		return true;
 	}*/
 	public boolean visit(TypeDeclaration node){
 		
 		System.out.println("Found some sort of class: " + node.getName());
 		EnumSet<Modifier> es = EnumSet.of(Modifier.None);
 		Class cl = new Class(node.getName().toString(), es, null, node, currentSourceName);
 		MethodDeclaration[] methods = node.getMethods();
 		for(MethodDeclaration meth : methods){
 			System.out.println("This class \"" + node.getName() + "\" has a method named \"" + meth.getName().toString() + "\" of type \"" + meth.getReturnType2().toString() + "\"");
 			listMethod(cl,meth);
 		}
 		parsedClasses.add(cl);
 		return true;
 	}
 	private void listMethod(Class parent, MethodDeclaration node){
 		Method meth = new Method(parent, node.getName().toString(), Modifier.None , parent, node);
 		parent.addMethod(meth);
 		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = (List<SingleVariableDeclaration>)node.parameters();
 		for(SingleVariableDeclaration svd : params){
 			System.out.println("This method \"" + node.getName() + "\" has a variable \"" + svd.getName().toString() + "\" of type \"" + svd.getType() + "\"");
 			listParameter(meth,svd);
 		}
 		
 	}
 	private void listParameter(Method meth, SingleVariableDeclaration svd){
 		Type tp = new Type(svd.getName().toString(), svd);
 		meth.addParameter(new Parameter(svd.getName().toString(), tp, svd));
 	}
 	
 	
}
