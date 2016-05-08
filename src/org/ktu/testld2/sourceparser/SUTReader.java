package org.ktu.testld2.sourceparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import eu.sarunas.atf.generators.code.java.CodeGeneratorJava;
import eu.sarunas.atf.generators.code.junit.TestTransformerJUnit;
import eu.sarunas.atf.generators.tests.TestsGenerator;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.tests.TestProject;
import java.io.*;

public class SUTReader {
	private static List<Class> testedClasses;
	private static String outputPath;
	
//C:\Users\Vidmantas\Desktop\Stuff\Others\Randoom\ZaDoom\sa_ld2\Original
	//C:\Users\Vidmantas\Desktop\Stuff\Others\Randoom\ZaDoom\sa_ld2\FFT\traditional_mutants
	//C:\Users\Vidmantas\Desktop\Stuff\Others\Randoom\ZaDoom\sa_ld2\TestRes
	public static void main(String[] args) throws FileNotFoundException{
		try {
			testedClasses = new ArrayList<Class>();
			if(args.length > 0){
				if(args.length > 1){
					outputPath = args[1];
				}else{
					System.out.println("Enter the output path:");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					outputPath = br.readLine();
				}
				parseFiles(args[0]);
			}else{
				parseFiles();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		tryTestGen();
	}
	private static void parseFiles() throws IOException{
		System.out.println("Enter the path to target directory:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();
		System.out.println("Enter the output path:");
		outputPath = br.readLine();
		//SourceFileParser.parse(SourceFileParser.readFileToString(path));
		testedClasses = SourceFileParser.parseAllInDir(path);
		System.out.println("Parsing operation completed successfully! A total of " + testedClasses.size() + " java classes have been found.");
		
	}
	private static void parseFiles(String path) throws IOException{
		testedClasses = SourceFileParser.parseAllInDir(path);
		System.out.println("Parsing operation completed successfully! A total of " + testedClasses.size() + " java classes have been found.");
		
	}
	
	private static void tryTestGen() throws FileNotFoundException{
		TestsGenerator tg = new TestsGenerator();
		TestProject fakeProj = new TestProject();
		fakeProj.setName("TestProj");
		int counter = 0;
		for(Class cl:testedClasses){
			for(eu.sarunas.atf.meta.sut.Method meth:cl.getMethods()){
				System.out.println("Generating tests for " + cl.getName() + " (from " + cl.getFromFilePath() + ")");
				try {
					eu.sarunas.atf.meta.tests.TestSuite ts = tg.generate(meth, fakeProj, "");
						TestTransformerJUnit trasnformer = new TestTransformerJUnit();
						eu.sarunas.atf.meta.sut.Class tclass = trasnformer.transformTest(ts);

						CodeGeneratorJava cdgj = new CodeGeneratorJava();
						String code = cdgj.generateClass(tclass);
						
						try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					              new FileOutputStream(outputPath +"//" + (counter++) + "_" + cl.getName() + "test.java"), "utf-8"))) {
							dumpToFile(writer, code );
						}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static void dumpToFile(Writer pw, String str) throws IOException{
		pw.write(str);
	}

}
