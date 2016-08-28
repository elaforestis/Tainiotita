import java.io.File;
import java.util.Vector;


public class PublicValues {
	public static Vector movieNames = new Vector();
	public static Vector movieSeries = new Vector();
	public static Vector resolution = new Vector();
	public static Vector availability = new Vector();
	public static Vector moviePath = new Vector();
	public static Object[][] newData;
	public static Boolean changed = false;
	public static Vector seriesPath = new Vector();
	public static boolean seriesMode = false;
	public static String currentSelectedTreeNode;

	
	public static void updatePublicValues(){
		newData = new Object[movieNames.size()][4];
		for(int i = 0;i<movieNames.size();i++){
			for(int j = 0;j<4;j++){
				//System.out.println("j:" + j + " i:" + i); //debugging
				switch(j){
					case 0:
						//System.out.println(movieNames.get(i));
						newData[i][0] = movieNames.get(i);
						break;
					case 1:
						//System.out.println(movieSeries.get(i));
						newData[i][1] = movieSeries.get(i);
						break;
					case 2:
						//System.out.println(resolution.get(i));
						newData[i][2] = resolution.get(i);
						break;
					case 3:
						//System.out.println(availability.get(i));
						newData[i][3] = availability.get(i);
						break;
				}
			}
		}
	}
}
