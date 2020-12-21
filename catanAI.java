import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

public class catanAI {
	public static void main(String [] args) throws IOException {
		JFileChooser chooser = new  JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
		chooser.addChoosableFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		int lines = 0;
		String text = "";
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File myfile = chooser.getSelectedFile();
            BufferedReader infile = new BufferedReader(new FileReader(myfile));
            String data = infile.readLine();
            if(data != null) {
            	data = infile.readLine();
            	lines++;
            	text += data;
            	data = infile.readLine();
           		while(data != null) {
           			lines++;
                	text += "\n" + data;
                	data = infile.readLine();
            	}
        	}
            infile.close();
		}
		//System.out.println(text);
		String dataStringLines[] = text.split("\n");
		String dataStrings[][] = new String[lines][15];
		for(int i = 0; i < lines; i++) {
			String temp[] = dataStringLines[i].split(",");
			dataStrings[i][0] = temp[0];
			dataStrings[i][1] = temp[1];
			dataStrings[i][2] = temp[2];
			System.out.println(temp[2]);
			for(int j = 15; j < 27; j++) {
				dataStrings[i][j-12] = temp[j];
			}
		}
		//System.out.println(Arrays.deepToString(dataStrings));
		//game number, player, points, L, C, W, S, O
		int datas[][] = new int[lines][8];
		for(int i = 0; i < dataStrings.length; i++) {
			for(int j = 0; j < 3; j++) {
				datas[i][j] = Integer.parseInt(dataStrings[i][j]);
			}
			for(int j = 3; j <= 13; j+= 2) {
				int pts = 0;
				if(Integer.parseInt(dataStrings[i][j]) == 2 || Integer.parseInt(dataStrings[i][j]) == 12) {
					pts = 1;
				}
				else if(Integer.parseInt(dataStrings[i][j]) == 3 || Integer.parseInt(dataStrings[i][j]) == 11) {
					pts = 2;
				}
				else if(Integer.parseInt(dataStrings[i][j]) == 4 || Integer.parseInt(dataStrings[i][j]) == 10) {
					pts = 3;
				}
				else if(Integer.parseInt(dataStrings[i][j]) == 5 || Integer.parseInt(dataStrings[i][j]) == 9) {
					pts = 4;
				}
				else if(Integer.parseInt(dataStrings[i][j]) == 6 || Integer.parseInt(dataStrings[i][j]) == 8) {
					pts = 5;
				}
				if(dataStrings[i][j+1].equals("L")) {
					datas[i][3] += pts;
				}
				else if(dataStrings[i][j+1].equals("C")) {
					datas[i][4] += pts;
				}
				else if(dataStrings[i][j+1].equals("W")) {
					datas[i][5] += pts;
				}
				else if(dataStrings[i][j+1].equals("S")) {
					datas[i][6] += pts;
				}
				else if(dataStrings[i][j+1].equals("O")) {
					datas[i][7] += pts;
				}
			}
		}
		System.out.println(Arrays.deepToString(datas));
		double avgWood = 0;
		double avgClay = 0;
		double avgWheat = 0;
		double avgSheep = 0;
		double avgOre = 0;
		for(int i = 0; i < 200; i++) {
			if(datas[i][2] >= 10) {
				avgWood += datas[i][3];
				avgClay += datas[i][4];
				avgWheat += datas[i][5];
				avgSheep += datas[i][6];
				avgOre += datas[i][7];
			}
		}
		avgWood /= 50.0;
		avgClay /= 50.0;
		avgWheat /= 50.0;
		avgSheep /= 50.0;
		avgOre /= 50.0;
		System.out.println(avgWood);
		System.out.println(avgClay);
		System.out.println(avgWheat);
		System.out.println(avgSheep);
		System.out.println(avgOre);
		double num = 0;
		double correct = 0;
		for(int i = 0; i < datas.length; i += 4) {
			double p1 = euclidianDist(new double[]{datas[i][3], datas[i][4], datas[i][5], datas[i][6], datas[i][7]},new double[] {avgWood, avgClay, avgWheat, avgSheep, avgOre});
			double p2 = euclidianDist(new double[] {datas[i+1][3], datas[i+1][4], datas[i+1][5], datas[i+1][6], datas[i+1][7]},new double[]{avgWood, avgClay, avgWheat, avgSheep, avgOre});
			double p3 = euclidianDist(new double[] {datas[i+2][3], datas[i+2][4], datas[i+2][5], datas[i+2][6], datas[i+2][7]},new double[]{avgWood, avgClay, avgWheat, avgSheep, avgOre});
			double p4 = euclidianDist(new double[] {datas[i+3][3], datas[i+3][4], datas[i+3][5], datas[i+3][6], datas[i+3][7]},new double[]{avgWood, avgClay, avgWheat, avgSheep, avgOre});
			double min = p1;
			int player = 1;
			if(p2<min) {
				min = p2;
				player = 2;
			}
			if(p3<min) {
				min = p3;
				player = 3;
			}
			if(p4<min) {
				min = p4;
				player = 4;
			}
			//System.out.println("winner is " + player);
			//System.out.println(datas[i+player-1][2]);
			if(datas[i+player-1][2] >= 10) {
				correct++;
			}
			num++;
		}
		System.out.println(correct);
		System.out.println("% correct: " + (correct/num*100.0));
		
