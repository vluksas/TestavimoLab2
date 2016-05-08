package org.ktu.testld2.sourceparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.sarunas.atf.meta.sut.Class;

import org.apache.commons.io.*;

public class SourceFileParser {
	//use ASTParse to parse string
		public static List<Class> parse(String str, String fPath) {
			 ASTParser parser = ASTParser.newParser(AST.JLS8);  // handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6
			 parser.setSource(str.toCharArray());
			 // In order to parse 1.5 code, some compiler options need to be set to 1.5
			 Map options = JavaCore.getOptions();
			 JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			 parser.setCompilerOptions(options);
			 CompilationUnit result = (CompilationUnit) parser.createAST(null);
			 
			 TestGenLabASTVisitor visitor = new TestGenLabASTVisitor();
			 visitor.beginConstructingClassList(fPath);
			 result.accept(visitor);
			 return visitor.getParsedClasses();
			 
		/*	ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
	 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			IJavaElement je = cu.getJavaElement();
	 
			cu.accept(new ASTVisitor() {
	 
				Set names = new HashSet();
	 
				public boolean visit(VariableDeclarationFragment node) {
					SimpleName name = node.getName();
					this.names.add(name.getIdentifier());
					System.out.println("Declaration of '" + name + "' at line"
							+ cu.getLineNumber(name.getStartPosition()));
					return false; // do not continue 
				}
	 
				public boolean visit(SimpleName node) {
					if (this.names.contains(node.getIdentifier())) {
						System.out.println("Usage of '" + node + "' at line "
								+ cu.getLineNumber(node.getStartPosition()));
					}
					return true;
				}
			});*/
	 
		}
	 
		//read file content into a string
		public static String readFileToString(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	 
			char[] buf = new char[10];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				//System.out.println(numRead);
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
	 
			reader.close();
	 
			return  fileData.toString();	
		}
	 
		public static List<Class> parseAllInDir(String path) throws IOException{
			List<Class> result = new ArrayList<Class>();
			parseFilesInDir(path, result);
			return result;
		}
		//loop directory to get file list
		private static void parseFilesInDir(String path, List<Class> result) throws IOException{
			File dirs = new File(path);
			String dirPath = dirs.getCanonicalPath();
	 
			File root = new File(dirPath);
			//System.out.println(rootDir.listFiles());
			File[] files = root.listFiles ( );
			String filePath = null;
			 for (File f : files ) {
				 filePath = f.getAbsolutePath();
				 if(f.isFile() && FilenameUtils.getExtension(filePath).equalsIgnoreCase("java")){
					 result.addAll(parse(readFileToString(filePath), filePath));
					 System.out.println("Finished parsing " + filePath);
				 }else if(f.isDirectory()){
					 parseFilesInDir(filePath, result);
				 }
			 }
		}

}
