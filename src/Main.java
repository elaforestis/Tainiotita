
public class Main {
	static Gui x;
	static ControlPanel y;
	public static void main(String args[]){
		x = new Gui();
		y = new ControlPanel();
		x.loadDB();
		x.addComponentListener(x);
		y.addComponentListener(y);
		x.setLocation(x.getLocation().x+105, x.getLocation().y); //+5 to compensate for controlpanel borders
		x.setVisible(true);
		y.setVisible(true);
	}
}
/*
 * implement: 
 * 			
 * remove single cell border
 * movie series row #10>#2 etc... fix
 * when clicking movie series autofill # (add comboseries: keylistener)
 * rename to movie mode button when in series mode
 * imdb implementation (check bookmarks)
 * auto add implementation (new class,check for 720p/1080p/brrip/dvdrip/dvd/bluray...,check fileformat to add, exclude samples(<5 min duration or sample in name),check imdb for matches at end)
 * show only dled movies
 * 
 * 
 * 
 * 
 * bugs:
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
*/