		num = 0;
		correct = 0;
		for(int i = 0; i < datas.length; i += 4) {
			double p1 = euclidianDist(new double[]{datas[i][3], datas[i][4], datas[i][5], datas[i][6], datas[i][7]},new double[] {20.0, 20.0, 20.0, 20.0, 20.0});
			double p2 = euclidianDist(new double[] {datas[i+1][3], datas[i+1][4], datas[i+1][5], datas[i+1][6], datas[i+1][7]},new double[]{20.0, 20.0, 20.0, 20.0, 20.0});
			double p3 = euclidianDist(new double[] {datas[i+2][3], datas[i+2][4], datas[i+2][5], datas[i+2][6], datas[i+2][7]},new double[]{20.0, 20.0, 20.0, 20.0, 20.0});
			double p4 = euclidianDist(new double[] {datas[i+3][3], datas[i+3][4], datas[i+3][5], datas[i+3][6], datas[i+3][7]},new double[]{20.0, 20.0, 20.0, 20.0, 20.0});
			double min = p1;
			int player = 1;
			if(p2<min) {
				min = p2;
				player = 2;
			}
			if(p3<min) {
				min = p3;
				player = 3;
			}
			if(p4<min) {
				min = p4;
				player = 4;
			}
			//System.out.println("winner is " + player);
			//System.out.println(datas[i+player-1][2]);
			if(datas[i+player-1][2] >= 10) {
				correct++;
			}
			num++;
		}
		System.out.println(correct);
		System.out.println("% correct: " + (correct/num*100.0));
		
	}
	public static double euclidianDist(double line[], double avg[]) {
		double returnVal = 0;
		for(int i = 0; i < 5; i++) {
			returnVal += (line[i]-avg[i])*(line[i]-avg[i]);
		}
		return Math.sqrt(returnVal);
	}
	
	public static double chebyDist(double line[], double avg[]) {
		double returnVal = 0;
		returnVal = Math.max(returnVal, Math.abs(line[0] - avg[0]));
		returnVal = Math.max(returnVal, Math.abs(line[1] - avg[1]));
		returnVal = Math.max(returnVal, Math.abs(line[2] - avg[2]));
		returnVal = Math.max(returnVal, Math.abs(line[3] - avg[3]));
		returnVal = Math.max(returnVal, Math.abs(line[4] - avg[4]));
		return returnVal;
	}
	
	public static double manhattanDist(double line[], double avg[]) {
		double returnVal = Math.abs(line[0] - avg[0]);
		returnVal += Math.abs(line[1] - avg[1]);
		returnVal += Math.abs(line[2] - avg[2]);
		returnVal += Math.abs(line[3] - avg[3]);
		returnVal += Math.abs(line[4] - avg[4]);
		return returnVal;
	}
}
